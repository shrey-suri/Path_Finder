package roadgraph;

import java.util.*;

import geography.*;

public class MapNode {
	private GeographicPoint location;
	private List<MapEdges> edges;
	private double distance;
	public MapNode() {
		this.location = null;
		edges = new LinkedList<MapEdges>();
		distance = -1;
	}
	public MapNode(GeographicPoint location) {
		this.location = location;
		edges = new LinkedList<MapEdges>();
		distance = -1;
	}
	public boolean setLocation(GeographicPoint location) {
		this.location = location;
		return true;
	}
	public boolean setDistance(double dis) {
		this.distance = dis;
		return true;
	}
	public boolean addEdge(GeographicPoint to, String roadName,
			String roadType, double length) {
		edges.add(new MapEdges(this.location,to,roadName,roadType,length));
		return true;
	}
	public List<MapEdges> getEdges() {
		return edges;
	}
	public GeographicPoint getLocation() {
		return location;
	}
	public double getDistance() {
		return distance;
	}
}
