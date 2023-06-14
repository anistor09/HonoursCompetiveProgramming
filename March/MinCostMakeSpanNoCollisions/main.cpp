#include <climits>
#include <iostream>
#include <vector>
#include <bitset>
#include <unordered_map>
#include <sstream>
#include <map>
#include <cstring>


using namespace std;

///////MAXFLOW MINCOST LIBRARY

struct Edge {
    Edge(int _a, int _b, int _c, int _f, int _w) {
        a = _a;
        b = _b;
        c = _c;
        f = _f;
        w = _w;
    }

    ~Edge() {};
    int a; //from
    int b; //to
    int c; //capacity
    int f; //flow
    int w; //weight
    Edge *r;
};

const int MAX_NODES = 10000;
const int MAX_DIST = 2000000; //do not choose INT_MAX because you are adding weights to it
vector<Edge *> adj[MAX_NODES];
int distances[MAX_NODES];
Edge *parents[MAX_NODES];
map<int, int>ind;
int idx;

int nodecount;

bool find_path(int from, int to, vector<Edge *> &output) {
    fill(distances, distances + nodecount, MAX_DIST);
    fill(parents, parents + nodecount, (Edge *) 0);
    distances[from] = 0;

    bool updated = true;
    while (updated) {
        updated = false;
        for (int j = 0; j < nodecount; ++j)
            for (int k = 0; k < (int) adj[j].size(); ++k) {
                Edge *e = adj[j][k];
                if (e->f >= e->c) continue;
                if (distances[e->b] > distances[e->a] + e->w) {
                    distances[e->b] = distances[e->a] + e->w;
                    parents[e->b] = e;
                    updated = true;
                }
            }
    }
    output.clear();
    if (distances[to] == MAX_DIST) return false;
    int cur = to;
    while (parents[cur]) {
        output.push_back(parents[cur]);
        cur = parents[cur]->a;
    }
    return true;
}

int min_cost_max_flow(int source, int sink) {
//    vector<Edge *> adj1[MAX_NODES];
//    for(int i = 0; i<MAX_NODES; i++)
//        for(int j = 0; j< adj[i].size();j++){
//            adj1[i].push_back(adj[i][j]);
//        }
    int total_cost = 0;
    vector<Edge *> p;
    while (find_path(source, sink, p)) {
        int flow = INT_MAX;
        for (int i = 0; i < p.size(); ++i)
            if (p[i]->c - p[i]->f < flow) flow = p[i]->c - p[i]->f;

        int cost = 0;
        for (int i = 0; i < p.size(); ++i) {
            cost += p[i]->w;
            p[i]->f += flow;
            p[i]->r->f -= flow;
        }
        cost += flow; //cost per flow
        total_cost += flow;
    }
    return total_cost;
}

void add_edge(int a, int b, int c, int w) {
    if (ind.find(a) == ind.end()) {
        ind[a] = ++idx;
        a = idx;
    } else {
        a = ind[a];
    }
    if (ind.find(b) == ind.end()) {
        ind[b] = ++idx;
        b = idx;
    } else {
        b = ind[b];
    }
    //cerr << a << "-->" << b << endl;
    Edge *e = new Edge(a, b, c, 0, w);
    Edge *re = new Edge(b, a, 0, 0, -w);
    e->r = re;
    re->r = e;
    adj[a].push_back(e);
    adj[b].push_back(re);
}

//////

struct VertexTime {
    int nr;
    int time;
};
struct WeightEdge {
    int from;
    int to;
    int weight;
};

int solveMinCost(unordered_map<int, std::vector<WeightEdge>> map);

int getFlowMaxSpan(unordered_map<int, std::vector<WeightEdge>> map, int mid);

int max_flow(int source, int sink);

std::string line;
int vertexCount, edgeCount, targetCount;
std::vector<int> startAgents;
std::unordered_map<int, std::vector<VertexTime>> paths;

std::unordered_map<int, std::vector<WeightEdge>> readInput() {
    std::unordered_map<int, std::vector<WeightEdge>> graph;
    std::stringstream stringstream;

    std::getline(std::cin, line);
    stringstream << line;
    stringstream >> vertexCount;
    stringstream >> edgeCount;
    stringstream >> targetCount;
    stringstream.clear();

    while (edgeCount > 0) {
        WeightEdge e;
        std::getline(std::cin, line);
        stringstream << line;
        stringstream >> e.from;
        stringstream >> e.to;
        stringstream >> e.weight;


        stringstream.clear();
        graph[e.from].push_back(e);
        edgeCount--;
    }
    int auxAgentNr = targetCount;
    while (auxAgentNr > 0) {
        int startVertex;
        std::getline(std::cin, line);
        stringstream << line;
        stringstream >> startVertex;

        stringstream.clear();
        startAgents.push_back(startVertex);
        auxAgentNr--;
    }

    int targetAux = 0;
    while (targetAux < targetCount) {
        int pathLength;
        std::getline(std::cin, line);
        stringstream << line;
        stringstream >> pathLength;
        stringstream.clear();
        while (pathLength > 0) {
            VertexTime current;
            std::getline(std::cin, line);
            stringstream << line;
            stringstream >> current.nr;
            stringstream >> current.time;
            stringstream.clear();
            paths[targetAux].push_back(current);

            pathLength--;
        }
        targetAux++;
    }

    return graph;
}

int solveMinCost(unordered_map<int, std::vector<WeightEdge>> graph) {
    int n = vertexCount;

    int maxTimestamps = 100;
    int targetsNr = targetCount;
    int N = (2 * n) * (maxTimestamps + 1);
    int matrixSize = N + targetsNr + 2;
    nodecount = matrixSize;
    int source = N + targetsNr;
    int sink = N + targetsNr + 1;

//    cerr << "N= " << N << endl;
//    cerr << "source= " << source << endl;
//    cerr << "sink= " << sink << endl;

    map<int, bool>vis;

    ///Add source to timestamp 0 agents edges
    for (int i = 0; i < startAgents.size(); i++) {
        add_edge(source, startAgents[i], 1, 0);
        vis[startAgents[i]] = true;
    }
    /// Add  transitions between timestamps
    for (int j = 0; j <= maxTimestamps; j++) {
        int start = 2 * j * n;
        for (int i = start; i < start + n; i++) {

            if (vis.find(i) == vis.end())
                continue;

            add_edge(i, i + n, 1, 0);
            vis[i + n] = true;
            if (j + 1 <= maxTimestamps) {
                add_edge(i + n, i + 2 * n, 1, 1);
                vis[i + 2 * n] = true;
            }

            for (int k = 0; k < graph[i - start].size(); k++) {
                if (j + graph[i - start][k].weight > maxTimestamps)
                    continue;
                int to = start + 2 * n * graph[i - start][k].weight + graph[i - start][k].to;
                vis[to] = true;

                add_edge(i + n, to, 1, graph[i - start][k].weight);

            }
        }
    }

    ///Add  edges from each node on the target to its corresponding sink
//    for (int i = 0; i < paths.size(); i++) {
//        int nextTime = 0;
//        int lastLoc = paths[i][0].nr;
//        int k = 0;
//        for (int j = 0; j < maxTimestamps - 1; j++) {
//            int position;
//            if (j == nextTime) {
//                position = paths[i][k].nr;
//                lastLoc = position;
//                k++;
//                if (k < paths[i].size())
//                    nextTime = paths[i][k].time;
//            } else {
//                position = lastLoc;
//            }
//            int nodeIndex = 2 * n * j + n + position;
//            int correspondingSink = N + i;
//            add_edge(nodeIndex, correspondingSink, 1, 0);
//
//        }
//    }
    for (auto path : paths) {
        for (int i = 0; i < path.second.size(); ++i) {
            int time = path.second[i].time;
            int node = path.second[i].nr;
            if (i == path.second.size() - 1) {
                for (int t = time; t <= maxTimestamps; ++t) {
                    int from = node + 2 * t * n + n;
                    if (vis.find(from) != vis.end())
                        add_edge(from, N + path.first, 1, 0);
                }
            } else {
                int from = node + 2 * time * n + n;
                if (vis.find(from) != vis.end())
                    add_edge(from, N + path.first, 1, 0);
            }
        }
    }
    ///Collect all target sinks to only one sink
    for (int i = N; i < N + targetsNr; i++)
        add_edge(i, sink, 1, 0);

    return min_cost_max_flow(ind[source], ind[sink]);
}
int solveMakeSpan(unordered_map<int, std::vector<WeightEdge>> graph){

    int left = 0;
    int right = 100;
    int res = INT_MAX;
    while(left<= right){

        int mid = (left + right)/2;

        if(getFlowMaxSpan(graph,mid) == targetCount) {
            res = mid;
            right = mid-1;
        }else{
            left = mid+1;
        }
    }

    return res;
}

int getFlowMaxSpan(unordered_map<int, std::vector<WeightEdge>> graph, int mid) {
    ind.clear();
    idx=0;
    for (int i = 0; i < MAX_NODES; ++i)
        adj[i].clear();
    int n = vertexCount;

    int maxTimestamps = mid;
    int targetsNr = targetCount;
    int N = (2 * n) * (maxTimestamps + 1);
    int matrixSize = N + targetsNr + 2;
    nodecount = matrixSize;
    int source = N + targetsNr;
    int sink = N + targetsNr + 1;

//    cerr << "N= " << N << endl;
//    cerr << "source= " << source << endl;
//    cerr << "sink= " << sink << endl;

    map<int, bool>vis;

    ///Add source to timestamp 0 agents edges
    for (int i = 0; i < startAgents.size(); i++) {
        add_edge(source, startAgents[i], 1, 0);
        vis[startAgents[i]] = true;
    }
    /// Add  transitions between timestamps
    for (int j = 0; j <= maxTimestamps; j++) {
        int start = 2 * j * n;
        for (int i = start; i < start + n; i++) {

            if (vis.find(i) == vis.end())
                continue;

            add_edge(i, i + n, 1, 0);
            vis[i + n] = true;
            if (j + 1 <= maxTimestamps) {
                add_edge(i + n, i + 2 * n, 1, 1);
                vis[i + 2 * n] = true;
            }

            for (int k = 0; k < graph[i - start].size(); k++) {
                if (j + graph[i - start][k].weight > maxTimestamps)
                    continue;
                int to = start + 2 * n * graph[i - start][k].weight + graph[i - start][k].to;
                vis[to] = true;

                add_edge(i + n, to, 1, graph[i - start][k].weight);

            }
        }
    }

    ///Add  edges from each node on the target to its corresponding sink
//    for (int i = 0; i < paths.size(); i++) {
//        int nextTime = 0;
//        int lastLoc = paths[i][0].nr;
//        int k = 0;
//        for (int j = 0; j < maxTimestamps - 1; j++) {
//            int position;
//            if (j == nextTime) {
//                position = paths[i][k].nr;
//                lastLoc = position;
//                k++;
//                if (k < paths[i].size())
//                    nextTime = paths[i][k].time;
//            } else {
//                position = lastLoc;
//            }
//            int nodeIndex = 2 * n * j + n + position;
//            int correspondingSink = N + i;
//            add_edge(nodeIndex, correspondingSink, 1, 0);
//
//        }
//    }
    for (auto path : paths) {
        for (int i = 0; i < path.second.size(); ++i) {
            int time = path.second[i].time;
            int node = path.second[i].nr;
            if (i == path.second.size() - 1) {
                for (int t = time; t <= maxTimestamps; ++t) {
                    int from = node + 2 * t * n + n;
                    if (vis.find(from) != vis.end())
                        add_edge(from, N + path.first, 1, 0);
                }
            } else {
                int from = node + 2 * time * n + n;
                if (vis.find(from) != vis.end())
                    add_edge(from, N + path.first, 1, 0);
            }
        }
    }
    ///Collect all target sinks to only one sink
    for (int i = N; i < N + targetsNr; i++)
        add_edge(i, sink, 1, 0);

    return min_cost_max_flow(ind[source], ind[sink]);
}



int main() {
    //freopen("test.in", "r", stdin);

    // read the input from console
    std::unordered_map<int, std::vector<WeightEdge>> graph = readInput();
    // Build graph with timestamps
    //int result = solveMinCost(graph);
    int makeSpan = solveMakeSpan(graph);

    std::cout << makeSpan;
    return 0;
}



