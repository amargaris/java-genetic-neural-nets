package toolkit.optimization.neural.functions;

public abstract class SingleDimFunction implements Function<Double, Double>{
	private SingleDimFunction der;

	protected abstract double fv(double x);
	
	public SingleDimFunction(){
		this(null);
	}
	public SingleDimFunction(SingleDimFunction der){
		this.der=der;
	}
	public SingleDimFunction getDer() throws Exception{
		if(der!=null){
			return der;
		}
		throw new Exception("Unknown Derivative");
	}

	@Override
	public final Double f(Double x) {
		return fv(x);
	}
}
