import java.util.ArrayList;

/**
 * Class to define all the operations of the minimum minHeap.
 * It includes operations like insert,remove and heapify.
 * All methods require the index of the node to be passed to
 * them.
 */
public class MinHeap {
    private HeapNode heapNodes[];
    int numberOfElements;

    /**
     * Default constructor of Minimum Heap class
     */
    public MinHeap() {
        this.heapNodes = new HeapNode[500];
        numberOfElements = 0;
    }

    /**
     * Inserts a minHeap node into the minimum minHeap
     * where the nodes are ordered by the
     * execution time as a constructionDoneTime
     *
     * @param totalTime          total time taken to construct the building
     * @param totalExecutionTime the time the building has to be executed
     * @param node               the red black tree node to created with building number
     */
    public void insert(long totalTime, long totalExecutionTime, RB_Node node) {
        HeapNode n = new HeapNode(totalTime, totalExecutionTime, node);
        if (numberOfElements == heapNodes.length)
            increaseHeapSize();
        heapNodes[numberOfElements] = n;
        numberOfElements++;
        int i = numberOfElements - 1;
        int parentNode = findParent(i);
        while (parentNode != i && heapNodes[i].constructionDoneTime < heapNodes[parentNode].constructionDoneTime) {
            replace(i, parentNode);
            i = parentNode;
            parentNode = findParent(i);
        }
        buildHeap();
    }

    /**
     * Return the number of elements present
     * in the minimum minHeap at a given time
     *
     * @return int Size of the minHeap
     */
    public int getSize() {
        return numberOfElements;
    }

    /**
     * Memory-opimization: we keep the
     * initial size of the minHeap as 100 and
     * then double the size whenever it is full
     */
    public void increaseHeapSize() {
        HeapNode newHeap[] = new HeapNode[2 * numberOfElements];
        if (numberOfElements == heapNodes.length) {
            //int i = 0;
            for (int i = 0; i < numberOfElements; i++) {
                newHeap[i] = heapNodes[i];
            }
        }
        heapNodes = newHeap;
    }

    /**
     * Used to build the minHeap after doing an insert
     */
    public void buildHeap() {
        int i = numberOfElements / 2;
        while (i >= 0) {
            minHeapify(i);
            i--;
        }
    }

    /**
     * Extracts minimum node which sits at the root
     * from the node
     * and returns it
     *
     * @return HeapNode current minimum in the minHeap.
     */
    public HeapNode extractMin() {
        if (numberOfElements == 0) {
            throw new RuntimeException("You ar trying to access a negative minHeap index.");
        } else if (numberOfElements == 1) {
            HeapNode minimumNode = remove(0);
            --numberOfElements;
            return minimumNode;
        }
        HeapNode minimum = heapNodes[0];
        HeapNode lastNode = remove(numberOfElements - 1);
        heapNodes[0] = lastNode;
        minHeapify(0);
        --numberOfElements;
        return minimum;
    }

    /**
     * Remove the minHeap node at the given position and
     * resetructure the minHeap.
     *
     * @param position the position at which the node to be deleted resides
     * @return HeapNode Return the minHeap node deleted.
     */
    public HeapNode remove(int position) {
        HeapNode deletedNode = heapNodes[position];
        int pos = position;
        while (pos < numberOfElements - 1) {
            heapNodes[pos] = heapNodes[pos + 1];
            pos++;
        }
        return deletedNode;
    }

    /**
     * This method is used to balance the minHeap
     * whenever an operation has been performed on it
     *
     * @param i The index to perform the balance on.
     */
    private void minHeapify(int i) {
        int left = findLeftChild(i);
        int rightNode = findRightChild(i);
        int minHeapIndex = -1;
        int k = 0;
        //Check if left node is less or equal to root and assign the minimum  node accordingly
        if (left <= numberOfElements - 1 &&
                heapNodes[left].constructionDoneTime <= heapNodes[i].constructionDoneTime) {
            if (heapNodes[left].constructionDoneTime == heapNodes[i].constructionDoneTime) {
                if (heapNodes[left].rb_Node.buildingID < heapNodes[i].rb_Node.buildingID) {
                    //if there is a tie then the least building number will be taken first
                    minHeapIndex = left;
                } else {
                    minHeapIndex = i;
                } // handling all other cases
            } else {
                minHeapIndex = left;
            }
        } else {
            minHeapIndex = i;
        }
        //Check if right node is less or equal to root and assign the minimum  node accordingly
        if (rightNode <= numberOfElements - 1 && heapNodes[rightNode].constructionDoneTime <= heapNodes[minHeapIndex].constructionDoneTime) {
            if (heapNodes[rightNode].constructionDoneTime == heapNodes[minHeapIndex].constructionDoneTime) {
                if (heapNodes[rightNode].rb_Node.buildingID < heapNodes[minHeapIndex].rb_Node.buildingID) {
                    //if there is a tie then the least building number will be taken first
                    minHeapIndex = rightNode;
                } else {
                    minHeapIndex = minHeapIndex;
                }
            } else {
                minHeapIndex = rightNode;
            }
        } else {
            minHeapIndex = minHeapIndex;
        }
        //if the minHeapIndex has changed then swap and then call heapify on it
        if (minHeapIndex != i) {
            replace(i, minHeapIndex);
            minHeapify(minHeapIndex);
        }
    }

    /**
     * Returns the minimum element of the minHeap which is the first element
     *
     * @return HeapNode minimum element node
     */
    public HeapNode getMin() {
        return heapNodes[0];
    }

    /**
     * Returns if the minHeap is empty or not
     *
     * @return boolean minHeap is empty or not
     */
    public boolean isEmpty() {
        return numberOfElements == 0 ? true : false;
    }

    /**
     * Finds the right child index when the parent is given
     *
     * @param i Parent index
     * @return int index of right child
     */
    private int findRightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * Finds the left child index when the parent is given
     *
     * @param i Parent index
     * @return index of right child
     */
    private int findLeftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * Finds the parent if child index is given
     *
     * @param i index of child
     * @return index of parent in minHeap
     */
    private int findParent(int i) {
        return ((i % 2) == 1 ? (i / 2) : ((i - 1) / 2));
    }

    /**
     * Swaps the nodes of the given indexes
     *
     * @param i      index of A
     * @param parent index of root
     */
    private void replace(int i, int parent) {
        HeapNode temp = heapNodes[parent];
        heapNodes[parent] = heapNodes[i];
        heapNodes[i] = temp;
    }
}