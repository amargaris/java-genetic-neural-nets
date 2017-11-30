package toolkit.optimization.training;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import toolkit.optimization.neural.functions.LinearFunction;

public class LinearTrainingSet extends AbstractTrainingSet{
	
	private double min,max;

	public LinearTrainingSet(double min,double max,int sampleSize) {
		super(4, 1, sampleSize);
		this.min=min;
		this.max=max;
	}
	
	@Override
	public double[][] generateInputOutputVector() {
		double a=min+getRandom().nextDouble()*(max-min);
		double b=min+getRandom().nextDouble()*(max-min);
		double x = min+getRandom().nextDouble()*(max-min);
		double x1 = min+getRandom().nextDouble()*(max-min);
		LinearFunction func = new LinearFunction(a, b);
		double y = func.f(x);
		double y1 = func.f(x1);
		return new double[][]
				{{x,y,x1,y1},  //input
				 {a/*,b*/}}; //output
	}
	public static int index=1;
	public static JFrame frame;
	@Override
	public void testNeural(double[] outputTraining, double[] outputNeural) {
		SwingUtilities.invokeLater(()->{
			if(frame ==null){
				frame=new JFrame("Results");//SwingUtil.generateFrame("Results");
				frame.getContentPane().setLayout(new GridLayout(0,5));
				frame.setVisible(false);
			}
			if(!frame.isVisible()){
				frame.setVisible(true);
			}
			System.out.println(String.format("Actual Line: y = %f * x + %f",outputTraining[0],2.0/*outputNeural[1]*/));
			System.out.println(String.format("Estimated Line: y = %f * x + %f",outputNeural[0],2.0/*outputNeural[1]*/));
			double[] x = AbstractTrainingSet.generate(min, max, 100);
			double[] y1 = AbstractTrainingSet.calculate(new LinearFunction(outputNeural[0],/*outputNeural[1]*/2.0), x);
			double[] y2 = AbstractTrainingSet.calculate(new LinearFunction(outputTraining[0], /*outputTraining[1]*/2.0), x);
			//JFrame 
//			PlotUtil.enableXYPlot(frame,"Curve - "+index++, "Independant Variable x", "Dependant Variable y", 
//					new String[]{"Actual","Estimation"}, PlotType.PLAIN,0);
//			for(int i=0;i<x.length;i++){
//				PlotUtil.addData("Estimation", x[i], y1[i]);
//				PlotUtil.addData("Actual", x[i], y2[i]);
//			}
		});
	}
}