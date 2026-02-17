package Dashboard;

import Login.Login;
import config.SessionManager;
import config.config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.border.*;

public class RoleSelectionDashboard extends JFrame {
 private String currentUsername;
private static final String ADMIN_SECRET_CODE = "ADMIN2026";

    // ===== PROFESSIONAL CORPORATE THEME =====
    private final Color BG = new Color(15, 23, 42);          // deep navy
    private final Color HEADER_BG = new Color(30, 41, 59);  // slate
    private final Color CARD_BG = new Color(51, 65, 85);    // soft slate
    private final Color CARD_HOVER = new Color(71, 85, 105);
    private final Color TEXT_MAIN = new Color(226, 232, 240);
    private final Color TEXT_SUB = new Color(148, 163, 184);
    private final Color BORDER = new Color(71, 85, 105);

   public RoleSelectionDashboard(String username) {
    this.currentUsername = username;  // ✅ FIXED
    initComponents();
    setLocationRelativeTo(null);
}

    private void initComponents() {
        setTitle("Contractor Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    // ================= HEADER =================
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BG);
        panel.setPreferredSize(new Dimension(800, 120));
        panel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(HEADER_BG);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(28, 40, 28, 40));

        JLabel title = new JLabel("Contractor Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_MAIN);

       JLabel welcome = new JLabel("Welcome, " + currentUsername);  // ✅ FIXED
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcome.setForeground(TEXT_SUB);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(welcome);

        panel.add(titlePanel, BorderLayout.CENTER);
        return panel;
    }

    // ================= CENTER =================
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(35, 70, 35, 70));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel instruction = new JLabel("");
        instruction.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        instruction.setForeground(TEXT_SUB);
        instruction.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        panel.add(instruction, gbc);

        gbc.gridy = 1;
        panel.add(createDashboardCard(
                "ADMIN",
                "Admin Dashboard",
                "System configuration and user management",
                new Color(56, 189, 248),
                e -> btnAdminActionPerformed(e)
        ), gbc);

        gbc.gridy = 2;
        panel.add(createDashboardCard(
                "CONTRACTOR",
                "Contractor Dashboard",
                "Projects, schedules and task monitoring",
                new Color(34, 197, 94),
                e -> btnContractorActionPerformed(e)
        ), gbc);

        gbc.gridy = 3;
        panel.add(createDashboardCard(
                "CLIENT",
                "Client Dashboard",
                "Project posting and contractor hiring",
                new Color(251, 191, 36),
                e -> btnClientActionPerformed(e)
        ), gbc);

        return panel;
    }

    // ================= CARD =================
    private JPanel createDashboardCard(
            String badge,
            String title,
            String description,
            Color accentColor,
            ActionListener action) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setPreferredSize(new Dimension(640, 96));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(new LineBorder(BORDER, 1, true));

        // Accent line
        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(6, 0));
        accent.setBackground(accentColor);
        card.add(accent, BorderLayout.WEST);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(CARD_BG);
        content.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        JPanel left = new JPanel();
        left.setBackground(CARD_BG);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel badgeLabel = new JLabel(badge);
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badgeLabel.setForeground(accentColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_MAIN);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(TEXT_SUB);

        left.add(badgeLabel);
        left.add(Box.createVerticalStrut(4));
        left.add(titleLabel);
        left.add(descLabel);

        JLabel arrow = new JLabel("→");
        arrow.setFont(new Font("Segoe UI", Font.BOLD, 22));
        arrow.setForeground(accentColor);

        content.add(left, BorderLayout.CENTER);
        content.add(arrow, BorderLayout.EAST);
        card.add(content, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(CARD_HOVER);
                content.setBackground(CARD_HOVER);
                left.setBackground(CARD_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
                content.setBackground(CARD_BG);
                left.setBackground(CARD_BG);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
        });

        return card;
    }

    // ================= FOOTER =================
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
        panel.setBackground(HEADER_BG);
        panel.setPreferredSize(new Dimension(800, 70));
        panel.setBorder(new MatteBorder(1, 0, 0, 0, BORDER));

        JButton logout = new JButton("LOG OUT");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logout.setForeground(TEXT_MAIN);
        logout.setBackground(new Color(185, 28, 28));
        logout.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 28));
        logout.setFocusPainted(false);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addActionListener(e -> btnLogoutActionPerformed(e));
        panel.add(logout);

        return panel;
    }

    // ================= ACTIONS =================
   private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {    
       String inputCode = JOptionPane.showInputDialog(this,
            "Enter Admin Secret Code:",
            "Admin Verification",
            JOptionPane.PLAIN_MESSAGE);
        
        if (inputCode == null) {
            return; // User cancelled
        }
        
        // Verify secret code
        if (!inputCode.equals(ADMIN_SECRET_CODE)) {
            JOptionPane.showMessageDialog(this,
                "❌ Invalid Admin Code!\nAccess Denied.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ CODE IS CORRECT - Update database
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "UPDATE users SET role = ?, status = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "admin");  // ✅ Set role to ADMIN
            pstmt.setString(2, "pending"); // Set status to pending
            pstmt.setString(3, currentUsername);
            
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Admin role saved: " + rows + " rows updated");
            
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ Show pending message
        JOptionPane.showMessageDialog(this,
            "✅ Your request to join as ADMIN has been submitted.\n\n" +
            "⏳ Please wait for super admin approval before logging in.\n" +
            "You will be notified once approved.",
            "Pending Approval",
            JOptionPane.INFORMATION_MESSAGE);
        
        this.dispose();
    new Login().setVisible(true);
    
    }

    private void btnContractorActionPerformed(java.awt.event.ActionEvent evt) {                                              
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            // ✅ Update BOTH role and status
            String sql = "UPDATE users SET role = ?, status = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "contractor"); // ✅ Set role to CONTRACTOR
            pstmt.setString(2, "pending");    // Set status to pending
            pstmt.setString(3, currentUsername);
            
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Contractor role saved: " + rows + " rows updated");
            
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ Show pending message
        JOptionPane.showMessageDialog(this,
            "✅ Your request to join as CONTRACTOR has been submitted.\n\n" +
            "⏳ Please wait for admin approval before logging in.\n" +
            "You will be notified once approved.",
            "Pending Approval",
            JOptionPane.INFORMATION_MESSAGE);
        
       this.dispose();
    new Login().setVisible(true);
    }

  private void btnClientActionPerformed(ActionEvent evt) {
    try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        
        String sql = "UPDATE users SET role = ?, status = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "client");
        pstmt.setString(2, "pending");
        pstmt.setString(3, currentUsername);  // ✅ FIXED
        
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Database error: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    JOptionPane.showMessageDialog(this,
        "✅ Request submitted.\n⏳ Wait for approval.",
        "Pending Approval",
        JOptionPane.INFORMATION_MESSAGE);
    
    this.dispose();
    new Login().setVisible(true);
}

   private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
    int confirm = JOptionPane.showConfirmDialog(this,
        "Return to login screen?",
        "Confirm",
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // ✅ CLEAR SESSION FIRST!
        SessionManager.getInstance().logout();
        
        this.dispose();
        new Login().setVisible(true);
        
    }
}
}