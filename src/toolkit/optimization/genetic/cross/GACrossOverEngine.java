package toolkit.optimization.genetic.cross;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosome;


public abstract class GACrossOverEngine<T> extends GAModule<T>{

	public abstract void crossOver(GAChromosome<T> first,GAChromosome<T> second);//Group sex not allowed!!
}