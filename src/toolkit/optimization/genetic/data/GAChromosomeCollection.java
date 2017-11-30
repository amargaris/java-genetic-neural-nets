package toolkit.optimization.genetic.data;

import java.util.Iterator;
import java.util.List;

public class GAChromosomeCollection<T> implements Iterable<GAChromosome<T>>{
	private List<GAChromosome<T>> dataset;
	private Double currentAverageFitness;
	private Double currentMinFitness;
	private Double currentMaxFitness;
	private GAChromosome<T> bestChromosome;
	
	public GAChromosomeCollection(List<GAChromosome<T>> list){
		this.dataset=list;
	}
	@Override
	public Iterator<GAChromosome<T>> iterator() {
		return dataset.iterator();
	}
	public List<GAChromosome<T>> getDataset() {
		return dataset;
	}
	public void update(double newFitness){
		if(currentMinFitness==null||newFitness<currentMinFitness){
			currentMinFitness= new Double(newFitness);
		}
		if(currentMaxFitness==null||newFitness>currentMaxFitness){
			currentMaxFitness= new Double(newFitness);
		}
	}
	public void setDataset(List<GAChromosome<T>> dataset) {
		this.dataset = dataset;
	}
	public Double getCurrentAverageFitness() {
		return currentAverageFitness;
	}
	public void setCurrentAverageFitness(Double currentAverageFitness) {
		this.currentAverageFitness = currentAverageFitness;
	}
	public Double getCurrentMinFitness() {
		return currentMinFitness;
	}
	public void setCurrentMinFitness(Double currentMinFitness) {
		this.currentMinFitness = currentMinFitness;
	}
	public Double getCurrentMaxFitness() {
		return currentMaxFitness;
	}
	public void setCurrentMaxScore(Double currentMaxScore) {
		this.currentMaxFitness = currentMaxScore;
	}
	public GAChromosome<T> getBestChromosome() {
		return bestChromosome;
	}
	public void setBestChromosome(GAChromosome<T> bestChromosome) {
		this.bestChromosome = bestChromosome;
	}
	
}