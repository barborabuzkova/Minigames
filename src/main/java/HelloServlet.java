import java.io.*;

import com.example.minigames.Card;
import com.example.minigames.Game;
import com.example.minigames.Position;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// @WebServlet(name = "HelloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private Game game;

    @Override
    public void init() {
        System.out.println("Init called");
        //initialization of position
        this.game = new Game();
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