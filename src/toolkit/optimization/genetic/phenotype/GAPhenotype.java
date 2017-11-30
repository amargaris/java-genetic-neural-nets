package toolkit.optimization.genetic.phenotype;

import toolkit.optimization.genetic.GAModule;


public abstract class GAPhenotype<T> extends GAModule<T>{
	
	public abstract void resolvePhenotype(T gene);
}
