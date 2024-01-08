
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class will implement the methods in the frontend interface 
 * and will properly output the data, taking into consideration the user
 * input and the data file given by the user
 * @author anishaapte
 *
 */
public class Frontend implements FrontendInterface {
    BackendInterface backend;
    Scanner scnr;
    boolean isLoaded = false;
    
    /**
     * Constructor for the frontend
     * @param backend - using the backend implementation
     */
    public Frontend(BackendInterface backend) {
        this.backend = backend;
        this.scnr = new Scanner(System.in); 
    }

    /**
     * Method for creating the main menu. The main menu will contain numbers 1-4
     * 1 : Load data file
     * 2 : Show statistics
     * 3 : Calculate Shortest Path
     * 4 : Exit the App
     */
    @Override
    public void displayMainMenu() {
        System.out.println("Main Menu ----");
        System.out.println("1. Load a data file");
        System.out.println("2. Show statistics");
        System.out.println("3. Calculate Shortest Path");
        System.out.println("4. Exit the App");
        System.out.println();
        System.out.print("Enter a number 1-4: ");

    }

    /**
     * This method will start the command look, which is responsible for displaying the 
     * main menu and taking into consideration what the user has inputed and call the 
     * correct method accordingly. 
     */
    @Override
    public void startCommandLoop() {
        while(true) {
            this.displayMainMenu();
            //capturing what the user has inputed
            String userInput = scnr.nextLine().trim();
            if (userInput.equals("1")) {
                //calling loadFile() method if user inputs 1
                this.loadFile();
            }
            else if(userInput.equals("2")) {
                //calling showStatistics() method if user inputs 2
                this.showStatistics();
            }
            else if (userInput.equals("3")) {
                //calling calcShortestPath() if user inputs 3
                this.calcShortestPath();
            }
            else if (userInput.equals("4")) {
                //calling endApp() method if user inputs 4
                this.endApp();
                break; 
            }           
            else {
                //if the user has entered a something that is not 1-4, error message will be printed
                System.out.println();
                System.out.println("ERROR - Invalid input, please enter a number 1-4");
                System.out.println();
            }         
        }

    }

    @Override
    public void loadFile() {
        System.out.print("Enter a data file:");
        //capturing the fileName that the user has inputed 
        String fileName = scnr.nextLine().trim();
        //checking for empty file
        if (fileName.equals("")) {
            System.out.println();
            System.out.println("File name cannot be empty");
            System.out.println();
        }
        else {
            //trying for the file to be a valid fileName
            //if not, exception will be caught and error message will be printed
           try {
               isLoaded = backend.readFile(fileName);
               System.out.println();
               if (isLoaded) {
                   System.out.println("File loaded successfully");
               }
               else {
                   System.out.println("File failed to load, please enter a valid file next time");
               }
               System.out.println();
           }
           catch(IOException e) {
               System.out.println();
               System.out.println("File failed to load, please enter a valid file next time");
               System.out.println();
           }
        }
        
    }

    /**
     * This method will output the statistics given by the file. The statistics 
     * include the number of buildings, number of edges, and total walking time
     */
    @Override
    public void showStatistics() {
        //making sure that the file has been properly loaded
        if (isLoaded) {
            //capturing the statistics given by backend
            String showStats = backend.summaryData();
            System.out.println(showStats);
            System.out.println();
        }
        else {
            //reaches this part if the file has not been loaded properly, 
            //main menu will be displayed again to load file 
            System.out.println();
            System.out.println("No data file was loaded, please select 1 to load a valid file");                
            System.out.println();
        }

    }

    /**
     * This method will calculate the shortest path of the desired start and destination 
     * points entered by the user. The output will include the shortest path to the 
     * start and destination buildings, the time segments, and the total walking time
     */
    @Override
    public void calcShortestPath() {
        //checking to see if file has loaded properly
        //will go into this if statement if file has not been loaded properly
        //error message will be printed, and main menu will be displayed 
        if (!isLoaded) {
            System.out.println();
            System.out.println("No data file was loaded, please select 1 to load a valid file");                
            System.out.println();
            return;
        }
        try {
            //telling the user to enter a start and destination point
            //also making sure that the user entered a value and not an empty point
            System.out.print("Please enter start point:");
            String startPoint = scnr.nextLine().trim();
            if (startPoint.equals("")) {
                System.out.println("Please enter a non-empty start point");
            }
            System.out.print("Please enter end point:");
            String destination = scnr.nextLine().trim();
            if(destination.equals("")) {
                System.out.println("Please enter a non-empty destination point ");
            }
            //capturing the results of the PathResult outputted by the getShortestPath() method on the backend
            PathResultInterface shortestPath = backend.getShortestPath(startPoint, destination);
            System.out.println();
            System.out.println("The shortest path is: " + shortestPath.getPath());
            System.out.println("The time segments are: " + shortestPath.getTimeSegments());
            System.out.println("The total time is: " + shortestPath.getTotalCost());
            System.out.println();

            
        }
        //catching an NoSuchElementException if the path does not exist
        //also printing an error message
        catch(NoSuchElementException e) {
            System.out.println();
            System.out.println("ERROR in computing path " + e.getMessage());
            System.out.println();
            
        }

    }

    /**
     * This method will print a statement letting the user know the add will end
     */
    @Override
    public void endApp() {
        //printing out statement to exit the app
        System.out.println();
        System.out.println("Exiting app, goodbye");

    }
    public static void main(String[] args) {
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph);
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
    }

}
