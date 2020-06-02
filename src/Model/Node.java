package Model;
import java.util.ArrayList;
public class Node {
	private int Number;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    
	public Node(int num) {
		Number = num;
	}
	
	public Edge addEdge(int to, double gain) {
		Edge g = new Edge(Number, to, gain);
		 edges.add(g);
		return g;
	}
	
	public boolean removeEdge(int to, double gain) {
		Edge g = new Edge(Number, to, gain);
		 if (edges.contains(g)) {
			 edges.remove(g);
			 return true;
	     }
		return false;
	}
	
	public Edge getEdge(int to){
		for(Edge g : edges) {
			if(g.getTo()==to && g.getFrom()==Number) {
				return g;
			}
		}
		return null;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}

	public ArrayList<Integer> getTos() {
		ArrayList<Integer> to = new ArrayList<Integer>();
		for(int i=0;i<edges.size();i++) {
			to.add(edges.get(i).getTo());
		}
		return to;
	}
}
