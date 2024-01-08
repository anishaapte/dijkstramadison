import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class contains test methods for the frontend with implementing the
 * backend
 * @author anishaapte
 *
 */
public class FrontendDeveloperTests {
    
    /**
     * This method will test the displayMainMeny() method. The test method 
     * is checking if the main menu is being displayed correctly when 
     * the command loop has started. It will make sure that the 4 menu items
     * are being displayed until the user has chosen to exit the app by clicking 
     * on the number 4 key. 
     */
    @Test
    public void testDisplayMainMenu()  {
        
        //using the tester until the user will input the number 4, which 
        //will exit out of the app
        TextUITester tester = new TextUITester("""
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //checking that the main menu is being displayed
        Assertions.assertTrue(output.startsWith("Main Menu ----") 
                && output.contains("1. Load a data file") 
                && output.contains("2. Show statistics")
                && output.contains("3. Calculate Shortest Path")
                && output.contains("4. Exit the App") 
                && output.contains("Enter a number 1-4:"),
                "Menu displayed successfully");
                
    }
    /**
     * This test method will make sure that the user knows that they have inputed a valid data file. 
     * The tester will use a valid data file, so this tester method will make sure that 
     * the sentence, "File loaded successfully" will be outputed by the program
     */
    @Test
    public void testLoadValidFile() {
        
        //using the scenario that the user inputs a valid data file 
        //the user will have to enter 1, the valid data file, and then 4 to exit 
        TextUITester tester = new TextUITester("""
                 1
                 data/map.csv
                 4          
                """);
       
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that my program outputs that the valid file was loaded successfully
        //also making sure that the user is prompted to enter a data file after clicking 
        //the first option, which is intended for the user to input a valid file 
        Assertions.assertTrue(output.contains("Enter a data file:") 
                && output.contains("File loaded successfully"), "Valid file tested");

    }
    /**
     * This test method will make sure that the user knows they have inputed an invalid data file. 
     * The tester will take an invalid data file, and will make sure that the user knows they have 
     * inputed a bad file. 
     */
    @Test
    public void testLoadInvalidFile() {
        //using the scenario that the user inputs an invalid data file 
        //the user will have to enter 1, the invalid data file, and then 4 to exit 
        TextUITester tester = new TextUITester("""
                1
                data/badMap.csv
                4          
               """);        
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that my program outputs that the invalid file was not loaded successfully
        //also making sure that the user is prompted to enter a data file after clicking 
        //the first option, which is intended for the user to input a valid file 
        Assertions.assertTrue(output.contains("Enter a data file:") 
                && output.contains("File failed to load, please enter a valid file next time"), "Invalid file tested");
        
    }
    /**
     * This test method is used to ensure the correct output for the case where the user 
     * has chosen the option to show the statistics without entering a valid data file first. 
     * The tester will make sure that the user knows that they need to enter a valid data file 
     * first before selecting this option 
     */
    @Test
    public void testLoadStatsWithoutFile() {
        
        //using the scenario that the user has only clicked the number 2 option : show statistics
        TextUITester tester = new TextUITester("""
                2
                4           
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output states that the user has to enter a 
        //valid data file first before selecting any other option
        Assertions.assertTrue(output.contains("No data file was loaded, please select 1 to load a valid file"), "No file stat tested");
        
    }
    /**
     * This tester method will make sure that the correct output is given when the user 
     * has inputed a valid data file and selects the 2nd option. The tester will 
     * will make sure that the number of edges, total walking time, and number of buildings
     * has been outputted by my program 
     */
    @Test
    public void testLoadStatsWithFile() {
        
        //testing the scenario where the user has successfully loaded a data file 
        //and is asking to see the statistics of the data file
        TextUITester tester = new TextUITester("""
                1
                data/map.csv
                2
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output contains the number of buildings, number of edges, 
        //and total walking time of the data file given by the user
        Assertions.assertTrue(output.contains("Number of buildings: 10") 
                && output.contains("Number of paths: 15") 
                && output.contains("Total distance of paths: 65.0 seconds"), 
                "Valid file stat tested");     
    }
    
    /**
     * This tester method will make sure that the correct output is given when the user
     * has inputed a valid data file and selects the option to chose the shortest path 
     * between a start and destination building. The tester method will show the time
     * between each building along the path, along with the total time it would take to reach
     * the destination building. 
     */
    @Test
    public void testShortestPathValid() {
        
        //testing the scenario where the user has inputed a valid data file 
        //and has chosen the 3rd option : calculate shortest path 
        TextUITester tester = new TextUITester("""
                1
                data/map.csv
                3
                D
                I
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output of my program shows the time it takes to get to each 
        //building on the path to the final destination, along with the total time it will
        //take to reach the final destination
        Assertions.assertTrue(output.contains("Please enter start point:") 
                && output.contains("Please enter end point:") 
                && output.contains("The shortest path is: [D, A, H, I]")
                && output.contains("The time segments are: [7.0, 8.0, 2.0]")
                && output.contains("The total time is: 17.0"),
                "Reachable path tested");   
        
    }
    /**
     * This tester method will make sure that the correct output is given when the user
     * has loaded a valid data file and has chosen the option to calculate the shortest path
     * between buildings that have no reachable path. The tester will make sure that the user 
     * knows that the path is not reachable between the inputed start and destination building 
     */
    @Test
    public void testShortestPathNotReachable() {
       
        //testing the scenario where the user has entered a valid data file and has chosen
        //option 3: calculate shortest path
        //the user has also entered buildings where there is no reachable path 
        TextUITester tester = new TextUITester("""
                1
                data/map.csv
                3
                F
                D
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output says that there is no reachable path between the
        //start and destination buildings
        Assertions.assertTrue(output.contains("Please enter start point") 
                && output.contains("Please enter end point:")
                && output.contains("ERROR in computing path no path to the target node"));                 
    }
    /**
     * This tester method will make sure that the correct output is displayed when the user 
     * has loaded a valid data file and chooses option 3 to calculate the shortest path. However, 
     * one of the buildings that the user has entered as start or destination building is not a valid building
     * in the file. 
     */
    @Test
    public void testShortestPathInvalidNode() {
        
        //testing the scenario where the user has entered a valid data file and has 
        //selected option 3 : calculate shortest path. However, the destination building
        //that the user has entered is invalid
        TextUITester tester = new TextUITester("""
                1
                data/map.csv
                3
                X
                Y
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output says that the end point does not exist as the user has entered
        //an invalid destination point
        Assertions.assertTrue(output.contains("Please enter start point") 
                && output.contains("Please enter end point:")
                && output.contains("ERROR in computing path start node is not in graph"));                 
    }
    /**
     * This tester method will make sure that the correct output is displayed when the user 
     * has loaded a valid data file and chooses option 3 to calculate the shortest path. However, 
     * the user has entered the same buildings for the start and destination point. 
     */
    @Test
    public void testShortestPathSameNode() {
        
        //testing the scenario where the user has entered a valid data file and has 
        //selected option 3 : calculate shortest path. However, the user has entered 
        //the same start and destination point
        TextUITester tester = new TextUITester("""
                1
                data/map.csv
                3
                A
                A
                4
                """);
        BackendInterface backend = new BackendPlaceholder();
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output says the walking time between the buildings is 0 minutes, 
        //as the user has entered the same building. Will also make sure that the total walking 
        //time given is 0 minutes
        Assertions.assertTrue(output.contains("Please enter start point:") 
                && output.contains("Please enter end point:") 
                && output.contains("The shortest path is: [A]")
                && output.contains("The time segments are: []")
                && output.contains("The total time is: 0.0"),
                "Reachable path tested");   
        
        
    }
    
    /**
     * Tester method for the integration with the backend. This tester
     * method is testing a valid file with gathering the summary data
     */
    @Test
    public void integrationSummaryData() {
        //testing the scenario where the user has successfully loaded a data file 
        //and is asking to see the statistics of the data file
        TextUITester tester = new TextUITester("""
                1
                campus.dot
                2
                4
                """);
        //using the backend implementation
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output contains the number of buildings, number of edges, 
        //and total walking time of the data file given by the user
        Assertions.assertTrue(output.contains("Number of Buildings: 160") 
                && output.contains("Number of Paths: 508") 
                && output.contains("Total Walking Time(Without Duplicate): 76604.99999999985 seconds"),
                "Valid file stat tested");     
        
        
    }
    
    /**
     * Tester method for the integration with the backend. This tester
     * method is testing a valid file with finding the shortest path
     */
    @Test 
    public void integrationShortestPathValid() {
        //using a valid file - campus.dot
        TextUITester tester = new TextUITester("""
                1
                campus.dot
                3
                Memorial Union
                1410 Engineering Dr
                4
                """);
        //using the backend implementation
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output of my program shows the time it takes to get to each 
        //building on the path to the final destination, along with the total time it will
        //take to reach the final destination
        Assertions.assertTrue(output.contains("Please enter start point:") 
                && output.contains("Please enter end point:") 
                && output.contains("The shortest path is: [Memorial Union, Science Hall, Music Hall, Law Building, "
                        + "Lathrop Hall, Thomas C. Chamberlin Hall, Medical Sciences Center, Wisconsin Institute for "
                        + "Discovery, 1410 Engineering Dr]")
                && output.contains("The time segments are: [105.8, 202.29999999999998, 157.3, 147.4, 192.29999999999998, "
                        + "188.30000000000004, 209.79999999999998, 118.89999999999999]")
                && output.contains("The total time is: 1322.1000000000001"),
                "Reachable path tested");          
    }
    
    /**
     * This test method will test the integration with the backend
     * when an end node that is not in the text file is entered 
     * as a destination point. 
     */
    @Test 
    public void integrationShortestPathInValid() {
        //using a valid file - campus.dot
        TextUITester tester = new TextUITester("""
                1
                campus.dot
                3
                Memorial Union
                Zen Gardens Way
                4
                """);
        //using the backend implementation
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that the output of my program shows the time it takes to get to each 
        //building on the path to the final destination, along with the total time it will
        //take to reach the final destination
        Assertions.assertTrue(output.contains("Please enter start point:") 
                && output.contains("Please enter end point:") 
                && output.contains("ERROR in computing path end node is not in the graph"),               
                "Not reachable path tested");          
    }
    /**
     * This test method tests the integration of loading an invalid file.
     * The test method should be returning an error message as the backend 
     * should be returning false or throwing an exception. 
     */
    @Test
    public void integrationLoadInvalidFile() {
        //using the scenario that the user inputs an invalid data file 
        //the user will have to enter 1, the invalid data file, and then 4 to exit 
        TextUITester tester = new TextUITester("""
                1
                abc.dot
                4          
               """);        
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);        
        FrontendInterface frontend = new Frontend(backend);
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //making sure that my program outputs that the invalid file was not loaded successfully
        //also making sure that the user is prompted to enter a data file after clicking 
        //the first option, which is intended for the user to input a valid file 
        Assertions.assertTrue(output.contains("Enter a data file:") 
                && output.contains("File failed to load, please enter a valid file next time"), "Invalid file tested");
        
    }
    

}
