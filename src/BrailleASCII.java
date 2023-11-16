import java.io.PrintWriter;

public class BrailleASCII {
  public static void main(String[] args) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);

    if (args.length != 2) {
      errorPrinter.println("Error: Incorrect number of parameters.\nThe command-line paramters must consist of two element, the first being the target character set (braille, ascii, or unicode) and the second being the source characters to be translated.");
      System.exit(2);
    } else {
      String targetCharSet = args[0];
      String sourceChars = args[1];
      char[] sourceCharsArr = sourceChars.toCharArray();
      String finalOutput = "";

      switch (targetCharSet) {
        case "braille": // ASCII to braille
          for (char ch : sourceCharsArr) {
            if (!(Character.isLowerCase(ch) && Character.isLetter(ch))) {
              errorPrinter.printf("Error: %s must be composed of only lowercase alphabetic letters to convert to Braille.\n", sourceChars);
              System.exit(4);
            }

            finalOutput += BrailleASCIITables.toBraille(ch);
          }

          pen.println(finalOutput);
          break;
        case "ascii": // braille to ASCII
          if (sourceChars.length() % 6 != 0) {
            errorPrinter.printf("Error: %s should only consist of 6 bit representations per letter.\n", sourceChars);
            System.exit(7);
          } else {
            int i = 0;
            int j = 6;

            while(j <= sourceChars.length()) {
              finalOutput += BrailleASCIITables.toASCII(sourceChars.substring(i, j));
              i += 6;
              j += 6;
            }
            pen.println(finalOutput); // should this be lowercase? so confused with the lowercase and uppercase stuff
          }
          break;
        case "unicode": // ASCII to Unicode
          String asciiToBraille = "";
          for (char ch : sourceCharsArr) {
            if (!(Character.isLowerCase(ch) && Character.isLetter(ch))) {
              errorPrinter.printf("Error: %s must be composed of only lowercase alphabetic letters to convert to Braille.\n", sourceChars);
              System.exit(4);
            }

            asciiToBraille += BrailleASCIITables.toBraille(ch);
          }

          int i = 0;
          int j = 6;
          finalOutput = "";

          while(j <= sourceChars.length() * 6) {
            finalOutput += BrailleASCIITables.toUnicode(asciiToBraille.substring(i, j));
            i += 6;
            j += 6;
          }
          pen.println(finalOutput);
          break;
        default:
          errorPrinter.printf("Error: %s is an incorrect target character set.\nThe target character set should be either braille, ascii, or unicode.\n", targetCharSet);
          System.exit(3);
          break;
      }
    }
  } // main(String[])
} // class BrailleASCII