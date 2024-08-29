import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // Basic validation
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Error: Passwords do not match");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Register the user
        boolean isRegistered = registerUser(username, password, email);

        if (isRegistered) {
            request.setAttribute("message", "Registration successful!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Error: Registration failed");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private boolean registerUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        DatabaseMetaData DatabaseUtil = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Use parameterized queries to prevent SQL injection
            stmt.setString(1, username);
            stmt.setString(2, password); // Note: You should hash the password in a real application
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
