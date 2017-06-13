/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.GUI;

import java.util.Arrays;

public class Messages {

   private static final int LINELENGTH = 35;
   private static int MAXTEXTS = 10;
   private String[][] texts = new String[MAXTEXTS][];

   public Messages() {
   
   }
   
   public void addText(String[] newText) {
      for (int i = MAXTEXTS - 1; i > 0; i--) {
         texts[i] = texts[i - 1];
      }
      texts[0] = newText;
   }
   
   public void addText(String newText)
   {
      String[] text;
      if (newText.length() <= LINELENGTH)
      {
         text = new String[1];
         text[0] = newText;
      }
      else 
      {
         String[] words = newText.split(" ");
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
      }
      for (int i = MAXTEXTS - 1; i > 0; i--) {
         texts[i] = texts[i - 1];
      }
      texts[0] = text;
   }

   public String[] getText(int i) {
      if (i >= 0 && i < MAXTEXTS) {
         return texts[i];
      }
      return null;
   }

   public void clearTexts() {
      texts = new String[MAXTEXTS][];
   }
}
