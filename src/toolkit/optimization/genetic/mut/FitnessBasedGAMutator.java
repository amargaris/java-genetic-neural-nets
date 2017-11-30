package toolkit.optimization.genetic.mut;

public abstract class FitnessBasedGAMutator<T> extends ConstantGAMutator<T>{
	private double expFactor;
	public FitnessBasedGAMutator(){
		this(-6.2146);//WTF
	}
	public FitnessBasedGAMutator(double expFactor){
		super(0);
		//https://www.tu-ilmenau.de/fileadmin/public/sse/Veroeffentlichungen/2013/pxc3889343_Salzwedel.pdf
		//Variable Mutation Rate at Genetic Algorithms:
		//Introduction of Chromosome Fitness in Connection with
		//Multi-Chromosome Representation

		this.expFactor=expFactor;
	}
	
	public double getMutationProbability(){
		double normFitness=getParent().getAvgNormFitness();
		return 0.5*Math.exp(expFactor*normFitness);
	}
}