package toolkit.optimization.genetic.gen;

import java.util.ArrayList;
import java.util.List;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.data.GAChromosome;

public abstract class GAChromosomeGenerator<T> extends GAModule<T>{
	private int populationSize;
	private int chromosomeSize;
	public abstract T generate();
	public GAChromosomeGenerator(int populationSize,int chromosomeSize){
		this.populationSize=populationSize;
		this.chromosomeSize=chromosomeSize;
	}
	public GAChromosomeGenerator(int populationSize){
		this.populationSize=populationSize;
		this.chromosomeSize=1;
	}
	public void setPopulationSize(int populationSize){
		this.populationSize=populationSize;
	}
	
	public int getPopulationSize(){
		return populationSize;
	}
	public GAChromosome<T> createSample(){
		return new GAChromosome<T>(generate(),chromosomeSize);
	}
	public GAChromosomeCollection<T> createCollection(){
		List<GAChromosome<T>> list = new ArrayList<>(getPopulationSize());
		for(int i=0;i<getPopulationSize();i++){
			list.add(createSample());
		}
		return new GAChromosomeCollection<T>(list);
	}
}