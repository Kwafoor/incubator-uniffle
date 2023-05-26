package org.apache.uniffle.api;

import org.apache.uniffle.proto.RssProtos;

public interface CoordinatorAdminClient {

    RssProtos.RefreshAccessCheckerResponse refreshAccessChecker(RssProtos.RefreshAccessCheckerRequest request);
}
