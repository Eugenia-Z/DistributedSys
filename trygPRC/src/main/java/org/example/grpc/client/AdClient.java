package org.example.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.adsystem.AdSystemProto.*;
import org.example.grpc.adsystem.CandidateServiceGrpc;

/**
 * @author eugenia
 * @date 5/10/25
 */
public class AdClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        CandidateServiceGrpc.CandidateServiceBlockingStub blockingStub = CandidateServiceGrpc.newBlockingStub(channel);

        CandidateRequest request = CandidateRequest.newBuilder().setUserId("user123").setAdSlotId("42").build();
        CandidateResponse response = blockingStub.getCandidates(request);
        response.getAdsList().forEach(ad ->
                System.out.println("Got ad: " + ad.getTitle() + ", bid: " + ad.getBid()));
        channel.shutdown();
    }
}

/*
Steps:
1. New Maven Project
2. Update pom.xml (added gRPC & protobuf plugins)
3. Create ptoto file (src/main/proto/adsystem.proto)
4. compile into java file ("mvn clean compile")
5. implement server (src/main/java/org/example/grpc/server/AdServer.java)
    - create a server, add to port, register the service, and start the server
    - implement CandidateServiceImplBase.getCandidates (the method defined in proto file)
    - construct a response
    - responseObserver.onNext(response)
    - responseObserver.onCompleted();;
6. invoke the method call from the Client(src/main/java/org/example/grpc/client/AdClient.java)
    - create a ManagedChannel, add the port
    - Create a stub, with the channel as the parameter
    - construct a request, set the required parameters defined in proto
    - invoke getCandidates() methods on the stub created, passing in request
    - process the result: response.getAdsList().forEach(ad -> xxx);
        - getAdsList() 是 由 Protobuf 的 Java 代码生成器 自动生成的 getter 方法；
        - 这是 Protobuf 的常规行为，每个字段都会自动生成：
    - shutdown the channel.
7. Run the project
    - run server:mvn compile exec:java -Dexec.mainClass="org.example.grpc.server.AdServer"
    - run client:mvn compile exec:java -Dexec.mainClass="org.example.grpc.client.AdClient"
8. check the hard-coded response in the console


CandidateRequest 携带了 userId 和 adSlotId，但在服务端的实现里，并没有对这两个字段做任何逻辑处理，只是“接受了”请求，
然后返回了一个硬编码的 CandidateResponse，这就是一个“骨架式”的 demo —— 更多是为了展示 gRPC 的通信流程，而非实现业务逻辑。
 */