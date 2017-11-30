package toolkit.optimization.neural;

public class Output extends Neuron{
	
	private NeuralNetwork net;
	private int index;
	
	public Output(NeuralNetwork net,int index,String name) {
		super(name);
		this.net=net;
		this.index=index;
		setNextLayer(new Neuron[0]);
	}
	public void receive(double value,int i){
		net.setOutput(index,net.getTrainingSet().getScaled(index,value));
	}
}