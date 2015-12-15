-file("src/test_pb.erl", 1).

-module(test_pb).

-export([encode_family/1, decode_family/1,
	 encode_person/1, decode_person/1]).

-record(family, {person}).

-record(person, {age, name}).

encode_family(Record) when is_record(Record, family) ->
    encode(family, Record).

encode_person(Record) when is_record(Record, person) ->
    encode(person, Record).

encode(person, Record) ->
    iolist_to_binary([pack(1, required,
			   with_default(Record#person.age, none), int32, []),
		      pack(2, required,
			   with_default(Record#person.name, none), string,
			   [])]);
encode(family, Record) ->
    iolist_to_binary([pack(1, repeated,
			   with_default(Record#family.person, none), person,
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

decode_family(Bytes) when is_binary(Bytes) ->
    decode(family, Bytes).

decode_person(Bytes) when is_binary(Bytes) ->
    decode(person, Bytes).

decode(person, Bytes) when is_binary(Bytes) ->
    Types = [{2, name, string, []}, {1, age, int32, []}],
    Decoded = decode(Bytes, Types, []),
    to_record(person, Decoded);
decode(family, Bytes) when is_binary(Bytes) ->
    Types = [{1, person, 'Person', [is_record, repeated]}],
    Decoded = decode(Bytes, Types, []),
    to_record(family, Decoded).

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

to_record(person, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, person), Record,
					 Name, Val)
		end,
		#person{}, DecodedTuples);
to_record(family, DecodedTuples) ->
    lists:foldl(fun ({_FNum, Name, Val}, Record) ->
			set_record_field(record_info(fields, family), Record,
					 Name, Val)
		end,
		#family{}, DecodedTuples).

set_record_field(Fields, Record, Field, Value) ->
    Index = list_index(Field, Fields),
    erlang:setelement(Index + 1, Record, Value).

list_index(Target, List) -> list_index(Target, List, 1).

list_index(Target, [Target | _], Index) -> Index;
list_index(Target, [_ | Tail], Index) ->
    list_index(Target, Tail, Index + 1);
list_index(_, [], _) -> 0.

