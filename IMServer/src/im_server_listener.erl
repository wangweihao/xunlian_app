-module(im_server_listener).

-export([start_link/3]).
-export([sleep/1]).
-export([acceptor_init/3]).
-export([acceptor_loop/1]).

-define(TIMEOUT, 50000).

%% 记录组保存必要信息，便于在整个文件中使用
-record(state, {
            parent,
            module,   % 处理模块
            port,
            listener  % 监听套接字
         }).


start_link(Parent, Port, Module) 
    when is_pid(Parent),
         is_list(Port),
         is_atom(Module) ->
    start_link(Parent, list_to_integer(Port), Module);
start_link(Parent, Port, Module)
    when is_pid(Parent),
         is_integer(Port),
         is_atom(Module) ->
    Args = [Parent, Port, Module],
    proc_lib:start_link(?MODULE, acceptor_init, Args).


%% 初始化 acceptor 接收器
acceptor_init(Parent, Port, Module) ->
    %% 变为系统进程，避免接受到 kill 信号导致终止
    process_flag(trap_exit, true),
    im_server_handle:start_link(),
    State = #state{
        parent = Parent,
        port = Port,
        module = Module
        },
    error_logger:info_msg("listening on port ~p~n", [Port]),
    case (catch do_init(State)) of
        {ok, ListenSocket} ->
            %% 同步创建，表明创建成功，回复 ack
            proc_lib:init_ack(State#state.parent, {ok, self()}),
            acceptor_loop(State#state{listener = ListenSocket});
        Error ->
            proc_lib:init_ack(Parent, Error),
            error
    end.


do_init(State) ->
    Options = [ binary,
                {packet, 2},
               %% {packet, 0},
               {reuseaddr, true},
               {backlog, 1024},
               %% {active, false}
               %% {active, true}
               {active, once}
              ],
    register(listener, self()),
    case gen_tcp:listen(State#state.port, Options) of
        {ok, ListenSocket} ->
            error_logger:info_msg("create listen socket success~n"),
            {ok, ListenSocket};
        {error, Reason}    ->
            throw({error, {listen, Reason}})
    end.


acceptor_loop(State) ->
    io:format("acceptor loop ~p~n", [State]),
    case (catch gen_tcp:accept(State#state.listener, ?TIMEOUT)) of
        {ok, Socket}     ->
            handle_connection(State, Socket),
            ?MODULE:acceptor_loop(State);
        {error, Reason}  ->
            handle_error(Reason),
            ?MODULE:acceptor_loop(State);
        {'EXIT', Reason} ->
            handle_error({'EXIT', Reason}),
            ?MODULE:acceptor_loop(State)
    end.

%% 注意连接断开时进程退出，否则大量进程占用资源
handle_connection(State, Socket) ->
    inet:setopts(Socket, [{packet, 2}, binary, 
                          {nodelay, true}, {active, once}]),
    io:format("receive connection socket:~p~n", [Socket]),
    io:format("create handle message process ~n"),
    {ok, Pid} = (State#state.module):start_link(),
    io:format("Pid:~p~n", [Pid]),
    ok = gen_tcp:controlling_process(Socket, Pid).
    %% 创建一个收发消息的进程
    %%{ok, Pid} = im_server_app:start_handle(State#state.port),
    %%ok = gen_tcp:controlling_process(Socket, Pid).
    %% 创建一个编码/解码的进程


handle_error(timeout) ->
    ok;
handle_error(closed)  ->
    error_logger:info_report("The accept Socket closed");
handle_error(econnreset) ->
    exit(normal);
handle_error({'EXIT', Reason}) ->
    error_logger:error_report(Reason),
    exit({accept_failed, Reason});
handle_error(Reason)  ->
    {error, Reason}.

sleep(T) ->
    receive
    after T -> ok end.

