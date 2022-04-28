package tal.exact;

import tal.model.Edge;

import java.util.*;

public class GraphCreator {
    private int[][] adjacencyMatrix;

    public int[][] prepareAdjacencyMatrix(List<Edge> edgeList, int nodesCount) {
        adjacencyMatrix = new int[nodesCount][nodesCount];
        edgeList.forEach((edge) -> {
            adjacencyMatrix[edge.getSrc()][edge.getDest()] = 1;
            adjacencyMatrix[edge.getDest()][edge.getSrc()] = 1;
        });
        return adjacencyMatrix;
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

    public List<Edge> convertToGraphFromEdges(List<Edge> edgeList) {
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
}
