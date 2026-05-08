package com.reportcard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReportCardGUI extends JFrame {
    private JTextField idField, nameField, subjectField, marksField;
    private JTextArea reportArea;
    private JButton addSubjectButton, generateButton, clearButton;
    private JTable subjectsTable;
    private DefaultTableModel tableModel;
    private List<Subject> subjects = new ArrayList<>();

    public ReportCardGUI() {
        setTitle("Report Card Generator");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("📋 Report Card Generator");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Panel (Input + Table)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 245));

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Student Information", 0, 0, new Font("Arial", Font.BOLD, 12)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: ID and Name
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createStyledLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        idField = createStyledTextField();
        inputPanel.add(idField, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(createStyledLabel("Student Name:"), gbc);
        gbc.gridx = 3;
        nameField = createStyledTextField();
        inputPanel.add(nameField, gbc);

        // Row 2: Subject and Marks
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(createStyledLabel("Subject Name:"), gbc);
        gbc.gridx = 1;
        subjectField = createStyledTextField();
        inputPanel.add(subjectField, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(createStyledLabel("Marks (0-100):"), gbc);
        gbc.gridx = 3;
        marksField = createStyledTextField();
        inputPanel.add(marksField, gbc);

        // Row 3: Add Subject Button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        addSubjectButton = createStyledButton("➕ Add Subject");
        inputPanel.add(addSubjectButton, gbc);

        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // Subjects Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Added Subjects", 0, 0, new Font("Arial", Font.BOLD, 12)));

        tableModel = new DefaultTableModel(new String[]{"Subject", "Marks"}, 0);
        subjectsTable = new JTable(tableModel);
        subjectsTable.setRowHeight(25);
        subjectsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        subjectsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableScroll = new JScrollPane(subjectsTable);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        centerPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.WEST);

        // Report Area Panel
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBackground(Color.WHITE);
        reportPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Report Card", 0, 0, new Font("Arial", Font.BOLD, 12)));

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        reportArea.setBackground(new Color(250, 250, 250));
        reportArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane reportScroll = new JScrollPane(reportArea);
        reportPanel.add(reportScroll, BorderLayout.CENTER);

        mainPanel.add(reportPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        generateButton = createStyledButton("📊 Generate Report");
        clearButton = createStyledButton("🗑️ Clear All");
        
        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event Listeners
        addSubjectButton.addActionListener(e -> addSubject());
        generateButton.addActionListener(e -> generateReport());
        clearButton.addActionListener(e -> clearAll());
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(12);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addSubject() {
        String subjName = subjectField.getText().trim();
        String marksStr = marksField.getText().trim();
        
        if (subjName.isEmpty() || marksStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both subject name and marks!", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int marks = Integer.parseInt(marksStr);
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Subject subject = new Subject(subjName, marks);
            subjects.add(subject);
            tableModel.addRow(new Object[]{subjName, marks});
            
            subjectField.setText("");
            marksField.setText("");
            subjectField.requestFocus();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Marks must be a valid number!", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateReport() {
        String idStr = idField.getText().trim();
        String name = nameField.getText().trim();
        
        if (idStr.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID and Name!", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (subjects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one subject!", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            Student student = new Student(id, name, subjects);
            ReportCard reportCard = new ReportCard(student);
            reportArea.setText(reportCard.generateReport());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID!", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearAll() {
        idField.setText("");
        nameField.setText("");
        subjectField.setText("");
        marksField.setText("");
        subjects.clear();
        tableModel.setRowCount(0);
        reportArea.setText("");
        JOptionPane.showMessageDialog(this, "✓ All data cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReportCardGUI().setVisible(true);
        });
    }
}