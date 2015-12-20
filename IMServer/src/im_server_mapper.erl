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

%% ------------------------------------------------------------------
%% gen_server Function Exports
%% ------------------------------------------------------------------

-export([init/1, handle_call/3, handle_cast/2, handle_info/2,
         terminate/2, code_change/3]).

-record(state, {
            mapid
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
    MapId = ets:new(mapper, [set]),
    State1 = State#state{
                mapid = MapId
              },
    error_logger:info_msg("create mappmer success~n"),
    {ok, State1}.

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

lookup(Account) ->
    gen_server:call(?MODULE, {lookup, Account}).

insert(Account, Socket) ->
    gen_server:call(?MODULE, {insert, Account, Socket}).

delete(Account) ->
    gen_server:call(?MODULE, {delete, Account}).
