import java.util.*;

public class CharacterLink {
	
	private String name;
	private int totalVotes;
	Map<CharacterLink, Integer> otherCharacters;
	
	public CharacterLink(String aName){
		this.name = aName;
		totalVotes = 0;
		otherCharacters = new HashMap<CharacterLink, Integer>();
	}
	
	public String getName(){
		return name;
	}
	public int getVotes(){
		return totalVotes;
	}
	public void incVotes(){
		this.totalVotes++;
	}
	public Integer mapPut( CharacterLink cl, Integer sharedVotes ){
		Integer oldVal = otherCharacters.put( cl, sharedVotes );
		return oldVal;
	}
	public Integer mapGet( CharacterLink cl ){
		return otherCharacters.get( cl );
	}
	public boolean mapConatinsKey( CharacterLink cl ){
		return otherCharacters.containsKey( cl );
	}
	public Set<CharacterLink> mapKeySet(){
		return otherCharacters.keySet();
	}
	public Set<Map.Entry<CharacterLink, Integer>> mapEntrySet(){
		return otherCharacters.entrySet();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
    	    return false;
    	}
    	if (!CharacterLink.class.isAssignableFrom(obj.getClass())) {
    	    return false;
    	}
    	final CharacterLink other = (CharacterLink) obj;
    	if( this.getName().equals(other.getName()) ){
    		return true;
    	}else{
    		return false;
    	}
    }
}