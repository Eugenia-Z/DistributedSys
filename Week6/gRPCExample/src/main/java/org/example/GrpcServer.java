/* package org.example;
import io.grpc.stub.StreamObserver;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObservers;


/**
 * @author eugenia
 * @date 3/11/25
 */
public class GrpcServer {
    public static void main(String[] args) throws Exception {
        // Create and start gRPC service
        Server server = ServerBuilder.forPort(8080)
                .addService(new GreeterImpl())
                .build();

        System.out.println("Server is starting...");
        server.start();
        System.out.println("Server started on port 8080");

        server.awaitTermination();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase{
        @Override
        public void sayHello(HelloRequest request, StreamObservers<HelloResponse> responseObserver){
            String message = "Hello, " + request.getName();

            // Create response and send
            HelloResponse response = HelloResponse.newBuilder()
                    .setMessage(message)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}*/