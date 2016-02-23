public class CorrelationPair implements Comparable<CorrelationPair> {

	private double normalizedValue;
	private String firstCharacter;
	private String secondCharacter;
	
	public CorrelationPair( double nv, String fc, String sc ){
		this.normalizedValue = nv;
		this.firstCharacter = fc;
		this.secondCharacter = sc;
	}
	public void printPair(){
		//the first print was true before I took into account the expected percentage of the vote
		//System.out.println(""+ this.normalizedValue + "% of the people that voted for "+ this.firstCharacter +" also voted for "+ this.secondCharacter);
		if( this.normalizedValue >= 0 ){
			System.out.println("+"+ this.normalizedValue + " percentage points between "+ this.firstCharacter.replaceFirst(";",",") +" and "+ this.secondCharacter.replaceFirst(";",","));
		}else{
			System.out.println(""+ this.normalizedValue + " percentage points between "+ this.firstCharacter.replaceFirst(";",",") +" and "+ this.secondCharacter.replaceFirst(";",","));
		}
	}
	
	public String getFirstCharacter(){
		return this.firstCharacter;
	}
	public String getSecondCharacter(){
		return this.secondCharacter;
	}
	
	public Double getDouble(){
		return new Double( this.normalizedValue );
	}
	
	@Override
	public int compareTo(CorrelationPair anotherCP) {
		return anotherCP.getDouble().compareTo(this.getDouble());
	}
}