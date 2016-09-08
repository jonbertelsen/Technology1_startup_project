package com.faisal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;

/**
 * Created by faisaljarkass on 19/08/16.
 * mvn appengine:update
 * mvn appengine:devserver
 */
public class MyServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(MyServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logger.log(Level.INFO, "doPost start...");
        logger.log(Level.INFO, "Username: " + request.getParameter("username"));
        logger.log(Level.INFO, "Password: " + request.getParameter("password"));
        logger.log(Level.INFO, "Checkbox: " + request.getParameter("rememberMe"));

        // Her begynder Jons kode ***********************************************

        // Declare variables and objects
        String sResultMessage = "";
        String [] sLoginCredentials;
        HashMap<String, String> hmUserLookup = new HashMap<String, String>();
        String sUsername;
        String sUserPassword;
        Scanner inputfile = null;
        Boolean bValidLogin = true;

        // Read query-parameters from Url
        sUsername = request.getParameter("username");
        sUserPassword = request.getParameter("password");

        // Read user names and passwords line by line from file and store in hashtable
        try
        {
            inputfile = new Scanner(new File("WEB-INF/users.txt"));

            while (inputfile.hasNextLine())
            {
                sLoginCredentials = inputfile.nextLine().split(",");
                hmUserLookup.put(sLoginCredentials[0], sLoginCredentials[1]);
            }
        }
        finally
        {
            if (inputfile != null)
            {
                inputfile.close();
            }
        }

            // Check if password is valid
            if (sUserPassword.equals(hmUserLookup.get(sUsername)))
            {
                sResultMessage += "Login granted";
                bValidLogin = true;
            }
            else
            {
                sResultMessage += "Login not granted";
                bValidLogin = false;
            }

            // If the user is validated then login - otherwise show login form again and a message

        if (bValidLogin)
        {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            String title = sResultMessage;
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " +
                            "transitional//en\">\n";
            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<h1 align=\"center\">" + title + "</h1>\n" +
                    "<ul>\n" +
                    "  <li><b>Username</b>: "
                    + request.getParameter("username") + "\n" +
                    "  <li><b>Password</b>: "
                    + request.getParameter("password") + "\n" +
                    "</ul>\n" +
                    "</body></html>");
        }
        else
        {
            response.sendRedirect("invalidlogin.html");
        }

        logger.log(Level.INFO, "doPost ended...");
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "doGet started...");
      /* response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Using POST Method to Read Form Data";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " +
                        "transitional//en\">\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Username</b>: "
                + request.getParameter("username") + "\n" +
                "  <li><b>Password</b>: "
                + request.getParameter("password") + "\n" +
                "</ul>\n" +
                "</body></html>");
        */
        logger.log(Level.INFO, "doGet ended...");
    }
}
