import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.minigames.Algorithm;
import com.example.minigames.Card;
import com.example.minigames.Game;
import com.example.minigames.Position;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

// @WebServlet(name = "HelloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private Game game;

    @Override
    public void init() {
        System.out.println("init called");
        this.game = new Game();
        System.out.println("Cards on board" + game.getPosition().getCurrentlyOnBoard().toString());
        System.out.println("number of sets: " + Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size());
        while (Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).isEmpty()) {
            game.addCardsNoSetFound();
        }
        System.out.println("init finished");
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
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        JSONObject re = new JSONObject();
        JSONArray cards = new JSONArray();

        if(request.getParameter("loadPage") != null && request.getParameter("loadPage").equals("true")) {
            System.out.println("loadPage: " + request.getParameter("loadPage"));

            re.put("SET_SIZE", Card.SET_SIZE);
            re.put("NUMBER_OF_CHARACTERISTICS", Card.NUMBER_OF_CHARACTERISTICS);
            re.put("boardSize", game.getPosition().getCurrentlyOnBoard().size());

            for (int i = 0; i < game.getPosition().getCurrentlyOnBoard().size(); i++) {
                cards.put(i, game.getPosition().getCurrentlyOnBoard().get(i));
            }
            // re.put("cards", cards);
        } else if (request.getParameter("setFound") != null && request.getParameter("setFound").equals("true")) {
            System.out.println(request.getParameter("cardsInSet")); // testing
            System.out.println("cards on board " + game.getPosition().getCurrentlyOnBoard());
            Set<Card> currentCards = new HashSet<>();
            int numberOfCardsAdded = 0;
            for (String s: request.getParameter("cardsInSet").split("\",\"")) {
                if (s.contains("[\"")) {
                    s = s.substring(2);
                } else if (s.contains("\"]")) {
                    s = s.substring(0,s.length() - 2);
                }
                for (Card c: game.getPosition().getCurrentlyOnBoard()) {
                    if (c.toString().equals(s)) {
                        currentCards.add(c);
                    }
                }
            }
            System.out.println(currentCards); // testing
            ArrayList<Card> cardsToAdd = game.getPosition().setCollected(currentCards);
            if (!cardsToAdd.isEmpty()) {
                re.put("collectedSet", true);
                for (int i = 0; i < cardsToAdd.size(); i++) {
                    cards.put(i, cardsToAdd.get(i));
                }
                numberOfCardsAdded += Card.SET_SIZE;
            } else {
                re.put("collectedSet", false);
            }
            System.out.println("added, number of sets is " + Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size());
            while (Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).isEmpty()) {
                System.out.println("adding cards, no set found");
                cardsToAdd = game.addCardsNoSetFound();
                numberOfCardsAdded += Card.SET_SIZE;
                for (int i = 0; i < cardsToAdd.size(); i++) {
                    cards.put(i, cardsToAdd.get(i));
                }
            }
            re.put("numberOfCardsAdded", numberOfCardsAdded);

        }

        re.put("cards", cards);
        re.put("numberOfSets", Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size());
        System.out.println(re);
        out.print(re.toString());
        out.flush();
        out.close();

    }


}