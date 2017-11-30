package toolkit.optimization.training.tests;

import java.util.Arrays;

public class TravelingSalesManSolution{
	
	int[] hops;
	private double cost = Double.NaN;
	
	public TravelingSalesManSolution(int[] hops){
		this.hops = hops;
	}
	public double getCost(){
		return cost;
	}
	public void setCost(double cost){
		this.cost = cost;
	}
	public int[] getHops() {
		return hops;
	}
	public void setHops(int[] hops) {
		this.hops = hops;
	}
	
	public String toString(){
		return "Path:"+Arrays.toString(hops)+(Double.isNaN(cost)?"":" Cost: "+cost);
	}
	
}