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
 * The queue that stores the information of passengers standing on the line.
 */
public class PassengerQueue extends ArrayList<Passenger>{


    String stopInfo[] = {"South P", "West", "SAC", "Chapin", "South P", "PathMart", "Walmart", "Target"};
    public void enqueue(Passenger p){
        this.add(p);
    }


    /**
     * Remove passengers from the lines.
     * @param numPas is the remaining seat available in the bus.
     * @return the group of passenger which can board the bus successfully.
     */
    public Passenger dequeue(int numPas){
        if(!this.isEmpty()){
            Passenger a =  null;
            int pos = 0;
            while( pos<this.size() && this.get(pos).getNumPassenger()>numPas ){
                pos++;
            }
            if(pos==this.size()){
                return null;
            }
            a = this.get(pos);
            this.remove(pos);
            return a;
        }else
            return null;
    }

    /**
     * Take the first passenger of the line without removing it.
     * @return the passenger on the line.
     */
    public Passenger peek(){
        return this.get(0);
    }

    /**
     * Return the total number of group of passengers in the line.
     * @return the number of groups on the line.
     */
    public int size(){
        return super.size();
    }

    /**
     * The method to evaluate if the passenger queue is empty.
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty(){
        return super.isEmpty();
    }

    /**
     * The method to put the necessary information of passenger in a queue in a string.
     * @return the information of all the passengers in a formatted string.
     */
    public String toString(){
        String result = "";
        for(int i = 0; i < size() ; i++) {
            int numPas = this.get(i).getNumPassenger();
            int destination = this.get(i).getDestination();
            int arrivedTime = this.get(i).getTimeArrived();
            result += "[" + numPas+","+destination+" ("+ stopInfo[destination] +")"+", "+ arrivedTime+ "]"+"  ";
        }
        return result;
    }
}
