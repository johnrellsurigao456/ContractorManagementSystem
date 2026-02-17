/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard;

import config.config;
import config.SessionManager;  // ✅ ADD THIS LINE
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.sql.*;
    

public class AdminDashboard extends javax.swing.JFrame {

    private String loggedInUsername;
    private int adminId;
    
    // Tables
    private DefaultTableModel modelUsers;
    private DefaultTableModel modelProjects;
    private DefaultTableModel modelPayments;
    private DefaultTableModel modelContractors;
    private DefaultTableModel modelClients;
    private DefaultTableModel modelPending;
    
    private JTable tblUsers;
    private JTable tblProjects;
    private JTable tblPayments;
    private JTable tblContractors;
    private JTable tblClients;
    private JTable tblPending;
    
    // Sorters for search
    private TableRowSorter<DefaultTableModel> sorterUsers;
    private TableRowSorter<DefaultTableModel> sorterProjects;
    private TableRowSorter<DefaultTableModel> sorterPayments;
    private TableRowSorter<DefaultTableModel> sorterContractors;
    private TableRowSorter<DefaultTableModel> sorterClients;
    private TableRowSorter<DefaultTableModel> sorterPending;
    
    // Dashboard stats
    private JLabel lblTotalUsers;
    private JLabel lblTotalProjects;
    private JLabel lblTotalContractors;
    private JLabel lblTotalRevenue;
    private String bio;
    private Object[] clientId;
    

  public AdminDashboard(String username) {
    // ✅ VALIDATE SESSION FIRST
    SessionManager session = SessionManager.getInstance();
    
    if (!session.isLoggedIn() || !session.validateSession("admin")) {
        JOptionPane.showMessageDialog(null,
            "❌ Unauthorized Access!\n\nPlease login first.",
            "Security Alert",
            JOptionPane.ERROR_MESSAGE);
        
        this.dispose();
        new Login.Login().setVisible(true);
        return;
    }
    
    this.loggedInUsername = username;
    initComponents();
    
    // ✅ ADD WINDOW LISTENER HERE (AFTER initComponents)
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            SessionManager.getInstance().logout();
            System.exit(0);
        }
    });
    
    getAdminId();
    jLabel2.setText("WELCOME, " + username.toUpperCase());
    setupAllTabs();
    loadAllData();
}
        /*  displayData("SELECT * FROM users", jTableUsers);*/

  
    private void getAdminId() {
       try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            String sql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUsername);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) adminId = rs.getInt("id");
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private void setupAllTabs() {
        setupProfileTab();
        setupDashboardTab();
        setupUsersTab();
        setupProjectsTab();
        setupPaymentsTab();
        setupContractorsTab();
        setupClientsTab();
        setupPendingTab();
    }

    private void loadDashboardStats() {
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            ResultSet r1 = conn.createStatement().executeQuery("SELECT COUNT(*) as c FROM users");
            if (r1.next()) lblTotalUsers.setText(String.valueOf(r1.getInt("c")));
            r1.close();
            
            try {
                ResultSet r2 = conn.createStatement().executeQuery("SELECT COUNT(*) as c FROM projects");
                if (r2.next()) lblTotalProjects.setText(String.valueOf(r2.getInt("c")));
                r2.close();
            } catch (Exception e) { lblTotalProjects.setText("0"); }
            
            ResultSet r3 = conn.createStatement().executeQuery("SELECT COUNT(*) as c FROM users WHERE role = 'contractor' AND status = 'approved'");
            if (r3.next()) lblTotalContractors.setText(String.valueOf(r3.getInt("c")));
            r3.close();
            
            try {
                ResultSet r4 = conn.createStatement().executeQuery("SELECT SUM(amount) as t FROM payments");
                if (r4.next()) lblTotalRevenue.setText("₱" + String.format("%.2f", r4.getDouble("t")));
                r4.close();
            } catch (Exception e) { lblTotalRevenue.setText("₱0.00"); }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadAllData() {
        loadUsers();
        loadProjects();
        loadPayments();
        loadContractors();
        loadClients();
        loadPending();
    }


   

   
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("ADMIN DASHBOARD");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WELCOME, ADMIN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("  👤 My Profile");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 30));

        jButton3.setBackground(new java.awt.Color(0, 0, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("📊 Dashboard");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, 30));

        jButton4.setBackground(new java.awt.Color(0, 0, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("👥 Manage Users ");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("📁 Manage Projects");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 30));

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("💰 View Payments ");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 30));

        jButton7.setBackground(new java.awt.Color(0, 0, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("🔧 View Contractors ");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 150, 30));

        jButton8.setBackground(new java.awt.Color(0, 0, 255));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText(" 🏢 View Clients ");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 150, 30));

        jButton9.setBackground(new java.awt.Color(0, 0, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("⏳ Pending Users");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 30));

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("LOGOUT");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 110, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 170, 430));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab7", jPanel9);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab8", jPanel10);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 480, 450));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION) {
            // ✅ DESTROY SESSION
            SessionManager.getInstance().logout();
            
            this.dispose();
            new Login.Login().setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      jTabbedPane1.setSelectedIndex(1);
        loadDashboardStats();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      jTabbedPane1.setSelectedIndex(2);
        loadUsers();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       jTabbedPane1.setSelectedIndex(3);
        loadProjects();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    jTabbedPane1.setSelectedIndex(4);
        loadPayments();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       jTabbedPane1.setSelectedIndex(5);
        loadContractors();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
      jTabbedPane1.setSelectedIndex(6);
        loadClients();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
      jTabbedPane1.setSelectedIndex(7);
        loadPending();
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
     SessionManager session = SessionManager.getInstance();
    
    if (!session.isLoggedIn()) {
        JOptionPane.showMessageDialog(null,
            "❌ Please login first!",
            "Security Alert",
            JOptionPane.ERROR_MESSAGE);
        
        new Login.Login().setVisible(true);
        return;
    }
    
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            String username = session.getCurrentUsername();
            if (username != null) {
                new AdminDashboard(username).setVisible(true);
            } else {
                new Login.Login().setVisible(true);
            }
        }
    });
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    
    
    
    private void loadUsers() {
       try {
            modelUsers.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT id, username, role, status FROM users WHERE status = 'approved' ORDER BY id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelUsers.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role") != null ? rs.getString("role").toUpperCase() : "—",
                    rs.getString("status").toUpperCase()
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadProjects() {
           try {
            modelProjects.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT p.id, p.project_name, " +
                        "c.username as client, " +
                        "COALESCE(co.username, 'None') as contractor, " +
                        "p.status " +
                        "FROM projects p " +
                        "JOIN users c ON p.client_id = c.id " +
                        "LEFT JOIN users co ON p.contractor_id = co.id " +
                        "ORDER BY p.id DESC";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelProjects.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("project_name"),
                    rs.getString("client"),
                    rs.getString("contractor"),
                    rs.getString("status")
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPayments() {
        try {
            modelPayments.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT pay.id, p.project_name, pay.amount, pay.payment_date, pay.status " +
                        "FROM payments pay " +
                        "JOIN projects p ON pay.project_id = p.id " +
                        "ORDER BY pay.id DESC";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelPayments.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("project_name"),
                    "₱" + String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("payment_date"),
                    rs.getString("status")
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadContractors() {
          try {
            modelContractors.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT id, username, status FROM users WHERE role = 'contractor' AND status = 'approved' ORDER BY id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelContractors.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("status").toUpperCase()
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClients() {
       try {
            modelClients.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT id, username, status FROM users WHERE role = 'client' AND status = 'approved' ORDER BY id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelClients.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("status").toUpperCase()
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadPending() {
        try {
            modelPending.setRowCount(0);
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            String sql = "SELECT id, username, role, status FROM users " +
                        "WHERE role IS NOT NULL AND role != 'admin' " +
                        "AND (status = 'pending' OR status IS NULL OR status = '') " +
                        "ORDER BY id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                modelPending.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role") != null ? rs.getString("role").toUpperCase() : "—",
                    "PENDING"
                });
            }
            
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupProfileTab() {
     jPanel3.setLayout(null);
    jPanel3.setBackground(new Color(245, 247, 250));
    
    // ✅ GET DATA FIRST
    String currentBio = getBio();
    String currentEmail = getEmail();
    String currentPic = getProfilePic();
    
    // ═══ SCROLL PANEL PARA DILI MATABAN ═══
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setBackground(new Color(245, 247, 250));
    mainPanel.setPreferredSize(new Dimension(460, 700));
    
    JScrollPane mainScroll = new JScrollPane(mainPanel);
    mainScroll.setBounds(0, 0, 475, 420);
    mainScroll.setBorder(null);
    mainScroll.getVerticalScrollBar().setUnitIncrement(16);
    jPanel3.add(mainScroll);
    
    // ═══ PROFILE CARD ═══
    JPanel card = new JPanel();
    card.setLayout(null);
    card.setBounds(10, 10, 440, 660);
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
    mainPanel.add(card);
    
    // ═══ SECTION TITLE - PROFILE INFO ═══
    JPanel headerBar = new JPanel();
    headerBar.setLayout(null);
    headerBar.setBounds(0, 0, 440, 35);
    headerBar.setBackground(new Color(220, 38, 38));  // RED for ADMIN
    card.add(headerBar);
    
    JLabel headerTitle = new JLabel("👤  MY PROFILE");
    headerTitle.setBounds(15, 5, 300, 25);
    headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
    headerTitle.setForeground(Color.WHITE);
    headerBar.add(headerTitle);
    
    // ═══ AVATAR ═══
    JPanel avatar = new JPanel();
    avatar.setBounds(20, 50, 90, 90);
    avatar.setBackground(new Color(220, 38, 38));  // RED for ADMIN
    avatar.setLayout(new BorderLayout());
    
    File picFile = new File(currentPic.isEmpty() ? "nofile" : currentPic);
    if (!currentPic.isEmpty() && picFile.exists()) {
        try {
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(picFile);
            java.awt.Image scaled = img.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
            avatar.add(new JLabel(new ImageIcon(scaled)));
        } catch (Exception e) {
            addDefaultAvatar(avatar);
        }
    } else {
        addDefaultAvatar(avatar);
    }
    card.add(avatar);
    
    // ═══ CHANGE PHOTO BUTTON ═══
    JButton btnPhoto = new JButton("📷 Change");
    btnPhoto.setBounds(20, 145, 90, 22);
    btnPhoto.setFont(new Font("Segoe UI", Font.PLAIN, 9));
    btnPhoto.setBackground(new Color(241, 245, 249));
    btnPhoto.setForeground(new Color(71, 85, 105));
    btnPhoto.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
    btnPhoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnPhoto.setFocusPainted(false);
    btnPhoto.addActionListener(e -> {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Images (*.jpg, *.png)", "jpg", "jpeg", "png"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            saveProfilePic(fc.getSelectedFile().getAbsolutePath());
            jPanel3.removeAll();
            setupProfileTab();
            jPanel3.revalidate();
            jPanel3.repaint();
        }
    });
    card.add(btnPhoto);
    
    // ═══ USERNAME ═══
    JLabel lblNameTitle = new JLabel("Username");
    lblNameTitle.setBounds(125, 50, 200, 15);
    lblNameTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    lblNameTitle.setForeground(new Color(148, 163, 184));
    card.add(lblNameTitle);
    
    JLabel lblName = new JLabel(loggedInUsername.toUpperCase());
    lblName.setBounds(125, 65, 300, 28);
    lblName.setFont(new Font("Segoe UI", Font.BOLD, 20));
    lblName.setForeground(new Color(30, 41, 59));
    card.add(lblName);
    
    // ═══ ROLE BADGE ═══
    JLabel lblRole = new JLabel("ADMIN", SwingConstants.CENTER);
    lblRole.setBounds(125, 96, 80, 22);
    lblRole.setFont(new Font("Segoe UI", Font.BOLD, 11));
    lblRole.setForeground(Color.WHITE);
    lblRole.setBackground(new Color(220, 38, 38));
    lblRole.setOpaque(true);
    card.add(lblRole);
    
    // ═══ SEPARATOR ═══
    JSeparator sep1 = new JSeparator();
    sep1.setBounds(15, 175, 410, 2);
    sep1.setForeground(new Color(241, 245, 249));
    card.add(sep1);
    
    // ═══ USER DETAILS ═══
    // User ID
    JLabel lblIdTitle = new JLabel("👤  User ID:");
    lblIdTitle.setBounds(15, 183, 120, 20);
    lblIdTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblIdTitle.setForeground(new Color(100, 116, 139));
    card.add(lblIdTitle);
    
    JLabel lblId = new JLabel("#" + String.format("%03d", adminId));
    lblId.setBounds(140, 183, 200, 20);
    lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblId.setForeground(new Color(30, 41, 59));
    card.add(lblId);
    
    // Email
    JLabel lblEmailTitle = new JLabel("✉️  Email:");
    lblEmailTitle.setBounds(15, 208, 120, 20);
    lblEmailTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblEmailTitle.setForeground(new Color(100, 116, 139));
    card.add(lblEmailTitle);
    
    JLabel lblEmail = new JLabel(currentEmail.isEmpty() ? "Not set" : currentEmail);
    lblEmail.setBounds(140, 208, 285, 20);
    lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    lblEmail.setForeground(new Color(30, 41, 59));
    card.add(lblEmail);
    
    // Status
    JLabel lblStatusTitle = new JLabel("🟢  Status:");
    lblStatusTitle.setBounds(15, 233, 120, 20);
    lblStatusTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblStatusTitle.setForeground(new Color(100, 116, 139));
    card.add(lblStatusTitle);
    
    JLabel lblStatus = new JLabel("● Super Admin");
    lblStatus.setBounds(140, 233, 285, 20);
    lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblStatus.setForeground(new Color(34, 197, 94));
    card.add(lblStatus);
    
    // ═══ SEPARATOR ═══
    JSeparator sep2 = new JSeparator();
    sep2.setBounds(15, 260, 410, 2);
    sep2.setForeground(new Color(241, 245, 249));
    card.add(sep2);
    
    // ═══ BIO SECTION ═══
    JPanel bioHeader = new JPanel();
    bioHeader.setLayout(null);
    bioHeader.setBounds(15, 268, 410, 25);
    bioHeader.setBackground(Color.WHITE);
    card.add(bioHeader);
    
    JLabel lblBioTitle = new JLabel("📝  Bio");
    lblBioTitle.setBounds(0, 0, 200, 25);
    lblBioTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
    lblBioTitle.setForeground(new Color(30, 41, 59));
    bioHeader.add(lblBioTitle);
    
    JLabel lblCharCount = new JLabel(currentBio.length() + "/200");
    lblCharCount.setBounds(355, 0, 55, 25);
    lblCharCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    lblCharCount.setForeground(new Color(148, 163, 184));
    bioHeader.add(lblCharCount);
    
    JTextArea txtBio = new JTextArea();
    txtBio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    txtBio.setLineWrap(true);
    txtBio.setWrapStyleWord(true);
    txtBio.setText(currentBio);
    txtBio.setForeground(new Color(30, 41, 59));
    txtBio.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    txtBio.setBackground(new Color(248, 250, 252));
    
    txtBio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        void update() {
            int count = txtBio.getText().length();
            lblCharCount.setText(count + "/200");
            if (count > 200) {
                SwingUtilities.invokeLater(() ->
                    txtBio.setText(txtBio.getText().substring(0, 200)));
            }
        }
    });
    
    JScrollPane bioScroll = new JScrollPane(txtBio);
    bioScroll.setBounds(15, 295, 410, 80);
    bioScroll.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
    card.add(bioScroll);
    
    // ═══ SAVE PROFILE BUTTON ═══
    JButton btnSave = new JButton("💾  SAVE PROFILE");
    btnSave.setBounds(15, 385, 410, 38);
    btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnSave.setBackground(new Color(220, 38, 38));
    btnSave.setForeground(Color.WHITE);
    btnSave.setBorderPainted(false);
    btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnSave.setFocusPainted(false);
    btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btnSave.setBackground(new Color(185, 28, 28));
        }
        public void mouseExited(java.awt.event.MouseEvent e) {
            btnSave.setBackground(new Color(220, 38, 38));
        }
    });
    btnSave.addActionListener(e -> {
        String bioText = txtBio.getText().trim();
        saveBio(bioText);
        JOptionPane.showMessageDialog(this,
            "✅ Profile saved successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        jPanel3.removeAll();
        setupProfileTab();
        jPanel3.revalidate();
        jPanel3.repaint();
    });
    card.add(btnSave);
    
    // ═══ SEPARATOR ═══
    JSeparator sep3 = new JSeparator();
    sep3.setBounds(15, 435, 410, 2);
    sep3.setForeground(new Color(241, 245, 249));
    card.add(sep3);
    
    // ═══ PASSWORD CHANGE SECTION ═══
    JPanel pwHeader = new JPanel();
    pwHeader.setLayout(null);
    pwHeader.setBounds(0, 438, 440, 35);
    pwHeader.setBackground(new Color(51, 65, 85));
    card.add(pwHeader);
    
    JLabel pwTitle = new JLabel("🔒  CHANGE PASSWORD");
    pwTitle.setBounds(15, 5, 300, 25);
    pwTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
    pwTitle.setForeground(Color.WHITE);
    pwHeader.add(pwTitle);
    
    // Current Password
    JLabel lblCurrent = new JLabel("Current Password:");
    lblCurrent.setBounds(15, 483, 150, 20);
    lblCurrent.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblCurrent.setForeground(new Color(100, 116, 139));
    card.add(lblCurrent);
    
    JPasswordField txtCurrent = new JPasswordField();
    txtCurrent.setBounds(170, 483, 255, 28);
    txtCurrent.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    txtCurrent.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    card.add(txtCurrent);
    
    // New Password
    JLabel lblNew = new JLabel("New Password:");
    lblNew.setBounds(15, 520, 150, 20);
    lblNew.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblNew.setForeground(new Color(100, 116, 139));
    card.add(lblNew);
    
    JPasswordField txtNew = new JPasswordField();
    txtNew.setBounds(170, 520, 255, 28);
    txtNew.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    txtNew.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    card.add(txtNew);
    
    // Confirm Password
    JLabel lblConfirm = new JLabel("Confirm Password:");
    lblConfirm.setBounds(15, 557, 150, 20);
    lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lblConfirm.setForeground(new Color(100, 116, 139));
    card.add(lblConfirm);
    
    JPasswordField txtConfirm = new JPasswordField();
    txtConfirm.setBounds(170, 557, 255, 28);
    txtConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    txtConfirm.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    card.add(txtConfirm);
    
    // Password strength indicator
    JLabel lblStrength = new JLabel("");
    lblStrength.setBounds(170, 590, 255, 18);
    lblStrength.setFont(new Font("Segoe UI", Font.BOLD, 11));
    card.add(lblStrength);
    
    // Update strength on type
    txtNew.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        void update() {
            String pw = new String(txtNew.getPassword());
            if (pw.isEmpty()) {
                lblStrength.setText("");
            } else if (pw.length() < 6) {
                lblStrength.setText("🔴 Weak");
                lblStrength.setForeground(new Color(220, 38, 38));
            } else if (pw.length() < 10) {
                lblStrength.setText("🟡 Medium");
                lblStrength.setForeground(new Color(234, 179, 8));
            } else {
                lblStrength.setText("🟢 Strong");
                lblStrength.setForeground(new Color(34, 197, 94));
            }
        }
    });
    
    // ═══ CHANGE PASSWORD BUTTON ═══
    JButton btnChangePw = new JButton("🔒  CHANGE PASSWORD");
    btnChangePw.setBounds(15, 615, 410, 38);
    btnChangePw.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnChangePw.setBackground(new Color(51, 65, 85));
    btnChangePw.setForeground(Color.WHITE);
    btnChangePw.setBorderPainted(false);
    btnChangePw.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnChangePw.setFocusPainted(false);
    btnChangePw.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btnChangePw.setBackground(new Color(30, 41, 59));
        }
        public void mouseExited(java.awt.event.MouseEvent e) {
            btnChangePw.setBackground(new Color(51, 65, 85));
        }
    });
    btnChangePw.addActionListener(e -> {
        String current = new String(txtCurrent.getPassword()).trim();
        String newPw = new String(txtNew.getPassword()).trim();
        String confirm = new String(txtConfirm.getPassword()).trim();
        
        // ✅ VALIDATIONS
        if (current.isEmpty() || newPw.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "⚠️ All password fields are required!",
                "Missing Fields",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (newPw.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "❌ New password must be at least 6 characters!",
                "Too Short",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!newPw.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                "❌ New passwords do not match!\n\nPlease re-enter.",
                "Mismatch",
                JOptionPane.ERROR_MESSAGE);
            txtNew.setText("");
            txtConfirm.setText("");
            txtNew.requestFocus();
            return;
        }
        
        // ✅ VERIFY CURRENT PASSWORD
        if (!verifyCurrentPassword(current)) {
            JOptionPane.showMessageDialog(this,
                "❌ Current password is incorrect!\n\nPlease try again.",
                "Wrong Password",
                JOptionPane.ERROR_MESSAGE);
            txtCurrent.setText("");
            txtCurrent.requestFocus();
            return;
        }
        
        // ✅ CANNOT USE SAME PASSWORD
        if (current.equals(newPw)) {
            JOptionPane.showMessageDialog(this,
                "❌ New password cannot be same\nas current password!",
                "Same Password",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ SAVE NEW PASSWORD
        if (changePassword(newPw)) {
            JOptionPane.showMessageDialog(this,
                "✅ Password changed successfully!\n\nPlease remember your new password.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            txtCurrent.setText("");
            txtNew.setText("");
            txtConfirm.setText("");
            lblStrength.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error changing password!\n\nPlease try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    });
    card.add(btnChangePw);
    }

    private void setupDashboardTab() {
         jPanel4.setLayout(null);
        jPanel4.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("SYSTEM OVERVIEW");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(20, 15, 400, 30);
        jPanel4.add(title);
        
        JPanel c1 = createCard("👥 Users", "0", 20, 60, new Color(59, 130, 246));
        JPanel c2 = createCard("📁 Projects", "0", 250, 60, new Color(34, 197, 94));
        JPanel c3 = createCard("🔧 Contractors", "0", 20, 210, new Color(251, 146, 60));
        JPanel c4 = createCard("💰 Revenue", "₱0.00", 250, 210, new Color(168, 85, 247));
        
        jPanel4.add(c1);
        jPanel4.add(c2);
        jPanel4.add(c3);
        jPanel4.add(c4);
    }
    private void setupUsersTab() {
        jPanel5.setLayout(null);
    jPanel5.setBackground(new Color(245, 247, 250));
    
    JLabel title = new JLabel("👥 MANAGE USERS");
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    title.setBounds(15, 10, 300, 30);
    jPanel5.add(title);
    
    // SEARCH BAR
    JTextField txtSearch = createSearchField(15, 50);
    jPanel5.add(txtSearch);
    
    JLabel searchIcon = new JLabel("🔎");
    searchIcon.setBounds(15, 50, 30, 30);
    jPanel5.add(searchIcon);
    
    JButton btnClear = createClearButton(360, 50);
    jPanel5.add(btnClear);
    
    String[] cols = {"ID", "Username", "Role", "Status"};
    modelUsers = new DefaultTableModel(cols, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    tblUsers = createTable(modelUsers);
    sorterUsers = new TableRowSorter<>(modelUsers);
    tblUsers.setRowSorter(sorterUsers);
    
    JScrollPane sp = new JScrollPane(tblUsers);
    sp.setBounds(15, 90, 445, 180);  // ✅ Reduced height to make room for buttons
    sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
    jPanel5.add(sp);
    
    // ✅ ADD THESE CRUD BUTTONS!
    JButton btnAdd = createBtn("➕ ADD USER", 15, 280, 100, 35, new Color(34, 197, 94));
    btnAdd.addActionListener(e -> addUser());
    jPanel5.add(btnAdd);
    
    JButton btnEdit = createBtn("✏️ EDIT", 125, 280, 100, 35, new Color(59, 130, 246));
    btnEdit.addActionListener(e -> editUser());
    jPanel5.add(btnEdit);
    
    JButton btnDelete = createBtn("🗑️ DELETE", 235, 280, 100, 35, new Color(220, 38, 38));
    btnDelete.addActionListener(e -> deleteApprovedUser());
    jPanel5.add(btnDelete);
    
    JButton btnRefresh = createBtn("🔄 Refresh", 345, 280, 100, 35, new Color(168, 85, 247));
    btnRefresh.addActionListener(e -> loadUsers());
    jPanel5.add(btnRefresh);
    
    setupSearch(txtSearch, sorterUsers, btnClear);
    }

   private JTable createTable(DefaultTableModel model) {
       JTable tbl = new JTable(model);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tbl.setRowHeight(28);
        tbl.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tbl.setSelectionBackground(new Color(219, 234, 254));
        tbl.setGridColor(new Color(226, 232, 240));
        return tbl;
    }

     private JButton createBtn(String text, int x, int y, int w, int h, Color bg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

   private void setupSearch(JTextField txtSearch, TableRowSorter<DefaultTableModel> sorter, JButton btnClear) {
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String text = txtSearch.getText();
                sorter.setRowFilter(text.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });
        
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            sorter.setRowFilter(null);
        });
    }

   private JTextField createSearchField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x + 30, y, 315, 30);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return txt;
    }


    private JPanel createCard(String title, String value, int x, int y, Color color) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(x, y, 210, 130);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        
        JPanel bar = new JPanel();
        bar.setBounds(0, 0, 5, 130);
        bar.setBackground(color);
        card.add(bar);
        
        JLabel lbl1 = new JLabel(title);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl1.setForeground(new Color(100, 116, 139));
        lbl1.setBounds(15, 15, 190, 20);
        card.add(lbl1);
        
        JLabel lbl2 = new JLabel(value);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbl2.setBounds(15, 60, 190, 40);
        card.add(lbl2);
        
        if (title.contains("Users")) lblTotalUsers = lbl2;
        else if (title.contains("Projects")) lblTotalProjects = lbl2;
        else if (title.contains("Contractors")) lblTotalContractors = lbl2;
        else if (title.contains("Revenue")) lblTotalRevenue = lbl2;
        
        return card;
    }

    private JButton createClearButton(int x, int y) {
       JButton btn = new JButton("✕");
        btn.setBounds(x, y, 50, 30);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private void setupProjectsTab() {
       jPanel6.setLayout(null);
        jPanel6.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("📁 MANAGE PROJECTS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(15, 10, 300, 30);
        jPanel6.add(title);
        
        // SEARCH BAR
        JTextField txtSearch = createSearchField(15, 50);
        jPanel6.add(txtSearch);
        
        JLabel searchIcon = new JLabel("🔎");
        searchIcon.setBounds(15, 50, 30, 30);
        jPanel6.add(searchIcon);
        
        JButton btnClear = createClearButton(360, 50);
        jPanel6.add(btnClear);
        
        String[] cols = {"ID", "Project", "Client", "Contractor", "Status"};
        modelProjects = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblProjects = createTable(modelProjects);
        sorterProjects = new TableRowSorter<>(modelProjects);
        tblProjects.setRowSorter(sorterProjects);
        
        JScrollPane sp = new JScrollPane(tblProjects);
        sp.setBounds(15, 90, 445, 220);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        jPanel6.add(sp);
        
        JButton btnRefresh = createBtn("🔄 Refresh", 15, 320, 100, 35, new Color(59, 130, 246));
        btnRefresh.addActionListener(e -> loadProjects());
        jPanel6.add(btnRefresh);
        
        setupSearch(txtSearch, sorterProjects, btnClear);
    }

    private void setupPaymentsTab() {
         jPanel7.setLayout(null);
        jPanel7.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("💰 VIEW PAYMENTS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(15, 10, 300, 30);
        jPanel7.add(title);
        
        // SEARCH BAR
        JTextField txtSearch = createSearchField(15, 50);
        jPanel7.add(txtSearch);
        
        JLabel searchIcon = new JLabel("🔎");
        searchIcon.setBounds(15, 50, 30, 30);
        jPanel7.add(searchIcon);
        
        JButton btnClear = createClearButton(360, 50);
        jPanel7.add(btnClear);
        
        String[] cols = {"ID", "Project", "Amount", "Date", "Status"};
        modelPayments = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPayments = createTable(modelPayments);
        sorterPayments = new TableRowSorter<>(modelPayments);
        tblPayments.setRowSorter(sorterPayments);
        
        JScrollPane sp = new JScrollPane(tblPayments);
        sp.setBounds(15, 90, 445, 220);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        jPanel7.add(sp);
        
        JButton btnRefresh = createBtn("🔄 Refresh", 15, 320, 100, 35, new Color(59, 130, 246));
        btnRefresh.addActionListener(e -> loadPayments());
        jPanel7.add(btnRefresh);
        
        setupSearch(txtSearch, sorterPayments, btnClear);
    }


    private void setupContractorsTab() {
         jPanel8.setLayout(null);
        jPanel8.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("🔧 VIEW CONTRACTORS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(15, 10, 300, 30);
        jPanel8.add(title);
        
        // SEARCH BAR
        JTextField txtSearch = createSearchField(15, 50);
        jPanel8.add(txtSearch);
        
        JLabel searchIcon = new JLabel("🔎");
        searchIcon.setBounds(15, 50, 30, 30);
        jPanel8.add(searchIcon);
        
        JButton btnClear = createClearButton(360, 50);
        jPanel8.add(btnClear);
        
        String[] cols = {"ID", "Username", "Status"};
        modelContractors = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblContractors = createTable(modelContractors);
        sorterContractors = new TableRowSorter<>(modelContractors);
        tblContractors.setRowSorter(sorterContractors);
        
        JScrollPane sp = new JScrollPane(tblContractors);
        sp.setBounds(15, 90, 445, 220);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        jPanel8.add(sp);
        
        JButton btnRefresh = createBtn("🔄 Refresh", 15, 320, 100, 35, new Color(59, 130, 246));
        btnRefresh.addActionListener(e -> loadContractors());
        jPanel8.add(btnRefresh);
        
        setupSearch(txtSearch, sorterContractors, btnClear);
    }

    private void setupClientsTab() {
      jPanel9.setLayout(null);
        jPanel9.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("🏢 VIEW CLIENTS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(15, 10, 300, 30);
        jPanel9.add(title);
        
        // SEARCH BAR
        JTextField txtSearch = createSearchField(15, 50);
        jPanel9.add(txtSearch);
        
        JLabel searchIcon = new JLabel("🔎");
        searchIcon.setBounds(15, 50, 30, 30);
        jPanel9.add(searchIcon);
        
        JButton btnClear = createClearButton(360, 50);
        jPanel9.add(btnClear);
        
        String[] cols = {"ID", "Username", "Status"};
        modelClients = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblClients = createTable(modelClients);
        sorterClients = new TableRowSorter<>(modelClients);
        tblClients.setRowSorter(sorterClients);
        
        JScrollPane sp = new JScrollPane(tblClients);
        sp.setBounds(15, 90, 445, 220);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        jPanel9.add(sp);
        
        JButton btnRefresh = createBtn("🔄 Refresh", 15, 320, 100, 35, new Color(59, 130, 246));
        btnRefresh.addActionListener(e -> loadClients());
        jPanel9.add(btnRefresh);
        
        setupSearch(txtSearch, sorterClients, btnClear);
    }

    private void setupPendingTab() {
         jPanel10.setLayout(null);
        jPanel10.setBackground(new Color(245, 247, 250));
        
        JLabel title = new JLabel("⏳ PENDING USERS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(15, 10, 300, 30);
        jPanel10.add(title);
        
        // SEARCH BAR
        JTextField txtSearch = createSearchField(15, 50);
        jPanel10.add(txtSearch);
        
        JLabel searchIcon = new JLabel("🔎");
        searchIcon.setBounds(15, 50, 30, 30);
        jPanel10.add(searchIcon);
        
        JButton btnClear = createClearButton(360, 50);
        jPanel10.add(btnClear);
        
        String[] cols = {"ID", "Username", "Role", "Status"};
        modelPending = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPending = createTable(modelPending);
        sorterPending = new TableRowSorter<>(modelPending);
        tblPending.setRowSorter(sorterPending);
        
        JScrollPane sp = new JScrollPane(tblPending);
        sp.setBounds(15, 90, 445, 180);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 2));
        jPanel10.add(sp);
        
        JButton btnApprove = createBtn("✓ APPROVE", 15, 280, 110, 35, new Color(34, 197, 94));
        btnApprove.addActionListener(e -> approveUser());
        jPanel10.add(btnApprove);
        
        JButton btnDelete = createBtn("✗ DELETE", 135, 280, 100, 35, new Color(220, 38, 38));
        btnDelete.addActionListener(e -> deleteUser());
        jPanel10.add(btnDelete);
        
        JButton btnRefresh = createBtn("🔄 Refresh", 245, 280, 100, 35, new Color(59, 130, 246));
        btnRefresh.addActionListener(e -> loadPending());
        jPanel10.add(btnRefresh);
        
        setupSearch(txtSearch, sorterPending, btnClear);
    }

    private void approveUser() {
        int row = tblPending.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) modelPending.getValueAt(row, 0);
        String username = (String) modelPending.getValueAt(row, 1);
        
        int conf = JOptionPane.showConfirmDialog(this, "Approve " + username + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            try {
                config cfg = new config();
                Connection conn = cfg.connectDB();
                
                String sql = "UPDATE users SET status = 'approved' WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this, "User approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPending();
                loadUsers();
                loadDashboardStats();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteUser() {
      int row = tblPending.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) modelPending.getValueAt(row, 0);
        String username = (String) modelPending.getValueAt(row, 1);
        
        int conf = JOptionPane.showConfirmDialog(this, "DELETE " + username + "?\nThis cannot be undone!", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf == JOptionPane.YES_OPTION) {
            try {
                config cfg = new config();
                Connection conn = cfg.connectDB();
                
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this, "User deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPending();
                loadDashboardStats();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addUser() {
     JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));  // ✅ 5 rows!
    
    JTextField txtUsername = new JTextField();
    JTextField txtEmail = new JTextField();  // ✅ DECLARED!
    JTextField txtPassword = new JTextField();
    JComboBox<String> cboRole = new JComboBox<>(new String[]{"admin", "contractor", "client"});
    JComboBox<String> cboStatus = new JComboBox<>(new String[]{"approved", "pending"});
    
    panel.add(new JLabel("Username:"));
    panel.add(txtUsername);
    panel.add(new JLabel("Email:"));
    panel.add(txtEmail);  // ✅ CORRECT FIELD!
    panel.add(new JLabel("Password:"));
    panel.add(txtPassword);
    panel.add(new JLabel("Role:"));
    panel.add(cboRole);
    panel.add(new JLabel("Status:"));
    panel.add(cboStatus);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Add New User", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();  // ✅ GET EMAIL!
        String password = txtPassword.getText().trim();
        String role = (String) cboRole.getSelectedItem();
        String status = (String) cboStatus.getSelectedItem();
        
        // ✅ VALIDATE ALL FIELDS
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ All fields are required!\n\n" +
                "Please fill in:\n" +
                "  • Username\n" +
                "  • Email\n" +
                "  • Password", 
                "Missing Information", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ VALIDATE EMAIL FORMAT
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                "❌ Invalid Email Format!\n\n" +
                "Email must be in format:\n" +
                "  user@gmail.com",
                "Invalid Email",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            // ✅ CHECK IF USERNAME EXISTS
            String checkUserSql = "SELECT COUNT(*) as c FROM users WHERE username = ?";
            PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
            checkUserStmt.setString(1, username);
            ResultSet rs1 = checkUserStmt.executeQuery();
            if (rs1.next() && rs1.getInt("c") > 0) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Username already exists!\n\n" +
                    "Please choose a different username.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                rs1.close();
                checkUserStmt.close();
                conn.close();
                return;
            }
            rs1.close();
            checkUserStmt.close();
            
            // ✅ CHECK IF EMAIL EXISTS
            String checkEmailSql = "SELECT COUNT(*) as c FROM users WHERE email = ?";
            PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql);
            checkEmailStmt.setString(1, email);
            ResultSet rs2 = checkEmailStmt.executeQuery();
            if (rs2.next() && rs2.getInt("c") > 0) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Email already registered!\n\n" +
                    "Please use a different email address.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                rs2.close();
                checkEmailStmt.close();
                conn.close();
                return;
            }
            rs2.close();
            checkEmailStmt.close();
            
            // ✅ INSERT NEW USER WITH EMAIL
            String sql = "INSERT INTO users (username, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);     // ✅ EMAIL INCLUDED!
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this, 
                "✅ User added successfully!\n\n" +
                "Username: " + username + "\n" +
                "Email: " + email + "\n" +
                "Role: " + role, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            loadUsers();
            loadDashboardStats();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "❌ Error: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    }

    private void editUser() {
     int row = tblUsers.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a user first!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int userId = (int) modelUsers.getValueAt(row, 0);
    String currentUsername = (String) modelUsers.getValueAt(row, 1);
    String currentRole = (String) modelUsers.getValueAt(row, 2);
    String currentStatus = (String) modelUsers.getValueAt(row, 3);
    
    // ✅ GET CURRENT EMAIL FROM DATABASE
    String currentEmail = "";
    try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        String sql = "SELECT email FROM users WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            currentEmail = rs.getString("email");
        }
        rs.close();
        pstmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));  // ✅ 5 rows
    
    JTextField txtUsername = new JTextField(currentUsername);
    JTextField txtEmail = new JTextField(currentEmail);  // ✅ EMAIL FIELD
    JTextField txtPassword = new JTextField();
    JComboBox<String> cboRole = new JComboBox<>(new String[]{"admin", "contractor", "client"});
    cboRole.setSelectedItem(currentRole.toLowerCase());
    JComboBox<String> cboStatus = new JComboBox<>(new String[]{"approved", "pending"});
    cboStatus.setSelectedItem(currentStatus.toLowerCase());
    
    panel.add(new JLabel("Username:"));
    panel.add(txtUsername);
    panel.add(new JLabel("Email:"));
    panel.add(txtEmail);  // ✅ CORRECT!
    panel.add(new JLabel("New Password (blank = keep):"));
    panel.add(txtPassword);
    panel.add(new JLabel("Role:"));
    panel.add(cboRole);
    panel.add(new JLabel("Status:"));
    panel.add(cboStatus);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String newUsername = txtUsername.getText().trim();
        String newEmail = txtEmail.getText().trim();  // ✅ GET EMAIL
        String newPassword = txtPassword.getText().trim();
        String newRole = (String) cboRole.getSelectedItem();
        String newStatus = (String) cboStatus.getSelectedItem();
        
        if (newUsername.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Email are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ✅ VALIDATE EMAIL
        if (!isValidEmail(newEmail)) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            // Check username (if changed)
            if (!newUsername.equals(currentUsername)) {
                String checkSql = "SELECT COUNT(*) as c FROM users WHERE username = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, newUsername);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt("c") > 0) {
                    JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    checkStmt.close();
                    conn.close();
                    return;
                }
                rs.close();
                checkStmt.close();
            }
            
            // Check email (if changed)
            if (!newEmail.equals(currentEmail)) {
                String checkEmailSql = "SELECT COUNT(*) as c FROM users WHERE email = ?";
                PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql);
                checkEmailStmt.setString(1, newEmail);
                ResultSet rs = checkEmailStmt.executeQuery();
                if (rs.next() && rs.getInt("c") > 0) {
                    JOptionPane.showMessageDialog(this, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    checkEmailStmt.close();
                    conn.close();
                    return;
                }
                rs.close();
                checkEmailStmt.close();
            }
            
            // ✅ UPDATE USER WITH EMAIL
            String sql;
            PreparedStatement pstmt;
            
            if (newPassword.isEmpty()) {
                sql = "UPDATE users SET username = ?, email = ?, role = ?, status = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newUsername);
                pstmt.setString(2, newEmail);  // ✅ EMAIL
                pstmt.setString(3, newRole);
                pstmt.setString(4, newStatus);
                pstmt.setInt(5, userId);
            } else {
                sql = "UPDATE users SET username = ?, email = ?, password = ?, role = ?, status = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newUsername);
                pstmt.setString(2, newEmail);  // ✅ EMAIL
                pstmt.setString(3, newPassword);
                pstmt.setString(4, newRole);
                pstmt.setString(5, newStatus);
                pstmt.setInt(6, userId);
            }
            
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers();
            loadDashboardStats();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void deleteApprovedUser() {
      int row = tblUsers.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a user first!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int userId = (int) modelUsers.getValueAt(row, 0);
    String username = (String) modelUsers.getValueAt(row, 1);
    String role = (String) modelUsers.getValueAt(row, 2);
    
    // Prevent deleting yourself
    if (username.equals(loggedInUsername)) {
        JOptionPane.showMessageDialog(this, "You cannot delete yourself!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Prevent deleting other admins (optional safety)
    if (role.equalsIgnoreCase("admin")) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "WARNING: You are about to delete an ADMIN user!\nThis action cannot be undone!\n\nDelete " + username + "?", 
            "Confirm Delete Admin", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
    }
    
    int conf = JOptionPane.showConfirmDialog(this, 
        "DELETE " + username + "?\n\nThis will also delete:\n- All their projects\n- All their payments\n\nThis cannot be undone!", 
        "Confirm Delete", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE);
        
    if (conf == JOptionPane.YES_OPTION) {
        try {
            config cfg = new config();
            Connection conn = cfg.connectDB();
            
            // Delete related records first (foreign keys)
            // Delete payments related to user's projects
            String sql1 = "DELETE FROM payments WHERE project_id IN (SELECT id FROM projects WHERE client_id = ? OR contractor_id = ?)";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, userId);
            pstmt1.setInt(2, userId);
            pstmt1.executeUpdate();
            pstmt1.close();
            
            // Delete projects
            String sql2 = "DELETE FROM projects WHERE client_id = ? OR contractor_id = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, userId);
            pstmt2.setInt(2, userId);
            pstmt2.executeUpdate();
            pstmt2.close();
            
            // Delete user
            String sql3 = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt3 = conn.prepareStatement(sql3);
            pstmt3.setInt(1, userId);
            pstmt3.executeUpdate();
            pstmt3.close();
            
            conn.close();
            
            JOptionPane.showMessageDialog(this, "User and related data deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers();
            loadDashboardStats();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private boolean isValidEmail(String email) {
       if (email == null || email.trim().isEmpty()) {
        return false;
    }
    
    email = email.trim().toLowerCase();
    
    // Must end with @gmail.com
    if (!email.endsWith("@gmail.com")) {
        return false;
    }
    
    // Must have something before @gmail.com
    String username = email.replace("@gmail.com", "");
    if (username.isEmpty()) {
        return false;
    }
    
    // Check if valid format (user@gmail.com)
    int atCount = email.length() - email.replace("@", "").length();
    if (atCount != 1) {
        return false;
    }
    
    // No spaces allowed
    if (email.contains(" ")) {
        return false;
    }
    
    return true;
}

    private String getProfilePic() {
        try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT profile_pic FROM users WHERE username = ?");
        pstmt.setString(1, loggedInUsername);
        ResultSet rs = pstmt.executeQuery();
        String pic = rs.next() ? 
            (rs.getString("profile_pic") != null ? rs.getString("profile_pic") : "") : "";
        rs.close(); pstmt.close(); conn.close();
        return pic;
    } catch (Exception e) { return ""; }
        
    }

    private void saveProfilePic(String path) {
      try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "UPDATE users SET profile_pic = ? WHERE username = ?");
          String absolutePath = null;
        pstmt.setString(1, absolutePath);  // ✅ use absolutePath
        pstmt.setString(2, loggedInUsername);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        System.out.println("✅ Profile pic saved: " + absolutePath);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private String getEmail() {
         try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT email FROM users WHERE username = ?");
        pstmt.setString(1, loggedInUsername);
        ResultSet rs = pstmt.executeQuery();
        String email = rs.next() ? 
            (rs.getString("email") != null ? rs.getString("email") : "Not set") : "Not set";
        rs.close(); pstmt.close(); conn.close();
        return email;
    } catch (Exception e) { return "Not set"; } //To change body of generated methods, choose Tools | Templates.
    }

    private String getBio() {
         try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT bio FROM users WHERE username = ?");
        pstmt.setString(1, loggedInUsername);
        ResultSet rs = pstmt.executeQuery();
        
        String bio = "";
        if (rs.next()) {
            String val = rs.getString("bio");
            bio = (val != null) ? val : "";
        }
        
        rs.close();
        pstmt.close();
        conn.close();
        return bio;
        
    } catch (Exception e) {
        e.printStackTrace();
        return "";
    }
}

   private void saveBio(String bioText) {  // ✅ renamed to bioText
    try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "UPDATE users SET bio = ? WHERE username = ?");
        pstmt.setString(1, bioText);  // ✅ use bioText
        pstmt.setString(2, loggedInUsername);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        System.out.println("✅ Bio saved: " + bioText);
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    private void addDefaultAvatar(JPanel avatar) {
          JLabel avatarLbl = new JLabel("👤", SwingConstants.CENTER);
    avatarLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    avatarLbl.setForeground(Color.WHITE);
    avatar.add(avatarLbl); //To change body of generated methods, choose Tools | Templates.
    }


    
    private boolean verifyCurrentPassword(String password) {
    try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "SELECT id FROM users WHERE username = ? AND password = ?");
        pstmt.setString(1, loggedInUsername);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        boolean valid = rs.next();
        rs.close();
        pstmt.close();
        conn.close();
        return valid;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// ✅ CHANGE PASSWORD
private boolean changePassword(String newPassword) {
    try {
        config cfg = new config();
        Connection conn = cfg.connectDB();
        PreparedStatement pstmt = conn.prepareStatement(
            "UPDATE users SET password = ? WHERE username = ?");
        pstmt.setString(1, newPassword);
        pstmt.setString(2, loggedInUsername);
        int rows = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        System.out.println("✅ Password changed! Rows: " + rows);
        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}


