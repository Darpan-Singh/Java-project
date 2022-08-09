package demo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import common.Game;
import common.Machine;

public class Game_0133 extends Game
{
	private ArrayList<Machine> mc_list = new ArrayList<Machine>();
	private int fault_mc;

	@Override
	public void addMachines(ArrayList<Machine> machines, int numFaulty)
	{
		//Machines list to all
		for(Machine temp:machines) temp.setMachines(machines);

		// a temporary copy of machines 
		mc_list.addAll(machines);

		fault_mc = numFaulty;
	}

	private int phase_num = 0;

	@Override
	public void startPhase()
	{
		Random random = new Random();
		phase_num++;

		ArrayList<Machine> temp_mc_list = new ArrayList<Machine>(mc_list);
		Collections.shuffle(temp_mc_list);

		for(Machine x: mc_list) x.setState(true);
		
		// Random ly seklected faulkty machines
		for(int i = 0; i<fault_mc; i++) temp_mc_list.get(i).setState(false);

		// Random machine as leader
		int rand = random.nextInt(mc_list.size());
		Machine mc_leader = mc_list.get(rand);

		// Function setter call
        mc_leader.setLeader();
	}

}
