/**
 * Class to define all the operations of the minimum minHeap.
 * It includes operations like insert,remove and heapify.
 * All methods require the index of the node to be passed to
 * them.
 */
public class MinHeap {
    private Heap_Node heapNodes[];
    int numberOfElements;

    /**
     * Default constructor of Minimum Heap class
     */
    public MinHeap() {
        this.heapNodes = new Heap_Node[100];
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
    public void insert(long totalTime, long totalExecutionTime, Red_Black_Node node) {
        Heap_Node n = new Heap_Node(totalTime, totalExecutionTime, node);
        if (numberOfElements == heapNodes.length)
            increaseHeapSize();
        heapNodes[numberOfElements] = n;
        numberOfElements++;
        int i = numberOfElements - 1;
        int parentNode = findParent(i);
        while (parentNode != i && heapNodes[i].constructionDoneTime < heapNodes[parentNode].constructionDoneTime) {
            swapHeapNodes(i, parentNode);
            i = parentNode;
            parentNode = findParent(i);
        }
        reArrangeHeap();
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
     * Memory-optimization: we keep the
     * initial size of the minHeap as 100 and
     * then double the size whenever it is full
     */
    private void increaseHeapSize() {
        Heap_Node newHeap[] = new Heap_Node[2 * numberOfElements];
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
    private void reArrangeHeap() {
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
     * @return Heap_Node current minimum in the minHeap.
     */
    Heap_Node extractMin() {
        if (numberOfElements == 0) {
            throw new RuntimeException("You ar trying to access a negative minHeap index.");
        } else if (numberOfElements == 1) {
            Heap_Node minimumNode = remove(0);
            --numberOfElements;
            return minimumNode;
        }
        Heap_Node minimum = heapNodes[0];
        Heap_Node lastNode = remove(numberOfElements - 1);
        heapNodes[0] = lastNode;
        minHeapify(0);
        --numberOfElements;
        return minimum;
    }

    /**
     * Remove the minHeap node at the given position and
     * restructure the minHeap.
     *
     * @param position the position at which the node to be deleted resides
     * @return Heap_Node Return the minHeap node deleted.
     */
    private Heap_Node remove(int position) {
        Heap_Node deletedNode = heapNodes[position];
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
     * @param givenIndex The index to perform the balance on.
     */
    private void minHeapify(int givenIndex) {
        int left = findLeftChild(givenIndex);
        int rightNode = findRightChild(givenIndex);
        int minHeapIndex = -1;
        int k = 0;
        //Check if left node is less or equal to root and assign the minimum  node accordingly
        if (left <= numberOfElements - 1 &&
                heapNodes[left].constructionDoneTime <= heapNodes[givenIndex].constructionDoneTime) {
            if (heapNodes[left].constructionDoneTime == heapNodes[givenIndex].constructionDoneTime) {
                if (heapNodes[left].redBlack_Node.buildingID < heapNodes[givenIndex].redBlack_Node.buildingID) {
                    //if there is a tie then the least building number will be taken first
                    minHeapIndex = left;
                } else {
                    minHeapIndex = givenIndex;
                } // handling all other cases
            } else {
                minHeapIndex = left;
            }
        } else {
            minHeapIndex = givenIndex;
        }
        //Check if right node is less or equal to root and assign the minimum  node accordingly
        if (rightNode <= numberOfElements - 1 &&
                heapNodes[rightNode].constructionDoneTime <= heapNodes[minHeapIndex].constructionDoneTime) {
            if (heapNodes[rightNode].constructionDoneTime == heapNodes[minHeapIndex].constructionDoneTime) {
                if (heapNodes[rightNode].redBlack_Node.buildingID < heapNodes[minHeapIndex].redBlack_Node.buildingID) {
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
        if (minHeapIndex != givenIndex) {
            swapHeapNodes(givenIndex, minHeapIndex);
            minHeapify(minHeapIndex);
        }
    }

    /**
     * Returns the minimum element of the minHeap which is the first element
     *
     * @return Heap_Node minimum element node
     */
    public Heap_Node getMin() {
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
    private void swapHeapNodes(int i, int parent) {
        Heap_Node temp = heapNodes[parent];
        heapNodes[parent] = heapNodes[i];
        heapNodes[i] = temp;
    }
}