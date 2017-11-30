package toolkit.optimization.genetic.select;

import java.util.LinkedList;
import java.util.List;

import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.data.GAChromosome;

public class RouletteGAMateSelector<T>extends GAMateSelector<T>{

	public RouletteGAMateSelector(int elitism) {
		super(elitism);
	}

	@Override
	public List<MatingPair<T>> mate(GAChromosomeCollection<T> collection) {
		double[] scores = new double[collection.getDataset().size()];
		double sum=0.0;
		for(int i=0;i<scores.length;i++){
			scores[i]=getParent().getFitness().getNormalized(collection.getDataset().get(i).getCurrentFitness());
			sum+=scores[i];
		}
		for(int i=0;i<scores.length;i++){
			scores[i]/=sum;
		}
		List<MatingPair<T>>list = new LinkedList<>();
		for(int i=0;i<getElitism();i++){
			list.add(new MatingPair<>(collection.getBestChromosome(), collection.getBestChromosome()));
		}
		while(list.size()!=getParent().getGenerator().getPopulationSize()/2){
			list.add(new MatingPair<>(getSample(scores), getSample(scores)));
		}
		return list;
	}
	
	public GAChromosome<T> getSample(double[] scores){
		double value = getParent().getRandom().nextDouble();
		double sum=0;
		for(int i=0;i<scores.length;i++){
			sum+=scores[i];
			if(value<=sum){
				return getParent().getCloner().doClone(getParent().getChromosomeCollection().getDataset().get(i));
			}
		}
		return null;
	}
	
}