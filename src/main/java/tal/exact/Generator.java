package tal.exact;

public class Generator {
    public static void generateRandomGraphEdgesJson(int vertices, int edges, String fileName) {
        edges = edges > (vertices * (vertices - 1))/2 ? vertices : edges;
        System.out.println(edges);
    }
}
