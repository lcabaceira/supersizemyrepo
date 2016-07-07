package org.alfresco.consulting.words;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random Word generator
 * <p>
 * <p>Generates a string of the required length from random real words
 *
 * @author AHunt
 */
public class RandomWords {
    private static Log logger = LogFactory.getLog(RandomWords.class);

    private static boolean initialized = false;
    private static int maxWordsInMemory = 300000;
    private static String wordFileName = "words.txt";
    private static ArrayList<String> wordList;

    public RandomWords() {
        init();
    }

    public static void init() {
        if (!initialized) {
            wordList = readWordsFromFile(maxWordsInMemory);
            initialized = true;
        }
    }

    private static String getWord() {
        int randomNumber = ThreadLocalRandom.current().nextInt(wordList.size());
        return wordList.get(randomNumber);
    }

    public static String getWords(int numWords) {

        if (numWords == 0) {
            return "";
        }

        String[] words = new String[numWords];

        for(int i = 0; i < numWords; i++) {
            words[i] = getWord();
        }

        return StringUtils.join(words, " ");
    }

    public void setMaxWordsInMemory(int maxWordsInMemory) {
        RandomWords.maxWordsInMemory = maxWordsInMemory;
    }

    public int getMaxWordsInMemory() {
        return maxWordsInMemory;
    }

    public void setWordFileName(String wordFileName) {
        RandomWords.wordFileName = wordFileName;
    }

    public String getWordFileName() {
        return wordFileName;
    }

    private static ArrayList<String> readWordsFromFile(int maxNumWords) {
        ArrayList<String> words = new ArrayList<>(maxNumWords);
        try {
            InputStreamReader isr = new InputStreamReader(RandomWords.class.getResourceAsStream(wordFileName));
            BufferedReader br = new BufferedReader(isr);
            String tmp;
            tmp = br.readLine(); // read first line of file.

            while ((tmp != null) && ((words.size() + 1) < maxNumWords)) {
                words.add(tmp);
                tmp = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            logger.error("Unable to read words file", e);
        }

        return words;
    }
}
