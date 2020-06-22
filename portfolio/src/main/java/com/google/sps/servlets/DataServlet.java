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
  private ArrayList<Comment> comments = new ArrayList<>();
  private ArrayList<String> mycomments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    Gson gson = new Gson();
    String json = gson.toJson(mycomments);
    response.getWriter().println(json);    
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        String text = getParameter(request, "text", "");  
        String first = getParameter(request, "first-name", "");   
        String last = getParameter(request, "last-name", "");  
      
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String comment = (String) entity.getProperty("text");
            long timestamp = (long) entity.getProperty("timestamp");
            String firstname = (String) entity.getProperty("first-name");
            String lastname = (String) entity.getProperty("last-name");

            Comment NewComment = new Comment(id, comment, firstname, lastname, timestamp);
            System.out.print("New comments: ");
            System.out.print(NewComment);
            comments.add(NewComment);
            System.out.print("comments: ");
            System.out.print(comments);
        }

    Entity taskEntity = new Entity("Comments");
    taskEntity.setProperty("text", text);
    mycomments.add(text);
    taskEntity.setProperty("first-name", first);
    taskEntity.setProperty("last-name", last);
    
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

