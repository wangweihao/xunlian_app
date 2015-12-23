-module(im_server_mapper).
-behaviour(gen_server).
-define(SERVER, ?MODULE).

%% ------------------------------------------------------------------
%% API Function Exports
%% ------------------------------------------------------------------

-export([start_link/0]).
-export([lookup/1]).
-export([insert/2]).
-export([delete/1]).
-export([delete_quit/1]).
-export([offline_message/1]).
-export([add_offline_message/2]).

%% ------------------------------------------------------------------
%% gen_server Function Exports
%% ------------------------------------------------------------------

-export([init/1, handle_call/3, handle_cast/2, handle_info/2,
         terminate/2, code_change/3]).

-record(state, {
            mapid,
            offlineid
         }).

%% ------------------------------------------------------------------
%% API Function Definitions
%% ------------------------------------------------------------------

start_link() ->
    gen_server:start_link({local, ?SERVER}, ?MODULE, [#state{}], []).


%% ------------------------------------------------------------------
%% gen_server Function Definitions
%% ------------------------------------------------------------------

init([State]) ->
    %% 存放连接用户信息
    MapId = ets:new(mapper, [set]),
    %% 存放离线信息，当用户登录时，该信息被发送并删除
    MapOffline = ets:new(mapper_offline, [bag]),

    State1 = State#state{
                mapid = MapId,
                offlineid = MapOffline
              },
    error_logger:info_msg("create mappmer success~n"),
    {ok, State1}.

%% 添加用户的离线消息
handle_call({add_offline_message, Account, Message}, _From, State) ->
    error_logger:info_msg("Account:~p offline Message:~p~n", [Account, Message]),
    Reply = case(catch (ets:insert(State#state.offlineid, {Account, Message}))) of
                true ->
                    ok;
                _    ->
                    error
            end,
    {reply, Reply, State};

%% 客户端登录时，发送离线消息
handle_call({offline, Account}, _From, State) ->
    error_logger:info_msg("Account:~p offline message~n", [Account]),
    Reply = case(catch (ets:lookup(State#state.offlineid, Account))) of
                [] ->
                    [];
                OfflineMsg  ->
                    OfflineMsg
            end,
    catch (ets:delete(State#state.offlineid, Account)),
    {reply, Reply, State};

%% 客户端退出时，删除数据
handle_call({delete_quit, Socket}, _From, State) ->
    error_logger:info_msg("Socket:~p quit ~p~n", [?MODULE, Socket]),
    [{_, Account}] = ets:lookup(State#state.mapid, Socket),
    ets:delete(State#state.mapid, Account),
    ets:delete(State#state.mapid, Socket),
    {reply, ok, State};

%% 用户退出要从 ets 表中删除数据
handle_call({delete, Account}, _From, State) ->
    error_logger:info_msg("Account:~p quit ~p~n", [?MODULE, Account]),
    case (catch (ets:delete(State#state.mapid, Account))) of
        true ->
            error_logger:info_msg("Account:~p left out ~n", [Account]);
        _    ->
            error_logger:info_msg("delete Account:~p error", [Account])
    end,
    {reply, ok, State};

handle_call({lookup, Account}, _From, State) ->
    %%io:format("data-+-+-+-+-+~p~n", [ets:tab2list(State#state.mapid)]),
    error_logger:info_msg("~p look up data ~p~n", [?MODULE, Account]),
    %%io:format("~p loop up data ~p~n", [?MODULE, Account]),
    Reply = case (catch (ets:lookup(State#state.mapid, Account))) of 
                [{_, Socket}] ->
                    io:format("lookup success~n"),
                    Socket;
                []  ->
                    io:format("lookup failure~n"),
                    error
                end,
    {reply, Reply, State};

handle_call({insert, Account, Socket}, _From, State) ->
    error_logger:info_msg("~p insert data ~p, ~p", [?MODULE, Account, Socket]),
    %%io:format("~p insert date ~p,~p", [?MODULE, Account, Socket]),
    case (catch ets:insert(State#state.mapid, {Account, Socket})) of
        true ->
            ets:insert(State#state.mapid, {Socket, Account}),
            io:format("insert success~n"),
            ok;
        _ ->
            error
    end,
    {reply, ok, State};

handle_call(_Request, _From, State) ->
    {reply, ok, State}.

handle_cast(_Msg, State) ->
    {noreply, State}.

handle_info(_Info, State) ->
    {noreply, State}.

terminate(_Reason, _State) ->
    ok.

code_change(_OldVsn, State, _Extra) ->
    {ok, State}.

%% ------------------------------------------------------------------
%% Internal Function Definitions
%% ------------------------------------------------------------------

add_offline_message(Account, Message) ->
    gen_server:call(?MODULE, {add_offline_message, Account, Message}).

offline_message(Account) ->
    gen_server:call(?MODULE, {offline, Account}).

lookup(Account) ->
    gen_server:call(?MODULE, {lookup, Account}).

insert(Account, Socket) ->
    gen_server:call(?MODULE, {insert, Account, Socket}).

delete(Account) ->
    gen_server:call(?MODULE, {delete, Account}).

delete_quit(Socket) ->
    gen_server:call(?MODULE, {delete_quit, Socket}).
