import java.util.*;

public  class DecisionTree {
    static int nrPoints;
    static HashMap<Integer, List<Error>>[] trees;

    public static List<Error> computerError(List<DataPoint> points, TreeSet<Integer> indices,
                                            int depth, boolean[] seen) {

        int hash = hash(indices);
        if (trees[depth] != null && trees[depth].containsKey(hash))
            return trees[depth].get(hash);
        List<Error> res = new ArrayList<>();
        if (depth == 1) {

                for(int i = 0; i< seen.length; i++){
                    if(!seen[i]){
                        int leftNeg = 0;
                        int leftPoz = 0;
                        int rightNeg = 0;
                        int rightPoz = 0;

                        for (DataPoint p : points) {
                            if (p.features[i] == 1) {
                                if(p.expected == 1)
                                    rightPoz++;
                                else
                                    rightNeg++;
                            } else {
                                if(p.expected == 1)
                                    leftPoz++;
                                else
                                    leftNeg++;
                            }
                        }
                        res.addAll(Arrays.asList(new Error(0,leftPoz + rightPoz),
                                new Error(leftNeg + rightNeg,0),
                                new Error(leftNeg,rightPoz),
                                new Error(rightNeg, leftPoz)));

                        res = filterErrors(res);

                        if(res.size()>0 && res.get(0).fn == 0 && res.get(0).fp == 0 )
                            break;
                    }

                }


                trees[depth].put(hash, res);
                return res;
            }

        else for (int i = 0; i < seen.length; i++) {
            if (!seen[i]) {
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


                List<Error> rightError = new ArrayList<>();


                List<Error> leftError = computerError(left, leftIndices, depth - 1, seen);
                if (left.size() > 0)
                    rightError = computerError(right, rightIndices, depth - 1, seen);




                if (left.size() > 0 && right.size() > 0) {
                    List<Error> unfilteredErrors = new ArrayList<>();
                    for (Error lErr : leftError) {
                        for (Error rErr : rightError) {

                            unfilteredErrors.add(new Error(lErr.fp + rErr.fp, lErr.fn + rErr.fn));
                        }

                    }
                    List<Error> filteredErrors = filterErrors(unfilteredErrors);
                    res.addAll(filteredErrors);
                    res = filterErrors(res);
                }

                seen[i] = false;
                if (res.size()> 0 && res.get(0).fn == 0 && res.get(0).fp == 0)
                    break;
            }
        }

        trees[depth].put(hash, res);
        return res;

    }
    public static int hash(TreeSet<Integer> indices) {
        int hash = 17;
        for (int i : indices) {
            hash = hash * 31 + i;
        }
        return hash;
    }

    public static List<Error> filterErrors(List<Error> unfilteredErrors){
        List<Error> filteredErrors = new ArrayList<>();

        Collections.sort(unfilteredErrors, (x, y) -> {
            int result = x.fp.compareTo(y.fp);
            if (result == 0) {
                result = x.fn.compareTo(y.fn);
            }
            return result;
        });

        for (int j = 0; j < unfilteredErrors.size(); j++) {
                if(j == 0)
                    filteredErrors.add(unfilteredErrors.get(0));
                else {
                    Error current = unfilteredErrors.get(j);
                    Error precedent = filteredErrors.get(filteredErrors.size() - 1);
                    if (current.fn < precedent.fn) {
                        filteredErrors.add(current);
                    }
                }
            }
        return filteredErrors;
    }
}