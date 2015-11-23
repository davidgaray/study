package org.garay.study.algorithms;

import org.garay.study.datastructures.graph.GraphEdge;
import org.garay.study.datastructures.graph.GraphVertex;
import org.garay.study.datastructures.graph.VertexData;
import org.garay.study.datastructures.graph.VertexPath;

import java.util.PriorityQueue;

/**
 * Created by technique on 11/21/15.
 */
public class Dijkstras {

    private PriorityQueue<EdgeDistance> minHeap;

    private GraphVertex<VertexData> root;
    public Dijkstras(GraphVertex<VertexData> root) {
        this.root = root;
    }

    public VertexPath execute(String targetName) {
        minHeap = new PriorityQueue<>();
        //initialize local edges
        for(GraphEdge<VertexData> curEdge: root.getEdges()) {
            minHeap.add(new EdgeDistance(curEdge, curEdge.getWeight()));
        }

        //keep visiting the shortest path
        while(!minHeap.isEmpty()) {
            VertexPath vertexPath = visitNextEdge(targetName);
            if(vertexPath != null) {
                return vertexPath;
            }
        }

        //Unable to find path
        return null;
    }

    private VertexPath visitNextEdge(String targetName) {
        //TDA:
        // -exit early if shorter path already written to edge
        // -construct new path
        // -if destination is found return shortest path
        // -otherwise, add all adjacent edges of newest vertex
        EdgeDistance closestEdgeDistance = minHeap.poll();
        GraphEdge<VertexData> edge = closestEdgeDistance.getEdge();
        VertexData fromVertexData = edge.getFromVertex().getData();
        VertexPath fromVertexPath = fromVertexData.getVertexPath();
        VertexData toVertexData = edge.getToVertex().getData();
        VertexPath toVertexPath = toVertexData.getVertexPath();
        if(toVertexPath != null && toVertexPath.getTotalDistance() <= closestEdgeDistance.getTotalDistance()) {
            //when an existing path exists, it should be a shorter path,
            // we verify and do not overwrite
            //TODO: validate this with defensive programming
            return null;
        }

        if(fromVertexPath == null){
            //first node traveled, create path
            toVertexPath = new VertexPath(edge);
        } else {
            toVertexPath = fromVertexPath.newPath(edge.getToVertex(), edge.getWeight());
        }
        toVertexData.setVertexPath(toVertexPath);

        if(edge.getToVertex().getName().equals(targetName)) {
            //we've found it
            return toVertexPath;

        }

        for(GraphEdge<VertexData> curEdge: edge.getToVertex().getEdges()) {
            //TODO: check if already visited
            minHeap.add(new EdgeDistance(curEdge, toVertexPath.getTotalDistance() + curEdge.getWeight()));
        }

        return null;
    }

    private class EdgeDistance implements Comparable{
        GraphEdge<VertexData> edge;
        int totalDistance;

        public EdgeDistance(GraphEdge<VertexData> edge, int totalDistance) {
            this.edge = edge;
            this.totalDistance = totalDistance;
        }

        public GraphEdge<VertexData> getEdge() {
            return edge;
        }

        public int getTotalDistance() {
            return totalDistance;
        }

        @Override
        public int compareTo(Object o) {
            EdgeDistance other = (EdgeDistance)o;
            return this.getTotalDistance() - other.getTotalDistance();
        }


    }


}
