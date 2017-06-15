/*
	File Name:	 Cipher.java
	Name:			 Shawn Wang
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Encrypts / decrypts integers and booleans recursively.
*/
package Game.Encryption;

public class Cipher {

   // number of repetitions
   private static final int ENCRYPTIONREPETITION = 3;
   
   // keys for encrypting integers
   private static final int INTEGERKEY1 = 5243;
   private static final int INTEGERKEY2 = 7;
   
   // key for encrypting booleans
   private static final String BOOLEANKEY = "&|";
   
   // number of booleans generated per boolean per round
   private static final int NUMBOOLEANS = BOOLEANKEY.length() + 1;

   // encrypts a number and returns its encrypted form as a string
   // calls encrypt method for integers
   public static String encrypt(int num) {
      return "" + encrypt(num, ENCRYPTIONREPETITION);
   }
     
   // encrypts a number by repeting a mathematical operation that will always reversible
   // calls itself  
   private static int encrypt(int num, int times) 
   {
      // mathematical operations
      // + and * can always be reversed
      num += INTEGERKEY1;
      num *= INTEGERKEY2;
   
      // check the remaining number of repetitions and continue if necessary
      if (times <= 1) {
         return num;
      } 
      else {
         return encrypt(num, times - 1);
      }
   }

   // encrypts a boolean and returns its encrypted form as a string
   // calls encrypt method for booleans
   public static String encrypt(boolean b) {
      return encrypt(b, ENCRYPTIONREPETITION);
   }

   // encrypts a boolean by finding a random set of booleans that will evaluate to the original boolean using the boolean key
   // calls itself  
   private static String encrypt(boolean b, int times) {
      int maxSolutions = (int) Math.pow(2, NUMBOOLEANS);  // maximum number of solutions 
      int numSolutions = 0;                               // current number of solutions
      Boolean[][] possibleEncryptions = new Boolean[maxSolutions][NUMBOOLEANS];   // stores all combinations
      Boolean[][] solutions = new Boolean[maxSolutions][NUMBOOLEANS];             // stores possible combinations 
   
      // generate all possible combinations
      int index = 0;
      for (int i = maxSolutions / 2; i >= 1; i /= 2) {
         for (int j = 0; j < maxSolutions; j++) {
            possibleEncryptions[j][index] = (j / i) % 2 == 1;
         }
         index++;
      }
      
      // evaluate each combination
      for (int i = 0; i < maxSolutions; i++) {
         boolean result = possibleEncryptions[i][0];
         for (int j = 0; j < BOOLEANKEY.length(); j++) {
            if (BOOLEANKEY.charAt(j) == '&') {
               result = result && possibleEncryptions[i][j + 1];
            } 
            else if (BOOLEANKEY.charAt(j) == '|') {
               result = result || possibleEncryptions[i][j + 1];
            }
         }
         // if the result is correct, record combination and increase counter
         if (result == b) {
            solutions[numSolutions] = possibleEncryptions[i];
            numSolutions++;
         }
      }
       
      // pick a solution randomly
      int solution = (int) (Math.random() * numSolutions);
      
      // check the remaining number of repetitions and continue if necessary
      String encryptedBoolean = "";
      if (times == 1) {
         // converts booleans into string and return it
         for (int i = 0; i < NUMBOOLEANS; i++) {
            if (solutions[solution][i]) {
               encryptedBoolean += "T";
            } 
            else {
               encryptedBoolean += "F";
            }
         }
         return encryptedBoolean;
      } 
      else {
         // encrypt the resulting booleans and returns it
         for (int i = 0; i < NUMBOOLEANS; i++) {
            encryptedBoolean += encrypt(solutions[solution][i], times - 1);
         }
         return encryptedBoolean;
      }
   }

   // decrypts a string into integer
   // calls decryptToInt
   public static int decryptToInt(String s) {
      int encryptedNumber = Integer.parseInt(s);
      return decryptToInt(encryptedNumber, ENCRYPTIONREPETITION);
   }
     
   // reverses the operation performed to find the original number
   private static int decryptToInt(int num, int times) {
   
      // reverse mathematical operation
      num /= INTEGERKEY2;
      num -= INTEGERKEY1;
       
      // repeat if necessary
      if (times == 1) {
         return num;
      } 
      else {
         return decryptToInt(num, times - 1);
      }
   }

   // decrypts a string into boolean
   // calls decryptToBoolean
   public static boolean decryptToBoolean(String s) {
      return decryptToBoolean(s, ENCRYPTIONREPETITION);
   }

    // evaluates the string of booleans according to the key
    // calls itseld
   private static boolean decryptToBoolean(String s, int times) {
      // check remaining repetitions
      if (times == 1) {
         // if last repetition, evaluate string
         return evaluate(s);
      } 
      else {
         // else, split string into appropriate segments and evaluate each segment
         String newText = "";
         String[] segments = new String[NUMBOOLEANS];
         int segmentLength = (int) Math.pow(NUMBOOLEANS, times - 1);
         
         // split string into segments
         for (int i = 0; i < NUMBOOLEANS; i++) {
            segments[i] = s.substring(i * segmentLength, (i + 1) * segmentLength);
            // evaluate each segment
            if (decryptToBoolean(segments[i], times - 1)) {
               newText += "T";
            } 
            else {
               newText += "F";
            }
         }
         // return evaluation result
         return evaluate(newText);
      }
   
   }
    // evaluates the boolean statement according to the boolean key
   private static boolean evaluate(String s) {
   
     // finds the first boolean
      boolean result = s.charAt(0) == 'T';
      
      // for each letter in the boolean key
      for (int i = 0; i < BOOLEANKEY.length(); i++) {
         if (BOOLEANKEY.charAt(i) == '&')       // if letter represent and 
         {
            // evaluate result
            result = result && (s.charAt(i + 1) == 'T');
         } 
         else if (BOOLEANKEY.charAt(i) == '|')  // if letter represent or
         {
            // evaluate result
            result = result || (s.charAt(i + 1) == 'T');
         }
      }
      // return evaluation result
      return result;
   }
}
