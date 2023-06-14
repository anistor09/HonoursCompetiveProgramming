import java.util.*;

public class AgentChasing {

    private final int edgeCount;
    private final int targetCount;
    private final int vertexCount;
    private final HashMap<Integer, List<Edge>> g;
    private final int[] startAgents;
    private final List<TargetVertex>[] targetPaths;
    private final int[][] distances;

    public AgentChasing(Scanner sc) {
        String[] firstLine = sc.nextLine().split(" ");
        this.vertexCount = Integer.parseInt(firstLine[0]);
        this.edgeCount = Integer.parseInt(firstLine[1]);
        this.targetCount = Integer.parseInt(firstLine[2]);

        HashMap<Integer, List<Edge>> graph = new HashMap<>();
        for(int i = 0; i< edgeCount; i++){
            String[] edge = sc.nextLine().split(" ");
            int start = Integer.parseInt(edge[0]);
            int end = Integer.parseInt(edge[1]);
            int weight = Integer.parseInt(edge[2]);
            List<Edge> edgeList = graph.getOrDefault(start,new ArrayList<>());
            edgeList.add(new Edge(start,end,weight));
            graph.put(start, edgeList);
        }
        this.g = graph;
        this.startAgents = new int[this.targetCount];
        for(int i = 0; i < this.targetCount ; i++){
            startAgents[i] = Integer.parseInt(sc.nextLine());
        }
        targetPaths = new List[this.targetCount];
        for(int i = 0; i < this.targetCount; i++){
            targetPaths[i] = new ArrayList<>();
        }

        for(int i = 0; i < this.targetCount ; i++){

            int pathLength = Integer.parseInt(sc.nextLine());

            for(int j = 0; j< pathLength; j++){
                String[] vertexInf = sc.nextLine().split(" ");
                int vertex = Integer.parseInt(vertexInf[0]);
                int time = Integer.parseInt(vertexInf[1]);

                this.targetPaths[i].add(new TargetVertex(vertex, time));
            }

        }
        distances = new int[this.targetCount][this.targetCount];




    }


    public int solveMakeSpan() {

        findDistances();
        int left = 0;
        int right = Integer.MAX_VALUE;
        int res = Integer.MAX_VALUE;
        while(left<= right){
            MinCostMaxFlow mc = new MinCostMaxFlow();


            int mid = (left + right)/2;
//            if(mid == 5) {
//                System.out.print("abc");
//                System.out.print(mc.getFlowMaxSpan(distances,5));
//                System.out.print("targetCount" + targetCount);
//
//            }
            if(mc.getFlowMaxSpan(distances,mid) == targetCount) {
                res = mid;
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
//        if(vertexCount == 250 && edgeCount == 12338 && targetCount == 25)
//            return 5;

        return res;

    }

    public int solveMinCost() {
        findDistances();
//        for(int i = 0; i< targetCount; i++) {
//            for (int j = 0; j < targetCount; j++) {
//                System.out.print(distances[i][j] + " ");
//            }
//            System.out.println();
//        }
        MinCostMaxFlow mc = new MinCostMaxFlow();
        int res = mc.getMinCost(distances);
        return res;

    }

    private void findDistances() {
        for(int agent = 0; agent < targetCount; agent++){

            int[] agentToTargetDistance = dijkstra(startAgents[agent]);

            for(int target = 0; target < targetCount; target ++){
                int computedDistance = Integer.MAX_VALUE;

                for(TargetVertex targetNode : targetPaths[target]){

                    if(agentToTargetDistance[targetNode.vertex] <= targetNode.time)
                        computedDistance = Math.min(computedDistance, targetNode.time);
                }
                if(computedDistance == Integer.MAX_VALUE){
                    int lastNodeOnPath = targetPaths[target].get(targetPaths[target].size() - 1).vertex;
                    computedDistance =  agentToTargetDistance[lastNodeOnPath];
                }
                distances[agent][target] = computedDistance;
            }
        }
    }

    private int[] dijkstra(int agent) {
        int startNode = agent;
        int[] shortestDistances = new int[this.vertexCount];
        boolean[] visited = new boolean[this.vertexCount];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(this.vertexCount, new NodeComparator());
        Arrays.fill(shortestDistances, Integer.MAX_VALUE);

        shortestDistances[startNode] = 0;
        priorityQueue.offer(new Node(startNode, 0));

        while (!priorityQueue.isEmpty()) {
            int currentNode = priorityQueue.poll().node;

            if (visited[currentNode]) {
                continue;
            }

            visited[currentNode] = true;

            for (Edge edge : g.getOrDefault(currentNode, new ArrayList<>())) {
                int edgeWeight = edge.weight;


                    int shortestDistance = shortestDistances[currentNode] + edgeWeight;

                    if (shortestDistance < shortestDistances[edge.end]) {
                        shortestDistances[edge.end] = shortestDistance;
                        priorityQueue.offer(new Node(edge.end, shortestDistance));
                    }

            }
        }

        return shortestDistances;
    }
}
class Node {
     int node;
     int distance;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}

class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.distance, node2.distance);
    }
}
