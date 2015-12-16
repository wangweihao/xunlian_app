
-module(im_server_sup).

-behaviour(supervisor).

%% API
-export([start_link/0]).

%% Supervisor callbacks
-export([init/1]).

-define(MAX_RESTART,    10).
-define(MAX_TIME,      60).
-define(LISTEN_PORT, 9090).

%% ===================================================================
%% API functions
%% ===================================================================

start_link() ->
    supervisor:start_link({local, ?MODULE}, ?MODULE, [?LISTEN_PORT, transport]).

%% ===================================================================
%% Supervisor callbacks
%% ===================================================================

init([Port, Module]) ->
    {ok, 
     { {one_for_one, ?MAX_RESTART, ?MAX_TIME}, 
           [
            {im_server_acceptor,
             {im_server_acceptor, start_link, [self(), Port, Module]},
             permanent,
             2000,
             worker,
             [im_server_acceptor]
            }    
           ]
     } 
    }.

