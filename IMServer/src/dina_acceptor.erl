-module(dina_acceptor).

-export([start_link/3]).

-export([acceptor_init/3 acceptor_loop/1]).

-record(state, {
            parent,
            module,
            port,
            listener
         }).

