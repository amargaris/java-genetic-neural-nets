package toolkit.optimization.genetic.chro;

public class Chromosome{
	boolean[] sequence;
	private String name;
	private Object value;
	
	public boolean[] getSequence() {
		return sequence;
	}
	public void setSequence(boolean[] sequence) {
		this.sequence = sequence;
		generateName();
	}
	protected void generateName(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<sequence.length;i++)
			sb.append(sequence[i]?"1":"0");
		name = sb.toString();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public Chromosome(Object value) {
		super();
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}

	private static final int[] intConv = new int[32];
	private static final long[] longConv = new long[64];
	private static final double[] doubleConv = new double[64];
	
	static{
		int start = 0b1;
		for(int i=0;i<intConv.length;i++){
			intConv[i]=start;
			start = start << 1;
		}
		long start2 = 0b1;
		for(int i=0;i<longConv.length;i++){
			longConv[i] = start2;
			start2 = start2 << 1;
		}
		double start3 = 0b1;
		for(int i=0;i<doubleConv.length;i++){
			doubleConv[i] = start3;
			long temp = Double.doubleToLongBits(start3);
			temp = temp << 1;
			start3 = Double.longBitsToDouble(temp);
		}
	}
	public static Chromosome fromInt(int value){
		Chromosome ch = new Chromosome(value);
		boolean[] chr = new boolean[intConv.length];
		for(int i=0;i<intConv.length;i++){
			chr[i] = (value | intConv[i])==value;
		}
		ch.setSequence(chr);
		return ch;
	}
	public static Chromosome fromLong(long value){
		Chromosome ch = new Chromosome(value);
		boolean[] chr = new boolean[longConv.length];
		for(int i=0;i<longConv.length;i++){
			chr[i] = (value | longConv[i])==value;
		}
		ch.setSequence(chr);
		return ch;
	}
	public static Chromosome fromDouble(double value){
		Chromosome ch = new Chromosome(value);
		boolean[] chr = new boolean[longConv.length];
		for(int i=0;i<doubleConv.length;i++){
			long l1 = Double.doubleToLongBits(value);
			long l2 = Double.doubleToLongBits(doubleConv[i]);
			
			chr[i] = (l1 | l2)==l1;
		}
		ch.setSequence(chr);
		return ch;
	}
}