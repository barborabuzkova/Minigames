import java.io.*;
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
        System.out.println("Cards on board" + game.getPosition().getCurrentlyOnBoard().toString());
        System.out.println("number of sets: " + Algorithm.findAllSets(game.getPosition().getCurrentlyOnBoard()).size());

        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        JSONObject re = new JSONObject();

        for (int i = 0; i < game.getPosition().getCurrentlyOnBoard().size(); i++) {
            re.put(Integer.toString(i), game.getPosition().getCurrentlyOnBoard().get(i));
        }
        System.out.println(re);

        out.print(re.toString());

    }


}