package edu.ncsu.csc411.ps03.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.ncsu.csc411.ps03.environment.Environment;

/**
	Represents a linear assignment problem where N workers must be assigned
	to N tasks. Each worker/task combination is further associated with some
	value. The goal of this task is the produce an optimal configuration that
	maximizes (or minimizes) the sum of the assigned worker/task values.
*/

public class ConfigurationSolver {
	private Environment env;
	private int[] configuration;
	private int[] bestConfiguration;
	private Random random;
	
	/** Initializes a Configuration Solver for a specific environment. */
	public ConfigurationSolver (Environment env) { 
		this.env = env;
		this.configuration = new int[this.env.getNumWorkers()];
		// Initializing by assigning work to an arbitrary task
		for(int i = 0; i < this.configuration.length; i++) {
			this.configuration[i] = i;
		}
		// Need to clone because of how Java handles assigning arrays
		// as values (relative referencing). Recall this is similar to
		// that one lecture in CSC 116 where if we don't duplicate the second
		// array, it will constantly change as this.configuration changes.
		// Cloning the array resolves this issue.
		this.bestConfiguration = this.configuration.clone();
		this.random = new Random();
		
	}
	
	/**
    	Problem Set 03 - For this Problem Set, you will be exploring search
    	methods for optimal configurations. In this exercise, you are given
    	a linear assignment problem, where you must determine the appropriate
    	configuration assignments for persons to tasks. Specifically, you are
    	seeking to MAXIMIZE the fitness score of this configuration. While brute
    	forcing your search will provide you with the optimal solution, you run
    	into the issue that there are N! possible permutations, which can increase
    	your search space as N increases. Instead, utilize one of the search methods
    	presented in class to tackle this problem.
    	
    	For the updateSearch(), design an algorithm that will iterate through an
    	iterative optimization algorithm, updating the current configuration as it
    	traverses its search space. The updateSearch() method should also return this
    	configuration back to the environment. For example, in an N=5 problem space, 
    	updateSearch() may return {4,1,2,3,0}, where Worker #4 is assigned Task #3.
    	
    	NOTE - simply doing random search, while it "will" work, is not permitted. If we
    	see you implementation is just random search, you will receive a -40% penalty to
    	your submission. Instead, think about the meta-heuristics presented in class, which
    	use "controlled" randomization.
	 */

	/**
		Replace this docstring comment with an explanation of your implementation.
		We will implement a simulated annealing algorithm. We will start with a random configuration
		but we wont randomly change the configuration. Instead we will change the configuration
		by swapping two values. We will then calculate the score of the new configuration and compare
		it to the score of the old configuration. If the new configuration has a higher score, we will
		keep it. If the new configuration has a lower score, we will keep it with a probability of
		e^((newScore - oldScore)/temperature). We will then decrease the temperature by a factor of
		0.99. We will repeat this process until the temperature is less than 0.0001. We will then
		return the best configuration we found.
	 */
	public int[] updateSearch () {
		// // Random Search (aka -40% penalty if you use this)

		 // Hold the integers from the current configuration array.
//		 ArrayList<Integer> intList = new ArrayList<Integer>();
//		 for(int val : this.configuration)
//		 	intList.add(val);	// each value represents a task assigned the worker.
//		 Collections.shuffle(intList); // then suffle the values.
//		 for(int i = 0; i < this.configuration.length; i++)
//		 	this.configuration[i] = intList.get(i);
//		 System.out.println(Arrays.toString(this.configuration) + " = " + env.calcScore(this.configuration));
//		 if (env.calcScore(this.configuration) > env.calcScore(bestConfiguration))
//		 	this.bestConfiguration = this.configuration.clone();

		// Write the simulated anneling algorithm in pseduo code
//		 func simulated_annealing (state):
//		 	for t = 1 to infinity do
//		 		T = schedule(t)
//		 		if T = 0 then return state
//		 		candidate = a randomly selected successor of current
//		 		ΔE = candidate.score - state.score
//		 		if ΔE > 0 then state = candidate
//		 		else 
//		 	      probability = e^(ΔE/T)
//		 		  if random < probability
//		 		  	state = candidate
		
//		 ArrayList<Integer> intList = new ArrayList<Integer>();
//		 for(int val : this.configuration)
//		 	intList.add(val);	// each value represents a task assigned the worker.
		 
		int t = 1;
		double T = schedule(t);
		while (T > 0.001) {  // while true takes time to test it. 
		    int[] candidate = randomNeighboor(configuration);
		    double E = env.calcScore(candidate) - env.calcScore(configuration);
		    
		    if (E > 0) {
		        configuration = candidate.clone();
		        if (env.calcScore(configuration) > env.calcScore(bestConfiguration)) {
		            return bestConfiguration = configuration.clone();
		        }
		    } else {
		        double prob= probability(E, T);
		        if (random.nextDouble() < prob) {
		            configuration = candidate.clone();
		        }
		    }
		    
		    t++;
		    T = schedule(t);
		}
		return getBestConfiguration();

	}
	

	private double probability(double E, double T) {
		
		return Math.exp(E/T);
	}

	private int[] randomNeighboor(int[] state) {
		
	    int[] neighbor = state.clone();

	    int job1 = random.nextInt(neighbor.length);
	    int job2 = random.nextInt(neighbor.length);
	    while (job1 == job2) {
	    	job2 = random.nextInt(neighbor.length); 
	    }
	    
	    // Simple swap operation for two jobs.
	    int temp = neighbor[job1];
	    neighbor[job1] = neighbor[job2];
	    neighbor[job2] = temp;

	    return neighbor;  // Return the candidate state;
	}

	private double schedule(int t) {
		double initialTemp = 1000;
		double decreaseRate = 0.99;
		
		// follow first slide for schedule which is not linear decrease time. 
		return initialTemp * Math.pow(decreaseRate, t);
	}

	/**
	 * In addition to updateSearch, you should also track you BEST OBSERVED CONFIGURATION.
	 * While your search may move to worse configurations (since that's what the algorithm
	 * needs to do), getBestConfiguration will return the best observed configuration by your
	 * agent.
	 * You do not need to change this method. Update bestConfiguration in updateSearch.
	*/
	public int[] getBestConfiguration() {
		return this.bestConfiguration;
	}
	
}