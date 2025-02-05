
import java.util.*;

// Custom Exception for Graph Operations
class GraphException extends RuntimeException {
    public GraphException(String message) {
        super(message);
    }
}

// Pair Class for Graph Edges
class Pair {
    int nbr, weight;
    public Pair(int nbr, int weight) {
        this.nbr = nbr;
        this.weight = weight;
    }
}

// Edge Class for Kruskal's Algorithm
class Edge implements Comparable<Edge> {
    int src, dest, weight;
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

// Disjoint Set for Kruskal's Algorithm
class DisjointSet {
    private int[] parent, rank;
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    public int find(int node) {
        if (parent[node] != node)
            parent[node] = find(parent[node]);
        return parent[node];
    }
    public void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV])
                parent[rootV] = rootU;
            else if (rank[rootU] < rank[rootV])
                parent[rootU] = rootV;
            else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

// Main Graph Class
public class Graph {
    private final int vertices;
    private final Map<Integer, List<Pair>> adjList;
    private final List<Edge> edges;

    // Constructor
    public Graph(int vertices) {
        if (vertices <= 0) throw new GraphException("Number of vertices must be positive.");
        this.vertices = vertices;
        this.adjList = new HashMap<>();
        this.edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    // Add Edge
    public void addEdge(int src, int dest, int weight, boolean isDirected) {
        if (src < 0 || dest < 0 || src >= vertices || dest >= vertices) {
            throw new GraphException("Invalid node index.");
        }
        adjList.get(src).add(new Pair(dest, weight));
        edges.add(new Edge(src, dest, weight));
        if (!isDirected) {
            adjList.get(dest).add(new Pair(src, weight));
        }
    }

    // Prim's Algorithm for Minimum Spanning Tree
    public List<Edge> primsMST() {
        List<Edge> result = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        boolean[] visited = new boolean[vertices];
        visited[0] = true;
        for (Pair neighbor : adjList.get(0)) {
            pq.add(new Edge(0, neighbor.nbr, neighbor.weight));
        }
        while (!pq.isEmpty() && result.size() < vertices - 1) {
            Edge edge = pq.poll();
            if (visited[edge.dest]) continue;
            visited[edge.dest] = true;
            result.add(edge);
            for (Pair neighbor : adjList.get(edge.dest)) {
                if (!visited[neighbor.nbr]) {
                    pq.add(new Edge(edge.dest, neighbor.nbr, neighbor.weight));
                }
            }
        }
        return result;
    }

    // Kruskal's Algorithm for Minimum Spanning Tree
    public List<Edge> kruskalsMST() {
        List<Edge> result = new ArrayList<>();
        DisjointSet ds = new DisjointSet(vertices);
        Collections.sort(edges);
        for (Edge edge : edges) {
            if (ds.find(edge.src) != ds.find(edge.dest)) {
                result.add(edge);
                ds.union(edge.src, edge.dest);
            }
        }
        return result;
    }

    // Print Adjacency List
    public void printGraph() {
        for (int node : adjList.keySet()) {
            System.out.print("Vertex " + node + " -> ");
            for (Pair edge : adjList.get(node)) {
                System.out.print(edge.nbr + "(Weight: " + edge.weight + "), ");
            }
            System.out.println();
        }
    }
}




public class GraphLibrary {

}
