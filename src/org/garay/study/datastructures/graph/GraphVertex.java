package org.garay.study.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by technique on 11/18/15.
 */
public class GraphVertex<T> {
    private T data;
    private List<GraphEdge<T>> edges = new ArrayList<>();
    private int depth = 0;

    public GraphVertex(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public GraphEdge connect(GraphVertex<T> toNode, Integer weight) {

        if(toNode.depth == 0) {
            toNode.depth = this.depth + 1;
        } else {
            //track max depth to prevent cycle
            toNode.depth = Math.max(toNode.depth, this.depth+1);
        }
        GraphEdge edge = new GraphEdge(this, toNode, weight);
        edges.add(edge);
        return edge;
    }

    public String asStringDeep(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(asString(depth));
        for(GraphEdge curEdge: edges) {
            sb.append(curEdge.getToVertex().asStringDeep(depth+1));
        }
        return sb.toString();
    }


    public String asWeightedStringDeep(int depth, Integer weight) {
        StringBuilder sb = new StringBuilder();
        sb.append(asWeightedString(depth, weight));
        for(GraphEdge curEdge: edges) {
            sb.append(curEdge.getToVertex().asWeightedStringDeep(depth + 1, curEdge.getWeight()));
        }
        return sb.toString();
    }

    public List<GraphEdge<T>> getEdges() {
        return this.edges;
    }

    public GraphEdge<T> getEdgeByVertexName(String name) {
        for(GraphEdge<T> curEdge: this.edges) {
            if(curEdge.getToVertex().getName().equals(name)) {
                return curEdge;
            }
        }
        return null;
    }

    public String getName() {
        return this.data.toString();
    }


    public int getDepth() {
        return depth;
    }

    public String asString(int depth) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < depth; i++) {
            sb.append("--");
        }
        sb.append("(" + this.data.toString() + ")\n");
        return sb.toString();
    }

    public String asWeightedString(int depth, Integer edgeWeight) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < depth; i++) {
            if(i==depth-1) {
                sb.append("-" + edgeWeight + "-");
            }
            else{
                sb.append("---");
            }

        }
        sb.append("(" + this.data.toString() + ")\n");
        return sb.toString();
    }

}
