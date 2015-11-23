package org.garay.study.datastructures.graph;

/**
 * Created by technique on 11/18/15.
 */
public class GraphEdge<T> {
    private GraphVertex<T> fromVertex;
    private GraphVertex<T> toVertex;
    private Integer weight;

    public GraphEdge(GraphVertex<T> fromVertex, GraphVertex<T> toVertex, Integer weight) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.weight = weight;
    }

    public GraphVertex<T> getFromVertex() {
        return fromVertex;
    }

    public GraphVertex<T> getToVertex() {
        return toVertex;
    }

    public Integer getWeight() {
        return weight;
    }
}
