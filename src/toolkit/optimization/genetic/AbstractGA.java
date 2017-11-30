package toolkit.optimization.genetic;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

import toolkit.optimization.genetic.cloner.GADataModelCloner;
import toolkit.optimization.genetic.cloner.GsonGAChromosomeCloner;
import toolkit.optimization.genetic.cross.GACrossOverEngine;
import toolkit.optimization.genetic.data.GAChromosome;
import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.fit.GAFitnessFunction;
import toolkit.optimization.genetic.gen.GAChromosomeGenerator;
import toolkit.optimization.genetic.mut.GAMutator;
import toolkit.optimization.genetic.phenotype.GAPhenotype;
import toolkit.optimization.genetic.select.GAMateSelector;
import toolkit.optimization.genetic.select.MatingPair;
import toolkit.optimization.genetic.select.RankedGAMateSelector;
import toolkit.optimization.genetic.select.RouletteGAMateSelector;
import toolkit.optimization.genetic.term.GATerminationCriteria;
import toolkit.optimization.genetic.valid.AcceptAllValidator;
import toolkit.optimization.genetic.valid.GAChromosomeValidator;
/**
 * Genetic Algorithm implementation for  back end analytics module.
 * The purpose of this class is to optimize a problem by the means of a genetic
 * algorithm solutionspace iteration. Genetic algorithms exist in the literature
 * with multiple forms therefore this implementation focuses on the framework and
 * not on the variations of the Genetic Algorithm in order to give the developer/
 * researcher the ability to customize it in any wanted way.
 * 
 * @author Aristotelis
 *
 * @param <T>	The Phenotype object of the Genetic Algorithm (e.g. int[] for the traveling salesman project)
 */
public class AbstractGA<T> {
	/*
	 * Components
	 */
	private GAChromosomeGenerator<T> generator;
	private GAChromosomeCollection<T> chromosomeCollection;
	private GAFitnessFunction<T> fitness;
	private GAMateSelector<T> selector;
	private GACrossOverEngine<T> crossover;
	private GAMutator<T> mutator;
	private GAChromosomeValidator<T> validator;
	private GATerminationCriteria<T> termination;
	private GADataModelCloner<T> cloner;
	private GAPhenotype<T> phenotype;
	/*
	 * General
	 */
	private Random random;
	/*
	 * State
	 */
	private long initTimeStamp;
	private int epoch;
	
	/*
	 * Listeners
	 */
	private Map<GAState,List<GAListener<T>>> eventListeners;
	
	/**
	 * Constructor of Genetic Algorithm
	 */
	public AbstractGA(){
		eventListeners= new LinkedHashMap<>();
		for(GAState state:GAState.values()){
			eventListeners.put(state, new LinkedList<GAListener<T>>());
		}
	}
	/**
	 * Listener invoked after each epoch / iteration is finished.
	 * 
	 * @param listener
	 */
	public void onIterationEnded(GAListener<T> listener){
		eventListeners.get(GAState.IterationEnded).add(listener);
	}
	/**
	 * Listener invoked after the initial creation of the dataset (before the loop).
	 * 
	 * @param listener
	 */
	public void onInitialization(GAListener<T> listener){
		eventListeners.get(GAState.Initialization).add(listener);
	}
	/**
	 * Listener invoked after the termination criteria of the algorithm is met.
	 * 
	 * @param listener
	 */
	public void onTermination(GAListener<T> listener){
		eventListeners.get(GAState.Termination).add(listener);
	}
	/**
	 * Listener invoked after the evaluation of each chromosome has taken place.
	 * 
	 * @param listener
	 */
	public void onEvaluation(GAListener<T>listener){
		eventListeners.get(GAState.Evaluation).add(listener);
	}
	/**
	 * Removes all event listeners for the specific GAState.
	 * @param state	The specific State that we wish to clear its event listeners.
	 */
	public void clearListeners(GAState state){
		eventListeners.get(state).clear();
	}
	/**
	 * Removes all event listeners from the genetic algorithm.
	 */
	public void clearAllListeners(){
		for(GAState state:GAState.values()){
			clearListeners(state);
		}
	}
	
	public GAPhenotype<T> getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(GAPhenotype<T> phenotype) {
		this.phenotype = phenotype;
		addModule(phenotype);
	}
	
	public void resolveCollectionPhenotype(){
		System.out.println("----------------Epoch "+getEpoch()+"---------------");
		for(GAChromosome<T> data:getChromosomeCollection())
			getPhenotype().resolvePhenotype(data.getContent());
	}

	public GAMutator<T> getMutator() {
		return mutator;
	}
	
	public void addModule(GAModule<T> module){
		module.setParent(this);
	}

	public void setMutator(GAMutator<T> mutator) {
		this.mutator = mutator;
		addModule(this.mutator);
	}

	public GAChromosomeCollection<T> getChromosomeCollection() {
		return chromosomeCollection;
	}

	public void setDataset(GAChromosomeCollection<T> dataset) {
		this.chromosomeCollection = dataset;
	}

	public GAChromosomeGenerator<T> getGenerator() {
		return generator;
	}

	public void setGenerator(GAChromosomeGenerator<T> generator) {
		this.generator = generator;
		addModule(this.generator);
	}

	public GAFitnessFunction<T> getFitness() {
		return fitness;
	}

	public void setFitness(GAFitnessFunction<T> fitness) {
		this.fitness = fitness;
		addModule(fitness);
	}

	public GAChromosomeValidator<T> getValidator() {
		return validator;
		
	}
	
	public void setAcceptAllValidator(){
		setValidator(new AcceptAllValidator<T>());
	}

	public void setValidator(GAChromosomeValidator<T> validator) {
		this.validator = validator;
		addModule(validator);
	}
	
	public void setGsonCloner(){
		setCloner(new GsonGAChromosomeCloner<T>(new Gson()));
	}
	
	public void setMinimizeFitness(GAFitnessFunction<T>fitness){
		fitness.setCondition(GAFitnessFunction.MINIMIZE);
		setFitness(fitness);
	}
	
	public void setMaximizeFitness(GAFitnessFunction<T>fitness){
		fitness.setCondition(GAFitnessFunction.MAXIMIZE);
		setFitness(fitness);
	}
	
	public void setProgressGraphicsEnabled(){
		onInitialization(new GAListener<T>() {
			
			@Override
			public void invoke(AbstractGA<T> ga) {
				//PlotUtil.enableXYPlot("Genetic Progress", "Generation", "Fitness", new String[]{"Average Fitness"/*,"mutation"*/}, PlotType.PLAIN);
			}
		});
		onIterationEnded(new GAListener<T>() {
			
			@Override
			public void invoke(AbstractGA<T> ga) {
				//PlotUtil.addData("Average Fitness", ga.getEpoch(), ga.getChromosomeCollection().getCurrentAverageFitness());
				//GeneralUtil.addData("mutation", ga.getEpoch(), ga.getMutator().getMutationProbability());
			}
		});
	}
	/**
	 * Adds an event listener on termination which will print information regarding the best chromosome and
	 * invoke the phenotype resolver for it in order to display it to the user.
	 */
	public void setPrintBestChromosomeOnFinish(){
		onTermination(new GAListener<T>() {
			@Override
			public void invoke(AbstractGA<T> ga) {
				System.out.println("Genetic finished: ");
				ga.getChromosomeCollection().getBestChromosome().phenotype(ga.getPhenotype());
				System.out.println("Score: "+ga.getChromosomeCollection().getBestChromosome().getCurrentFitness());
			}
		});
	}
	
	public GAMateSelector<T> getSelector() {
		return selector;
	}

	public void setSelector(GAMateSelector<T> selector) {
		this.selector = selector;
		addModule(selector);
	}
	
	public void setRouletteSelector(int elitism){
		setSelector(new RouletteGAMateSelector<T>(elitism));
	}
	
	public void setRankedSelector(int elitism){
		setSelector(new RankedGAMateSelector<T>(elitism));
	}

	public GACrossOverEngine<T> getCrossover() {
		return crossover;
	}

	public void setCrossover(GACrossOverEngine<T> crossover) {
		this.crossover = crossover;
		addModule(crossover);
	}

	public GATerminationCriteria<T> getTermination() {
		return termination;
	}

	public void setTermination(GATerminationCriteria<T> termination) {
		this.termination = termination;
		addModule(termination);
	}
	
	public GADataModelCloner<T> getCloner() {
		return cloner;
	}

	public void setCloner(GADataModelCloner<T> cloner) {
		this.cloner = cloner;
		addModule(cloner);
	}

	public void setTimeTerminationThreshold(final long milliseconds){
		setTermination(new GATerminationCriteria<T>() {
			@Override
			public boolean isTerminated() {
				return System.currentTimeMillis()-getInitTimeStamp()>milliseconds;
			}
		});
	}
	
	public void forceQuit(){
		setTimeTerminationThreshold(200);
	}
	
	public void setFitnessLowerThreshold(final double lowerThreshold){
		setTermination(new GATerminationCriteria<T>() {

			@Override
			public boolean isTerminated() {
				return getChromosomeCollection().getBestChromosome().getCurrentFitness()<=lowerThreshold;
			}
		});
	}
	public void setFitnessUpperThreshold(final double upperThreshold){
		setTermination(new GATerminationCriteria<T>() {
			
			@Override
			public boolean isTerminated() {
				return getChromosomeCollection().getBestChromosome().getCurrentFitness()>=upperThreshold;
			}
		});
	}
	
	public void setEpochThreshold(final int maxEpoch){
		setTermination(new GATerminationCriteria<T>() {

			@Override
			public boolean isTerminated() {
				return getEpoch()==maxEpoch;
			}
			
		});
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	public long getInitTimeStamp() {
		return initTimeStamp;
	}

	public void setInitTimeStamp(long initTimeStamp) {
		this.initTimeStamp = initTimeStamp;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Random getRandom(){
		return random;
	}
	
	public void invokeListeners(GAState state){
		for(GAListener<T>listener:eventListeners.get(state)){
			listener.invoke(this);
		}
	}
	
	public double getAvgNormFitness(){
		return getFitness().getNormalized(getChromosomeCollection().getCurrentAverageFitness());
	}
	
	public void optimize() throws Exception{
		if(generator==null){
			throw new MissingComponentException("Generator");
		}
		if(mutator==null){
			throw new MissingComponentException("Mutator");
		}
		if(fitness==null){
			throw new MissingComponentException("Fitness Function");
		}
		if(validator==null){
			throw new MissingComponentException("Validator");
		}
		if(crossover==null){
			throw new MissingComponentException("Crossover Engine");
		}
		if(selector==null){
			throw new MissingComponentException("Selector");
		}
		/*
		 * Initialization
		 */
		initTimeStamp= System.currentTimeMillis();
		epoch=0;
		chromosomeCollection = generator.createCollection();
		invokeListeners(GAState.Initialization);
		
		do{
			epoch++;
			/*
			 * Evaluation of Fitness
			 */
			fitness.startEvaluation();
			for(GAChromosome<T>data:chromosomeCollection){
				fitness.doEvaluateFitness(data);
			}
			fitness.evaluationEnded();
			
			/*
			 * Selection
			 */
			List<MatingPair<T>> fornicationList=selector.mate(chromosomeCollection);//
			/*
			 * Crossover
			 */
			chromosomeCollection.getDataset().clear();
			for(MatingPair<T>mate:fornicationList){
				if(!mate.isMasturbation()){
					crossover.crossOver(mate.getFirst(), mate.getSecond());
				}
				chromosomeCollection.getDataset().add(mate.getFirst());
				chromosomeCollection.getDataset().add(mate.getSecond());
			}
			/*
			 * Mutate 
			 */
			for(GAChromosome<T>data:chromosomeCollection){
				mutator.mutate(data);
			}
			/*
			 * Validation Policy
			 */
			validator.applyValidationPolicy();
			invokeListeners(GAState.IterationEnded);
		} while( !termination.isTerminated() );//loop until the termination criteria is met
		
		invokeListeners(GAState.Termination);
	}

}

