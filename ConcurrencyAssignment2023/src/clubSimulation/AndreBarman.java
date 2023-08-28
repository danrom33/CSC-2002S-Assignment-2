package clubSimulation;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;



public class AndreBarman extends Thread {
    PeopleLocation barpersonLocation;
    private Clubgoer[] customers;
    public AtomicInteger serving = new AtomicInteger(-1);


    int noPatrons;
    ClubGrid grid;
    GridBlock currentBlock;

    int movingSpeed;
    Random rand = new Random();

    public AndreBarman(ClubGrid club, Clubgoer[] customers, int noPatrons, int movingSpeed){
        this.grid = club;
        this.customers = customers;
        this.noPatrons = noPatrons;
        this.movingSpeed = movingSpeed;
        barpersonLocation = new PeopleLocation(noPatrons);
		int barman_x = (int) (Math.random()*grid.getMaxX()); //start at a random x position
		int barman_y = grid.getBar_y()+1; //start one y coordinate behind bar
		try{
			currentBlock = new GridBlock(barman_x,barman_y,false,false,false); //create instance of the barman's starting grid block
			barpersonLocation.setLocation(currentBlock); //Set the barman's location to the starting grid block
		} catch(InterruptedException ex){
			System.out.println(ex.getMessage());
			System.exit(0);
		}
    }

    //Keeps barman waiting untill simulation is resumed
    private void checkPause() throws InterruptedException {
		synchronized(Clubgoer.simPaused){
			while(Clubgoer.simPaused.get())
				Clubgoer.simPaused.wait();
		}  	
        
    }
    //Keeps barman waiting untill simukation is started
	private void startSim() throws InterruptedException {
		synchronized(Clubgoer.simStart){
			while(!Clubgoer.simStart.get())
				Clubgoer.simStart.wait();
		}	
        
    }


    //Barman serves drink t customer in front of him
    public synchronized void serveDrink(){
            try{
                currentBlock = grid.move(currentBlock, 0, 1, barpersonLocation); //moves back a row to pur the drink
                sleep(movingSpeed/2); 
                currentBlock = grid.move(currentBlock, 0, -1, barpersonLocation); //moves forward to hand drink to customer
            } catch (InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }

    

    public void run(){
        int currentDirection = 1;
        try {
            startSim();
            while(true){
                checkPause();
                //Barman moves in a straight line exactly 1 row behind the bar. If he reaches the end of the bar, he changes direction
                if(currentBlock == grid.move(currentBlock, currentDirection, 0, barpersonLocation))
                    currentDirection*=-1;
                currentBlock = grid.move(currentBlock, currentDirection, 0, barpersonLocation);
                synchronized(serving){
                    int customer = grid.customerWaiting(barpersonLocation); //gets threadID of customer in front of him (-1 if noone)
                    serving.set(customer); //AtomicInteger set to threadID
                    if(serving.get() != -1){
                        if(customers[serving.get()].getThirsty())
                            serveDrink(); //If there is a customer in front of him and they are thirsty (not just wznadered in front of bar), serve them a drink
                    }
                    //notify all customers waaiting to be served. Customer with threadID that has been served will stop waiting
                    serving.notifyAll();
                }
                sleep(movingSpeed/2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
