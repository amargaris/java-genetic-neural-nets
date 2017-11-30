package toolkit.optimization.genetic.term;



public class EpochTerminationCriteria<T> extends GATerminationCriteria<T>{
	private int numberOfEpochs;
	public EpochTerminationCriteria(int numberOfEpochs) {
		this.numberOfEpochs=numberOfEpochs;
	}

	@Override
	public boolean isTerminated() {
		return getParent().getEpoch()==numberOfEpochs;
	}
	
}