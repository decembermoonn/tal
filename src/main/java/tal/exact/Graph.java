package tal.exact;

import java.util.*;

public class Graph {
    private int[][] adjacencyMatrix;
    private final int nodesCount;

    // --- GRAPH CREATION ----------------------------------------------------------------------------------------------
    // --- might be buggy, but works -----------------------------------------------------------------------------------

    public Graph(List<Edge> edgeList) {
        List<Edge> convertedEdgeList = convertToGraphFromEdges(edgeList);
        convertedEdgeList.forEach(System.out::println);
        nodesCount = edgeList.size();
        prepareAdjacencyMatrix(convertedEdgeList);
    }

    public void printAdjacencyMatrix() {
        if (adjacencyMatrix != null) {
            for(int[] row: adjacencyMatrix) {
                for(int value: row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            return;
        }
        System.out.println("No matrix");
    }

    private List<Edge> convertToGraphFromEdges(List<Edge> edgeList) {
        List<Edge> converedEdgeList = new ArrayList<>();
        for(int i = 0; i < edgeList.size(); i++) {
            Edge outerEdge = edgeList.get(i);
            int oSrc = outerEdge.getSrc();
            int oDest = outerEdge.getDest();
            for(int j = i + 1; j < edgeList.size(); j++) {
                Edge innerEdge = edgeList.get(j);
                int iSrc = innerEdge.getSrc();
                int iDest = innerEdge.getDest();
                if(oSrc == iSrc || oSrc == iDest || oDest == iSrc || oDest == iDest) {
                    int nSrc = oSrc * 10 + oDest;
                    int nDest = iSrc * 10 + iDest;
                    if(converedEdgeList.stream().noneMatch(e -> e.getSrc() == nSrc && e.getDest() == nDest )) {
                        Edge e = new Edge();
                        e.setSrc(nSrc);
                        e.setDest(nDest);
                        converedEdgeList.add(e);
                    }
                }
            }
        }
        return makeProperIndexes(converedEdgeList);
    }

    private List<Edge> makeProperIndexes(List<Edge> edgeList) {
        Set<Integer> uniqueIndexes = new TreeSet<>();
        edgeList.forEach(e -> uniqueIndexes.addAll(Arrays.asList(e.getDest(), e.getSrc())));
        Map<Integer, Integer> oldIdToNewIdMap = new HashMap<>();
        List<Integer> uniqueIndexesList = uniqueIndexes.stream().toList();
        for(int i = 0; i < uniqueIndexes.size(); i++) {
            oldIdToNewIdMap.put(uniqueIndexesList.get(i), i);
        }
        return edgeList.stream().peek(e -> {
            e.setSrc(oldIdToNewIdMap.get(e.getSrc()));
            e.setDest(oldIdToNewIdMap.get(e.getDest()));
        }).toList();
    }

    private void prepareAdjacencyMatrix(List<Edge> edgeList) {
        adjacencyMatrix = new int[nodesCount][nodesCount];
        edgeList.forEach((edge) -> {
            adjacencyMatrix[edge.getSrc()][edge.getDest()] = 1;
            adjacencyMatrix[edge.getDest()][edge.getSrc()] = 1;
        });
    }

    // --- GRAPH COLORING ----------------------------------------------------------------------------------------------
    /*
        We assume that we have adjacency matrix built from
        graph where nodes represents edges and edges
        represents connections of those edges
    */

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
