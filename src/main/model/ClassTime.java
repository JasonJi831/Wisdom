package model;


// Represents a course time with a specified day and time.
public class ClassTime {
    private String day;
    private int startingTime;
    private int endingTime;


    // EFFECTS: constructs a course time which contains an empty list of scheduled class day.
    public ClassTime(String day, int startingTime, int endingTime) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;

    }


    // REQUIRES: both given time should be an integer in a 24-hour format.(e.g. (0)900 stands for 9:00 or 9:00AM,
    // 1400 stands for 14:00 or 2:00Pm). If the first digit is 0, it should be omitted. Also, both given times should
    // fall within the range of (0, 2400).
    // EFFECTS: returns true if the course conflicts with the given course, meaning that there is at least one overlap
    // between the start and end times of the courses and the given course; otherwise, return false.
    public Boolean ifConflict(ClassTime classTime) {
        while (day == classTime.getDay()) {
            return this.startingTime <= classTime.getStartingTime() && classTime.getStartingTime() < this.endingTime;
        }
        return false;
    }


    // Requires: the given string should be one of: "Mon", "Tue", "Wed", "Thu", or "Fri".
    // MODIFIES: this
    // EFFECTS: set the specific day of the course time to a given day.
    public void setDay(String day) {
        this.day = day;

    }


    // REQUIRES: both given time should be an integer in a 24-hour format.(e.g. 900 stands for 9:00 or 9:00AM,
    // 1400 stands for 14:00 or 2:00Pm), and both given times should fall within the range of (0, 2400).
    // Also, starting time < ending time.
    // MODIFIES: this
    // EFFECTS: set the starting and ending time of the course to two given times respectively.
    public void setTime(int startingTime, int endingTime) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;

    }


    // EFFECTS: returns a list of strings representing the days of the week when the course is scheduled.
    public String getDay() {
        return day;
    }


    // EFFECTS: returns the starting time of a course
    public int getStartingTime() {
        return startingTime;
    }

    // EFFECTS: returns the ending time of a course
    public int getEndingTime() {
        return endingTime;
    }

}