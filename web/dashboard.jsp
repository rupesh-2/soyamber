<%@ page import="java.sql.*" %>
<%@ page import="com.DB.DBconnect" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DBconnect.getConnection();

        // Query to fetch user details
        String userDetailsSql = "SELECT username, email FROM users WHERE id = ?";
        pstmt = conn.prepareStatement(userDetailsSql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            String username = rs.getString("username");
            String email = rs.getString("email");
            out.println("Welcome, " + username + "<br>");
            out.println("Your email: " + email + "<br>");
        }

        rs.close();
        pstmt.close();

        // Query to fetch user-specific data
        String userDataSql = "SELECT data FROM user_data WHERE user_id = ?";
        pstmt = conn.prepareStatement(userDataSql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            out.println("Your data: " + rs.getString("data") + "<br>");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Print the exception to the page for debugging
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
%>
