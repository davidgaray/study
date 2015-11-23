package org.garay.study.datastructures.graph;

/**
 * Created by technique on 11/21/15.
 */
public abstract class VertexBuilder <T> {
    private String name;

    public VertexBuilder<T> setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Package private
     * @return
     */
    public String getName() {
        return this.name;
    }

    public abstract GraphVertex<T> getVertex();
}
