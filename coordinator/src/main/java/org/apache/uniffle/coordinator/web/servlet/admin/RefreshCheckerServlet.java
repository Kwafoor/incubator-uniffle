/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.uniffle.coordinator.web.servlet.admin;

import org.apache.commons.collections.CollectionUtils;
import org.apache.uniffle.coordinator.CoordinatorConf;
import org.apache.uniffle.coordinator.CoordinatorServer;
import org.apache.uniffle.coordinator.ServerNode;
import org.apache.uniffle.coordinator.access.checker.AccessChecker;
import org.apache.uniffle.coordinator.web.Response;
import org.apache.uniffle.coordinator.web.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class RefreshCheckerServlet extends BaseServlet {
    private final CoordinatorServer coordinator;

    public RefreshCheckerServlet(CoordinatorServer coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    protected Response handleGet(HttpServletRequest req, HttpServletResponse resp) {
        List<String> checkers = coordinator.getCoordinatorConf().get(CoordinatorConf.COORDINATOR_ACCESS_CHECKERS);
        String accessClass = req.getParameter("class");
        if (CollectionUtils.isEmpty(checkers)) {
            return Response.fail("Access checkers is empty, will not update any checkers.");
        }
        try {
            Class<?> aClass = Class.forName(accessClass);
            Optional<AccessChecker> checker = coordinator.getAccessManager().getAccessCheckers().stream()
                    .filter(aClass::isInstance)
                    .findAny();
            if (!checker.isPresent()) {
                return Response.fail(String.format("Access checkers %s is none exist, will not update any checkers.", accessClass));
            }
            checker.get().refreshAccessChecker();
        } catch (ClassNotFoundException e) {
            return Response.fail(String.format("Access checker class %s is not found.", accessClass));
        }
        return Response.success(null);
    }
}
