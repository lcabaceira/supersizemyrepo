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

    public static void useFewerWords() {
        maxWordsInMemory = 1000;
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
        final LineReader lineReader = new LineReader(maxNumWords, RandomWords.wordFileName);
        lineReader.readLinesFromFile();
        return lineReader.getLines();
    }

    private static class LineReader {
        private final int maxLines;
        private final String fileName;
        private BufferedReader lineReader = null;
        private ArrayList<String> lines;

        LineReader(int maxLines, String fileName) {
            this.maxLines = maxLines;
            this.fileName = fileName;
            lines = new ArrayList<>(maxLines);
        }

        private void closeReader() {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    logger.error("Unable to close file: " + fileName, e);
                }
            }
        }

        ArrayList<String> getLines() {
            return lines;
        }

        void readLinesFromFile() {
            try {
                readFile();
            } catch (IOException e) {
                logger.error("Error reading file: " + fileName, e);
            }
            finally {
                closeReader();
            }
        }

        private void readFile() throws IOException {
            openFileForReading();

            readLines();
        }

        private void readLines() throws IOException {
            String currentLine = lineReader.readLine(); // read first line of file.
            while ((currentLine != null) && (lines.size() <= maxLines)) {
                lines.add(currentLine);
                currentLine = lineReader.readLine();
            }
        }

        private void openFileForReading() {
            InputStreamReader inputStreamReader = new InputStreamReader(RandomWords.class.getResourceAsStream(fileName));
            lineReader = new BufferedReader(inputStreamReader);
        }
    }
}
