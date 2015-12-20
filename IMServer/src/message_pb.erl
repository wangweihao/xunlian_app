-file("src/message_pb.erl", 1).

-module(message_pb).

-export([encode_head/1, decode_head/1,
	 encode_keepalive/1, decode_keepalive/1, encode_quit/1,
	 decode_quit/1, encode_ack/1, decode_ack/1,
	 encode_sendmsg/1, decode_sendmsg/1, encode_login/1,
	 decode_login/1]).

-record(head, {mark, version}).

-record(keepalive, {isalive}).

-record(quit, {quit}).

-record(ack, {msgid}).

-record(sendmsg, {friendaccount, msg, time, id}).

-record(login, {selfaccount, id}).

encode_head(Record) when is_record(Record, head) ->
    encode(head, Record).

encode_keepalive(Record)
    when is_record(Record, keepalive) ->
    encode(keepalive, Record).

encode_quit(Record) when is_record(Record, quit) ->
    encode(quit, Record).

encode_ack(Record) when is_record(Record, ack) ->
    encode(ack, Record).

encode_sendmsg(Record)
    when is_record(Record, sendmsg) ->
    encode(sendmsg, Record).

encode_login(Record) when is_record(Record, login) ->
    encode(login, Record).

encode(login, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#login.selfaccount, none), string,
			   []),
		      pack(2, required, with_default(Record#login.id, none),
			   int32, [])]);
encode(sendmsg, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#sendmsg.friendaccount, none),
			   string, []),
		      pack(2, required,
			   with_default(Record#sendmsg.msg, none), string, []),
		      pack(3, required,
			   with_default(Record#sendmsg.time, none), string, []),
		      pack(4, required, with_default(Record#sendmsg.id, none),
			   int32, [])]);
encode(ack, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#ack.msgid, none), int32, [])]);
encode(quit, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#quit.quit, none), int32, [])]);
encode(keepalive, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#keepalive.isalive, none), bool,
			   [])]);
encode(head, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#head.mark, none), int32, []),
		      pack(3, required,
			   with_default(Record#head.version, none), int32,
			   [])]).

with_default(undefined, none) -> undefined;
with_default(undefined, Default) -> Default;
with_default(Val, _) -> Val.

pack(_, optional, undefined, _, _) -> [];
pack(_, repeated, undefined, _, _) -> [];
pack(FNum, required, undefined, Type, _) ->
    exit({error,
	  {required_field_is_undefined, FNum, Type}});
pack(_, repeated, [], _, Acc) -> lists:reverse(Acc);
pack(FNum, repeated, [Head | Tail], Type, Acc) ->
    pack(FNum, repeated, Tail, Type,
	 [pack(FNum, optional, Head, Type, []) | Acc]);
pack(FNum, _, Data, _, _) when is_tuple(Data) ->
    [RecName | _] = tuple_to_list(Data),
    protobuffs:encode(FNum, encode(RecName, Data), bytes);
pack(FNum, _, Data, Type, _) ->
    protobuffs:encode(FNum, Data, Type).

decode_head(Bytes) when is_binary(Bytes) ->
    decode(head, Bytes).

decode_keepalive(Bytes) when is_binary(Bytes) ->
    decode(keepalive, Bytes).

decode_quit(Bytes) when is_binary(Bytes) ->
    decode(quit, Bytes).

decode_ack(Bytes) when is_binary(Bytes) ->
    decode(ack, Bytes).

decode_sendmsg(Bytes) when is_binary(Bytes) ->
    decode(sendmsg, Bytes).

decode_login(Bytes) when is_binary(Bytes) ->
    decode(login, Bytes).

decode(login, Bytes) when is_binary(Bytes) ->
    Types = [{2, id, int32, []},
	     {1, selfaccount, string, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(login, Decoded);
decode(sendmsg, Bytes) when is_binary(Bytes) ->
    Types = [{4, id, int32, []}, {3, time, string, []},
	     {2, msg, string, []}, {1, friendaccount, string, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(sendmsg, Decoded);
decode(ack, Bytes) when is_binary(Bytes) ->
    Types = [{1, msgid, int32, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(ack, Decoded);
decode(quit, Bytes) when is_binary(Bytes) ->
    Types = [{1, quit, int32, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(quit, Decoded);
decode(keepalive, Bytes) when is_binary(Bytes) ->
    Types = [{1, isalive, bool, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(keepalive, Decoded);
decode(head, Bytes) when is_binary(Bytes) ->
    Types = [{3, version, int32, []}, {1, mark, int32, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(head, Decoded).

decode(<<>>, _, Acc) -> Acc;
decode(Bytes, Types, Acc) ->
    {{FNum, WireType}, Rest} =
	protobuffs:read_field_num_and_wire_type(Bytes),
    case lists:keysearch(FNum, 1, Types) of
      {value, {FNum, Name, Type, Opts}} ->
	  {Value1, Rest1} = case lists:member(is_record, Opts) of
			      true ->
				  {V, R} = protobuffs:decode_value(Rest,
								   WireType,
								   bytes),
				  RecVal =
				      decode(list_to_atom(string:to_lower(atom_to_list(Type))),
					     V),
				  {RecVal, R};
			      false ->
				  {V, R} = protobuffs:decode_value(Rest,
								   WireType,
								   Type),
				  {unpack_value(V, Type), R}
			    end,
	  case lists:member(repeated, Opts) of
	    true ->
		case lists:keytake(FNum, 1, Acc) of
		  {value, {FNum, Name, List}, Acc1} ->
		      decode(Rest1, Types,
			     [{FNum, Name,
			       lists:reverse([Value1 | lists:reverse(List)])}
			      | Acc1]);
		  false ->
		      decode(Rest1, Types, [{FNum, Name, [Value1]} | Acc])
		end;
	    false ->
		decode(Rest1, Types, [{FNum, Name, Value1} | Acc])
	  end;
      false -> exit({error, {unexpected_field_index, FNum}})
    end.

unpack_value(Binary, string) when is_binary(Binary) ->
    binary_to_list(Binary);
unpack_value(Value, _) -> Value.

to_record(login, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, login), Record,
					 Name, Val)
		end,
		#login{}, DecodedTuples);
to_record(sendmsg, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, sendmsg), Record,
					 Name, Val)
		end,
		#sendmsg{}, DecodedTuples);
to_record(ack, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, ack), Record, Name,
					 Val)
		end,
		#ack{}, DecodedTuples);
to_record(quit, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, quit), Record,
					 Name, Val)
		end,
		#quit{}, DecodedTuples);
to_record(keepalive, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, keepalive), Record,
					 Name, Val)
		end,
		#keepalive{}, DecodedTuples);
to_record(head, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, head), Record,
					 Name, Val)
		end,
		#head{}, DecodedTuples).

set_record_field(Fields, Record, Field, Value) ->
    Index = list_index(Field, Fields),
    erlang:setelement(Index + 1, Record, Value).

list_index(Target, List) -> list_index(Target, List, 1).

list_index(Target, [Target | _], Index) -> Index;
list_index(Target, [_ | Tail], Index) ->
    list_index(Target, Tail, Index + 1);
list_index(_, [], _) -> 0.

