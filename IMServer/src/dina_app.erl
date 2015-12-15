-module(dina_app).

-behaviour(application).

%% Application callbacks
-export([start/2, stop/1, init/1]).

-define(MAX_RESTART,    5).
-define(MAX_TIME,      60).
-define(LISTEN_PORT, 8080).

%% ===================================================================
%% Application callbacks
%% 该 IMServer 启动时创建 3 个进程，
%% 分别负责接受连接，接受数据，建立映射等。
%% ===================================================================

start(_StartType, _StartArgs) ->
    dina_sup:start_link().

stop(_State) ->
    ok.

init([Port, Module]) ->
    {ok, 
     {_SupFlags = {one_for_one, ?MAX_RESTART, ?MAX_TIME}
        [
            %%Tcp Server:负责接受客户端的连接
            {
                dina_sup,
                {dina_acceptor, start_link, [self(), Port, Module]},
                permanent,
                2000,
                worker,
                [dina_acceptor]       
            },

            %%Client Connect supervisor:负责接受客户端的消息，并进行编码解码，回复等
            {
                mapper,
                {mapper, start, []},
                permanent,
                2000,
                worker,
                [mapper]
            },

            %%map:帐号和Socket 进行映射，便于找到对方。
            {
                dina_transport_sup,
                {supervisor, start_link, [{local, dina_transport_sup}, ?MODULE, [Module]]},
                permanent,
                infinity,
                supervisor,
                []  
            }
        ]
     }
    }
