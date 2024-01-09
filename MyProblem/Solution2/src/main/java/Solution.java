import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


class Solution {

    int[][] distances;
    int n, m;
    int[][] visited, visited1;

    /**
     * Check if the next position is on the board and  it has not been visited before
     **/

    public boolean isValid(int i, int j, int[][] visited2) {

        return i >= 0 && i < n && j >= 0 && j < m && visited2[i][j] == 0;
    }

    /**
     * Start a  BSF from each zombie and store for each cell the distance to the closest zombie
     **/
    public void computeZombieDistances(Queue<Cell> q) {

        int distance = 0;
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!q.isEmpty()) {
            int size = q.size();

            while (size > 0) {

                Cell current = q.poll();
                Integer i = current.getKey();
                Integer j = current.getValue();

                distances[i][j] = distance;

                for (int[] direction : directions) {
                    int newi = i + direction[0];
                    int newj = j + direction[1];

                    if (isValid(newi, newj, visited)) {

                        q.offer(new Cell(newi, newj));
                        visited[newi][newj] = 1;
                    }
                }
                size--;
            }
            distance++;
        }
    }

    /**
     * *
     *
     * @param mini minimum distance that we are allowed to encounter to on our way to the exit
     * @return boolean representing if a path with the required minimum distance exists
     */
    public boolean isSafe(int mini) {

        if (distances[0][0] < mini)
            return false;


        Queue<Cell> q = new LinkedList<>();

        q.offer(new Cell(0, 0));

        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        visited1 = new int[n][m];

        visited1[0][0] = 1;

        while (!q.isEmpty()) {

            Cell current = q.poll();

            Integer i = current.getKey();
            Integer j = current.getValue();

            if (i == n - 1 && j == m - 1)
                return true;

            for (int[] direction : directions) {
                int newi = i + direction[0];
                int newj = j + direction[1];


                if (isValid(newi, newj, visited1)) {


                    if (distances[newi][newj] >= mini) {

                        q.offer(new Cell(newi, newj));
                        visited1[newi][newj] = 1;

                    }
                }
            }
        }
        return false;
    }

    /**
     * *
     *
     * @param map input matrix
     * @return the maximum safeness score defined as the minimum Manhattan distance from any cell in the path to any
     * zombie on the map.
     */
    public int maximumSafenessScore(List<List<Integer>> map) {

        Queue<Cell> zombies = new LinkedList<>();

        n = map.size();
        m = map.get(0).size();
        visited = new int[n][m];

        // Store each zombie
        for (int i = 0; i < n; i++) {
            List<Integer> current = map.get(i);

            for (int j = 0; j < m; j++) {
                if (current.get(j) == 1) {
                    zombies.offer(new Cell(i, j));
                    visited[i][j] = 1;
                }
            }

        }

        distances = new int[n][m];

        // Start BFS from each zombie location
        computeZombieDistances(zombies);


        int res = m + n;
        int maxi = res;
        int mini = 0;

        visited1 = new int[n][m];

        // Find the maximum safeness factor using binary search

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

    // Helper class representing one cell on our board
    class Cell {
        Integer key;
        Integer value;

        public Cell(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }

        Integer getKey() {
            return key;
        }

        Integer getValue() {
            return value;

        }
    }
}


