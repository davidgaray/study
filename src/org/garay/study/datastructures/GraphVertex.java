package org.garay.study.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by technique on 11/18/15.
 */
public class GraphVertex<T, W> {
    private T data;
    private List<GraphEdge<W>> edges = new ArrayList<>();

    public GraphVertex(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public GraphEdge<W> connect(GraphVertex<T, W> toNode, W weight) {
        GraphEdge<W> edge = new GraphEdge<>(this, toNode, weight);
        edges.add(edge);
        return edge;
    }

    public String asStringDeep(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(asString(depth));
        for(GraphEdge<W> curEdge: edges) {
            sb.append(curEdge.getToNode().asStringDeep(depth+1));
        }
        return sb.toString();
    }


    public String asWeightedStringDeep(int depth, W weight) {
        StringBuilder sb = new StringBuilder();
        sb.append(asWeightedString(depth, weight));
        for(GraphEdge<W> curEdge: edges) {
            sb.append(curEdge.getToNode().asWeightedStringDeep(depth + 1, curEdge.getWeight()));
        }
        return sb.toString();
    }



    public String asString(int depth) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < depth; i++) {
            sb.append("--");
        }
        sb.append("(" + this.data.toString() + ")\n");
        return sb.toString();
    }

    public String asWeightedString(int depth, W edgeWeight) {
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
