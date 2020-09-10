#Program Description
The MarkovModel program is based on the concept by the algorithm invented by 
the Russian mathematician Andrey Markov. The algorithm is used for decision 
making processes and is regarding for its "memorylessness decision making. 
The program will use the Markov decision making process to generate a sequence
of words based on words taken from a text file. The words in the text file will
be used as prefix words for prediction words that come right after the prefix. 
The program will generate a specific amount of prediction words based on the 
given degree of words input to the program. The words generated can be done as
a sequence if words or a sequence of individual characters, based on what the
user perefers. 

#Short Response 
1. What is a Hashmap and how is it useful? A hashmap is a data structure used
to store mapping relationships. it uses a key, which is any object and a 
corresponding value, which also must be an object. The map dosnt maintain 
a certain order, so the data can be accessed easily and efficiently using 
the key and value pair. The hashmap is useful in situations where you need
to have an object related to another object, where the data corresponds to
something related to eachother.
2. How is a hashmap different than an ArrayList? The main difference between a
hashmap and an arraylist is that an arraylist is index based data structure and
a hashmap works on hashing the to retreive the stored values. Hashmap also will
store two objects at a time that map to eachother, whereas an arraylist will 
only store one value type.
3. name one limitation of the MarkovModel. The markov model is taking into 
account only the current state and maintains no memory of the previous states
so in a graph of all possible states of probability you can become basically
trapped without any possibility to encode anything in between. 
4. The reason text produced by the MarkovModel program doesnt make sense 
sometimes is due to the fact that the model is using probability to determine
the next prediction word. Its not a perfect model for determining the most 
ideal word to use in a sequence of text, and it doesnt account for grammar
or logical semantic representation of a string of text.
5. Yes, you want a higher degree of word model because in a lower degree setting
for example, 0, the word generated output will be entirely random and 
mostly inaccurate, but in a higher degree setting the output will become more
accuate in its predictions.


# Markov_Model_Java
