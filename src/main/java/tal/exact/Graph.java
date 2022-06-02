package tal.exact;

import tal.model.Edge;

import java.util.*;

public class Graph {
    // IN-DATA AND SOME HELPER FUNCTIONS

    public final GraphCreator graphCreator = new GraphCreator();
    private final int nodesCount;
    private final int[][] adjacencyMatrix;

    public Graph(List<Edge> edgeList) {
        nodesCount = edgeList.size();
        List<Edge> convertedEdgeList = graphCreator.convertToGraphFromEdges(edgeList);
        adjacencyMatrix = graphCreator.prepareAdjacencyMatrix(convertedEdgeList, nodesCount);
    }

    private int checkNewColorVectorCounter = 0;
    private int checkNewColorVectorColorInsideMatrixCounter = 0;
    private int buildNewColorVectorCounter = 0;

    public void printCounters() {
        System.out.println("checkNewColorVectorCounter:" + checkNewColorVectorCounter);
        System.out.println("checkNewColorVectorColorInsideMatrixCounter:" + checkNewColorVectorColorInsideMatrixCounter);
        System.out.println("buildNewColorVectorCounter:" + buildNewColorVectorCounter);
        System.out.println("całkowita liczba operacji:" +
                (checkNewColorVectorColorInsideMatrixCounter + buildNewColorVectorCounter));
    }

    // THE CORE OF COLORING ALGORITHM
    // ONLY THIS PART IS EXAMINED

    public int[] performCompleteColoringAlgorithm() {
        if (nodesCount == 1) return new int[]{0};
        int[] colorsVector = new int[nodesCount];
        int base = 2;

        while (base <= nodesCount) {
            if (colorsVector != null) {
//                Arrays.stream(colorsVector).forEach(System.out::print);
//                System.out.println();
                checkNewColorVectorCounter++;
                if (coloringIsOkForCertainColorsVector(colorsVector)) return colorsVector;
                colorsVector = buildNextColorsVector(colorsVector, base);
            } else {
                colorsVector = new int[nodesCount];
                base++;
            }
        }

        return null;
    }

    private boolean coloringIsOkForCertainColorsVector(int[] colorsVector) {
        for (int columnIndex = 0; columnIndex < nodesCount - 1; columnIndex++) {
            int nodeColor = colorsVector[columnIndex];
            for (int elementIndex = columnIndex + 1; elementIndex < nodesCount; elementIndex++) {
                checkNewColorVectorColorInsideMatrixCounter++;
                if (adjacencyMatrix[elementIndex][columnIndex] == 1) {
                    if (nodeColor == colorsVector[elementIndex]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int[] buildNextColorsVector(int[] prevVector, int base) {
        prevVector[prevVector.length - 1]++;

        for (int i = prevVector.length - 1; i >= 0; i--) {
            buildNewColorVectorCounter++;
            if (prevVector[i] == base) {
                prevVector[i] = 0;
                if (i - 1 < 0) {
                    return null;
                }
                prevVector[i - 1]++;
            }
        }
        return prevVector;
    }
}
