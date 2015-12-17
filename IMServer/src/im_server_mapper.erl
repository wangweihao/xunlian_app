-module(im_server_mapper).
-behaviour(gen_server).
-define(SERVER, ?MODULE).

%% ------------------------------------------------------------------
%% API Function Exports
%% ------------------------------------------------------------------

-export([start_link/0]).
-export([lookup/2, insert/3]).

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

lookup(Account, Pid) ->
    gen_server:call(?MODULE, {lookup, Account, Pid}).

insert(Account, Socket, Pid) ->
    gen_server:call(?MODULE, {insert, Account, Socket, Pid}).

%% ------------------------------------------------------------------
%% gen_server Function Definitions
%% ------------------------------------------------------------------

init([State]) ->
    MapId = ets:new(mapper, [set]),
    State1 = State#state{
                mapid = MapId
              },
    {ok, State1}.

handle_call({lookup, Account, Pid}, _From, State) ->
    case (catch ets:lookup(State#state.mapid, Account)) of
        [{_, Socket}] ->
            Pid ! {Socket};
        _  ->
            Pid ! {error}
    end,
    {reply, ok, State};

handle_call({insert, Account, Socket, Pid}, _From, State) ->
    case (catch ets:insert(State#state.mapid, {Account, Socket})) of
        true ->
            Pid ! {ok};
        _ ->
            Pid ! {error}
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

