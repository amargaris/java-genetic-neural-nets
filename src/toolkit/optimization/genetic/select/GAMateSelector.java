package toolkit.optimization.genetic.select;

import java.util.List;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosomeCollection;

public abstract class GAMateSelector<T> extends GAModule<T>{
	private int elitism;
	public GAMateSelector(int elitism){
		this.elitism=elitism;
	}
	public int getElitism(){
		return elitism;
	}
	public abstract List<MatingPair<T>> mate(GAChromosomeCollection<T> dataset);
	
}