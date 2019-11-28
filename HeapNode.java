/**
 * Class which defines the node of a heap
 * which containes the time constructed
 * and the total construction time
 */
public class HeapNode {
    long constructionDoneTime;
    long total_time;
    RB_Node rb_Node;

    public HeapNode(long totalTimeToBeConstructed, long constructedTime, RB_Node correspondingRB_node) {
        constructionDoneTime = constructedTime;
        this.rb_Node = correspondingRB_node;
        this.total_time = totalTimeToBeConstructed;
    }

    /**
     * Default constructor
     */
    public HeapNode() {
    }
}
