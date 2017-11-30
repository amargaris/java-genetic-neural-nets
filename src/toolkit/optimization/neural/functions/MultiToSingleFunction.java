package toolkit.optimization.neural.functions;

public abstract class MultiToSingleFunction implements Function<double[], Double>{
	private MultiToSingleFunction der;

	public abstract double fv(double[] x);
	
	public MultiToSingleFunction(){
		this(null);
	}
	public MultiToSingleFunction(MultiToSingleFunction der){
		this.der=der;
	}
	public MultiToSingleFunction getDer() throws Exception{
		if(der!=null){
			return der;
		}
		throw new Exception("Unknown Derivative");
	}

	@Override
	public Double f(double[] x) {
		return fv(x);
	}

}
