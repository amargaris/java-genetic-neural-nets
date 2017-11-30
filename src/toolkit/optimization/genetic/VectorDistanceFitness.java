package toolkit.optimization.genetic;

import java.util.Arrays;

import toolkit.optimization.genetic.data.GAChromosome;
import toolkit.optimization.genetic.fit.GAFitnessFunction;

public class VectorDistanceFitness extends GAFitnessFunction<double[]>{
	private double[] targetVector;
	public VectorDistanceFitness(double[] targetVector) {
		super();
		setCondition(MINIMIZE);
		this.targetVector=targetVector;
		System.out.println("Target Vector: "+Arrays.toString(targetVector));
	}
	@Override
	public Double evaluateFitness(GAChromosome<double[]> input) {
		double val=0;
		for(int i=0;i<targetVector.length;i++){
			val+=Math.pow(targetVector[i]-input.getContent()[i], 2);
		}
		return Math.sqrt(val);
	}
	
}