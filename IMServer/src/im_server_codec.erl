-module(im_server_codec).

-export([encode/1]).
-export([decode/1]).


encode(Message) ->
    io:format("解码~n"),
    decode(Message), 
    io:format("发送给好友~n"),
    sleep(5000).

decode(_Message) ->
    ok.

sleep(T) ->
    receive
    after T -> ok end.
