package com.deezzex.util;

import com.deezzex.my_impl.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GraphBuilder {
    private static final String PATH_TO_FILE = "C:\\Users\\pshti\\IdeaProjects\\lab-dm-2\\src\\main\\resources\\matrix.txt";

    private static int[][] getInputMatrix() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(PATH_TO_FILE)));
        boolean firstLine = true;
        int N = 0;
        int[][] matrix = new int[0][];

        while (sc.hasNextLine()) {
            if (firstLine) {
                String[] line = sc.nextLine().trim().split(" ");
                N = Integer.parseInt(line[0]);
                firstLine = false;
            }

            matrix = new int[N][N];

            for (int i = 0; i < matrix.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }

        printInputMatrix(matrix);
        return matrix;
    }

    public static Map<Character, List<Node>> buildMyGraph() throws FileNotFoundException {
        Map<Character, List<Node>> graph = new HashMap<>();

        int[][] matrix = getInputMatrix();

        Integer curNodeName = 1;
        Integer nextNodeName = 1;

        for (int[] curRow : matrix) {
            List<Node> nodes = new ArrayList<>();
            for (int curCol : curRow) {
                if (curCol == 0) {
                    nextNodeName++;
                    continue;
                }

                nodes.add(new Node(String.valueOf(nextNodeName).charAt(0), curCol));

                nextNodeName++;
            }

            graph.put(String.valueOf(curNodeName).charAt(0), nodes);
            curNodeName++;
            nextNodeName = 1;
        }

        System.out.println("Вхідний граф: ");
        graph.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println();
        return graph;
    }

    public static Graph<Integer, DefaultEdge> buildTestGraph() throws FileNotFoundException {
        Graph<Integer, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);

        int[][] matrix = getInputMatrix();

        for (int i = 1; i <= matrix[0].length; i++) {
            graph.addVertex(i);
        }

        Integer curNodeName = 1;
        Integer nextNodeName = 1;

        List<String> initializedEdges = new ArrayList<>();
        for (int[] curRow : matrix) {
            for (Integer curCol : curRow) {
                if (curCol == 0 || initializedEdges.contains(curNodeName + ":" + nextNodeName)) {
                    nextNodeName++;
                    continue;
                }

                DefaultEdge edge = graph.addEdge(curNodeName, nextNodeName);
                graph.setEdgeWeight(edge, curCol);

                initializedEdges.add(curNodeName + ":" + nextNodeName);
                initializedEdges.add(nextNodeName + ":" + curNodeName);

                nextNodeName++;
            }

            curNodeName++;
            nextNodeName = 1;
        }

        return graph;
    }

    private static void printInputMatrix(int[][] matrix) {
        System.out.println("Вхідна матриця: ");
        for (int[] curRow : matrix) {
            for (int curCol : curRow) System.out.printf("%d ", curCol);
            System.out.println();
        }
        System.out.println();
    }
}
