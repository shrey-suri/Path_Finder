/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	 private HashMap<GeographicPoint,MapNode> vertices;
	private int numEdges;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<GeographicPoint,MapNode>();
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(location == null || vertices.containsKey(location))
		return false;
		vertices.put(location, new MapNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		//TODO: Implement this method in WEEK 3
		if(errorExistsFromLocation(from,to) || errorExistsFromRest(roadName,roadType,length)) {
			throw new IllegalArgumentException();
		}
		vertices.get(from).addEdge(to, roadName, roadType, length);
		numEdges++;
	}
	
	private boolean errorExistsFromLocation(GeographicPoint from, GeographicPoint to) {
		if(!vertices.containsKey(from) || !vertices.containsKey(to))
			return true;
		if(vertices.get(from)== null || vertices.get(to) == null)
			return true;
		return false;
	}
	private boolean errorExistsFromRest(String roadName,String roadType, double length) {
		if(roadName == null || roadType == null)
			return true;
		if(length < 0)
			return true;
		return false;
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if(errorExistsFromLocation(start,goal))
		return null;
		HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
		if (!bfsSearcher(start,goal,parent,nodeSearched)) {
			//System.out.println("No path exists");
			return null;
		}
		// reconstruct the path
		return constructPath(start,goal,parent);
	}
	
	private boolean bfsSearcher(GeographicPoint start, 
		     GeographicPoint goal,HashMap<GeographicPoint, GeographicPoint> parent ,Consumer<GeographicPoint> nodeSearched){
		Queue<GeographicPoint> queue = new LinkedList<GeographicPoint>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		queue.add(start);
		nodeSearched.accept(start);
		while(!queue.isEmpty()) {
			GeographicPoint curr = queue.remove();
			if(curr.equals(goal)) {
				return true;
			}
			List<MapEdges> edge = vertices.get(curr).getEdges();
			//System.out.println(edge.size());
			ListIterator<MapEdges> it = edge.listIterator();
			while(it.hasNext()) {
				GeographicPoint next = it.next().getEnd();
				if (!visited.contains(next)) {
					visited.add(next);
				queue.add(next);
				parent.put(next, curr);
				nodeSearched.accept(next);
				}
			}
		}
		return false;
	}
	private List<GeographicPoint> constructPath(GeographicPoint start, 
		     GeographicPoint goal,HashMap<GeographicPoint, GeographicPoint> parent){
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (!curr.equals(start)) {
			path.addFirst(curr);
			curr = parent.get(curr);
			//System.out.println(3);
		}
		path.addFirst(start);
		return path;
	}
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if(errorExistsFromLocation(start,goal))
			return null;
			HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
			//System.out.println(1);
			if (!Searcher(start,goal,parent,nodeSearched,0)) {
				//System.out.println("No path exists");
				return null;
			}
			// reconstruct the path
			//System.out.println(parent);
			return constructPath(start,goal,parent);
			//return null;
	}

	private boolean Searcher(GeographicPoint start, 
		     GeographicPoint goal,HashMap<GeographicPoint, GeographicPoint> parent ,Consumer<GeographicPoint> nodeSearched,int type){
		Comparator<MapNode> compare1 = new Tester();
		PriorityQueue<MapNode> queue = new PriorityQueue<MapNode>(compare1);
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		for(MapNode t : vertices.values())
			t.setDistance(Double.POSITIVE_INFINITY);
		queue.add(vertices.get(start));
		visited.add(start);
		nodeSearched.accept(start);
		int t =0;
		//System.out.println(t);
		while(!queue.isEmpty()) {
			GeographicPoint curr = (queue.remove()).getLocation();
			t++;
			double t3 = vertices.get(curr).getDistance();
			if(t3 == Double.POSITIVE_INFINITY)
				t3 =0;
			//System.out.println(curr);
			if (!visited.contains(curr)) 
				visited.add(curr);
			if(curr.equals(goal)) {
			System.out.println(t);
				return true;
			}
			List<MapEdges> edge = vertices.get(curr).getEdges();
			ListIterator<MapEdges> it = edge.listIterator();
			while(it.hasNext()) {
				MapEdges next = it.next();
				GeographicPoint nextPoint = next.getEnd();
				double dis = next.getDistance() + t3;
				if (!visited.contains(nextPoint)) {
					if(type == 1)
						dis += nextPoint.distance(goal);
					int com = compare(vertices.get(nextPoint).getDistance(),dis);
					if(com == -1) {
						vertices.get(nextPoint).setDistance(dis);
					//	System.out.println(nextPoint + " " +dis);
				queue.add(vertices.get(nextPoint));
				//System.out.println(nextPoint);
				//System.out.println(2);
				/*System.out.println("PriorityQueue ");
				for(MapNode t2 : queue) {
					System.out.println(t2.getLocation());
				}*/
				parent.put(nextPoint, curr);
				nodeSearched.accept(nextPoint);
				}
				}
			}
		}
		/*System.out.println("PriorityQueue ");
		while(!queue.isEmpty()) {
			System.out.println(queue.remove().getLocation());
		}
		System.out.println(parent);*/
		return false;
	}
	private int compare(double first, double second) {
		if(first == Double.POSITIVE_INFINITY)
			return -1;
		if(first<second)
			return 1;
		if(first>second)
			return -1;
		return 0;
	}
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		//System.out.println("a star");
		if(errorExistsFromLocation(start,goal))
			return null;
			HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
			if (!Searcher(start,goal,parent,nodeSearched,1)) {
				//System.out.println("No path exists");
				return null;
			}
			// reconstruct the path
			return constructPath(start,goal,parent);
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		//System.out.println(route2);
		
		
	}
	
}
