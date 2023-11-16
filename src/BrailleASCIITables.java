import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * A class that converts a value to its respectively mapped value in one of the systems: braille, ASCII, unicode.
 */
public class BrailleASCIITables {
  
  /**
   * Converts an ASCII character to a string of bits representing the corresponding braille character.
   * @param letter an ASCII character 
   * @return a string of the bits corresponding to the braille character of the ASCII character, letter
   */
  public static String toBraille(char letter) { // ASCII letter to braille
    String fileName = "ASCIIToBraille.txt"; // for Linux
    String letterToASCIIBits = "";
    int asciiToBrailleTreeBits = 8;
    // convert ASCII letter to 8-bit binary
    int charNum = (int) letter;
    
    for (int i = 7; i >= 0; i--) {
      if ((int)(Math.pow(2, i)) <= charNum) {
        letterToASCIIBits += "1";
        charNum -= (int)(Math.pow(2, i));
      } else {
        letterToASCIIBits += "0";
      }
    }

    return retrieveMappedValue(fileName, letterToASCIIBits, asciiToBrailleTreeBits);
  } // toBraille(char)

  /**
   * Converts a string of bits representing a braille character to its respective ASCII character
   * @param bits string of bits corresponding to a braille character
   * @return a string that contains the exact ASCII letter the braille represents
   */
  public static String toASCII(String bits) { // braille to ASCII
    String fileName = "BrailleToASCII.txt"; // for Linux
    int brailleToASCIITreeBits = 6;

    return retrieveMappedValue(fileName, bits, brailleToASCIITreeBits);
  } // toASCII(String)

  /**
   * Converts a string of bits representing a braille character to its corresponding Unicode braille character
   * @param bits string of bits representing a braille character
   * @return a string containing the Unicode braille character for bits
   */
  public static String toUnicode(String bits) { // braille to Unicode
    String fileName = "BrailleToUnicode.txt"; // for Linux
    int brailleToUnicodeTreeBits = 6;

    return retrieveMappedValue(fileName, bits, brailleToUnicodeTreeBits);
  } // toUnicode(String)

  // HELPER METHODS

  /**
   * 
   * @param filename
   * @param input
   * @param requiredNumOfBits
   * @return
   */
  static String retrieveMappedValue(String filename, String input, int requiredNumOfBits) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    File file = new File(filename);
    InputStream inputStream;
    BitTree tree = new BitTree(requiredNumOfBits);
    String convertedValue = "";

    try {
      inputStream = new FileInputStream(file);
      tree.load(inputStream);
      convertedValue = tree.get(input);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s cannot be found.", filename);
      System.exit(5);
    } catch (Exception e) {
      errorPrinter.printf("%s", e.getMessage());
      System.exit(6);
    }

    return convertedValue;
  } // retrieveMappedValue(String, String, int)
} // class BrailleASCIITables