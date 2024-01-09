import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

    int[][] distances;
    int n, m;
    int[][] visited, visited1;

    public boolean isValid(int i, int j, int[][] visited2) {

        if (i >= 0 && i < n && j >= 0 && j < m && visited2[i][j] == 0)
            return true;

        return false;
    }


    public void computeThievesDistances(Queue<Pair<Integer, Integer>> q) {

        int distance = 0;
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!q.isEmpty()) {
            int size = q.size();

            while (size > 0) {

                Pair<Integer, Integer> current = q.poll();
                Integer i = current.getKey();
                Integer j = current.getValue();

                distances[i][j] = distance;

                for (int k = 0; k < directions.length; k++) {
                    int newi = i + directions[k][0];
                    int newj = j + directions[k][1];

                    if (isValid(newi, newj, visited)) {

                        q.offer(new Pair(newi, newj));
                        visited[newi][newj] = 1;

                    }

                }

                size--;
            }

            distance++;
        }
    }

    public boolean isSafe(int mini) {


        if (distances[0][0] < mini)
            return false;


        Queue<Pair<Integer, Integer>> q = new LinkedList<>();

        q.offer(new Pair(0, 0));

        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        visited1 = new int[n][m];

        visited1[0][0] = 1;

        while (!q.isEmpty()) {

            Pair<Integer, Integer> current = q.poll();

            Integer i = current.getKey();
            Integer j = current.getValue();

            if (i == n - 1 && j == m - 1)
                return true;

            for (int k = 0; k < directions.length; k++) {
                int newi = i + directions[k][0];
                int newj = j + directions[k][1];


                if (isValid(newi, newj, visited1)) {
                    // System.out.println( newi + " " + newj);

                    if (distances[newi][newj] >= mini) {

                        q.offer(new Pair(newi, newj));
                        visited1[newi][newj] = 1;

                    }

                }

            }

        }

        return false;
    }

    public int maximumSafenessFactor(List<List<Integer>> grid) {

        Queue<Pair<Integer, Integer>> thieves = new LinkedList<>();

        n = grid.size();
        m = grid.get(0).size();
        visited = new int[n][m];

        for (int i = 0; i < n; i++) {
            List<Integer> current = grid.get(i);

            for (int j = 0; j < m; j++) {
                if (current.get(j) == 1) {
                    thieves.offer(new Pir(i, j));
                    visited[i][j] = 1;
                }
            }

        }

        distances = new int[n][m];


        computeThievesDistances(thieves);


        int res = m + n;
        int maxi = res;
        int mini = 0;

        visited1 = new int[n][m];

        while (mini <= maxi) {
            int mid = (mini + maxi) / 2;

            if (isSafe(mid)) {

                res = mid;
                mini = mid + 1;

            } else {
                maxi = mid - 1;
            }
        }


        return res;
    }
}