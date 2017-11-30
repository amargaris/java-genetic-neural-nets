package toolkit.optimization.genetic.chro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CompositeChromosome extends Chromosome{
	List<Chromosome> list;
	public CompositeChromosome(Chromosome...chromosomes ){
		super(chromosomes);
		list = Arrays.asList(chromosomes);
		
		int count = Arrays.asList(chromosomes).stream().mapToInt(i->new Integer(i.getSequence().length)).sum();
		List<Boolean>list = new ArrayList<>(count);
		for(Chromosome cha:chromosomes)
			for(boolean b :cha.sequence)
				list.add(b);
		
		Boolean[] sub_sequence = list.toArray(new Boolean[0]);
		boolean[] sequence = new boolean[sub_sequence.length];
		for(int i=0;i<sequence.length;i++){
			sequence[i]= sub_sequence[i];
		}
		setSequence(sequence);
		StringBuilder sb = new StringBuilder();
		for(Chromosome cha : chromosomes)
			sb.append(cha.toString());
		setName(sb.toString());
	}
	public abstract Object merge();
}