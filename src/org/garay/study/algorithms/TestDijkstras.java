package org.garay.study.algorithms;

import org.garay.study.datastructures.graph.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by technique on 11/22/15.
 */
public class TestDijkstras {


    private GraphBuilder<String> stringGraphBuilder;
    private GraphBuilder<VertexData> pathAwareGraphBuilder;

    @Before
    public void setup() {

        VertexBuilder<String> stringVertexBuilder = new VertexBuilder<String>() {
            @Override
            public GraphVertex<String> getVertex() {
                return new GraphVertex<String>(this.getName());
            }
        };

        VertexBuilder<VertexData> pathAwareVertexBuilder = new VertexBuilder<VertexData>() {
            @Override
            public GraphVertex<VertexData> getVertex() {
                return new PathAwareVertex(this.getName());
            }
        };

        this.stringGraphBuilder = new GraphBuilder(stringVertexBuilder);
        this.pathAwareGraphBuilder = new GraphBuilder<>(pathAwareVertexBuilder);
    }

    @Test
    public void verifyDjikstrasOnlyWeightOneThroughout() {
        GraphVertex root = pathAwareGraphBuilder.setNumVerticies(7).setMinEdgesPerVertex(2).setMaxEdgesPerVertex(2).setMaxWeight(1).build(true);
        assertEquals(
                "(A)\n" +
                        "--(B)\n" +
                        "----(D)\n" +
                        "----(E)\n" +
                        "--(C)\n" +
                        "----(F)\n" +
                        "----(G)", root.asStringDeep(0).trim());
        Dijkstras dijkstras = new Dijkstras(root);
        VertexPath vertexPath = dijkstras.execute("D");

        assertEquals("Expected totalDistance to be equal to the sum of both edges", Integer.valueOf(2), vertexPath.getTotalDistance());
        List<GraphVertex<VertexData>> verticies = vertexPath.getVerticies();
        assertEquals("Expected verticies A,B,D", "A", verticies.get(0).getName());
        assertEquals("Expected verticies A,B,D", "B", verticies.get(1).getName());
        assertEquals("Expected verticies A,B,D", "D", verticies.get(2).getName());
    }



    @Test
    public void nonTreeGraphsIsPrintable() {
        GraphVertex root = pathAwareGraphBuilder.setNumVerticies(26).build(false);
        System.out.println(root.asWeightedStringDeep(0,0));
        Dijkstras dijkstras = new Dijkstras(root);
        VertexPath vertexPath = dijkstras.execute("I");
        System.out.println(vertexPath);

    }
}
