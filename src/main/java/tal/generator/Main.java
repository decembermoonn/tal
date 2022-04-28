package tal.generator;

import tal.model.Edge;
import tal.utils.FileOperations;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Edge> edgeList = createRandomGraph();
        saveEdgeListAsJsonFile(edgeList);
    }

    /**
     * Creates undirectional graph with random number of vertices (2 - 6)
     * and random connections between those vertices.
     */
    private static List<Edge> createRandomGraph() {
        List<Edge> edgeList = new ArrayList<>();
        int vertices = 1 + (int) Math.ceil(Math.random() * 5);
        return fillListWithVertices(edgeList, vertices);
    }

    private static List<Edge> fillListWithVertices(List<Edge> edgeList, int totalVertices) {
        for (int row = 0; row < totalVertices; row++) {
            for (int column = row + 1; column < totalVertices; column++) {
                boolean shouldConnect = Math.random() > 0.3;
                if (shouldConnect) {
                    Edge edge = new Edge();
                    edge.setSrc(row);
                    edge.setDest(column);
                    edgeList.add(edge);
                }
            }
        }
        return edgeList;
    }

    /**
     * Saves graph as json to 'generated.json' file.
     * Objects order is compatible with application requirements.
     */
    private static void saveEdgeListAsJsonFile(List<Edge> edgeList) {
        String json = FileOperations.makeJsonFromEdgesList(edgeList);
        FileOperations.saveJsonString(json);
    }
}
