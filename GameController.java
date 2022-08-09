import java.util.ArrayList;

import common.Game;
import common.Location;
import common.Machine;

import demo.Game_0133;
import demo.Machine_0133;

public class GameController {

	public static void main(String[] args) {
		GameController controller = new GameController();
		controller.init();
	
	}
	
	void init() {
		
		// set up and initialize machines
		machines = new ArrayList<>();
		
		game = new Game_0133();
		
		// add a bunch of different machines. For now, all demo machines
		// These will be replaced by machines of different students
		// Add as many as you would like, and change numFaults accordingly (but less than 1/3 of total machines)
		
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		machines.add(new Machine_0133());
		
		// change the following as needed
		int numFaults = 2;
		int stepSize = 4;
		int dispTime = 200;
		Machine.setMoveStep(500);
		
		// Try not to change anything beyond this
		
		for(Machine machine: machines) {
			machine.setStepSize(stepSize); // pixels to move in each step
			machine.start(); // starts the machine running in its own thread
		}
		
		game.addMachines(machines, numFaults);
		
		
	
		// set up GUI
		gameDisp = new GameDisplay(this,machines.size());
		gameDisp.init();
		
		
		for (int i = 0; i < 2000; i++) {	
			gameDisp.clear();
			gameDisp.update();
			try {
				Thread.sleep(dispTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void startPhase(int leaderId, ArrayList<Boolean> correctMachines) {
		game.startPhase(leaderId, correctMachines);
		
	}

	public void updateView() {
		gameDisp.clear();
		for(Machine machine: machines) {
			Location pos = machine.getPosition();
			gameDisp.drawCircle(pos, 10);
			gameDisp.drawText(pos, machine.name());
			//System.out.println(pos + machine.name());
		}
		
	}

	private Game game;
	private GameDisplay gameDisp;
	ArrayList<Machine> machines;
	
}


/*
for(int i=0;i<100;i++) {
	for(Machine machine: machines) {
		Location pos = machine.getPosition();
		System.out.println(machine.name() + ":" + pos.getX() + "," + pos.getY() ); 
	}
	game.startPhase();
	try {
		Thread.sleep(sleepTime);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
*/
