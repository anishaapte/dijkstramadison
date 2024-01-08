// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.io.IOException;

/**
 * An interface for the backend with the job of finding graph data and pathfinding.
 */
public interface BackendInterface {
    /*
     * the constructor
     */
    // public Backend(GraphADT graphADT);

 
    /**
     * @param filepath - true if the file was read successfully, false otherwise
     * @return - true if file is valid, false otherwise
     * @throws IOException - if the file cannot be read
     */
    boolean readFile(String filepath) throws IOException;


    /**
     * Finds the shortest path from the start to the destination building.
     *
     * @param src starting building
     * @param dest  destination building
     * @return an instance of interface that provides path details
     */
    PathResultInterface getShortestPath(String src, String dest);


    /**
     * info about dataset - i.e, number of buildings, edges, and total walking time.
     *
     * @return a string that has info about the dataset
     */
    String summaryData();
}



