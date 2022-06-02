package tal.generator;

import tal.model.Edge;
import tal.utils.FileOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random random = new Random();

    public static void main(String[] args) {
        List<Edge> edgeList = createRandomGraph();
        saveEdgeListAsJsonFile(edgeList);
    }

    /**
     * Creates undirectional graph with random number of vertices (2 - 6)
     * and random connections between those vertices.
     */
    private static List<Edge> createRandomGraph() {
        Scanner s = new Scanner(System.in);
        int verticesCount, edgesCount;
        do {
            System.out.println("Podaj liczbę wierzchołków (1-10): ");
            verticesCount = s.nextInt();
        } while(verticesCount < 1 || verticesCount > 10);
        int maxEdgesCount = verticesCount * (verticesCount - 1) / 2;
        do {
            System.out.println("Podaj liczbę krawędzi: (0-" + maxEdgesCount + "): " );
            edgesCount = s.nextInt();
        } while(edgesCount < 0 || edgesCount > maxEdgesCount);

        return fillListWithVertices(verticesCount, edgesCount);
    }

    private static List<Edge> fillListWithVertices(int verticesCount, int edgesCount) {
        List<Edge> edgeList = new ArrayList<>();

        while(edgeList.size() < edgesCount) {
            int sourceVertex = random.nextInt(0, verticesCount);
            int destVertces = random.nextInt(0, verticesCount);
            Edge edge = createEdge(sourceVertex, destVertces);
            if(edgeIsOk(edgeList, edge)) edgeList.add(edge);
        }

        return sortList(edgeList);
    }

    private static List<Edge> sortList(List<Edge> edgeList) {
        edgeList.sort((e1, e2) -> {
            if(e1.getSrc() < e2.getSrc()) return -1;
            if(e1.getSrc() == e2.getSrc()) {
                if(e1.getDest() < e2.getDest()) return -1;
                if(e1.getDest() == e2.getDest()) return 0;
                if(e1.getDest() > e2.getDest()) return 1;
            }
            if(e1.getSrc() > e2.getSrc()) return 1;
            return 0;
        });
        return edgeList;
    }

    private static boolean edgeIsOk(List<Edge> edgeList, Edge edge) {
        return edge.getDest() != edge.getSrc() &&
                edgeList.stream().noneMatch(edgeFromList -> areEdgesTheSame(edgeFromList, edge));
    }

    private static boolean areEdgesTheSame(Edge edgeFromList, Edge edge) {
        return
                (edgeFromList.getDest() == edge.getDest() && edgeFromList.getSrc() == edge.getSrc()) ||
                        (edgeFromList.getDest() == edge.getSrc() && edgeFromList.getSrc() == edge.getDest());
    }

    private static Edge createEdge(int source, int destination) {
        Edge edge = new Edge();
        edge.setSrc(source);
        edge.setDest(destination);
        return edge;
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
