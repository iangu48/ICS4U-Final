/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Encription;

public class Cipher {

    private static final int ENCRYPTIONREPETITION = 3;
    private static final int INTEGERKEY1 = 5243;
    private static final int INTEGERKEY2 = 7;
    private static final String BOOLEANKEY = "&|";
    private static final int NUMBOOLEANS = BOOLEANKEY.length() + 1;

    public static String encrypt(int num) {
        return "" + encrypt(num, ENCRYPTIONREPETITION);
    }

    private static int encrypt(int num, int times) {

        num += INTEGERKEY1;
        num *= INTEGERKEY2;

        if (times <= 1) {
            return num;
        } else {
            return encrypt(num, times - 1);
        }
    }

    public static String encrypt(boolean b) {
        return encrypt(b, ENCRYPTIONREPETITION);
    }

    private static String encrypt(boolean b, int times) {
        int maxSolutions = (int) Math.pow(2, NUMBOOLEANS);
        int numSolutions = 0;
        Boolean[][] possibleEncryptions = new Boolean[maxSolutions][NUMBOOLEANS];
        Boolean[][] solutions = new Boolean[maxSolutions][NUMBOOLEANS];

        int index = 0;
        for (int i = maxSolutions / 2; i >= 1; i /= 2) {
            for (int j = 0; j < maxSolutions; j++) {
                possibleEncryptions[j][index] = (j / i) % 2 == 1;
            }
            index++;
        }

        for (int i = 0; i < maxSolutions; i++) {
            boolean result = possibleEncryptions[i][0];
            for (int j = 0; j < BOOLEANKEY.length(); j++) {
                if (BOOLEANKEY.charAt(j) == '&') {
                    result = result && possibleEncryptions[i][j + 1];
                } else if (BOOLEANKEY.charAt(j) == '|') {
                    result = result || possibleEncryptions[i][j + 1];
                }
            }
            if (result == b) {
                solutions[numSolutions] = possibleEncryptions[i];
                numSolutions++;
            }
        }

        int solution = (int) (Math.random() * numSolutions);

        String encryptedBoolean = "";
        if (times == 1) {
            for (int i = 0; i < NUMBOOLEANS; i++) {
                if (solutions[solution][i]) {
                    encryptedBoolean += "T";
                } else {
                    encryptedBoolean += "F";
                }
            }
            return encryptedBoolean;
        } else {
            for (int i = 0; i < NUMBOOLEANS; i++) {
                encryptedBoolean += encrypt(solutions[solution][i], times - 1);
            }
            return encryptedBoolean;
        }
    }

    public static int decryptToInt(String s) {
        int encryptedNumber = Integer.parseInt(s);
        return decryptToInt(encryptedNumber, ENCRYPTIONREPETITION);
    }

    private static int decryptToInt(int num, int times) {
        num /= INTEGERKEY2;
        num -= INTEGERKEY1;

        if (times == 1) {
            return num;
        } else {
            return decryptToInt(num, times - 1);
        }
    }

    public static boolean decryptToBoolean(String s) {
        return decryptToBoolean(s, ENCRYPTIONREPETITION);
    }

    private static boolean decryptToBoolean(String s, int times) {
        if (times == 1) {
            return evaluate(s);
        } else {
            String newText = "";
            String[] segments = new String[NUMBOOLEANS];
            int segmentLength = (int) Math.pow(NUMBOOLEANS, times - 1);
            for (int i = 0; i < NUMBOOLEANS; i++) {
                segments[i] = s.substring(i * segmentLength, (i + 1) * segmentLength);
                if (decryptToBoolean(segments[i], times - 1)) {
                    newText += "T";
                } else {
                    newText += "F";
                }
            }
            return evaluate(newText);
        }

    }

    private static boolean evaluate(String s) {
        boolean result = s.charAt(0) == 'T';
        for (int i = 0; i < BOOLEANKEY.length(); i++) {
            if (BOOLEANKEY.charAt(i) == '&') {
                result = result && (s.charAt(i + 1) == 'T');
            } else if (BOOLEANKEY.charAt(i) == '|') {
                result = result || (s.charAt(i + 1) == 'T');
            }
        }
        return result;
    }
}
