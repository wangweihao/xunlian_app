-module(im_client).

-export([client_start/2]).

-include("message_pb.hrl").

client_start(Account, FriendAccount) ->
    Msg = "hello world",
    {ok, Socket} = gen_tcp:connect("127.0.0.1", 9090,
                                  [binary, {packet, 2}]),
    LMsg = #login{selfaccount = Account, id = 100},
    LBin = message_pb:encode_login(LMsg),
    LType = 1,
    %% erlang client 若指明 {packet,2} 则不用自己封装长度
    %LSBin = <<LSize:16, LType:8, LBin/bitstring>>,
    LL = <<LType:8, LBin/bitstring>>,
    gen_tcp:send(Socket, LL), 

    sleep(2000),

    io:format("send message....~n"),
    SMsg = #sendmsg{friendaccount = FriendAccount, msg = Msg, time = "2015", id = 1000},
    Bin = message_pb:encode_sendmsg(SMsg),
    Type = 2,
    SS = <<Type:8, Bin/bitstring>>,
    gen_tcp:send(Socket, SS),
    io:format("send message success...~n"),
    sleep(2000),
    io:format("recv message...~n"),
    receive
        {tcp, Socket, EBin} ->
            io:format("client recv message~n"),
            io:format("Message:~p~n", [EBin]),
            R = message_pb:decode_sendmsg(EBin),
            io:format("Message:~p~n", [R]),
            gen_tcp:close(Socket),
            io:format("recv message success...~n");
        E -> 
            io:format("匹配失败...~n"),
            io:format("~p~n", [E])
    end.

sleep(T) ->
    receive
    after T -> ok end.
