import java.util.ArrayList;

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
 * The bus object stores int CAPACITY as the number of passengers that can board on the bus.
 */
public class Bus {


    public static int CAPACITY;
    String route;
    int nextStop ;
    int toNextStop;
    int timeToRest;
    int numPassenger;
    boolean isInBus;
    public ArrayList<Passenger> numPass = new ArrayList<Passenger>();

    /**
     * The constructor of bus object. Take 2 parameters timeRest and isIn. The nextStop will be set to 0 if it is an InRoute bus, 4 otherwise.
     * @param timeRest is the time that the bus need to rest at South P.
     * @param isIn is a boolean result that states whether the bus is In Route bus. True if it is. False otherwise.
     */
    public Bus(int timeRest, boolean isIn){
        if(isIn) {
            nextStop = 0;
        }else {
            nextStop = 4;
        }
        numPassenger = 0;
        toNextStop = 0;
        if(timeRest == 0) {
            timeToRest = timeRest;
        }else {
            timeToRest = timeRest - 1;
        }
        this.isInBus = isIn;
    }

    /**
     * The method will unload the passenger if the bus arrived to their destinations.
     * @param dest is the destination.
     * @return the number of passenger that unloaded.
     */
    public int unload(int dest){
        int numUnload = 0;
        for (int i = 0; i< numPass.size();i++){
            Passenger a = numPass.get(i);
            if(a.getDestination()==dest){
                numUnload += a.getNumPassenger();
                numPassenger -= a.getNumPassenger();
                numPass.remove(i);
                i--;
            }
        }
        return numUnload;
    }

    /**
     *
     * @return true if the bus is in route bus, false otherwise
     */
    public boolean isInBus() {
        return isInBus;
    }

    /**
     *
     * @return return the capacity of the bus
     */
    public static int getCAPACITY() {
        return CAPACITY;
    }

    /**
     *
     * @param CAPACITY the capacity that needs to be set for the bus
     */
    public static void setCAPACITY(int CAPACITY) {
        Bus.CAPACITY = CAPACITY;
    }

    /**
     *
     * @return whether the bus is in route or out route
     */
    public String getRoute() {
        return route;
    }

    /**
     *
     * @param route is the type of bus
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     *
     * @return the next stop of the bus
     */
    public int getNextStop() {
        return nextStop;
    }

    /**
     *
     * @param nextStop change the next stop
     */
    public void setNextStop(int nextStop) {
        this.nextStop = nextStop;
    }

    /**
     *
     * @return how many minute until arrive the next stop
     */
    public int getToNextStop() {
        int a =  this.toNextStop;
        return a;
    }

    /**
     * Change the time to next stop
     */
    public void setToNextStop() {
        if(toNextStop!=0) {
            this.toNextStop--;
        }else {
            this.toNextStop = 20;
            if(isInBus) {
                this.nextStop = (nextStop + 1) % 4;
                if(this.nextStop == 0){
                    this.timeToRest = 30;
                    this.toNextStop = 0;
                }
            }else {
                this.nextStop = (nextStop+1)%4 +4;
                if(this.nextStop ==4){
                    this.timeToRest = 30;
                    this.toNextStop = 0;
                }
            }
        }
    }

    /**
     *
     * @return how long the bus needs to stay in south P and takes rest
     */
    public int getTimeToRest() {
        return timeToRest;
    }

    /**
     * change the time of the bus to rest
     */
    public void setTimeToRest() {
        if(this.timeToRest>0){
            this.timeToRest--;
        }
    }

    /**
     *
     * @return the number of passenger
     */
    public int getNumPassenger() {
        return numPassenger;
    }

    /**
     *
     * @param numPassenger is the number of passenger in the bus that needs to be set
     */
    public void setNumPassenger(int numPassenger) {
        this.numPassenger = numPassenger;
    }

    /**
     *
     * @param a is the group of passenger board on the bus
     */
    public void addPassenger(Passenger a){
        numPass.add(a);
        numPassenger += a.getNumPassenger();
    }

    /**
     *
     * @return true if the bus is resting
     */
    public boolean isResting(){
        if (timeToRest>0){
            return true;
        }else
            return false;
    }

    /**
     *
     * @param a the remaining time that the bus can rest
     */
    public void setTimeToRest(int a){
        this.timeToRest = a;
    }

    /**
     *
     * @return true if the bus arrives a stop, false otherwise
     */
    public boolean isArrivedStop(){
        boolean result = false;
        if(toNextStop==0 ){
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    /**
     *
     * @return the number of remaining seat in the bus
     */
    public int getRemainSeat(){
        return (CAPACITY - numPassenger);
    }
}
