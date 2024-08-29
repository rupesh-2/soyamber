import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Replace this with your actual authentication logic
        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            // Redirect to a dashboard page or similar
            response.sendRedirect("dashboard.jsp");
        } else {
            // Set an error message and forward back to the login page
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login1.jsp").forward(request, response);
        }
    }

    private boolean authenticate(String username, String password) {
        // Dummy authentication logic
        // Replace this with your actual authentication code
        return "admin".equals(username) && "password".equals(password);
    }
}
