package config;

public class SessionManager {
    private static SessionManager instance;
    private String currentUsername;
    private String currentRole;
    private boolean isLoggedIn;
    
    private SessionManager() {
        this.isLoggedIn = false;
    }
    
    // Singleton pattern
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    // Login - create session
    public void login(String username, String role) {
        this.currentUsername = username;
        this.currentRole = role;
        this.isLoggedIn = true;
        System.out.println("✅ Session created for: " + username + " (" + role + ")");
    }
    
    // Logout - destroy session
    public void logout() {
        System.out.println("🔓 Session destroyed for: " + currentUsername);
        this.currentUsername = null;
        this.currentRole = null;
        this.isLoggedIn = false;
    }
    
    // Check if logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    // Get current username
    public String getCurrentUsername() {
        return currentUsername;
    }
    
    // Get current role
    public String getCurrentRole() {
        return currentRole;
    }
    
    // Validate session
    public boolean validateSession(String requiredRole) {
        if (!isLoggedIn) {
            System.out.println("❌ No active session!");
            return false;
        }
        
        if (!currentRole.equals(requiredRole)) {
            System.out.println("❌ Unauthorized access! Required: " + requiredRole + ", Current: " + currentRole);
            return false;
        }
        
        return true;
    }
}