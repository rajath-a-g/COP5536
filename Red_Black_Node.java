/**
 * Contains the basic structure of the
 * red black tree node with the
 * parent and left and right children
 * we also have the execution time for reference
 */
public class Red_Black_Node {
    int buildingID;
    int nodeCount;
    long total_time;
    long construction_time;
    Red_Black_Node left, right, parent;
    Node_Color nodeColor;

    /**
     * Parameterized constructor of Red_Black_Node
     *
     * @param buildingID        Unique identifier of building
     * @param nodeCount         nodeCount of buildings
     * @param left              left child
     * @param right             right child
     * @param parent            parent of red black tree
     * @param nodeColor         nodeColor of the node
     * @param total_time        total-time th building has to be constructed
     * @param construction_time days building has executed
     */
    public Red_Black_Node(int buildingID, int nodeCount, Red_Black_Node left, Red_Black_Node right,
                          Red_Black_Node parent, Node_Color nodeColor, long total_time, long construction_time) {

        this.buildingID = buildingID;
        this.nodeCount = nodeCount;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.nodeColor = nodeColor;
        this.total_time = total_time;
        this.construction_time = construction_time;
    }

    /**
     * Default constructor
     */
    public Red_Black_Node() {
    }

    /**
     * Return if the red black is created or else EmptyRBTree will handle
     * this call returning true
     *
     * @return boolean ifEmpty
     */
    public boolean isEmpty() {
        return false;
    }
}
