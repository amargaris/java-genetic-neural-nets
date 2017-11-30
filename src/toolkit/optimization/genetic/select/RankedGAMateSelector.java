package toolkit.optimization.genetic.select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.data.GAChromosome;

public class RankedGAMateSelector<T> extends GAMateSelector<T>{

	public RankedGAMateSelector(int elitism) {
		super(elitism);
	}

	@Override
	public List<MatingPair<T>> mate(GAChromosomeCollection<T> dataset) {
		GAChromosomeCollection<T> clonedDataset=dataset;//getParent().getCloner().cloneCollection(dataset);
		List<GAChromosome<T>> list = clonedDataset.getDataset();
		TreeSet<GAChromosome<T>> tree = new TreeSet<>(new Comparator<GAChromosome<T>>() {

			@Override
			public int compare(GAChromosome<T> o1, GAChromosome<T> o2) {
				return o1.getCurrentFitness() //compare 1
						.compareTo	//to
							(o2.getCurrentFitness())//other 
								*getParent().getFitness().getCondition(); //max or min is better?
			}
		});
		for (GAChromosome<T> data : list) {
			tree.add(data);
		}
		List<MatingPair<T>> pairList = new ArrayList<>();
		
		//Bazw $elitism zeygaria omoiwn chromosomatwn gia tin elite
		for(int i=0;i<getElitism();i++){
			pairList.add(new MatingPair<>(tree.first(),tree.first()));
		}
		//Ola ta alla einai ranked kata tin katataksi toys
		Iterator<GAChromosome<T>> it=tree.iterator();
		while (it.hasNext()&&pairList.size()!=clonedDataset.getDataset().size()/2) {
			pairList.add(new MatingPair<>(it.next(),it.next()));
		}
		return pairList;
	}
	
}