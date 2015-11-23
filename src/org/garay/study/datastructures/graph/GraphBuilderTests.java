package org.garay.study.datastructures.graph;

import org.garay.study.algorithms.Dijkstras;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by technique on 11/18/15.
 */
public class GraphBuilderTests {

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
    public void ensureThatWeCanBuildASingleVertex() {
        GraphVertex vertex = stringGraphBuilder.setNumVerticies(1).build(true);
        assertEquals("(A)", vertex.asStringDeep(0).trim());
    }

    @Test
    public void ensureThatWeCanBuildAVertexWithTwoEdges() {
        GraphBuilder builder = stringGraphBuilder.setNumVerticies(3).setMinEdgesPerVertex(2).setMaxEdgesPerVertex(2);
        GraphVertex vertex = builder.build(true);
        assertEquals("(A)" +
                "\n--(B)" +
                "\n--(C)", vertex.asStringDeep(0).trim());
    }

    @Test
    public void ensureThatWeCanBuildSevenVerticiesWithWhereEachGetsTwoEdges() {
        GraphVertex vertex = stringGraphBuilder.setNumVerticies(7).setMinEdgesPerVertex(2).setMaxEdgesPerVertex(2).build(true);
        assertEquals(
                "(A)\n" +
                        "--(B)\n" +
                        "----(D)\n" +
                        "----(E)\n" +
                        "--(C)\n" +
                        "----(F)\n" +
                        "----(G)", vertex.asStringDeep(0).trim());
    }

    @Ignore
    @Test
    public void printDeepGraph() {
        GraphVertex vertex = stringGraphBuilder.setNumVerticies(100).setMinEdgesPerVertex(1).setMaxEdgesPerVertex(5).build(true);
        System.out.println(vertex.asWeightedStringDeep(0, 0));
    }

    @Test
    public void simplePathAwareGraph() {
        GraphVertex<VertexData> root = pathAwareGraphBuilder.setNumVerticies(7).setMinEdgesPerVertex(2)
                .setMaxEdgesPerVertex(2).setMaxWeight(1).build(true);
        assertEquals(
                "(A)\n" +
                        "--(B)\n" +
                        "----(D)\n" +
                        "----(E)\n" +
                        "--(C)\n" +
                        "----(F)\n" +
                        "----(G)", root.asStringDeep(0).trim());

        GraphEdge<VertexData> abEdge = root.getEdgeByVertexName("B");
        GraphVertex<VertexData> bVertex = abEdge.getToVertex();
        VertexData bVertexData = bVertex.getData();
        bVertexData.setVertexPath(new VertexPath(abEdge));

        GraphEdge<VertexData> bdEdge = bVertex.getEdgeByVertexName("D");
        GraphVertex<VertexData> dVertex = bdEdge.getToVertex();
        VertexData dVertexData = dVertex.getData();
        VertexPath abdPath = bVertexData.getVertexPath().newPath(dVertex, bdEdge.getWeight());
        dVertexData.setVertexPath(abdPath);

        assertEquals("Expected totalDistance to be equal to the sum of both edges", Integer.valueOf(2), abdPath.getTotalDistance());
        List<GraphVertex<VertexData>> verticies = abdPath.getVerticies();
        assertEquals("Expected verticies A,B,D", "A", verticies.get(0).getName());
        assertEquals("Expected verticies A,B,D", "B", verticies.get(1).getName());
        assertEquals("Expected verticies A,B,D", "D", verticies.get(2).getName());

    }

    @Test
    public void verifyDjikstrasSimplestCase() {
        GraphVertex<VertexData> root = pathAwareGraphBuilder.setNumVerticies(2).setMinEdgesPerVertex(1)
                .setMaxEdgesPerVertex(1).setMaxWeight(1).build(true);
        assertEquals(
                "(A)\n" +
                        "--(B)",
                root.asStringDeep(0).trim());
        Dijkstras dijkstras = new Dijkstras(root);
        VertexPath vertexPath = dijkstras.execute("B");
        assertEquals("Expected totalDistance to be equal to the weight of the only edge", Integer.valueOf(1), vertexPath.getTotalDistance());
        List<GraphVertex<VertexData>> verticies = vertexPath.getVerticies();
        assertEquals("Expected verticies A,B,D", "A", verticies.get(0).getName());
        assertEquals("Expected verticies A,B,D", "B", verticies.get(1).getName());
    }


    @Test
    public void verifyDjikstrasSimplestThreeVertexCase() {
        GraphVertex<VertexData> root = pathAwareGraphBuilder.setNumVerticies(3).setMinEdgesPerVertex(1)
                .setMaxEdgesPerVertex(1).setMaxWeight(1).build(true);
        assertEquals(
                "(A)\n" +
                        "--(B)\n" +
                        "----(C)",
        root.asStringDeep(0).trim());
        Dijkstras dijkstras = new Dijkstras(root);
        VertexPath vertexPath = dijkstras.execute("C");
        assertEquals("Expected totalDistance to be equal to the sum of both edges", Integer.valueOf(2), vertexPath.getTotalDistance());
        List<GraphVertex<VertexData>> verticies = vertexPath.getVerticies();
        assertEquals("Expected verticies A,B,D", "A", verticies.get(0).getName());
        assertEquals("Expected verticies A,B,D", "B", verticies.get(1).getName());
        assertEquals("Expected verticies A,B,D", "C", verticies.get(2).getName());

    }

}
