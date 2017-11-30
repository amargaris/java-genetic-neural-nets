package toolkit.optimization.neural;

public class Bias extends Input{
	
	public Bias(NeuralNetwork net){
		this(net,"Bias "+(net.biasCounter++));
	}
	
	public Bias(NeuralNetwork net,String name){
		super(name,net,0);
	}

	@Override
	public double nextValue() {
		return 1.0;
	}
	
}