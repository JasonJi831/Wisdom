// Reference: Sam, Jul 26.2021, TellerApp, java,
// "https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java"

package ui;

import model.Course;

import model.Student;


import java.util.Scanner;
import java.util.*;

public class CourseRegistrationSystem {
    private Student userStudent;
    private Scanner input;


    // EFFECTS: runs the course registration system
    public CourseRegistrationSystem() {
        runRobot();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRobot() {
        boolean keepGoing = true;
        String command = null;

        displayMenu();

        while (keepGoing) {
            firstOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processForStudent(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.print("Hello! What's your name?\n");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String name = input.next();
        System.out.print("Hello, " + name + "!" + " Whats your student id?(8 digits)\n");
        input.useDelimiter("\n");
        int id = input.nextInt();
        validId(name, id);
    }

    // REQUIRES: id > 0
    // MODIFIES: this
    // EFFECTS: check whether the user typed an 8-digit integer as his/her student id.
    private void validId(String name, int id) {
        if (10000000 <= id && id <= 99999999) {
            initial(name, id);
        } else {
            System.out.print("Please enter an 8-digit integer! \n");
            input.useDelimiter("\n");
            int newId = input.nextInt();
            validId(name, newId);

        }
    }


    // MODIFIES: this
    // EFFECTS: initializes a student with the given name and id.
    private void initial(String name, int id) {
        userStudent = new Student(name, id);
    }


    // EFFECTS: displays menu of options to user.
    private void firstOptions() {
        System.out.println("Please Select from:");
        System.out.println("\twork -> working on my course work list");
        System.out.println("\tr -> working on my course registered list");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prompts user to select his/her course work list or registered list.
    private void processForStudent(String command) {
        if (command.equals("work")) {
            workListProcess();
        } else if (command.equals("r")) {
            registeredListProcess();
        } else {
            System.out.println("Your selection is not valid, please type it again! ");
        }

    }

    // MODIFIES: this
    // EFFECTS: prompts user to select the actions for course work list.
    private void workListProcess() {
        String command = null;
        System.out.println("Please Select from:");
        System.out.println("\to -> open my work list");
        System.out.println("\taddc -> adds a course to my work list");
        System.out.println("\tadds -> adds a section of a course to my work list");
        System.out.println("\td -> delete a section of a course from my work list");
        System.out.println("\tr -> returns to the last step");
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("o")) {
            showWorkList();

        } else if (command.equals("addc")) {
            addCourse();

        } else if (command.equals("adds")) {
            addNewSectionForEmptyWorkList();

        } else if (command.equals("d")) {
            deleteASection();

        } else if (command.equals("r")) {
            System.out.println("Welcome to HomePage!");

        } else {
            System.out.println("please type a valid letter! ");
            workListProcess();
        }
    }


    // EFFECTS: prompts user to show his/her own work list.
    private void showWorkList() {
        List<Course> workList = userStudent.getWorkList();
        int courseNumInWorkList = workList.size();
        System.out.println("You have " + courseNumInWorkList + " course(s) in the work list: "
                + userStudent.showALlCourseInWorkList());
        showSectionInWorkList();
    }

    // MODIFIES: this
    // EFFECTS: prompts user to show all sections of a course his/her wants to check in course work list.
    private void showSectionInWorkList() {
        System.out.println("See all the sections of a course? (yes or no)");
        String choice = input.next();
        if (choice.equals("yes")) {
            System.out.println("Please type the subject of the course (Please type a String!)");
            String courseSubject = input.next();
            courseSubject = courseSubject.toUpperCase();
            System.out.println("Please type the course number of this course? (Please type an integer!)");
            int courseNumber = input.nextInt();
            if (userStudent.showAllSectionOfThisCourseInWorkList(courseSubject, courseNumber).size() == 0) {
                if (userStudent.repetitiveCourseInWorkList(courseSubject, courseNumber)) {
                    System.out.println("You have not add any section of this course!");
                } else {
                    System.out.println("You course list doesn't contain " + courseSubject + courseNumber
                            + "!");
                }
            } else {
                System.out.println("Here is the list of section of this course: "
                        + userStudent.showAllSectionOfThisCourseInWorkList(courseSubject, courseNumber));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to add a new course to the course work list.
    private void addCourse() {
        System.out.println("What's the subject of the course you want to add? (Please type a String!)");
        String courseSubject = input.next();
        courseSubject = courseSubject.toUpperCase();
        System.out.println("What's the course number of this course? (Please type an integer!)");
        int courseNumber = input.nextInt();
        if (userStudent.repetitiveCourseInWorkList(courseSubject, courseNumber)) {
            System.out.println("This Course is in your work list, do you want to add a new section for it? "
                    + "(Yes or No!)");
            addSectionForAnExistingCourse(courseSubject, courseNumber);
        } else {
            userStudent.addACourseToWorkList(courseSubject, courseNumber);
        }
    }

    // MODIFIES: this
    // EFFECTS: based on the fact that there is nothing in the course work list, prompts user to add a new section
    // for a course which is in his/her course work list.
    private void addNewSectionForEmptyWorkList() {
        List<Course> workList = userStudent.getWorkList();
        if (workList.isEmpty()) {
            System.out.println("Your course work list is empty, so you cannot add a new section!");
        } else {
            System.out.println("What's the subject of this course (Please type a String!)");
            String courseSubject = input.next();
            System.out.println("What's the course number? (Please type an integer!)");
            int courseNumber = input.nextInt();
            addNewSectionProcess(courseSubject, courseNumber);


        }
    }

    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: based on the fact that there is nothing in the course work list, adds a new section
    // for a course which is in his/her course work list.
    private void addNewSectionProcess(String courseSubject, int courseNumber) {
        System.out.println("Please enter the section number you want to add (Please type an integer!)");
        int sectionNum = input.nextInt();
        userStudent.addANewSectionToWorkList(courseSubject, courseNumber, sectionNum);
        System.out.println("The section " + sectionNum + " has been added to the " + courseSubject
                + courseNumber + "!");

    }

    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: based on the fact that some course has been added to the work list, prompts user to add a new section 
    // for a course which is in the course work list.
    private void addSectionForAnExistingCourse(String courseSubject, int courseNumber) {
        String command = null;
        command = input.next();
        if (command.equals("Yes")) {
            System.out.println("Please enter the section number you want to add (Please type an integer!)");
            int sectionNum = input.nextInt();
            userStudent.addANewSectionToWorkList(courseSubject, courseNumber, sectionNum);
            System.out.println("The section " + sectionNum + " has been added to the " + courseSubject
                    + courseNumber + "!");
        } else if (command.equals("No")) {
            workListProcess();
        } else {
            System.out.println("This Course has already got into the list, do you want to add a new section for it? "
                    + "(Yes or No!)");
            addSectionForAnExistingCourse(courseSubject, courseNumber);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to delete a section for a course which is in the course work list.
    private void deleteASection() {
        List<Course> workList = userStudent.getWorkList();
        if (workList.isEmpty()) {
            System.out.println("Your course work list is empty, so you cannot delete a section!");
        } else {
            System.out.println("What's the subject of the course you want to delete? (Please type a String!)");
            String courseSubject = input.next();
            courseSubject = courseSubject.toUpperCase();
            System.out.println("What's the course number? (Please type an integer!)");
            int courseNumber = input.nextInt();
            System.out.println("Here is the list of section of the course " + courseSubject + courseNumber + ":"
                    + userStudent.showAllSectionOfThisCourseInWorkList(courseSubject, courseNumber));
            List<Integer> allSections = userStudent.showAllSectionOfThisCourseInWorkList(courseSubject, courseNumber);
            if (allSections.isEmpty()) {
                System.out.println("You have not add any section for this course!!!");
            } else {
                deleteASectionProcess(courseSubject, courseNumber);

            }
        }
    }

    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: delete a section for a course which is in the ourse work list.
    private void deleteASectionProcess(String courseSubject, int courseNumber) {
        System.out.println("Which is the section you want to delete? (Please type an integer!)");
        int sectionNumber = input.nextInt();
        if (userStudent.deleteOneCourseSection(courseSubject, courseNumber, sectionNumber)) {
            userStudent.deleteOneCourseSection(courseSubject, courseNumber, sectionNumber);
            System.out.println("Section " + sectionNumber + " of " + courseSubject + courseNumber
                    + " has been deleted!");
        } else {
            System.out.println("Your work list doesn't contain this section.");
            workListProcess();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select the actions for course registered list.
    private void registeredListProcess() {
        String command = null;
        System.out.println("Please Select from:");
        System.out.println("\to -> open my registered list");
        System.out.println("\treg -> register a section of a course");
        System.out.println("\tdrop-> drop a section of a course");
        System.out.println("\tret -> returns to the last step");
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("o")) {
            showRegisteredList();
        } else if (command.equals("reg")) {
            registerCourseProcess();
        } else if (command.equals("drop")) {
            dropASectionOfCourse();
        } else if (command.equals("ret")) {
            System.out.println("Welcome to HomePage!");
        } else {
            System.out.println("please type a valid letter! ");
            workListProcess();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to show his/her own registered list.
    private void showRegisteredList() {
        List<Course> registerList = userStudent.getRegistereddList();
        int courseNumInRegisterList = registerList.size();
        System.out.println("You have " + courseNumInRegisterList + " course(s) in the registered list: "
                + userStudent.showALlCourseInRegisteredList());
        showSectionInRegisteredList();

    }

    // MODIFIES: this
    // EFFECTS: prompts user to show all sections of a course his/her wants to check in course registered list.
    private void showSectionInRegisteredList() {
        System.out.println("See all the sections of a course? (yes or no)");
        String choice = input.next();
        if (choice.equals("yes")) {
            System.out.println("Please type the subject of the course (Please type a String!)");
            String courseSubject = input.next();
            courseSubject = courseSubject.toUpperCase();
            System.out.println("Please type the course number of this course? (Please type an integer!)");
            int courseNumber = input.nextInt();
            System.out.println(userStudent.showAllSectionOfThisCourseInRegisteredList(courseSubject, courseNumber));
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to register a new course section and adds it to the course registered list.
    private void registerCourseProcess() {
        System.out.println("What's the subject of the course you want to register? (Please type a String!)");
        String courseSubject = input.next();
        courseSubject = courseSubject.toUpperCase();
        System.out.println("What's the course number of this course? (Please type an integer!)");
        int courseNumber = input.nextInt();
        if (userStudent.repetitiveCourseInRegisteredList(courseSubject, courseNumber)) {
            alreadyRegister();
        } else {
            System.out.println("Which section you want to register (Please type an integer!)");
            int sectionNumber = input.nextInt();
            userStudent.registerCourse(courseSubject, courseNumber, sectionNumber);
            System.out.println("The course " + courseSubject + courseNumber + "-" + sectionNumber
                    + " has been registered!!!");

        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to drop a certain section of a course.
    private void dropASectionOfCourse() {
        List<Course> registereddList = userStudent.getRegistereddList();
        if (registereddList.isEmpty()) {
            System.out.println("Your course registered list is empty, so you cannot delete a section!");
        } else {
            System.out.println("What's the subject of the course you want to drop? (Please type a String!)");
            String courseSubject = input.next();
            courseSubject = courseSubject.toUpperCase();
            System.out.println("What's the course number? (Please type an integer!)");
            int courseNumber = input.nextInt();
            System.out.println("Here is the list of section of the course " + courseSubject + courseNumber + ":"
                    + userStudent.showAllSectionOfThisCourseInRegisteredList(courseSubject, courseNumber));
            List<Integer> allSections = userStudent.showAllSectionOfThisCourseInRegisteredList(courseSubject,
                    courseNumber);
            if (allSections.isEmpty()) {
                System.out.println("You have not add any section for this course!!!");
            } else {
                dropASectionOfCourseProcess(courseSubject, courseNumber);

            }
        }
    }

    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: drops a specific section of a course; goes back to the panel for choosing the actions for the course
    // work list if the registered list does not include this course.
    private void dropASectionOfCourseProcess(String courseSubject, int courseNumber) {
        System.out.println("Which is the section you want to drop? (Please type an integer!)");
        int sectionNumber = input.nextInt();
        if (userStudent.dropOneCourseSection(courseSubject, courseNumber, sectionNumber)) {
            userStudent.dropOneCourseSection(courseSubject, courseNumber, sectionNumber);
            System.out.println("Section " + sectionNumber + " of " + courseSubject + courseNumber
                    + " has been dropped!");
        } else {
            System.out.println("Your registered list doesn't contain this section.");
            workListProcess();
        }
    }

    // EFFECTS: tells the user that they have previously registered for a course and directs them to return to the
    // panel to choose their actions for the list of registered courses.
    public void alreadyRegister() {
        System.out.println("You have already registered this course!!!");
        registeredListProcess();

    }


}
