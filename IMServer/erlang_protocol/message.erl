-module(message).

-compile([export_all]).

-include("message_pb.hrl").

encodeLogin() ->
    Message = #login{mark = 1, selfaccount = "wangweihao", id = 100},
    message_pb:encode_login(Message).

encodeSendMsg() ->
    Message = #sendmsg{mark = 1, friendaccount = "weihao", msg = "hello world", time = "16:07", id = 10000},
    message_pb:encode_sendmsg(Message).

encodeAck() ->
    Message = #ack{mark = 1, msgid = 10000},
    message_pb:encode_ack(Message).

encodeQuit() ->
    Message = #quit{mark = 4},
    message_pb:encode_quit(Message).

encodeKeepAlive() ->
    Message = #keepalive{mark = 5, isalive = true},
    message_pb:encode_keepalive(Message).
