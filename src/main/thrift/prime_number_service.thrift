namespace * com.nygaardc.dixa.thrift

service PrimeNumberService {
    list<i32> get(1: i32 n);
}
