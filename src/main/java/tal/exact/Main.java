package tal.exact;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Generator.generateRandomGraphEdgesJson(3, 6, "test.json");

        // In order for algorithm to present color vector with maintained order
        // Each edge should be larger than previous (meaning that src1 < src2 or dest1 < dest2 if src1 == src2)
        String json = FileOperations.getJsonStringFromFileInResources("firstGraphEdges.json");
        List<Edge> edgeList = FileOperations.getEdgesListFromJsonString(json);

        // Graph creation
        Graph graph = new Graph(edgeList);
        graph.printAdjacencyMatrix();

        // From this place we check complexity
        int[] colorVector = graph.performCompleteColoringAlgorithm();
        Arrays.stream(colorVector).forEach(System.out::print);
    }
}
