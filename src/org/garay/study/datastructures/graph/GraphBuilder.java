package org.garay.study.datastructures.graph;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by technique on 11/18/15.
 */
public class GraphBuilder<T> {
    private int numVertices = 10;
    private int minEdgesPerVertex = 1;
    private int maxEdgesPerVertex = 3;
    private int minWeight = 1;
    private int maxWeight = 5;
    private VertexBuilder<T> vertexBuilder;
    private LinkedList<GraphVertex<T>> vertexes;
    private Map<Integer, List<GraphVertex<T>>> verticiesByDepth;
    private double probabilityOfAdditionalEdge = .4;


    public GraphBuilder(VertexBuilder<T> vertexBuilder) {
        this.vertexBuilder = vertexBuilder;
    }

    public GraphBuilder setNumVerticies(int numVertices) {
        this.numVertices = numVertices;
        return this;
    }

    public GraphBuilder setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
        return this;
    }

    public GraphBuilder setMaxEdgesPerVertex(int maxEdgesPerVertex) {
        this.maxEdgesPerVertex = maxEdgesPerVertex;
        return this;
    }

    public GraphBuilder setMinEdgesPerVertex(int minEdgesPerVertex) {
        this.minEdgesPerVertex = minEdgesPerVertex;
        return this;
    }


    public GraphVertex build(boolean treeOnly) {
        if(numVertices <1 || maxEdgesPerVertex < 1)
            throw new RuntimeException("Need a value for numVertices, maxEdgesPerVertex");

        vertexes = new LinkedList<>();
        verticiesByDepth = new HashMap<>();

        GraphVertex<T> root = vertexBuilder.setName("A").getVertex();
        addVertex(root);
        connect(root, this.numVertices, this.numVertices -1, treeOnly);
        return root;
    }

    private void connect(GraphVertex<T> root, int totalNodes, int numVerticiesRemaining, boolean treeOnly) {
        while(numVerticiesRemaining > 0) {
            GraphVertex<T> curRoot = vertexes.remove();
            int numEdges = ThreadLocalRandom.current().nextInt(this.minEdgesPerVertex, this.maxEdgesPerVertex +1);
            if(numEdges > numVerticiesRemaining) {
                numEdges = numVerticiesRemaining;
            }
            //connect a few edges from this node
            for(int i=0; i < numEdges; i++) {
                String name = getName(totalNodes - numVerticiesRemaining);
                GraphVertex<T> newVertex = vertexBuilder.setName(name).getVertex();
                int weight = ThreadLocalRandom.current().nextInt(this.minWeight, this.maxWeight+1);
                curRoot.connect(newVertex, weight);
                addVertex(newVertex);
                if(!treeOnly) {
                    maybeCreateAdditionalEdge(newVertex);
                }
                numVerticiesRemaining--;
            }
        }
    }

    private void addVertex(GraphVertex<T> vertex) {
        vertexes.add(vertex);
        addVertexAtDepth(vertex, vertex.getDepth());
    }

    private void addVertexAtDepth(GraphVertex<T> vertex, int depth) {
        List<GraphVertex<T>> graphVertexes = this.verticiesByDepth.get(Integer.valueOf(depth));
        if(graphVertexes == null) {
            graphVertexes = new ArrayList<>();
            this.verticiesByDepth.put(depth, graphVertexes);
        }
        graphVertexes.add(vertex);
    }

    private void maybeCreateAdditionalEdge(GraphVertex<T> newVertex) {
        int vertexDepth = newVertex.getDepth();
        if(vertexDepth <= 1) {
            //usually there's only one root at depth 0, so only connect if you're a couple deep
            return;
        }

        if(Math.random() < probabilityOfAdditionalEdge){
            //create additional edge
            List<GraphVertex<T>> vertexesAtDepth = this.verticiesByDepth.get(vertexDepth - 1);

            int nodeIndex = ThreadLocalRandom.current().nextInt(0, vertexesAtDepth.size());
            GraphVertex<T> newFromVertex = vertexesAtDepth.get(nodeIndex);
            if(newFromVertex.getEdgeByVertexName(newVertex.getName()) != null) {
                //this prevents the creation of a duplicate index
                //it also breaks the probabilityOfAdditionalEdge, as it fails to produce an edge
                //in this case.  Consider revising if that probability must be accurate.
                return;
            }
            int weight = ThreadLocalRandom.current().nextInt(this.minWeight, this.maxWeight+1);
            newFromVertex.connect(newVertex, weight);

        }
    }


    /**
     * @return One based character as String, starts with "A"
     */
    protected static String getName(int number) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int curIndex = number % 26;
            number = number / 26;
            sb.insert(0, String.valueOf((char)(curIndex+65)));
            if(number == 0) {
                break;
            }
        }
        return sb.toString();
    }







}
