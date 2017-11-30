package toolkit.optimization.genetic.mut;



public abstract class ConstantGAMutator<T> extends GAMutator<T>{
	private double mutationProbability;
	public ConstantGAMutator(double mutationProbability){
		this.mutationProbability=mutationProbability;
	}
	
	public double getMutationProbability() {
		return mutationProbability;
	}
}