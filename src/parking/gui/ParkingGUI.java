package parking.gui;

import parking.core.Vehicle;
import parking.core.ParkingSession;
import parking.management.ParkingLot;
import parking.patterns.facade.ParkingSystemFacade;
import parking.patterns.factory.VehicleType;
import parking.patterns.builder.ParkingReport;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

/**
 * Modern GUI for Smart Parking System
 * Clean, professional design with consistent sizing
 */
public class ParkingGUI extends JFrame {
    
    // Colors - Modern palette
    private static final Color PRIMARY = new Color(41, 128, 185);      // Blue
    private static final Color PRIMARY_DARK = new Color(31, 97, 141);  // Dark Blue
    private static final Color SUCCESS = new Color(39, 174, 96);       // Green
    private static final Color WARNING = new Color(243, 156, 18);      // Orange
    private static final Color DANGER = new Color(231, 76, 60);        // Red
    private static final Color BG_DARK = new Color(44, 62, 80);        // Dark background
    private static final Color BG_LIGHT = new Color(236, 240, 241);    // Light background
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_LIGHT = new Color(255, 255, 255);
    private static final Color CARD_BG = new Color(255, 255, 255);
    
    // Components
    private ParkingSystemFacade facade;
    private ParkingLot parkingLot;
    private JLabel availableLabel;
    private JLabel occupiedLabel;
    private JLabel revenueLabel;
    private JTable vehicleTable;
    private DefaultTableModel tableModel;
    private JTextArea logArea;
    private double totalRevenue = 0;
    
    public ParkingGUI() {
        facade = new ParkingSystemFacade();
        parkingLot = ParkingLot.getInstance();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Smart Parking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
        
        // Main container with dark sidebar
        JPanel mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(BG_LIGHT);
        
        // Left sidebar
        mainContainer.add(createSidebar(), BorderLayout.WEST);
        
        // Main content area
        mainContainer.add(createMainContent(), BorderLayout.CENTER);
        
        setContentPane(mainContainer);
        refreshData();
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_DARK);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));
        
        // Logo/Title
        JLabel titleLabel = new JLabel("PARKING");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(149, 165, 166));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(subtitleLabel);
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Menu buttons
        String[] menuItems = {
            "Vehicle Entry",
            "Vehicle Exit", 
            "View Vehicles",
            "Generate Report",
            "Refresh Data"
        };
        
        Color[] buttonColors = {SUCCESS, DANGER, PRIMARY, WARNING, PRIMARY_DARK};
        ActionListener[] actions = {
            e -> showEntryDialog(),
            e -> showExitDialog(),
            e -> showVehiclesDialog(),
            e -> showReport(),
            e -> refreshData()
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton btn = createMenuButton(menuItems[i], buttonColors[i]);
            final int index = i;
            btn.addActionListener(actions[index]);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        // Exit button at bottom
        JButton exitBtn = createMenuButton("Exit Application", DANGER);
        exitBtn.addActionListener(e -> System.exit(0));
        sidebar.add(exitBtn);
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(TEXT_LIGHT);
        button.setBackground(bgColor);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(190, 45));
        button.setMinimumSize(new Dimension(190, 45));
        button.setPreferredSize(new Dimension(190, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        Color hoverColor = bgColor.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBackground(BG_LIGHT);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Top - Statistics cards
        content.add(createStatsPanel(), BorderLayout.NORTH);
        
        // Center - Table and Log
        content.add(createCenterPanel(), BorderLayout.CENTER);
        
        return content;
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(BG_LIGHT);
        statsPanel.setPreferredSize(new Dimension(0, 120));
        
        // Available spots card
        availableLabel = new JLabel("0", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Available Spots", availableLabel, SUCCESS));
        
        // Occupied spots card  
        occupiedLabel = new JLabel("0", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Occupied Spots", occupiedLabel, WARNING));
        
        // Revenue card
        revenueLabel = new JLabel("0.00 ILS", SwingConstants.CENTER);
        statsPanel.add(createStatCard("Total Revenue", revenueLabel, PRIMARY));
        
        return statsPanel;
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        // Accent bar at top
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setPreferredSize(new Dimension(0, 4));
        card.add(accentBar, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));
        
        // Value
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(TEXT_DARK);
        
        JPanel textPanel = new JPanel(new BorderLayout(0, 5));
        textPanel.setBackground(CARD_BG);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BG_LIGHT);
        
        // Table panel
        JPanel tablePanel = createTablePanel();
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Log panel
        JPanel logPanel = createLogPanel();
        centerPanel.add(logPanel, BorderLayout.SOUTH);
        
        return centerPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel header = new JLabel("Parked Vehicles");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(TEXT_DARK);
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(header, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"License Plate", "Vehicle Type", "Entry Time", "Spot #"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vehicleTable = new JTable(tableModel);
        vehicleTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        vehicleTable.setRowHeight(35);
        vehicleTable.setShowGrid(false);
        vehicleTable.setIntercellSpacing(new Dimension(0, 0));
        vehicleTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        vehicleTable.getTableHeader().setBackground(BG_LIGHT);
        vehicleTable.getTableHeader().setForeground(TEXT_DARK);
        vehicleTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Alternating row colors
        vehicleTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                ((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panel.setPreferredSize(new Dimension(0, 120));
        
        JLabel header = new JLabel("Activity Log");
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(TEXT_DARK);
        header.setBorder(new EmptyBorder(0, 0, 5, 0));
        panel.add(header, BorderLayout.NORTH);
        
        logArea = new JTextArea();
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setEditable(false);
        logArea.setBackground(new Color(248, 249, 250));
        logArea.setForeground(TEXT_DARK);
        logArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void showEntryDialog() {
        JDialog dialog = createDialog("Vehicle Entry", 400, 250);
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // License plate
        gbc.gridx = 0; gbc.gridy = 0;
        content.add(createLabel("License Plate:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        JTextField plateField = createTextField();
        content.add(plateField, gbc);
        
        // Vehicle type
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        content.add(createLabel("Vehicle Type:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        JComboBox<VehicleType> typeCombo = new JComboBox<>(VehicleType.values());
        typeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeCombo.setPreferredSize(new Dimension(0, 35));
        content.add(typeCombo, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton confirmBtn = createActionButton("Confirm Entry", SUCCESS);
        JButton cancelBtn = createActionButton("Cancel", new Color(149, 165, 166));
        
        confirmBtn.addActionListener(e -> {
            String plate = plateField.getText().trim().toUpperCase();
            if (plate.isEmpty()) {
                showError("Please enter a license plate");
                return;
            }
            
            VehicleType type = (VehicleType) typeCombo.getSelectedItem();
            Vehicle vehicle = facade.createVehicle(type, plate, "GUI User", false, "Unknown");
            boolean success = facade.parkVehicle(vehicle);
            
            if (success) {
                log("ENTRY: " + plate + " (" + type + ")");
                refreshData();
                dialog.dispose();
            } else {
                showError("Entry failed. Parking may be full or vehicle already parked.");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        content.add(buttonPanel, gbc);
        
        dialog.add(content);
        dialog.setVisible(true);
    }
    
    private void showExitDialog() {
        JDialog dialog = createDialog("Vehicle Exit", 400, 200);
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // License plate
        gbc.gridx = 0; gbc.gridy = 0;
        content.add(createLabel("License Plate:"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        JTextField plateField = createTextField();
        content.add(plateField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton confirmBtn = createActionButton("Confirm Exit", DANGER);
        JButton cancelBtn = createActionButton("Cancel", new Color(149, 165, 166));
        
        confirmBtn.addActionListener(e -> {
            String plate = plateField.getText().trim().toUpperCase();
            if (plate.isEmpty()) {
                showError("Please enter a license plate");
                return;
            }
            
            double fee = facade.removeVehicle(plate);
            
            if (fee >= 0) {
                totalRevenue += fee;
                log("EXIT: " + plate + " - Fee: " + String.format("%.2f", fee) + " ILS");
                JOptionPane.showMessageDialog(this,
                    "Vehicle: " + plate + "\nParking Fee: " + String.format("%.2f", fee) + " ILS",
                    "Exit Complete",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                dialog.dispose();
            } else {
                showError("Vehicle not found: " + plate);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        content.add(buttonPanel, gbc);
        
        dialog.add(content);
        dialog.setVisible(true);
    }
    
    private void showVehiclesDialog() {
        JDialog dialog = createDialog("All Parked Vehicles", 600, 400);
        
        List<ParkingSession> sessions = parkingLot.getCurrentSessions();
        
        String[] columns = {"License Plate", "Type", "Color", "Entry Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (ParkingSession s : sessions) {
            Vehicle v = s.getVehicle();
            String entryTime = v.getEntryTime() != null ? v.getEntryTime().format(formatter) : "N/A";
            model.addRow(new Object[]{
                v.getLicensePlate(),
                v.getClass().getSimpleName(),
                v.getColor(),
                entryTime
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }
    
    private void showReport() {
        ParkingReport report = facade.generateDailyReport();
        
        StringBuilder sb = new StringBuilder();
        sb.append("========== PARKING REPORT ==========\n\n");
        sb.append(report.generateReport());
        
        JDialog dialog = createDialog("Parking Report", 500, 400);
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        dialog.add(new JScrollPane(textArea));
        dialog.setVisible(true);
        
        log("Report generated successfully");
    }
    
    private void refreshData() {
        // Update statistics
        int available = parkingLot.getAvailableSpots();
        int occupied = parkingLot.getOccupiedSpots();
        
        availableLabel.setText(String.valueOf(available));
        occupiedLabel.setText(String.valueOf(occupied));
        revenueLabel.setText(String.format("%.2f ILS", totalRevenue));
        
        // Update table
        tableModel.setRowCount(0);
        List<ParkingSession> sessions = parkingLot.getCurrentSessions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        int spotNum = 1;
        for (ParkingSession s : sessions) {
            Vehicle v = s.getVehicle();
            String entryTime = v.getEntryTime() != null ? v.getEntryTime().format(formatter) : "N/A";
            tableModel.addRow(new Object[]{
                v.getLicensePlate(),
                v.getClass().getSimpleName(),
                entryTime,
                spotNum++
            });
        }
        
        log("Data refreshed - " + sessions.size() + " vehicles parked");
    }
    
    private JDialog createDialog(String title, int width, int height) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        return dialog;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(130, 38));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void log(String message) {
        String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
        logArea.append("[" + timestamp + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ParkingGUI gui = new ParkingGUI();
            gui.setVisible(true);
        });
    }
}
