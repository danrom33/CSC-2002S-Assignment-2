package clubSimulation;

import java.util.concurrent.atomic.AtomicInteger;

// GridBlock class to represent a block in the club.
// only one thread at a time "owns" a GridBlock

public class GridBlock {
	private int isOccupied; 
	private final boolean isExit;  //is tthis the exit door?
	private final boolean isBar; //is it a bar block?
	private final boolean isDance; //is it the dance area?
	private int [] coords; // the coordinate of the block.
	
	GridBlock(boolean exitBlock, boolean barBlock, boolean danceBlock) throws InterruptedException {
		isExit=exitBlock;
		isBar=barBlock;
		isDance=danceBlock;
		isOccupied= -1;
	}
	
	GridBlock(int x, int y, boolean exitBlock, boolean refreshBlock, boolean danceBlock) throws InterruptedException {
		this(exitBlock,refreshBlock,danceBlock);
		coords = new int [] {x,y};
	}
	
	public  int getX() {return coords[0];}  
	
	public  int getY() {return coords[1];}
	
	public synchronized boolean get(int threadID) throws InterruptedException {
		if (isOccupied==threadID) return true; //thread Already in this block
		if (isOccupied>=0) return false; //space is occupied
		isOccupied=threadID;  //set ID to thread that had block
		return true;
	}

	//returns ID of thread occupying block
	public synchronized int getOccupied(){
		return isOccupied;
	}
		

	public void release() {
		//When thread moves out of a block, notifies all threads waiting on this block (used for 1 patron occupying entrance block at same time)
		synchronized(this){
			isOccupied=-1;
			this.notifyAll();
		}
	}
	
	public synchronized boolean occupied() {
		if(isOccupied==-1) return false;
		return true;
	}
	
	public boolean isExit() {
		return isExit;	
	}

	public   boolean isBar() {
		return isBar;
	}
	public   boolean isDanceFloor() {
		return isDance;
	}

}
