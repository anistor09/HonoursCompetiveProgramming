#include <algorithm>
#include <iostream>
#include <vector>
#include <climits>
#define NMAX 500000
using namespace std;

int dp[NMAX];
int n;
int cost[NMAX];
std::vector<int> neighbours[NMAX];
bool visited[NMAX];

void readInput() {
    cin >> n;
    for (long i = 0; i < n; i++) {
        cin >> cost[i];
        long nrChildren = 0;
        cin >> nrChildren;
        for (long j = 0; j < nrChildren; j++) {
            int child;
            cin >> child;
            neighbours[i].push_back(child);
        }
    }


    return;
}

int computeDp(int node);

int sum2(int node) {
    int localSum = 0;
    long size = neighbours[node].size();
    for (long i = 0; i < size; i++) {
        int child = neighbours[node][i];
        long size2 = neighbours[child].size();
        for (long j = 0; j < size2; j++) {
            int grandChild = neighbours[child][j];
            localSum += computeDp(grandChild);
        }
    }
    return localSum;
}

int sum(int node) {
    int localSum = 0;
    long size = neighbours[node].size();
    for (long i = 0; i < size; i++) {
        int child = neighbours[node][i];

        localSum += computeDp(child);

    }
    return localSum;

}

int computeDp(int node) {
    if (visited[node])
        return dp[node];
    int left = cost[node] + sum2(node);
    int right = INT_MAX;
    int sumNode = sum(node);
    long size = neighbours[node].size();
    for (long i = 0; i < size; i++) {
        int neigh = neighbours[node][i];
        right = min(sumNode + cost[neigh] - computeDp(neigh) + sum2(neigh), right);
    }
    visited[node] = true;
    dp[node] = min(left, right);
    return dp[node];
}


int main() {
    readInput();
    cout << computeDp(0);

    return 0;

}
