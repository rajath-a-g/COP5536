
import java.util.ArrayList;

/**
 * This class has the core logic of red black tree
 * with all the operations like :
 * search() - log(n)
 * insert() - log(n)
 * getMin() - O(1)
 * insertSearch - log(n)
 */
class EmptyRedBlackTree extends Red_Black_Node {
    public EmptyRedBlackTree() {
        super();
        this.nodeColor = Node_Color.black;
    }

    public boolean isEmpty() {
        return true;
    }
}

public class Red_Black_Tree {
    Red_Black_Node root = new EmptyRedBlackTree();

    /**
     * Finds all the jobs between the range
     * buildingID1, buildingID2
     *
     * @param buildingID1 start of the range of buildings
     * @param buildingID2 stop of the range of buildings
     * @return ArrayList list of the buildings within the range
     */
    ArrayList<Integer> getBuildingDetails(int buildingID1, int buildingID2) {
        ArrayList<Integer> resultList = new ArrayList<Integer>();
        getAllBuildings(root, buildingID1, buildingID2, resultList);
        return resultList;
    }

    /**
     * Return the building and its details
     * which are required to print.
     *
     * @param buildingID unique identifier of the building
     * @return int the building details
     */
    int getBuildingDetails(int buildingID) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        findJobs2(root, buildingID, res);
        //isBalanced(null);
        if (res.size() == 0) {
            return 0;
        } else {
            return res.get(0);
        }
    }

    /**
     * Prints all the nodes in the
     * range of values
     *
     * @param root  root of the red black tree
     * @param start start of the range
     * @param end   end of the range
     * @param res   Given list of buildings
     */
    private void getAllBuildings(Red_Black_Node root, int start, int end, ArrayList<Integer> res) {
        if (root == null) {
            return;
        }
        if (root.buildingID > start) {
            getAllBuildings(root.left, start, end, res);
        }
        if (root.buildingID >= start && root.buildingID <= end) {
            res.add(root.buildingID);
        }
        if (root.buildingID < end) {
            getAllBuildings(root.right, start, end, res);
        }
    }

    /**
     * Finds the jobs around an buildingID
     *
     * @param root       root of RB_Tree
     * @param buildingID Identifier of building
     * @param resultList List of results
     */
    private void findJobs2(Red_Black_Node root, int buildingID, ArrayList<Integer> resultList) {
        if (root == null) {
            return;
        }
        if (root.buildingID > buildingID) {
            findJobs2(root.left, buildingID, resultList);
        }
        if (root.buildingID == buildingID) {
            resultList.add(root.buildingID);
        }
        if (root.buildingID < buildingID) {
            findJobs2(root.right, buildingID, resultList);
        }

    }

    /**
     * Find the minimum in the red black tree
     *
     * @param root root
     * @return minimum value RB node
     */
    private Red_Black_Node getTheMinimumValue(Red_Black_Node root) {
        Red_Black_Node minimum = root;
        while (!minimum.left.isEmpty()) {
            minimum = minimum.left;
        }
        return minimum;
    }

    /**
     * Finds and returns the
     * maximum of Red black tree
     *
     * @param root root
     * @return Maximum value red black tree node
     */
    private Red_Black_Node getMaximumValue(Red_Black_Node root) {
        Red_Black_Node maximum = root;
        while (!maximum.right.isEmpty()) {
            maximum = maximum.right;
        }
        return maximum;
    }

    public int nextBuilding_to_Construct(int buildingID) {
        Red_Black_Node current = Search(buildingID, root);
        if (current.isEmpty()) {
            Red_Black_Node tempro = this.root;
            Red_Black_Node endNode = tempro;
            while (!tempro.isEmpty()) {
                endNode = tempro;
                if (buildingID > tempro.buildingID) {
                    tempro = tempro.right;
                } else {
                    tempro = tempro.left;
                }
            }
            if (endNode.buildingID > buildingID) {
                return endNode.buildingID;
            } else {
                Red_Black_Node rbNode_parent = endNode.parent;
                while (!rbNode_parent.isEmpty() && rbNode_parent.right == endNode) {
                    endNode = rbNode_parent;
                    rbNode_parent = rbNode_parent.parent;
                }
                return rbNode_parent.buildingID;
            }
        } else {
            if (!current.right.isEmpty()) {
                Red_Black_Node min_node = getTheMinimumValue(current.right);
                return min_node.buildingID;
            } else {
                Red_Black_Node parent = current.parent;
                while (!parent.isEmpty() && parent.right == current) {
                    current = parent;
                    parent = parent.parent;
                }
                return parent.buildingID;
            }
        }
    }

    /**
     * Search if a node exists in the Red black tree
     * if we it does not exits then return root
     *
     * @param buildingID   Unique identifier of the building
     * @param root root
     * @return Red_Black_Node or else root
     */
    public Red_Black_Node Search(int buildingID, Red_Black_Node root) {
        while(!root.isEmpty()){
                if (root.buildingID == buildingID) {
                    return root;
                } else if (root.buildingID > buildingID) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
        return root;
    }

    /**
     * Check if a building is present in the red black tree
     * or not
     *
     * @param buildingID Unique identifier of building
     * @param root       root
     * @return int 0 0r 1 based on search
     */
    public int checkIfBuildingPresent(int buildingID, Red_Black_Node root) {
        while (!root.isEmpty()) {
            if (root.buildingID == buildingID) {
                return 1;
            } else if (root.buildingID > buildingID) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return 0;
    }

    /**
     * Given a building identifier, find the lowest building
     * less than the given building identifier
     *
     * @param buildingID unique identifier of building
     * @return the previous building
     */
    public int getPreviousBuilding(int buildingID) {
        Red_Black_Node curentNode = Search(buildingID, root);
        if (curentNode.isEmpty()) {
            Red_Black_Node tempro = this.root;
            Red_Black_Node lastRBNode = new EmptyRedBlackTree();
            while (!tempro.isEmpty()) {
                lastRBNode = tempro;
                if (buildingID > tempro.buildingID) {
                    tempro = tempro.right;
                } else {
                    tempro = tempro.left;
                }
            }

            if (lastRBNode.buildingID < buildingID) {
                return lastRBNode.buildingID;
            } else {
                Red_Black_Node parentRBNode = lastRBNode;
                Red_Black_Node childRBNode = tempro;
                while (!parentRBNode.isEmpty() && parentRBNode.left == childRBNode) {
                    childRBNode = parentRBNode;
                    parentRBNode = parentRBNode.parent;
                }
                return parentRBNode.buildingID;
            }

        } else {
            if (!curentNode.left.isEmpty()) {
                Red_Black_Node maximumNode = getMaximumValue(curentNode.left);
                return maximumNode.buildingID;
            } else {
                Red_Black_Node parentNode = curentNode.parent;
                while (!parentNode.isEmpty() && parentNode.left == curentNode) {
                    curentNode = parentNode;
                    parentNode = parentNode.parent;
                }
                return parentNode.buildingID;
            }
        }
    }

    /**
     * Insert a red black tree node into the red black tree
     *
     * @param buildingID         Unique identifier of the building
     * @param totalExecutionTime total execution time
     * @param executedTime       time the building has to be constructed
     * @return Red black tree node
     */
    public Red_Black_Node insert(int buildingID, long totalExecutionTime, long executedTime) {
        Red_Black_Node tempro = this.root;
        Red_Black_Node lastNode = tempro;
        while (!tempro.isEmpty()) {
            lastNode = tempro;
            if (buildingID > tempro.buildingID) {
                tempro = tempro.right;
            } else {
                tempro = tempro.left;
            }
        }
        EmptyRedBlackTree Empty = new EmptyRedBlackTree();
        Red_Black_Node z = new Red_Black_Node(buildingID, 0, Empty, Empty, Empty, Node_Color.red,
                totalExecutionTime, executedTime);
        z.parent = lastNode;
        if (lastNode.isEmpty()) {
            this.root = z;
        }
        if (lastNode.buildingID > buildingID) {
            lastNode.left = z;
        } else {
            lastNode.right = z;
        }
        return z;
    }

    static class INT
    {
        static int d;
        INT()
        {
            d = 0;
        }
    }

    static boolean isBalancedUtil(Red_Black_Node root,
                                  INT maxh, INT minh)
    {

        // Base case
        if (root == null)
        {
            maxh.d = minh.d = 0;
            return true;
        }

        // To store max and min heights of left subtree
        INT lmxh=new INT(), lmnh=new INT();

        // To store max and min heights of right subtree
        INT rmxh=new INT(), rmnh=new INT();

        // Check if left subtree is balanced,
        // also set lmxh and lmnh
        if (isBalancedUtil(root.left, lmxh, lmnh) == false)
            return false;

        // Check if right subtree is balanced,
        // also set rmxh and rmnh
        if (isBalancedUtil(root.right, rmxh, rmnh) == false)
            return false;

        // Set the max and min heights
        // of this node for the parent call
        maxh.d = Math.max(lmxh.d, rmxh.d) + 1;
        minh.d = Math.min(lmnh.d, rmnh.d) + 1;

        // See if this node is balanced
        if (maxh.d <= 2*minh.d)
            return true;

        return false;
    }

    // A wrapper over isBalancedUtil()
    static boolean isBalanced(Red_Black_Node root)
    {
        INT maxh=new INT(), minh=new INT();
        return isBalancedUtil(root, maxh, minh);
    }
    /**
     * Contains all the cases in a left rotate of the red black tree
     *
     * @param around The node around which we have to left rotate
     */
    public void rotate_left(Red_Black_Node around){
        Red_Black_Node y = around.right;
        around.right = y.left;
        if(!y.left.isEmpty()){
            y.left.parent = around;
        }
        if(around.parent.isEmpty()){
            this.root = y;
            y.parent = around.parent;
        }
        else if(around == around.parent.left){
            around.parent.left = y;
            y.parent = around.parent;
        }
        else{
            around.parent.right = y;
            y.parent = around.parent;
        }
        y.left = around;
        around.parent = y;

    }

    /**
     * Contains all the cases in a right rotate of the red black tree
     *
     * @param around The node around which we have to left rotate
     */
    public void rotate_right(Red_Black_Node around){
        Red_Black_Node x = around.left;
        around.left = x.right;
        if(!x.right.isEmpty()){
            x.right.parent = around;
        }
        if(around.parent.isEmpty()){
            this.root = x;
            x.parent = around.parent;
        }
        else if(around == around.parent.left){
            around.parent.left = x;
            x.parent = around.parent;
        }
        else{
            around.parent.right = x;
            x.parent = around.parent;
        }
        x.right = around;
        around.parent = x;
    }

    /**
     * Swap function for swapping
     * two red black tree nodes
     *
     * @param oneNode the first node to swap
     * @param twoNode the second node to swap
     */
    public void replace_RB(Red_Black_Node oneNode, Red_Black_Node twoNode){
        if(oneNode.parent.isEmpty()){
            this.root = twoNode;
        }
        else if(oneNode == oneNode.parent.left){
            oneNode.parent.left = twoNode;
        }
        else{
            oneNode.parent.right = twoNode;
        }
        twoNode.parent = oneNode.parent;
    }

    /**
     * Contains all the cases of a delete of a red black tree
     *
     * @param z Node to be deleted
     */
    public void delete(Red_Black_Node z){
        Red_Black_Node y = z;
        Red_Black_Node copy_y = y;
        Red_Black_Node x;
        copy_y.nodeColor = y.nodeColor;
        if(z.left.isEmpty()){
            x = z.right;
            replace_RB(z,z.right);
        }
        else if(z.right.isEmpty()){
            x= z.left;
            replace_RB(z,z.left);
        }
        else{
            y = getTheMinimumValue(z.right);
            x = y.right;
            copy_y.nodeColor = y.nodeColor;
            replace_RB(y,y.right);
            replace_RB(z,y);
            y.left = z.left;
            y.left.parent = y;
            y.right = z.right;
            y.right.parent = y;
            y.nodeColor = z.nodeColor;
        }
        if(copy_y.nodeColor == Node_Color.black){
            rearrange(x);
        }
    }

    /**
     * Deletion is not complete, in most of the cases
     * we have to restructure the tree to satisfy
     * the red black tree conditions
     *
     * @param reArrangeNode Node around which the restructure has to be done
     */
    public void rearrange(Red_Black_Node reArrangeNode){
        while(reArrangeNode != this.root && reArrangeNode.nodeColor == Node_Color.black){
            //x is left child
            if(reArrangeNode == reArrangeNode.parent.left){
                Red_Black_Node rightOfRearrange = reArrangeNode.parent.right;
                //when sibling of x is red
                if(rightOfRearrange.nodeColor == Node_Color.red){
                    rightOfRearrange.nodeColor = Node_Color.black;
                    reArrangeNode.parent.nodeColor = Node_Color.red;
                    rotate_left(reArrangeNode.parent);
                    rightOfRearrange = reArrangeNode.parent.right;
                }
                if(rightOfRearrange.left.nodeColor == Node_Color.black  &&
                        rightOfRearrange.right.nodeColor == Node_Color.black){
                    //when sibling of x is black, and both children of sibling are black
                    rightOfRearrange.nodeColor = Node_Color.red;
                    reArrangeNode = reArrangeNode.parent;
                }
                else{
                    //sibling of x is black and left child of sibling  is red and right child is black
                    if(rightOfRearrange.right.nodeColor == Node_Color.black){

                        rightOfRearrange.left.nodeColor = Node_Color.black;
                        rightOfRearrange.nodeColor = Node_Color.red;
                        rotate_right(rightOfRearrange);
                        rightOfRearrange = reArrangeNode.parent.right;
                    }
                    //sibling of x is black and right child is black
                    rightOfRearrange.nodeColor = reArrangeNode.parent.nodeColor;
                    reArrangeNode.parent.nodeColor = Node_Color.black;
                    rightOfRearrange.right.nodeColor = Node_Color.black;
                    rotate_left(reArrangeNode.parent);
                    reArrangeNode = this.root;

                }
            }
            //x is the right child
            else if(reArrangeNode == reArrangeNode.parent.right){
                Red_Black_Node w = reArrangeNode.parent.left;
                if(w.nodeColor == Node_Color.red){
                    w.nodeColor = Node_Color.black;
                    reArrangeNode.parent.nodeColor = Node_Color.red;
                    rotate_right(reArrangeNode.parent);
                    w = reArrangeNode.parent.left;
                }
                if(w.left.nodeColor == Node_Color.black  &&  w.right.nodeColor == Node_Color.black){
                    w.nodeColor = Node_Color.red;
                    reArrangeNode = reArrangeNode.parent;
                }
                else{
                    if(w.left.nodeColor == Node_Color.black){
                        w.right.nodeColor = Node_Color.black;
                        w.nodeColor = Node_Color.red;
                        rotate_left(w);
                        w = reArrangeNode.parent.left;
                    }
                    w.nodeColor = reArrangeNode.parent.nodeColor;
                    reArrangeNode.parent.nodeColor = Node_Color.black;
                    w.left.nodeColor = Node_Color.black;
                    rotate_right(reArrangeNode.parent);
                    reArrangeNode = this.root;
                }

            }

        }
        reArrangeNode.nodeColor = Node_Color.black;
    }
}

	
