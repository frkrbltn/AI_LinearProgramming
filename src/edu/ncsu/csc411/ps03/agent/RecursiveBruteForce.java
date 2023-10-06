package edu.ncsu.csc411.ps03.agent;

import java.util.ArrayList;
import java.util.Collections;

import edu.ncsu.csc411.ps03.environment.Environment;

/**
	Represents an intelligent agent 
*/

public class RecursiveBruteForce extends ConfigurationSolver{
	private ArrayList<int[]> permutations;
	private int[] configuration;
	private int permCounter;
	private int numPermations;
	/** Initializes a Configuration Solver for a specific environment. */
	public RecursiveBruteForce (Environment env) { 
		super(env);
		this.permutations = new ArrayList<int[]>();
		this.permCounter = 0;
		this.configuration = new int[env.getNumWorkers()];
		// Initializing by assigning work to an arbitrary task
		for(int i = 0; i < this.configuration.length; i++) {
			this.configuration[i] = i;
		}
		this.numPermations = 1;
		for(int i = 1; i < env.getNumWorkers()+1; i++) {
			this.numPermations *= i;
		}
		findPermutations(this.configuration);
	}
	
	// Utility function to swap two characters in a character array
    private void swap(int[] values, int i, int j)
    {
        int temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }
 
    // Recursive function to generate all permutations of a string
    private void permutations(int[] permutation, int currentIndex)
    {
        if (currentIndex == permutation.length - 1) {
            this.permutations.add(permutation.clone());
            return;
        }
 
        for (int i = currentIndex; i < permutation.length; i++)
        {
            swap(permutation, currentIndex, i);
            permutations(permutation, currentIndex + 1);
            swap(permutation, currentIndex, i);
        }
    }
    
    public void findPermutations(int[] permutation) {
        // base case
        if (permutation == null || permutation.length == 0) {
        	this.permutations.add(permutation.clone());
        	return;
        }
        permutations(permutation, 0);
    }
	
	@Override
	public int[] updateSearch() {
		int idx = 0;
		if (permCounter < this.numPermations) {
			idx = permCounter;
		} else {
			idx = this.numPermations-1;
		}
		int[] configuration = permutations.get(idx);
		permCounter++;
		return configuration;
	}
}