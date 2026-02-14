package config;

import java.sql.*;

public class config {
    
    public Connection connectDB() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:contractor_management.db";
            conn = DriverManager.getConnection(url);
            
            if (conn != null) {
                System.out.println("Connection Successful");
            }
        } catch (Exception e) {
            System.err.println("Connection Failed: " + e.getMessage());
        }
        return conn;
    }
    
    public String validateLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = connectDB();
            String sql = "SELECT role, status FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                String status = rs.getString("status");
                
                // If role is NULL, return "NOROLE"
                if(role == null || role.trim().isEmpty()) {
                    return "NOROLE";
                }
                
                // If status is pending, return "PENDING"
                if(status != null && status.equals("pending")) {
                    return "PENDING";
                }
                
                return role;
            }
            
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
