public class WordCount {

 private String word;
 private int count;

 // take in a word and setup its count at 1
 public WordCount(String w){
  word = w;
  count = 1;
 }

 // getters
 public String getWord() {
  return word;
 }

 public int getCount() {
  return count;
 }

 // common use for count should be to just increment
 public void increment() {
  count++;
 }

 // provided in case count may be useful to change manually
 public void setCount(int c) {
  count = c;
 }

 // print word and count
 public String toString() {
  return word + ":" + count;
 }
}
