# My Personal Project

## Phase 0:

### *What will the application do?*

Considering the widespread use of the course registration system in education, I would like to design an application
similar to the course registration system for students.
This application will help them efficiently adding courses to their course work list, registering courses, checking
their work list and registered course list, dropping courses and streamlining the course guidance process.


### *Who will use it?*

This application will primarily serve our university, specifically for the students who need a course 
registration system. It may also provide essential tools for university staff and faculty.



### *Why is this project of interest to you?*

I am interested in making such an application for two reasons.
Firstly, it's an opportunity to apply what I've learned in this course to solve a
practical problem. Secondly, I'm passionate about helping students save time in course scheduling, 
which makes their work smoother and more efficient.


### User Stories:

## Phase1
- As a user, I want to add a course to the course work list.-*addACourseToWorkList()*
- As a user, I want to add a section of a course which is in the course work list.--*addANewSectionToWorkList()*

- As a user, I want to register a course (add a course to the course registered list).-*registerCourse()*
- As a user, I want to show all of my courses in the course work list.-*showALlCourseInWorkList()*
- As a user, I want to show all of my courses in the course registered list.-*showALlCourseInRegisteredList()*

- As a user, I want to drop a course section. (Removing a course section from the registered course list) 
- --*dropOneCourseSection()*
## Phase2
- As a user, I want to save my current course work list or registered list for future use.
- As a user, when I open the course registration system, I want to be given the option to reload the previously 
saved work list and course registration list from where I left off at the last time.



## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by navigating to the 
"Options" menu at the top of the application, selecting "Course WorkList", and then clicking the "add courses" button
in the bottom panel.


- You can generate the second required action related to the user story "adding multiple Xs to a Y" by navigating to the
  "Options" menu at the top of the application, selecting "Registered List", and then clicking the "register courses"
  button in the bottom panel.


- You can generate the third action related to the user story "adding multiple Xs to a Y" by navigating to the
  "Options" menu at the top of the application, selecting "Course WorkList", and then clicking the "add a section"
  button in the bottom panel.


- You can locate my visual component, which is an image of the UBC badge, upon starting the GUI. 
It is displayed at the center of the login panel background.


- You can save the state of my application by attempting to close the window, 
which will trigger a prompt asking if you wish to save the current course work list and registered list. Clicking 
"For sure!" will save the state to a file.


- You can reload the state of my application by restarting the application after it has been closed. 
Upon restart, you will be prompted with the option to load the previous state. Clicking "For sure!" will load the state 
from the file.

## Phase 4: Task 2
### A representative sample of the events:
+ Mon Nov 27 21:14:37 PST 2023
+ Jason(666666) added the course: CPSC330 to his/her course workList!
+ Mon Nov 27 21:14:43 PST 2023
+ Jason(666666) added the section: 203 for the course CPSC330 to his/her course workList
+ Mon Nov 27 21:14:48 PST 2023
+ Jason(666666) added the section: 201 for the course CPSC330 to his/her course workList
+ Mon Nov 27 21:14:54 PST 2023
+ Jason(666666) removed the section: 203 of the course CPSC330 from his/her course workList!
+ Mon Nov 27 21:15:03 PST 2023
+ Jason(666666) registered the section: 201 for the course CPSC330!
+ Mon Nov 27 21:15:06 PST 2023
+ Jason(666666) dropped the course: CPSC330
+ Mon Nov 27 21:15:21 PST 2023
+ Jason(666666) dropped the course: CPSC110











