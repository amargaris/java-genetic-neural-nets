package toolkit.optimization.genetic.cloner;

import java.util.ArrayList;
import java.util.List;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.data.GAChromosome;

public abstract class GADataModelCloner<T> extends GAModule<T>{

	public GAChromosome<T> doClone(GAChromosome<T>input){
		GAChromosome<T>newa = new GAChromosome<T>(input.getContent());
		newa.setCurrentFitness(input.getCurrentFitness());
		newa.setPreviousFitness(input.getPreviousFitness());
		newa.setContent(clone(input.getContent()));
		return newa;
	}
	public abstract T clone(T input);
	public GAChromosomeCollection<T> cloneCollection(GAChromosomeCollection<T> input){
		List<GAChromosome<T>>list=new ArrayList<>();
		for(GAChromosome<T> data:input){
			list.add(doClone(data));
		}
		return new GAChromosomeCollection<>(list);
	}
}