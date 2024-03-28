package com.example.minigames;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private final int setSize = 3;
    private final int numberOfCharacteristics = 4;
    private Card[] totalDeck;
    private Card[] currentShuffledDeck;
    private Card[] currentlyOnBoard;
    private Card[] collectedSets;

    public void init() {
        //initialization of deck
        totalDeck = new Card[(int) Math.pow(setSize, numberOfCharacteristics)];
        int count = 0;
        for (int i = 0; i < setSize; i++) {
            for (int j = 0; j < setSize; j++) {
                for (int k = 0; k < setSize; k++) {
                    for (int l = 0; l < setSize; l++) {
                        totalDeck[count] = new Card(new int[]{i,j,k,l});
                        count++;
                    }
                }
            }
        }
        //these should maybe be arraylists?
        currentlyOnBoard = new Card[setSize * (numberOfCharacteristics + 1)];
        collectedSets = new Card[(int) Math.pow(setSize, numberOfCharacteristics)];
        //put total deck into currentShuffledDeck and shuffle it
        currentShuffledDeck = totalDeck.clone();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");
    }

    public void destroy() {
    }
}