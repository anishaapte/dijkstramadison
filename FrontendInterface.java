// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -


/**
 * This interface implements the functionality for the frontend of the UW Path Finder. 
 * It interacts with the backend to load a data set, display statistics, and calculate 
 * shortest path
 * @author anishaapte
 *
 */
public interface FrontendInterface {
    /**
     * Constructor :
     * public IndividualFrontendInterface(IndividualBackendInterface backend, Scanner scnr);
     *
     *
     */

    /**
     * This method displays the following menu items :
     * 1) Load data file 
     * 2) Show statistics of the dataset
     * 3) Calculate the shortest path of specified start and destination building
     * 4) Exit
     */
    public void displayMainMenu();

    /**
     * This method starts the main command loop of the frontend. 
     * Displays the 4 menu items until the user chooses the exit menu. 
     * This method ensures that the user properly enters numbers 1-4 in
     * the right sequence. For example, if the user enters 5, then error
     * message will be printed. 
     */
    public void startCommandLoop();

    /**
     * This method prompts the user to enter a data file and calls backend
     * to properly load the file. If backend sends an exception, frontend will 
     * not throw, but will print the error message and ask to load another file. 
     *
     * If user has already loaded the file, but clicks 1 again, then user will
     * be informed that <fileName> is the current dataset, and they should enter another
     * fileName or can hit enter to keep their existing dataset
     */
    public void loadFile();

    /**
     * This method will display the number of buildings, number of edges, 
     * and the total walking time in the graph. This method will only run if 
     * the user has entered a data file. 
     *
     * Also, if user chooses to show statistics before loading the file, then 
     * this method will print an error message saying that the file has not been loaded
     */
    public void showStatistics();

    /**
     * This method will prompt the user for a start and destination building, and will
     * list the shortest path between those buildings, including all buildings on the way,
     * the estimated walking time for each segment, and the total time it takes to walk 
     * the path. 
     *
     * Also, if user chooses to calculate the shortest path before loading the file, then 
     * this method will print an error message saying that the file has not been loaded
     */
    public void calcShortestPath();

    /**
     * This method will exit the app
     */
    public void endApp();

}
