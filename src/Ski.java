
/**
 * This class is the heart of the program in which the skier will go down the
 * hill one step at a time
 * 
 * @author azararya
 */

public class Ski {

	/**
	 * the root of the binary tree
	 */
	private BinaryTreeNode<SkiSegment> root;

	/**
	 * This constructor takes in a string array with data about the node types and
	 * initializes root
	 * 
	 * @param data is a list of string objects
	 */
	public Ski(String[] data) {

		/**
		 * create an array of objects of class SkiSegment and store the array parameter
		 * data into it
		 */
		SkiSegment[] segments = new SkiSegment[data.length];
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				if (data[i].contains("jump"))
					segments[i] = new JumpSegment(String.valueOf(i), data[i]);

				else if (data[i].contains("slalom"))
					segments[i] = new SlalomSegment(String.valueOf(i), data[i]);

				else
					segments[i] = new SkiSegment(String.valueOf(i), data[i]);
			} else
				segments[i] = null;
		}

		/**
		 * construct the tree with a TreeBuilder object and buildTree method and using
		 * segments array as input
		 */
		TreeBuilder<SkiSegment> TBObj = new TreeBuilder<SkiSegment>();
		LinkedBinaryTree<SkiSegment> treeObj = TBObj.buildTree(segments);

		root = treeObj.getRoot();

	}

	/**
	 * This function returns the root
	 * 
	 * @return root
	 */
	public BinaryTreeNode<SkiSegment> getRoot() {
		return root;
	}

	/**
	 * This function determines the best path should be taken by the skier
	 * 
	 * @param node     a segment that is added to the rear of the sequence
	 * @param sequence a sequence of the segments
	 */
	public void skiNextSegment(BinaryTreeNode<SkiSegment> node, ArrayUnorderedList<SkiSegment> sequence) {

		sequence.addToRear(node.getData());

		BinaryTreeNode<SkiSegment> nextNode;

		/* determine the next node to access from the input node */
		if (node.getLeft() != null || node.getRight() != null) {

			if (node.getLeft() == null && node.getRight() != null) {
				nextNode = node.getRight();
				skiNextSegment(nextNode, sequence);
			}

			else if (node.getLeft() != null && node.getRight() == null) {
				nextNode = node.getLeft();
				skiNextSegment(nextNode, sequence);
			}

			else if (node.getLeft() != null && node.getRight() != null) {
				nextNode = findPath(node.getLeft(), node.getRight());
				skiNextSegment(nextNode, sequence);
			}
		}
	}

	/**
	 * This function determines the best segment based on their contents as jump,
	 * slalom, regular segment or null
	 * 
	 * @param leftNode
	 * @param rightNode
	 * @return bestterSeg
	 */
	private BinaryTreeNode<SkiSegment> findPath(BinaryTreeNode<SkiSegment> leftNode,
			BinaryTreeNode<SkiSegment> rightNode) {

		BinaryTreeNode<SkiSegment> bestterSeg = new BinaryTreeNode<SkiSegment>(null);

		/* if both nodes contain jump */
		if (leftNode.getData() instanceof JumpSegment && rightNode.getData() instanceof JumpSegment) {
			if (((JumpSegment) leftNode.getData()).getHeight() > ((JumpSegment) rightNode.getData()).getHeight())
				bestterSeg = leftNode;
			else
				bestterSeg = rightNode;
		}

		/* if just left node contains jump */
		else if (leftNode.getData() instanceof JumpSegment && !(rightNode.getData() instanceof JumpSegment))
			bestterSeg = leftNode;

		/* if just right node contains jump */
		else if (!(leftNode.getData() instanceof JumpSegment) && rightNode.getData() instanceof JumpSegment)
			bestterSeg = rightNode;

		/* if both nodes contain slaloms */
		else if (leftNode.getData() instanceof SlalomSegment && rightNode.getData() instanceof SlalomSegment) {
			if (((SlalomSegment) leftNode.getData()).getDirection().equals("L")
					&& ((SlalomSegment) rightNode.getData()).getDirection().equals("W"))
				bestterSeg = leftNode;
			else
				bestterSeg = rightNode;
		}

		/* if left node is slalom and right node is regular segment */
		else if (leftNode.getData() instanceof SlalomSegment && rightNode.toString().charAt(0) == ' ') {
			if (((SlalomSegment) leftNode.getData()).getDirection().equals("L"))
				bestterSeg = leftNode;
			else
				bestterSeg = rightNode;
		}

		/* if left node is regular segment and right node is slalom */
		else if (leftNode.toString().charAt(0) == ' ' && rightNode.getData() instanceof SlalomSegment) {
			if (((SlalomSegment) rightNode.getData()).getDirection().equals("L"))
				bestterSeg = rightNode;
			else
				bestterSeg = leftNode;
		}

		/* if both nodes are the same */
		else
			bestterSeg = rightNode;

		return bestterSeg;

	}

}
