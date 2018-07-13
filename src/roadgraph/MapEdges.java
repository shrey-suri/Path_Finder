package roadgraph;

import geography.GeographicPoint;

public class MapEdges {
	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double length;
	
	public MapEdges() {
	}
	public MapEdges(GeographicPoint start, GeographicPoint end, String rn, String rt, double len) {
		this.start = start;
		this.end = end;
		this.roadName = rn;
		this.roadType = rt;
		this.length = len;
	}
	public GeographicPoint getStart() {
		return start;
	}
	public GeographicPoint getEnd() {
		return end;
	}
	public String getRoadName() {
		return roadName;
	}
	public String getRoadType() {
		return roadType;
	}
	public double getDistance() {
		return length;
	}
}
