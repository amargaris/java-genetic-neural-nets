package toolkit.optimization.genetic.chro;

public class BooleanChromosome extends Chromosome{
	public BooleanChromosome(boolean value){
		super(value);
		setSequence(new boolean[]{value});
	}
}