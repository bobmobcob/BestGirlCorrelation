import java.util.*;
import java.io.*;

public class BestGirlCorrelationVHigi{
	
	public static final double TotalVotes = 3369.0;
	public static final int numOfVotesForGirlToCount = 20;//this doesn't matter in this version
	public static final double DifferenceDisplayThreshold = 20.0;//neither does this
	//use odd numbers to refer to series
	//nisekoi was the first series in the poll, monogatari was the second, fate was third...
	//odd numbers because every other question was "have you seen show x"
	public static final int[] seriesToUse = {1,3,5};
	
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
			for(int i = 0; i < seriesToUse.length; i++){
				//only want every other column b/c of the "have you seen" question first
				//System.out.println(""+line+" "+i+" "+seriesToUse[i]);
				if( splitLine[seriesToUse[i]].equals("No opinion") || splitLine[seriesToUse[i]].equals("") ){
					continue;
				}
				currentChar = new CharacterLink(splitLine[seriesToUse[i]]);
				aLIndex = allCharacters.indexOf( currentChar );
				if( aLIndex == -1 ){
					//this character is not in the list
					allCharacters.add( currentChar );
				}else{
					//this character is in the list
					currentChar = allCharacters.get( aLIndex );
				}
				currentChar.incVotes();
				for(int j = i+1; j < seriesToUse.length; j++){
					if( splitLine[seriesToUse[j]].equals("No opinion") || splitLine[seriesToUse[j]].equals("") ){
						continue;
					}
					otherChar = new CharacterLink(splitLine[seriesToUse[j]]);
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
		//ArrayList<CorrelationPair> arrListForSortResults = new ArrayList<CorrelationPair>();
		ArrayList<String> alreadyPrinted = new ArrayList<String>();
		for( CharacterLink oneCharacter : allCharacters ){
			//if( oneCharacter.getVotes() > numOfVotesForGirlToCount ){//to avoid girls with only a few votes skewing data
				for (Map.Entry<CharacterLink, Integer> entry : oneCharacter.mapEntrySet()) {
					if( !alreadyPrinted.contains(entry.getKey().getName()) ){
						System.out.println(oneCharacter.getName().replaceFirst(";",",")+"\t"+entry.getKey().getName().replaceFirst(";",",")+"\t"+oneCharacter.mapGet(entry.getKey()) );
					}
					//arrListForSortResults.add(new CorrelationPair( ((double)(Math.round(100000.0* (((entry.getValue()+0.0)/(oneCharacter.getVotes()+0.0)) - ((double)entry.getKey().getVotes())/TotalVotes) )))/1000.0, oneCharacter.getName(), entry.getKey().getName()));
				}
				alreadyPrinted.add(oneCharacter.getName());
			//}
		}
		/*
		Collections.sort(arrListForSortResults);
		for( CorrelationPair oneResult : arrListForSortResults ){
			if( Math.abs(oneResult.getDouble()) > DifferenceDisplayThreshold ){
				oneResult.printPair();
			}
		}*/
	}//end main
}