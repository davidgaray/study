package org.garay.study.datastructures;

/**
 * Created by technique on 11/18/15.
 */
public class GraphEdge <W>{
    private GraphVertex fromNode;
    private GraphVertex toNode;
    private W weight;

    public GraphEdge(GraphVertex fromNode, GraphVertex toNode, W weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public GraphVertex getFromNode() {
        return fromNode;
    }

    public GraphVertex getToNode() {
        return toNode;
    }

    public W getWeight() {
        return weight;
    }
}
