/**
 * Ka Wing Fong
 * 109794011
 * HW 4
 * CSE 214-R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author Ka Wing Fong
 */

/**
 * The class that store the information for each group of passengers
 */
public class Passenger {
    //This class represents a group of passengers; the group can be a single person as well.
    // This class should keep instance variables that represents the size of the group,
    // the destination, and the time arrived at bus stop (int).


    int numPassenger, timeArrived;
    int destination;

    /**
     * The constructor of this class. A passenger object will be created if this method is called.
     * @param numPassenger the number of passenger of a group
     * @param timeArrived is the time that the passenger arrived to the bus stop
     * @param destination is the destination that the passenger is going to.
     */

    //hello world testing git
    public Passenger(int numPassenger, int timeArrived, int destination) {
        this.numPassenger = numPassenger;
        this.timeArrived = timeArrived;
        this.destination = destination;
    }

    /**
     * Return the number of passenger as an integer.
     * @return the number of passenger in this group
     */
    public int getNumPassenger() {
        return numPassenger;
    }

    /**
     * return the arrival time of passenger as an integer.
     * @return the time that the passenger arrived the bus stop.
     */
    public int getTimeArrived() {
        return timeArrived;
    }


    /**
     * Return the destination as an integer.
     * @return the destination of the passenger.
     */
    public int getDestination() {
        return destination;
    }


}
