package com.mahjong.MahJavaServer;


import MahJavaLib.GreeterGrpc;
import MahJavaLib.HelloReply;
import MahJavaLib.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

class Greeter extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        System.out.println(request.toString());
        HelloReply.Builder builder = HelloReply.newBuilder();
        builder.setGreeting("Hello " + request.getName());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}

public class MahJavaServerApplication {
    public static void main(String[] args) throws InterruptedException, IOException {
        int port = 6900;
        Server server = ServerBuilder.forPort(port).addService(new Greeter()).build();
        server.start();
        server.awaitTermination();
    }
}