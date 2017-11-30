package toolkit.optimization;

public interface ProblemModel<T>{
	public void calculateCostFunction();
	public void generateDataset();
	public T getBestSolution();
	public T getWorstSolution();
	
	public default T solve(){
		generateDataset();
		calculateCostFunction();
		return getBestSolution();
	}
}