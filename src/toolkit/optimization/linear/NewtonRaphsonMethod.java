package toolkit.optimization.linear;

import toolkit.optimization.neural.functions.SingleDimFunction;

public class NewtonRaphsonMethod {
	
	private static final double limit = Math.pow(10, -4);
	private static final double offset = 1;
	
	private SingleDimFunction f;
	
	public NewtonRaphsonMethod(SingleDimFunction f){
		this.f=f;
	}
	/**
	 * test for nick
	 */
	public NewtonRaphsonMethod(){
		this(8,3);
	}
	public NewtonRaphsonMethod(double m,double n){
		this(
/*f(x)*/new SingleDimFunction(
/*f'(x)*/	new SingleDimFunction(
/*f''(x)*/		new SingleDimFunction(){ 
						@Override public double fv(double x) { 
							return - Math.sinh(x-1)*Math.log((2+n)*x+1) 
							       + (2+n)*Math.cosh(x-1)/((2+n)*x+1) 
							       + (2+n)*((-(2+n)/Math.pow((2+n)*x+1,2))*Math.sinh(x-1) 
							       + Math.cosh(x-1)/(2+n)*x+1);
					}}){
				@Override public double fv(double x) {return Math.cosh(x-1)*Math.log((2+n)*x+1) + ((2+n)/((2+n)*x+1) ) *Math.cosh(x-1);}}) 
		{ @Override public double fv(double x) { return Math.sinh(x-1)*Math.log((2+n)*x+1) -m-1;}});
	}
	public double y(double x){
		return f.f(x);
	}
	public double yder(double x) throws Exception{
		return f.getDer().f(x);
	}
	public double yderder(double x) throws Exception{
		return f.getDer().getDer().f(x);
	}
	public static double getNeutonRaphsonError(double M,double m,double error){
		return M/(2*m) * error;
	}
	public static double getAbsoluteError(double x1,double x2){
		return Math.pow(Math.abs(x1-x2),2);
	}
	/**
	 * [xstart,xend) : N = numberOfSamples
	 * @param xstart
	 * @param xend
	 * @param numberOfSamples
	 * @return
	 */
	public static double[] sample(double xstart,double xend,int numberOfSamples){
		double range = xend-xstart;
		double step = range/numberOfSamples;
		double[] vals = new double[numberOfSamples];
		for(int i=0;i<vals.length;i++){
			vals[i]=xstart+step*i;
		}
		return vals;
	}
	public static SingleDimFunction getDer(SingleDimFunction of,int order) throws Exception{
		SingleDimFunction dim = of;
		for(int i=0;i<order;i++){
			dim=dim.getDer();
		}
		return dim;
	}
	public double[] getFx(double[] x) throws Exception{
		return getSampleResult(x, getDer(f,0));
	}
	
	public double[] getFDerX(double[] x) throws Exception{
		return getSampleResult(x, getDer(f,1));
	}
	
	public double[] getFderder(double[] x) throws Exception {
		return getSampleResult(x, getDer(f,2));
	}
		
	public double[] getSampleResult(double[] sample, SingleDimFunction simple){
		double[] y = new double[sample.length];
		for(int i=0;i<y.length;i++){
			y[i]=simple.f(sample[i]);
		}
		return y;
	}
	public double solve(double x,double limit) throws Exception{
		double error=0.0;
		do{
			double tempx=x;
			x = x - y(x)/yder(x);
			error = getNeutonRaphsonError(yderder(x),yder(x) , getAbsoluteError(tempx, x));
			System.out.println("new X = "+x);
		}while(error>limit);
		return x;
	}
	public static double min(double[] vals){
		double min = Double.POSITIVE_INFINITY;
		for(double d:vals){
			if(d<min){
				min=d;
			}
		}
		return min;
	}
	public static double max(double[] vals){
		double max = Double.NEGATIVE_INFINITY;
		for(double d:vals){
			if(d>max){
				max=d;
			}
		}
		return max;
	}
	public static void main(String...strings ) throws Exception{
		NewtonRaphsonMethod nrm = new NewtonRaphsonMethod(7,0);
		double[] mySamples = sample(0, 4, 10000);
		double[] y = nrm.getFx(mySamples);
		double[] yder = nrm.getFDerX(mySamples);
		double[] yderder = nrm.getFderder(mySamples);
//		PlotUtil.enableXYPlot("Newton Raphson Method", "x", "y", new String[]{"y","yder","yderder"}, PlotType.PLAIN);

		double xStart = NewtonRaphsonMethod.offset;
		nrm.solve(xStart,limit);
		
		for(int i=0;i<mySamples.length;i++){
			if(Double.isNaN(y[i])||Double.isNaN(yder[i]))
				continue;
//			PlotUtil.addData( "y", mySamples[i], y[i]);
//			PlotUtil.addData( "yder", mySamples[i], yder[i]);
//			PlotUtil.addData("yderder", mySamples[i], yderder[i]);
		}
	}
}
