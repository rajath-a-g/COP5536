/**
 * Contains the basic structure of the
 * red black tree node with the
 * parent and left and right children
 * we also have the exection time for reference
 */
public class RB_Node {
    int buildingID;
    int count;
    long total_time;
    long execution_time;
    RB_Node left, right, parent;
    Color color;

	/**
	 * Parameterized constructor of RB_Node
	 * 
	 * @param buildingID Unique identifier of building
	 * @param count count of buildings
	 * @param left left child
	 * @param right right child
	 * @param parent parent of red black tree
	 * @param color color of the node
	 * @param total_time total-time th building has to be constructed
	 * @param execution_time days building has executed
	 */
    public RB_Node(int buildingID, int count, RB_Node left, RB_Node right,
						  RB_Node parent, Color color, long total_time, long execution_time) {

        this.buildingID = buildingID;
        this.count = count;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.color = color;
        this.total_time = total_time;
        this.execution_time = execution_time;
    }

    public RB_Node() {
    }

    public boolean isEmpty() {
        return false;
    }
}
