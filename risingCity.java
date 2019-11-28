import java.util.*;
import java.io.*;

class risingCity {
    static HeapNode n = null;

    public static void main(String args[]) throws IOException {
        BufferedReader readerForEachLine = new BufferedReader(new FileReader(args[0]));
        //finding total time for execution for the counter to run
        BufferedReader readerForMaxTime = new BufferedReader(new FileReader(args[0]));
        //String for matching to calculate max time
        String maxTimeString;
        //Variable to hold the max time, long to hold large numbers
        long total_max_time = 0;
        //List to check if pb exists or not
        List<Long> listPBDelete = new ArrayList<>();
        //List which contains the corresponding pb details
        List<String> listPBDeleteStr = new ArrayList<>();
        int fiveDayCounter = 0, isConstruction = 0;
        //Starting global counter in loop
        while ((maxTimeString = readerForMaxTime.readLine()) != null) {
            if (maxTimeString.split(" ")[1].split("\\(")[0].equalsIgnoreCase("Insert"))
                total_max_time += Long.parseLong(maxTimeString.split(",")[1].split("\\)")[0])
                        + Long.parseLong(maxTimeString.split(":")[0]);
        }
        readerForMaxTime.close();
        //parsing the input file to recognize commands given by user
        String newLineFromInput = readerForEachLine.readLine();
        int buildingID1, buildingID2;
        Building building = new Building();
        long day_counter = 0;
        Long total_time = 0l, timeExecuted = 0l;
        long arrival_day = Long.parseLong(newLineFromInput.split(":")[0]);
        String subStr = newLineFromInput.split(" ")[1];
        String inputFileStr = (subStr.split("\\("))[0];
        String arg = (subStr.split("\\("))[1];
        //long start = System.nanoTime();
        boolean isBuildingCompleted = false;
        for (day_counter = 0; day_counter <= total_max_time; day_counter++) {
            try {
                if (arrival_day == day_counter) {
                    if (inputFileStr.equalsIgnoreCase("Insert")) {
                        buildingID1 = Integer.parseInt(arg.split("\\)")[0].split(",")[0]);
                        total_time = Long.parseLong(arg.split("\\)")[0].split(",")[1]);
                        building.Insert(buildingID1, total_time, timeExecuted);
                    }
                    if (inputFileStr.equalsIgnoreCase("PrintBuilding") && listPBDelete.contains(arrival_day)) {
                        if (arg.split("\\)")[0].split(",").length == 1) {
                            buildingID1 = Integer.parseInt(arg.split("\\)")[0].split(",")[0]);
                            building.printBuilding(buildingID1);
                        }
                        if (arg.split("\\)")[0].split(",").length == 2) {
                            buildingID1 = Integer.parseInt(arg.split("\\)")[0].split(",")[0]);
                            buildingID2 = Integer.parseInt(arg.split("\\)")[0].split(",")[1]);
                            building.printBuilding(buildingID1, buildingID2);
                        }
                    }
                    if ((newLineFromInput = readerForEachLine.readLine()) != null) {
                        arrival_day = Integer.parseInt(newLineFromInput.split(":")[0]);
                        subStr = newLineFromInput.split(" ")[1];
                        inputFileStr = (subStr.split("\\("))[0];
                        if (inputFileStr.equalsIgnoreCase("PrintBuilding")) {
                            listPBDelete.add(arrival_day);
                            listPBDeleteStr.add("(" + arg);
                        }
                        arg = (subStr.split("\\("))[1];
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
                    n.rb_Node.execution_time += 1;
                    //check if the building has completed construction
                    //and then print all the builldings and then delete it
                    //and then print the time it has taken to finish construction
                    //inserting the building back to heap to restructure if the building has
                    //not completed execution
                    if (n.rb_Node.execution_time == n.total_time) {
                        if (listPBDelete.contains(day_counter + 1)) {
                            int index = listPBDelete.indexOf(day_counter + 1);
                            //using index to print that building range and then deleting the building

                            if (listPBDeleteStr.get(index).split("\\)")[0].split(",").length == 2) {
                                //Check if there is a Print building at the same time
                                buildingID1 = Integer.parseInt(arg.split("\\)")[0].split(",")[0]);
                                    buildingID2 = Integer.parseInt(arg.split("\\)")[0].split(",")[1]);
                                    //If yes, then remove it from list so that it is not printed again
                                    listPBDelete.remove(index);
                                    listPBDeleteStr.remove(index);
                                    building.printBuilding(buildingID1, buildingID2);
                                } else {
                                buildingID1 = Integer.parseInt(arg.split("\\)")[0].split(",")[0]);
                                listPBDelete.remove(index);
                                listPBDeleteStr.remove(index);
                                building.printBuilding(buildingID1);
                            }
                        }
                        isBuildingCompleted = true;
                        //Print the deleted building with the total time
                        building.printDeletedBuilding(n.rb_Node.buildingID, (day_counter + 1));
                        //Print the same to output file
                        building.writeOutputtoFile();
                        //delete the finished the node from the RB tree
                        building.red_black_tree.delete(n.rb_Node);
                        //get the updated minimum from heap
                        if(fiveDayCounter != 5) {
                            fiveDayCounter = 0;
                            isConstruction = 0;
                        } }else {
                            isBuildingCompleted = false;
                    }
                    //Check if the building has been constructed for
                    // five days if yes insert it back to the heap
                    if (fiveDayCounter == 5) {
                        fiveDayCounter = 0;
                        if(!isBuildingCompleted) {
                            building.minHeap.insert(n.total_time, n.constructionDoneTime, n.rb_Node);
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
        readerForEachLine.close();
    }
}
