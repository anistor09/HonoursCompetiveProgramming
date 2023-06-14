import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DecisionTreeTest extends TestCase {

    public void testFilterErrors() {
        List<Error> lst = Arrays.asList(new Error(144 ,43),new Error(146 ,41),
                new Error( 151, 40),new Error(151, 38),new Error(153,35));
       List<Error> filtered = DecisionTree.filterErrors(lst);
        Collections.sort(lst, (x, y) -> {
            int result = x.fp.compareTo(y.fp);
            if (result == 0) {
                result = x.fn.compareTo(y.fn);
            }
            return result;
        });
        for(Error error : lst){
            System.out.println(error.fp + " " + error.fn);
        }
    }
}