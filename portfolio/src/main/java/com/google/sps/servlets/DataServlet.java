// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.data.Comment;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comment")
public class DataServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<Comment> comments = new ArrayList<>();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    
    String languageCode = request.getParameter("languageCode");
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        String comment = (String) entity.getProperty("comment");
        String firstName = (String) entity.getProperty("firstName");
        String lastName = (String) entity.getProperty("lastName");
        long timestamp = (long) entity.getProperty("timestamp");

        // Do the translation.
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(comment, Translate.TranslateOption.targetLanguage(languageCode));
        String translatedText = translation.getTranslatedText();

        Comment NewComment = new Comment(id, translatedText, firstName, lastName, timestamp);
        comments.add(NewComment);
    }
    response.setContentType("application/json; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    response.getWriter().println(json);    
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {      
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    //get params
    String text = getParameter(request, "comment", "");  
    String first = getParameter(request, "firstName", "");   
    String last = getParameter(request, "lastName", ""); 
    String languageCode = request.getParameter("languageCode");

    //setting data in datastore
    Entity taskEntity = new Entity("Comment");
    taskEntity.setProperty("comment", text);
    taskEntity.setProperty("firstName", first);
    taskEntity.setProperty("lastName", last);
    taskEntity.setProperty("timestamp", System.currentTimeMillis());    
    datastore.put(taskEntity); 
        
    response.sendRedirect("/index.html#contact"); 
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}

