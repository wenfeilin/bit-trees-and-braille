public class BitTreeLeaf extends BitTreeNode {
    String value;

    public BitTreeLeaf(String value) {
      super(null, null);
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  } // class BitTreeLeaf