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



/**
 * EFFECTS: Course Registration System GUI
 */
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


    /**
     * EFFECTS: Constructs the CourseRegistrationSystemGUI, initializing the user interface.
     */
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



    /**
     * MODIFIES: this
     * EFFECTS: Initializes the information panel with no data.
     */
    private void initializeInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(600, 300));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        String[][] emptyArray = {};
        showCourseInWorkList(infoPanel, emptyArray);
        backgroundPanel.add(infoPanel);

    }



    /**
     * MODIFIES: this
     * EFFECTS: Adds a window listener that prompts the user to save data on closing and handles the user's response.
     */
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


    /**
     * EFFECTS: Prints all logged events to the console, excluding the 'Event log cleared.' event.
     */
    private void printEventsLogged() {
        for (Event next : EventLog.getInstance()) {
            if (!next.getDescription().equals("Event log cleared.")) {
                System.out.println(next.toString());
            }
        }
    }



    /**
     * MODIFIES: this
     * EFFECTS: Saves the current state of the course work list and registered list to a file.
     * Displays an error message if the save operation fails.
     */
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

    /**
     * EFFECTS: Sets up the background panel and loads the image to be used as the background.
     */
    private void setupBackground() {
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.white);
        backgroundPanel.setPreferredSize(new Dimension(600, 300));
        add(backgroundPanel);
        backgroundPanel.setLayout(null);
        addBackGround();
        loadHistory();
    }

    /**
     * EFFECTS: adds the background of panel
     */
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

    /**
     * EFFECTS: Resizes a BufferedImage to the specified width and height.
     *
     * @param image     The original image to resize.
     * @param newWidth  The width of the resized image.
     * @param newHeight The height of the resized image.
     * @return A new BufferedImage that is a resized version of the original image.
     */
    public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * MODIFIES: this
     * EFFECTS: Displays the login interface.
     */
    private void login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(createLoginPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * EFFECTS: Sets up and displays the homepage with user information and navigational options.
     *
     * @param name The name of the student.
     * @param id The student ID.
     */
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

    /**
     * EFFECTS: Displays buttons for managing the course work list.
     */
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

    /**
     * EFFECTS: Displays buttons for managing the registered course list.
     */
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

    /**
     * EFFECTS: Adds an action listener to the "Register Courses" button.
     *
     * @param registerCoursesButton the button to which the listener will be added.
     * EFFECTS: Attaches an action listener to the "Register Courses" button that initiates displaying the
     *          registration panel.
     */
    private void addListenerToRegisterCoursesButton(JButton registerCoursesButton) {
        registerCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCoursesInRegListPanel();
            }
        });
    }

    /**
     * EFFECTS: Adds an action listener to the "Drop Courses" button.
     *
     * @param dropCoursesButton the button to which the listener will be added.
     * EFFECTS: Attaches an action listener to the "Drop Courses" button that initiates the course dropping process.
     */
    private void addListenerToDropCoursesButton(JButton dropCoursesButton) {
        dropCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCoursesInRegListPanel();
            }
        });
    }


    /**
     * EFFECTS: Adds an action listener to the "Delete Courses" button.
     *
     * @param acButton the button to which the listener will be added.
     * EFFECTS: Attaches an action listener to the "Delete Courses" button that initiates the course deletion process
     *          from the work list.
     */
    private void addListenerToDeleteCoursesButton(JButton acButton) {
        acButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCoursesInWorkListPanel();
            }
        });
    }


    /**
     * EFFECTS: Adds an action listener to the "Add Courses" button.
     *
     * @param acButton the button to which the listener will be added.
     * EFFECTS: Attaches an action listener to the "Add Courses" button that initiates the course addition process
     *          to the work list.
     */
    private void addListenerToAddCoursesButton(JButton acButton) {
        acButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCoursesInWorkListPanel();
            }
        });
    }

    /**
     * EFFECTS: Adds an action listener to the "Add Sections" button.
     *
     * @param asButton the button to which the listener will be added.
     * EFFECTS: Attaches an action listener to the "Add Sections" button that initiates the section addition process
     *          to the work list.
     */
    private void addListenerToAddSectionsButton(JButton asButton) {
        asButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSectionsInWorkListPanel();
            }
        });
    }


    /**
     * MODIFIES: this
     * EFFECTS: Shows a dialog panel with input fields for subject and course number of the course to be dropped from
     *          the registered list.
     */
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

    /**
     * Handles the process of dropping a course from the registered list based on user input.
     *
     * MODIFIES: this
     * EFFECTS: Attempts to drop a course from the registered list based on user input. If the input is invalid
     *          or the course is not in the registered list, displays an error message. Updates the registered list
     *          panel upon successful course dropping.
     */
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


    /**
     * Drops a course from the registered list.
     *
     * @param subject      the subject of the course to drop.
     * @param courseNumber the number of the course to drop.
     * EFFECTS: If the course is in the registered list, removes it and updates the list display.
     *          If the course is not in the registered list, displays an error message.
     */
    private void dropCourses(String subject, int courseNumber) {
        if (!userStudent.repetitiveCourseInRegisteredList(subject, courseNumber)) {
            JOptionPane.showMessageDialog(null,
                    "Your work list doesn't contain " + subject + String.valueOf(courseNumber),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userStudent.removeAllSectionsFromRegList(subject, courseNumber);
        }
    }


    /**
     * MODIFIES: this
     * EFFECTS: Registers courses in the registration list panel.
     */
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

    /**
     * Creates and styles the "Register" button.
     *
     * @return JButton the styled "Register" button.
     * EFFECTS: Creates a "Register" button with a custom font, color scheme, and hover effects.
     */
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

    /**
     * Adds instructional labels to the registration frame.
     *
     * EFFECTS: Adds two labels to the registration frame that provide instructions on how to register a course.
     */
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

    /**
     * Creates and configures a panel for course registration input fields.
     *
     * @return JPanel a panel with input fields for subject, course number, and section number.
     * EFFECTS: Creates a panel with labeled text fields for entering course registration information.
     */
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

    /**
     * Adds an action listener to the "Register" button that triggers course registration.
     *
     * @param registerButton the button to attach the listener to.
     * EFFECTS: Attaches an action listener to the "Register" button that initiates the course registration process.
     */
    private void addListenerToRegisterButton(JButton registerButton) {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCoursesSaving();
            }
        });
    }

    /**
     * Handles the course registration process and provides user feedback.
     *
     * MODIFIES: this
     * EFFECTS: Attempts to register a course and section based on user input. If successful, updates the registered
     *          list panel and closes the registration frame. If the input is invalid, displays an error message
     *          to the user.
     */
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

    /**
     * Registers a course section if it exists in the work list and has not already been registered.
     *
     * @param subject       the subject of the course.
     * @param courseNumber  the course number.
     * @param sectionNumber the section number to register.
     * EFFECTS: Registers a section for a course if the course is in the work list and the section is not already
     *          registered.
     *          If the course or section is not valid, displays an error message to the user.
     */
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


    /**
     * MODIFIES: this
     * EFFECTS: Deletes courses from the work list panel.
     */
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



    /**
     * MODIFIES: this
     * EFFECTS: Adds courses to the work list panel.
     */
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


    /**
     * MODIFIES: this
     * EFFECTS: Adds sections to the work list panel.
     */
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



    /**
     * MODIFIES: this
     * EFFECTS: Adds sections to the work list panel.
     */
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

    /**
     * Deletes a section from the student's course work list.
     * Notifies the user with an error message if the course or section does not exist in the work list.
     *
     * @param subject       the subject of the course
     * @param sectionNumber the section number to be deleted
     * @param courseNumber  the course number from which the section is to be deleted
     * MODIFIES: this
     * EFFECTS: If the course and section are valid and exist, deletes the section from the work list.
     *          If the course does not exist, notifies the user.
     *          If the section does not exist in the course, notifies the user.
     *          If all sections from a course are deleted, removes the course from the work list.
     *          Otherwise, throws a NumberFormatException if the course number is not valid.
     */
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
            if (userStudent.showAllSectionOfThisCourseInWorkList(subject, courseNumber).isEmpty()) {
                userStudent.removeAllSectionsFromWorkList(subject, courseNumber);
            }
        }
    }


    /**
     * Saves the addition of a section to the student's course work list based on user input.
     * Notifies the user with an error message if the input is invalid or the section already exists.
     *
     * @param result the result of the user's confirmation to add a section
     * MODIFIES: this
     * EFFECTS: If the user confirms the action, adds the section to the work list.
     *          Notifies the user if the course number is invalid or empty.
     *          Updates the panel displaying the work list if a section is successfully added.
     */
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


    /**
     * MODIFIES: this
     * EFFECTS: Handles the saving process for adding courses to the work list.
     *
     * @param result The result from the confirm dialog.
     */
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

    /**
     * Checks if a course has already been added to the work list and updates the panel accordingly.
     *
     * @param subject      the subject code of the course to check.
     * @param courseNumber the course number to check.
     * MODIFIES: this
     * EFFECTS: If the course specified by subject and courseNumber has not been added to the work list,
     *          it is added and the work list panel is updated. If the course is already in the work list,
     *          an error message is displayed to the user.
     */
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

    /**
     * EFFECTS: Add a section to the workList panel, popping notification if user types a course which has not existed
     * in the panel or the section this user wants to add has already been added to the panel
     */
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


    /**
     * EFFECTS: Updates the panel displaying the course work list.
     */
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


    /**
     * MODIFIES: this
     * EFFECTS: Displays the courses in the work list on the panel.
     *
     * @param infoPanel The panel where courses are displayed.
     * @param courseData The course data to display on the panel.
     */
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

    /**
     * EFFECTS: Retrieves the data for the work list in a format suitable for display in a JTable.
     *
     * @param courseList The list of courses to display.
     * @return A 2D array of strings representing the course data.
     */
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

    /**
     * EFFECTS: Shows the data on the registered list panel.
     *
     * @param infoPanel The panel where courses are displayed.
     * @param courseData The course data to display on the panel.
     */
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

    /**
     * EFFECTS: Retrieves the data for the registered list in a format suitable for display in a JTable.
     *
     * @param courseList The list of registered courses to display.
     * @return A 2D array of strings representing the course data.
     */
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


    /**
     * EFFECTS: Creates and configures the menu bar.
     */
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

    /**
     * EFFECTS: Adds a listener to the 'Course WorkList' menu item.
     */
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

    /**
     * EFFECTS: Adds a listener to the 'Registered List' menu item.
     */
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

    /**
     * EFFECTS: Updates the panel displaying the registered course list.
     */
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


    /**
     * EFFECTS: Creates and returns the login panel.
     *
     * @return A JPanel that contains the login form.
     */
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

    /**
     * EFFECTS: Creates and returns the login button panel.
     *
     * @return A JPanel that contains the login button.
     */
    private JPanel createButtonPanel() {
        loginButtonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        addLoginListener(loginButton);
        loginButtonPanel.add(loginButton);
        return loginButtonPanel;
    }

    /**
     * EFFECTS: Adds a listener to the login button that validates user input and opens the homepage.
     *
     * @param loginButton The login button to add a listener to.
     */
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

    /**
     * EFFECTS: Prompts the user to choose whether they want to load previous session data.
     */
    private void loadHistory() {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        JLabel statusLabel1 = new JLabel("Would you like to load the previous"
                + " course workList ", SwingConstants.CENTER);
        JLabel statusLabel2 = new JLabel("and registration records from the file?", SwingConstants.CENTER);
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

    /**
     * EFFECTS: Adds a listener to the 'Yes' button to load session data.
     *
     * @param btnYes The 'Yes' button to add a listener to.
     */
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

    /**
     * EFFECTS: Adds a listener to the 'No' button to start a new session.
     *
     * @param btnNo The 'No' button to add a listener to.
     */
    private void addListenerToButtonNo(JButton btnNo) {

        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(backgroundPanel);
                login();
            }
        });
    }



    /**
     * MODIFIES: this
     * EFFECTS: Loads the course work list and registered list from the file and clears the event log.
     */
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

    /**
     * EFFECTS: Runs the course registration GUI.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new CourseRegistrationSystemGUI();
    }

}

