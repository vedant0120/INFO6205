package edu.neu.coe.info6205.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.math.*;



import java.util.Random;


import edu.neu.coe.info6205.sort.simple.InsertionSort;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
        logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");

        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);

        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, Consumer<T> f) {
        this(description, null, f, null);
    }

    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;

    final static LazyLogger logger = new LazyLogger(Benchmark_Timer.class);
    
    

    public static void main(String[] args) {
        
    	//Instantiate the class which provides the method to perform insertion sort
    	InsertionSort sorter = new InsertionSort();
    	
    	//Generate series for plotting results for each type of array ordering

    	//Instantiate Benchmark_Timer that invokes Timer class to perform Benchmark Test
    	Benchmark_Timer<Integer[]> benchmarkTimer = new Benchmark_Timer<>("Benchmark Test for Insertion Array", null, (a) -> sorter.sort(a, 0, a.length), null);
    	
    	//Generate an ordered array for size 1000 and double it till 17000, and run benchmark test
    	for(int i = 1000; i < 17000; i = i*2) {
    		
    		int fI = i; 
    		
    		//Implement the supplier to provide an ordered array of random elements of size i
    		Supplier<Integer[]> supplier = new Supplier<Integer[]>() {

				@Override
				public Integer[] get() {
					Random random = new Random();
			    	Integer[] val = new Integer[fI];
			    	
			    	//Generate the random array
			    	for(int i = 0; i < fI; i++) {
			    		val[i] = random.nextInt(fI*100);
			    	}
			    	
			    	//Sort the random array 
			    	Arrays.sort(val);
			    	return val;
				}
    			
    		};
    		
    		//Gather the result for benchmark test for ordered array of size i for 10 runs
        	double d = benchmarkTimer.runFromSupplier(supplier, 10);
        	
        	//Get the log for the log log graph

        	
        	System.out.println("For n : " + i + " ordered array , time taken: " + d);
    	}
    	
    	System.out.println("............................................................................................");
    	
    	//Generate a randomly ordered array for size 1000 and double it till 17000, and run benchmark test
    	for(int i = 1000; i < 17000; i = i*2) {
    		int fI = i; 
    		
    		//Implement the supplier to provide a randomly ordered array of size i
    		Supplier<Integer[]> supplier = new Supplier<Integer[]>() {

				@Override
				public Integer[] get() {
					Random random = new Random();
			    	Integer[] val = new Integer[fI];
			    	
			    	//Generate the random array
			    	for(int i = 0; i < fI; i++) {
			    		val[i] = random.nextInt(fI);
			    	}
			    	return val;
				}
    			
    		};
    		
    		//Gather the result for benchmark test for randomly array of size i for 10 runs
        	double d = benchmarkTimer.runFromSupplier(supplier, 10);
        	
        	//Get the log of T and n for the log log graph

        	
        	System.out.println("For n : " + i + " randomly ordered array, time taken : " + d);
    	}
    	
    	System.out.println("............................................................................................");
    	
    	//Generate a reverse ordered array for size 1000 and double it till 17000, and run benchmark test
    	for(int i = 1000; i < 17000; i = i*2) {
    		int fI = i; 
    		
    		//Implement the supplier to provide a reverse ordered array of size n
    		Supplier<Integer[]> supplier = new Supplier<Integer[]>() {

				@Override
				public Integer[] get() {
					Random random = new Random();
			    	Integer[] val = new Integer[fI];
			    	
			    	//Generate the random array
			    	for(int i = 0; i < fI; i++) {
			    		val[i] = random.nextInt(fI);
			    	}
			    	
			    	//Sort it reverse 
			    	Arrays.sort(val, Collections.reverseOrder());
			    	return val;
				}
    			
    		};
    		
    		//Gather the result for benchmark test for reverse ordered array of size i for 10 runs
        	double d = benchmarkTimer.runFromSupplier(supplier, 10);
        	

        	System.out.println("For n : " + i + " reverse ordered array, time taken : " + d);
    	}
    	
    	System.out.println("............................................................................................");
    	
    	//Generate a partially ordered array for size 1000 and double it till 17000, and run benchmark test
    	for(int i = 1000; i < 17000; i = i*2) {
    		int fI = i; 
    		
    		//Implement the supplier to provide a partially ordered array of size n
    		Supplier<Integer[]> supplier = new Supplier<Integer[]>() {

				@Override
				public Integer[] get() {
					Random random = new Random();
			    	Integer[] val = new Integer[fI];
			    	
			    	//Generate the random array
			    	for(int i = 0; i < fI; i++) {
			    		val[i] = random.nextInt(fI*100);
			    	}
			    	
			    	//Sort the array
			    	Arrays.sort(val);
			    	
			    	//Disorder 20% of the array 
			    	int disorderNum = (int) (0.2*fI);
			    	
			    	//Generate random indices to create disorder and replace it
			    	for(int i = 0; i < disorderNum; i++) {
			    		int index = random.nextInt(fI);
			    		val[index] = random.nextInt(fI*100);
			    	}
			 
			    	return val;
				}
    			
    		};
    		
    		//Gather the result for benchmark test for partially ordered array of size i for 10 runs
        	double d = benchmarkTimer.runFromSupplier(supplier, 10);
        	

        	System.out.println("For n: " + i + " partially ordered array, time taken: " + d);
    	}
    	

    }
}