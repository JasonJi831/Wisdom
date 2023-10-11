# My Personal Project

## Phase 0:

### *What will the application do?*

Given the widespread use of the course registration system in education, I would like to design an application
similar to the course registration system for a course coordinator in charge of many students.
This application will help the course coordinator efficiently register courses for students,
handle their wait lists, switching sections for students, drop courses for students and
streamlining the course guidance process.


### *Who will use it?*

This application will primarily serve our university, specifically for the course coordinators. It may also
provide essential tools for university staff and faculty.



### *Why is this project of interest to you?*

I am interested in making such an application for two reasons.
Firstly, it's an opportunity to apply what I've learned in this course to solve a
practical problem. Secondly, I'm passionate about helping the course coordinator
save time in course scheduling, making their work smoother and more efficient.


### User Stories:
- As a user, I want to add a student to a coordinator's student list. (I can add as many students as I want!)
  --*addStudent()*
- As a user, I want to view all students handled by a coordinator.--*getAllStudents()*
- As a user, I want to add a course to the course work list for a specific student.-*addCourseToWorkList()*
- As a user, I want to add a course to the course wait list for a specific student.-*addCourseToWaitList()*
- As a user, I want to register a course.--*registerCourse()*
- As a user, I want to check if there are available seats in a course.--*ifSeatAvailable()*

- As a user, I want to drop a course for a student. (Removing a course from the
  registered course list) --*dropCourse()*
- As a user, I want to remove of a course from a student's course work list. --*deleteCourseFromWorkList()*
- As a user, I want to remove of a course from a student's course wait list. --*removeCourseFromWaitList()*
- As a user, I want to reduce the remaining available seats by one because someone has registered for this course.
  --*remainingSeatsAdjust()*



