package com.deezzex.my_impl;

import com.deezzex.util.GraphBuilder;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class MyRunner {
    public static void main(String[] args) throws FileNotFoundException {
        Map<Character, List<Node>> graph = GraphBuilder.buildMyGraph();

        PostmanProblemSolver postmanProblemSolver = new PostmanProblemSolver();

        postmanProblemSolver.solve(graph);
    }
}
