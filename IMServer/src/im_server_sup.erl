
-module(im_server_sup).

-behaviour(supervisor).

%% API
-export([start_link/0]).

%% Supervisor callbacks
-export([init/1]).

-define(LISTEN_PORT, 9090).


%% ===================================================================
%% API functions
%% ===================================================================

start_link() ->
    supervisor:start_link({local, ?MODULE}, ?MODULE, [?LISTEN_PORT, im_server_transport]).

%% ===================================================================
%% Supervisor callbacks
%% ===================================================================

init([Port, Module]) ->
    {ok, { {one_for_one, 5, 10}, 
           [
            %% 监听进程
            {
                im_server_listener, {im_server_listener, start_link, [self(), Port, Module]},
                permanent,
                brutal_kill,
                worker,
                [im_server_listener]
            },

            %% 处理消息进程督程
            {
                im_server_transport_sup, {im_server_transport_sup, start_link, []},
                permanent,
                infinity,
                supervisor,
                [im_server_transport_sup]
            },

            %% 存储用户 Socket 映射进程
            {
                im_server_mapper, {im_server_mapper, start_link, []},
                permanent,
                brutal_kill,
                worker,
                [im_server_mapper]
            } 
           ]
         
         } }.

