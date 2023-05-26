package org.apache.uniffle.test;

import org.apache.uniffle.coordinator.CoordinatorConf;
import org.apache.uniffle.proto.RssProtos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatorAdminServiceTest extends CoordinatorAdminTestBase {

    @Test
    public void test() throws Exception {
        CoordinatorConf coordinatorConf = getCoordinatorConf();
        coordinatorConf.setString(CoordinatorConf.COORDINATOR_ASSIGNMENT_STRATEGY.key(), "BASIC");
        String accessChecker = "org.apache.uniffle.test.AccessClusterTest$MockedAccessChecker";
        coordinatorConf.setString(CoordinatorConf.COORDINATOR_ACCESS_CHECKERS.key(), accessChecker);
        createCoordinatorServer(coordinatorConf);
        startServers();
        assertEquals(coordinatorAdminClient.refreshAccessChecker(accessChecker),0);
        shutdownServers();
    }

}
