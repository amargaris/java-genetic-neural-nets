package toolkit.optimization.genetic.chro;

public class IntChromosome extends Chromosome{
	public IntChromosome(int value){
		super(value);
		Chromosome ch = Chromosome.fromInt(value);
		setSequence(ch.getSequence());
	}
}