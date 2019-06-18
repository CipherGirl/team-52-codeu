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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
//sentiment imports
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;




import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.AnalyzeEntitySentimentRequest;
import com.google.cloud.language.v1.AnalyzeEntitySentimentResponse;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.AnalyzeSyntaxRequest;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.ClassificationCategory;
import com.google.cloud.language.v1.ClassifyTextRequest;
import com.google.cloud.language.v1.ClassifyTextResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.EntityMention;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.Token;

import java.util.List;
import java.util.Map;


/** Handles fetching and saving {@link Message} instances. */


@WebServlet("/messages")


public class MessageServlet extends HttpServlet {

 private Datastore datastore;

 @Override


 public void init() {


   datastore = new Datastore();


 }


 /**
  * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
  * an empty array if the user is not provided.
  */


 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

   response.setContentType("application/json");

   String user = request.getParameter("user");

   if (user == null || user.equals("")) {


     // Request is invalid, return empty array


     response.getWriter().println("[]");


     return;


   }

   List<Message> messages = datastore.getMessages(user);


   Gson gson = new Gson();
   String json = gson.toJson(messages);


   response.getWriter().println(json);
 }

 /** Stores a new {@link Message}. */


 @Override


 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
   UserService userService = UserServiceFactory.getUserService();
   if (!userService.isUserLoggedIn()) {
     response.sendRedirect("/index.html");
    return;
   }

   String user = userService.getCurrentUser().getEmail();


   String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());

   //sentiment detection code
   Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
   LanguageServiceClient languageService = LanguageServiceClient.create();
   Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
   double score = sentiment.getScore();
   languageService.close();

   //printing score
   System.out.println("Score: " + sentiment.getScore());

   //sending message code
   Message message = new Message(user, text, score);
   datastore.storeMessage(message);

   response.sendRedirect("/user-page.html?user=" + user);
 }
}
