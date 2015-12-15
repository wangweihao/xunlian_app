-module(test).

-compile([export_all]).

-include("test_pb.hrl").

encode() ->
	Person = #person{age=25, name="John"},
	test_pb:encode_person(Person).

decode() ->
	Data = encode(),
	test_pb:decode_person(Data).

encode_repeat() ->
	RepeatData =
	[
		#person{age=25, name="John"},
		#person{age=23, name="Lucy"},
		#person{age=2, name="Tony"}
	],
	Family = #family{person=RepeatData},
	test_pb:encode_family(Family).
	
decode_repeat() ->
	Data = encode_repeat(),
	test_pb:decode_family(Data).