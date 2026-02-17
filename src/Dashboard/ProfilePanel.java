package Dashboard;

import config.config;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import javax.imageio.ImageIO;

public class ProfilePanel extends JPanel {
    
    private String username;
    private String role;
    private String roleColor;
    
    // Profile data
    private String email = "";
    private String bio = "";
    private String profilePicPath = "";
    private int userId = 0;
    
    // UI Components
    private JLabel lblAvatar;
    private JLabel lblUsername;
    private JLabel lblEmail;
    private JLabel lblRole;
    private JLabel lblId;
    private JLabel lblStatus;
    private JTextArea txtBio;
    private JButton btnChangePic;
    private JButton btnSave;
    
    public ProfilePanel(String username, String role) {
        this.username = username;
        this.role = role;
        
        // Set role color
        switch (role.toLowerCase()) {
            case "admin":
                this.roleColor = "#DC2626";
                break;
            case "client":
                this.roleColor = "#2563EB";
                break;
            case "contractor":
                this.roleColor = "#D97706";
                break;
            default:
                this.roleColor = "#6B7280";
        }
        
        loadUserData();
        setupUI();
    }
    
    private void loadUserData() {
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT id, email, bio, profile_pic, status FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                userId = rs.getInt("id");
                email = rs.getString("email") != null ? rs.getString("email") : "";
                bio = rs.getString("bio") != null ? rs.getString("bio") : "";
                profilePicPath = rs.getString("profile_pic") != null ? rs.getString("profile_pic") : "";
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupUI() {
        setLayout(null);
        setBackground(new Color(245, 247, 250));
        
        // ═══════════════════════════════
        // MAIN CARD
        // ═══════════════════════════════
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(20, 15, 440, 370);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        add(card);
        
        // ═══════════════════════════════
        // LEFT SIDE - AVATAR & INFO
        // ═══════════════════════════════
        
        // Avatar
        lblAvatar = new JLabel();
        lblAvatar.setBounds(20, 20, 100, 100);
        lblAvatar.setBackground(getColorFromRole());
        lblAvatar.setOpaque(true);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setVerticalAlignment(SwingConstants.CENTER);
        lblAvatar.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        
        // Load profile picture or show default
        if (!profilePicPath.isEmpty()) {
            loadProfilePicture(profilePicPath);
        } else {
            lblAvatar.setText("👤");
            lblAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
            lblAvatar.setForeground(Color.WHITE);
        }
        card.add(lblAvatar);
        
        // Change Photo Button
        btnChangePic = new JButton("📷 Change Photo");
        btnChangePic.setBounds(20, 125, 100, 25);
        btnChangePic.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        btnChangePic.setBackground(new Color(241, 245, 249));
        btnChangePic.setForeground(new Color(71, 85, 105));
        btnChangePic.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        btnChangePic.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChangePic.setFocusPainted(false);
        btnChangePic.addActionListener(e -> changeProfilePicture());
        card.add(btnChangePic);
        
        // ═══════════════════════════════
        // RIGHT SIDE - USER DETAILS
        // ═══════════════════════════════
        
        // Username
        JLabel lblNameTitle = new JLabel("Username");
        lblNameTitle.setBounds(135, 20, 200, 15);
        lblNameTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNameTitle.setForeground(new Color(148, 163, 184));
        card.add(lblNameTitle);
        
        lblUsername = new JLabel(username.toUpperCase());
        lblUsername.setBounds(135, 35, 290, 25);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUsername.setForeground(new Color(30, 41, 59));
        card.add(lblUsername);
        
        // Role Badge
        lblRole = new JLabel(role.toUpperCase());
        lblRole.setBounds(135, 63, 100, 22);
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblRole.setForeground(Color.WHITE);
        lblRole.setBackground(getColorFromRole());
        lblRole.setOpaque(true);
        lblRole.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblRole);
        
        // Separator line
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(135, 93, 290, 2);
        sep1.setForeground(new Color(241, 245, 249));
        card.add(sep1);
        
        // ID
        JLabel lblIdTitle = new JLabel("👤  User ID");
        lblIdTitle.setBounds(135, 100, 150, 18);
        lblIdTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblIdTitle.setForeground(new Color(100, 116, 139));
        card.add(lblIdTitle);
        
        lblId = new JLabel("#" + String.format("%03d", userId));
        lblId.setBounds(290, 100, 135, 18);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblId.setForeground(new Color(30, 41, 59));
        card.add(lblId);
        
        // Email
        JLabel lblEmailTitle = new JLabel("✉️  Email");
        lblEmailTitle.setBounds(135, 123, 150, 18);
        lblEmailTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmailTitle.setForeground(new Color(100, 116, 139));
        card.add(lblEmailTitle);
        
        lblEmail = new JLabel(email.isEmpty() ? "Not set" : email);
        lblEmail.setBounds(200, 123, 225, 18);
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEmail.setForeground(new Color(30, 41, 59));
        card.add(lblEmail);
        
        // Status
        JLabel lblStatusTitle = new JLabel("🟢  Status");
        lblStatusTitle.setBounds(135, 146, 150, 18);
        lblStatusTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatusTitle.setForeground(new Color(100, 116, 139));
        card.add(lblStatusTitle);
        
        lblStatus = new JLabel("● Active");
        lblStatus.setBounds(200, 146, 225, 18);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(new Color(34, 197, 94));
        card.add(lblStatus);
        
        // ═══════════════════════════════
        // BIO SECTION
        // ═══════════════════════════════
        
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(15, 170, 410, 2);
        sep2.setForeground(new Color(241, 245, 249));
        card.add(sep2);
        
        JLabel lblBioTitle = new JLabel("📝  Bio");
        lblBioTitle.setBounds(15, 178, 200, 20);
        lblBioTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBioTitle.setForeground(new Color(30, 41, 59));
        card.add(lblBioTitle);
        
        JLabel lblBioHint = new JLabel("Tell others about yourself...");
        lblBioHint.setBounds(300, 178, 200, 20);
        lblBioHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblBioHint.setForeground(new Color(148, 163, 184));
        card.add(lblBioHint);
        
        txtBio = new JTextArea();
        txtBio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBio.setLineWrap(true);
        txtBio.setWrapStyleWord(true);
        txtBio.setText(bio.isEmpty() ? "" : bio);
        txtBio.setForeground(new Color(30, 41, 59));
        txtBio.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        txtBio.setBackground(new Color(248, 250, 252));
        
        JScrollPane bioScroll = new JScrollPane(txtBio);
        bioScroll.setBounds(15, 200, 410, 100);
        bioScroll.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        card.add(bioScroll);
        
        // Character counter
        JLabel lblCharCount = new JLabel("0/200 characters");
        lblCharCount.setBounds(330, 302, 120, 18);
        lblCharCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCharCount.setForeground(new Color(148, 163, 184));
        card.add(lblCharCount);
        
        // Update character count
        txtBio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            
            private void updateCount() {
                int count = txtBio.getText().length();
                lblCharCount.setText(count + "/200 characters");
                
                // ✅ LIMIT TO 200 CHARACTERS
                if (count > 200) {
                    SwingUtilities.invokeLater(() -> {
                        String text = txtBio.getText();
                        txtBio.setText(text.substring(0, 200));
                    });
                }
            }
        });
        
        // Update count initially
        lblCharCount.setText(bio.length() + "/200 characters");
        
        // ═══════════════════════════════
        // SAVE BUTTON
        // ═══════════════════════════════
        
        btnSave = new JButton("💾  SAVE PROFILE");
        btnSave.setBounds(15, 330, 410, 35);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(getColorFromRole());
        btnSave.setForeground(Color.WHITE);
        btnSave.setBorderPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveProfile());
        
        // Hover effect
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnSave.setBackground(getColorFromRole().darker());
            }
            public void mouseExited(MouseEvent evt) {
                btnSave.setBackground(getColorFromRole());
            }
        });
        card.add(btnSave);
    }
    
    private Color getColorFromRole() {
        switch (role.toLowerCase()) {
            case "admin": return new Color(220, 38, 38);
            case "client": return new Color(37, 99, 235);
            case "contractor": return new Color(217, 119, 6);
            default: return new Color(107, 114, 128);
        }
    }
    
    private void loadProfilePicture(String path) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                BufferedImage img = ImageIO.read(imgFile);
                
                // Resize to fit avatar
                Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                BufferedImage rounded = makeRoundedImage(scaled);
                
                lblAvatar.setText("");
                lblAvatar.setIcon(new ImageIcon(rounded));
            } else {
                setDefaultAvatar();
            }
        } catch (Exception e) {
            setDefaultAvatar();
        }
    }
    
    private BufferedImage makeRoundedImage(Image image) {
        BufferedImage result = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, 100, 100));
        g2.drawImage(image, 0, 0, 100, 100, null);
        g2.dispose();
        
        return result;
    }
    
    private void setDefaultAvatar() {
        lblAvatar.setText("👤");
        lblAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setIcon(null);
    }
    
    private void changeProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        
        // Filter for image files only
        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files (*.jpg, *.png, *.gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            profilePicPath = selectedFile.getAbsolutePath();
            loadProfilePicture(profilePicPath);
            
            JOptionPane.showMessageDialog(this,
                "✅ Profile picture selected!\n\nClick 'Save Profile' to save changes.",
                "Photo Selected",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveProfile() {
        String bioText = txtBio.getText().trim();
        
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "UPDATE users SET bio = ?, profile_pic = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bioText);
            pstmt.setString(2, profilePicPath);
            pstmt.setString(3, username);
            
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "✅ Profile saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Update local data
            bio = bioText;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "❌ Error saving profile: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}