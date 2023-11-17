import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A class that stores mappings from bits to values.
 * 
 * @author Wenfei Lin
 */
public class BitTree {
  // FIELDS
  int levels;
  BitTreeNode root;

  // METHODS
  /**
   * Creates a binary bit tree with n + 1 levels and a root node. 
   * @param n the length of a path in a tree (levels of tree - 1)
   */
  public BitTree(int n) {
    this.levels = ++n;
    BitTreeNode rootNode = new BitTreeNode(null, null);
    this.root = rootNode;
  } // BitTree(int)

  /**
   * Adds or replaces the value at the end of the path through the tree specified
   * by bits with value.
   * 
   * @param bits  the path to follow through the tree (composed of 0's and 1's)
   *              and should be an appropriate length
   * @param value a value for the leaf at the end of the path taken by bits
   * @throws Exception if bits is the inappropriate length or contains values
   *                   other than 0 or 1
   */
  public void set(String bits, String value) throws Exception {
    if (bits.length() == levels - 1) {
      BitTreeNode pointer = this.root;

      for (int i = 0; i < bits.length(); i++) {
        if (bits.charAt(i) == '0') { // Go left
          if (i == bits.length() - 1) { // The last bit (leaf)
            if (pointer.left == null) { // If the leaf hasn't been made yet,
              // Make the leaf with the associated value
              BitTreeLeaf newLeftLeaf = new BitTreeLeaf(value);
              pointer.left = newLeftLeaf;
            } else { // Otherwise, the leaf exists, 
              // So replace value in leaf
              ((BitTreeLeaf) pointer.left).setValue(value);
            }
          } else { // Do this for the nodes
            if (pointer.left == null) { // If the left node hasn't been created yet,
              // Create the left node and connect it to the tree
              BitTreeNode newLeftNode = new BitTreeNode(null, null);
              pointer.left = newLeftNode;
            } 

            pointer = pointer.left;
          }
        } else if (bits.charAt(i) == '1') { // Go right
          if (i == bits.length() - 1) { // The last bit (leaf)
            if (pointer.right == null) { // If the leaf hasn't been made yet,
              // Make the leaf with the associated value
              BitTreeLeaf newRightLeaf = new BitTreeLeaf(value);
              pointer.right = newRightLeaf;
            } else { // Otherwise, the leaf exists, 
              // So replace value in leaf
              ((BitTreeLeaf) pointer.right).setValue(value);
            }
          } else { // Do this for the nodes
            if (pointer.right == null) { // If the right node hasn't been created yet,
              // Create the right node and connect it to the tree
              BitTreeNode newRightNode = new BitTreeNode(null, null);
              pointer.right = newRightNode;
            }

            pointer = pointer.right;
          }
        } else { // Not composed solely of 0's and 1's
          throw new Exception(String.format("Error: %s is not composed of only 0's and 1's.\n", 
              bits));
        }
      }
    } else { // Inappropriate length
      throw new Exception(String.format("Error: %s is not %d bits long.\n", bits, levels - 1));
    }
  } // set(String, String)

  /**
   * Follows the path through the tree given by bits, returning the value at the end.
   * 
   * @param bits a path in the given trees
   * @return the value at the end of the path specified by bits
   * @throws Exception If bits is a non-existent path or is of an incorrect length or 
   *                   doesn't only contain 0's and 1's
   */
  public String get(String bits) throws Exception {
    BitTreeNode pointer = this.root;

    if (bits.length() == levels - 1) { // Make sure path is correct length
      for (int i = 0; i < bits.length(); i++) { // Continue for length of path
        if (bits.charAt(i) == '0') { // Go left
          if (pointer.left != null) {
            pointer = pointer.left;
          } else { // Non-existent path
            throw new Exception(String.format("Error: %s is a non-existent path in the tree.\n", 
                bits));
          }
        } else if (bits.charAt(i) == '1') { // Go right
          if (pointer.right != null) {
            pointer = pointer.right;
          } else { // Non-existent path
            throw new Exception(String.format("Error: %s is a non-existent path in the tree.\n", 
                bits));
          }
        } else { // Not composed solely of 0's and 1's
          throw new Exception(String.format("Error: %s is not composed of only 0's and 1's.\n", 
              bits));
        }
      }
    } else { // Inappropriate length
      throw new Exception(String.format("Error: %s is not %d bits long.\n", bits, levels - 1));
    }

    // Check that a leaf is at the end of the path and return the value at that leaf
    if (pointer instanceof BitTreeLeaf) { 
      return ((BitTreeLeaf) pointer).getValue();
    } else { // Not valid path in tree because there is no leaf at the end of the path
      throw new Exception(String.format("Error: %s is a non-existent path in the tree.\n", bits));
    }
  } // get(String)

  /**
   * Prints out the contents of the tree in CSV format (ex: 101100,M as one mapping on one line).
   * 
   * @param pen PrintWriter to print contents
   */
  public void dump(PrintWriter pen) {
    // Start traversal at the root
    BitTreeNode current = this.root;
    String path = "";

    traverseAll(current, path, pen);
  } // dump(PrintWriter)

  /**
   * Reads a series of lines of the form bits,value and stores them in the tree.
   * @param source file source
   */
  public void load(InputStream source) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    Scanner fileReader = new Scanner(source);
    String line;
    String[] partsOfLine;
    int lineNumber = 1;

    while (fileReader.hasNextLine()) {
      line = fileReader.nextLine();
      partsOfLine = line.split(","); // Split path and value in each line

      try {
        // Set the mapping in the tree
        this.set(partsOfLine[0], partsOfLine[1]);
      } catch (Exception e) { // Some error in the input from the specified file line
        errorPrinter.printf("(Line %d) %s", lineNumber, e.getMessage());
        System.exit(1);
      }
      lineNumber++;
    }

    // Close file reader
    fileReader.close();
  } // load(InputStream)

  // Helper methods

  /**
   * Traverses all the paths in the tree while printing the path and the value at the end 
   * of the path in CSV format (ex: 101100,M as one mapping on one line).
   * 
   * @param node current node
   * @param path path in tree 
   * @param pen PrintWriter to print contents
   */
  void traverseAll(BitTreeNode node, String path, PrintWriter pen) {
    if (node == null) { // Stop when current node is null
      return;
    }

    if (node instanceof BitTreeLeaf) { // Reached a leaf, so print path, value
      pen.print(path);
      pen.printf(",%s\n", ((BitTreeLeaf) node).getValue());
    } else {
      traverseAll(node.left, path + "0", pen); // Traversing left, add "0" to the path
      traverseAll(node.right, path + "1", pen); // Traversing right, add "1" to the path
    }
  } // traverse(BitTreeNode, String, PrintWriter)
} // class BitTree