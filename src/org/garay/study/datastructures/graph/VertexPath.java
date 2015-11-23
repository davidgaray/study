package org.garay.study.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by technique on 11/21/15.
 */
public class VertexPath <T> {
    private final List<GraphVertex<T>> verticies;
    private final Integer weight;

    public VertexPath(GraphEdge edge) {
        List<GraphVertex<T>> vert = new ArrayList<GraphVertex<T>>();
        vert.add(edge.getFromVertex());
        vert.add(edge.getToVertex());
        this.verticies = vert;
        this.weight = edge.getWeight();
    }

    public VertexPath(List<GraphVertex<T>> verticies, Integer weight) {
        this.verticies = verticies;
        this.weight = weight;
    }

    public VertexPath newPath(GraphVertex newVertex, Integer additionalWeight) {
        List<GraphVertex> newVerticies = new ArrayList<GraphVertex>(verticies);
        newVerticies.add(newVertex);
        return new VertexPath(newVerticies, weight + additionalWeight);
    }

    public Integer getTotalDistance() {
        return this.weight;
    }

    public List<GraphVertex<T>> getVerticies(){
        return this.verticies;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PATH: ");

        for(GraphVertex<T> curVertex: this.verticies) {
            sb.append("(" + curVertex.getName() + ")-->" );
        }

        sb.append("[" + this.weight + "]");

        return sb.toString();
    }
}
