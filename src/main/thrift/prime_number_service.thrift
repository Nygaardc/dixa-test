namespace * com.nygaardc.dixa.thrift

exception BadInput {
    1: string error_msg;
}

service PrimeNumberService {
    list<i32> get(1: i32 n) throws (1: BadInput badInput);
}
