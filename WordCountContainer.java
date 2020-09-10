import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////////
//                
// Title:            (WordCountContainer.java)
// Files:            (list of source files)
// Semester:         (CS8B) Fall 2018
//
// Author:           (Matthew Roth)
// Email:            (mrroth@ucsd.edu)
// CS Login:         (cs8bfds)
// Lecturer's Name:  (Prof. Paul Cao; TA's - Grace Chen, Alberto, Cheng, Emily,
//                     Godwin, Henry, Hensen, Hilda, Lavanya, Nishil, Sneha)
// 
// Class Desc:        The WordCountContainer class will create an ArrayList
//                    of WordCount objects, and will be the interface class
//                    to operate methods on the objects within the arraylist.
//                    The program will be a container to hold list of object 
//                    WordCount, and perform various operations on the object
//                    and the list through its methods.
//
///////////////////////////////////////////////////////////////////////////////

public class WordCountContainer {

  /**
   * PA7 update w/ new method 
   * the add() method is implemented for the 
   * new Class Markov, taking a String as an
   * argument and adding it to the arraylist, and
   * if the list already contains the word then it
   * will increment its count
   * @param word the string word 
   * */
  public void add(String word) {
    if( word.equals("") || word.equals(null) )
      return;

    list.add(new WordCount(word));
    for( int i = 0; i < list.size()-1;i++ ){
      //ignore the case when checking for words that match
      if(list.get(i).getWord().equalsIgnoreCase(word)){
        //call the increment method of the indexed object
        //to increase the count of the word count
        list.get(i).increment();
        //remove the object from the list 
        list.remove(list.size()-1);
      }
    }
  }

  //ArrayList containing the list of WordCount objects 
  ArrayList<WordCount> list;

  /**
   * the constructor to create a new Arraylist of
   * WordCount objects 
   *
   * */
  public WordCountContainer() {
    list = new ArrayList<WordCount>();
  }

  /**
   * the getWordsFromFile method will take a txt file as an
   * argument input, and search the text file for words 
   * and then see if any of the words match with any of the 
   * objects words in the arraylist, and if they do match
   * then increment the objects word count, and delete the duplicate object 
   * @param filename the file to search for words that match 
   * */
  public void getWordsFromFile( String filename ) throws IOException {

    //Scanner object to scan through a txt file 
    Scanner input = new Scanner (new File(filename));

    //loop through the arraylist of wordcount objects and 
    //check each object for word from the text file
    //and if the words match then call the increment method
    //in the matching object, and remove the duplicate object 
    //containing the same word
    while (input.hasNext()){
      //searching through the txt file line by line, skip whitespace
      String word = input.next();
      list.add(new WordCount(word));
      for (int i = 0; i < list.size()-1;i++){
        //ignore the case when checking for words that match
        if(list.get(i).getWord().equalsIgnoreCase(word)){
          //call the increment method of the indexed object
          //to increase the count of the word count
          list.get(i).increment();
          //remove the object from the list 
          list.remove(list.size()-1);
        }
      }
    }    
  } 

  /**
   * removeCommon method will take a txt file as input argument
   * and check the WordContainer arraylist for matching words from 
   * the txt, and remove the words from the object that macth the txt file 
   * @param omitFilename the file containing words to remove from objects 
   *
   * */
  public void removeCommon( String omitFilename ) throws IOException {

    //Scanner object to scan through a txt file 
    Scanner input = new Scanner (new File(omitFilename));
    //loop through the arraylist of wordcount objects and 
    //check each object for the removal word from the text file
    //and if the words match then remove the object from the ArrayList
    while (input.hasNext()){
      String word = input.next();
      for (int i = 0; i < list.size()-1;i++){
        //ignore the case of the word 
        if(list.get(i).getWord().equalsIgnoreCase(word)){
          list.remove(i);         
        }       
      }
      //if the firt elements object contains the word to remove after
      //the arraylist has been iterated through to check for
      //removal words, then  clear the Arraylist.
      if(list.get(0).getWord().equalsIgnoreCase(word)) 
        list.clear();
    }
  }

  /**
   * the getIndexOfArrayList method will take an arraylist as an argument
   * and return the index of the element containing the largest count. 
   * this index will corespond directly to the <WordCount> ArrayList's 
   * object index, and we can use this index to copy the ArrayList object 
   * into a new ArrayList for manipulation, and return value 
   *
   * * */
  private int getIndexOfArrayList(){

    //check for null value 
    if( list == null || list.size() == 0) return -1;
    int largest = 0;
    //check for the largest count size in the list 
    for(int i =1; i< list.size();i++)
    {
      if (list.get(i).getCount() > list.get(largest).getCount()) largest = i;
    }
    //return an int value that coresponds to the index number on the list 
    return largest;

  }

  /**
   * The topNWord method will return an ArrayList and take 
   * an int as an argument. the N value is the amount of 
   * words to search for with the highest word count
   * @param n the top occuring words  
   *
   * */
  public ArrayList<WordCount> topNWords(int n) {
    int x = n;
    //check if the list is empty
    if (list.isEmpty())
      return list;
    //if the list is smaller than the n value then return the whole list
    if (x > list.size())
      return list;
    //if the argument sent as n is a negative number return empty 
    if(x < 0){
      list.clear();
      return list;
    }

    //create a new ArrayList to hold the top n words 
    ArrayList<WordCount> newList = new ArrayList<WordCount>();

    //loop through n total times and return the top n objects 
    for(int i = 0;i<x; i++){ 

      //use the helper method to obtain the array index of the 
      //array containing the largest word count 
      int index = getIndexOfArrayList();          
      newList.add(list.get(index));
      //set the wordcount to negative so that the next loop doesnt
      //pick up the same object as the highest wordcount 
      list.get(index).setCount(-(list.get(index).getCount()));
    }

    //set all the negative word count values back to positive 
    for(int l =0;l<list.size()-1;l++){
      for(int k =0; k < list.size() -1; k++){
        if(list.get(k).getCount() < 0)
          list.get(k).setCount(-(list.get(k).getCount()));
      }
    }
    return newList;
  }

  /**
   * The toString method will return a String
   * representation of the class 
   **/
  public String toString() {
    if(list.isEmpty() || list.get(0).getWord().equals(""))
      return "The list is empty. There are no words.";

    //create a StringBuilder object and append the whitespace in 
    //between the incoming object word and count 
    StringBuilder result = new StringBuilder();
    result.append(" "); 
    for (int i=0;i<list.size();i++){
      result.append(list.get(i).getWord() + "(" + list.get(i).getCount() 
          + ")" + " ");
    } 
    String str = new String(result);

    return str.trim();
  }

  /**
   *
   * the outputWords method will print the toString 
   * of the list of objects in the ArrayList to a 
   * text file 
   * @param printToFile boolean to print or not to print 
   *
   * */
  public void outputWords(boolean printToFile) throws IOException{

    //if true then print to file
    if(printToFile){
      //create a file object to output a file 
      File file = new File("myOutput.txt");
      //create a PrintWriter object and write the words in file into 
      //the new text file 
      PrintWriter output = new PrintWriter(file);
      output.println(list.toString());
      output.close();
    }
    else{ 
      System.out.println(list.toString());
    }

    System.out.println();
  }

  public ArrayList<WordCount> getList(){
    return list;
  }
}
