import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
// import java.math.BigInteger;
// import java.util.Arrays;

public class BrailleASCIITables {
  
  public static String toBraille(char letter) { // ASCII letter to braille
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    BitTree brailleTree = new BitTree(8);
    String fileName = "ASCIIToBraille.txt"; // for Linux
    File file = new File(fileName);
    InputStream inputStream;
    String asciiToBrailleOutput = "";
    String letterToASCIIBits = "";

    // convert ASCII letter to 8-bit binary
    int charNum = (int) letter;

    if (Character.isLowerCase(letter)) {
      charNum -= 32;
    }
    

    for (int i = 7; i >= 0; i--) {
      if ((int)(Math.pow(2, i)) <= charNum) {
        letterToASCIIBits += "1";
        charNum -= (int)(Math.pow(2, i));
      } else {
        letterToASCIIBits += "0";
      }
    }

    try {
      inputStream = new FileInputStream(file);
      brailleTree.load(inputStream);
      asciiToBrailleOutput = brailleTree.get(letterToASCIIBits);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s cannot be found.", fileName);
      System.exit(5); 
    } catch (Exception e) { // need to also consider not one of alphabets considered in braille txt file
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 8 bits long.\n", letterToASCIIBits);
      System.exit(6);
    }

    return asciiToBrailleOutput;
  } // String toBraille(char)

  public static String toASCII(String bits) { // braille to ASCII
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    BitTree asciiTree = new BitTree(6);
    String fileName = "BrailleToASCII.txt"; // for Linux
    File file = new File(fileName);
    InputStream inputStream;
    String bitsToASCIIOutput = "";

    try {
      inputStream = new FileInputStream(file);
      asciiTree.load(inputStream);
      bitsToASCIIOutput = asciiTree.get(bits);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s cannot be found.", fileName);
      System.exit(5);
    } catch (Exception e) {
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 6 bits long.\n", bits);
      System.exit(6);
    }

    return bitsToASCIIOutput;
  } // String toASCII(String)

  public static String toUnicode(String bits) { // braille to Unicode
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    BitTree unicodeTree = new BitTree(6);
    String fileName = "BrailleToUnicode.txt"; // for Linux
    File file = new File(fileName);
    InputStream inputStream;
    String bitsToUnicodeCharNum = "";

    try {
      inputStream = new FileInputStream(file);
      unicodeTree.load(inputStream);
      bitsToUnicodeCharNum = unicodeTree.get(bits);
      
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s cannot be found.", fileName);
      System.exit(5);
    } catch (Exception e) {
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 6 bits long.\n", bits);
      System.exit(6);
    }

    return bitsToUnicodeCharNum;
  } // String toUnicode(String)
} // class BrailleASCIITables