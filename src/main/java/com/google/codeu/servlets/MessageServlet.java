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

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/about")
public class AboutMeServlet extends HttpServlet {

  private Datastore datastore;

 @Override
 public void init() {
  datastore = new Datastore();
 }
 
 /**
  * Responds with the "about me" section for a particular user.
  */
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  response.setContentType("text/html");
  
  String user = request.getParameter("user");
  
  if(user == null || user.equals("")) {
   // Request is invalid, return empty response
   return;
  }
  
  String aboutMe = "This is " + user + "'s about me.";
  
  response.getOutputStream().println(aboutMe);
 }
 
 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  UserService userService = UserServiceFactory.getUserService();  
  if (!userService.isUserLoggedIn()) {
   response.sendRedirect("/index.html");
   return;
  }
  
  String userEmail = userService.getCurrentUser().getEmail();

  System.out.println("Saving about me for " + userEmail);
  // TODO: save the data
  
  response.sendRedirect("/user-page.html?user=" + userEmail);
 }
}
