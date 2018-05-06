package me.mattgd.wildfire;

/**
 * @author mattgd
 * @version 1.03 2016/2/1
 */
class Wildfire {
    private double probCatch = 0.6;
    private double probTree = 0.8;
    private double probBurning = 0.1;
    private int[][] forest;
 
    // precondition: n is an odd number
    public Wildfire(int size) {
        forest = new int[size + 2][size + 2];
         
        for (int i = 1; i < forest.length - 1; i++) {
            for (int j = 1; j < forest.length - 1; j++) {
                if (Math.random() <= probTree) {
                    if (Math.random() <= probBurning) {
                        forest[i][j] = BlockState.BURNING.getValue();
                    } else {
                        forest[i][j] = BlockState.TREE.getValue();
                    }
                }
            }
        }
    }
     
    // This method runs one round of the simulation.
    void applySpread() {
        int[][] burningForest = new int[forest.length][forest[0].length];
         
        for (int i = 1; i < burningForest.length - 1; i++) {
            for (int j = 1; j < burningForest.length - 1; j++) {
                burningForest[i][j] = spread(i, j);
            }
        }
         
        forest = burningForest;
    }
     
    // You can use this helper method if you want...
    private int spread(int row, int col) {
        int[][] forest = getForest();
        int cell = forest[row][col];

        final int BURNING = BlockState.BURNING.getValue();
        final int EMPTY = BlockState.EMPTY.getValue();
        final int TREE = BlockState.TREE.getValue();

        if (cell == BURNING) {
            cell = EMPTY;
        } else if (cell == TREE) {
            double probLightning = 0.1;
            if (forest[row - 1][col] == BURNING || forest[row + 1][col] == BURNING
                 || forest[row][col - 1] == BURNING || forest[row][col + 1] == BURNING) {
                if (Math.random() <= probCatch) {
                    cell = BURNING;
                }
            } else if (Math.random() <= probCatch * probLightning) {
                cell = BURNING;
            }
        } else if (cell == EMPTY) {
            double probGrow = 0.1;

            if (Math.random() <= probGrow) {
                cell = TREE;
            }
        }
 
        return cell;
    }
  
    // So you can look at the state of the data without the viewer
    public void display() {
        for (int[] aForest : forest) {
            for (int anAForest : aForest) {
                System.out.print(anAForest + " ");
            }

            System.out.println();
        }

        System.out.println("\n--------------------------------------------------\n\n");
    }
  
    // The graphics needs a way to look at the data in this "engine"
    int[][] getForest() {
        return forest;
    }
     
    public double getProbCatch() {
        return probCatch;
    }
     
    public double getProbTree() {
        return probTree;
    }
     
    public void setProbTree(double prob) {
        probTree = prob;
    }
     
    public double getProbBurning() {
        return probBurning;
    }
     
    public void setProbBurning(double prob) {
        probBurning = prob;
    }
     
    private void setProbCatch(double prob) {
        probCatch = prob;
    }
     
    private void burn() {
        boolean burning = true;
        while (burning) {
            burning = false;
            applySpread();

            for (int[] aForest : forest) {
                for (int j = 0; j < forest[0].length; j++) {
                    if (aForest[j] == BlockState.BURNING.getValue()) {
                        burning = true;
                        break;
                    }
                }
            }
        }
    }
     
    private double percentBurned(double probCatch) {
        setProbCatch(probCatch);
        burn();
         
        int count = 0;
        for (int i = 1; i < forest.length - 1; i++) {
            for (int j = 1; j < forest[0].length - 1; j++) {
                if (forest[i][j] == BlockState.EMPTY.getValue()) count++;
            }
        }

        return count / Math.pow(forest.length - 2, 2) * 100;
    }
     
    public static void main(String args[]) {
        double probCatch = 0;
        double[] probAvg = new double[10];
        Wildfire wf;
         
        while (probCatch <= 1) {
            wf = new Wildfire(15);
            double percentBurned = wf.percentBurned(probCatch);
            System.out.printf("Percent burnt (%.0f%% probability): %.2f%n", probCatch * 100, percentBurned);
            probCatch += 0.2;
        }
         
        probCatch = 0;
         
        while (probCatch <= 1) {
            for (int i = 0; i < 10; i++) {
                wf = new Wildfire(15);
                double percentBurned = wf.percentBurned(probCatch);
                probAvg[i] = percentBurned;
            }
            System.out.printf("Average percent burnt (%.0f%% probability): %.2f%n", probCatch * 100, getAverage(probAvg));
             
            probCatch += 0.2;
        }
         
    }
     
    private static double getAverage(double[] probAvg) {
        int total = 0;
        for (double avg : probAvg) {
            total += avg;
        }

        return total / 10;
    }
         
}
