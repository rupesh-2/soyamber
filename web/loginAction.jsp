<%@ page import="java.sql.*" %>
<%@ page import="com.DB.DBconnect" %>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DBconnect.getConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            // User is authenticated
            session.setAttribute("userId", rs.getInt("id"));
            session.setAttribute("username", rs.getString("username"));
            response.sendRedirect("dashboard.jsp");
        } else {
            // Authentication failed
            out.println("Invalid username or password");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
%>
