package toolkit.optimization.genetic.fit;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.GAState;
import toolkit.optimization.genetic.data.GAChromosome;

public abstract class GAFitnessFunction<T> extends GAModule<T>{
	private double epochTotalEvaluation;

	private GAChromosome<T> bestChromosome;

	private int condition;
	
	public static final int MAXIMIZE= 1;
	public static final int MINIMIZE=-1;
	
	/**
	 * Default Fitness Function is set to maximize.
	 */
	public GAFitnessFunction() {
		this.condition=MAXIMIZE;
	}
	/**
	 * Change fitness function criteria: Maximize or Minimize
	 * @param condition GAFitnessFunction.Maximize or GAFitnessFunction.Minimize
	 */
	public void setCondition(int condition){
		this.condition=condition;
	}
	/**
	 * Cleans up the state of the evalution.
	 */
	public void startEvaluation(){
		bestChromosome=null;
		epochTotalEvaluation=0;
	}
	public void pushAverageEvaluation(){
		getParent().getChromosomeCollection().setCurrentAverageFitness(epochTotalEvaluation);
	}
	public void evaluationEnded(){
		epochTotalEvaluation/=getParent().getGenerator().getPopulationSize();
		pushAverageEvaluation();
		getParent().getChromosomeCollection().setBestChromosome(bestChromosome);
		getParent().invokeListeners(GAState.Evaluation);
	}
	public int getCondition(){
		return condition;
	}
	public void doEvaluateFitness(GAChromosome<T> input){
		Double score=evaluateFitness(input);
		input.updateFitness(score);
		getParent().getChromosomeCollection().update(score);
		if(bestChromosome==null){
			bestChromosome=input;
		}
		else{
			if(score.compareTo(bestChromosome.getCurrentFitness())*condition>0){
				bestChromosome=input;
			}
		}
		epochTotalEvaluation+=score;
	}
	public double getNormalized(double input){
		double max=getParent().getChromosomeCollection().getCurrentMaxFitness();
		double min=getParent().getChromosomeCollection().getCurrentMinFitness();
		input-=min;
		input/=(max-min);

		if(condition==MAXIMIZE){
			return input;
		}
		else{
			return 1-input;
		}
	}
	public abstract Double evaluateFitness(GAChromosome<T> input);
}