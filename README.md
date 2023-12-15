# bit-trees-and-braille
Wenfei Lin


BrailleASCII.java is a program that converts some string of input (ASCII characters or Braille 6-bit representation of characters) into the desired characters (6-bit Braille representation of characters, ASCII characters, or Unicode representation of Braille characters) by creating and traversing binary bit trees to convert from one representation to the other. Each of the three binary bit trees are set with mappings specified by one of the text files provided, ASCIIToBraille.txt, BrailleToASCII.txt, or BrailleToUnicode.txt. These functionalities are done with the help of BitTree.java, BitTreeLeaf.java, BitTreeNode.java, and BrailleASCIITables.java.


Credit:

- StackOverflow(https://stackoverflow.com/questions/18380901/how-do-i-convert-unicode-codepoints-to-their-character-representation) for figuring out how to convert to Unicode Braille character

- Binary Search Tree Lab Code (https://github.com/Grinnell-CSC207/lab-bsts-c/blob/main/src/SimpleBST.java) as guide for figuring out how to implement the dump method in BitTree.java

- SamR for hints on Braille to Unicode conversion