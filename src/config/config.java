package config;

import java.sql.*;

public class config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:contractor_management.db");
            System.out.println("Connection Successful");
            createAllTables(con);
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
            e.printStackTrace();
        }
        return con;
    }
    
    private static void createAllTables(Connection conn) {
        try {
        Statement stmt = conn.createStatement();
        
        // Users table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "email TEXT, " +
            "password TEXT NOT NULL, " +
            "role TEXT, " +
            "status TEXT DEFAULT 'active')"
        );
        System.out.println("✅ Users table ready");
        
        // ✅ ADD MISSING COLUMNS (kung dili pa naa)
        try {
            stmt.execute("ALTER TABLE users ADD COLUMN bio TEXT DEFAULT ''");
            System.out.println("✅ Bio column added");
        } catch (Exception e) {
            System.out.println("ℹ️ Bio column already exists");
        }
        
        try {
            stmt.execute("ALTER TABLE users ADD COLUMN profile_pic TEXT DEFAULT ''");
            System.out.println("✅ Profile pic column added");
        } catch (Exception e) {
            System.out.println("ℹ️ Profile pic column already exists");
        }
        
        try {
            stmt.execute("ALTER TABLE users ADD COLUMN email TEXT DEFAULT ''");
            System.out.println("✅ Email column added");
        } catch (Exception e) {
            System.out.println("ℹ️ Email column already exists");
        }
        
        // ✅ SET EMPTY STRING FOR NULL VALUES
        stmt.execute("UPDATE users SET bio = '' WHERE bio IS NULL");
        stmt.execute("UPDATE users SET profile_pic = '' WHERE profile_pic IS NULL");
        stmt.execute("UPDATE users SET email = '' WHERE email IS NULL");
        System.out.println("✅ Null values fixed");
        
        // Projects table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS projects (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "project_name TEXT NOT NULL, " +
            "description TEXT, " +
            "budget REAL NOT NULL, " +
            "client_id INTEGER NOT NULL, " +
            "contractor_id INTEGER, " +
            "status TEXT DEFAULT 'Open', " +
            "created_date TEXT DEFAULT (datetime('now')), " +
            "FOREIGN KEY (client_id) REFERENCES users(id), " +
            "FOREIGN KEY (contractor_id) REFERENCES users(id))"
        );
        System.out.println("✅ Projects table ready");
        
        // Payments table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS payments (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "project_id INTEGER NOT NULL, " +
            "amount REAL NOT NULL, " +
            "payment_date TEXT DEFAULT (date('now')), " +
            "status TEXT DEFAULT 'Paid', " +
            "FOREIGN KEY (project_id) REFERENCES projects(id))"
        );
        System.out.println("✅ Payments table ready");
        
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
   public String validateLogin(String usernameOrEmail, String password) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(
             "SELECT role, status FROM users WHERE (username = ? OR email = ?) AND password = ?")) {
        
        pstmt.setString(1, usernameOrEmail);
        pstmt.setString(2, usernameOrEmail);  // ✅ CHECK BOTH USERNAME AND EMAIL
        pstmt.setString(3, password);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            String role = rs.getString("role");
            String status = rs.getString("status");
            
            if (role == null || role.trim().isEmpty()) {
                return "NOROLE";
            }
            
            if ("pending".equals(status)) {
                return "PENDING";
            }
            
            return role;
        }
        
        return null;
        
    } catch (SQLException e) {
        System.err.println("Login error: " + e.getMessage());
        return null;
    }

    }
    
    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }
            pstmt.executeUpdate();
            System.out.println("✅ Record added");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

   public String getUsernameFromEmailOrUsername(String usernameOrEmail) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(
             "SELECT username FROM users WHERE username = ? OR email = ?")) {
        
        pstmt.setString(1, usernameOrEmail);
        pstmt.setString(2, usernameOrEmail);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return rs.getString("username");
        }
        
        return usernameOrEmail;  // fallback
        
    } catch (SQLException e) {
        e.printStackTrace();
        return usernameOrEmail;
    }
}
}