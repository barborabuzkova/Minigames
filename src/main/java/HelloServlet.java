import java.io.*;

import com.example.minigames.Card;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// @WebServlet(name = "HelloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private Card[] totalDeck;
    private Card[] currentlyOnBoard;
    private Card[] collectedSets;

    @Override
    public void init() {
        System.out.println("Init called");
        //initialization of deck
        totalDeck = new Card[(int) Math.pow(Card.SET_SIZE, Card.NUMBER_OF_CHARACTERISTICS)];
        int count = 0;
        for (int i = 0; i < Card.SET_SIZE; i++) {
            for (int j = 0; j < Card.SET_SIZE; j++) {
                for (int k = 0; k < Card.SET_SIZE; k++) {
                    for (int l = 0; l < Card.SET_SIZE; l++) {
                        totalDeck[count] = new Card(new int[]{i,j,k,l});
                        count++;
                    }
                }
            }
        }
        //TODO FIX
        currentlyOnBoard = new Card[Card.SET_SIZE * (Card.NUMBER_OF_CHARACTERISTICS + 1)];
        collectedSets = new Card[(int) Math.pow(Card.SET_SIZE, Card.NUMBER_OF_CHARACTERISTICS)];

        //shuffle
        // Collections.shuffle


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Get was called :)");
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Post was called :)");
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");


    }


}