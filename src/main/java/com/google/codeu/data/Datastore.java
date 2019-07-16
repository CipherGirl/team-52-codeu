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

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.*;
import com.google.appengine.api.datastore.FetchOptions;
/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());
    messageEntity.setProperty("sentimentscore", message.getSscore());
    messageEntity.setProperty("groupId", message.getGroupId());
    System.out.println("sentiment score is :  "+ message.getSscore());
    datastore.put(messageEntity);
    System.out.println("Message entity is: "+ messageEntity);
  }

    public void storeGroupMessage(Group gmessage) {
        Entity gmessageEntity = new Entity("Group", gmessage.getId().toString());
        gmessageEntity.setProperty("user", gmessage.getUser());
        gmessageEntity.setProperty("text", gmessage.getText());
        gmessageEntity.setProperty("timestamp", gmessage.getTimestamp());

        gmessageEntity.setProperty("groupId", gmessage.getGroupId());

        datastore.put(gmessageEntity);
        System.out.println("Group entity is: "+ gmessageEntity);
    }


  public void storeImage(Image image){
        Entity imageEntity = new Entity("Image", image.getImageId().toString());
        imageEntity.setProperty("user", image.getImageUser());
        imageEntity.setProperty("url", image.getImageURL() );



        datastore.put(imageEntity);
        System.out.println("image entity is: "+ imageEntity);
    }

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */

  public List<Message> getMessagesforquery(Query query) {

      List<Message> messages = new ArrayList<>();
      PreparedQuery results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {
          try {
              String idString = entity.getKey().getName();
              UUID id = UUID.fromString(idString);
              String user = (String) entity.getProperty("user");
              String text = (String) entity.getProperty("text");
              long timestamp = (long) entity.getProperty("timestamp");
              double sscore = (double) entity.getProperty("sentimentscore");

              Message message = new Message(id, user, text, sscore, timestamp);
              //System.out.println("message is: " + message);
              messages.add(message);
          } catch (Exception e) {
              System.err.println("Error reading message.");
              System.err.println(entity.toString());
              e.printStackTrace();
          }
      }
      return messages;
  }

  public List<Image> getImagesforquery(Query query) {

      List<Image> images = new ArrayList<>();
      PreparedQuery results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {
          try {
              String idString = entity.getKey().getName();
              UUID id = UUID.fromString(idString);
              String user = (String) entity.getProperty("user");
              String url = (String) entity.getProperty("url");


              Image image = new Image(id, user, url);
              //System.out.println("Image is: " + image);
              images.add(image);
          } catch (Exception e) {
              System.err.println("Error reading image.");
              System.err.println(entity.toString());
              e.printStackTrace();
          }
      }
      return images;
  }



  public List<Message> getMessages(String user) {
    Query query =
        new Query("Message")
            .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
            .addSort("timestamp", SortDirection.DESCENDING);

    return getMessagesforquery(query); //getMessagesforquery() returns List<Message>
  }

  public List<Message> getAllMessages(){
      Query query = new Query("Message")
              .addSort("timestamp", SortDirection.DESCENDING);

      return getMessagesforquery(query); //getMessagesforquery() returns List<Message>
  }

  public List<Image> getImages(String user) {
    Query query =
        new Query("Image")
            .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user));


    return getImagesforquery(query); //getMessagesforquery() returns List<Message>
  }

  public List<Group> getGroupMessagesforquery(Query query){
      List<Group> gmessages = new ArrayList<>();
      PreparedQuery results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {
          try {
              String idString = entity.getKey().getName();
              UUID id = UUID.fromString(idString);
              String user = (String) entity.getProperty("user");
              String text = (String) entity.getProperty("text");
              long timestamp = (long) entity.getProperty("timestamp");

              String groupId = (String) entity.getProperty("groupId");
              Group gmessage = new Group(id, user, text, groupId, timestamp);
              //System.out.println("message is: " + message);
              gmessages.add(gmessage);
          } catch (Exception e) {
              System.err.println("Error reading message.");
              System.err.println(entity.toString());
              e.printStackTrace();
          }
      }

      return gmessages;
  }

  public List<Group> getGroupMessages(String user) {
        Query query =
                new Query("Group")
                        .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
                        .addSort("timestamp", SortDirection.DESCENDING);

        return getGroupMessagesforquery(query); //getMessagesforquery() returns List<Message>
  }

  public List<Group> getAllGroupMessages(){
        Query query = new Query("Group")
                .addSort("timestamp", SortDirection.DESCENDING);

        return getGroupMessagesforquery(query); //getMessagesforquery() returns List<Message>
  }





  public Set<String> getUsers(){
        Set<String> users = new HashSet<>();
        Query query = new Query("Message");
        PreparedQuery results = datastore.prepare(query);
        for(Entity entity : results.asIterable()) {
            users.add((String) entity.getProperty("user"));
        }
        return users;
    }
 
  /** Stores the User in Datastore. */
 public void storeUser(User user) {
  Entity userEntity = new Entity("User", user.getEmail());
  userEntity.setProperty("email", user.getEmail());
  userEntity.setProperty("aboutMe", user.getAboutMe());
  datastore.put(userEntity);
 }

 /**
  * Returns the User owned by the email address, or
  * null if no matching User was found.
  */
 public User getUser(String email) {
 
  Query query = new Query("User")
    .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
  PreparedQuery results = datastore.prepare(query);
  Entity userEntity = results.asSingleEntity();
  if(userEntity == null) {
   return null;
  }
  
  String aboutMe = (String) userEntity.getProperty("aboutMe");
  User user = new User(email, aboutMe);
  
  return user;
 }
  /** Returns the total number of messages for all users. */
public int getTotalMessageCount(){
  Query query = new Query("Message");
  PreparedQuery results = datastore.prepare(query);
  return results.countEntities(FetchOptions.Builder.withLimit(1000));
}


  
}


