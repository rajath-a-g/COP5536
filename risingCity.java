import java.util.*;
import java.io.*;

/**
 * This class handles all the core logic of construction of the
 * building. Refer the report of for the flow of the construction
 */
class risingCity {
    private static final String REGEX_BRACKET = "\\)";
    private static final String REGEX_COMMA = ",";
    private static final String PRINT_BUILDING = "PrintBuilding";
    private static final String REGEX_OPEN_BRACKET = "\\(";
    private static final String REGEX_SPACE = " ";
    private static final String REGEX_COLON = ":";
    static Heap_Node n = null;

    public static void main(String args[]) throws IOException {
        BufferedReader readerForEachLine = new BufferedReader(new FileReader(args[0]));
        //finding total time for execution for the counter to run
        BufferedReader readerForMaxTime = new BufferedReader(new FileReader(args[0]));
        //String for matching to calculate max time
        String maxTimeString;
        //Variable to hold the max time, long to hold large numbers
        long total_max_time = 0;
        //List to check if pb exists or not
        List<Long> printBuildingList = new ArrayList<>();
        //List which contains the corresponding pb details
        List<String> printBuildingStringList = new ArrayList<>();
        int fiveDayCounter = 0, isConstruction = 0;
        //Starting global counter in loop
        while ((maxTimeString = readerForMaxTime.readLine()) != null) {
            if (maxTimeString.split(REGEX_SPACE)[1]
                    .split(REGEX_OPEN_BRACKET)[0].equalsIgnoreCase("Insert"))
                total_max_time += Long.parseLong(maxTimeString.split(REGEX_COMMA)[1].split(REGEX_BRACKET)[0])
                        + Long.parseLong(maxTimeString.split(REGEX_COLON)[0]);
        }
        readerForMaxTime.close();
        //parsing the input file to recognize commands given by user
        String newLineFromInput = readerForEachLine.readLine();
        int buildingID1, buildingID2;
        Building building = new Building();
        long day_counter = 0;
        Long total_time = 0l, timeExecuted = 0l;
        long arrival_day = Long.parseLong(newLineFromInput.split(REGEX_COLON)[0]);
        String subStr = newLineFromInput.split(REGEX_SPACE)[1];
        String inputFileStr = (subStr.split(REGEX_OPEN_BRACKET))[0];
        String argument = (subStr.split(REGEX_OPEN_BRACKET))[1];
        //long start = System.nanoTime();
        boolean isBuildingCompleted = false;
        for (day_counter = 0; day_counter <= total_max_time; day_counter++) {
            try {
                if (arrival_day == day_counter) {
                    if (inputFileStr.equalsIgnoreCase("Insert")) {
                        buildingID1 = Integer.parseInt(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[0]);
                        total_time = Long.parseLong(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[1]);
                        building.Insert(buildingID1, total_time, timeExecuted);
                    }
                    if (inputFileStr.equalsIgnoreCase("PrintBuilding") &&
                            printBuildingList.contains(arrival_day)) {
                        if (argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA).length == 1) {
                            buildingID1 = Integer.parseInt(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[0]);
                            building.printBuilding(buildingID1);
                        }
                        if (argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA).length == 2) {
                            buildingID1 = Integer.parseInt(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[0]);
                            buildingID2 = Integer.parseInt(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[1]);
                            building.printBuilding(buildingID1, buildingID2);
                        }
                    }
                    if ((newLineFromInput = readerForEachLine.readLine()) != null) {
                        arrival_day = Integer.parseInt(newLineFromInput.split(REGEX_COLON)[0]);
                        subStr = newLineFromInput.split(REGEX_SPACE)[1];
                        inputFileStr = (subStr.split(REGEX_OPEN_BRACKET))[0];
                        argument = (subStr.split(REGEX_OPEN_BRACKET))[1];
                        if (inputFileStr.equalsIgnoreCase(PRINT_BUILDING)) {
                            printBuildingList.add(arrival_day);
                            printBuildingStringList.add("(" + argument);
                        }
                    }
                    //after all reads write to file
                    building.writeOutputtoFile();
                }
                //if no construction is going on then schedule one.
                if (isConstruction == 0) {
                    if (building.minHeap.isEmpty()) {
                        //continue if there is no construction to
                        // be done to check if there is a construction next
                        continue;
                    } else {
                        //extract th minimum executed time from the heap
                        // and run the construction for five days
                        n = building.minHeap.extractMin();
                        isConstruction = 1;
                        fiveDayCounter = 0;
                        isBuildingCompleted = false;
                    }
                }
                //scheduling if there is no building is being built
                if (isConstruction == 1) {
                    fiveDayCounter++;
                    n.constructionDoneTime += 1;
                    n.redBlack_Node.construction_time += 1;
                    //check if the building has completed construction
                    //and then print all the builldings and then delete it
                    //and then print the time it has taken to finish construction
                    //inserting the building back to heap to restructure if the building has
                    //not completed execution
                    if (n.redBlack_Node.construction_time == n.total_time) {
                        if (printBuildingList.contains(day_counter + 1)) {
                            int index = printBuildingList.indexOf(day_counter + 1);
                            //using index to print that building range and then deleting the building
                            if (printBuildingStringList.get(index).split(REGEX_BRACKET)[0]
                                    .split(REGEX_COMMA).length == 2) {
                                //Check if there is a Print building at the same time
                                buildingID1 = Integer.parseInt(argument
                                        .split(REGEX_BRACKET)[0].split(REGEX_COMMA)[0]);
                                buildingID2 = Integer.parseInt(argument
                                        .split(REGEX_BRACKET)[0].split(REGEX_COMMA)[1]);
                                //If yes, then remove it from list so that it is not printed again
                                printBuildingList.remove(index);
                                printBuildingStringList.remove(index);
                                building.printBuilding(buildingID1, buildingID2);
                            } else {
                                buildingID1 = Integer.parseInt(argument.split(REGEX_BRACKET)[0].split(REGEX_COMMA)[0]);
                                printBuildingList.remove(index);
                                printBuildingStringList.remove(index);
                                building.printBuilding(buildingID1);
                            }
                        }
                        isBuildingCompleted = true;
                        //Print the deleted building with the total time
                        building.printDeletedBuilding(n.redBlack_Node.buildingID, (day_counter + 1));
                        //Print the same to output file
                        building.writeOutputtoFile();
                        //delete the finished the node from the RB tree
                        building.red_black_tree.delete(n.redBlack_Node);
                        //get the updated minimum from heap
                        if (fiveDayCounter != 5) {
                            fiveDayCounter = 0;
                            isConstruction = 0;
                        }
                    } else {
                        isBuildingCompleted = false;
                    }
                    //Check if the building has been constructed for
                    // five days if yes insert it back to the heap
                    if (fiveDayCounter == 5) {
                        fiveDayCounter = 0;
                        if (!isBuildingCompleted) {
                            building.minHeap.insert(n.total_time, n.constructionDoneTime, n.redBlack_Node);
                        }
                        isConstruction = 0;
                    }
                }
            } catch (Exception e) {
                //print if any exception occurs
                e.printStackTrace();
            }
        }
        //Commented code to check for time taken in seconds
        //long end = System.nanoTime();
        //System.out.println("TimeElapsed:" + ((double) (end - start) / 1000000000));
        //Close the reader after use
        readerForEachLine.close();
    }
}
