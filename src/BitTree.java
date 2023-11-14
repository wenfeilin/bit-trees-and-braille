import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BitTree {
  int levels; 
  BitTreeNode root;

  public BitTree(int n) {
    this.levels = ++n;
    this.root = null;
  } // BitTree(int)

  /**
   * Adds or replaces the value at the end of the path through the tree specified by bits with value
   * 
   * @param bits the path to follow through the tree (composed of 0's and 1's) and should be an appropriate length
   * @param value a value for the leaf at the end of the path taken by bits
   * @throws Exception if bits is the inappropriate length or contains values other than 0 or 1
   */
  public void set(String bits, String value) throws Exception {
    if (bits.length() == levels - 1) {
      BitTreeNode pointer = this.root;
      BitTreeNode rootNode = new BitTreeNode(null, null);
      this.root = rootNode;

      for (int i = 0; i < bits.length() - 1; i++) {
        if (bits.charAt(i) == '0') { // go left
          if (i == bits.length() - 2) { // the last bit
            BitTreeLeaf newLeftLeaf = new BitTreeLeaf(value);
            pointer.left = newLeftLeaf;
          } else {
            BitTreeNode newLeftNode = new BitTreeNode(null, null);
            pointer.left = newLeftNode;
            pointer = pointer.left;
          }
        } else if (bits.charAt(i) == '1') { // go right
          if (i == bits.length() - 2) { // the last bit
            BitTreeLeaf newRightLeaf = new BitTreeLeaf(value);
            pointer.right = newRightLeaf;
          } else {
            BitTreeNode newRightNode = new BitTreeNode(null, null);
            pointer.right = newRightNode;
            pointer = pointer.right;
          }
        } else { // not composed solely of 0's and 1's
          throw new Exception();
        }
      }
    } else { // inappropriate length
      throw new Exception();
    }
  } // set(String, String)

  public String get(String bits) throws Exception {
    BitTreeNode pointer = this.root;

    if (bits.length() == levels - 1) {
      for (int i = 0; i < bits.length(); i++) {
        if (bits.charAt(i) == '0') { // go left
          if (pointer.left != null) {
            pointer = pointer.left;
          } else { // non-existent path
            throw new Exception();
          }
        } else if (bits.charAt(i) == '1') { // go right
          if (pointer.right != null) {
            pointer = pointer.right;
          } else { // non-existent path
            throw new Exception();
          }
        } else { // not composed solely of 0's and 1's
          throw new Exception();
        }
      }
    } else { // inappropriate length
      throw new Exception();
    }

    return ((BitTreeLeaf) pointer).getValue(); // do i need to throw an exception in case last node is not a leaf? I assume that's not checked in set but idk... think abt it more
  } // get(String)

  public void dump(PrintWriter pen) {
    BitTreeNode current = this.root;
    String path = "";

    traverse(current, path, pen);
    // if levels...

  } // dump(PrintWriter)

  public void load(InputStream source) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    Scanner fileReader = new Scanner(source);
    String line;
    String[] partsOfLine;
    int lineNumber = 1;

    while (fileReader.hasNextLine()) {
      line = fileReader.nextLine();
      partsOfLine = line.split(",");

      try {
        this.set(partsOfLine[0], partsOfLine[1]);
      } catch (Exception e) {
        errorPrinter.printf("The bits in line %d an inappropriate length or doesn't consist only of 0's and 1's.\n", lineNumber);
        System.exit(1);
      }
      lineNumber++;
    }

    fileReader.close();
  } // load(InputStream)

  // Helper methods
  void traverse(BitTreeNode node, String path, PrintWriter pen) {
    if (node != null) {

      if (node instanceof BitTreeLeaf) {
        pen.printf(",%c", ((BitTreeLeaf)node).getValue());
      }
      
      traverse(node.left, path, pen);
      traverse(node.right, path, pen);
    }
  }
} // class BitTree