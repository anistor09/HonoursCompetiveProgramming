import java.util.*;

public  class DecisionTree {
     static int nrPoints;
     static HashMap<Struct<TreeSet<Integer>,Integer>,Integer>[] trees;



    public static int computerError(List<DataPoint> points, TreeSet<Integer> indices,
                                    int depth, boolean[] seen, int upperBound) {

        if(upperBound< 0)
            return Integer.MAX_VALUE;

        Struct<TreeSet<Integer>,Integer> pair = new Struct<>(indices,depth);
        if(trees[points.size()]!=null && trees[points.size()].containsKey(pair))
            return trees[points.size()].get(pair);

        if (depth == 0) {
            int sum = 0;
            for (DataPoint point : points) {
                sum += point.expected;
            }
            int result0 = Math.min(sum, points.size() - sum);
            if(trees[points.size()] == null)
                trees[points.size()] = new HashMap<>();
            trees[points.size()].put(new Struct<>(indices,0),result0);
            return result0;
        }
        if (depth == 2) {
            int result2 = solveDepth2Tree(points, seen);
            if(trees[points.size()] == null)
                trees[points.size()] = new HashMap<>();
            trees[points.size()].put(new Struct<>(indices,2),result2);
            return result2;
        }

        int mini = Integer.MAX_VALUE;

        for (int i = 0; i < seen.length; i++) {
            if (!seen[i]) {
                if(mini == 0)
                    break;
                seen[i] = true;


                List<DataPoint> left = new ArrayList<>();
                List<DataPoint> right = new ArrayList<>();
                TreeSet<Integer> leftIndices = new TreeSet<>();
                TreeSet<Integer> rightIndices = new TreeSet<>();


                for (DataPoint p : points) {
                    if (p.features[i] == 1) {
                        right.add(p);
                        rightIndices.add(p.index);
                    } else {
                        left.add(p);
                        leftIndices.add(p.index);
                    }
                }

                int leftError = computerError(left, leftIndices, depth - 1, seen,upperBound);
                int rightError = 0;

                if(leftError<=mini)
                    rightError = computerError(right, rightIndices, depth - 1,
                            seen, mini - leftError);

                int localError = leftError + rightError;

                mini = Math.min(mini, localError);

                if(trees[points.size()] == null)
                    trees[points.size()] = new HashMap<>();
                trees[points.size()].put(new Struct<>(indices,depth),mini);
                seen[i] = false;
            }
        }
        return mini;

    }

    private static int solveDepth2Tree(List<DataPoint> points, boolean[] seen) {
        List<DataPoint> positive = new ArrayList<>();
        List<DataPoint> negative = new ArrayList<>();
        for (DataPoint d : points) {
            if (d.expected == 1)
                positive.add(d);
            else
                negative.add(d);
        }
        int[] fqSgPos = new int[seen.length];
        int[] fqSgNeg = new int[seen.length];
        int[][] fqDoublePos = new int[seen.length][seen.length];
        int[][] fqDoubleNeg = new int[seen.length][seen.length];
        int n = seen.length;

        for (DataPoint d : positive) {
            for (int i = 0; i < n; i++)
                if (d.features[i] == 1) {
                    fqSgPos[i]++;
                    for (int j = i + 1; j < n; j++) {
                        if (d.features[j] == 1) {
                            fqDoublePos[i][j]++;
                            fqDoublePos[j][i]++;
                        }
                    }
                }
        }

        for (DataPoint d : negative) {
            for (int i = 0; i < n; i++)
                if (d.features[i] == 1) {
                    fqSgNeg[i]++;
                    for (int j = i + 1; j < n; j++) {
                        if (d.features[j] == 1) {
                            fqDoubleNeg[i][j]++;
                            fqDoubleNeg[j][i]++;
                        }
                    }
                }
        }
        int[] bestLeftSubtree = new int[n];
        int[] bestRightSubtree = new int[n];
        Arrays.fill(bestLeftSubtree,Integer.MAX_VALUE);
        Arrays.fill(bestRightSubtree,Integer.MAX_VALUE);


        for (int i = 0; i < n; i++)
            for(int j = 0; j<n; j++){
                if(i!=j){
                    int Cs1 = Math.min(fqSgPos[j] - fqDoublePos[i][j],
                            fqSgNeg[j] - fqDoubleNeg[i][j]
                    );
                    int Cs2 = Math.min( positive.size() - fqSgPos[i] - fqSgPos[j] + fqDoublePos[i][j],
                            negative.size() - fqSgNeg[i] - fqSgNeg[j] + fqDoubleNeg[i][j]
                    );
                    int MsLeft = Cs1 + Cs2;
                    if(bestLeftSubtree[i]> MsLeft){
                        bestLeftSubtree[i] = MsLeft;

                    }
                }
            }

        for (int i = 0; i < n; i++)
            for(int j = 0; j<n; j++){
                if(i!=j){
                    int Cs1 = Math.min(fqDoublePos[i][j],
                            fqDoubleNeg[i][j]
                    );
                    int Cs2 = Math.min( fqSgPos[i] -  fqDoublePos[i][j],
                            fqSgNeg[i] -  fqDoubleNeg[i][j]
                    );
                    int MsRight = Cs1 + Cs2;
                    if(bestRightSubtree[i]> MsRight){
                        bestRightSubtree[i] = MsRight;

                    }
                }
            }
        int miniArrays = Integer.MAX_VALUE;
        for(int i = 0; i<n; i++)
            miniArrays = Math.min(bestLeftSubtree[i]+ bestRightSubtree[i],miniArrays);


        return miniArrays;

    }
}
