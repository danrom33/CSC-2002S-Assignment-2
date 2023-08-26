package clubSimulation;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



public class AndreBarman extends Thread {
    PeopleLocation barpersonLocation;
    private Clubgoer[] customers;
    public AtomicBoolean busy = new AtomicBoolean(false);
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
		int barman_x = (int) (Math.random()*grid.getMaxX());
		int barman_y = grid.getBar_y()+1;
		try{
			GridBlock barmanPos = new GridBlock(barman_x,barman_y,false,false,false);
			barpersonLocation.setLocation(barmanPos);
		} catch(InterruptedException ex){
			System.out.println(ex.getMessage());
			System.exit(0);
		}
        currentBlock = barpersonLocation.getLocation();
    }

    private void checkPause() throws InterruptedException {
		synchronized(Clubgoer.simPaused){
			while(Clubgoer.simPaused.get())
				Clubgoer.simPaused.wait();
		}  	
        
    }
	private void startSim() throws InterruptedException {
		synchronized(Clubgoer.simStart){
			while(!Clubgoer.simStart.get())
				Clubgoer.simStart.wait();
		}	
        
    }

    public synchronized void serveDrink(){
            try{
                busy.set(true);
                currentBlock = grid.move(currentBlock, 0, 1, barpersonLocation);
                sleep(movingSpeed); //pouring drink
                currentBlock = grid.move(currentBlock, 0, -1, barpersonLocation);
                sleep(movingSpeed); //customer paying
                busy.set(false);
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
                if(currentBlock == grid.move(currentBlock, currentDirection, 0, barpersonLocation))
                    currentDirection*=-1;
                currentBlock = grid.move(currentBlock, currentDirection, 0, barpersonLocation);
                synchronized(serving){
                    int customer = grid.customerWaiting(barpersonLocation);
                    serving.set(customer);
                    if(serving.get() != -1){
                        if(customers[serving.get()].getThirsty())
                            serveDrink();
                    }        
                    serving.notifyAll();
                }
                sleep(movingSpeed);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
