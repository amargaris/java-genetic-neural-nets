package toolkit.optimization.genetic.impl;

import java.util.Arrays;

import toolkit.optimization.genetic.cross.ConstantGACrossOverEngine;
import toolkit.optimization.genetic.data.GAChromosome;
import toolkit.optimization.genetic.fit.GAFitnessFunction;
import toolkit.optimization.genetic.gen.GAChromosomeGenerator;
import toolkit.optimization.genetic.mut.ConstantGAMutator;
import toolkit.optimization.genetic.phenotype.GAPhenotype;
import toolkit.optimization.neural.NeuralNetwork;
import toolkit.optimization.training.LinearTrainingSet;


public class NeuralNetworkTrainingGA extends SimpleGA<double[]>{
	
	private NeuralNetwork network;
	
	private double mutationProbability,crossOverProbability;
	private double mutationScale;
	private Double minimumError;
	
	public NeuralNetworkTrainingGA(NeuralNetwork network,int maxEpochs, int populationSize,int elitism,double mutationProbability,double mutationScale,double crossOverProbability) {
		super(maxEpochs,populationSize, elitism);
		this.network = network;
		this.mutationProbability=mutationProbability;
		this.crossOverProbability=crossOverProbability;
		this.mutationScale=mutationScale;
	}
	public NeuralNetworkTrainingGA(NeuralNetwork network,double minimumError, int populationSize,int elitism,double mutationProbability,double mutationScale,double crossOverProbability) {
		this(network, 0, populationSize, elitism, mutationProbability, mutationScale,crossOverProbability);
		this.minimumError=minimumError;
	}
	@Override
	public SimpleGA<double[]> init() {
		setGenerator(new GAChromosomeGenerator<double[]>(getPopulationSize()) {
			
			@Override
			public double[] generate() {
				return network.getRandomWeights();
			}
		});
		if(minimumError!=null){
			setFitnessLowerThreshold(minimumError);
		}
		setMinimizeFitness(new GAFitnessFunction<double[]>() {
			
			@Override
			public Double evaluateFitness(GAChromosome<double[]> input) {
				network.setWeights(input.getContent());
				return network.train();
			}
		});
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
		setMutator(new ConstantGAMutator<double[]>(mutationProbability) {

			@Override
			public GAChromosome<double[]> doMutate(GAChromosome<double[]> input) {
				int a=getParent().getRandom().nextInt(input.getContent().length);
				input.getContent()[a]=input.getContent()[a]+getParent().getRandom().nextGaussian()*mutationScale;
				return input;
			}
		});
		setPhenotype(new GAPhenotype<double[]>() {
			
			@Override
			public void resolvePhenotype(double[] gene) {
				System.out.println("Chromosome: "+Arrays.toString(gene));
				network.setWeights(gene);
				network.showResult(10);
			}
		});
		return this;
	}
	public static void main(String... args){
		final int POPULATION_SIZE=1000;
		final int ELITISM=50;
		final int EPOCHS=1000;
		final double MUTATION=0.05;
		final double CROSS=0.5;
		NeuralNetwork.SCALE=1;
		final double MUTATION_SCALE=1;
//		final double MIN_SCORE=3.5;
		try{
			new NeuralNetworkTrainingGA
				(new NeuralNetwork(
						new LinearTrainingSet(0,1,1),new int[]{6,3,2},true),
//						MIN_SCORE
						EPOCHS
						,POPULATION_SIZE,ELITISM,MUTATION,MUTATION_SCALE,CROSS)
			.init().optimize();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	}
