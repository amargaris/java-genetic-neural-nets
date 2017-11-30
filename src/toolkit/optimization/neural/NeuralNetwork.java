package toolkit.optimization.neural;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import toolkit.optimization.neural.functions.AbsErrorFunction;
import toolkit.optimization.neural.functions.ErrorFunction;
import toolkit.optimization.neural.functions.SingleDimFunction;
import toolkit.optimization.training.AbstractTrainingSet;
	

public class NeuralNetwork {

	private ErrorFunction errorFunction;
	public static double SCALE=1;
	public static SingleDimFunction initialWeightFunction= new SingleDimFunction() {
		Random rand = new Random();
		@Override
		protected double fv(double x) {
			return rand.nextGaussian()*SCALE;
		}
	};

	private double[] iterationInputVector;//ayto tha epistrefei pantote to Input neuron X
	private double[] iterationOutputVector;//edw apothikeyei ta apotesmata toy to Output Neuron
	
	private List<Neuron> inputs; //ayta kanoyn trigger to neural
	private List<Neuron> adjustable;//tha ginei replaced me to layer API

	private List<List<Neuron>>layers;
	private AbstractTrainingSet training;
	
	int biasCounter;
	
	public NeuralNetwork(AbstractTrainingSet set,int[] hiddenLayers,boolean hasBias){
		training = set;
		try {
			training.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		inputs = new LinkedList<>();
		adjustable= new ArrayList<>();
		layers= new ArrayList<>();
		errorFunction=new AbsErrorFunction();
		biasCounter=0;
		for(int i=0;i<training.getNumberOfInputs();i++){
			new Input("Input "+i,this,i);
		}
		if(hasBias)
			new Bias(this);
		//new Bias(this);//TODO 1 bias gia kathe layer... uber sfalma + bias sto output
		/*
		 * Outputs
		 *  O -->output[0]
		 *  O -->output[1]
		 *  0 --> ...
		 */
		
		iterationOutputVector = new double[training.getNumberOfOutputs()];
		List<Neuron> outputLayer = new ArrayList<>();
		for(int i=0;i<training.getNumberOfOutputs();i++){
			Neuron central = new Neuron("Output Layer, "+i);
			central.setNextLayer(new Output(this, i, "Y value ("+i+")"));
			outputLayer.add(central);
			adjustable.add(central);
		}
		
		//outputLayer.add(bias);
		//adjustable.add(bias);
		layers.add(outputLayer);
		/*
		 * Creating Hidden layer
		 */
		List<Neuron>previous=null;
		for(int i=hiddenLayers.length-1;i>=0;i--){
			List<Neuron>theLayer= new ArrayList<>();
			for(int j=0;j<hiddenLayers[i];j++){
				theLayer.add(new Neuron("Hidden Layer "+i+" neuron "+j));
			}
			//theLayer.add(new Bias(this));
			Neuron[] forNext = previous==null?outputLayer.toArray(new Neuron[0]):previous.toArray(new Neuron[0]);
			for(Neuron neuron:theLayer){
				neuron.setNextLayer(forNext);
				adjustable.add(neuron);
			}
			previous=theLayer;
			addLayer(theLayer);
			
		}
		/*
		 * Linking input streams with the next neurons (first)
		 */
		for(Neuron inputNeuron:inputs){
			inputNeuron.setNextLayer(getFirstLayer().toArray(new Neuron[0]));
		}
		
	}
	public void addLayer(List<Neuron> newLayer){
		layers.add(newLayer);
	}
	public void setOutput(int index,double value){
		iterationOutputVector[index]=value;
	}
	public AbstractTrainingSet getTrainingSet(){
		return training;
	}
	
	public List<Neuron> getFirstLayer(){
		return layers.get(layers.size()-1);
	}
	
	public List<Neuron> getInputs(){
		return inputs;
	}
	
	public void setWeights(double[] weights){
		int counter=0;
		for(int i=0;i<adjustable.size();i++){
			Neuron current = adjustable.get(i);
			for(int j=0;j<current.getWeights().size();j++){
				current.getWeights().set(j, weights[counter++]);
			}
		}
	}
	public Double[] getWeights(){
		List<Double>weights = new ArrayList<>();
		for(Neuron neuron:adjustable){
			for(Double d:neuron.getWeights()){
				weights.add(d);
			}
		}
		return weights.toArray(new Double[0]);
	}
	public double[] getRandomWeights(){
		int totalWeights = getWeights().length;
		double[] weightArray=new double[totalWeights];
		for(int i=0;i<totalWeights;i++){
			weightArray[i]=initialWeightFunction.f(0.0);
		}
		return weightArray;
	}
	
	public double[] getCurrentInputs(){
		return iterationInputVector;
	}
	public void iterate(double[] input){
		this.iterationInputVector=input;
		for(Neuron inp:inputs){
			inp.feedForward();
		}
	}
	public double train(){
		double totalError=0;
		for(int i=0;i<training.size();i++){
			double[] input= training.getTrainingInput(i);
			double[] output=training.getTrainingOutput(i);
			iterate(input);
			double er= 0.0;
			for(int k=0;k<output.length;k++){
				er+=errorFunction.error(output[k],iterationOutputVector[k]);
			}
			totalError+=er;
		}
		return totalError/training.size();
	}
	public void showResult(int numberOfResults){
		//int numberOfResults =10;
		for(int i=0;i<numberOfResults;i++){
			double[][] randomSample = training.getRandomInputOutput();
			double[] targetAB = randomSample[1];
			iterate(randomSample[0]);
			double[] result=iterationOutputVector;
			training.testNeural(targetAB, result);
		}
		
	}
}
