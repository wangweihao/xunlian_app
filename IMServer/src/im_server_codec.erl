-module(im_server_codec).

-export([encode/1]).
-export([decode/2]).

-include("./protobuf/message_pb.hrl").


%% 解码 google protocol buffer
decode(Socket, Message) ->
    %% 1.获取数据的类型
    Size = byte_size(Message),
    PSize = Size*8-8,
    <<Mark:8/bitstring, Protobuf:PSize/bitstring>> = Message,
    %% 2.根据 Mark 提取 protocol buffer 数据
    case Mark of 
        % mark 1 登录，向数据库里插入信息
        1 ->
            case (message_pb:decode_login(Protobuf)) of
                {login, SelfAccount, _Id} ->
                    case (im_server_mapper:insert(SelfAccount, Socket)) of
                        ok     ->
                            ok;
                        error  ->
                            ok
                    end,
                    ok;
                _ ->
                    error
            end,
            ok;
        % mark 2 聊天消息
        2 ->
            case (message_pb:decode_sendmsg(Protobuf)) of
                {sendmsg, Account, _Msg, _Time, _Id} ->
                    case (im_server_mapper:lookup(Account)) of
                        Socket ->
                            ok;
                        error  ->
                            ok
                    end,
                    ok;
                _ ->
                    error
            end,
            ok;
        _ ->
            gen_tcp:send(Socket, "hello world"),
            error
    end,
    %% 3.根据 mark 查询 ets 表
    %% 4.接受查询结果，组装成 protocol buffer
    %% 5.发送给好友
    io:format("解码:~p~n", [Message]),
    encode(Message), 
    io:format("发送给好友~n"),
    sleep(5000).

encode(_Message) ->
    ok.

sleep(T) ->
    receive
    after T -> ok end.
