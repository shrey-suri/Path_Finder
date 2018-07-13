package roadgraph;
import java.util.*;
public class Tester implements Comparator<MapNode> {

	@Override
	public int compare(MapNode o1, MapNode o2) {
		// TODO Auto-generated method stub
		double dis2 = o2.getDistance();
		double dis1 = o1.getDistance();
	//	System.out.println();
		//System.out.println("o1 = " + o1.getLocation() + " " + dis1);
		//System.out.println("o2 = " + o2.getLocation() + " " + dis2);
		if(dis1<dis2)
			return -1;
		if(dis1>dis2)
			return 1;
		return 0;
	}

}
