package toolkit.optimization.genetic.cross;

import java.util.Random;

import toolkit.optimization.genetic.data.GAChromosome;

public abstract class ConstantGACrossOverEngine<T> extends GACrossOverEngine<T>{
	private double crossOverProbability;
	public ConstantGACrossOverEngine(double crossOverProbability){
		
		this.crossOverProbability=crossOverProbability;
	}
	public abstract void swapArguments(GAChromosome<T>first,GAChromosome<T>second,int index);
	@Override
	public void crossOver(GAChromosome<T> first, GAChromosome<T> second) {
		Random rand = getRandom();
		for(int i=0;i<first.getGeneLength();i++){//assume first.geneLength==second.geneLength
			double test=rand.nextDouble();
			if(test<=crossOverProbability){
				swapArguments(first, second, i);
			}
		}
	}
}