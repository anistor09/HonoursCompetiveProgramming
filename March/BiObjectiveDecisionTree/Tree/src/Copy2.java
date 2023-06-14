import java.util.*;

public class Copy2 {
//    import java.util.*;
//
//    public  class DecisionTree {
//        static int nrPoints;
//        static HashMap<Struct<TreeSet<Integer>, Integer>, List<Error>>[] trees;
//
//
//        public static List<Error> computerError(List<DataPoint> points, TreeSet<Integer> indices,
//                                                int depth, boolean[] seen) {
//
//            Struct<TreeSet<Integer>, Integer> pair = new Struct<>(indices, depth);
//            if (trees[points.size()] != null && trees[points.size()].containsKey(pair))
//                return trees[points.size()].get(pair);
//
//            List<Error> res = new ArrayList<>();
//            if (depth == 1) {
//
//                for(int i = 0; i< seen.length; i++){
//                    if(!seen[i]){
//                        int leftNeg = 0,leftPoz = 0,rightNeg = 0,rightPoz = 0;
//
//                        for (DataPoint p : points) {
//                            if (p.features[i] == 1) {
//                                if(p.expected == 1)
//                                    rightPoz++;
//                                else
//                                    rightNeg++;
//                            } else {
//                                if(p.expected == 1)
//                                    leftPoz++;
//                                else
//                                    leftNeg++;
//                            }
//                        }
//                        res.addAll(Arrays.asList(new Error(0,leftPoz + rightPoz),
//                                new Error(leftNeg + rightNeg,0),
//                                new Error(leftNeg,rightPoz),
//                                new Error(rightNeg, leftPoz)));
//
//                        res = filterErrors(res);
//
//                        if(res.size()>0 && res.get(0).fp == 0 && res.get(0).fn == 0 )
//                            break;
//                    }
//                }
//
//                if (trees[points.size()] == null)
//                    trees[points.size()] = new HashMap<>();
//                trees[points.size()].put(new Struct<>(indices, 1), res);
//                return res;
//            }else for (int i = 0; i < seen.length; i++) {
//                if (!seen[i]) {
//                    seen[i] = true;
//
//
//                    List<DataPoint> left = new ArrayList<>();
//                    List<DataPoint> right = new ArrayList<>();
//                    TreeSet<Integer> leftIndices = new TreeSet<>();
//                    TreeSet<Integer> rightIndices = new TreeSet<>();
//
//
//                    for (DataPoint p : points) {
//                        if (p.features[i] == 1) {
//                            right.add(p);
//                            rightIndices.add(p.index);
//                        } else {
//                            left.add(p);
//                            leftIndices.add(p.index);
//                        }
//                    }
//
//
//                    List<Error> rightError = new ArrayList<>();
//                    List<Error> leftError = computerError(left, leftIndices, depth - 1, seen);
//
//                    if (leftError.size() > 0)
//                        rightError = computerError(right, rightIndices, depth - 1, seen);
//
//
//                    if (left.size() > 0 && right.size() > 0) {
//                        List<Error> unfilteredErrors = new ArrayList<>();
//                        for (Error lErr : leftError) {
//                            for (Error rErr : rightError) {
//
//                                unfilteredErrors.add(new Error(lErr.fp + rErr.fp, lErr.fn + rErr.fn));
//                            }
//                        }
//                        List<Error> filteredErrors = filterErrors(unfilteredErrors);
//
//                        if (trees[points.size()] == null)
//                            trees[points.size()] = new HashMap<>();
//                        res = new ArrayList<>(trees[points.size()].getOrDefault(new Struct<>(indices, depth), new ArrayList<>()));
//                        res.addAll(filteredErrors);
//                        res = filterErrors(res);
//
//                        trees[points.size()].put(new Struct<>(indices, depth), res);
//                    }
//
//                    seen[i] = false;
//
//                    if(res.size()>0 && res.get(0).fp == 0 && res.get(0).fn == 0 ){
//                        break;
//                    }
//                }
//            }
//            return res;
//
//        }
//        public static List<Error> filterErrors(List<Error> unfilteredErrors){
//            List<Error> filteredErrors = new ArrayList<>();
//
//            Collections.sort(unfilteredErrors, (x, y) -> {
//                if (x.fp != y.fp)
//                    return x.fp - y.fp;
//                else
//                    return x.fn - y.fn;
//            });
//
//            for (int j = 0; j < unfilteredErrors.size(); j++) {
//                if(j == 0)
//                    filteredErrors.add(unfilteredErrors.get(0));
//                else {
//                    Error current = unfilteredErrors.get(j);
//                    Error precedent = unfilteredErrors.get(j - 1);
//                    if (current.fn < precedent.fn) {
//                        filteredErrors.add(current);
//                    }
//                }
//            }
//            return filteredErrors;
//        }
//    }
}
