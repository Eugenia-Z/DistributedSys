package org.example.hello;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.hello.HelloProto;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        org.example.grpc.hello.GreeterGrpc.GreeterBlockingStub blockingStub = org.example.grpc.hello.GreeterGrpc.newBlockingStub(channel);
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder().setName("World").build();
        HelloProto.HelloResponse response = blockingStub.sayHello(request);

        System.out.println(response);
        channel.shutdownNow();

    }
}