import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * A class that converts a value to its respectively mapped value in one of the
 * systems: braille, ASCII, unicode.
 * 
 * @author Wenfei Lin
 */
public class BrailleASCIITables {

  /**
   * Converts an ASCII character to a string of bits representing the
   * corresponding braille character.
   * 
   * @param letter an ASCII character
   * @return a string of the bits corresponding to the braille character of the
   *         ASCII character, letter
   */
  public static String toBraille(char letter) { // ASCII letter to Braille
    String fileName = "ASCIIToBraille.txt"; // for Linux
    String letterToASCIIBits = "";
    int asciiToBrailleTreeBits = 8; // ascii letter expressed in 8 bits

    // Convert ASCII letter to 8-bit binary
    int charNum = (int) letter;
    int maxPowerIn8BitBinary = 7;
    int minPowerIn8BitBinary = 0;

    for (int i = maxPowerIn8BitBinary; i >= minPowerIn8BitBinary; i--) {
      if ((int) (Math.pow(2, i)) <= charNum) {
        letterToASCIIBits += "1";
        charNum -= (int) (Math.pow(2, i));
      } else {
        letterToASCIIBits += "0";
      }
    }

    // Get and return the associated braille bits for the ASCII character
    return retrieveMappedValue(fileName, letterToASCIIBits, asciiToBrailleTreeBits);
  } // toBraille(char)

  /**
   * Converts a string of bits representing a braille character to its respective
   * ASCII character.
   * 
   * @param bits string of bits corresponding to a braille character
   * @return a string that contains the exact ASCII letter the braille represents
   */
  public static String toASCII(String bits) { // Braille to ASCII
    String fileName = "BrailleToASCII.txt"; // for Linux
    int brailleToASCIITreeBits = 6; // braille expressed in 6 bits

    // Get and return the associated ASCII bits for the braille character
    return retrieveMappedValue(fileName, bits, brailleToASCIITreeBits);
  } // toASCII(String)

  /**
   * Converts a string of bits representing a braille character to its
   * corresponding Unicode braille character.
   * 
   * @param bits string of bits representing a braille character
   * @return a string containing the Unicode braille character for bits
   */
  public static String toUnicode(String bits) { // Braille to Unicode
    String fileName = "BrailleToUnicode.txt"; // for Linux
    int brailleToUnicodeTreeBits = 6; // braille expressed in 6 bits

    // Get and return the associated unicode "value" for expressing the braille
    // character
    return retrieveMappedValue(fileName, bits, brailleToUnicodeTreeBits);
  } // toUnicode(String)

  // HELPER METHODS

  /**
   * Gets the respective mapped value after traversing the path specified by
   * input.
   * 
   * @param filename file that contains all mappings of a binary bit tree
   * @param input a path to traverse through in the tree (a string of bits)
   * @param requiredNumOfBits the number of bits the path should contain
   * @return the value associated with the path (input) in the tree specified by
   *         filename
   */
  static String retrieveMappedValue(String filename, String input, int requiredNumOfBits) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);

    // Creates the binary bit tree corresponding to the mappings in the specified
    // file
    File file = new File(filename);
    InputStream inputStream;
    BitTree tree = new BitTree(requiredNumOfBits);
    String convertedValue = "";

    try {
      inputStream = new FileInputStream(file);
      // Adds all the mappings in the file to the binary bit tree
      tree.load(inputStream);
      // Retrieves the value mapped by the path of bits (input) if the path is a valid
      // path in the tree
      convertedValue = tree.get(input);
    } catch (FileNotFoundException fnfe) { // Error with file opening
      errorPrinter.printf("Error: %s cannot be found.", filename);
      System.exit(5);
    } catch (Exception e) { // Some error specific to the path of bits given
      errorPrinter.printf("%s", e.getMessage());
      System.exit(6);
    }

    return convertedValue;
  } // retrieveMappedValue(String, String, int)
} // class BrailleASCIITables