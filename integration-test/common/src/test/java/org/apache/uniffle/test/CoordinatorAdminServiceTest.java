package org.apache.uniffle.test;

import org.apache.uniffle.common.config.RssBaseConf;
import org.apache.uniffle.common.metrics.TestUtils;
import org.apache.uniffle.coordinator.CoordinatorConf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatorAdminServiceTest extends CoordinatorAdminTestBase {

    private static final String URL_PREFIX = "http://127.0.0.1:12345/api/";
    private static final String ADMIN_URL = URL_PREFIX + "server/admin/refresh/accessChecker";

    @Test
    public void test() throws Exception {
        CoordinatorConf coordinatorConf = getCoordinatorConf();
        coordinatorConf.setString(CoordinatorConf.COORDINATOR_ASSIGNMENT_STRATEGY.key(), "BASIC");
        String accessChecker = "org.apache.uniffle.test.AccessClusterTest$MockedAccessChecker";
        coordinatorConf.setString(CoordinatorConf.COORDINATOR_ACCESS_CHECKERS.key(), accessChecker);
        createCoordinatorServer(coordinatorConf);
        startServers();
//CoordinatorConf.JETTY_HTTP_PORT
        while (true){
            Thread.sleep(100000);
        }
//        assertEquals(adminRestApi.refreshAccessChecker(accessChecker),"");
//        shutdownServers();
    }
}
