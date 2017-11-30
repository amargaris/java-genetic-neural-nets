package toolkit.optimization.linear.vectrav;

import java.util.Arrays;

public class BinaryVectorTraversal implements VectorTraversal{
	
	private double[] startingVector,currentVector;
	private double startingStep,currentStep;
	private double factor;
	private Double previousError;
	
	public BinaryVectorTraversal(double[] startingVector,double startingStep){
		this.startingVector=startingVector;
		this.startingStep=startingStep;
		reset();
	}
	public void reset(){
		this.previousError=null;
		this.factor=1.0;
		this.currentVector=Arrays.copyOf(startingVector, startingVector.length);
		this.currentStep=startingStep;
		//TODO other as well
	}
	public void goodDirection(){
		//this.factor*=2;
	}
	@Override
	public double[] getNextVector(Double newError,int dimension) {
		if(previousError!=null){
			if(newError*previousError<0){ //bolzano
				currentStep/=2.0;
				factor = -factor;
			}else{
				if(newError<0){
					if(newError-previousError<0){
						factor = -factor;
					}else{
						goodDirection();
					}
				}else{
					if(newError-previousError>0){
						factor = -factor;
					}else{
						goodDirection();
					}
				}
			}
			currentVector[dimension]+=(currentStep*factor);
		}
		this.previousError=newError;
		return currentVector;
	}
	
}