package toolkit.optimization.training;

import java.util.Arrays;
import java.util.Random;

import toolkit.optimization.neural.functions.SingleDimFunction;

public abstract class AbstractTrainingSet{
	
	private final double[] min,max;
	private final double[][] inputSet;
	private final double[][] outputSet;
	
	private int sampleSize;

	private Random rand;
	
	public AbstractTrainingSet (int numberOfInputs,int numberOfOutputs,int sampleSize){
		
		
		min = new double[numberOfOutputs];
		max = new double[numberOfOutputs];
		
		inputSet = new double[sampleSize][numberOfInputs];
		outputSet = new double[sampleSize][numberOfOutputs];
		
		rand= new Random();
		
		this.sampleSize=sampleSize;		
	}
	public AbstractTrainingSet(int numberOfInputs,int numberOfOutputs){
		this(numberOfInputs,numberOfOutputs,0);
	}
	public void setSampleSize(int sampleSize){
		this.sampleSize = sampleSize;
	}
	
	public int getSampleSize(){
		return sampleSize;
	}
	
	public void init() throws Exception{
		if(sampleSize==0){
			throw new Exception("Sample has size :0");
		}
		for(int i=0;i<sampleSize;i++){
			double[][] inputOutputVector=generateInputOutputVector();
			inputSet[i]=inputOutputVector[0];
			outputSet[i]=inputOutputVector[1];
		}
		
		for(int i=0;i<outputSet[0].length;i++){
			calculateStandardization(i);
		}
	}
	
	public int getNumberOfInputs(){
		return inputSet[0].length;
	}
	
	public int getNumberOfOutputs(){
		return outputSet[0].length;
	}
	
	public abstract double[][] generateInputOutputVector();
	public abstract void testNeural(double[] outputTraining,double[] outputNeural);
	
	public Random getRandom(){
		return rand;
	}
	
	public int size(){
		return inputSet.length;
	}
	public double[][] getRandomInputOutput(){
		int a= getRandom().nextInt(size());
		return new double[][]
				{getTrainingInput(a)
				,getTrainingOutput(a)};
	}
	
	public double[] getTrainingInput(int i){
		return Arrays.copyOf(inputSet[i], inputSet[i].length);
	}
	
	public double[] getTrainingOutput(int i){
		return Arrays.copyOf(outputSet[i], outputSet[i].length);
	}
	
	public void calculateStandardization(int index){
		min[index]=Double.POSITIVE_INFINITY;
		max[index]=Double.NEGATIVE_INFINITY;
		
		for(int i=0;i<size();i++){
			double currentOutputValue = getTrainingOutput(i)[index];
			if(currentOutputValue<min[index]){
				min[index]=currentOutputValue;
			}
			if(currentOutputValue>max[index]){
				max[index]=currentOutputValue;
			}
		}
	}
	
	public double getScaled(int index,double standardizedValue) {
		return min[index] +standardizedValue*(max[index]-min[index]);
	}
	
	public double getStandardized(int index,double scaled) throws Exception{
		if(min[index]!=max[index]){
			return (scaled-min[index])/(max[index]-min[index]);
		}
		throw new Exception("no max no min exception");
	}
	public static double[] generate(double min,double max,int number){
		double[] vals = new double[number];
		double range = max-min;
		range/=(double)number;
		for(int i=0;i<vals.length;i++){
			vals[i]=min+i*range;
		}
		return vals;
	}
	public static double[] calculate(SingleDimFunction target,double[] x){
		double[] y= new double[x.length];
		for(int i=0;i<y.length;i++){
			y[i]=target.f(x[i]);
		}
		return y;
	}
	
}