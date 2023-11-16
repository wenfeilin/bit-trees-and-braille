import java.io.PrintWriter;

/**
 * A class that stores mappings from bits to values.
 */
public class BrailleASCII {
  public static void main(String[] args) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);

    if (args.length != 2) {
      errorPrinter.println("Error: Incorrect number of parameters.\nThe command-line paramters must consist of two elements, the first being the target character set (braille, ascii, or unicode) and the second being the source characters to be translated.");
      System.exit(2);
    } else {
      String targetCharSet = args[0];
      String sourceChars = args[1];
      char[] sourceCharsArr = sourceChars.toCharArray();
      String finalOutput = "";

      switch (targetCharSet) {
        case "braille": // ASCII to braille
          for (char ch : sourceCharsArr) {
            if (!(ch == ' ' || Character.isLetter(ch))) {
              errorPrinter.printf("Error: %s must be composed of only alphabetic letters and/or spaces to convert to Braille.\n", sourceChars);
              System.exit(4);
            }

            finalOutput += BrailleASCIITables.toBraille(ch);
          }

          pen.println(finalOutput);
          break;
        case "ascii": // braille to ASCII
          int numBitsInBrailleChar = 6;
          if (sourceChars.length() % numBitsInBrailleChar != 0) {
            errorPrinter.printf("Error: %s should only consist of %d bit representations per letter.\n", sourceChars, numBitsInBrailleChar);
            System.exit(7);
          } else {
            int i = 0;
            int j = numBitsInBrailleChar;

            while(j <= sourceChars.length()) {
              finalOutput += BrailleASCIITables.toASCII(sourceChars.substring(i, j)).toLowerCase();
              i += numBitsInBrailleChar;
              j += numBitsInBrailleChar;
            }
            pen.println(finalOutput); 
          }
          break;
        case "unicode": // ASCII to Unicode
          String asciiToBraille = "";
          int numBitsInASCIIChar = 6;

          for (char ch : sourceCharsArr) {
            if (!(ch == ' ' || Character.isLetter(ch))) {
              errorPrinter.printf("Error: %s must be composed of only alphabetic letters and/or spaces to convert to Braille.\n", sourceChars);
              System.exit(4);
            }

            asciiToBraille += BrailleASCIITables.toBraille(ch);
          }

          int i = 0;
          int j = numBitsInASCIIChar;
          String finalUnicodeString = "";

          while(j <= sourceChars.length() * numBitsInASCIIChar) {
            String bitsToUnicodeCharNum = BrailleASCIITables.toUnicode(asciiToBraille.substring(i, j));
            int unicodeCharHex = Integer.parseInt(bitsToUnicodeCharNum, 16);
            finalUnicodeString += Character.toString(unicodeCharHex);

            i += numBitsInASCIIChar;
            j += numBitsInASCIIChar;
          }
          pen.println(finalUnicodeString);
          break;
        default:
          errorPrinter.printf("Error: %s is an incorrect target character set.\nThe target character set should be either braille, ascii, or unicode.\n", targetCharSet);
          System.exit(3);
          break;
      }
    }
  } // main(String[])
} // class BrailleASCII