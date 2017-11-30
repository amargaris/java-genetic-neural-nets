package toolkit.optimization.linear;

import java.util.Arrays;

import toolkit.optimization.linear.eval.Evaluator;
import toolkit.optimization.linear.term.TerminatorCriteria;
import toolkit.optimization.linear.vectrav.BinaryVectorTraversal;
import toolkit.optimization.linear.vectrav.VectorTraversal;

public class LinearProblemModel {
	
	private Evaluator eval; 
	private VectorTraversal vect;
	private TerminatorCriteria term;
	private int dimensions;
	private Double[] errors;
	private long timeStamp;
	
	private boolean enableGraphics;
	
	public LinearProblemModel(int dimensions,Evaluator eval,VectorTraversal traversal){
		this.eval=eval;
		this.vect=traversal;
		this.dimensions=dimensions;
		errors = new Double[dimensions];
	}
	public LinearProblemModel(int dimensions,Evaluator eval,double[] initial,double step){
		this(dimensions, eval, new BinaryVectorTraversal(initial, step));
	}
	public LinearProblemModel setGraphics(boolean enableGraphics){
		this.enableGraphics=enableGraphics;
		return this;
	}
	public LinearProblemModel stopWithErrorThreshold(double errorThreshold){
		double[] errors=new double[dimensions];
		Arrays.fill(errors, errorThreshold);
		stopWithErrorThreshold(errors);
		return this;
	}
	public LinearProblemModel stopWithErrorThreshold(final double[] errorThresholdPerDimension){
		term = new TerminatorCriteria() {
			
			@Override
			public boolean willTerminate(LinearProblemModel input) {
				for(int i=0;i<errorThresholdPerDimension.length;i++){
					if(errors[i]==null){
						return false;
					}
					if(Math.abs(errors[i])>errorThresholdPerDimension[i]){
						return false;
					}
				}
				return true;
			}
		};
		return this;
	}
	public LinearProblemModel stopAfterMilliseconds(final long milliseconds){
		term = new TerminatorCriteria() {
			@Override
			public boolean willTerminate(LinearProblemModel input) {
				return (System.currentTimeMillis()-timeStamp)>=milliseconds;
			}
		};
		return this;
	}
	public void printSolution(){
		System.out.println("Solution : "+Arrays.toString(solve()));
	}
	public double[] solve(){
		try{
			this.timeStamp=System.currentTimeMillis();
			double[] input=null;
			for(int i=0;i<dimensions;i++){
				if(enableGraphics){
//					PlotUtil.enableXYPlot("overview", "Epoch", "Error", new String[]{"value"}, PlotType.PLAIN);
//					PlotUtil.enableXYPlot("line", "Epoch", "Error", new String[]{"value"}, PlotType.PLAIN);
				}
				int k=0;
				do{
					input=vect.getNextVector(errors[i],i);
					errors[i]=eval.evaluate(input);
					if(enableGraphics){
//						PlotUtil.addData("line","value", input[i],errors[i]);
//						PlotUtil.addData("overview","value", k++, errors[i]);
					}
				}while(!term.willTerminate(this));
			}
			return input;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
