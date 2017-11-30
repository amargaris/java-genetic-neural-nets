package toolkit.optimization.genetic.valid;

import toolkit.optimization.genetic.data.GAChromosome;

public class AcceptAllValidator<T> extends GAChromosomeValidator<T>{

	@Override
	public Boolean isValid(GAChromosome<T> data) {
		return new Boolean(true);
	}
	
}