package org.garay.study.datastructures;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by technique on 11/18/15.
 */
public class GraphBuilder {
    private int numVertices = 10;
    private int minEdgesPerVertex = 1;
    private int maxEdgesPerVertex = 3;
    private int minWeight = 1;
    private int maxWeight = 5;

    public GraphBuilder setNumVerticies(int numVertices) {
        this.numVertices = numVertices;
        return this;
    }

    public GraphBuilder setMaxEdgesPerVertex(int maxEdgesPerVertex) {
        this.maxEdgesPerVertex = maxEdgesPerVertex;
        return this;
    }

    GraphBuilder setMinEdgesPerVertex(int minEdgesPerVertex) {
        this.minEdgesPerVertex = minEdgesPerVertex;
        return this;
    }

    public GraphVertex build() {
        if(numVertices > 26)
            throw new RuntimeException("Too lazy to implement naming for more than 26 nodes");

        if(numVertices <1 || maxEdgesPerVertex < 1)
            throw new RuntimeException("Need a value for numVertices, maxEdgesPerVertex");

        GraphVertex<String, Integer> root = new GraphVertex<>("A");
        connect(root, this.numVertices, this.numVertices -1);
        return root;

    }

    private void connect(GraphVertex<String, Integer> root, int totalNodes, int numVerticiesRemaining) {
        LinkedList<GraphVertex<String, Integer>> vertexes = new LinkedList<>();
        vertexes.add(root);

        while(numVerticiesRemaining > 0) {
            GraphVertex<String, Integer> curRoot = vertexes.remove();
            int numEdges = ThreadLocalRandom.current().nextInt(this.minEdgesPerVertex, this.maxEdgesPerVertex +1);
            if(numEdges > numVerticiesRemaining) {
                numEdges = numVerticiesRemaining;
            }
            //connect a few edges from this node
            for(int i=0; i < numEdges; i++) {
                GraphVertex<String, Integer> newVertex = new GraphVertex<>(getName(totalNodes - numVerticiesRemaining));
                vertexes.add(newVertex);
                int weight = ThreadLocalRandom.current().nextInt(this.minWeight, this.maxWeight+1);
                curRoot.connect(newVertex, weight);
                numVerticiesRemaining--;
            }
        }
    }

    /**
     * @param index 0-25
     * @return Zero based character as String, starts with "A"
     */
    private static String getName(int index) {
        return String.valueOf((char)(index+65));
    }






}
