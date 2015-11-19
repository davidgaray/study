package org.garay.study.datastructures;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by technique on 11/18/15.
 */
public class GraphBuilderTests {

    @Test
    public void ensureThatWeCanBuildASingleVertex() {
        GraphBuilder builder = new GraphBuilder().setNumVerticies(1);
        GraphVertex vertex = builder.build();
        assertEquals("(A)", vertex.asStringDeep(0).trim());
    }

    @Test
    public void ensureThatWeCanBuildAVertexWithTwoEdges() {
        GraphBuilder builder = new GraphBuilder().setNumVerticies(3).setMinEdgesPerVertex(2).setMaxEdgesPerVertex(2);
        GraphVertex vertex = builder.build();
        assertEquals("(A)" +
                "\n--(B)" +
                "\n--(C)", vertex.asStringDeep(0).trim());
    }

    @Test
    public void ensureThatWeCanBuildSevenVerticiesWithWhereEachGetsTwoEdges() {
        GraphBuilder builder = new GraphBuilder().setNumVerticies(7).setMinEdgesPerVertex(2).setMaxEdgesPerVertex(2);
        GraphVertex vertex = builder.build();
        assertEquals(
                "(A)\n" +
                        "--(B)\n" +
                        "----(D)\n" +
                        "----(E)\n" +
                        "--(C)\n" +
                        "----(F)\n" +
                        "----(G)", vertex.asStringDeep(0).trim());
    }

    @Test
    public void printGraph() {
        GraphBuilder builder = new GraphBuilder().setNumVerticies(24).setMinEdgesPerVertex(1).setMaxEdgesPerVertex(5);
        GraphVertex vertex = builder.build();
        System.out.println(vertex.asWeightedStringDeep(0,0));

    }






}
