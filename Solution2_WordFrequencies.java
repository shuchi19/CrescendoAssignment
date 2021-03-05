package com.shuchi.globalsign;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


class WordData {
      // A little class to hold the data for one word.
   String word;   // The word (in lower case letters).
   int count;     // The number of times the word has been found.
   WordData(String w) {
        // Constructor creates an object with the specified word
        // and with the counter initialized to 1.
      word = w;
      count = 1;
   }
}

public class Solution2_WordFrequencies {

   static WordData[] words;  // An array to hold the words from the file.
                             //   Note that the array will be expanded as 
                             //   necessary, in the insertWord() subroutine.
   
   static int wordCount;   // The number of words currently stored in
                           //   the array.


   public static void main(String[] args) {
   
      TextReader in;    // A stream for reading from the input file.
      PrintWriter out;  // A stream for writing to the output file.
      
      String inputFileName;   // Input file name, specified by the user.
      String outputFileName;  // Output file name, specified by the user.
      
      words = new WordData[10];  // Start with space for 10 words.
      wordCount = 0;             // Currently, there are no words in the array.
      
      /* Get the input file name from the user and try to create the
         input stream.  If there is a FileNotFoundException, print
         a message and terminate the program. */
      
//     TextIO.put("Input file name?  ");
      inputFileName = "C:\\Users\\Shuchi.Sinha\\Desktop\\My Assignment\\mobydick.txt";
      try {
         in = new TextReader(new FileReader(inputFileName));
      }
      catch (FileNotFoundException e) {
          TextIO.putln("Can't find file \"" + inputFileName + "\".");
          return;
      }
      
      /* Get the output file name from the user and try to create the
         output stream.  If there is an IOException, print a message
         and terminate the program. */

     // TextIO.put("Output file name? ");
      outputFileName ="C:\\Users\\Shuchi.Sinha\\Desktop\\My Assignment\\result.txt";
      try {
         out = new PrintWriter(new FileWriter(outputFileName));
      }
      catch (IOException e) {
          TextIO.putln("Can't open file \"" + outputFileName + "\" for output.");
          TextIO.putln(e.toString());
          return;
      }
      
      /* Read all the words from the input stream and insert them into
         the array of words.  Reading from a TextReader can result in
         an error of type TextReader.Error.  If one occurs, print an
         error message and terminate the program. */
      
      try {
         while (true) {
               // Skip past and non-letters in the input stream.  If an
               //   end-of-stream has been reached, end the loop.  Otherwise,
               //   read a word and insert it into the array of words.
            while ( ! in.eof() && ! Character.isLetter(in.peek()) )
               in.getAnyChar();
            if (in.eof())
               break;
            insertWord(in.getAlpha());
         }
      }
      catch (TextReader.Error e) {
         TextIO.putln("An error occurred while reading from the input file.");
         TextIO.putln(e.toString());
         out.close();
         return;
      }
      
      /* Output the words in alphabetical order. */
      
//      out.println("Words in alphabetical order, with the number of occurrences:");
//      out.println("-----------------------------------------------------------");
//      out.println();
      
    //  putWordList(out);
      
      /* Sort the list of words according the frequency with which
         they were found in the file, and then output the list again. */
      
      sortByFrequency();
      putWordList(out);
      
      if (out.checkError()) {
         TextIO.putln("Some error occurred while writing to the output file.");
         TextIO.putln("The output might be missing, incomplete, or corrupted.");
      }
      else {
         TextIO.putln("Done.");
      }
   out.close();
   } 
   

   static void insertWord(String w) {
           // Insert the word w into the array of words, unless it already
           // appears there.  If the word already appears in the list,
           // add 1 to the counter for that word.  The words in the array are in
           // lower case,  and w is converted to lower case before it is processed.
           // Note that the words in the array are kept in alphabetical order.
           // If the array has no more space to hold w, then it is doubled
           // in size.

      int pos = 0;  // This will be the position in the array where w belongs.

      w = w.toLowerCase();
      
      /* Find the position in the array where w belongs, after all the
         words that precede w alphabetically.  If a copy of w already
         occupies that position, then it is not necessary to insert
         w, so just add 1 to the counter associated with the word
         and return. */

      while (pos < wordCount && words[pos].word.compareTo(w) < 0)
         pos++;
      if (pos < wordCount && words[pos].word.equals(w)) {
         words[pos].count++;
         return;
      }
         
      /* If the array is full, make a new array that is twice as 
          big, copy all the words from the old array to the new,
          and set the variable, words, to refer to the new array. */

      if (wordCount == words.length) {
         WordData[] newWords = new WordData[words.length*2];
         System.arraycopy(words,0,newWords,0,wordCount);
         words = newWords;
      }
      
      /* Put w into its correct position in the array.  Move any
         words that come after w up one space in the array to
         make room for w.  Create a new WordData object to hold
         the new word.  */

      for (int i = wordCount; i > pos; i--)
         words[i] = words[i-1];
      words[pos] = new WordData(w);
      wordCount++;

   }  // end insertWord()
   
   
   static void putWordList(PrintWriter output) {
          // Write the list of words from the words array, along with their
          // associated frequencies, to the output stream specified in the
          // parameter to this subroutine.  Maintaining spacing if the count is large.
       for (int i = 0; i < 20; i++) {
          output.print("   ");
          output.print(words[i].count); 
         
          for (int space = 0; space < 10; space++)
             output.print(" ");
        
          output.println(words[i].word);
         
       }
   }  // end putWordList()
   
   
   static void sortByFrequency() {
          // Use insertion sort to sort the words in the list so that they
          // are in order of decreasing frequency.  (Note that insertion sort
          // has the following neat property:  In a group of words that all
          // have the same frequency, the words will remain in alphabetical
          // order after the words have been sorted by frequency.)
       for (int i = 1; i < wordCount; i++) {
          WordData temp = words[i];  // Save the word in position i.
          int location;              // An index in the array.
          location = i - 1;
          while (location >= 0 && words[location].count < temp.count) {
                // Bump words that have frequencies less than the frequency
                // of temp up one space in the array.
             words[location + 1] = words[location]; 
             location--;
          }
          words[location + 1] = temp;  // Put temp in last vacated space.
       
       }
   }  // end sortByFrequency()

   
}  