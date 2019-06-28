package io.happycoding.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.google.gson.Gson;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Image;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;





@WebServlet("/get-images")
public class ImagesGetServlet extends HttpServlet {
    private Datastore datastore;

    @Override
    public void init() {

        datastore = new Datastore();

    }
    public String uploadUrl = "";
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        UserService userService = UserServiceFactory.getUserService();

        String user = userService.getCurrentUser().getEmail();
        response.setContentType("application/json");
        List<Image> images = datastore.getImages(user);
        Gson gson = new Gson();
        String json = gson.toJson(images);
        response.getWriter().println(json);


    }

}