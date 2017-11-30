package toolkit.optimization.training;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class RegressionTrainingSet extends AbstractTrainingSet{
	private double[][] dataFromXLSX;
	private int counter;
	public RegressionTrainingSet() throws Exception{
		super(1, 2);
		/*String pathName=SwingUtil.promptForOpenFile("Enter xslx",new String[]{"xlsx"},FileUtil.Desk);
		Workbook book = MicrosoftExcelUtil.fromFile(pathName);
		String[] sheets = MicrosoftExcelUtil.readSheetsFromWorkbook(book);
		String[][] data = MicrosoftExcelUtil.readSpecificStringTable(book, sheets[0],3);*/
		String[][] data = null;
		dataFromXLSX = new double[data.length-1][3];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				dataFromXLSX[i][j]= Double.parseDouble(data[i+1][j]);
			}
		}
		setSampleSize(dataFromXLSX.length);
	}
	public static void main(String... args){
		try {
			new RegressionTrainingSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public double[][] generateInputOutputVector() {
		if(counter==dataFromXLSX.length){
			counter=0;
		}
		double[] vals = dataFromXLSX[counter++];
		return new double[][]
				{{vals[0]},
				 {vals[1],vals[2]}};
	}
	public static int index=1;
	public static JFrame frame;
	@Override
	public void testNeural(double[] outputTraining, double[] outputNeural) {
		SwingUtilities.invokeLater(()->{
			if(frame ==null){
				frame=new JFrame("Results");//SwingUtil.generateFrame("Results");
				//frame.getContentPane().setLayout(new GridLayout(0,5));
				frame.setVisible(false);
			}
			if(!frame.isVisible()){
				frame.setVisible(true);
			}
			double[] x = new double[getSampleSize()];//AbstractTrainingSet.generate(0, 1, 100);
			double[] y1 = getRandomInputOutput()[1];
			//double[] y1 = AbstractTrainingSet.calculate(new LinearFunction(outputNeural[0],/*outputNeural[1]*/2.0), x);
			double[] y2 = outputNeural;//AbstractTrainingSet.calculate(new LinearFunction(outputTraining[0], /*outputTraining[1]*/2.0), x);
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
