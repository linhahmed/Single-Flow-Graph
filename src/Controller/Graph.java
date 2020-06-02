package Controller;
import java.util.*;

import Model.Edge;
import Model.Node;

public class Graph {
	private int NumOfNodes;
	private ArrayList<Node> Nodes;
	private ArrayList<Integer[]> forwardPaths;
	private List<List<Integer>> Loops;
	private Set<Integer> visited;
	private Deque<Integer> pointStack;
	private Deque<Integer> markedStack;
	private Set<Integer> markedSet;
	private List<List<List<List<Integer>>>> nonTouchingLoops;
	private ArrayList<List<String>> nonTouchingLoopsGain;
	private List<Edge> allEdges;

	public Graph(int NumOfNodes) {
		Nodes = new ArrayList<Node>();
		allEdges = new ArrayList<Edge>();
		forwardPaths = new ArrayList<Integer[]>();
		Loops = new ArrayList<List<Integer>>();
		nonTouchingLoops = new ArrayList<List<List<List<Integer>>>>();
		nonTouchingLoopsGain = new ArrayList<List<String>>();
		this.NumOfNodes = NumOfNodes;
		visited = new HashSet<>();
		pointStack = new LinkedList<>();
		markedStack = new LinkedList<>();
		markedSet = new HashSet<>();
	}

	public boolean addNodes() {
		if (NumOfNodes == 1) {
			return false;
		}
		for (int i = 0; i < NumOfNodes; i++) {
			Node node = new Node(i + 1);
			Nodes.add(node);
		}
		return true;
	}

	public List<Edge> getAllEdges() {
		return allEdges;
	}

	public boolean addEdge(int from, int to, double gain) {
		if (Nodes.size() >= from && Nodes.size() >= to) {
			Edge g = Nodes.get(from - 1).addEdge(to, gain);
			allEdges.add(g);
			return true;
		}
		return false;
	}

	double gain = 1;
	boolean first = true;

	private void MakeForwadPath(Integer f, Integer u, Integer d, boolean[] isVisited, List<Integer> localPathList) {
		isVisited[u - 1] = true;
		if (first) {
			localPathList.add(u);
			first = false;
		}
		if (u.equals(d)) {
			Integer[] array = localPathList.toArray(new Integer[0]);
			forwardPaths.add(array);
			isVisited[u - 1] = false;
			return;
		}
		ArrayList<Integer> Edges = Nodes.get(u - 1).getTos();
		int x;
		for (Integer i : Edges) {
			x = i;
			if (!isVisited[i - 1]) {
				localPathList.add(x);
				MakeForwadPath(f, i, d, isVisited, localPathList);
				localPathList.remove(i);
			}
		}
		isVisited[u - 1] = false;
	}

	
	public ArrayList<Integer[]> getForawrdPaths(int a, int b) {
		boolean[] isVisited = new boolean[NumOfNodes];
		ArrayList<Integer> pathList = new ArrayList<>();
		MakeForwadPath(a, a, b, isVisited, pathList);
		return forwardPaths;
	}

	
	public ArrayList<String> getForawrdPathsGain() {
	    ArrayList<String> forawrdPathsGain = new ArrayList<String>();
		for (int i = 0; i < forwardPaths.size(); i++) {
			double gain = 1;
			for (int j = 0; j < forwardPaths.get(i).length - 1; j++) {
				int a = forwardPaths.get(i)[j];
				int b = forwardPaths.get(i)[j + 1];
				Edge edge = Nodes.get(a - 1).getEdge(b);
				gain = gain * edge.getGain();
			}
			forawrdPathsGain.add(String.valueOf(gain));
		}
		return forawrdPathsGain;
	}


	public List<List<Integer>> getLoops() { // Tarjan
		visited = new HashSet<>();
		pointStack = new LinkedList<>();
		markedStack = new LinkedList<>();
		markedSet = new HashSet<>();
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < NumOfNodes; i++) {
			findLoops(i + 1, i + 1, result);
			visited.add(i + 1);
			while (!markedStack.isEmpty()) {
				markedSet.remove(markedStack.pollFirst());
			}
		}
		Loops = result;
		return result;
	}

	private boolean findLoops(int start, int current, List<List<Integer>> result) {
		boolean hasLoop = false;
		pointStack.offerFirst(current);
		markedSet.add(current);
		markedStack.offerFirst(current);
		for (Integer w : Nodes.get(current - 1).getTos()) {
			if (visited.contains(w)) {
				continue;
			} else if (w.equals(start)) {
				hasLoop = true;
				pointStack.offerFirst(w);
				List<Integer> loop = new ArrayList<>();
				Iterator<Integer> itr = pointStack.iterator();
				while (itr.hasNext()) {
					loop.add(itr.next());
				}
				pointStack.pollFirst();
				Collections.reverse(loop);
				result.add(loop);
			} else if (!markedSet.contains(w)) {
				hasLoop = findLoops(start, w, result) || hasLoop;
			}
		}
		if (hasLoop) {
			while (!markedStack.peekFirst().equals(current)) {
				markedSet.remove(markedStack.pollFirst());
			}
			markedSet.remove(markedStack.pollFirst());
		}
		pointStack.pollFirst();
		return hasLoop;
	}
	

	public ArrayList<String> getLoopsGain() {
		ArrayList<String> LoopsGain = new ArrayList<String>();
		for (int i = 0; i < Loops.size(); i++) {
			double gain = 1;
			for (int j = 0; j < Loops.get(i).size() - 1; j++) {
				int a = Loops.get(i).get(j);
				int b = Loops.get(i).get(j + 1);
				Edge edge = Nodes.get(a - 1).getEdge(b);
				gain = gain * edge.getGain();
			}
			LoopsGain.add(String.valueOf(gain));
		}
		return LoopsGain;
	}
	

	public List<List<List<List<Integer>>>> getNonTouchingLoops() {
		for (List<Integer> loop1 : Loops) {
			for (List<Integer> loop2 : Loops) {
				if (!isTouching(loop1, loop2)) {
					List<List<Integer>> temp = new ArrayList<List<Integer>>();
					temp.add(loop1);
					temp.add(loop2);
					if (nonTouchingLoops.isEmpty()) {
						nonTouchingLoops.add(new ArrayList<List<List<Integer>>>());
						nonTouchingLoopsGain.add(new ArrayList<String>());
					}
					String gain1 = getGain(loop1);
					String gain2 = getGain(loop2);
					double gain = Double.parseDouble(gain1) * Double.parseDouble(gain2);
					nonTouchingLoopsGain.get(0).add(String.valueOf(gain));
					nonTouchingLoops.get(0).add(temp);
					completeNontouched(temp);
				}
			}
		}
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			removeNonTouchingDuplicateLoops();
		}
		return nonTouchingLoops;
	}

	
	private void completeNontouched(List<List<Integer>> temploops) {
		boolean flag = false;
		for (List<Integer> loop1 : Loops) {
			flag = false;
			for (List<Integer> loop2 : temploops) {
				if (isTouching(loop1, loop2)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				List<List<Integer>> temp = new ArrayList<List<Integer>>(temploops);
				temp.add(loop1);

				if (nonTouchingLoops.size() < temp.size() - 1) {
					nonTouchingLoops.add(new ArrayList<List<List<Integer>>>());
					nonTouchingLoopsGain.add(new ArrayList<String>());
				}
				double gain = 1;
				for (int i = 0; i < temp.size(); i++) {
					gain *= Double.parseDouble(getGain(temp.get(i)));
				}

				nonTouchingLoopsGain.get(temp.size() - 2).add(String.valueOf(gain));

				nonTouchingLoops.get(temp.size() - 2).add(temp);
				completeNontouched(temp);
			}
		}
	}

	private void removeNonTouchingDuplicateLoops() {
		boolean flag;
		int count = -1;
		for (List<List<List<Integer>>> NontouchingLoop : nonTouchingLoops) {
			count++;
			for (int i = 0; i < NontouchingLoop.size(); i++) {
				for (int j = 0; j < NontouchingLoop.size(); j++) {
					flag = false;
					List<List<Integer>> paths1 = NontouchingLoop.get(i);
					List<List<Integer>> paths2 = NontouchingLoop.get(j);
					if (paths1.size() == paths2.size()) {
						for (List<Integer> cycle : paths1) {
							if (!paths2.contains(cycle)) {
								flag = true;
								break;
							}
						}
					}
					if (!flag && i != j) {
						NontouchingLoop.remove(i);
						nonTouchingLoopsGain.get(count).remove(i);
						break;
					}
				}
			}
		}
	}

	private boolean isTouching(List<Integer> loop1, List<Integer> loop2) {
		for (Integer node1 : loop1) {
			for (Integer node2 : loop2) {
				if (node1 == node2) {
					return true;
				}
			}
		}
		return false;
	}

	public String getGain(List<Integer> loop) {
		double gain = 1;
		for (int i = 0; i < loop.size() - 1; i++) {
			int a = loop.get(i);
			int b = loop.get(i + 1);
			Edge edge = Nodes.get(a - 1).getEdge(b);
			gain = gain * edge.getGain();
		}
		return String.valueOf(gain);
	}
	
	public List<List<String>> getnonTouchingLoopsGain() {
		return nonTouchingLoopsGain;
	}


	public String getDelta() {
		double delta = 1;
		double SumLoops = 0;
		ArrayList<String> LoopsGain = getLoopsGain();
		for (int i = 0; i < LoopsGain.size(); i++) {
			SumLoops += Double.valueOf(LoopsGain.get(i));
		}
		delta -= SumLoops;
		for (int i = 0; i < nonTouchingLoopsGain.size(); i++) {
			double Sum = 0;
			for (int j = 0; j < nonTouchingLoopsGain.get(i).size(); j++) {
				Sum += Double.valueOf(nonTouchingLoopsGain.get(i).get(j));
			}
			if (i % 2 == 0) {
				delta += Sum;
			} else {
				delta -= Sum;
			}
		}
		return String.valueOf(delta);
	}

	public String getDeltaOfPath(List<Integer> path) {
		List<List<Integer>> loopsToBeRemoved = new ArrayList<List<Integer>>(Loops);
		List<List<Integer>> loopsTemp = new ArrayList<List<Integer>>(Loops);
		double deltaPath = 1;
		for (List<Integer> loop : loopsTemp) {
			if (isTouching(loop, path)) {
				loopsToBeRemoved.remove(loop);
			}
		}
		double Sum =0;
		for (int i = 0; i < loopsToBeRemoved.size(); i++) {
			Sum += Double.valueOf(getGain(loopsToBeRemoved.get(i)));
		}
		deltaPath -= Sum;
		return String.valueOf(deltaPath);
	}
	
	
	public String CalculateTF() {
		double TF = 0;
		double Sum = 0;
		ArrayList<String> FPG = getForawrdPathsGain();
		for(int i=0;i<FPG.size();i++) {
			List<Integer> path = Arrays.asList(forwardPaths.get(i));
			double Product = Double.valueOf(getDeltaOfPath(path));
			Product *= Double.valueOf(FPG.get(i));
			Sum += Product;
		}
		TF = Sum / Double.valueOf(getDelta());
		return String.valueOf(TF);
	}

	public static void main(String[] args) {
		// Create a sample graph
		Graph g = new Graph(7);
		System.out.println(g.addNodes());
		g.addEdge(1, 2, 1);
		g.addEdge(2, 3, 5);
		g.addEdge(2, 7, 10);
		g.addEdge(3, 4, 10);
		g.addEdge(4, 3, -1);
		g.addEdge(4, 5, 2);
		g.addEdge(5, 4, -2);
		g.addEdge(5, 6, 1);
		g.addEdge(5, 2, -1);
		g.addEdge(7, 7, -1);
		g.addEdge(7, 5, 2);
		System.out.println("loops");
		List<List<Integer>> h = g.getLoops();
		for (List<Integer> csv : h) {
			if (!csv.isEmpty()) {
				System.out.print(csv.get(0));
				for (int i = 1; i < csv.size(); i++) {
					System.out.print("," + csv.get(i));
				}
			}
			System.out.print("\n");
		}
		System.out.println("paths");
		List<Integer[]> l = g.getForawrdPaths(1, 6);
		for (Integer[] csv : l) {
			System.out.print(csv[0]);
			for (int i = 1; i < csv.length; i++) {
				System.out.print("," + csv[i]);
			}
			System.out.print("\n");
		}
		

	/*	System.out.println("gains");
		ArrayList<String> p = g.getLoopsGain();
		for (String i : p) {
			System.out.println(i);
		}*/
		List<List<List<List<Integer>>>> o = g.getNonTouchingLoops();
		System.out.println(o);
	/*	List<List<String>> k = g.getnonTouchingLoopsGain();
		System.out.println(k);*/
		String po = g.getDelta();
		System.out.println(po);
		List<Integer> list = Arrays.asList(l.get(0));
		System.out.println(list);
		String f = g.getDeltaOfPath(list);
		System.out.println(f);
		List<Integer> list1 = Arrays.asList(l.get(1));
		System.out.println(list1);

		String f1 = g.getDeltaOfPath(list1);
		System.out.println(f1);
		
		
		String loo = g.CalculateTF();
		System.out.println(loo);

	}

}