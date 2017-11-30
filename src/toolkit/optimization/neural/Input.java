package toolkit.optimization.neural;


public class Input extends Neuron{
	private NeuralNetwork net;
	private int index;
	public Input(String name,NeuralNetwork net,int index) {
		super(name);
		this.net=net;
		this.index=index;
		this.net.getInputs().add(this);
		
	}
	public void feedForward(){
		double val = nextValue();
		for(int i=0;i<getConnections().length;i++){
			getConnections()[i].receive(val,location[i]);
		}
	}
	public  double nextValue() {
		return net.getCurrentInputs()[index];
	}
}