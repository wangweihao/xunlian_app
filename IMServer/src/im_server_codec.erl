-module(im_server_codec).

-export([encode/4]).
-export([decode/2]).

-include("message_pb.hrl").


%% 解码 google protocol buffer
decode(Socket, Message) ->
    %% 1.获取数据的类型
    <<Mark:8/bitstring, Protobuf/bitstring>> = Message,
    %% 2.根据 Mark 提取 protocol buffer 数据
    io:format("------------- make:~p ----------------~n", [Mark]),
    case Mark of 
        % mark 1 登录，向数据库里插入信息
        <<1>> ->
            case (message_pb:decode_login(Protobuf)) of
                {login, SelfAccount, _Id} ->
                    case (im_server_mapper:insert(SelfAccount, Socket)) of
                        ok     ->
                            io:format("----------- insert date --------------~n"),
                            io:format("account:~p, socket:~p~n", [SelfAccount, Socket]),
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
        <<2>> ->
            case (message_pb:decode_sendmsg(Protobuf)) of
                {sendmsg, Account, Msg, Time, Id} ->
                    %% 3.根据 mark 查询 ets 表
                    case (im_server_mapper:lookup(Account)) of
                        FSocket ->
                            %% 4.接受查询结果，组装成 protocol buffer
                            SMsg = encode(Account, Msg, Time, Id),
                            %% 5.发送给好友
                            case gen_tcp:send(FSocket, SMsg) of
                                ok ->
                                    io:format("send friend message success~n");
                                {error, Reason} ->
                                    io:format("send friend message error:~p~n", Reason)
                            end;
                        error  ->
                            ok
                    end,
                    ok;
                _ ->
                    error
            end,
            ok;
        _ ->
            io:format("------------------- failure ---------------------~n"),
            gen_tcp:send(Socket, "hello world\n"),
            error
    end,
    sleep(500).

encode(Account, Msg, Time, Id) ->
    SMsg = #sendmsg{friendaccount = Account, msg = Msg, time = Time, id = Id},
    M = message_pb:encode_sendmsg(SMsg),
    M.

sleep(T) ->
    receive
    after T -> ok end.
