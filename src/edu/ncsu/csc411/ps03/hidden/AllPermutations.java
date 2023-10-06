package edu.ncsu.csc411.ps03.hidden;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import edu.ncsu.csc411.ps03.environment.Environment;
import edu.ncsu.csc411.ps03.environment.Environment.Node;

/**
 * Supplemental file that runs the "simulation" exactly N! times
 * to allow you to record brute force appoarches.
 * @author Adam Gaweda
 */
public class AllPermutations {
	private Environment env;
	private long ITERATIONS;
	private int N;
	
	// Build the simulation with the following parameters
	public AllPermutations(int[][] map) {
		this.env = new Environment(map);
		this.N = map.length;
		ITERATIONS = factorial(N);
	}
	
	public long factorial(int N) {
		long total = 1;
		for(int i = 1; i <= N; i++) {
			total *= i;
		}
		return total;
	}
	
	// Iterate through the simulation, updating the environment at each time step
	public void run() {
		double start = System.currentTimeMillis();
		for (long i = 1; i <= ITERATIONS; i++) {
			env.updateConfiguration();
		}
		double stop = System.currentTimeMillis();
		
		String msg = "Configuration after "+ITERATIONS+" iterations:";
		int[] configuration = this.env.getCurrentConfiguration();
		System.out.println(msg);
		System.out.println(Arrays.toString(configuration));
		System.out.println("Score: " + this.env.calcScore(configuration));
		System.out.println("Time (ms): " + (stop-start));
		
		PriorityQueue<Node> previousConfigurations = env.getPreviousConfigurations();
		writeConfigurationsToFile("outputs/configurations_n"+N+".csv", previousConfigurations);
	}
	
	public void writeConfigurationsToFile(String filename, PriorityQueue<Node> previousConfigurations) {
		try {
			PrintWriter output = new PrintWriter(filename);
			while(!previousConfigurations.isEmpty()) {
				Node prev = previousConfigurations.remove();
				int[] prevConf = prev.getConfig();
				int prevValue = prev.getValue();
				String line = "";
				for(int j = 0; j < prevConf.length; j++) {
					line += prevConf[j] + ",";
				}
				line += prevValue;
				output.println(line);
			}
			output.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public static void main(String[] args) {
		int[][] map = {
//				{60, 88, 67, 88, 70, 97, 76, 27, 28},
//				{21, 60, 45, 13, 82, 88, 69, 60, 15},
//				{29, 50, 87, 31, 66, 87, 54, 90, 54},
//				{40, 99, 52, 75, 33, 69, 21, 83, 51},
//				{98, 96, 31, 95, 55, 89, 95, 57, 15},
//				{19, 91, 36, 22, 38, 51, 79, 62, 64},
//				{91, 81, 98, 23, 31, 50, 23, 96, 66},
//				{71, 38, 11, 81, 30, 83, 55, 35, 24},
//				{81, 79, 29, 25, 37, 23, 95, 42, 55},
//				{19,26,17,21,13},
//				{27,29,15,21,20},
//				{17,12,15,12,27},
//				{19,19,26,19,16},
//				{18,21,18,22,18},
				{48, 27, 44, 31, 39, 14, 44, 17, 14, 45},
				{19, 42, 31, 40, 48, 26, 29, 29, 19, 45},
				{11, 44, 28, 19, 35, 36, 48, 20, 38, 32},
				{21, 43, 40, 37, 34, 44, 18, 11, 13, 39},
				{31, 12, 33, 38, 35, 23, 17, 10, 28, 15},
				{21, 46, 16, 34, 11, 25, 18, 38, 15, 48},
				{44, 21, 32, 48, 27, 39, 50, 10, 29, 24},
				{47, 49, 49, 11, 43, 18, 29, 29, 25, 28},
				{45, 28, 13, 37, 50, 26, 39, 31, 16, 27},
				{28, 25, 17, 37, 35, 20, 11, 28, 10, 34},
			};
		AllPermutations sim = new AllPermutations(map);
		sim.run();
    }
	
	public int getScore() {
		return env.calcScore(env.getCurrentConfiguration());
	}
}