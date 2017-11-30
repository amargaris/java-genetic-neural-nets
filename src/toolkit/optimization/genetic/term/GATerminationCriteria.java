package toolkit.optimization.genetic.term;

import toolkit.optimization.genetic.GAModule;


public abstract class GATerminationCriteria<T> extends GAModule<T>{

	public abstract boolean isTerminated();
}