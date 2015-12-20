-module(im_server_transport).
-behaviour(gen_server).

-define(SERVER, ?MODULE).

%% ------------------------------------------------------------------
%% API Function Exports
%% ------------------------------------------------------------------

-export([start_link/0]).

%% ------------------------------------------------------------------
%% gen_server Function Exports
%% ------------------------------------------------------------------

-export([init/1, handle_call/3, handle_cast/2, handle_info/2,
         terminate/2, code_change/3]).

%% ------------------------------------------------------------------
%% API Function Definitions
%% ------------------------------------------------------------------

start_link() ->
    gen_server:start_link(?MODULE, [], []).

%% ------------------------------------------------------------------
%% gen_server Function Definitions
%% ------------------------------------------------------------------

init(Args) ->
    im_server_handle:start_link(),
    {ok, Args}.


handle_call(_Request, _From, State) ->
    {reply, ok, State}.

handle_cast(_Msg, State) ->
    {noreply, State}.

handle_info({tcp, Socket, Data}, State) ->
    inet:setopts(Socket, [{active, once}]),
    error_logger:info_msg("receive Message => Pid:~p Socket:~p, Data:~p~n", [self(), Socket, Data]),
    im_server_handle:decode_message(Socket, Data),
    {noreply, State};

%% 客户端断开连接需要做一些处理
%% 1.删除 ets 表中的 key-value
handle_info({tcp_closed, Socket}, State) ->
    io:format("tcp_close...~n"),
    gen_tcp:close(Socket),
    %% 用户关闭连接则退出 transport 进程和 handle 进程
    exit(normal),
    {noreply, State};

handle_info({'EXIT', _, _}, State) ->
    error_logger:info_msg("handle_info exit ~n"),
    {noreply, State};

handle_info(_, State) ->
    error_logger:info_msg("handle_info other ~n"),
    {noreply, State}.

terminate(_Reason, _State) ->
    ok.

code_change(_OldVsn, State, _Extra) ->
    {ok, State}.

%% ------------------------------------------------------------------
%% Internal Function Definitions
%% ------------------------------------------------------------------

