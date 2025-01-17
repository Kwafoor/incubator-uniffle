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

package org.apache.uniffle.common.metrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestUtils {

  private TestUtils() {
  }

  public static String httpGet(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    StringBuilder content = new StringBuilder();
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));) {
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
    }
    return content.toString();
  }

  public static String httpPost(String urlString, String postData) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setDoOutput(true);
    con.setRequestMethod("POST");
    StringBuilder content = new StringBuilder();
    try (OutputStream outputStream = con.getOutputStream();) {
      outputStream.write(postData.getBytes());
      try (BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));) {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
      }
    }

    return content.toString();
  }
}
