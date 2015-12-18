-module(message).

-compile([export_all]).

-include("message_pb.hrl").

%% mark 1
encodeLogin() ->
    Message = #login{selfaccount = "wangweihao", id = 100},
    A = message_pb:encode_login(Message),
    B = message_pb:decode_login(A),
    io:format("encode:~p~ndecode:~p ~n", [A,B]),
    io:format("Size:~p~n", [byte_size(A)]).

%% mark 2
encodeSendMsg() ->
    Message = #sendmsg{friendaccount = "weihao", msg = "hello world", time = "16:07", id = 10000},
    A = message_pb:encode_sendmsg(Message),
    B = message_pb:decode_sendmsg(A),
    io:format("encode:~p~ndecode:~p ~n", [A,B]),
    io:format("Size:~p~n", [byte_size(A)]).

%% mark 3
encodeAck() ->
    Message = #ack{msgid = 10000},
    message_pb:encode_ack(Message).

%% mark 4
encodeQuit() ->
    Message = #quit{quit = 4},
    message_pb:encode_quit(Message).

%% mark 5
encodeKeepAlive() ->
    Message = #keepalive{isalive = true},
    message_pb:encode_keepalive(Message).
