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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Redirects the user to the Google login page or their page if they're already logged in.
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost (HttpServletRequest req,
                         HttpServletResponse resp)
          throws ServletException, IOException {

    resp.setContentType("text/html");

    try {
      String idToken = req.getParameter("id_token");
      GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
      String name = (String) payLoad.get("name");
      String email = (String) payLoad.getEmail();
      System.out.println("User name: " + name);
      System.out.println("User email: " + email);

      HttpSession session = req.getSession(true);
      session.setAttribute("userName", name);


      resp.sendRedirect("/user-page.html?user=" + email);


    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
//public class LoginServlet extends HttpServlet {
//  @Override
//  protected void doPost (HttpServletRequest req,
//                         HttpServletResponse resp)
//          throws ServletException, IOException {
//
//    resp.setContentType("text/html");
//
//    try {
//      String idToken = req.getParameter("id_token");
//      GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
//      String name = (String) payLoad.get("name");
//      String email = payLoad.getEmail();
//      System.out.println("User name: " + name);
//      System.out.println("User email: " + email);
//
//      HttpSession session = req.getSession(true);
//      session.setAttribute("userName", name);
//      req.getServletContext()
//              .getRequestDispatcher("/index.html").forward(req, resp);
//
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//}
/*
public class LoginServlet extends HttpServlet {

  */
/*@Override
  protected void doGet (HttpServletRequest request,
                         HttpServletResponse response)
          throws ServletException, IOException {

    response.setContentType("text/html");

    try {
      String idToken = request.getParameter("id_token");
      GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
      String name = (String) payLoad.get("name");
      String email = payLoad.getEmail();
      System.out.println("User name: " + name);
      System.out.println("User email: " + email);

      HttpSession session = request.getSession(true);
      session.setAttribute("userName", name);

      if (email!=null) {
        //String user = userService.getCurrentUser().getEmail();
        response.sendRedirect("/user-page.html?user=" + email);
        return;
      }
//      request.getServletContext()
//              .getRequestDispatcher("/welcome-page.jsp").forward(request, response);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }*//*


  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
          throws ServletException, IOException {

    response.setContentType("text/html");

    try {
      String idToken = request.getParameter("id_token");
      GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
      String name = (String) payLoad.get("name");
      String email = payLoad.getEmail();
      System.out.println("User name: " + name);
      System.out.println("User email: " + email);

      HttpSession session = request.getSession(true);
      session.setAttribute("userName", name);
      request.getServletContext()
              .getRequestDispatcher("/index.html").forward(request, response);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  */
/*@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();

    // If the user is already logged in, redirect to their page
    if (userService.isUserLoggedIn()) {
      String user = userService.getCurrentUser().getEmail();
      response.sendRedirect("/user-page.html?user=" + user);
      return;
    }

    // Redirect to Google login page. That page will then redirect back to /login,
    // which will be handled by the above if statement.
    String googleLoginUrl = userService.createLoginURL("/login");
    response.sendRedirect(googleLoginUrl);
  }*//*

}
*/
