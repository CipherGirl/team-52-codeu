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


import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Styled Text Part1 Imports
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@WebServlet("/group")
public class chatServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {

        datastore = new Datastore();

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        List<Message> gmessages = datastore.getAllGroupMessages();
        Gson gson = new Gson();
        String json = gson.toJson(gmessages);

        response.getOutputStream().println("HALOOOOOOOO");

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
        response.sendRedirect("/index.html");
        return;
        }

        String user = userService.getCurrentUser().getEmail();
        String text = Jsoup.clean(request.getParameter("grouptext"), Whitelist.none());
        String groupId = Jsoup.clean(request.getParameter("groupid"), Whitelist.none());





        //Styled Text Part 1 (MOW)
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        text = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"


        //sending message code //updated message code, including images(part1 ) using regex expression
        String regex = "(https?://\\S+\\.(png|jpg|jpeg|gif|png|svg|mp4))";
        //String regex = "(http)?s?:?(\/\/[^"']*\.(?:png|jpg|jpeg|gif|png|svg|mp4))";
        String replacement = "<img src=\"$1\" />";
        String textWithImagesReplaced = text.replaceAll(regex, replacement);


        //change code for group
        Message gmessage = new Message(user, textWithImagesReplaced ,groupId);


        datastore.storeMessage(gmessage);

        response.sendRedirect("/group.html");
        }

}
