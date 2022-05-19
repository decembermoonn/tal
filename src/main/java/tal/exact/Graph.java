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

    // THE CORE OF COLORING ALGORITHM
    // ONLY THIS PART IS EXAMINED

    public int[] performCompleteColoringAlgorithm() {
        int[] colorsVector = new int[nodesCount];
        int base = 2;

        while(base <= nodesCount) {
            if(colorsVector != null) {
                for(int color : colorsVector) {
                    if (color == base - 1) {
                        if(coloringIsOkForCertainColorsVector(colorsVector)) return colorsVector;
                        else break;
                    }
                }
                colorsVector = buildNextColorsVector(colorsVector, base);
            } else {
                colorsVector = new int[nodesCount];
                base++;
            }
        }
        return null;
    }

    private boolean coloringIsOkForCertainColorsVector(int[] colorsVector) {
        for(int columnIndex = 0; columnIndex < nodesCount - 1; columnIndex++) {
            int nodeColor = colorsVector[columnIndex];
            for(int elementIndex = columnIndex + 1; elementIndex < nodesCount; elementIndex++) {
                if(adjacencyMatrix[elementIndex][columnIndex] == 1) {
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

        for(int i = prevVector.length - 1; i >= 0; i--) {
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
}
