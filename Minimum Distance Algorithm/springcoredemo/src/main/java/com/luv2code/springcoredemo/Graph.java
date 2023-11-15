package com.luv2code.springcoredemo;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

    public void addVertex(String vertex) {
        adjacencyList.put(vertex, new HashMap<>());
    }

    public void addEdge(String source, String destination, int weight) {
        adjacencyList.get(source).put(destination, weight);
        adjacencyList.get(destination).put(source, weight); // Assuming an undirected graph
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialization
        for (String vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(new Node(start, 0));

        // Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll().vertex;
            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(current).entrySet()) {
                int newDistance = distances.get(current) + neighbor.getValue();
                if (newDistance < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDistance);
                    priorityQueue.add(new Node(neighbor.getKey(), newDistance));
                }
            }
        }

        return distances;
    }

    public Set<String> getVertices() {
        return adjacencyList.keySet();
    }

    private static class Node {
        String vertex;
        int distance;

        Node(String vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}


