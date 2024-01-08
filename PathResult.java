// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.util.List;

/**
 * A class that provides details of a shortest path search.
 */
public class PathResult implements PathResultInterface {
  // declaring the fields
  List<String> pathData; // path data as a list of buildings
  List<Double> timeSegments; // time between segments as a list of doubles
  double totalCost; // total cost as a double

  /**
   * The constructor for the PathResult class.
   * @param pathData - the path data as a list of buildings
   * @param timeSegments - the time between segments as a list of doubles
   * @param totalCost - the total cost as a double
   */
  PathResult(List<String> pathData, List<Double> timeSegments, double totalCost) {
    // initializing the fields
    this.pathData = pathData;
    this.timeSegments = timeSegments;
    this.totalCost = totalCost;
  }

  /**
   * Provide path as a list of buildings.
   *
   * @return a list of buildings that represents the path
   */
  public List<String> getPath() {
    // returning the path data
    return this.pathData;
  }

  /**
   * Provides list of walking times for each path segment.
   *
   * @return a list of walking times
   */
  public List<Double> getTimeSegments() {
    // returning the time segments
    return this.timeSegments;
  }


  /**
   * Provide total path cost (estimated time to walk from start to destination)
   *
   * @return the total walking time
   */
  public double getTotalCost() {
    // returning the total cost
    return this.totalCost;
  }

}
