// Java Program to implement
// the above approach
import java.util.*;

public class MinCostMaxFlow {

    // Stores the found edges
    boolean found[];

    // Stores the number of nodes
    int N;

    // Stores the capacity
    // of each edge
    int cap[][];

    int flow[][];

    // Stores the cost per
    // unit flow of each edge
    int cost[][];

    // Stores the distance from each node
    // and picked edges for each node
    int dad[], dist[], pi[];

    static final int INF
            = Integer.MAX_VALUE / 2 - 1;

    // Function to check if it is possible to
    // have a flow from the src to sink
    boolean search(int src, int sink)
    {

        // Initialise found[] to false
        Arrays.fill(found, false);

        // Initialise the dist[] to INF
        Arrays.fill(dist, INF);

        // Distance from the source node
        dist[src] = 0;

        // Iterate until src reaches N
        while (src != N) {

            int best = N;
            found[src] = true;

            for (int k = 0; k < N; k++) {

                // If already found
                if (found[k])
                    continue;

                // Evaluate while flow
                // is still in supply
                if (flow[k][src] != 0) {

                    // Obtain the total value
                    int val
                            = dist[src] + pi[src]
                            - pi[k] - cost[k][src];

                    // If dist[k] is > minimum value
                    if (dist[k] > val) {

                        // Update
                        dist[k] = val;
                        dad[k] = src;
                    }
                }

                if (flow[src][k] < cap[src][k]) {

                    int val = dist[src] + pi[src]
                            - pi[k] + cost[src][k];

                    // If dist[k] is > minimum value
                    if (dist[k] > val) {

                        // Update
                        dist[k] = val;
                        dad[k] = src;
                    }
                }

                if (dist[k] < dist[best])
                    best = k;
            }

            // Update src to best for
            // next iteration
            src = best;
        }

        for (int k = 0; k < N; k++)
            pi[k]
                    = Math.min(pi[k] + dist[k],
                    INF);

        // Return the value obtained at sink
        return found[sink];
    }

    // Function to obtain the maximum Flow
    int[] getMaxFlow(int cap[][], int cost[][],
                     int src, int sink)
    {

        this.cap = cap;
        this.cost = cost;

        N = cap.length;
        found = new boolean[N];
        flow = new int[N][N];
        dist = new int[N + 1];
        dad = new int[N];
        pi = new int[N];

        int totflow = 0, totcost = 0;

        // If a path exist from src to sink
        while (search(src, sink)) {

            // Set the default amount
            int amt = INF;
            for (int x = sink; x != src; x = dad[x])

                amt = Math.min(amt,
                        flow[x][dad[x]] != 0
                                ? flow[x][dad[x]]
                                : cap[dad[x]][x]
                                - flow[dad[x]][x]);

            for (int x = sink; x != src; x = dad[x]) {

                if (flow[x][dad[x]] != 0) {
                    flow[x][dad[x]] -= amt;
                    totcost -= amt * cost[x][dad[x]];
                }
                else {
                    flow[dad[x]][x] += amt;
                    totcost += amt * cost[dad[x]][x];
                }
            }
            totflow += amt;
        }

        // Return pair total cost and sink
        return new int[] { totflow, totcost };
    }
    public int getFlowMaxSpan(int[][] distances, int treshold){
        int n = distances.length;
        int[][] cap = new int[2 * n+2][2 * n+2];
        int[][] cost = new int[2 * n+2][2 * n+2];
        int s = 2 * n;
        int t = 2 * n+1;
        for(int i = 0 ; i < 2 * n; i ++)
            for(int j = 0; j < 2 * n; j++)
                if( i <= n-1 && j > n-1 && j < 2 * n)
                    if(distances[i][j - n] <= treshold)
                        cost[i][j] = distances[i][j - n];
                    else {
                        cost[i][j] = 1547483647;
                    }

        for(int i = 0 ; i < 2 * n+2; i ++)
            for(int j = 0; j < 2 * n+2; j++)
            {
                if(i == 2 * n && j <= n-1 )
                    cap[i][j] = 1;

                if(j == 2 * n+1 && i> n-1 && i < 2 * n)
                    cap[i][j] = 1;

                if(i <= n-1 && j < 2 * n && j > n-1 )
                    cap[i][j] = 1;
            }


        int ret[] = this.getMaxFlow(cap, cost, s, t);
        return ret[0];

    }

    public int getMinCost(int[][] distances){
        int n = distances.length;
        int[][] cap = new int[2 * n+2][2 * n+2];
        int[][] cost = new int[2 * n+2][2 * n+2];
        int s = 2 * n;
        int t = 2 * n+1;
        for(int i = 0 ; i < 2 * n; i ++)
            for(int j = 0; j < 2 * n; j++)
                if( i <= n-1 && j > n-1)
                cost[i][j] = distances[i][j - n];

        for(int i = 0 ; i < 2 * n+2; i ++)
            for(int j = 0; j < 2 * n+2; j++)
            {
                if(i == 2 * n && j <= n-1 )
                    cap[i][j] = 1;

                if(j == 2 * n+1 && i> n-1 && i < 2 * n)
                    cap[i][j] = 1;

                if(i <= n-1 && j < 2 * n && j> n-1)
                    cap[i][j] = 1;
            }


        int ret[] = this.getMaxFlow(cap, cost, s, t);
        return ret[1];

    }
    public int getMinCostNoCol(HashMap<Integer, List<Edge>> g, int targetCount,int vertexCount, int[] startAgents,
                               List<TargetVertex>[] targetPaths, int maxTime){

        int n = vertexCount;

//        int maxTimestamps = maxTime+1;
        int maxTimestamps = 100;
        int targetsNr = targetCount;
        int N =   (2 * n) * maxTimestamps;
        int matrixSize =  N + targetsNr + 2;
        int source = N + targetsNr;
        int sink = N + targetsNr + 1;
        int[][] cost = new int[matrixSize][matrixSize];
        int[][] cap = new int[matrixSize][matrixSize];

        ///Add source to timestamp 0 agents edges
        for(int i = 0; i<  startAgents.length; i++){
            cap[source][startAgents[i]] = 1;
        }
        /// Add with transitions between timestamps
        for( int j =0;j< maxTimestamps-1; j++){
            int start = 2 * j * n + n;
            for(int i = start; i < start + n; i++){
                cap[i][i+n]=1;
                cap[i-n][i] = 1;
//                if(j == maxTimestamps-1)
//                    cap[i+n][i+2 * n]=1;

                cost[i][i+n] = 1;
                for(Edge e : g.getOrDefault((i-start),new ArrayList<>()))
                {
                    int to = start + n + e.end;
                    cap[i][to] = 1;
                    cost[i][to] = e.weight;

                }
            }
        }

        ///Add  edges from each node on the target to its corresponding sink
        for(int i = 0; i< targetPaths.length; i++){
            int nextTime = 0;
            int lastLoc = targetPaths[i].get(0).vertex;
            int k = 0;
            for(int j = 0; j< maxTimestamps-1; j++){
                int position;
                if(j< nextTime)
                     position = lastLoc;
                else if(j == nextTime)
                    {
                        position = targetPaths[i].get(k).vertex;
                        lastLoc = position;
                        k++;
                        if( k<targetPaths[i].size())
                            nextTime = targetPaths[i].get(k).time;
                    }else{
                     position = lastLoc;
                }
                    int nodeIndex = 2 * n * j + n + position;
                    int correspondingSink = N + i;
                    cap[nodeIndex][correspondingSink] =1;

            }
        }
        //Collect all target sinks to only one sink
        for(int i = N; i< N +targetsNr ;i++ )
            cap[i][sink] = 1;


        int ret[] = this.getMaxFlow(cap, cost, source, sink);
        System.out.println("flow " +" " + ret[0] + " targets" + targetsNr);
        return ret[1];

    }


    // Driver Code
//    public static void main(String args[])
//    {
//
//        // Creating an object flow
//
//
//        int s = 0, t = 4;
//
//        int cap[][] = { { 0, 3, 1, 0, 3 },
//                { 0, 0, 2, 0, 0 },
//                { 0, 0, 0, 1, 6 },
//                { 0, 0, 0, 0, 2 },
//                { 0, 0, 0, 0, 0 } };
//
//        int cost[][] = { { 0, 1, 0, 0, 2 },
//                { 0, 0, 0, 3, 0 },
//                { 0, 0, 0, 0, 0 },
//                { 0, 0, 0, 0, 1 },
//                { 0, 0, 0, 0, 0 } };
//
//        int ret[] = flow.getMaxFlow(cap, cost, s, t);
//
//        System.out.println(ret[0] + " " + ret[1]);
//    }
}
