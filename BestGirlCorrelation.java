import java.util.*;
import java.io.*;

public class BestGirlCorrelation{
	
	public static final double TotalVotes = 3369.0;
	public static final int numOfVotesForGirlToCount = 20;
	public static final double DifferenceDisplayThreshold = 20.0;
	
	public static void main( String[] args ){
		BufferedReader infile;
		String[] splitLine;
		String line;
		CharacterLink currentChar, otherChar;
		int aLIndex;
		Integer correlation;
		HashMap<String, Double> nameToVotes;
		ArrayList<CharacterLink> allCharacters = new ArrayList<CharacterLink>();
		try{
			infile = new BufferedReader( new FileReader( "results.txt" ) );
		}catch(FileNotFoundException e){
			System.out.println("Error: File 'results.txt' not found. Exiting...");
			return;
		}
		
		try{
			line = infile.readLine();
		}catch(IOException e){
			System.out.println("Error: IOException reading from 'results.txt'. Exiting...");
			return;
		}
		while( line != null ){
			splitLine = line.split(",");
			for(int i = 1; i < splitLine.length; i+=2){
				//only want every other column b/c of the "have you seen" question first
				if( splitLine[i].equals("No opinion") || splitLine[i].equals("") ){
					continue;
				}
				currentChar = new CharacterLink(splitLine[i]);
				aLIndex = allCharacters.indexOf( currentChar );
				if( aLIndex == -1 ){
					//this character is not in the list
					allCharacters.add( currentChar );
				}else{
					//this character is in the list
					currentChar = allCharacters.get( aLIndex );
				}
				currentChar.incVotes();
				for(int j = i+2; j < splitLine.length; j+=2){
					if( splitLine[j].equals("No opinion") || splitLine[j].equals("") ){
						continue;
					}
					otherChar = new CharacterLink(splitLine[j]);
					aLIndex = allCharacters.indexOf( otherChar );
					if( aLIndex == -1 ){
						//this character is not in the list
						allCharacters.add( otherChar );
					}else{
						//this character is in the list
						otherChar = allCharacters.get( aLIndex );
					}
					//inc the connection between currentChar and otherChar
					correlation = currentChar.mapGet( otherChar );
					if( correlation == null ){
						//not in map
						currentChar.mapPut( otherChar, 1 );
						otherChar.mapPut( currentChar, 1 );
					}else{
						//in map just increment
						currentChar.mapPut( otherChar, correlation+1 );
						otherChar.mapPut( currentChar, correlation+1 );
					}
				}//end j for loop
			}//end i for loop
			
			try{
				line = infile.readLine();
			}catch(IOException e){
				System.out.println("Error: IOException reading from 'results.txt'. Exiting...");
				return;
			}
		}//end while
		try{
			infile.close();
		}catch(IOException e){
			System.out.println("Error: Failed to close input file. Exiting...");
			return;
		}

		//all characters have been read in from file and stored with correlations in arraylist
		ArrayList<CorrelationPair> arrListForSortResults = new ArrayList<CorrelationPair>();
		for( CharacterLink oneCharacter : allCharacters ){
			if( oneCharacter.getVotes() > numOfVotesForGirlToCount ){//to avoid girls with only a few votes skewing data
				for (Map.Entry<CharacterLink, Integer> entry : oneCharacter.mapEntrySet()) {
					arrListForSortResults.add(new CorrelationPair( ((double)(Math.round(100000.0* (((entry.getValue()+0.0)/(oneCharacter.getVotes()+0.0)) - ((double)entry.getKey().getVotes())/TotalVotes) )))/1000.0, oneCharacter.getName(), entry.getKey().getName()));
				}
			}
		}
		Collections.sort(arrListForSortResults);
		for( CorrelationPair oneResult : arrListForSortResults ){
			if( Math.abs(oneResult.getDouble()) > DifferenceDisplayThreshold ){
				oneResult.printPair();
			}
		}
	}//end main
}