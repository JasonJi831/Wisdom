//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class Chat {
//    private JFrame frame;
//    private JTextArea courseListArea;
//    private JTextField courseField;
//
//    // Constructor to initialize the GUI
//    public Chat() {
//        initializeUI();
//    }
//
//    // Method to set up the main GUI components
//    private void initializeUI() {
//        frame = new JFrame("Course Registration System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setLayout(new BorderLayout());
//
//        setupMenu();
//        setupInputForm();
//        setupCourseDisplay();
//
//        frame.setVisible(true);
//    }
//
//    // Method to set up the menu bar
//    private void setupMenu() {
//        JMenuBar menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//        JMenuItem loadItem = new JMenuItem("Load");
//        JMenuItem saveItem = new JMenuItem("Save");
//
//        loadItem.addActionListener(e -> loadCourses());
//        saveItem.addActionListener(e -> saveCourses());
//
//        fileMenu.add(loadItem);
//        fileMenu.add(saveItem);
//        menuBar.add(fileMenu);
//        frame.setJMenuBar(menuBar);
//    }
//
//    // Placeholder for loading courses functionality
//    private void loadCourses() {
//        // Load courses logic
//    }
//
//    // Placeholder for saving courses functionality
//    private void saveCourses() {
//        // Save courses logic
//    }
//
//    // Method to set up the input form for adding courses
//    private void setupInputForm() {
//        JPanel inputPanel = new JPanel();
//        courseField = new JTextField(20);
//        JButton addButton = new JButton("Add Course");
//
//        addButton.addActionListener(e -> addCourse(courseField.getText()));
//        inputPanel.add(courseField);
//        inputPanel.add(addButton);
//
//        frame.add(inputPanel, BorderLayout.SOUTH);
//    }
//
//    // Placeholder for adding a course
//    private void addCourse(String courseName) {
//        // Add course logic
//        // For demonstration, just appending the course name to the list
//        updateCourseList(courseName);
//    }
//
//    // Method to set up the area for displaying course information
//    private void setupCourseDisplay() {
//        courseListArea = new JTextArea();
//        courseListArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(courseListArea);
//        frame.add(scrollPane, BorderLayout.CENTER);
//    }
//
//    // Method to update the course list display
//    private void updateCourseList(String courseInfo) {
//        courseListArea.append(courseInfo + "\n");
//    }
//
//    // Main method to run the application
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new Chat());
//    }
//}
