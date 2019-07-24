/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns login data as JSON, e.g. {"isLoggedIn": true, "username": "alovelace@codeustudents.com"}
 */
@WebServlet(urlPatterns = {"/login-status"})
public class LoginStatusServlet extends HttpServlet {


  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //String email = (String) request.getAttribute("userEmail");
    String email = request.getParameter("userEmail");
    JsonObject jsonObject = new JsonObject();
    //jsonObject = (JsonObject) request.getAttribute("json_login");
//    if (email!=null) {
//      jsonObject.addProperty("isLoggedIn", true);
//      jsonObject.addProperty("username", email);
//    } else {
//      jsonObject.addProperty("isLoggedIn", false);
//    }
//    //PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.getWriter().println(jsonObject.toString());
    System.out.println("In the login-Servlet");
  }
}