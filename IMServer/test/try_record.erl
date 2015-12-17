-module(try_record).

-export([start_link/4]).

-record(state, {
            parent,
            module,
            port,
            listener
         }).

start_link(A, B, C ,D) ->
    #state{
        parent = A,
        module = B,
        port = C,
        listener = D
      },
    io:format("message~p~n", [#state.parent]).
