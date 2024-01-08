// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * Backend class.
 */
public class BackendDeveloperTests {

  /**
   * This test checks whether the readFile method in the Backend class will return false when
   * reading an invalid file.
   */
  @Test
  public void testReadFile() {
    // initializing invalid file path
    String invalidFilePath = "campus2.dot";
    // initializing graph and backend
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);

    // checking invalid file
    try {
      boolean invalidFile = backend.readFile(invalidFilePath);
      Assertions.assertFalse(invalidFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This test checks whether the getShortestPath method in the Backend class calculate and store
   * the correct shortest path data.
   */
  @Test
  public void testGetShortestPathData() {
    // initializing valid file path
    String validFilePath = "campus.dot";
    // initializing graph and backend
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);

    // checking valid file
    try {
      boolean validFile = backend.readFile(validFilePath);
      Assertions.assertTrue(validFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // checking if the shortest path stored is correct
    PathResultInterface path =
        backend.getShortestPath("Memorial Union", "Computer Sciences and Statistics");
    Assertions.assertEquals(
        "[Memorial Union, Science Hall, Music Hall, Law Building, X01, " + "Luther Memorial Church, Noland Hall, Meiklejohn House, " + "Computer Sciences and Statistics]",
        path.getPath().toString());
  }

  /**
   * This test checks whether the getShortestPath method in the Backend class calculate and store
   * the correct time segments between buildings of the shortest path.
   */
  @Test
  public void testGetShortestPathTimeSegments() {
    // initializing valid file path
    String validFilePath = "campus.dot";
    // initializing graph and backend
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);

    // checking valid file
    try {
      boolean validFile = backend.readFile(validFilePath);
      Assertions.assertTrue(validFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // checking if the time segments stored are correct
    PathResultInterface path =
        backend.getShortestPath("Memorial Union", "Computer Sciences and Statistics");
    Assertions.assertEquals(
        "[105.8, 202.29999999999998, 157.3, 174.7, 65.5, " + "183.50000000000003, 124.20000000000002, 164.20000000000002]",
        path.getTimeSegments().toString());
  }

  /**
   * This test checks whether the getShortestPath method in the Backend class calculate and store
   * the correct shortest path total cost from start to destination.
   */
  @Test
  public void testGetShortestPathTotalCost() {
    // initializing valid file path
    String validFilePath = "campus.dot";
    // initializing graph and backend
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);

    // checking valid file
    try {
      boolean validFile = backend.readFile(validFilePath);
      Assertions.assertTrue(validFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // checking if the total cost stored is correct
    PathResultInterface path =
        backend.getShortestPath("Memorial Union", "Computer Sciences and Statistics");
    Assertions.assertEquals(1177.5, path.getTotalCost());
  }

  /**
   * This test checks whether the getSummaryData method in the Backend class calculate and return
   * the correct summary data of the dataset.
   */
  @Test
  public void testGetSummaryData() {
    // initializing valid file path
    String validFilePath = "campus.dot";
    // initializing graph and backend
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    Backend backend = new Backend(graph);

    // checking valid file
    try {
      boolean validFile = backend.readFile(validFilePath);
      Assertions.assertTrue(validFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // checking if the summary data is correct
    String summary = backend.summaryData();
    Assertions.assertEquals(summary,
        "\n" + "Number of Buildings: 160\n" + "Number of Paths: 508\n" + "Total Walking Time(Without Duplicate): 76604.99999999985 seconds\n" + "Total Walking Time(With Duplicate): 110675.49999999997 seconds");

  }

  /**
   * This test checks whether the program will display the correct shortest path between two
   * buildings.
   */
  @Test
  public void testGetShortestPathIntegration() {
    // Initialize tester with inputs to display shortest path between two buildings
    TextUITester test = new TextUITester(
        "1\ncampus.dot\n3\nMemorial Union\nComputer Sciences and Statistics\n4\n");

    // Instantiate the graph object
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    // Instantiate the backend object
    Backend backend = new Backend(graph);

    // Instantiate and run the frontend object
    FrontendInterface frontend = new Frontend(backend);

    // Run the frontend
    frontend.startCommandLoop();

    // Getting the output from the tester
    String output = test.checkOutput();

    // check if the frontend output is correct
    boolean correctOutput =
        output.startsWith("Main Menu ----") && output.contains("The shortest path is: ");
    Assertions.assertTrue(correctOutput);

    // check if the backend shortest path is correct
    PathResultInterface path =
        backend.getShortestPath("Memorial Union", "Computer Sciences and Statistics");
    Assertions.assertEquals(
        "[Memorial Union, Science Hall, Music Hall, Law Building, X01, " + "Luther Memorial Church, Noland Hall, Meiklejohn House, " + "Computer Sciences and Statistics]",
        path.getPath().toString());

    // check if the program output is correct
    boolean correctPath = output.contains(path.getPath().toString());
    Assertions.assertTrue(correctPath);
  }

  /**
   * This test checks whether the program will display the correct summary data of the dataset.
   */
  @Test
  public void testShowStatisticIntegration() {
    // Initialize tester with inputs to display dataset summary
    TextUITester test = new TextUITester("1\ncampus.dot\n2\n4\n");

    // creating graph
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    // creating backend
    Backend backend = new Backend(graph);

    // Instantiate and run the frontend object
    FrontendInterface frontend = new Frontend(backend);

    // Run the frontend
    frontend.startCommandLoop();

    // Getting the output from the tester
    String output = test.checkOutput();

    // check if the frontend output is correct
    boolean correctOutput =
        output.startsWith("Main Menu ----") && output.contains("Number of Buildings: ");

    Assertions.assertTrue(correctOutput);

    // checking if the summary data is correct
    String summary = backend.summaryData();
    Assertions.assertEquals(summary,
        "\n" + "Number of Buildings: 160\n" + "Number of Paths: 508\n" + "Total Walking Time(Without Duplicate): 76604.99999999985 seconds\n" + "Total Walking Time(With Duplicate): 110675.49999999997 seconds");

    // check if the program output is correct
    boolean correctSummary = output.contains("160") && output.contains("508") && output.contains(
        "76604.99999999985") && output.contains("110675.49999999997");
    Assertions.assertTrue(correctSummary);
  }

  /**
   * This test checks frontend method to properly handle user input to show the dataset summary.
   */
  @Test
  public void testShowStatisticsDataForPartner() {
    TextUITester tester = new TextUITester("1\ncampus.dot\n2\n4\n");
    BackendInterface backend = new BackendPlaceholder();
    FrontendInterface frontend = new Frontend(backend);
    frontend.startCommandLoop();
    String output = tester.checkOutput();
    boolean correctOutput = output.startsWith("Main Menu ----") && output.contains(
        "Number of Buildings: ") && output.contains("Number of Paths: ") && output.contains(
        "Total Walking Time(Without Duplicate): ") && output.contains(
        "Total Walking Time(With Duplicate): ");
  }

  /**
   * This test checks whether frontend method correctly handle user input to show the shortest path
   * between two buildings.
   */
  @Test
  public void testGetShortestPathForPartner() {
    TextUITester tester = new TextUITester(
        "1\ncampus.dot\n3\nMemorial Union\nComputer Sciences and Statistics\n4\n");
    BackendInterface backend = new BackendPlaceholder();
    FrontendInterface frontend = new Frontend(backend);
    frontend.startCommandLoop();
    String output = tester.checkOutput();
    boolean correctOutput = output.startsWith("Main Menu ----") && output.contains(
        "Please enter start point:") && output.contains(
        "Please enter end point:") && output.contains("The shortest path is: ") && output.contains(
        "The time segments are: ") && output.contains("The total time is: ");
  }
}
