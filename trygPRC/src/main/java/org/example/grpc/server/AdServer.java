package org.example.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.grpc.adsystem.AdSystemProto.*;
import org.example.grpc.adsystem.CandidateServiceGrpc;

import java.io.IOException;

/**
 * @author eugenia
 * @date 5/10/25
 */
public class AdServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new CandidateServiceImpl())
                .build();
        System.out.println("Starting server on port 50051...");
        server.start();
        server.awaitTermination();
    }

    static class CandidateServiceImpl extends CandidateServiceGrpc.CandidateServiceImplBase{
        @Override
        public void getCandidates(CandidateRequest request, StreamObserver<CandidateResponse> responseObserver){
            Ad ad1 = Ad.newBuilder().setAdId("ad001").setTitle("Shoes 50% Off").setBid(1.2).build();
            Ad ad2 = Ad.newBuilder().setAdId("ad002").setTitle("New iPads").setBid(1.5).build();
            
            CandidateResponse response = CandidateResponse.newBuilder().addAds(ad1).addAds(ad2).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
