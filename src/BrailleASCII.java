import java.io.PrintWriter;

/**
 * A class that converts input (more than a character long if inputted as so) to
 * its respective braille character(s), unicode braille character(s), or ASCII
 * letter(s).
 * 
 * @author Wenfei Lin
 */
public class BrailleASCII {
  public static void main(String[] args) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);

    if (args.length != 2) { // Check length of arguments given
      errorPrinter.println(
          "Error: Incorrect number of parameters.\nThe command-line paramters must" +  
              "consist of two elements, the first being the target character set (braille," + 
              "ascii, or unicode) and the second being the source characters to be translated" + 
              "(as a string if there are spaces).");
      System.exit(2);
    } else {
      String targetCharSet = args[0];
      String sourceChars = args[1];
      char[] sourceCharsArr = sourceChars.toCharArray();

      switch (targetCharSet) {

        // ASCII to braille
        case "braille": 
          convertASCIIToBraille(pen, errorPrinter, sourceChars, sourceCharsArr);
          break;

        // braille to ASCII
        case "ascii": 
          convertBrailleToASCII(pen, errorPrinter, sourceChars);
          break;

        // ASCII to Unicode
        case "unicode": 
          convertASCIIToUnicode(pen, errorPrinter, sourceChars, sourceCharsArr);
          break;
        
        // Error with first input specifying target character set
        default: 
          errorPrinter.printf(
              "Error: %s is an incorrect target character set.\nThe target character set" + 
                  "should be either braille, ascii, or unicode.\n",
              targetCharSet);
          System.exit(3);
          break;
      } // switch
    } // if/else
  } // main(String[])

  // +----------------+------------------------------------------------
  // | Helper Methods |
  // +----------------+

  /**
   * Prints the converted braille text from the ASCII text.
   * 
   * @param pen for printing
   * @param errorPrinter for printing errors
   * @param sourceChars input characters to be converted
   * @param sourceCharsArr array of input characters to be converted
   */
  private static void convertASCIIToBraille(PrintWriter pen, PrintWriter errorPrinter, String sourceChars, char[] sourceCharsArr) {
    String finalOutput = "";

    for (char ch : sourceCharsArr) {
      // Check each character is either a space or an alphabetic letter
      checkLetterOrSpace(ch, errorPrinter, sourceChars);

      // Convert each ASCII character to braille bits and concatenate
      finalOutput += BrailleASCIITables.toBraille(ch);
    } // for

    pen.println(finalOutput);
  } // convertASCIIToBraille(PrintWriter, PrintWriter, String, char[])

  /**
   * Prints the converted ASCII text from the braille text.
   * 
   * @param pen for printing result
   * @param errorPrinter for printing error messages
   * @param sourceChars input characters to be converted
   */
  private static void convertBrailleToASCII(PrintWriter pen, PrintWriter errorPrinter, String sourceChars) {
    int numBitsInBrailleChar = 6;

    // Check each ASCII character in braille representation has 6 bits per ASCII character
    if (sourceChars.length() % numBitsInBrailleChar != 0) {
      errorPrinter.printf("Error: %s should only consist of %d bit representations" + 
          "per letter.\n", sourceChars, numBitsInBrailleChar);
      System.exit(7);
    } else {
      int i = 0;
      int j = numBitsInBrailleChar;
      String finalOutput = "";

      while (j <= sourceChars.length()) {
        // Convert each 6 bits of braille representation to lowercase ASCII character
        finalOutput += BrailleASCIITables.toASCII(sourceChars.substring(i, j)).toLowerCase();
        i += numBitsInBrailleChar;
        j += numBitsInBrailleChar;
      } // while
      pen.println(finalOutput);
    } // if/else
  } // convertBrailleToASCII(PrintWriter, PrintWriter, String)

  /**
   * Prints the converted Unicode text from the ASCII text.
   * 
   * @param pen for printing result
   * @param errorPrinter for printing error messages
   * @param sourceChars input characters to be converted
   * @param sourceCharsArr array of input characters to be converted
   */
  private static void convertASCIIToUnicode(PrintWriter pen, PrintWriter errorPrinter, String sourceChars, char[] sourceCharsArr) {
    String asciiToBraille = "";
    int numBitsInASCIIChar = 6;

    for (char ch : sourceCharsArr) {
      // Check each character is either a space or an alphabetic letter
      checkLetterOrSpace(ch, errorPrinter, sourceChars);

      // Convert each ASCII character to braille bit representation first
      asciiToBraille += BrailleASCIITables.toBraille(ch);
    } // for

    pen.println(convertBitsToUnicode(numBitsInASCIIChar, sourceChars, asciiToBraille));
  } // convertASCIIToUnicode(PrintWriter, PrintWriter, String, char[])

  /**
   * Convert string of bits to their respective Unicode characters in braille.
   * 
   * @param numBitsInASCIIChar bits string
   * @param sourceChars input characters to be converted
   * @param asciiToBraille braille string
   * @return Unicode version of bits string (braille)
   */
  private static String convertBitsToUnicode(int numBitsInASCIIChar, String sourceChars, String asciiToBraille) {
    int i = 0;
    int j = numBitsInASCIIChar;
    String finalUnicodeString = "";

    while (j <= sourceChars.length() * numBitsInASCIIChar) {
      // Convert each 6 bit braille representation to respective Unicode braille character
      String bitsToUnicodeCharNum = 
          BrailleASCIITables.toUnicode(asciiToBraille.substring(i, j));
      // Convert last 4 Unicode numbers to hexadecimal
      int unicodeCharHex = Integer.parseInt(bitsToUnicodeCharNum, 16);
      // Convert hexadecimal to Unicode braille character and concatenate results
      finalUnicodeString += Character.toString(unicodeCharHex);

      i += numBitsInASCIIChar;
      j += numBitsInASCIIChar;
    } // while

    return finalUnicodeString;
  } // convertBitsToUnicode(int, String, String)

  /**
   * Checks if a character is a letter or a space and prints error message
   * accordingly if the character is not either of those
   * 
   * @param ch a character
   * @param errorPrinter for printing error messages
   * @param sourceChars input characters to be converted
   */
  private static void checkLetterOrSpace(char ch, PrintWriter errorPrinter, String sourceChars) {
    // Check each character is either a space or an alphabetic letter
    if (!(ch == ' ' || Character.isLetter(ch))) { 
      errorPrinter.printf(
          "Error: %s must be composed of only alphabetic letters and/or spaces" + 
              "to convert to Braille.\n", sourceChars);
      System.exit(4);
    } // if
  } // checkLetterOrSpace(char, PrintWriter, String)
} // class BrailleASCII