package com.mahjong.MahJavaClient;

import MahJavaLib.GreeterGrpc;
import MahJavaLib.HelloReply;
import MahJavaLib.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MahJavaClientApplication {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 6900;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);
        HelloRequest req = HelloRequest.newBuilder().setName("Client").build();
        HelloReply reply = blockingStub.sayHello(req);
        System.out.println(reply.getGreeting());
    }
}
