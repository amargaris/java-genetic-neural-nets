package toolkit.optimization.genetic.valid;

import java.util.Iterator;

import toolkit.optimization.genetic.GAModule;
import toolkit.optimization.genetic.data.GAChromosomeCollection;
import toolkit.optimization.genetic.data.GAChromosome;

public abstract class GAChromosomeValidator<T> extends GAModule<T>{

	public void applyValidationPolicy(){
		GAChromosomeCollection<T> coll=getParent().getChromosomeCollection();
		Iterator<GAChromosome<T>> it=coll.iterator();
		int counter=0;
		while(it.hasNext()){
			GAChromosome<T> dataModel=it.next();
			if(!isValid(dataModel)){
				it.remove();
				counter++;
			}
		}
		if(counter!=0){
			System.out.println("unfinished");
			System.exit(0);
			/*GADataCollection<T> padding=getParent().getGenerator().createCollection();TODO 
			getParent().getDataset().getDataset().addAll(padding.getDataset());*/
		}
	}
	public abstract Boolean isValid(GAChromosome<T> data);
}