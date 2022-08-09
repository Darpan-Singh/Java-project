package demo;

import common.Location;
import common.Machine;
import java.util.*;

public class Machine_0133 extends Machine
{
	private int id;
	private static int nextId = 0;

	public Machine_0133()
	{
		id = nextId++;
	}

	private ArrayList<Machine> mach_list = new ArrayList<Machine>();
	private int machine_num;

	@Override
	public void setMachines(ArrayList<Machine> machines)
	{
		mach_list.addAll(machines);
		machine_num = machines.size();
	}

	private boolean state;
	private static int fault_count=0;

	@Override
	public void setState(boolean isCorrect)
	{
		state = isCorrect;

		// Number of faulty machines
		if(!isCorrect) fault_count++;
	}

	private boolean leader_state = false;
	private static int phase = 0;

	@Override
	public void setLeader()
	{
		phase++;
		leader_state = true;
				
		//Round 0
		this.sendMessage(id,phase,0,404);

		//Round 1: All of the machines relay all of the messages they receive to the others. 
        for(Machine temp:mach_list) 
		{
			temp.sendMessage((int)temp.getId(),phase,1,404);
		}

		//Round 2
		for(Machine temp:mach_list)
		{
			temp.sendMessage((int)temp.getId(),phase,2,404);
		}

		leader_state = false;
		fault_count = 0;
	}


	// If choice = 0 -> right turn 
	// If choice = 1 -> left turn
	// If choice = 2 -> no msg sent
	private int l = 0 , r = 0;

	// First round left and right
	private int round_0_left = 0 , round_0_right = 0;

	//Final round left and right
	private int finaldecision_l = 0, finaldecision_r = 0;

	@Override
	public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision)
	{
		// If the leader is not bad, the same message is delivered to all of the machines, 
		// but if the leader is faulty, random messages are sent to at least 2t+1 machines. 
		if(decision == 404 && roundNum==0)
		{
			int count=0, choice = 434;
			Random random = new Random();

			if(state == true) choice = random.nextInt(2);

			for(Machine m: mach_list)
			{
				int t = random.nextInt(2);
				
				if((count < fault_count) && (state == false) && t ==1)
				{
					// choice = random.nextInt(3);
					// if(choice == 2) count++; 
					count++;
					continue;

				}
				// else if(state == false && count >= fault_count) choice = random.nextInt(2);

				m.sendMessage(id,phase,0,choice);
			}
		}

		// Msg recieveerd after Round 0
		else if(roundNum == 0 && decision != 404 )  
		{
			if(decision==0)
			{
				round_0_right++;
				r++;
			}
			else if(decision == 1)
			{
				round_0_left++;
				l++;
			} 
		}

		//  If a machine is not broken, it simply sends the message it receives to all other machines in 
		// this round, however if it is faulty, it can either send the same random message to all 
		// machines or remain silent for this round. 
		else if(decision == 404 && roundNum == 1)
		{
			int choice=434;
			Random random = new Random();

			if(state == false)
			{
				int k = random.nextInt(2);
				if(k==0) choice = random.nextInt(2);  // send same random message to all the other Machines
				else if(k==1) return;
			}

			if(state == true) 
			{
				if(round_0_left == 1) choice=1;
				else if(round_0_right == 1) choice=0;
				else return ;
			}

			for(Machine machine:mach_list) machine.sendMessage(id,phase,1,choice);

		}

		// All the respective machines getting msg after round 1
		else if(decision != 404 && roundNum == 1)
		{
			if(decision==0) r++;
			else if(decision==1) l++; 
		}

		// Whether of whether or not the machine is defective, each 
		// machine sends its majority decision to all other machines. 
		else if(decision ==404 && roundNum==2)
		{
			int choice = 434;
			Random random = new Random();

			if(state == true)
			{
				if(l>r) choice=1;
				else if(r>l) choice=0;
			}
			else choice = random.nextInt(2);

			for(Machine machine:mach_list) machine.sendMessage(id,phase,2,choice);

		}

		// Msg recieved after round 2
		else if(decision !=404 && roundNum == 2)
		{
			if(decision ==1) finaldecision_l++;
			else if(decision == 0) finaldecision_r++;
		}
	}


	private int step;

	@Override
	public void setStepSize(int stepSize)
	{
		step = stepSize;
	}


	private Location position = new Location(0,0);
	private Location direction = new Location(1,0); // using Location as a 2d vector. Bad!
	
	@Override
	public
	void move()
	{
		// If 2t+1 identical values aren't received by every machine,result  is declared 
		// based on the majority value, and an error message is issued. 
		int k= -100;

		if(finaldecision_l > finaldecision_r)
		{
			k=finaldecision_l;
			if(phase !=0 && (k < (2*fault_count+1)))
			{
				System.out.println("Error " + phase );
			}

			if(direction.getY() ==0) direction.setLoc(0,direction.getX());
			else if(direction.getX() ==0) direction.setLoc(direction.getY()*-1, 0);
		}

		else if(finaldecision_r > finaldecision_l)
		{
			k=finaldecision_r;

			if(k < ((2*fault_count)+1) && phase !=0)
			{
				System.out.println("Error " + phase );
			}

			if(direction.getY() ==0) direction.setLoc(0,direction.getX()*-1);
			else if(direction.getX()==0) direction.setLoc(direction.getY(), 0);
		}
		
		position.setLoc(position.getX() + direction.getX()*step, position.getY() + direction.getY()*step);

		l=0;r=0;

		round_0_right=0; round_0_left=0;
		
		finaldecision_r=0; finaldecision_l=0;
	}


	@Override
	public Location getPosition() {
		
		return new Location(position.getX(), position.getY());
	}


	@Override
	public String name()
	{
		return "0133_"+id;
	}	
}
