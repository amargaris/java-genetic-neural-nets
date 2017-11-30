package toolkit.optimization.neural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import toolkit.optimization.neural.functions.SigmoidFunction;
import toolkit.optimization.neural.functions.SingleDimFunction;

public class Neuron{
	
	/*
	 * Constant
	 */
	private Neuron[] connections;//outputs
	protected int[] location;//index per output (poio index exei ayto to neuron ws pros to epomeno)
	private ArrayList<Double> weights;
	
	private int numberOfInputs;
	private String name;
	private SingleDimFunction transferFunction;

	/*
	 * State
	 */
	private int currentInputs;
	private double sum;
	
	public Neuron(String name){
		this(name,new SigmoidFunction(1));
	}
	public Neuron(String name,SingleDimFunction transferFunction){
		this.name=name;
		this.weights= new ArrayList<>();
		this.transferFunction= transferFunction;
		resetEpoch();
	}
	
	public String getName(){
		return name;
	}
	public void increaseInputs(){
		numberOfInputs++;
	}
	public void setNextLayer(Neuron nextNeuron){
		setNextLayer(new Neuron[]{nextNeuron});
	}
	public void setNextLayer(Neuron nextNeuron,double initialValue){
		setNextLayer( new Neuron[]{nextNeuron}, new double[]{initialValue});
	}
	public void setNextLayer(Neuron[] connections){
		
		connections = Arrays.asList(connections).stream().filter((n)->(!(n instanceof Bias))).collect(Collectors.toList()).toArray(new Neuron[0]);
		double[] weights= new double[connections.length];
		for(int i=0;i<weights.length;i++){
			weights[i]=NeuralNetwork.initialWeightFunction.f(0.0);
		}
		setNextLayer(connections, weights);
	}
	
	public void setNextLayer(Neuron[] connections,double[] weights){
		this.connections=connections;
		location = new int[weights.length];
		for(int i=0;i<connections.length;i++){
			Neuron neuron = connections[i];
			System.out.println(this.name+" connected to "+neuron.name);
			location[i]=neuron.numberOfInputs; //ayto einai gia to connection i toy to currentInput
			neuron.weights.add(weights[i]);
			neuron.increaseInputs();			
		}
	}
	
	public void feedForward(){
		double act=activationFunction(sum);
		for(int i=0;i<connections.length;i++){
			connections[i].receive(act,location[i]);
		}
	}
	public double activationFunction(double input){
		return transferFunction.f(input);
	}
	public void receive(double input,int index){
		sum+=input*weights.get(index);
		currentInputs++;
		if(currentInputs==numberOfInputs){
			feedForward();
			resetEpoch();
		}
	}
	public ArrayList<Double> getWeights() {
		return weights;
	}
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}
	public void resetEpoch(){
		sum=0;
		currentInputs=0;
	}
	public Neuron[] getConnections() {
		return connections;
	}
	
	public String toString(){
		return getName();
	}
	
}