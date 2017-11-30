package toolkit.optimization.genetic.impl;

import java.util.Arrays;

import toolkit.optimization.genetic.VectorDistanceFitness;
import toolkit.optimization.genetic.cross.ConstantGACrossOverEngine;
import toolkit.optimization.genetic.data.GAChromosome;
import toolkit.optimization.genetic.gen.GAChromosomeGenerator;
import toolkit.optimization.genetic.mut.ConstantGAMutator;
import toolkit.optimization.genetic.mut.FitnessBasedGAMutator;
import toolkit.optimization.genetic.mut.GAMutator;
import toolkit.optimization.genetic.phenotype.GAPhenotype;

public class VectorComparisonGA extends SimpleGA<double[]>{
	
	private static final double CLIMAX=10000;
	private static final int DIMENSIONS=4;
	private final double mutationProbability=0.2;
	private final double crossOverProbability=0.5;
	
	private static final int POPULATION_SIZE=1000;
	private static final int ELITISM=5;
	private static final int MAX_EPOCHS=1000;

	public GAMutator<double[]> conmut= new ConstantGAMutator<double[]>(mutationProbability) {

		@Override
		public GAChromosome<double[]> doMutate(GAChromosome<double[]> input) {
			int a=getParent().getRandom().nextInt(input.getContent().length);
			input.getContent()[a]=input.getContent()[a]+getParent().getRandom().nextGaussian();
			return input;
		}
	};
	public GAMutator<double[]> fitmut=new FitnessBasedGAMutator<double[]>(){
		@Override
		public GAChromosome<double[]> doMutate(GAChromosome<double[]> input) {
			int a=getParent().getRandom().nextInt(input.getContent().length);
			input.getContent()[a]=input.getContent()[a]+getParent().getRandom().nextGaussian();
			return input;
		}
	};
	public VectorComparisonGA(){
		super(MAX_EPOCHS,POPULATION_SIZE,ELITISM);
	}
	public VectorComparisonGA(double[] theVector){
		this();
		setFitness(new VectorDistanceFitness(theVector));
	}
	
	@Override
	public SimpleGA<double[]>init() {
		setGenerator(new GAChromosomeGenerator<double[]>(POPULATION_SIZE,DIMENSIONS) {
			
			@Override
			public double[] generate() {
				double[] arr = new double[DIMENSIONS];
				for(int i=0;i<DIMENSIONS;i++){
					arr[i]=getParent().getRandom().nextGaussian()*CLIMAX;
				}
				return arr;
			}
		});
		setFitness(new VectorDistanceFitness(getGenerator().generate()));
		setCrossover(new ConstantGACrossOverEngine<double[]>(crossOverProbability) {

			@Override
			public void swapArguments(GAChromosome<double[]> maleGene, GAChromosome<double[]> femaleGene,int index) {
				double[] sperm=maleGene.getContent();
				double[] egg=femaleGene.getContent();
				double temp=sperm[index];
				sperm[index]=egg[index];
				egg[index]=temp;
			}
		});
		setMutator(conmut);
		setPhenotype(new GAPhenotype<double[]>() {
			
			@Override
			public void resolvePhenotype(double[] gene) {
				System.out.println("Chromosome: "+Arrays.toString(gene));
			}
		});
		return this;
	}
	public static void main(String... args){
		try{
			new VectorComparisonGA().init().optimize();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}