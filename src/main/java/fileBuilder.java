
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

/**
 * Created by Christian Gibbs on 9/30/2016.
 */
public class fileBuilder {

    private static String sep = File.separator;

    private static String filePath = "src" + sep + "main" + sep + "resources";

    private static void deleteOldFiles()
    {
        String fileName = filePath + sep + "images.txt";
        File fileToDelete = new File(fileName);
        if(fileToDelete.exists())
        {
            fileToDelete.delete();
        }

    }


    public static void addImage(ArrayList<String> imageList) {

        String fileName = filePath + sep + "images.txt";
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter textWriter = new BufferedWriter(writer);
            for (String src : imageList
                    ) {

                try {
                    textWriter.write(src);
                    textWriter.newLine();

                } catch (IOException e) {
                    System.out.println("unable to write to " + fileName);
                }//end nesetd trycatch

            }//end foreach
            textWriter.close();

        } catch (IOException e2) {
            System.out.println("File could not be opened: " + fileName);
        }//end outer try catch
    }


    public static void buildDictionary() {
        //read wordlist file
        String fileName = filePath + sep + "wordlist.txt";
        File file = new File(fileName);
        ArrayList<String> wordList = new ArrayList<String>();
        ArrayList<Integer> countList = new ArrayList<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String nextWord;
            while ((nextWord = reader.readLine()) != null) {
                wordList.add(nextWord);
            }//end while
            file.deleteOnExit();
            reader.close();
            System.out.println("Sorting alphabetically...");
            Collections.sort(wordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    saveDictionary(wordList);
    }

    private static void saveDictionary(ArrayList<String> wordList) {
        deleteOldFiles();
        String fileName = filePath + sep + "dictionary.txt";
        System.out.println("Counting word occurrences...");
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter textWriter = new BufferedWriter(writer);
            int currentIndex = 0;
            int nextIndex = currentIndex + 1;
            while (currentIndex < wordList.size()) {
                String word = wordList.get(currentIndex);
                String nextWord = wordList.get(nextIndex);
                int occurrences = 1;
                while (nextWord.equalsIgnoreCase(word) == true) {
                    occurrences = occurrences + 1;
                    currentIndex = currentIndex + 1;
                    nextIndex = currentIndex + 1;
                    if(nextIndex <wordList.size()) {
                        nextWord = wordList.get(nextIndex);
                    }
                    else{nextWord = "";}
                }//// end nested while
                String textLine = word+ "," + occurrences;
                try {
                    textWriter.write(textLine);
                    textWriter.newLine();

                } catch (IOException e) {
                    System.out.println("unable to write to " + fileName);
                }//end nesetd trycatch
                currentIndex = currentIndex + 1;
                nextIndex = currentIndex + 1;

            }//end outer while
            textWriter.close();
            System.out.println("List of words, with word count, is saved in dictionary.txt");

        } catch (IOException e2) {
            System.out.println("Dictionary File could not be opened: " + fileName);
        }//end outer try catch
    }



}