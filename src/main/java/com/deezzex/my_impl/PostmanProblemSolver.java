package com.deezzex.my_impl;

import java.util.*;
import java.util.stream.Collectors;

public class PostmanProblemSolver {
    private final List<Edge> edges = new ArrayList<>();

    public void solve(Map<Character, List<Node>> graph) {
        if (!checkEulerGraph(graph)) {
            System.out.println("Вхідний граф не є Ейлеровим.");
            findNewEdges(graph);
        }

        calculateAllPostmanWay(graph);
    }

    private boolean checkEulerGraph(Map<Character, List<Node>> graph) {
        for (List<Node> nodes : graph.values()) {
            if (nodes.size() % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    private void calculateAllPostmanWay(Map<Character, List<Node>> graph) {
        int wayLength = 0;

        for (Map.Entry<Character, List<Node>> node : graph.entrySet()) {
            wayLength += node.getValue().stream().mapToInt(Node::getWeight).sum();
        }

        wayLength /= 2;
        System.out.println("Довжина шляху листоноші без повторюючих вузлів: " + wayLength);

        wayLength += edges.stream().mapToInt(Edge::getLength).sum();
        System.out.println("Довжина повного шляху листоноші: " + wayLength);
    }

    private void findNewEdges(Map<Character, List<Node>> graph) {
        List<Character> oddNodes = getOddNodes(graph);

        if (oddNodes.size() % 2 != 0) {
            throw new RuntimeException("Некоректна матриця.");
        }

        System.out.println("Вузли з непарною кількістю ребер:");
        oddNodes.forEach(x -> System.out.print(x + " "));
        System.out.println();

        Map<Character, Map<Character, Edge>> matrix = oddNodes.stream()
                .collect(Collectors.toMap(x -> x, x -> findMinDistanceToNode(graph, x).entrySet().stream()
                        .filter(res -> oddNodes.contains(res.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));

        List<Edge> superSet = matrix.values().stream()
                .flatMap(map -> map.values().stream())
                .filter(x -> x.getLength() > 0)
                .distinct()
                .collect(Collectors.toList());

        List<Edge> filteredSet = new ArrayList<>();
        for (Edge edge : superSet) {
            if (filteredSet.stream().noneMatch(i -> i.isSame(edge.getPath()))) {
                filteredSet.add(edge);
            }
        }

        int pairCount = oddNodes.size() / 2;

        edges.addAll(filteredSet
                .stream()
                .sorted(Comparator.comparing(Edge::getLength))
                .limit(pairCount)
                .collect(Collectors.toList()));

        System.out.println("Додаткові шляхи:");
        edges.forEach(x -> System.out.println("Шлях: " + x.getPath() + " Довжина: " + x.getLength()));
    }

    private List<Character> getOddNodes(Map<Character, List<Node>> graph) {
        return graph.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() % 2 != 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<Character, Edge> findMinDistanceToNode(Map<Character, List<Node>> graph, char from) {
        int length = graph.size();
        List<Character> usedNodes = new ArrayList<>();
        usedNodes.add(from);
        Map<Character, Edge> distances = new HashMap<>();
        distances.put(from, new Edge("", 0));

        for (int i = 0; i < length - 1; i++) {
            char minimalNodeChar = ' ';
            int minimalNodeDistance = Integer.MAX_VALUE;
            String path = "";

            for (Character x : usedNodes) {
                String temporaryPath = x.toString();
                Optional<Node> optionalNode = graph.get(x).stream().filter(y -> !usedNodes.contains(y.getName())).min(Comparator.comparingInt(Node::getWeight));
                if (optionalNode.isPresent()) {
                    Node node = optionalNode.get();
                    int minimalLength = node.getWeight();
                    if (x != from) {
                        temporaryPath = distances.get(x).getPath();
                        minimalLength += distances.get(x).getLength();
                    }

                    if (minimalNodeDistance > minimalLength) {
                        minimalNodeDistance = minimalLength;
                        minimalNodeChar = node.getName();
                        path = temporaryPath;
                    }
                }
            }

            path += minimalNodeChar;
            usedNodes.add(minimalNodeChar);
            distances.put(minimalNodeChar, new Edge(path, minimalNodeDistance));
        }

        return distances;
    }
}