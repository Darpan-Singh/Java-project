package common;

import java.util.ArrayList;

public abstract class Game {


	// set up the game by passing in a list of Machines, and "t", the number of
	// faulty machines. 
	public abstract void addMachines(ArrayList<Machine> machines, int numFaulty);

	// called when a new phase is to be started
	// selects a new leader, identifies the faulty machines, and initiates agreement
	// If first 2 methods are implemented, then the leader and faulty machines can be
	// used from that.
	public abstract void startPhase();
	
	// Second version of startPhase for cases where leader, correct machines 
	// are chosen outside of Game (e.g in  GUI or main)
	// leader - the leader chosen
	// areCorrect: boolean list indicating if the i'th machine is correct (true)
	//    or faulty (false)
	// Note that the number of machines marked as faulty will be <= numFaulty 
	// defined in numFaulty
	// 
	// Default implementation ignores the input parameters and invokes 
	// the startPhase that takes no parameters. Hence, existing implementations
	// of Game are not impacted
	// 
	public void startPhase(int leaderId, ArrayList<Boolean> areCorrect) {
		startPhase();
	}

}
