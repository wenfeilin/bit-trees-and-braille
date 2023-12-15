/**
 * A class that represents a node in a binary bit tree with only information
 * about its left and right children.
 * 
 * @author Wenfei Lin
 */
public class BitTreeNode {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  BitTreeNode left;
  BitTreeNode right;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  
  /**
   * Creates a node of the binary bit tree that has left and right children (could
   * be null).
   * 
   * @param leftNode  pointer to the left child of this node
   * @param rightNode pointer to the right child of this node
   */
  public BitTreeNode(BitTreeNode leftNode, BitTreeNode rightNode) {
    this.left = leftNode;
    this.right = rightNode;
  } // BitTreeNode(BitTreeNode, BitTreeNode)
} // class BitTreeNode