/*
	File Name:	 Messages.java
	Name:			 Shawn Wang
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Main container for text messages in the game. 
                Stores up to 10 texts.
                Can store both single text messages and messages containing multiple strings.
                Automatic conversion of a long string into short ones.
*/
package Game.GUI;

// imports needed classes
import java.util.Arrays;

public class Messages {
   
   // allowed length of a single line of text
   private static final int LINELENGTH = 35;
   
   // max number of texts;
   private static final int MAXTEXTS = 10;
   
   // texts stored
   private String[][] texts = new String[MAXTEXTS][];
   
   // adds a text
   // Takes in a string array
   public void addText(String[] newText) {
     // shift texts one down
      for (int i = MAXTEXTS - 1; i > 0; i--) {
         texts[i] = texts[i - 1];
      }
      // insert first text
      texts[0] = newText;
   }
   
   // adds a text
   // takes in a string
   public void addText(String newText)
   {
      // convert new text
      String[] text = conversion(newText);
      // shift texts one down
      for (int i = MAXTEXTS - 1; i > 0; i--) {
         texts[i] = texts[i - 1];
      }
      // insert first text
      texts[0] = text;
   }

   // returns a text given its index number
   public String[] getText(int i) {
      if (i >= 0 && i < MAXTEXTS) {
         return texts[i];
      }
      return null;
   }

   // clears texts stored
   public void clearTexts() {
      texts = new String[MAXTEXTS][];
   }
   
   // converts a long string into a series of shorter ones
   private String[] conversion (String s)
   {
      String[] text;
      if (s.length() <= LINELENGTH)
      {
         text = new String[1];
         text[0] = s;
         return text;
      }
      else 
      {
         String[] words = s.split(" ");
         text = new String[1];
         text[0] = "";
         int index = 0;
         for (int i = 0; i < words.length; i++)
         {
            if (words[i].length() + text[index].length() > LINELENGTH)
            {
               index++;
               text = Arrays.copyOf(text, index + 1);
               text[index] = "";
            }
            text[index] += words[i] + " ";
         }
         return text;
      }
   }
}
