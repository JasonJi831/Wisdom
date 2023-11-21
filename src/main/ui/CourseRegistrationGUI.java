package ui;


import model.Course;
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
import java.util.List;
import java.util.stream.Collectors;

public class CourseRegistrationGUI extends JFrame {
    private JTextField nameField;
    private JTextField studentIdField;
    private JTextField courseNumberField;
    private JTextField subjectField;
    private JTextField sectionField;
    private JTextField subjectFieldSection;
    private JTextField courseNumberFieldSection;
    private JTextField deleteSubjectField;
    private JTextField deleteCourseNumberField;
    private JTextField deleteSectionField;

    private JPanel loginPanel;
    private JPanel loginButtonPanel;
    private JLabel statusLabel;
    private JPanel backgroundPanel;
    private JMenu menu;
    private JScrollPane scrollPane;
    private JPanel infoPanel;
    private JPanel topBarPanel;
    private JPanel buttonPanel;

    // field for saving and loading
    private static final String JSON_STORE = "./data/student.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Student userStudent;


    // EFFECTS: Show the login page of the course registration system
    public CourseRegistrationGUI() {
        super("Course Registration System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener();
        setupBackground();
        initializeInfoPanel();
        setResizable(true);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds an infoPanel with no data to this JFrame
    private void initializeInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(600, 300));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        String[][] emptyArray = {};
        showCourseInWorkList(infoPanel, emptyArray);
        backgroundPanel.add(infoPanel);

    }

    // MODIFIES: this
    // EFFECTS: adds WindowListener with a specific implementation of windowClosing to this
    private void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you want to save the current" + " course work list and registered list?",
                        "Save or not", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    saveBothLists();
                    dispose();
                } else if (result == JOptionPane.NO_OPTION) {
                    dispose();
                }
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
    // EFFECTS: initials and set up all elements of the JFrame window
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
        add(createLoginPanel(), BorderLayout.CENTER);
        add(createButtonPanel(null), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: prompts the user to the homepage, creates a bar to make it more elaborated.
    private void homepage(String name, int id) {
        topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(0, 51, 102));
        topBarPanel.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(topBarPanel, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Student Course Registration System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel userInfoLabel = new JLabel("Name:  " + name + "      Student #:  " + id);
        userInfoLabel.setForeground(Color.WHITE); // White text
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topBarPanel.add(titleLabel, BorderLayout.WEST);
        topBarPanel.add(userInfoLabel, BorderLayout.EAST);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(topBarPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        setVisible(true);
        topBarPanel.revalidate();
        topBarPanel.repaint();
        menuBar();
    }

    // EFFECTS: add three buttons that can prompt the user to add courses or add sections the course workList.
    private void buttonsForWorkList() {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton addCoursesButton = new JButton("add courses");
        JButton addSectionsButton = new JButton("add a section");
        JButton deleteButton = new JButton("delete course sections");
        Dimension buttonSize = new Dimension(240, 86);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        addCoursesButton.setPreferredSize(buttonSize);
        addSectionsButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        addCoursesButton.setFont(buttonFont);
        addSectionsButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        buttonPanel.add(addCoursesButton);
        buttonPanel.add(addSectionsButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.revalidate();
        buttonPanel.repaint();
        addListenerToAddCoursesButton(addCoursesButton);
        addListenerToAddSectionsButton(addSectionsButton);
        addListenerToDeleteCoursesButton(deleteButton);

    }

    // EFFECTS: add two buttons that can prompt the user to add courses or add sections the reg workList.
    private void buttonsForRegList() {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton registerCoursesButton = new JButton("register courses");
        JButton dropCoursesButton = new JButton("drop courses");
        Dimension buttonSize = new Dimension(240, 86);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        registerCoursesButton.setPreferredSize(buttonSize);
        dropCoursesButton.setPreferredSize(buttonSize);
        registerCoursesButton.setFont(buttonFont);
        dropCoursesButton.setFont(buttonFont);
        buttonPanel.add(registerCoursesButton);
        buttonPanel.add(dropCoursesButton);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.revalidate();
        buttonPanel.repaint();
        addListenerToRegisterCoursesButton(registerCoursesButton);
        addListenerToDropCoursesButton(dropCoursesButton);
    }

    // EFFECTS: add a listener to the button "register courses"
    private void addListenerToRegisterCoursesButton(JButton registerCoursesButton) {
        registerCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                CoursesInWorkListPanel();
            }
        });
    }

    // EFFECTS: add a listener to the button "drop courses"
    private void addListenerToDropCoursesButton(JButton dropCoursesButton) {
        dropCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                addCoursesInWorkListPanel();
            }
        });
    }


    // EFFECTS: add a listener to the button "add courses"
    private void addListenerToDeleteCoursesButton(JButton acButton) {
        acButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCoursesInWorkListPanel();
            }
        });
    }


    // EFFECTS: add a listener to the button "add courses"
    private void addListenerToAddCoursesButton(JButton acButton) {
        acButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCoursesInWorkListPanel();
            }
        });
    }

    // EFFECTS: add a listener to the button "add sections"
    private void addListenerToAddSectionsButton(JButton asButton) {
        asButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSectionsInWorkListPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: shows a panel that can let the user type in course subject, course number and section number of a course
    // the user wants to delete from the course workList.
    private void deleteCoursesInWorkListPanel() {
        JPanel addACoursePanel = new JPanel();
        addACoursePanel.setLayout(new GridLayout(3, 2));
        JLabel subjectLabel = new JLabel("Course Subject: ");
        deleteSubjectField = new JTextField(20);
        addACoursePanel.add(subjectLabel);
        addACoursePanel.add(deleteSubjectField);
        JLabel courseNumberLabel = new JLabel("Course Number: ");
        deleteCourseNumberField = new JTextField(20);
        addACoursePanel.add(courseNumberLabel);
        addACoursePanel.add(deleteCourseNumberField);
        JLabel sectionLabel = new JLabel("Section Number: ");
        deleteSectionField = new JTextField(20);
        addACoursePanel.add(sectionLabel);
        addACoursePanel.add(deleteSectionField);
        int result = JOptionPane.showConfirmDialog(null, addACoursePanel,
                "Delete a course section", JOptionPane.OK_CANCEL_OPTION);
        deleteSectionsSaving(result);

    }

    // MODIFIES: this
    // EFFECTS: shows a panel that can let the user type in the course subject and course number.
    private void addCoursesInWorkListPanel() {
        JPanel addACoursePanel = new JPanel();
        addACoursePanel.setLayout(new GridLayout(2, 2));
        JLabel subjectLabel = new JLabel("Course Subject: ");
        subjectField = new JTextField(20);
        addACoursePanel.add(subjectLabel);
        addACoursePanel.add(subjectField);
        JLabel courseNumberLabel = new JLabel("Course Number: ");
        courseNumberField = new JTextField(20);
        addACoursePanel.add(courseNumberLabel);
        addACoursePanel.add(courseNumberField);
        int result = JOptionPane.showConfirmDialog(null, addACoursePanel,
                "Add a course", JOptionPane.OK_CANCEL_OPTION);
        addCoursesSaving(result);
    }

    // MODIFIES: this
    // EFFECTS: shows a panel that can let the user type in the course subject and course number.
    private void addSectionsInWorkListPanel() {
        JPanel addACoursePanel = new JPanel();
        addACoursePanel.setLayout(new GridLayout(3, 2));
        JLabel subjectLabel = new JLabel("Course Subject: ");
        subjectFieldSection = new JTextField(20);
        addACoursePanel.add(subjectLabel);
        addACoursePanel.add(subjectFieldSection);
        JLabel courseNumberLabel = new JLabel("Course Number: ");
        courseNumberFieldSection = new JTextField(20);
        addACoursePanel.add(courseNumberLabel);
        addACoursePanel.add(courseNumberFieldSection);
        JLabel sectionLabel = new JLabel("Section Number: ");
        sectionField = new JTextField(20);
        addACoursePanel.add(sectionLabel);
        addACoursePanel.add(sectionField);
        int result = JOptionPane.showConfirmDialog(null, addACoursePanel,
                "Add a section", JOptionPane.OK_CANCEL_OPTION);
        addSectionsSaving(result);
    }


    // MODIFIES: this
    // EFFECTS: delete a section from the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a message,
    // if the user doesn't have such a course in course work list, remind user by popping a message
    private void deleteSectionsSaving(int result) {
        if (result == JOptionPane.OK_OPTION) {
            String subject = deleteSubjectField.getText().trim();
            String courseNumberStr = deleteCourseNumberField.getText().trim();
            int sectionNumber = Integer.parseInt(deleteSectionField.getText().trim());
            if (!courseNumberStr.isEmpty()) {
                try {
                    int courseNumber = Integer.parseInt(courseNumberStr);
                    deleteSection(subject, sectionNumber, courseNumber);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid course number.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Course number cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updatePanelInWorkList();
    }


    // MODIFIES: this
    // EFFECTS: delete a section from the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a error,
    // if the user doesn't have a such course to course work list, remind user by popping a error,
    // if the user doesn't have a given such in his course workList, remind user by popping a error.
    private void deleteSection(String subject, int sectionNumber, int courseNumber) {
        if (!userStudent.workListContainSameCourse(subject, courseNumber)) {
            JOptionPane.showMessageDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (userStudent.deleteOneCourseSection(subject, courseNumber, sectionNumber)) {
            userStudent.deleteOneCourseSection(subject, courseNumber, sectionNumber);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Your work list doesn't contain this section.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: add a section to the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a message,
    // if the user has been added such a course to course work list, remind user by popping a message
    private void addSectionsSaving(int result) {
        if (result == JOptionPane.OK_OPTION) {
            String subject = subjectFieldSection.getText().trim();
            String courseNumberStr = courseNumberFieldSection.getText().trim();
            int sectionNumber = Integer.parseInt(sectionField.getText().trim());
            if (!courseNumberStr.isEmpty()) {
                try {
                    int courseNumber = Integer.parseInt(courseNumberStr);
                    addSection(subject, courseNumber, sectionNumber);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid course number.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Course number cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updatePanelInWorkList();
    }

    // MODIFIES: this
    // EFFECTS: add a section to the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a message,
    // if the user has been added such a course to course work list, remind user by popping a message
    private void addCoursesSaving(int result) {
        if (result == JOptionPane.OK_OPTION) {
            String subject = subjectField.getText().trim();
            String courseNumberStr = courseNumberField.getText().trim();
            if (!courseNumberStr.isEmpty()) {
                try {
                    int courseNumber = Integer.parseInt(courseNumberStr);
                    checkSameCourseAdded(subject, courseNumber);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid course number.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Course number cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS; update the panel only if the user add a course that has not existed in the course work list.
    private void checkSameCourseAdded(String subject, int courseNumber) {
        if (userStudent.addANewCourseToWorkListCheckSameCourse(subject, courseNumber)) {
            JOptionPane.showMessageDialog(null,
                    "You have added " + subject + courseNumber + " to the course work list",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userStudent.addACourseToWorkList(subject, courseNumber);
            updatePanelInWorkList();
        }
    }

    // EFFECTS: add a section to the workList panel, popping notification if user types a course which has not existed
    // in the panel or the section this user wants to add has already been added to the panel
    private void addSection(String subject, int courseNumber, int sectionNumber) {
        if (!userStudent.workListContainSameCourse(subject, courseNumber)) {
            JOptionPane.showMessageDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (userStudent.addANewSectionToWorkListCheckSameSection(subject, courseNumber, sectionNumber)) {
            JOptionPane.showMessageDialog(null,
                    "You have already added the section: " + subject + courseNumber + "-"
                            + sectionNumber + " before!! ",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userStudent.addANewSectionToWorkList(subject, courseNumber, sectionNumber);
        }
    }


    // EFFECTS: update the panel that shows the course details of each course in course workList
    private void updatePanelInWorkList() {

        infoPanel.remove(scrollPane); // Remove the old scrollPane.
        List<Course> courseListInWorkList = userStudent.getWorkList();
        String[][] courseData = getWorkListData(courseListInWorkList);
        showCourseInWorkList(infoPanel, courseData);
        infoPanel.revalidate();
        infoPanel.repaint();
        add(infoPanel);
        revalidate();
        repaint();

    }

    // EFFECTS: show the data on the panel
    private void showCourseInWorkList(JPanel infoPanel, String[][] courseData) {
        remove(infoPanel);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        String[] columnNames = {"Subject", "Course No.", "Section No."};
        Object[][] data = courseData;
        JTable courseTable = new JTable(data, columnNames);
        courseTable.setEnabled(false);
        courseTable.getTableHeader().setFont(headerFont);
        scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(scrollPane);

    }

    // EFFECTS: returns String[][] orderListData according to the current state of myRestaurant
    private String[][] getWorkListData(List<Course> courseList) {
        String[][] courseData = new String[courseList.size()][4];
        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);
            courseData[i][0] = c.getSubject();
            courseData[i][1] = String.valueOf(c.getCourseNumber());
            List<Integer> sectionNumbers = c.getAllSections();
            courseData[i][2] = sectionNumbers.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

        }
        return courseData;
    }


    // EFFECTS: show the data on the panel
    private void showCourseInRegList(JPanel infoPanel, String[][] courseData) {
        remove(infoPanel);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        String[] columnNames = {"Subject", "Course No.", "Section No."};
        Object[][] data = courseData;
        JTable courseTable = new JTable(data, columnNames);
        courseTable.setEnabled(false);
        courseTable.getTableHeader().setFont(headerFont);
        scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(scrollPane);

    }

    // EFFECTS: returns String[][] orderListData according to the current state of myRestaurant
    private String[][] getRegListData(List<Course> courseList) {
        String[][] courseData = new String[courseList.size()][4];
        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);
            courseData[i][0] = c.getSubject();
            courseData[i][1] = String.valueOf(c.getCourseNumber());
            List<Integer> sectionNumbers = c.getAllSections();
            courseData[i][2] = sectionNumbers.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

        }
        return courseData;
    }


    // EFFECTS: creates a menuBar called options
    private void menuBar() {
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Options");
        buttonPanel = new JPanel();
        buttonsForWorkList();
        courseWorkListListener();
        courseRegisteredListener();
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // EFFECTS: creates the listener for the course workList "item"
    private void courseWorkListListener() {
//        buttonsForWorkList();
        JMenuItem courseWorkListItem = new JMenuItem("Course WorkList");
        courseWorkListItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePanelInWorkList();
                buttonsForWorkList();
            }
        });
        menu.add(courseWorkListItem);
    }

    // EFFECTS: creates the listener for the course registered "item"
    private void courseRegisteredListener() {
//        buttonsForRegList();
        JMenuItem registeredListItem = new JMenuItem("Registered List");
        registeredListItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePanelInRegisteredList();
                buttonsForRegList();

            }
        });
        menu.add(registeredListItem);
    }

    // EFFECTS: update the panel that shows the course details of each course in course registered list
    private void updatePanelInRegisteredList() {
        infoPanel.remove(scrollPane);
        List<Course> courseListInRegList = userStudent.getRegistereddList();
        String[][] courseData = getRegListData(courseListInRegList);
        showCourseInRegList(infoPanel, courseData);
        infoPanel.revalidate();
        infoPanel.repaint();
        add(infoPanel);
        revalidate();
        repaint();
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

    // EFFECTS: add a listener to login button
    private void addLoginListener(JButton loginButton) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int studentId = Integer.parseInt(studentIdField.getText());
                userStudent = new Student(name, studentId);
                try {
                    remove(loginPanel);
                    remove(loginButtonPanel);
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
        JButton btnYes = new JButton("For sure!");
        JButton btnNo = new JButton("Start a new one!");
        btnYes.setBounds(100, 190, 150, 40);
        btnNo.setBounds(280, 190, 150, 40);
        statusLabel.setBounds(-20, 50, 600, 40);
        backgroundPanel.add(statusLabel, BorderLayout.CENTER);
        backgroundPanel.add(btnYes);
        backgroundPanel.add(btnNo);
        addListenerToButtonYes(btnYes);
        addListenerToButtonNo(btnNo);
    }

    // EFFECTS: add a listener to the button "Yes"
    private void addListenerToButtonYes(JButton btnYes) {
        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBothList();
                updatePanelInWorkList();
                homepage(userStudent.getName(), userStudent.getId());

            }
        });

    }

    // EFFECTS: add a listener to the button "No"
    private void addListenerToButtonNo(JButton btnNo) {

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

