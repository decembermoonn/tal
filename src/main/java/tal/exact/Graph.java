package tal.exact;

import tal.model.Edge;
import tal.model.Pair;

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
    private int createPairsCounter = 0;
    private int sortCounter = 0;
    private int findUsedColorsCounter = 0;
    private int findUnusedColorCounter = 0;

    private int heuristicMemoryUsed = 0;

    public void printCounters() {
        System.out.println("checkNewColorVectorCounter:" + checkNewColorVectorCounter);
        System.out.println("checkNewColorVectorColorInsideMatrixCounter:" + checkNewColorVectorColorInsideMatrixCounter);
        System.out.println("buildNewColorVectorCounter:" + buildNewColorVectorCounter);
        System.out.println("całkowita liczba operacji:" +
                (checkNewColorVectorColorInsideMatrixCounter + buildNewColorVectorCounter));
    }

    public void printHeuristicCountes() {
        System.out.println("createPairsCounter: " + createPairsCounter);
        System.out.println("sortCounter: " + sortCounter);
        System.out.println("findUsedColorsCounter: " + findUsedColorsCounter);
        System.out.println("findUnusedColorCounter: " + findUnusedColorCounter);
        System.out.println("całkowita liczba operacji: " + (createPairsCounter + sortCounter + findUsedColorsCounter + findUnusedColorCounter));
    }

    public void printHeuristicMemoryUsed(int key) {
        System.out.println(key + " - Currently used memory (ints):" + heuristicMemoryUsed);
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

    public int[] performHeuristicLFColoringAlgorithm() {

        if (nodesCount == 1) return new int[]{0};
        int[] colorsVector = new int[nodesCount];

        heuristicMemoryUsed += nodesCount;

        //Set node colors to impossible value
        Arrays.fill(colorsVector, -1);

        List<Pair> nodeDegreeList = new ArrayList<>();
        int degreeCounter;

        for(int i=0; i<nodesCount; i++) {   //Create list of pairs (nodeId, nodeDegree)

            degreeCounter = 0;

            for(int j=0; j<nodesCount; j++) {
                if(adjacencyMatrix[i][j] == 1)
                {
                    degreeCounter++;
                }
            }
            nodeDegreeList.add(new Pair(i, degreeCounter));
            createPairsCounter++;
            heuristicMemoryUsed += 2;
        }

        nodeDegreeList.sort((o1, o2) -> {   //Sort descendingly by node degree
            sortCounter++;
            if(o1.getValue() < o2.getValue()) return 1;
            else if(o1.getValue() > o2.getValue()) return -1;
            return 0;
        });

        int color;
        int nodeId;
        List<Integer> usedColors;
        for(Pair p : nodeDegreeList) {
            usedColors = new ArrayList<>();
            color = 0;
            nodeId = p.getKey();

            for(int i=0; i<nodesCount; i++) { //Find used colors for adjacent nodes
                if(adjacencyMatrix[nodeId][i] == 1) {
                    usedColors.add(colorsVector[i]);
                    findUsedColorsCounter++;

                }
            }

            usedColors.sort((i1, i2) -> { //Sort ascending by color values
                sortCounter++;
                if(i1 < i2) return -1;
                else if(i1 > i2) return 1;
                return 0;
            });

            for(int usedColor: usedColors) { //Find the lowest unused color
                if(color == usedColor)  {
                    color++;
                    findUnusedColorCounter++;
                }
            }

            colorsVector[nodeId] = color;

            heuristicMemoryUsed += usedColors.size();
            printHeuristicMemoryUsed(p.getKey());
            heuristicMemoryUsed -= usedColors.size();
//            for(int n: usedColors) {
//                System.out.print(n + " ");
//            }
//            System.out.println();
//            for(int n: colorsVector) {
//                System.out.print(n + " ");
//            }
//            System.out.println();
        }

        return colorsVector;
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
