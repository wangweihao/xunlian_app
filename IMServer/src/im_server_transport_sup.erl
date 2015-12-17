-module(im_server_transport_sup).

-behaviour(supervisor).

%% API
-export([start_link/0]).

%% Supervisor callbacks
-export([init/1]).

start_link() ->
    supervisor:start_link({local, ?MODULE}, ?MODULE, []).


%% ===================================================================
%% Supervisor callbacks
%% ===================================================================

init([]) ->
    {ok, { {simple_one_for_one, 5, 10},
           [
            %% 处理消息进程
            {
                im_server_transport, {im_server_transport, start_link, []},
                temporary,
                brutal_kill,
                worker,
                [im_server_transport]
            }
           ]
         } }.
