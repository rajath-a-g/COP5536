import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class defines all the the operations
 * that can be done on a building like insert,
 * Print a Building,print a deleted building
 * Print buildings within a range
 */
public class Building {
    public int buildingID;
    public MinHeap minHeap;
    public RB_tree red_black_tree;
    ArrayList<String> outputToFile;

    /**
     * Default constructor which creates the minimum minHeap,
     * Red black tree
     */
    public Building() {
        this.minHeap = new MinHeap();
        this.red_black_tree = new RB_tree();
        this.outputToFile = new ArrayList<String>();
    }

    /**
     * This method is used to write to
     * the output file after a set of operations
     */
    public void writeOutputtoFile() {
        try {
            BufferedWriter outputBufferedWriter =
                    new BufferedWriter(new FileWriter("output_file.txt"));
            int res = 0;
            while (res < outputToFile.size()) {
                outputBufferedWriter.append(outputToFile.get(res));
                outputBufferedWriter.append('\n');
                res++;
            }
            outputBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method inserts a new building by inserting it into the
     * heap and the red black tree
     *
     * @param buildingID          Identifier of the building
     * @param total_time_to_build total time required to build the building
     * @param totalExecutionTime  total time required to build the building
     */
    public void Insert(int buildingID, long total_time_to_build, long totalExecutionTime) {
        if (red_black_tree.checkIfBuildingPresent(buildingID, red_black_tree.root) == 0) {
            red_black_tree.insert(buildingID, total_time_to_build, totalExecutionTime);
            RB_Node newNode = red_black_tree.Search(buildingID, red_black_tree.root);
            minHeap.insert(total_time_to_build, totalExecutionTime, newNode);
        } else {
            outputToFile.add("Duplicate building number being insert, Aborted construction");
            writeOutputtoFile();
            throw new RuntimeException("Duplicate building number in list");
        }

    }

    /**
     * Print the details of the job which has been asked for.
     *
     * @param buildingID the identifier of the building to be printed
     */
    public void printBuilding(int buildingID) {
        RB_Node printNode = red_black_tree.Search(buildingID, red_black_tree.root);
        if (!printNode.isEmpty()) {
            outputToFile.add("(" + red_black_tree.getBuildingDetails(buildingID) + "," +
					printNode.execution_time + ","
                    + printNode.total_time + ")");
        } else {
            outputToFile.add("(0,0,0)");
        }
    }

    /**
     * Print the details of the job that has completed execution
     *
     * @param buildingID  Identifier of the building
     * @param finish_time time at which the building completed
     */
    public void printDeletedBuilding(int buildingID, long finish_time) {
        outputToFile.add("(" + buildingID + "," + finish_time + ")");
    }

    /**
     * Print all the buildings in the given range of identifiers
     *
     * @param buildingID1 Start of range of building numbers
     * @param buildingID2 End of range of building numbers
     */
    public void printBuilding(int buildingID1, int buildingID2) {
        ArrayList<Integer> res = red_black_tree.getBuildingDetails(buildingID1, buildingID2);
        if ((res.size() != 0)) {
            String s = "";
            int build = 0;
            while (build < res.size()) {
                if (res.get(build) != 0) {
                    RB_Node newNode = red_black_tree.Search(res.get(build), red_black_tree.root);
                    s += "(" + res.get(build) + "," + newNode.execution_time + "," + newNode.total_time + ")";
                    if (build != res.size() - 1) {
                        s += ",";
                    }
                }
                build++;
            }
            char[] charArray = s.toCharArray();
            if (charArray[charArray.length - 1] == ',') {
                s = s.substring(0, charArray.length - 1);
            }
            outputToFile.add(s);
        } else {
            outputToFile.add("(0,0,0)");
        }
    }
}
