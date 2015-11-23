package org.garay.study.datastructures.graph;

/**
 * Created by technique on 11/21/15.
 */
public class VertexData {
    private String name;
    private VertexPath path;

    public VertexData(String name) {
        this.name = name;
    }

    public void setVertexPath(VertexPath vertexPath) {
        this.path = vertexPath;
    }

    public VertexPath getVertexPath() {
        return this.path;
    }


    public String toString() {
        return this.name;
    }
}
