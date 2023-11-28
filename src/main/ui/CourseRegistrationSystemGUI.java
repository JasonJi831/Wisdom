// Reference1: Paul Carter, Jun 28, 2021, B02-SpaceInvadersBase, java,
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase/commit/a114795faa8022e803137f805057d5e13d0e3184

// Reference2: fgrund, Oct 15, 2022, C3-LectureLabStarter, java,
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// Reference3: liuhongkai, Oct 1st, 2023, RestaurantOrdersManager, java
// https://github.students.cs.ubc.ca/CPSC210/SpaceInvadersRefactored

// Reference4: Paul Carter, Sep 5, 2023, AlarmSystem, java,
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

package ui;

import model.Course;
import model.Student;
import model.Event;
import model.EventLog;

import persistence.JsonReader;
import persistence.JsonWriter;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.JTableHeader;
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

public class CourseRegistrationSystemGUI extends JFrame {
    private JFrame registrationFrame;
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
    private JTextField subjectFieldReg;
    private JTextField courseNumberFieldReg;
    private JTextField sectionFieldReg;
    private JTextField dropSubjectField;
    private JTextField dropCourseNumberField;

    private JPanel loginPanel;
    private JPanel loginButtonPanel;
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
    public CourseRegistrationSystemGUI() {
        super("Course Registration System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addListenerForWindow();
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
    private void addListenerForWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Yes", "No", "Cancel"};
                int result = JOptionPane.showOptionDialog(null,
                        "Do you want to save the current course work list and registered list?",
                        "Save or not",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     // do not use a custom Icon
                        options,  // the titles of buttons
                        options[0]); // default button title

                if (result == JOptionPane.YES_OPTION) {
                    saveBothLists();
                    dispose();
                    printEventsLogged();
                } else if (result == JOptionPane.NO_OPTION) {
                    dispose();
                    printEventsLogged();
                }
            }
        });
    }


    // EFFECTS: print to the console all the events that have been logged since the application run.
    // if you loaded a previous state of course registration system, the previous action events will not be
    // shown.
    private void printEventsLogged() {
        for (Event next : EventLog.getInstance()) {
            if (!next.getDescription().equals("Event log cleared.")) {
                System.out.println(next.toString());
            }
        }
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
            imageLabel.setBounds(150, 30, 300, 200);
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
        add(createButtonPanel(), BorderLayout.SOUTH);
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
                registerCoursesInRegListPanel();
            }
        });
    }

    // EFFECTS: add a listener to the button "drop courses"
    private void addListenerToDropCoursesButton(JButton dropCoursesButton) {
        dropCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCoursesInRegListPanel();
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
    // EFFECTS: shows a panel that can let the user type in course subject and course number of a course
    // the user wants to drop from the course registered List.
    private void dropCoursesInRegListPanel() {
        JPanel dropACoursePanel = new JPanel();
        dropACoursePanel.setLayout(new GridLayout(2, 2));
        JLabel subjectLabel = new JLabel("Course Subject: ");
        dropSubjectField = new JTextField(20);
        dropACoursePanel.add(subjectLabel);
        dropACoursePanel.add(dropSubjectField);
        JLabel courseNumberLabel = new JLabel("Course Number: ");
        dropCourseNumberField = new JTextField(20);
        dropACoursePanel.add(courseNumberLabel);
        dropACoursePanel.add(dropCourseNumberField);
        Object[] options = {"Confirm", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, dropACoursePanel,
                "Drop a course", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        dropCoursesSaving(result);
    }


    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // MODIFIES: this
    // EFFECTS: drop a course from the userStudent's course registered list,
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a message,
    // if the user doesn't have such a course in course registered list, remind user by popping a message.
    private void dropCoursesSaving(int result) {
        if (result == JOptionPane.OK_OPTION) {
            String subject = dropSubjectField.getText().trim();
            String courseNumberStr = dropCourseNumberField.getText().trim();
            if (!courseNumberStr.isEmpty()) {
                try {
                    int courseNumber = Integer.parseInt(courseNumberStr);
                    dropCourses(subject, courseNumber);
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
        updatePanelInRegisteredList();
    }


    // EFFECTS: drop a course from the registered list panel, popping notification if user types a course
    // which has not existed in the course registered list
    private void dropCourses(String subject, int courseNumber) {
        if (!userStudent.repetitiveCourseInRegisteredList(subject, courseNumber)) {
            JOptionPane.showMessageDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userStudent.removeAllSectionsFromRegList(subject, courseNumber);
        }
    }

    // MODIFIES: this
    // EFFECTS: shows a window that can let the user type in the info of a course which user wants to register.
    private void registerCoursesInRegListPanel() {
        registrationFrame = new JFrame("Course Registration");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setLayout(new BoxLayout(registrationFrame.getContentPane(), BoxLayout.Y_AXIS));
        infoPanel.remove(scrollPane);
        List<Course> courseListInWorkList = userStudent.getWorkList();
        String[][] courseData = getWorkListData(courseListInWorkList);
        showCourseInWorkList(infoPanel, courseData);
        infoPanel.revalidate();
        infoPanel.repaint();
        scrollPane.setPreferredSize(new Dimension(500, 250));
        addAnInstructionForRegister();
        JPanel inputPanel = registerCoursePanelHelper();
        JPanel buttonPanel = new JPanel();
        JButton registerButton = getRegisterButton();
        addListenerToRegisterButton(registerButton);
        buttonPanel.add(registerButton);
        registrationFrame.add(scrollPane);
        registrationFrame.add(inputPanel);
        registrationFrame.add(buttonPanel);
        registrationFrame.pack();
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.setVisible(true);
    }

    // EFFECTS: creates a register button with good-looking style.
    private static JButton getRegisterButton() {
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Set a custom font
        registerButton.setForeground(Color.WHITE); // Set the text color to white
        registerButton.setBackground(new Color(0, 123, 255)); // A nice blue color
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false); // Turn off the border painting for a flat look
        registerButton.setFocusPainted(false); // Remove the focus indicator (optional)
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change the cursor to a hand on hover
        registerButton.setMargin(new Insets(5, 10, 5, 10));
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 101, 204)); // Darker blue when hovering
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 123, 255)); // Original color when not hovering
            }
        });
        return registerButton;
    }

    // EFFECTS: creates an instruction for registering a course.
    private void addAnInstructionForRegister() {
        JLabel instructionLabel1 = new JLabel("To register a course from your course work list shown below, ");
        JLabel instructionLabel2 = new JLabel(" please type the course information into the following boxes.");
        instructionLabel1.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        instructionLabel2.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        instructionLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrationFrame.add(instructionLabel1);
        registrationFrame.add(instructionLabel2);
    }

    // EFFECTS: creates a panel to help user register a course, the user needs to type the subject, course number and
    // section number.
    private JPanel registerCoursePanelHelper() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        JLabel subjectLabel = new JLabel("Subject:");
        JLabel courseNoLabel = new JLabel("Course Number: ");
        JLabel sectionNoLabel = new JLabel("Section Number: ");
        subjectFieldReg = new JTextField();
        courseNumberFieldReg = new JTextField();
        sectionFieldReg = new JTextField();
        inputPanel.add(subjectLabel);
        inputPanel.add(subjectFieldReg);
        inputPanel.add(courseNoLabel);
        inputPanel.add(courseNumberFieldReg);
        inputPanel.add(sectionNoLabel);
        inputPanel.add(sectionFieldReg);
        return inputPanel;
    }

    // EFFECTS; add a listener to the button "register"
    private void addListenerToRegisterButton(JButton registerButton) {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCoursesSaving();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add a section to the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping a message,
    // if the user has been added such a course to course work list, remind user by popping a message
    private void registerCoursesSaving() {
        String subject = subjectFieldReg.getText().trim();
        String courseNumberStr = courseNumberFieldReg.getText().trim();
        int sectionNumber = Integer.parseInt(sectionFieldReg.getText().trim());
        if (!courseNumberStr.isEmpty()) {
            try {
                int courseNumber = Integer.parseInt(courseNumberStr);
                registerSections(subject, courseNumber, sectionNumber);
            } catch (NumberFormatException ex) {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,
                        "Please enter a valid course number.",
                        "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options, options[0]);
            }
        } else {
            Object[] options = {"OK"};

            JOptionPane.showOptionDialog(null,
                    "Course number cannot be empty.",
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        }

        updatePanelInRegisteredList();
        registrationFrame.dispose();

    }

    // EFFECTS: add a section to the registered list panel, popping notification if user types a course
    // which has not existed in the course work list or the section this user wants to
    // add has already been added to the panel.
    private void registerSections(String subject, int courseNumber, int sectionNumber) {
        if (!userStudent.workListContainSameCourse(subject, courseNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else if (userStudent.repetitiveCourseInRegisteredList(subject, courseNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "You have already registered " + subject + courseNumber + " before!! ",
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else if (!userStudent.showAllSectionOfThisCourseInWorkList(subject, courseNumber).contains(sectionNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "You have not add " + subject + courseNumber + "-" + sectionNumber + " to your "
                            + "course workList list before!! ",
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else {
            userStudent.registerCourse(subject, courseNumber, sectionNumber);
        }
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
        Object[] options = {"Confirm", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, addACoursePanel,
                "delete a course section", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        deleteSectionsSaving(result);
    }


    // MODIFIES: this
    // EFFECTS: shows a panel that can let the user type in the course subject and course number for adding a course.
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
        Object[] options = {"Confirm", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, addACoursePanel,
                "Add a course section", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
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
        Object[] options = {"Confirm", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, addACoursePanel,
                "Add a course section", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
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
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Please enter a valid course number.",
                            "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                            null, options, options[0]);
                }
            } else {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null, "Course number cannot be empty.",
                        "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options, options[0]);
            }
        }
        updatePanelInWorkList();
    }


    // MODIFIES: this
    // EFFECTS: delete a section from the userStudent's course workList
    // if the user type a valid course number, otherwise, throws NumberFormatException by popping an error,
    // if the user doesn't have a such course to course work list, remind user by popping an error,
    // if the user doesn't have a given such in his course workList, remind user by popping an error.
    private void deleteSection(String subject, int sectionNumber, int courseNumber) {
        if (!userStudent.workListContainSameCourse(subject, courseNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else if (!userStudent.whetherContainSuchSectionInWorkList(subject, courseNumber, sectionNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Your work list doesn't contain this section.", "Input Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else {
            userStudent.deleteOneCourseSection(subject, courseNumber, sectionNumber);
            if (userStudent.showAllSectionOfThisCourseInWorkList(subject,courseNumber).isEmpty()) {
                userStudent.removeAllSectionsFromWorkList(subject, courseNumber);
            }
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
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Please enter a valid course number.",
                            "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                            null, options, options[0]);
                }
            } else {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,
                        "Course number cannot be empty.",
                        "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options, options[0]);
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
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null,
                            "Please enter a valid course number.",
                            "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                            null, options, options[0]);
                }
            } else {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,
                        "Course number cannot be empty.",
                        "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options, options[0]);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS; update the panel only if the user add a course that has not existed in the course work list.
    private void checkSameCourseAdded(String subject, int courseNumber) {
        if (userStudent.addANewCourseToWorkListCheckSameCourse(subject, courseNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "You have added " + subject + courseNumber + " to the course work list",
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else {
            userStudent.addACourseToWorkList(subject, courseNumber);
            updatePanelInWorkList();
        }
    }

    // EFFECTS: add a section to the workList panel, popping notification if user types a course which has not existed
    // in the panel or the section this user wants to add has already been added to the panel
    private void addSection(String subject, int courseNumber, int sectionNumber) {
        if (!userStudent.workListContainSameCourse(subject, courseNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        } else if (userStudent.addANewSectionToWorkListCheckSameSection(subject, courseNumber, sectionNumber)) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "You have already added the section: " + subject + courseNumber + "-"
                            + sectionNumber + " before!! ",
                    "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
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

    // MODIFIES: this
    // EFFECTS: show the courses information on the panel with the blue header.
    private void showCourseInWorkList(JPanel infoPanel, String[][] courseData) {
        remove(infoPanel);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        String[] columnNames = {"Subject", "Course No.", "Section No."};
        JTable courseTable = new JTable(courseData, columnNames);
        courseTable.setEnabled(false);
        JTableHeader tableHeader = courseTable.getTableHeader();
        tableHeader.setFont(headerFont);
        tableHeader.setBackground(new Color(44, 121, 210, 255));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setPreferredSize(new Dimension(100, 36));
        courseTable.setRowHeight(24);
        courseTable.setShowHorizontalLines(true);
        courseTable.setShowVerticalLines(true);
        courseTable.setGridColor(new Color(200, 200, 200));
        courseTable.setSelectionBackground(new Color(204, 229, 255));
        scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(scrollPane);
        infoPanel.revalidate();
        infoPanel.repaint();
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
        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        String[] columnNames = {"Subject", "Course No.", "Section No."};
        JTable courseTable = new JTable(courseData, columnNames);
        courseTable.setEnabled(false);
        JTableHeader tableHeader = courseTable.getTableHeader();
        tableHeader.setFont(headerFont);
        tableHeader.setBackground(new Color(44, 121, 210, 255));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setPreferredSize(new Dimension(100, 36));
        courseTable.setRowHeight(24);
        courseTable.setShowHorizontalLines(true);
        courseTable.setShowVerticalLines(true);
        courseTable.setGridColor(new Color(200, 200, 200));
        courseTable.setSelectionBackground(new Color(204, 229, 255));
        scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(scrollPane);
        infoPanel.revalidate();
        infoPanel.repaint();

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
        menu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menu.setForeground(Color.WHITE);
        menu.setBackground(new Color(0, 51, 102));
        menu.setOpaque(true);
        menu.setForeground(Color.WHITE);
        menu.setBackground(new Color(0, 51, 102));
        menu.setOpaque(true);
        buttonPanel = new JPanel();
        buttonsForWorkList();
        courseWorkListListener();
        courseRegisteredListener();
        menuBar.add(menu);
        menuBar.setBackground(new Color(0, 51, 102));
        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        setJMenuBar(menuBar);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // EFFECTS: creates the listener for the course workList "item"
    private void courseWorkListListener() {
        JMenuItem courseWorkListItem = new JMenuItem("Course WorkList");
        courseWorkListItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseWorkListItem.setBackground(new Color(255, 255, 255));
        courseWorkListItem.setForeground(new Color(0, 51, 102));
        courseWorkListItem.setToolTipText("View and manage your list of courses");

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
        JMenuItem registeredListItem = new JMenuItem("Registered List");
        registeredListItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registeredListItem.setBackground(new Color(255, 255, 255));
        registeredListItem.setForeground(new Color(0, 51, 102));
        registeredListItem.setToolTipText("View your list of registered courses");

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
        nameField = new JTextField(15);
        loginPanel.add(nameField);
        loginPanel.add(new JLabel("Student ID"));
        studentIdField = new JTextField(15);
        loginPanel.add(studentIdField);
        return loginPanel;
    }

    // EFFECTS: creates the button "Login" with its own listener.
    private JPanel createButtonPanel() {
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
                    remove(loginPanel);
                    remove(loginButtonPanel);
                    homepage(name, studentId);

                } catch (NumberFormatException ex) {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null,
                            "Please enter a valid student ID (numbers only).",
                            "Login Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                            null, options, options[0]);
                }
            }
        });
    }

    // EFFECTS: prompts the user to choose whether they want to load the history data.
    private void loadHistory() {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        JLabel statusLabel1 = new JLabel("Do you want to load the previous"
                + " course workList ", SwingConstants.CENTER);
        JLabel statusLabel2 = new JLabel("and the registered list from the file?", SwingConstants.CENTER);
        JButton btnYes = new JButton("For sure!");
        JButton btnNo = new JButton("Start a new one!");
        btnYes.setFont(buttonFont);
        btnNo.setFont(buttonFont);
        statusLabel1.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        statusLabel2.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        btnYes.setBounds(125, 190, 150, 40);
        btnNo.setBounds(325, 190, 150, 40);
        statusLabel1.setBounds(0, 10, 600, 50);
        statusLabel2.setBounds(0, 35, 600, 50);
        backgroundPanel.add(statusLabel1, BorderLayout.CENTER);
        backgroundPanel.add(statusLabel2, BorderLayout.CENTER);
        backgroundPanel.add(btnYes);
        backgroundPanel.add(btnNo);
        addListenerToButtonYes(btnYes);
        addListenerToButtonNo(btnNo);
        setLocationRelativeTo(null);
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
    // EFFECTS: loads the course work list and registered list from the file. Clear all logEvents.
    private void loadBothList() {
        remove(backgroundPanel);
        try {
            jsonReader = new JsonReader(JSON_STORE);
            userStudent = jsonReader.read();
            EventLog.getInstance().clear();
        } catch (IOException e) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Unable to load from file " + JSON_STORE,
                    "Fail to load", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        }
    }

    // EFFECTS: run the course registration GUI
    public static void main(String[] args) {
        new CourseRegistrationSystemGUI();
    }

}

