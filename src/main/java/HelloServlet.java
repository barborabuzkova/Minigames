import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.minigames.Algorithm;
import com.example.minigames.Card;
import com.example.minigames.Game;
import com.example.minigames.ASet;
import jakarta.servlet.http.*;
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
        System.out.println();
        System.out.println("Post was called :)");
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        JSONObject re = new JSONObject();
        JSONArray cards = new JSONArray();

        if (request.getParameter("restart") != null && request.getParameter("restart").equals("true")) {
            init();
        }

        if(request.getParameter("loadPage") != null && request.getParameter("loadPage").equals("true")) {
            System.out.println("loadPage: " + request.getParameter("loadPage"));
            loadPage(re, cards);

        } else if (request.getParameter("setFound") != null && request.getParameter("setFound").equals("true")) {
            System.out.println("Possible set found, cardsInSet " + request.getParameter("cardsInSet")); // testing
            Set<Card> currentCards = processCardsInRequest(request);
            setFound(re, currentCards, cards);

        } else if (request.getParameter("giveHint") != null && request.getParameter("giveHint").equals("true")) {
            System.out.println("Hint requested.");
            giveHint(re);

        } else if (request.getParameter("giveUp") != null && request.getParameter("giveUp").equals("true")) {
            System.out.println("Give up, answer requested.");
            giveUp(re);
        }

        re.put("cards", cards);
        re.put("numberOfSets", Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size());
        System.out.println(re);
        out.print(re.toString());
        out.flush();
        out.close();
    }

    private void loadPage(JSONObject re, JSONArray cards) {
        re.put("SET_SIZE", Card.SET_SIZE);
        re.put("NUMBER_OF_CHARACTERISTICS", Card.NUMBER_OF_CHARACTERISTICS);
        re.put("boardSize", game.getPosition().getCurrentlyOnBoard().size());

        for (int i = 0; i < game.getPosition().getCurrentlyOnBoard().size(); i++) {
            cards.put(i, game.getPosition().getCurrentlyOnBoard().get(i));
        }
    }

    private void setFound(JSONObject re, Set<Card> currentCards, JSONArray cards) {
        if (!Algorithm.checkIfSet(currentCards)) { //not a set
            re.put("collectedSet", false);
            System.out.println("Not a set, no change made.");

        } else { //it is a set, decide if adding cards
            re.put("collectedSet", true);

            if (game.getPosition().getCurrentDeck().isEmpty()) { //deck empty
                ArrayList<Card> cardsToAdd = game.getPosition().setCollected(currentCards); // backend collect sets
                System.out.println("Is a set, collected, number of sets is " + Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size() +
                        "; Cards on board is " + game.getPosition().getCurrentlyOnBoard().size() +
                        "; Cards left in deck is " + game.getPosition().getCurrentDeck().size());
                re.put("numberOfCardsAdded", 0);
                if (Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).isEmpty()) {
                    re.put("gameover", "true");
                }

            } else { //deck not empty, add cards
                ArrayList<Card> cardsToAdd = game.getPosition().setCollected(currentCards); // backend collect sets
                int numberOfCardsAdded = 0;

                for (int i = 0; i < cardsToAdd.size(); i++) { //adds cards to JSONArray
                    cards.put(i, cardsToAdd.get(i));
                    numberOfCardsAdded ++;
                }

                //adds additional cards if necessary
                while (Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).isEmpty()) {
                    System.out.println("adding cards, no set found");
                    cardsToAdd = game.addCardsNoSetFound();
                    for (int i = 0; i < Card.SET_SIZE; i++) {
                        cards.put(numberOfCardsAdded, cardsToAdd.get(i));
                        numberOfCardsAdded ++;
                    }
                }
                System.out.println("Is a set, cards added, number of sets is " + Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size() +
                        "; Cards on board is " + game.getPosition().getCurrentlyOnBoard().size() +
                        "; Cards left in deck is " + game.getPosition().getCurrentDeck().size());
                re.put("numberOfCardsAdded", numberOfCardsAdded);
            }
        }
    }

    private void giveHint(JSONObject re) {
        ArrayList<ASet> currentSets = new ArrayList<ASet>(Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()));
        System.out.println("hint: " + currentSets.get(0).getCards().toArray()[0]);
        re.put("hint", currentSets.get(0).getCards().toArray()[0]);
    }

    private void giveUp(JSONObject re) {
        ArrayList<ASet> currentSets = new ArrayList<ASet>(Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()));
        System.out.print("answer: ");
        JSONArray answer = new JSONArray();
        for (int i = 0; i < Card.SET_SIZE; i++) {
            System.out.print(currentSets.get(0).getCards().toArray()[i]);
            answer.put(i, currentSets.get(0).getCards().toArray()[i]);
        }
        System.out.println();
        re.put("answer", answer);
    }

    private Set<Card> processCardsInRequest(HttpServletRequest request) {
        Set<Card> currentCards = new HashSet<>();
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
        //System.out.println(currentCards);
        return currentCards;
    }
}