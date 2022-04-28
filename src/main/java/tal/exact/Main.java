package tal.exact;

import tal.model.Edge;
import tal.utils.FileOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // In order for algorithm to present color vector with maintained order
        // Each edge should be larger than previous (meaning that src1 < src2 or dest1 < dest2 if src1 == src2)
        String jsonString = FileOperations.getJsonStringFromFileInResources("generated.json");
        List<Edge> edgeList = FileOperations.getEdgesListFromJsonString(jsonString);

        // Graph creation
        Graph graph = new Graph(edgeList);
        graph.graphCreator.printAdjacencyMatrix();

        // From this place we check complexity
        int[] colorVector = graph.performCompleteColoringAlgorithm();
        Arrays.stream(colorVector).forEach(System.out::print);
    }
}
