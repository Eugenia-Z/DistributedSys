package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author eugenia
 * @date 3/11/25
 */
/*
 * public class GrpcClient {
 * // Create a channel to connect to the server
 * ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
 * .usePlaintext()// 禁用加密，使用明文传输
 * .build();
 * // Create a stub, used to revoke remote services
 * GreetGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
 * 
 * // Create request
 * HelloRequest request = helloRequest.newBuilder()
 * .setName("World")
 * .build();
 * 
 * // Invoke remote procedure
 * HelloResponse response = stub.sayHello(request);
 * 
 * // Print response
 * System.out.println(response.getMessage());
 * 
 * // Close the channel
 * channel.shutdown();
 * }
 */
