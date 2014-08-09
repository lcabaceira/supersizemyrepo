package org.alfresco.consulting.words;

import org.alfresco.consulting.locator.PropertiesLocator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
/**
 * Random Word generator
 * 
 * <p>Generates a string of the required length   
 * from random real words
 * 
 * @author AHunt
 */
public class RandomWords
{
    private static Properties props = PropertiesLocator.getProperties("super-size-my-repo.properties");
    private static String files_deployment_location = props.getProperty("files_deployment_location");

	private static int maxWordsInMemory = 300000;
	private static String wordFileName = "words.txt";
	private static ArrayList<String> wordList;
	private static Random rnd = new Random();
	
	public RandomWords()
	{
		
		  wordList = readWordsFromFile(maxWordsInMemory);
	}
	public static void init()
	{
		  wordList = readWordsFromFile(maxWordsInMemory);
	}
	public static String getWord()
	{
		
		return wordList.get(rnd.nextInt(wordList.size()));
	}
	public static String getWords(int numWords)
	{

		if (numWords==0)
		{
			return "";
		}
		String returnString="";
		for (int i=1;i<= numWords;i++)
		{
			returnString+=getWord()+" ";
		}
		//Strip the trailing space
		// Because I'm too lazy to do this in a CS-approved way
		returnString = returnString.substring(0, returnString.length()-1);
		return returnString;
	}
	
	public void setMaxWordsInMemory(int maxWordsInMemory) 
	{
		this.maxWordsInMemory = maxWordsInMemory;
	}
	public int getMaxWordsInMemory() 
	{
		return maxWordsInMemory;
	}
	public void setWordFileName(String wordFileName)
	{
		this.wordFileName = wordFileName;
	}
	public String getWordFileName() 
	{
		return wordFileName;
	}
	
	
	private static ArrayList<String> readWordsFromFile(int maxNumWords)
	{
		int wordsRead=0;
		ArrayList<String> words = new ArrayList<String>();
		try
		{
			InputStreamReader isr = new InputStreamReader(RandomWords.class.getResourceAsStream(wordFileName));
			BufferedReader br = new BufferedReader(isr);
			String tmp;
			tmp = br.readLine(); // read first line of file.
			
			while((tmp != null) && (wordsRead <= maxNumWords))
			{ 
				words.add(tmp);
				tmp = br.readLine();
			}
			br.close();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return words;
	}
	 
}
