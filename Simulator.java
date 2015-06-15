import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
 * The simulator of the busses.
 *
 */
public class Simulator {

    public static Scanner input = new Scanner(System.in);

    public static int numInBusses,numOutBusses, minGroupSize, maxGroupSize, groupsServed, totalTimeWaited,dur;
    public static double arrivalProb;

    public static boolean running = true;
    public static ArrayList<Bus> inBuses = new ArrayList<Bus>();
    public static ArrayList<Bus> outBuses = new ArrayList<Bus>();
    public static ArrayList<ArrayList<Bus>> busRoute = new ArrayList<ArrayList<Bus>>();

    static PassengerQueue southP1 = new PassengerQueue();
    static PassengerQueue West = new PassengerQueue();
    static PassengerQueue SAC = new PassengerQueue();
    static PassengerQueue Chapin = new PassengerQueue();
    static PassengerQueue SouthP2 = new PassengerQueue();
    static PassengerQueue PathMart = new PassengerQueue();
    static PassengerQueue Walmart = new PassengerQueue();
    static PassengerQueue Target = new PassengerQueue();

    static String stopInfo[] = {"South P", "West", "SAC", "Chapin", "South P", "PathMart", "Walmart", "Target"};

    static ArrayList<PassengerQueue> passQInfo = new ArrayList<PassengerQueue>();

    /**
     * This class will generate passengers as the probability that the user entered.
     * The in route and out route busses will be generated as the user entered.
     * It will run the simulator and return a string array with the groupServed and totalWaiting time.
     * @param duration is the duration that the simulator needs to run
     * @return a string array storing the groupserved and totalWaitingTime
     */
    public static double[] simulate(int duration){
        busRoute.add(inBuses);
        busRoute.add(outBuses);

        passQInfo.add(southP1);
        passQInfo.add(West);
        passQInfo.add(SAC);
        passQInfo.add(Chapin);
        passQInfo.add(SouthP2);
        passQInfo.add(PathMart);
        passQInfo.add(Walmart);
        passQInfo.add(Target);

        double[] lastMessage = new double[2];

        for(int i = 0; i< numInBusses;i++){
            int inResting = i*30;
            Bus aa = new Bus(inResting,true);
            inBuses.add(aa);
        }
        for(int i = 0; i< numOutBusses;i++){
            int outResting = i* 30;
            Bus bb = new Bus(outResting,false);
            outBuses.add(bb);
        }

        for(int i = 1 ; i <=duration ; i++){
            String busInfo = "";
            String onBoardPass ="";
            String curMin = "Minute";
            String stopIn = "";

            curMin = curMin+" "+ i;

            //generate all the passengers and add them to the queue
            for(int k = 0; k < passQInfo.size();k++){
                if(isOccur()){
                    int numPass = (int)(Math.random()*(maxGroupSize-minGroupSize+1))+minGroupSize;
                    int dest;
                    boolean isValid = false;
                    if(k<4){
                        int generatedDest = (int)(Math.random()*4);
                        while (generatedDest==k || generatedDest < k){
                            generatedDest = (int)(Math.random()*4);
                            if(k!=0&&generatedDest==0){
                                break;
                            }
                        }
                        dest = generatedDest;
                    }else {
                        int generatedDest = (int)(Math.random()*4)+4;
                        while (generatedDest==k||generatedDest<k){
                            generatedDest = (int)(Math.random()*4)+4;
                            if(k!=4&&generatedDest==4){
                                break;
                            }
                        }
                        dest = generatedDest;
                    }
                    Passenger p = new Passenger(numPass,i, dest);
                    passQInfo.get(k).add(p);
                  //  System.out.println(k);
                    String arrivedStop = stopInfo[k];
                    String destStop = stopInfo[dest];
                    onBoardPass = onBoardPass+ "A group of "+ numPass + " passengers arrived at "+ arrivedStop + " heading to " + destStop +".\n";
                }
            }

            // to get the message of all the busses
            for(int l = 0; l<busRoute.size();l++){
                for (int j = 0; j<busRoute.get(l).size(); j++){
                    String toStop;
                    int unloadNum=0;
                    /////
                    if(busRoute.get(l).get(j).toNextStop==1){
                        busRoute.get(l).get(j).setToNextStop();
                    }
                    if(!busRoute.get(l).get(j).isResting()) {

                        int busStopIndex = busRoute.get(l).get(j).getNextStop();
                        if (busRoute.get(l).get(j).isArrivedStop()) {

                            busRoute.get(l).get(j).setToNextStop();

                            if(busRoute.get(l).get(j).numPassenger!=0){
                                unloadNum = busRoute.get(l).get(j).unload(busStopIndex);
                            }
                            int numPeople = 0;
                            if(!passQInfo.get(busStopIndex).isEmpty()){

                                for (int dd = 0; dd<passQInfo.get(busStopIndex).size();dd++) {
                                    int availableSeat = busRoute.get(l).get(j).getRemainSeat();
                                    Passenger p = passQInfo.get(busStopIndex).dequeue(availableSeat);
                                    if(p!=null) {
                                        totalTimeWaited = totalTimeWaited + (i - p.getTimeArrived());
                                        numPeople += p.getNumPassenger();
                                        busRoute.get(l).get(j).addPassenger(p);
                                        groupsServed = groupsServed + 1;
                                        dd--;
                                    }
                                }
                            }

                            if(unloadNum==0) {
                                toStop = " arrives at " + stopInfo[busStopIndex] + ". Picks up " + numPeople + " passengers.";
                            }else {
                                toStop = " arrives at " + stopInfo[busStopIndex] + " . Drops off "+ unloadNum + " passengers"+". Picks up " + numPeople + " passengers.";
                            }
                            if((busStopIndex==3&&busRoute.get(l).get(j).toNextStop==1)||(busStopIndex==7&&busRoute.get(l).get(j).toNextStop==1)){
                                busRoute.get(l).get(j).setTimeToRest(30);
                            }
                        } else {
                            busRoute.get(l).get(j).setToNextStop();
                            toStop = " is moving toward " + stopInfo[busStopIndex] + ". Arrives in " + busRoute.get(l).get(j).getToNextStop() + " minutes.";
                        }
                    }else {
                        String dropMes = "";
                        if(busRoute.get(l).get(j).numPassenger!=0){
                            dropMes = dropMes + " Drops off "+busRoute.get(l).get(j).unload(busRoute.get(l).get(j).getNextStop())+" passengers.";
                        }
                        toStop = " is resting at "+stopInfo[busRoute.get(l).get(j).getNextStop()]+ " for "+busRoute.get(l).get(j).getTimeToRest()+" minutes." + dropMes;
                        busRoute.get(l).get(j).setTimeToRest();
                    }

                    String whatRoute;
                    if(l == 0){
                        whatRoute = "In Route Bus ";
                    }else {
                        whatRoute = "Out Route Bus ";
                    }
                    busInfo = busInfo+ whatRoute + (j+1) +  toStop+"\n";
                }
            }

            // for the passenger lines
            for(int k = 0; k < passQInfo.size() ; k++){
                stopIn = stopIn+ k + " "+"("+stopInfo[k]+") : "+ passQInfo.get(k).toString()+"\n";
            }

            printMessage(curMin,onBoardPass,busInfo,stopIn);

        }
        lastMessage[0]=groupsServed;
        if(groupsServed!=0) {
            lastMessage[1] = (double)totalTimeWaited / lastMessage[0];
        }else {
            lastMessage[1] = -1;
        }
        return lastMessage;
    }

    /**
     * Prompt the user to enter number of in route busses and out route busses. Also prompting user to enter the minimum size and maximum
     * size of group, capacity of busses, and the duration of simulation.
     */
    public static void main(String[] args){

        while(running) {
            try {

                System.out.print("Enter the number of In Route busses: ");
                numInBusses = input.nextInt();
                System.out.print("Enter the number of Out Route busses: ");
                numOutBusses = input.nextInt();
                System.out.print("Enter the minimum group size of passengers: ");
                minGroupSize = input.nextInt();
                System.out.print("Enter the maximum group size of passengers: ");
                maxGroupSize = input.nextInt();
                if (maxGroupSize < minGroupSize) {
                    throw new WrongGroupSizeException();
                }
                System.out.print("Enter the capacity of a bus: ");
                Bus.CAPACITY = input.nextInt();
                if (Bus.CAPACITY < 1) {
                    throw new WrongCapacityException();
                }
                System.out.print("Enter the arrival probability: ");
                arrivalProb = input.nextDouble();
                if (arrivalProb > 1 || arrivalProb < 0) {
                    throw new WrongArrivalProb();
                }
                System.out.print("Enter the duration of the simulation: ");
                dur = input.nextInt();
                if (dur < 0) {
                    throw new WrongDurationException();
                }
                double[] result = simulate(dur);

                String displayMes;
                if (groupsServed > 0) {
                    double averageTime = ((double) totalTimeWaited) / groupsServed;
                    String fff = String.format("%.2f", averageTime);
                    displayMes = groupsServed + " groups of passengers served. Average wait time is " + fff + " minutes.";
                } else {
                    displayMes = "0 group of passengers served. Average wait time is NaN.";
                }
                System.out.println("\n" + displayMes);
                System.out.print("Perform another simulation(Y/N): ");
                String prompt = input.next();
                prompt = prompt.toLowerCase();
                if (prompt.equals("n")) {
                    running = false;
                    System.out.println("Program terminating...");
                } else {
                    inBuses.clear();
                    outBuses.clear();
                    busRoute.clear();
                    for (int i = 0; i < passQInfo.size(); i++) {
                        passQInfo.get(i).clear();
                    }
                    groupsServed = 0;
                    totalTimeWaited = 0;
                    passQInfo.clear();
                }

            } catch (WrongGroupSizeException e) {

                System.out.println("Your minimum group size is bigger than the maximum group size.");
            } catch (WrongArrivalProb e) {
                System.out.println("Your arrival probability should be between 0 to 1.");
            } catch (WrongDurationException e) {
                System.out.println("The duration cannot be negative.");
            } catch (WrongCapacityException e) {
                System.out.println("The capacity of the bus should be bigger than 0.");
            }

        }

    }

    /**
     * This method will print the message on the console every minute.
     * @param curMin is the current minute.
     * @param onBoardPass is all the information of passengers arrived at passenger queue at current minute.
     * @param busInfo is the information of all the busses.
     * @param PassLine is the information of all the passenger queues.
     */
    public static void printMessage(String curMin, String onBoardPass, String busInfo, String PassLine){
        System.out.println(curMin);
        System.out.print(onBoardPass);
        System.out.print(busInfo);
        System.out.print(PassLine);
    }

    /**
     * The method to determine whether a new group of passenger should be generated at current minute.
     * @return true if passenger should be generated, false otherwise
     */
    public static boolean isOccur(){
        double i = Math.random();
        if(i<arrivalProb){
            return true;
        }else
            return false;
    }


}
