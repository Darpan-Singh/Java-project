import java.util.ArrayList;

import common.*;
import demo.*;

public class Main {

	public static void main(String[] args) {
		
		Game game = new Game_0133();
		ArrayList<Machine> machines = new ArrayList<>();
		
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
		int sleepTime = 2000;
		
		// Try not to change anything beyond this
		
		for(Machine machine: machines) {
			machine.setStepSize(stepSize); // pixels to move in each step
			machine.start(); // starts the machine running in its own thread
		}
		
		game.addMachines(machines, numFaults);
		
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
	}
}
