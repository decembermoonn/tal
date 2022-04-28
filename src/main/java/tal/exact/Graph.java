package tal.exact;

import tal.model.Edge;

import java.util.*;

public class Graph {
    public final GraphCreator graphCreator = new GraphCreator();
    private final int nodesCount;
    private final int[][] adjacencyMatrix;

    public Graph(List<Edge> edgeList) {
        nodesCount = edgeList.size();
        List<Edge> convertedEdgeList = graphCreator.convertToGraphFromEdges(edgeList);
        adjacencyMatrix = graphCreator.prepareAdjacencyMatrix(convertedEdgeList, nodesCount);
    }

    public int[] performCompleteColoringAlgorithm() {
        int base = 2;
        int[] colorsVector = new int[nodesCount];

        while(base <= 10) {
            if (colorsVector == null) {
                colorsVector = new int[nodesCount];
                base++;
            }
            else {
                int finalBase = base;
                if (Arrays.stream(colorsVector).anyMatch((num) -> num == finalBase - 1)) {
                    if(coloringIsOkForCertainColorsVector(colorsVector)) return colorsVector;
                }
                colorsVector = buildNextColorsVector(colorsVector, base);
            }
        }
        return null;
    }

    private int[] buildNextColorsVector(int[] prevVector, int base) {
        int len = prevVector.length;
        prevVector[len - 1]++;

        for(int i = len - 1; i >= 0; i--) {
            if (prevVector[i] == base) {
                prevVector[i] = 0;
                if(i - 1 < 0) {
                    return null;
                }
                prevVector[i-1]++;
            }
        }
        return prevVector;
    }

    private boolean coloringIsOkForCertainColorsVector(int[] colorsVector) {
//        for (int val: colorsVector) {
//            System.out.print(val);
//        }
//        System.out.println();
        for(int columnIndex = 0; columnIndex < nodesCount; columnIndex++) {
            int[] columnVector = getAdjacencyMatrixColumn(columnIndex);
            int nodeColor = colorsVector[columnIndex];
            for(int elementIndex = columnIndex + 1; elementIndex < columnVector.length; elementIndex++) {
                if(columnVector[elementIndex] == 1) {
                    if (nodeColor == colorsVector[elementIndex]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int[] getAdjacencyMatrixColumn(int column) {
        int[] colValues = new int[nodesCount];
        if (adjacencyMatrix != null) {
            for(int i = 0; i < nodesCount; i++) {
                colValues[i] = adjacencyMatrix[i][column];
            }
        }
        return colValues;
    }
}
