package ui;


import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CourseRegistrationGUI extends JFrame {
    private JTextField nameField;
    private JTextField studentIdField;
    private JPanel loginTitlePanel;
    private JPanel loginPanel;
    private JPanel loginButtonPanel;
    private JLabel statusLabel;
    private JButton btnYes;
    private JButton btnNo;
    private JPanel backgroundPanel;
    private JMenu menu;
    private JScrollPane scrollPane;

    // field for saving and loading
    private static final String JSON_STORE = "./data/student.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Student userStudent;


    // Show the login page of the course registration system
    public CourseRegistrationGUI() {
        super("Course Registration System");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener();
        setupBackground();
        setResizable(true);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds WindowListener with a specific implementation of windowClosing to this
    private void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to save the current"
                                + " course work list and registered list?", "Save or not",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    saveBothLists(); // Save the lists
                    dispose(); // Close the application
                } else if (result == JOptionPane.NO_OPTION) {
                    dispose(); // Close the application without saving
                } // else if result is CANCEL_OPTION, do nothing, the window will not close
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: saves the course work list and registered list to the file.
    private void saveBothLists() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(userStudent);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file " + JSON_STORE,
                    "Fail to save", JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts the user entry the number of tables (the size of that ArrayList<Table>) or load a state
    //          sets up myRestaurant according to user's choice
    //          initials and set up all elements of the JFrame window
    private void setupBackground() {
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.white);
        backgroundPanel.setPreferredSize(new Dimension(600, 300));
        add(backgroundPanel);
        backgroundPanel.setLayout(null);
        addBackGround();
        loadHistory();
    }


    // EFFECTS: adds the background of panel
    private void addBackGround() {
        try {
            BufferedImage originalBackground = ImageIO.read(new File("./images/UBC_image.png"));
            BufferedImage resizedBackground = resize(originalBackground, 100, 100);
            ImageIcon feastAppImage = new ImageIcon(resizedBackground);
            JLabel imageLabel = new JLabel(feastAppImage);
            imageLabel.setBounds(120, 30, 300, 200);
            backgroundPanel.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to find the image " + "./images",
                    "Lost Image", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: a helper to help resize an BufferedImage, and returns the resized image.
    public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    // MODIFIES: this
    // EFFECTS: shows the login interface.
    private void login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createLoginPanel(), BorderLayout.CENTER);
        add(createButtonPanel(null), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        String name = nameField.getText();
        int id = Integer.parseInt(studentIdField.getText());
        userStudent = new Student(name, id);
        remove(loginTitlePanel);
        remove(loginPanel);
        remove(loginButtonPanel);

    }

    // EFFECTS: prompts the user to the homepage, creates a bar to make it more elaborated.
    private void homepage(String name, int id) {
        remove(loginButtonPanel);
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(0, 51, 102));
        topBarPanel.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(topBarPanel, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Student Service Centre");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel userInfoLabel = new JLabel("Name:  " + name + "      Student #:  " + id);
        userInfoLabel.setForeground(Color.WHITE); // White text
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topBarPanel.add(titleLabel, BorderLayout.WEST);
        topBarPanel.add(userInfoLabel, BorderLayout.EAST);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        getContentPane().add(topBarPanel, BorderLayout.NORTH);
        setVisible(true);
        topBarPanel.revalidate();
        topBarPanel.repaint();
        comboBoxOfTwoList();
    }

    // EFFECTS: creates a combo box for course workList and registered list.
    private void comboBoxOfTwoList() {
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        String[] options = {"Course Worklist", "Registered List"};
        JComboBox<String> listComboBox = new JComboBox<>(options);
        createsListerForBothList(listComboBox);
        add(listComboBox, BorderLayout.SOUTH);
    }

    // EFFECTS: creates the listener for the course workList and registered list
    private void  createsListerForBothList(JComboBox<String> listComboBox) {
        listComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedList = (String) cb.getSelectedItem();
                if ("Course Worklist".equals(selectedList)) {
                    cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showWorkListMenuBar();
                        }
                    });
                } else if ("Registered List".equals(selectedList)) {
                    cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showWorkListMenuBar();
                        }
                    });
                }
            }
        });
    }



    // EFFECTS: constructs and shows the menu bar of the course Work List
    private void showWorkListMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Options");
        addCourseListener();
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // EFFECTS: creates the listener for the button "Add courses"
    private void addCourseListener() {
        JMenuItem addCourseItem = new JMenuItem("Add courses");
        addCourseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseWorklist();
            }
        });
        menu.add(addCourseItem);
    }

//    // EFFECTS: add the listener to comboBox
//    private addListenerToComboBox() {
//        listComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JComboBox cb = (JComboBox) e.getSource();
//                String selectedList = (String) cb.getSelectedItem();
//                if ("Course Worklist".equals(selectedList)) {
//                    showList(courseWorklistData);
//                } else if ("Registered List".equals(selectedList)) {
//                    showList(registeredListData);
//                }
//            }
//        });
//
//        // Add the combo box to the frame (e.g., at the NORTH location)
//        this.add(listComboBox, BorderLayout.NORTH);
//
//        // Initialize the content panel and add it to the frame
//        contentPanel = new JPanel();
//        this.add(contentPanel, BorderLayout.CENTER);
//
//        // Start by showing the course worklist
//        showList(courseWorklistData);
//    }


//    // EFFECTS:
//    private void menuBar() {
//        JMenuBar menuBar = new JMenuBar();
//        menu = new JMenu("Options");
//        courseWorkListListener();
//        courseRegisteredListener();
//        menuBar.add(menu);
//        frame.setJMenuBar(menuBar);
//        frame.setSize(1000, 600);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//    }

//    // EFFECTS: creates the listener for the course workList "item"
//    private void courseWorkListListener() {
//        JMenuItem courseWorkListItem = new JMenuItem("Course WorkList");
//        courseWorkListItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showCourseWorklist();
//            }
//        });
//        menu.add(courseWorkListItem);
//    }
//
//    // EFFECTS: creates the listener for the course registered "item"
//    private void courseRegisteredListener() {
//
//        JMenuItem registeredListItem = new JMenuItem("Registered List");
//        registeredListItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showRegisteredList();
//            }
//        });
//        menu.add(registeredListItem);
//    }


    private void showCourseWorklist() {
        String[] columnNames = {"Subject", "Course No.", "Section No."};

        // Sample data, you would replace this with your actual course worklist data
        Object[][] data = {
                {"CONS 127", "Lecture", "1"},
                {"CPSC 210", "Lecture", "1"}
        };

        // Create the table with the data and column names
        JTable table = new JTable(data, columnNames);

        // Improve table appearance and functionality
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add the table to a scroll pane in case the data exceeds the visible area
        scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame (or a panel within the frame)
        add(scrollPane, BorderLayout.CENTER);

        // Refresh the frame to show the new table
        revalidate();
        repaint();
    }

    private void showRegisteredList() {
        String[] columnNames = {"Subject", "Course No.", "Section No."};

        // Sample data, you would replace this with your actual course worklist data
        Object[][] data = {
                {"CPSC 110", "Lecture", "1"},
                {"CPSC 121", "Lecture", "1"}
        };

        // Create the table with the data and column names
        JTable table = new JTable(data, columnNames);

        // Improve table appearance and functionality
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add the table to a scroll pane in case the data exceeds the visible area
        scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame (or a panel within the frame)
        add(scrollPane, BorderLayout.CENTER);

        // Refresh the frame to show the new table
        revalidate();
        repaint();
    }


    // EFFECTS: creates the title panel.
    private JPanel createTitlePanel() {
        loginTitlePanel = new JPanel();
        return loginTitlePanel;
    }

    // EFFECTS: creates the login panel.
    private JPanel createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        loginPanel.add(new JLabel("Name"));
        nameField = new JTextField(15); // Initialize the name field
        loginPanel.add(nameField);
        loginPanel.add(new JLabel("Student ID"));
        studentIdField = new JTextField(15); // Initialize the student ID field
        loginPanel.add(studentIdField);
        return loginPanel;
    }

    // EFFECTS: creates the button "Login" with its own listener.
    private JPanel createButtonPanel(JFrame frame) {
        loginButtonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        addLoginListener(loginButton);
        loginButtonPanel.add(loginButton);
        return loginButtonPanel;
    }

    private void addLoginListener(JButton loginButton) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String studentIdStr = studentIdField.getText();
                try {
                    int studentId = Integer.parseInt(studentIdStr);
                    remove(loginPanel);
                    homepage(name, studentId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid student ID (numbers only).",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: prompts the user to choose whether they want to load the history data.
    private void loadHistory() {
        statusLabel = new JLabel("Do you want to load the previous course work list and the registered "
                + "list from the file?", SwingConstants.CENTER);
        btnYes = new JButton("For sure!");
        btnNo = new JButton("Start a new one!");
        btnYes.setBounds(100, 190, 150, 40);
        btnNo.setBounds(280, 190, 150, 40);
        statusLabel.setBounds(-20, 50, 600, 40);
        backgroundPanel.add(statusLabel, BorderLayout.CENTER);
        backgroundPanel.add(btnYes);
        backgroundPanel.add(btnNo);
        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBothList();
            }
        });
        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(backgroundPanel);
                login();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads the course work list and registered list from the file
    private void loadBothList() {
        remove(backgroundPanel);
        try {
            jsonReader = new JsonReader(JSON_STORE);
            userStudent = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to load from file " + JSON_STORE,
                    "Fail to load", JOptionPane.ERROR_MESSAGE);
        }

    }

    // EFFECTS: run the course registration GUI
    public static void main(String[] args) {
        new CourseRegistrationGUI();
    }

}

