/**
 * A class that represents a leaf that can store a value in a binary bit tree
 */
public class BitTreeLeaf extends BitTreeNode {
  // FIELDS
  String value;

  // METHODS
  /**
   * Creates a leaf (an "endpoint") of the binary bit tree that only contains
   * information about the value it is storing.
   * 
   * @param value
   */
  public BitTreeLeaf(String value) {
    super(null, null);
    this.value = value;
  } // BitTreeLeaf(String)

  /**
   * Returns the value of this leaf
   * 
   * @return the value of this leaf
   */
  public String getValue() {
    return this.value;
  } // getValue()
} // class BitTreeLeaf