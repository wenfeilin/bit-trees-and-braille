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
    String fileName = "ASCIIToBraille.txt";
    File file = new File(fileName);
    InputStream inputStream;
    String asciiToBrailleOutput = "";
    String letterToASCIIBits = "";

    // convert ASCII letter to 8-bit binary
    int charNum = (int) letter;

    for (int i = 8; i >= 1; i--) {
      if ((int)(Math.pow(2, i)) <= charNum) {
        letterToASCIIBits += "1";
      } else {
        letterToASCIIBits += "0";
      }
    }

    try {
      inputStream = new FileInputStream(file);
      brailleTree.load(inputStream);
      asciiToBrailleOutput = brailleTree.get(letterToASCIIBits);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s is cannot be found.", fileName);
      System.exit(5); 
    } catch (Exception e) { // need to also consider not one of alphabets considered in braille txt file
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 8 bits long.\n", letterToASCIIBits);
      System.exit(6);
    }

    return asciiToBrailleOutput;

    // int letterASCIINum = (int) letter - 97;
    // int row = letterASCIINum / 10;
    // int col = letterASCIINum % 10;
    // char[] bitsArr = new char[6];
    // Arrays.fill(bitsArr, '0');

    // if (letter == 'w') {
    //   col = 9;
    // } else if (letter == 'x' || letter == 'y' || letter == 'z') {
    //   --col;
    // }

    // switch(col) {
    //   case 0:
    //     bitsArr[0] = '1';
    //     break;
    //   case 1:
    //     bitsArr[0] = '1';
    //     bitsArr[1] = '1';
    //     break;
    //   case 2:
    //     bitsArr[0] = '1';
    //     bitsArr[3] = '1';
    //     break;
    //   case 3:
    //     bitsArr[0] = '1';
    //     bitsArr[3] = '1';
    //     bitsArr[4] = '1';
    //     break;
    //   case 4:
    //     bitsArr[0] = '1';
    //     bitsArr[4] = '1';
    //     break;
    //   case 5:
    //     bitsArr[0] = '1';
    //     bitsArr[1] = '1';
    //     bitsArr[3] = '1';
    //     break;
    //   case 6:
    //     bitsArr[0] = '1';
    //     bitsArr[1] = '1';
    //     bitsArr[3] = '1';
    //     bitsArr[4] = '1';
    //     break;
    //   case 7:
    //     bitsArr[0] = '1';
    //     bitsArr[1] = '1';
    //     bitsArr[4] = '1';
    //     break;
    //   case 8:
    //     bitsArr[1] = '1';
    //     bitsArr[3] = '1';
    //     break;
    //   case 9:
    //     bitsArr[1] = '1';
    //     bitsArr[3] = '1';
    //     bitsArr[4] = '1';
    //     break;
    // }

    // if (row == 1) {
    //   bitsArr[2] = '1';
    // } else if (letter == 'w') {
    //   bitsArr[5] = '1';
    // } else {
    //   bitsArr[2] = '1';
    //   bitsArr[5] = '1';
    // }

    // return new String(bitsArr);
  } // String toBraille(char)

  public static String toASCII(String bits) { // braille to ASCII
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    BitTree asciiTree = new BitTree(6);
    String fileName = "BrailleToASCII.txt";
    File file = new File(fileName);
    InputStream inputStream;
    String bitsToASCIIOutput = "";

    try {
      inputStream = new FileInputStream(file);
      asciiTree.load(inputStream);
      bitsToASCIIOutput = asciiTree.get(bits);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s is cannot be found.", fileName);
      System.exit(5);
    } catch (Exception e) {
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 6 bits long.\n", bits);
      System.exit(6);
    }

    return bitsToASCIIOutput;
    
    // int aInASCIINum = 97;
    // int bitsASCIINum = -1;
    // String top4BrailleDotPositions = bits.substring(0, 2) + bits.substring(3, 5);

    // switch(top4BrailleDotPositions) {
    //   case "1000":
    //     bitsASCIINum = 0;
    //     break;
    //   case "1100":
    //     bitsASCIINum = 1;
    //     break;
    //   case "1010":
    //     bitsASCIINum = 2;

    //     if (bits.charAt(5) == '1') { // special case: x
    //       --bitsASCIINum;
    //     }
    //     break;
    //   case "1011":
    //     bitsASCIINum = 3;

    //     if (bits.charAt(5) == '1') { // special case: y
    //       --bitsASCIINum;
    //     }
    //     break;
    //   case "1001":
    //     bitsASCIINum = 4;

    //     if (bits.charAt(5) == '1') { // special case: z
    //       --bitsASCIINum;
    //     }
    //     break;
    //   case "1110":
    //     bitsASCIINum = 5;
    //     break;
    //   case "1111":
    //     bitsASCIINum = 6;
    //     break;
    //   case "1101":
    //     bitsASCIINum = 7;
    //     break;
    //   case "0110":
    //     bitsASCIINum = 8;
    //     break;
    //   case "0111":
    //     bitsASCIINum = 9;

    //     if (bits.charAt(5) == '1') { // special case: w
    //       bitsASCIINum -= 7;
    //     }
    //     break;
    // }

    // if (bits.charAt(5) == '1') {
    //   bitsASCIINum += 20;
    // } else if (bits.charAt(2) == '1') {
    //   bitsASCIINum += 10;
    // }

    // return "" + (char) (bitsASCIINum + aInASCIINum);
  } // String toASCII(String)

  public static String toUnicode(String bits) { // braille to Unicode
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    BitTree unicodeTree = new BitTree(6);
    String fileName = "BrailleToUnicode.txt";
    File file = new File(fileName);
    InputStream inputStream;
    String bitsToUnicodeOutput = "";

    try {
      inputStream = new FileInputStream(file);
      unicodeTree.load(inputStream);
      bitsToUnicodeOutput = unicodeTree.get(bits);
    } catch (FileNotFoundException fnfe) {
      errorPrinter.printf("Error: %s is cannot be found.", fileName);
      System.exit(5);
    } catch (Exception e) {
      errorPrinter.printf("Error: %s is not composed of only 0's and 1's and/or bits is not 6 bits long.\n", bits);
      System.exit(6);
    }

    return bitsToUnicodeOutput;

    // BigInteger brailleUnicodeOffsetHex = new BigInteger("10240");
    // int binaryToDecimalSumOfBits = 0;

    // for(int i = 0, j = bits.length() - 1; i < bits.length(); i++, j--) {
    //   if (bits.charAt(i) == '1') {
    //     binaryToDecimalSumOfBits += Math.pow(2, j);
    //   }
    // }

    // BigInteger unicode = new BigInteger("" + binaryToDecimalSumOfBits);

    // return unicode.add(brailleUnicodeOffsetHex).toString(16);
  } // String toUnicode(String)
} // class BrailleASCIITables