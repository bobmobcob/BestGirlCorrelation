# BestGirlCorrelation

Created Feb 07 2016

## Status:
The basic correlation script is done. It calculates the percentage of people that voted for Character A that also voted for Character B. It then compares that percentage to the percentage of the total vote Character B received.

## Files:
CorrelationPair.java - class to hold the final data. implements comparable so it can be sorted.
BestGirlCorrelation.java - main program
results.txt - the survey results in a csv file (commas in the names were changed to semicolons)

## Running it yourself:
Download the .java files and results.txt. Ensure you have JDK installed on on the system path. In command prompt (PC) or terminal (linux/unix), cd to the folder with the downloaded files. Type 'javac BestGirlCorrelation.java' then 'java BestGirlCorrelation'.
Look online for a tutorial if you need help running the java.

You can customize how much data is displayed by changing the public variables at the top of BestGirlCorrelation.java. 
	numOfVotesForGirlToCount - How many votes a girl must have for her results to be displayed
	DifferenceDisplayThreshold - How big a percentage point difference above or below the expected percentage is required for it to be displayed

## Improvements:
There was a lot more data in the survey (like the source material questions) that I did not use. I would like to work on this more and include this information. In the meantime, feel free to modify what I've done if you're want to do your own stats.