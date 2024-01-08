// --== CS400 File Header Information ==--
// Name: Anisha Apte 
// Email: aaapte@wisc.edu
// Group and Team: E04
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
//        public String toString() {
//            return this.node + "," + this.cost + "->" + this.predecessor;
//        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        
        //implement in step 5.3
        //making sure that the start and end nodes are actually in the graph 
        if (!super.containsNode(start)) {
            throw new NoSuchElementException("start node is not in graph");
        }
        if (!super.containsNode(end)) {
            throw new NoSuchElementException("end node is not in the graph");
        }
        //making the priority queue
        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
        //making the MapADT of the nodes that have already been visited
        MapADT<NodeType,Node> visitedNodes = new PlaceholderMap<NodeType,Node>();
        //getting the start and end node
        Node startNode = nodes.get(start);
        Node endNode = nodes.get(end);
        //adding the start node with a cost of 0 and no predecessor to the 
        //priority queue
        SearchNode starter = new SearchNode(startNode,0,null);
        pq.add(starter);
        
        while(!pq.isEmpty()) {
            //removing the first element from the priority queue
            SearchNode searchNode = pq.remove();
            //end node has been reached
            if (searchNode.node.equals(endNode)) {
                return searchNode;
            }
            //making sure that no duplicates get put into the priority queue
            if (visitedNodes.containsKey(searchNode.node.data)) {
                continue;
            }
            visitedNodes.put(searchNode.node.data, searchNode.node);
            //going through the list of edges leaving from the searchNode
            List<Edge> adjacent = searchNode.node.edgesLeaving;
            for (int i = 0; i < adjacent.size(); i++) {
                //skipping the node if it has already been visited
                Node next = adjacent.get(i).successor;
                if (visitedNodes.containsKey(next.data)) {
                    continue;
                }
                else {
                   //otherwise creating a new SearchNode, and finding its neighbors
                   SearchNode newStartNode = new SearchNode(next,searchNode.cost + adjacent.get(i).data.doubleValue(),searchNode);
                   pq.add(newStartNode);
                }
            }//end for 
             
        }//end while 
        //no path was found to the target node, exception thrown
        throw new NoSuchElementException("no path to the target node");
        
        
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // implement in step 5.4
        SearchNode shortPath = this.computeShortestPath(start, end);
        SearchNode temp = shortPath;
        List<NodeType> shortestPath = new LinkedList<NodeType>();
        //making sure that the temp gets added in the right order
        while (temp != null) {
            shortestPath.add(0, temp.node.data);
            temp = temp.predecessor;            
        }
        //returning the list
        return shortestPath;
       
              
        
        
  
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // implement in step 5.4
        //capturing the shortestCost and returning it 
        try {
            SearchNode shortestCost = this.computeShortestPath(start, end);
            return shortestCost.cost; 
        }
        catch(NoSuchElementException e) {
            return Double.NaN;
        }
       
        
    }

    // TODO: implement 3+ tests in step 4.1
    
   /**
    * Test method that makes use of an example that was traced through 
    * in lecture. Confirming that the results of the implementation
    * match what I computed by hand. Find the shortest path cost from 
    * D to I. 
    * 
    */
//@Test
//   public void resultsMatch() {
//       //creating the Dijkstra graph and inserting the nodes and connecting 
//       //the right edges
//       DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
//       graph.insertNode("A");
//       graph.insertNode("B");
//       graph.insertNode("D");
//       graph.insertNode("G");
//       graph.insertNode("H");
//       graph.insertNode("M");
//       graph.insertNode("E");
//       graph.insertNode("F");
//       graph.insertNode("I");
//       graph.insertNode("L");
//       
//       graph.insertEdge("A", "H", 8);
//       graph.insertEdge("A", "B", 1);
//       graph.insertEdge("A", "M", 5);
//       graph.insertEdge("M", "E", 3);
//       graph.insertEdge("M", "F", 4);
//       graph.insertEdge("H", "B", 6);
//       graph.insertEdge("B", "M", 3);
//       graph.insertEdge("D", "A", 7);
//       graph.insertEdge("D", "G", 2);
//       graph.insertEdge("I", "H", 2);
//       graph.insertEdge("H", "I", 2);
//       graph.insertEdge("I", "L", 5);
//       graph.insertEdge("I", "D", 1);
//       graph.insertEdge("F", "G", 9);
//       graph.insertEdge("G", "L", 7);
//       
//       //computing the shortest path cost
//       List<String> pathData = graph.shortestPathData("D", "I");
//       
//       //checking that the shortest path cost is what I computed by hand 
//       Assertions.assertEquals(17.0, graph.shortestPathCost("D", "I"));
//       
//       //finding the shortest path route
//    
//       //asserting that the shortest path route is equal to the implemented version
//       Assertions.assertEquals("[D, A, H, I]", pathData.toString());
//       
//   }
   
   /**
    * Creating another test using the same graph data used in the test
    * case above. Checking that the cost and sequence of data long the 
    * shortest path between a different start and end node
    */
   //@Test
//   public void diffStartAndEndNode() {
//       //creating the Dijkstra graph and inserting the nodes and connecting 
//       //the right edges
//       DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
//       graph.insertNode("A");
//       graph.insertNode("B");
//       graph.insertNode("D");
//       graph.insertNode("G");
//       graph.insertNode("H");
//       graph.insertNode("M");
//       graph.insertNode("E");
//       graph.insertNode("F");
//       graph.insertNode("I");
//       graph.insertNode("L");
//       
//       graph.insertEdge("A", "H", 8);
//       graph.insertEdge("A", "B", 1);
//       graph.insertEdge("A", "M", 5);
//       graph.insertEdge("M", "E", 3);
//       graph.insertEdge("M", "F", 4);
//       graph.insertEdge("H", "B", 6);
//       graph.insertEdge("B", "M", 3);
//       graph.insertEdge("D", "A", 7);
//       graph.insertEdge("D", "G", 2);
//       graph.insertEdge("I", "H", 2);
//       graph.insertEdge("H", "I", 2);
//       graph.insertEdge("I", "L", 5);
//       graph.insertEdge("I", "D", 1);
//       graph.insertEdge("F", "G", 9);
//       graph.insertEdge("G", "L", 7);
//       
//       //computing the shortest path cost
//       List<String> pathData = graph.shortestPathData("H", "G");
//       
//      
//       //checking that the shortest path cost is what I computed by hand 
//       Assertions.assertEquals(5.0, graph.shortestPathCost("H", "G"));
//       System.out.println(graph.shortestPathCost("H", "G"));
//
//       
//       //asserting that the shortest path route is equal to the implemented version
//       System.out.println(pathData.toString());
//       Assertions.assertEquals("[H, I, D, G]", pathData.toString());       
//   }
   
   /**
    * Creating a test method that checks the behavior of the implementation 
    * when the nodes that I am searching for a path between exist in the 
    * graph, but there is no sequence of directed edges that connects them from 
    * start to the end. 
    */
   //@Test
//   public void noSequenceofDirectedNodes() {
//       //creating the Dijkstra graph and inserting the nodes and connecting 
//       //the right edges
//       DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
//       graph.insertNode("A");
//       graph.insertNode("B");
//       graph.insertNode("D");
//       graph.insertNode("G");
//       graph.insertNode("H");
//       graph.insertNode("M");
//       graph.insertNode("E");
//       graph.insertNode("F");
//       graph.insertNode("I");
//       graph.insertNode("L");
//       
//       graph.insertEdge("A", "H", 8);
//       graph.insertEdge("A", "B", 1);
//       graph.insertEdge("A", "M", 5);
//       graph.insertEdge("M", "E", 3);
//       graph.insertEdge("M", "F", 4);
//       graph.insertEdge("H", "B", 6);
//       graph.insertEdge("B", "M", 3);
//       graph.insertEdge("D", "A", 7);
//       graph.insertEdge("D", "G", 2);
//       graph.insertEdge("I", "H", 2);
//       graph.insertEdge("H", "I", 2);
//       graph.insertEdge("I", "L", 5);
//       graph.insertEdge("I", "D", 1);
//       graph.insertEdge("F", "G", 9);
//       graph.insertEdge("G", "L", 7);
//       
//       //computing the shortest path cost
//       try {
//           List<String> pathData = graph.shortestPathData("F", "D"); 
//           Assertions.assertTrue(false, "should not come here");
//       }
//       catch (NoSuchElementException e) {
//           Assertions.assertTrue(true, "should come into catch block");
//       }
//       
//
//       //checking that the shortest path cost is what I computed by hand
//       Assertions.assertEquals(Double.NaN, graph.shortestPathCost("F", "D"));
//       
//   }

  }

