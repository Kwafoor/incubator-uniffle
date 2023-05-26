package org.apache.uniffle.coordinator;

import io.grpc.stub.StreamObserver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.uniffle.coordinator.access.checker.AccessChecker;
import org.apache.uniffle.proto.CoordinatorAdminServerGrpc;
import org.apache.uniffle.proto.RssProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CoordinatorAdminGrpcService extends CoordinatorAdminServerGrpc.CoordinatorAdminServerImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(CoordinatorAdminGrpcService.class);

    private final CoordinatorServer coordinatorServer;

    public CoordinatorAdminGrpcService(CoordinatorServer coordinatorServer) {
        this.coordinatorServer = coordinatorServer;
    }

    @Override
    public void refreshAccessChecker(RssProtos.RefreshAccessCheckerRequest request, StreamObserver<RssProtos.RefreshAccessCheckerResponse> responseObserver) {
        List<String> checkers = coordinatorServer.getCoordinatorConf().get(CoordinatorConf.COORDINATOR_ACCESS_CHECKERS);
        String accessClass = request.getAccessCheckerClass();
        if (CollectionUtils.isEmpty(checkers)) {
            LOG.warn("Access checkers is empty, will not update any checkers.");
            return;
        }
        try {
            Class<?> aClass = Class.forName(accessClass);
            Optional<AccessChecker> checker = coordinatorServer.getAccessManager().getAccessCheckers().stream()
                    .filter(aClass::isInstance)
                    .findAny();
            if (!checker.isPresent()) {
                LOG.warn("Access checkers {} is none exist, will not update any checkers.", accessClass);
                return;
            }
            checker.get().refreshAccessChecker();
        } catch (ClassNotFoundException e) {
            LOG.warn("Access checker class {} is not found.", accessClass, e);
        }
        final RssProtos.RefreshAccessCheckerResponse response = RssProtos.RefreshAccessCheckerResponse
                .newBuilder()
                .setStatus(RssProtos.RefreshAccessCheckerResponse.AdminCliStatus.SUCCESS)
                .setMsg("")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
