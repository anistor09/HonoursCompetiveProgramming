#include <algorithm>
#include <iostream>
#include <vector>
#include <sstream>
#include<iomanip>
#include <queue>
using  namespace std;

std::string line;
int nodesNr;
int relationsNr;

std::vector<int> neighbours[1000];
int dp[1000];
int k;
int in[1000];
bool visited[1000];
struct Pair {
    int distance;
    int index;
};

struct ComparePairs {
    bool operator()(const Pair& a, const Pair& b) {
        return a.distance < b.distance; // Compare in decreasing order
    }
};
void readInput() {

    cin>>nodesNr>>relationsNr;



    while (relationsNr > 0) {
        int from,to;

        cin >> from>>to;

        neighbours[from].push_back(to);
        in[to]++;

        relationsNr--;
    }

    cin >> k;



    return;

}
void getDistance(int currentNode){
    for(int i = 0; i< neighbours[currentNode].size();i++){
        int x = neighbours[currentNode][i];
        if(!visited[x]){
            visited[x] = true;
            getDistance(x);

        }
        dp[currentNode] = std::max(dp[currentNode],1 + dp[x]);

    }
    return;

}


int main() {
    readInput();

    for(int i = 1; i<= nodesNr; i++){
        if(!visited[i]){
            visited[i] = true;
            getDistance(i);

        }
    }

    std::priority_queue<Pair, std::vector<Pair>, ComparePairs> pq;

    for(int i = 1; i<=nodesNr; i++){
        if(in[i] == 0)
            pq.push({dp[i],i});
    }
    int quarter = 0;

    while (!pq.empty()) {

        int size = pq.size();
        std::queue<Pair> next;
        quarter++;
        for (int i = 0; i < std::min(size, k); i++) {
            Pair out = pq.top();
            pq.pop();

            for(int i = 0; i< neighbours[out.index].size();i++){
                int x = neighbours[out.index][i];
                in[x] --;
                if(in[x] == 0){
                    next.push({dp[x],x});
                }
            }
        }
        while(!next.empty()){
            pq.push(next.front());
            next.pop();
        }


    }
    cout<<quarter;

    return 0;
}
