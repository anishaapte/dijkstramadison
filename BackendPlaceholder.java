import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is a placeholder class of the backend implementation, that will be used
 * in the constructor when creating the frontend. The implementation of these methods 
 * will be hardcoded until the integration phase when I have access to the actual 
 * implementation given by the backend
 * @author anishaapte
 *
 */
public class BackendPlaceholder implements BackendInterface {
    GraphADT<String, Double> graph = null;
    double totalCost = 65.0;

    /**
     * This method will read in the file name, and throw an exception if the file is not valid. 
     * DijkstraGraph will also be created to be used in the frontend implementation and tests
     */
    @Override
    public boolean readFile(String filepath) throws IOException{
        //checking if filepath is valid
        if (filepath == null) {
            throw new IOException("file was not entered");
            //return false;
        }
        if (filepath.equals("data/badMap.csv")) {
            throw new IOException("file was not entered");
            //return false;
        }
        //creating the hardcoded version of the DijkstraGraph
        graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("M");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("I");
        graph.insertNode("L");
        
        graph.insertEdge("A", "H", 8.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("M", "F", 4.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("I", "L", 5.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "L", 7.0);

        return true;
    }

    /**
     * This method will calculate the shortest path between a start and destination point
     */
    @Override
    public PathResultInterface getShortestPath(String src, String dest) {
        //capturing the list of the shortestPath data
        List<String> shortestPathData = graph.shortestPathData(src, dest);
        //capturing the shortestPathCost
        double shortestPathCost = graph.shortestPathCost(src, dest);
        //capturing the cost of the segments in a list
        List<Double> segmentCosts = new ArrayList<Double>();
        for (int i = 0; i < shortestPathData.size() - 1; i++) {
            double segmentCost = graph.getEdge(shortestPathData.get(i), shortestPathData.get(i + 1));
            segmentCosts.add(segmentCost);            
        }
        
        PathResultInterface p = new PathResultInterface() {

            @Override
            public List<String> getPath() {
                // TODO Auto-generated method stub
                return shortestPathData;
            }

            @Override
            public List<Double> getTimeSegments() {
                // TODO Auto-generated method stub
                return segmentCosts;
            }

            @Override
            public double getTotalCost() {
                // TODO Auto-generated method stub
                return shortestPathCost;
            }
        };
        
        return p;
    }

    /**
     *This method is used to get the summary data. This will include
     *the node count, edge count, and total cost
     */
    @Override
    public String summaryData() {
        String sumData = "Number of buildings: " + graph.getNodeCount() + "\n" + "Number of paths: " + graph.getEdgeCount() + "\n" +
                "Total distance of paths: " + this.totalCost + " seconds";
        return sumData;
    }

}
