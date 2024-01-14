package views;

import java.util.Arrays;

/**
 * Helps to keep track of the board.
 * Doesn't matter the layout.
 */
public class Board {
    public final int fieldSize;
    public final int numPlayers;
    public final int numPieces;
    public final int numHouses = 5; // we only display the first 5
                                    // for more, open the popup window
    public final int padding = 20; // in all directions a bit of space
    public int width;
    public int height;
    public final double attractToCenterStrength = 3.0;
    public final int[] startingPosIndices;// = new int[numPlayers];
    public final int[][] fieldCoordinates;// = new int[fieldSize][2]; // for each field their coordinate onscreen
    public final int[][][] houseCoordinates;// = new int[numPlayers][numPieces][2]; // for each player, for each of the 4 house-fields, their coordinate
    public final int[] drawCardFields;
    public Board(int fieldSize, int numPlayers, int numPieces) {
        this.fieldSize = fieldSize;
        this.numPlayers = numPlayers;
        this.numPieces = numPieces;
        startingPosIndices = new int[numPlayers];
        fieldCoordinates = new int[fieldSize][2];
        houseCoordinates = new int[numPlayers][Math.min(numPieces, numHouses+1)][2]; // +1 for the ellipse
        drawCardFields = new int[numPlayers];
        // distribute the players in equal distance from each other
        for (int i = 0; i < numPlayers; i++) {
            startingPosIndices[i] = (i * fieldSize / numPlayers);
            drawCardFields[i] = (int) ((i+0.5) * fieldSize / numPlayers);
        }
        calculateCoordinates();
    }

    /**
     * Fills field- and house-Coordinates.
     * Provide width and height.
     */
    private void calculateCoordinates() {
        // at first, (0,0) is the center, we'll move the entire thing over
        // after positionFields, when we know much the board will extend outwards

        positionFields();

        // gather all coordinates in one array
        int[][] allPoints = new int[fieldSize + numPlayers * (houseCoordinates[0].length)][2];
        int pointIndex = 0;
        for (int i = 0; i < fieldSize; i++) {
            allPoints[pointIndex++] = fieldCoordinates[i];
        }
        for (int i = 0; i < numPlayers; i++) {
            for (int h = 0; h < houseCoordinates[i].length; h++) {
                allPoints[pointIndex++] = houseCoordinates[i][h];
            }
        }

        // calculate size of board
        Arrays.sort(allPoints, (p1, p2) -> p1[0]-p2[0]);
        int minX = allPoints[0][0] - padding; int maxX = allPoints[allPoints.length-1][0] + padding;
        Arrays.sort(allPoints, (p1, p2) -> p1[1]-p2[1]);
        int minY = allPoints[0][1] - padding; int maxY = allPoints[allPoints.length-1][1] + padding;
        width = maxX - minX; height = maxY - minY;

        // shift the entire coordinate-system, so that (0,0) is in the top left
        shiftAll(-minX, -minY);
    }

    /*
    shifts all coordinates by dx, dy
     */
    private void shiftAll(int dx, int dy) {
        for (int i = 0; i < fieldSize; i++) {
            fieldCoordinates[i][0] += dx;
            fieldCoordinates[i][1] += dy;
        }
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < houseCoordinates[i].length; j++) {
                houseCoordinates[i][j][0] += dx;
                houseCoordinates[i][j][1] += dy;
            }
        }
    }
    /*
    If the field index is starting pos of player j, returns j.
    Otherwise, returns -1
     */
    public int whoseStartingPosIndex(int index) {
        for (int j = 0; j < numPlayers; j++) {
            if (startingPosIndices[j] == index) {
                return j;
            }
        }
        return -1;
    }
    private void positionFields() {
        // make a wobbly circle with house-"arms"
        double radius = 3.0 * (double) fieldSize + 50.0;
        double[] fieldAngle = new double[fieldSize]; // fill during first loop, use during second
        for (int i = 0; i < fieldSize; i++) {
            // calculate values
            double angle = 2 * Math.PI * (double) i / fieldSize;
            double fieldsPerPlayer = (double) fieldSize / numPlayers;
            double r = Math.cos(2*Math.PI * (i % fieldsPerPlayer) / fieldsPerPlayer);
            fieldAngle[i] = angle;
            int x = (int) (Math.cos(angle) * (-r * radius/15 + 1.2*radius));
            int y = (int) (Math.sin(angle) * (-r * radius/15 + 1.2*radius));
            fieldCoordinates[i] = new int[]{x, y};
        }
        // trail the house positions next to the player starting pos
        for (int i = 0; i < numPlayers; i++) {
            int tempRadius = 0;
            double angle = fieldAngle[startingPosIndices[i]];
            int[] startCoord = fieldCoordinates[startingPosIndices[i]];
            for (int h = 0; h < houseCoordinates[i].length; h++) {
                //angle += Math.PI / 10;
                tempRadius += 30;
                houseCoordinates[i][h] = new int[]{startCoord[0] + (int) (Math.cos(angle) * tempRadius), startCoord[1] + (int) (Math.sin(angle) * tempRadius)};
            }
        }
        positionFieldsNaturally();
    }
    private void positionFieldsNaturally() {
        if (fieldSize > 300) {
            // Algorithm has quadratic time which is too inefficient
            // we better be doing hardcoded instead to not fry the hell outta the client's pc
            positionFieldsNaturallyButHardcoded(); // not yet implemented
            return;
        }
        // for not that many fields, this algorithm is  -*- beautiful -*-
        class Vertex {
            public double x;
            public double y;
            public double[] velocity;
            public final double repelStrength = 1000;
            public double random() {
                return 0.1*(Math.random()*2-1);
            }
            public Vertex(double x, double y) {
                this.x = x;
                this.y = y;
                velocity = new double[] {0, 0};
            }
            public void update() {
                x += velocity[0];
                y += velocity[1];
                velocity[0] = 0;
                velocity[1] = 0;
            }
            private double[] getRepelFrom(Vertex pos, double strength) {
                if (this == pos) {
                    return new double[]{0,0};
                }
                double[] posToSelf = pos.vecTo(this);
                double distanceSquared = Math.pow(distanceBetween(new double[]{0,0}, posToSelf), 2);
                if (distanceSquared == 0) {
                    distanceSquared = 1;
                }
                strength /= distanceSquared;
                scaleToLength(posToSelf, strength);
                return posToSelf;
            }
            private double[] getAttractTo(Vertex pos, double strength) {
                double[] posToSelf = pos.vecTo(this);
                strength *= Math.pow(distanceBetween(new double[]{0,0}, posToSelf), 2);
                scaleToLength(posToSelf, -strength);
                return posToSelf;
            }
            public void addRepelForces(Vertex other, double strength, boolean attract) {
                double[] otherToSelf;
                if (attract) {
                    otherToSelf = getAttractTo(other, strength);
                } else {
                    otherToSelf = getRepelFrom(other, strength);
                }
                velocity[0] += otherToSelf[0] + random();
                velocity[1] += otherToSelf[1] + random();
                other.velocity[0] -= otherToSelf[0] + random();
                other.velocity[1] -= otherToSelf[1] + random();
            }
            public double[] vecTo(Vertex dest) {
                return new double[] {dest.x - x, dest.y - y};
            }
            public double distanceBetween(double[] a, double[] b) {
                return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
            }
            private void scaleToLength(double[] vector, double targetLength) {
                double length = distanceBetween(new double[]{0,0}, vector);
                double factor = targetLength / length;
                vector[0] *= factor;
                vector[1] *= factor;
            }
        }

        class Edge {
            public final double strength = 0.001;
            public Vertex v1;
            public Vertex v2;
            public Edge(Vertex v1, Vertex v2) {
                this.v1 = v1;
                this.v2 = v2;
            }
            public void contract() {
                v1.addRepelForces(v2, strength, true);
            }
        }
        // model the fields using vertices and edges
        Vertex[] vertices = new Vertex[fieldCoordinates.length + houseCoordinates.length * houseCoordinates[0].length];
        int vertexIndex = 0;
        for (int i = 0; i < fieldCoordinates.length; i++) {
            vertices[vertexIndex++] = new Vertex(fieldCoordinates[i][0], fieldCoordinates[i][1]);
        }
        for (int i = 0; i < houseCoordinates.length; i++) {
            for (int j = 0; j < houseCoordinates[i].length; j++) {
                vertices[vertexIndex++] = new Vertex(houseCoordinates[i][j][0], houseCoordinates[i][j][1]);
            }
        }
        Edge[] edges = new Edge[vertices.length];
        Vertex prev = vertices[fieldCoordinates.length-1];
        int edgeIndex = 0;
        for (int i = 0; i < fieldCoordinates.length; i++) {
            edges[edgeIndex] = new Edge(prev, vertices[edgeIndex]);
            prev = vertices[i];
            edgeIndex++;
        }
        for (int player = 0; player < houseCoordinates.length; player++) {
            prev = vertices[startingPosIndices[player]];
            for (int j = 0; j < houseCoordinates[player].length; j++) {
                edges[edgeIndex] = new Edge(prev, vertices[edgeIndex]);
                prev = vertices[edgeIndex];
                edgeIndex++;
            }
        }
        // simulate
        double correctedCenterStrength = attractToCenterStrength*Math.exp(-0.005*vertices.length)*Math.pow(10, -5);
        for (int i = 0; i < 200; i++) {
            for (Vertex v1 : vertices) {
                for (Vertex v2 : vertices) {
                    v1.addRepelForces(v2, v1.repelStrength, false);
                }
            }
            for (Edge edge : edges) {
                edge.contract();
            }
            for (Vertex v : vertices) {
                v.addRepelForces(new Vertex(0, 0), correctedCenterStrength, true);
                v.update();
            }
        }
        // convert back
        vertexIndex = 0;
        for (int i = 0; i < fieldCoordinates.length; i++) {
            fieldCoordinates[i][0] = (int) vertices[vertexIndex].x;
            fieldCoordinates[i][1] = (int) vertices[vertexIndex++].y;
        }
        for (int i = 0; i < houseCoordinates.length; i++) {
            for (int j = 0; j < houseCoordinates[i].length; j++) {
                houseCoordinates[i][j][0] = (int) vertices[vertexIndex].x;
                houseCoordinates[i][j][1] = (int) vertices[vertexIndex++].y;
            }
        }
    }
    private void positionFieldsNaturallyButHardcoded() {
        double radiusDifference = 50;
        double edgeSpacing = 30;
        double vertexSpacing = 50;
        double minInnerRadius = 100;   // 3.3 because 3.0 could be too close
        double shiftHousesToCenter = 0.7*radiusDifference;
        double innerRadius = Math.max((3.3*vertexSpacing) * numPlayers / (2*Math.PI), minInnerRadius);
        // -> big enough to fit 3*vertexSpacing on the outline for each player
        for (int player = 0; player < numPlayers; player++) {
            double angle = 2*Math.PI * player / numPlayers;
            fieldCoordinates[startingPosIndices[player]][0] = (int) (Math.cos(angle) * (innerRadius - shiftHousesToCenter));
            fieldCoordinates[startingPosIndices[player]][1] = (int) (Math.sin(angle) * (innerRadius - shiftHousesToCenter));
            for (int h = 0; h < houseCoordinates[player].length; h++) {
                houseCoordinates[player][h][0] = (int) (Math.cos(angle) * (innerRadius + (h+1)*(radiusDifference) - shiftHousesToCenter));
                houseCoordinates[player][h][1] = (int) (Math.sin(angle) * (innerRadius + (h+1)*(radiusDifference) - shiftHousesToCenter));
            }
            int nextPlayer;
            int numFieldsToPosition;
            if (player == numPlayers - 1) {
                nextPlayer = 0;
                numFieldsToPosition = fieldCoordinates.length - startingPosIndices[player];
            } else {
                nextPlayer = player +1;
                numFieldsToPosition = startingPosIndices[nextPlayer] - startingPosIndices[player];
            }
            int[][][] sectionCoordinates = new int[numFieldsToPosition][2][2];
            int layer = 0;
            int layerFullness = 0;
            for (int i = 0; i < numFieldsToPosition; i++) {
                double layerRadius = innerRadius + layer * radiusDifference;
                //double arcDistance = edgeSpacing * (layerFullness - 2) + 3*vertexSpacing;
                double arcDistance = edgeSpacing * layerFullness + vertexSpacing;
                double newAngle = angle + arcDistance / layerRadius;
                if ((arcDistance+vertexSpacing) / layerRadius >= 2*Math.PI / numPlayers) {
                    // end of line
                    // adjust last vertex of previous row
                    sectionCoordinates[i-1][0][0] = (int) (Math.cos(angle + 2*Math.PI / numPlayers - vertexSpacing / layerRadius) * (layerRadius + 0.5*radiusDifference));
                    sectionCoordinates[i-1][0][1] = (int) (Math.sin(angle + 2*Math.PI / numPlayers - vertexSpacing / layerRadius) * (layerRadius + 0.5*radiusDifference));
                    // now begin new row
                    layer++;
                    layerFullness = 0;
                    layerRadius = innerRadius + layer * radiusDifference;
                    arcDistance = edgeSpacing * layerFullness + vertexSpacing;
                    newAngle = angle + arcDistance / layerRadius;
                }
                sectionCoordinates[i][0][0] = (int) (Math.cos(newAngle) * layerRadius);
                sectionCoordinates[i][0][1] = (int) (Math.sin(newAngle) * layerRadius);
                sectionCoordinates[i][1][0] = layer;
                sectionCoordinates[i][1][1] = layerFullness;
                layerFullness += 1;
            }
            // assign the calculated coordinate section to the fields
            int fieldIndex = 0;
            int indexFromBack = nextPlayer == 0 ? fieldCoordinates.length-1 : startingPosIndices[nextPlayer]-1;
            int rightmostIndex = -1; // meaning not set
            for (int sectionIndex = 0; sectionIndex < numFieldsToPosition; sectionIndex++) {
                int currentLayer = sectionCoordinates[sectionIndex][1][0];
                if (sectionIndex >= sectionCoordinates.length-1 || sectionCoordinates[sectionIndex + 1][1][0] > currentLayer) {
                    // to the right, leave one line of fields to the right for the way back
                    fieldCoordinates[indexFromBack] = sectionCoordinates[sectionIndex][0];
                    indexFromBack--;
                } else {
                    if (currentLayer % 2 == layer % 2) {
                        rightmostIndex = -1;
                        fieldCoordinates[startingPosIndices[player]+1 + (fieldIndex++)] = sectionCoordinates[sectionIndex][0];
                    } else {
                        if (rightmostIndex == -1) {
                            rightmostIndex = fieldIndex;
                            int lookAhead = 2;
                            while (sectionCoordinates[sectionIndex + lookAhead++][1][0] == currentLayer) {
                                rightmostIndex++;
                            }
                            rightmostIndex += fieldIndex;
                        }
                        fieldCoordinates[startingPosIndices[player]+1 + (rightmostIndex-fieldIndex++)] = sectionCoordinates[sectionIndex][0];
                    }
                }
            }
        }
    }
}