///////////////////////////////////////////////////////////////////////////////
//                
// Title:            (MarkovModel.java)
// Semester:         (CS8B) Fall 2018
//
// Author:           (Matthew Roth)
// Email:            (mrroth@ucsd.edu)
// CS Login:         (cs8bfds)
// Lecturer's Name:  (Prof. Paul Cao; TA's - Grace Chen, Alberto, Cheng, Emily,
//                     Godwin, Henry, Hensen, Hilda, Lavanya, Nishil, Sneha)
// 
// Class Desc:       the MarkovModel class will use Hashmap, and ArrayLists to 
// generate a random predicted sequence of text based on frequency of occuring words
// in a content file, and use the WordCountContainer as a medium for 
// placing and receiving words. The Markov Model is a mathematical 
// algorithim based on probability which is used in decision making 
// processes.     
//                    
///////////////////////////////////////////////////////////////////////////////

import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.Scanner;
import java.util.*;
import java.lang.Math;
/**
 * the MarkovModel class will use Hashmap, and ArrayLists to generate 
 * a random predicted sequence of text based on frequency of occuring words
 * in a content file, and use the WordCountContainer as a medium for 
 * placing and receiving words. The Markov Model is a mathematical 
 * algorithim based on probability which is used in decision making 
 * processes.
 * */

public class MarkovModel {

  //The Hashmap that will hold the data for an ArrayList of String type and 
  //also a WordCountContainer for the value 
  protected HashMap<ArrayList<String>, WordCountContainer> predictionMap;
  //the degree is the n amount of words 
  protected int degree;
  protected Random random;
  //if the argument is a word or character for the model 
  protected boolean isWordModel;

  /**
   * the constructor for the MarkovModel object is 
   * to initialize the degree and isWordModel to 
   * create a MarkovModel object 
   * @param degree the degree of word
   * @param isWordModel if word or char
   **/
  public MarkovModel (int degree, boolean isWordModel) {
    //return if degree is less than 0 
    if ( degree < 0 )
      return;
    //intialize the Random object 
    this.random = new Random();
    this.degree = degree;
    this.isWordModel = isWordModel;
    //intialize the HashMap 
    predictionMap = new HashMap<ArrayList<String>, WordCountContainer>();

  }
  /**
   * private helper method takes a String as an argument 
   * and will fill the HashMap with words to be used 
   * for generating a random sequence of text later.
   * This method uses characters in constructing a 
   * text prediction 
   * @param content the words from the file
   * */
  private void trainCharacterModel(String content) {

    //add the words from the substring in the content file 
    //to an ArrayList then add the list to the Hashmap if its
    //not already there, and if its already in the Hash then
    //increment the WordCount 
    for( int i = degree; i < content.length(); i++){
      //temporary arraylist to help add new characrters to the hashmap
      ArrayList<String> prefix = new ArrayList<String>();   
      for( int j = degree;  j > 0; j--){
        prefix.add(content.substring(i-j,i-j+1));
      }
      //check if the hashmap doesnt contain the key and then if not
      //add it to the map with a new empty wordcountcontainer
      if(!predictionMap.containsKey(prefix)){
        predictionMap.put(prefix, new WordCountContainer());
      }
      //add the substring from the array into the hashmap as a value 
      predictionMap.get(prefix).add(content.substring(i,i+1).toLowerCase());

    }
  }

  /**
   * private helper method to assist in the word
   * processing with the train model using a string
   * to build the sequence of text 
   * @param content the string of text file 
   * @param filename the file to read in
   *
   * */
  private void trainWordModel(String text) {

    //create an array of strings using the split method to parse out
    //the whitespaces chars 
    String[] content = text.split(" ");
    //add each word to the arraylist 
    for(int i = 0; i<content.length-degree;i++){
      //create a new temporary arraylist to help populate the 
      //hashmap with wordcount objects 
      ArrayList<String> prefix = new ArrayList<String>();
      for(int j = 0; j<degree;j++){
        prefix.add(content[i+j].toLowerCase());
      }
      //if the list is not already in the hasmap then add it to 
      //the hash and create a new empty wordcountcontainer 
      if(!predictionMap.containsKey(prefix)){
        predictionMap.put(prefix, new WordCountContainer());
      }
      //add the string to the wordcountcontainer inside the hashmap
      predictionMap.get(prefix).add(content[i+degree]);
    }
  }

  /**
   * the trainFromText method will take a String as an argument 
   * that represents the .txt file, and will obtain the words from 
   * the txt file and condense them all into one string to be
   * used for training the predictor of MarkovModel
   * @param filename the txt file
   *
   * */
  public void trainFromText(String filename) {

    // `content` contains everything from the file, in one single string
    String content;
    try {
      content = new String(Files.readAllBytes(Paths.get(filename)));
      Scanner input = new Scanner(new File(filename));
      //if its a word model wrap the word properly for degree words
      if(isWordModel){
        //int counter to stop wrapping words after n degree
        int counter = 0;
        while(input.hasNext()) {
          //once the counter is equal to degree break the loop
          if(counter == degree)
            break;
          String wrapWord = input.next();
          content += " " + wrapWord;
          counter++;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    //if its not a word model it will be a char model
    if(!isWordModel){
      String wrapChar = content.substring(0,degree);
      content += wrapChar;
    }
    //call the helper methods for either wordmodel or charmodel
    if(isWordModel)
      trainWordModel(content);
    else
      trainCharacterModel(content);

  }
  /**
   * the getFlattenedList method will take an ArrayList of strings
   * as an argument and return an arraylist of strings that will 
   * have all the possible prediction words
   * @param prefix the arraylist
   * @return newList the new prediction list
   *
   * */
  public ArrayList<String> getFlattenedList(ArrayList<String> prefix){
    //create an arrylist of strings to hold all the new prediction words
    ArrayList<String> tempList = new ArrayList<String>(); 
    //add each prediction word from the wordcountcontainer in the hashmap 
    //to the new arraylist 
    int size = predictionMap.get(prefix).getList().size();
    for(int i = 0; i<size;i++){
      for(int j = 0;j < predictionMap.get(prefix).getList().get(i).getCount();j++){

        tempList.add(predictionMap.get(prefix).getList().get(i).getWord().toLowerCase());

      }
    }
    return tempList;
  }

  /**
   * return a string that is generated randomly using the
   * getFlattenedList method 
   * @param prefix the arraylist of prefixes
   * @return the prediction word
   * */
  public String generateNext(ArrayList<String> prefix) {
    //create a temporary Arraylist of strings to point 
    //at the flattened list for accessing individual elements
    ArrayList<String> flattenedList = new ArrayList<String>();
    //point the temp list at the returned arraylist from the 
    //getflattnedlist method 
    flattenedList = getFlattenedList(prefix);
    //return a random string in the arraylist 
    return flattenedList.get(Math.abs(random.nextInt(flattenedList.size()))).toLowerCase();

  }

  /**
   * the generate method will create a sequence of 
   * words output onto the console based on predicitons
   * from prefix words in the hashmap 
   * @param count the number of words to generate
   * @return string the words combined into on string
   *
   * */
  public String generate(int count) {

    //create an Arraylist of string type arraylists that hold all the
    //prefix words in the hashmap
    ArrayList<ArrayList<String>> keys = 

      new ArrayList<ArrayList<String>>(predictionMap.keySet());
    //create a temp arraylist of strings to hold words from the hashmap
    //and append to a new string 
    ArrayList<String> tempList = new ArrayList<String>();
    //create a stringbuilder to append words to and create a new string 
    StringBuilder generatedText = new StringBuilder();
    //create the new string word by getting the key and value from the 
    //hashmap and calling the generateNext method to return a random 
    //string value of a prediciton word 
    for(int j = 0; j < count; j++){
      //create a new random int value to seed the random generator 
      int randomInt = Math.abs(random.nextInt(keys.size()));
      String addWord = generateNext(keys.get(randomInt));
      //if its a wordmodel then add each word with a whitespace bewteen 
      if(isWordModel){

        generatedText.append(addWord + " ");
        //if its a Character model proceed by appending each char as is
      }else
        generatedText.append(addWord);

    }
    //create a string object with the final apended stringbuilder to return 
    String finalOutputString = new String(generatedText);

    return finalOutputString;
  }

  /**
   * the toString method will print out a 
   * String representation of the key from the hashmap 
   * which are the prefix words, and also the corresponding values
   * which will be the prediction words
   * @return the final string representation
   *
   **/
  @Override 
  public String toString(){

    //create a string builder object to append new words into from 
    //the hashmap
    StringBuilder prefixAndPredictionWords = new StringBuilder();

    //append each element from the key and corresponding value 
    //into the string builder
    for (ArrayList<String> key: predictionMap.keySet()){
      prefixAndPredictionWords.append(key+ ": "+ predictionMap.get(key) + "\n");
    }

    //create a String object out of the String builder to be returned
    String toString = new String(prefixAndPredictionWords);

    return toString;
  } 
}
