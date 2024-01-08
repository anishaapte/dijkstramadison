// --== CS400 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.util.List;

/**
 * An interface for a class that provides details of a shortest path search.
 */
public interface PathResultInterface {
    /**
     * will provide path as a list of buildings.
     *
     * @return a list of buildings that represents the path
     */
    List<String> getPath();

    /**
     * provides list of walking times for each path segment.
     *
     * @return a list of walking times
     */
    List<Double> getTimeSegments();


    /**
     * will provide total path cost (estimated time to walk from start to destination)
     *
     * @return the total walking time
     */
    double getTotalCost();
}
