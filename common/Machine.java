package common;

import java.util.ArrayList;

public abstract class Machine extends Thread {

	public abstract void setMachines(ArrayList<Machine> machines);
	// The list of machines in the Game. Called on this machine before any game activity starts
	// The position of this machine in this list is its ID, which is used in sendMessage
	
	public abstract void setState(boolean isCorrect); 
	// informs the machine whether it is now a correct
	// or faulty machine. This can change in subsequent rounds

	public abstract void setLeader(); 
	// informs the machine that it should act as the leader
	// and that it should choose a decision and initiate a
	// new phase of the multi-round protocol
	// This is only for the current phase. 
	// Receiving a Round 0 message from another machine implies it is no longer the leader

	
	// called when a Machine wants to send a message to another machine
	public abstract void sendMessage(int sourceId, int phaseNum, int roundNum, int decision);
    // sourceID: id of the caller
    // phaseNum: current phase number. Each machine keeps track of the phase number
    // roundNum: round within the phase
    // decision: for now, we will assume it can be 0 (turn left) or 1 (turn right)

	public abstract void setStepSize(int stepSize);
	// the machine should move by "stepSize" in its direction of motion during each call to move()
	
	protected abstract void move(); /*
							 * the machine is expected to update its current position in the direction it
							 * wants to move. Note that the direction for correct machines changes only at
							 * the end of each phase. We therefore expect all correct machines to be moving
							 * in the same direction once a decision is reached in each phase. All machines
							 * initially start at 0,0, and initally move to the right.
							 * And update their position by stepSize pixels in the direction of movement. 
							 */
	
	public abstract String name(); // the name of the machine as decided by the derived class

	public abstract Location getPosition(); // machine should return its current position
	
	public void run() {
		while (true) {  // will be replaced with a better mechanism
			move();
			try {
				Thread.sleep(moveSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setMoveStep (int step) {
		moveSleep = step;
	}
	
	private static int moveSleep = 500;
}
