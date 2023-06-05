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

package org.apache.uniffle.api;

import org.apache.uniffle.client.RestClient;
import org.apache.uniffle.client.UniffleRestClient;

import java.util.HashMap;
import java.util.Map;

public class AdminRestApi {
  private UniffleRestClient client;

  private static final String API_BASE_PATH = "admin";

  private AdminRestApi() {}

  public AdminRestApi(UniffleRestClient client) {
    this.client = client;
  }

  public String refreshAccessChecker(String checker) {
    String path = String.format("/api/server/%s/%s", API_BASE_PATH, "refresh/accessChecker");
    Map<String, Object> params = new HashMap<>();
    params.put("class", checker);
    return this.getClient().get(path, params, null);
  }

  private RestClient getClient() {
    return this.client.getHttpClient();
  }
}
