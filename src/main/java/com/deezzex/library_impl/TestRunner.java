package com.deezzex.library_impl;

import com.deezzex.util.GraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.ChinesePostman;
import org.jgrapht.graph.DefaultEdge;

import java.io.FileNotFoundException;

public class TestRunner {


    public static void main(String[] args) throws FileNotFoundException {
        Graph<Integer, DefaultEdge> graph = GraphBuilder.buildTestGraph();

        ChinesePostman<Integer, DefaultEdge> cp = new ChinesePostman<>();

        GraphPath<Integer, DefaultEdge> cppSolution = cp.getCPPSolution(graph);

        System.out.println("Довжина повного шляху листоноші: " + cppSolution.getWeight());
        System.out.println("Маршрут листоноші: " + cppSolution.getVertexList());
    }
}
