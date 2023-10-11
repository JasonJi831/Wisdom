package model;

// Represents a course section with its three-digit section number, total seats capacity, total remaining seats, a list
// of sections, and a corresponding list of times.
// Note: Both lists should have the same length.


public class Section {
    private int sectionNumber;
    private int totalSeats;
    private int seatsRemaining;
    private ClassTime classTime;

    // EFFECTS: constructs a section with the given section number, zero total seats capacity and remaining seats and
    // the courseTime.
    public Section(int sectionNumber) {
        this.sectionNumber = sectionNumber;
        this.totalSeats = 0;
        this.seatsRemaining = 0;

    }


    // MODIFIES: this
    // EFFECTS: reduce the remaining available seats by one because someone has registered for this course.
    public void remainingSeatsAdjust() {
        seatsRemaining--;
    }


    // EFFECTS: returns true if there are available seats in a course; false otherwise.
    public Boolean ifSeatAvailable() {
        return seatsRemaining > 0;

    }






    // MODIFIES: this
    // EFFECTS: set this section's total seat capacity to a given integer number.
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;

    }

    // MODIFIES: this
    // EFFECTS: set this section's remaining seats equals to the seats capacity.
    public void setSeatsRemaining() {
        this.seatsRemaining = this.totalSeats;

    }



    // EFFECTS: return the section number of the course section.
    public int getSectionNumber() {
        return this.sectionNumber;
    }


    // EFFECTS: returns the total number of seats capacity of a section
    public int getTotalSeats() {
        return this.totalSeats;

    }


    // EFFECTS: returns the number of remaining seats of the course
    public int getRemainingSeats() {
        return this.seatsRemaining;

    }



}