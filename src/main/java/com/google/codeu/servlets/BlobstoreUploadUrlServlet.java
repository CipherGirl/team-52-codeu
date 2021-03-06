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
/**
 * When the fetch() function requests the /blobstore-upload-url URL, the content of the
 * response is the URL that allows a user to upload a file to Blobstore.
 * If this sounds confusing, try running a devserver and navigating to /blobstore-upload-url
 * to see the Blobstore URL.
 */
@WebServlet("/blobstore-upload-url")
public class BlobstoreUploadUrlServlet extends HttpServlet {
    private Datastore datastore;

    @Override
    public void init() {

        datastore = new Datastore();

    }
    public String uploadUrl = "";
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        uploadUrl = blobstoreService.createUploadUrl("/my-form-handler") ;

        response.setContentType("text/html");
        response.getOutputStream().println(uploadUrl);
        //System.out.println("Upload url: "+ uploadUrl);


    }

}