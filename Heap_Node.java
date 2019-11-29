/**
 * Class which defines the node of a heap
 * which contains the time constructed
 * and the total construction time
 */
class Heap_Node {
    long constructionDoneTime;
    long total_time;
    Red_Black_Node redBlack_Node;

    /**
     * Parameterized Constructor
     *
     * @param totalTimeToBeConstructed   total time the construction should take place
     * @param constructedTime            time the building has been constructed
     * @param correspondingRedBlack_node Red black node corresponding to the heap node
     */
    Heap_Node(long totalTimeToBeConstructed, long constructedTime, Red_Black_Node correspondingRedBlack_node) {
        constructionDoneTime = constructedTime;
        this.redBlack_Node = correspondingRedBlack_node;
        this.total_time = totalTimeToBeConstructed;
    }
}
