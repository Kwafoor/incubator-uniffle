package org.apache.uniffle.impl.grpc;

import io.grpc.ManagedChannel;
import org.apache.uniffle.api.CoordinatorAdminClient;
import org.apache.uniffle.client.impl.grpc.GrpcClient;
import org.apache.uniffle.proto.CoordinatorAdminServerGrpc;
import org.apache.uniffle.proto.RssProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoordinatorAdminGrpcClient extends GrpcClient implements CoordinatorAdminClient {
    private static final Logger LOG = LoggerFactory.getLogger(CoordinatorAdminGrpcClient.class);
    private CoordinatorAdminServerGrpc.CoordinatorAdminServerBlockingStub blockingStub;

    public CoordinatorAdminGrpcClient(String host, int port) {
        this(host, port, 3);
    }

    public CoordinatorAdminGrpcClient(String host, int port, int maxRetryAttempts) {
        this(host, port, maxRetryAttempts, true);
    }

    public CoordinatorAdminGrpcClient(String host, int port, int maxRetryAttempts, boolean usePlaintext) {
        super(host, port, maxRetryAttempts, usePlaintext);
        blockingStub = CoordinatorAdminServerGrpc.newBlockingStub(channel);
    }

    @Override
    public RssProtos.RefreshAccessCheckerResponse refreshAccessChecker(RssProtos.RefreshAccessCheckerRequest request) {
        return blockingStub.refreshAccessChecker(request);
    }

    public int refreshAccessChecker(String checker) {
        System.out.println("refresh " + checker);
        RssProtos.RefreshAccessCheckerRequest rpcRequest = RssProtos.RefreshAccessCheckerRequest
                .newBuilder()
                .setAccessCheckerClass(checker)
                .build();
        RssProtos.RefreshAccessCheckerResponse response = refreshAccessChecker(rpcRequest);
        RssProtos.RefreshAccessCheckerResponse.AdminCliStatus statusCode = response.getStatus();
        switch (statusCode) {
            case SUCCESS:
                LOG.info("Refresh access checker {} succed.", checker);
                break;
            case FAILED:
                LOG.error("Refresh access checker {} failed. Exception:{}", checker, response.getMsg());
                break;
        }
        return statusCode.getNumber();
    }
}
