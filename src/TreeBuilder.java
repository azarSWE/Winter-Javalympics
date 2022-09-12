
/**
 * This class is used to create the binary trees using a queue_based approach
 * 
 * @author azararya
 * @param <T> generic data type
 */

public class TreeBuilder<T> {

	/**
	 * This method is used to build a binary tree based on level order approach
	 * 
	 * @param data is an array of values
	 * @return a binary tree
	 */
	public LinkedBinaryTree<T> buildTree(T[] data) {

		/* initialize dataQueue with all elements to be added into the nodes in order */
		LinkedQueue<T> dataQueue = new LinkedQueue<T>();
		for (int i = 0; i < data.length; i++) {
			dataQueue.enqueue(data[i]);
		}

		LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<BinaryTreeNode<T>>();

		BinaryTreeNode<T> root = new BinaryTreeNode<T>(dataQueue.dequeue());
		LinkedBinaryTree<T> bTObj = new LinkedBinaryTree<T>(root);

		parentQueue.enqueue(root);

		/* insert the elements of the array into the nodes */
		while (dataQueue.size() >= 2) {

			T a = dataQueue.dequeue();
			T b = dataQueue.dequeue();

			BinaryTreeNode<T> parent = parentQueue.dequeue();

			if (a != null) {
				BinaryTreeNode<T> leftChild = new BinaryTreeNode<T>(a);
				parent.setLeft(leftChild);
				parentQueue.enqueue(leftChild);
			}

			if (b != null) {
				BinaryTreeNode<T> rightChild = new BinaryTreeNode<T>(b);
				parent.setRight(rightChild);
				parentQueue.enqueue(rightChild);
			}
		}

		return bTObj;
	}
}
