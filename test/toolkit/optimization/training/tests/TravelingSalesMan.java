package toolkit.optimization.training.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import toolkit.optimization.ProblemModel;

public class TravelingSalesMan implements ProblemModel<TravelingSalesManSolution>{
	
	public static void main(String...strings ){
		TravelingSalesMan ts = new TravelingSalesMan(10);
		System.out.println(ts.solve());
		System.out.println(ts.getWorstSolution());
		
	}
	private int cities;
	private transient Random rand;
	private double[][] transitionMatrix;
	
	
	public TravelingSalesMan(int cities){
		this.cities = cities;
		transitionMatrix = new double[cities][cities];
		rand = new Random();
		IntStream.range(0, cities-1).forEach(i->
				IntStream.range(0, cities-1).forEach(j->
						transitionMatrix[i][j] = rand.nextDouble()));
		
	}
	public int getCities(){
		return cities;
	}
	public double getPathCost(TravelingSalesManSolution tsms){
		double sum = 0.0;
		for(int i=0;i<tsms.hops.length;i++){
			sum+=transitionMatrix[i][tsms.hops[i]];
		}
		return sum;
	}
	public static List<List<Integer>> permute(int[] num) {
		List<List<Integer>> result = new ArrayList<>();
	 
		//start from an empty list
		result.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.length; i++) {
			//list of list in current iteration of the array num
			List<List<Integer>> current = new ArrayList<>();
	 
			for (List<Integer> l : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					// + add num[i] to different locations
					l.add(j, num[i]);
	 
					List<Integer> temp = new ArrayList<>(l);
					current.add(temp);
	 
					//System.out.println(temp);
	 
					// - remove num[i] add
					l.remove(j);
				}
			}
	 
			result = new ArrayList<>(current);
		}
	 
		return result;
	}
	//public static boolean isValid
	public static TravelingSalesManSolution[] generateAllPossible(int dimension){
		int[] vals = new int[dimension];
		for(int i=0;i<vals.length;i++)
			vals[i]=i;
		
		return permute(vals)
			.parallelStream()
			.map(i->new TravelingSalesManSolution(i.stream().mapToInt(s->s).toArray()))
			.collect(Collectors.toList())
			.toArray(new TravelingSalesManSolution[0]);
	}
	private static Gson g  = new GsonBuilder().setPrettyPrinting().create();	
	public String toString(){
		return g.toJson(this);
	}
	private TravelingSalesManSolution[] solutions;
/*	public long checkSolutions(){
		  
		try{
			  Color[] cols = HeatMapPanel.createMultiGradient( new Color[]{Color.blue,Color.red}, 500);
			  GeneralUtil.displayIntoFrame(new HeatMapPanel(this.transitionMatrix, true,cols), "HeatMap");
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		
		long start = System.nanoTime();
		generateDataset();
		calculateCostFunction();
		TravelingSalesManSolution tsms = findBest();
		long end = System.nanoTime();
		System.out.println(tsms);
		
		
		return end-start;
		//Arrays.asList(solutions).forEach(System.out::println);;
	}*/
	public void calculateCost(TravelingSalesManSolution tsms){
		double cost = 0.0;
		for(int i=0;i<tsms.hops.length;i++){
			cost+= this.transitionMatrix[i][tsms.hops[i]];
		}
		tsms.setCost(cost);
	}
	public void calculateCostFunction(){
		Arrays.asList(solutions).parallelStream().forEach(this::calculateCost);
	}
	public TravelingSalesManSolution findBest(int a){
		double min = a > 0 ?Double.POSITIVE_INFINITY:Double.NEGATIVE_INFINITY;
		int index = -1;
		for(int i=0;i<solutions.length;i++){//TravelingSalesManSolution tsms :solutions){
			if(solutions[i].getCost()*a < min*a ){
				min = solutions[i].getCost();
				index = i;
			}
		}
		//Arrays.sort(solutions,(a,b)->-(new Double(a.getCost()).compareTo(b.getCost())));
		return solutions[index]; //solutions[0];
	}

	@Override
	public void generateDataset() {
		solutions = generateAllPossible(this.cities);
	}
	@Override
	public TravelingSalesManSolution getBestSolution() {
		return findBest(1);
	}
	@Override
	public TravelingSalesManSolution getWorstSolution() {
		return findBest(-1);
	}
}
