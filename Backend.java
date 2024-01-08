// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class that implements the backend interface. This class is responsible for reading the
 * dataset, finding the shortest path, and providing info about the dataset.
 */
public class Backend implements BackendInterface {

  // declaring sum fields for the total time (including dupe edges)
  private double sum = 0;

  // arraylist to store list of nodes
  ArrayList<String> nodes = new ArrayList<>();
  // graph used in backend
  GraphADT graph;

  /**
   * The constructor for the backend class. It takes a graph as a parameter.
   *
   * @param graph - the graph to be used in the backend.
   */
  Backend(GraphADT graph) {
    this.graph = graph;
  }

  /**
   * Reads graph data from a file then creates a graph.
   * @param filepath the path to the DOT file containing the graph data
   * @return true if the file was read successfully, false otherwise
   * @throws IOException if the file cannot be read
   */
  public boolean readFile(String filepath) throws IOException{

    // variables to store the nodes, edges data in the file
    String start = null;
    String end = null;
    double time = 0;

    // reading the file
    try {
      File file = new File(filepath);
      // scanner to read the file
      Scanner scanner = new Scanner(file);
      // skipping the first line
      scanner.nextLine();
      // reading the file line by line
      while (scanner.hasNextLine()) { // while there's a line to read
        // reading the line
        String data = scanner.nextLine();
        // parsing the data
        Pattern pattern = Pattern.compile("\\\"(.*?)\\\"|\\[seconds=(.*?)\\]");
        Matcher matching = pattern.matcher(data);

        // getting the start, end, and time
        if (matching.find()) {
          start = matching.group(1);
        }

        if (matching.find()) {
          end = matching.group(1);
        }

        if (matching.find()) {
          time = Double.parseDouble(matching.group(2));
          sum += time;
        }

        // adding the data to the graph
        graph.insertNode(start);
        graph.insertNode(end);

        // adding the edges to the graph
        graph.insertEdge(start, end, time);
        graph.insertEdge(end, start, time);

        // adding the nodes to the nodes list
        if (!nodes.contains(start + "--" + end) && !nodes.contains(end + "--" + start)) {
          nodes.add(start + "--" + end);
          nodes.add(end + "--" + start);
        }
      }
      // closing the scanner
      scanner.close();
      // catching the exception
    } catch (IOException e) {
      // printing the error message
      System.out.println(e.getMessage());
      return false; // returning false if failed to read the file
    }

    return true;
  }


  /**
   * Finds the shortest path from the start to the destination building and stores the path data,
   * the time between segments, and the total time in an instance of the PathResult class.
   *
   * @param src  starting building
   * @param dest destination building
   * @return an instance of interface that provides path details
   */
  public PathResultInterface getShortestPath(String src, String dest) {
    // getting the path data and time
    List<String> pathData = graph.shortestPathData(src, dest);
    double time = graph.shortestPathCost(src, dest);

    // getting time between segments
    List<Double> timeSegments = new LinkedList<>();

    // looping through the path data to get the time between segments
    for (int i = 0; i < pathData.size() - 1; i++) {
      timeSegments.add(graph.shortestPathCost(pathData.get(i), pathData.get(i + 1)));
    }

    // returning the path result
    return new PathResult(pathData, timeSegments, time);
  }


  /**
   * Info about dataset - i.e, number of buildings, edges, and total walking time.
   *
   * @return a string that has info about the dataset
   */
  public String summaryData() {
    // getting the number of buildings or nodes
    int numNodes = graph.getNodeCount();
    int numEdges = graph.getEdgeCount() / 2;
    double totalWeight = 0;

    // looping through the nodes to get the total weight
    for (String node : nodes) {
      String[] nodeData = node.split("--");
      totalWeight = totalWeight + (Double) graph.getEdge(nodeData[0], nodeData[1]);
    }

    totalWeight = totalWeight / 2;

    // returning the string with the info about the dataset
    return "\nNumber of Buildings: " + numNodes + "\nNumber of Paths: " + numEdges
        + "\nTotal Walking Time(Without Duplicate): " + totalWeight + " seconds\nTotal Walking Time(With Duplicate): " + sum + " seconds";
  }
}
