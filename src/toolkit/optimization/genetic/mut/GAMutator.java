package toolkit.optimization.genetic.mut;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosome;


public abstract class GAMutator<T> extends GAModule<T>{
	public abstract GAChromosome<T> doMutate(GAChromosome<T>input);//methodos
	public abstract double getMutationProbability();//pithanotita 
	public GAChromosome<T> mutate(GAChromosome<T>input){
		double test = getRandom().nextDouble();
		if(test<=getMutationProbability()){
			return doMutate(input);
		}
		return input;
	};
	
}