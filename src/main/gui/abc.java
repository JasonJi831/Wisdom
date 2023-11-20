//package gui;
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.net.URL;
//
//import model.Student;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//public class CourseRegistrationGUI {
//    private JFrame frame;
//    private JPanel mainPanel;
//    private JButton btnLoadData;
//    private JButton btnNewStudent;
//    private JButton btnWorkList;
//    private JButton btnRegisteredList;
//    private JButton btnSaveData;
//    private JButton btnYes;
//    private JButton btnNo;
//    private JLabel statusLabel;
//    private ImageIcon greenLightImage;
//    private JLabel imageAsLabel;
//    private Student userStudent;
//    private static final String JSON_STORE = "./data/student.json";
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//    private q courseRegistrationGUI;
//
//
//
//    public CourseRegistrationGUI() {
//
//        frame = new JFrame("Course Registration System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 200);
//
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(5, 1));
//
//
//        private static JPanel createTitlePanel() {
//            JPanel panel = new JPanel();
//            panel.add(new JLabel("BCS Schedule Sharer"));
//            return panel;
//        }
//
//        private static JPanel createLoginPanel() {
//            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
//            panel.add(new JLabel("Username"));
//            panel.add(new JTextField(15));
//            panel.add(new JLabel("Password"));
//            panel.add(new JPasswordField(15));
//            return panel;
//        }
//
//        private static JPanel createButtonPanel(JFrame frame) {
//            JPanel panel = new JPanel();
//            JButton loginButton = new JButton("Login");
//            JButton registerButton = new JButton("Register");
//            panel.add(loginButton);
//            panel.add(registerButton);
//            return panel;
//        }
//
//
//        // Create and set up the window
//        frame = new JFrame("Course Registration System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//
//
//        // Set background image
////        setBackGroundImage("background.jpg");
//
//        // Create and set up the content pane
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new GridLayout(5, 1));
//
//        userLogin();
//
//        // Initialize components
//        whetherLoad();
//
//        // Add components to panel
//        addComponentsToPanel();
//
//        // Add panel to frame
//        frame.add(mainPanel, BorderLayout.CENTER);
//
//        // Display the window
//        frame.setVisible(true);
//    }
//
//    // EFFECTS: shows the login interface for a user.
//    private void userLogin() {
//
//    }
//
//    private void setBackGroundImage(String fileName) {
////        greenLightImage = new ImageIcon(fileName);
////        imageAsLabel = new JLabel(greenLightImage);
////        mainPanel.add(imageAsLabel);
//
//        URL imageUrl = getClass().getResource(fileName);
//        // Load the image to an ImageIcon
//        ImageIcon imageIcon = new ImageIcon(fileName);
//
//        // Scale the image to fit the JFrame's size
//        Image scaledImage = imageIcon.getImage().getScaledInstance(frame.getWidth(),
//                frame.getHeight(), Image.SCALE_SMOOTH);
//
//        imageIcon = new ImageIcon(scaledImage);
//
//        // Create a JLabel that will hold the background image
//        JLabel background = new JLabel(imageIcon);
//
//        // Set the layout manager for the background label
//        background.setLayout(new BorderLayout());
//
//        // Set the background label as the content pane of the frame
//        // This will overwrite any previous set content pane
//        frame.setContentPane(background);
//        frame.validate();
//    }
//
//    // EFFECTS: prompts the user to choose whether they want to load the history data.
//    private void whetherLoad() {
//
//        statusLabel = new JLabel("Do you want to load the course work list and the registered list from the file?",
//                SwingConstants.CENTER);
//        btnYes = new JButton("For sure!");
//        btnNo = new JButton("Start a new one!");
//
//        // Add an action listener to the button
//        btnYes.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // This code will be executed when the "Yes" button is clicked
//                loadBothList();
//            }
//        });
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads the course work list and registered list from the file
//    private void loadBothList() {
//        try {
//            userStudent = jsonReader.read();
//            System.out.println("Loaded " + userStudent.getName() + " 's work list from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//
//    }
//
////        btnLoadData = new JButton("Load Data");
////        btnNewStudent = new JButton("New Student");
////        btnWorkList = new JButton("Course Work List");
////        btnRegisteredList = new JButton("Registered Courses List");
////        btnSaveData = new JButton("Save Data");
//
//
////        // Add action listeners
////        btnLoadData.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                // Implement loading data logic
////                statusLabel.setText("Data Loaded");
////            }
////        });
////
////        btnNewStudent.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                // Implement new student creation logic
////                statusLabel.setText("New Student Created");
////            }
////        });
//
//    // ... Similarly for other buttons
//
//
//    private void addComponentsToPanel() {
////        mainPanel.add(btnLoadData);
////        mainPanel.add(btnNewStudent);
////        mainPanel.add(btnWorkList);
////        mainPanel.add(btnRegisteredList);
////        mainPanel.add(btnSaveData);
//        mainPanel.add(statusLabel);
//        mainPanel.add(btnYes);
//        mainPanel.add(btnNo);
//    }
//
//
//
//    // EFFECTS: call the course registration GUI
//    public static void main(String[] args) {
//        new CourseRegistrationGUI();
//    }
//}
