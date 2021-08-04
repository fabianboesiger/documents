## Bounds

### Coloring 

|Type|Rounds|
|---|---|
|Rooted Tree|$O(log^* n)$|
|General Graph with $O(\Delta^2)$ Colors (Linial's Algorithm)|$O(\log^* n)$|
|General Graph with $O(\Delta+1)$ Colors|$O(\Delta \log \Delta + \log^* n)$|
|$O(\log \log n)$ coloring of graph with $\Delta = O(1)$|$O(1)$|


### Algorithms

|Type|Rounds|Message Complexity|
|---|---|---|
|Minimum Spanning Tree (MST)|$O(n \log n)$|$O(m \log n)$|
||$O(n)$|$O(m + n \log n)$|
|Maximal Independent Set (MIS) TODO|$O(log n)$, WHP||
|Approximatie Min Cut|$\Omega(D)$||
|Network Decomposition into $O(\log n)$ strong diameter clusters|||

*Assuming an Algorithm which computes MIS in $f(n) rounds exists, then:$*

* *We can compute a $\Delta + 1$ coloring in $f(n (\Delta + 1))$ rounds*
* *We can compute a maximal matching in $f(n (\Delta + 1)) rounds$*

### Shared Memory

|Type|||

### Labeling

|Type|Label Size|
|---|---|
|Adjacency Tree|$2 \log n$|
|Adjacency General Graph|$\Omega(n)$|
|Ancestor Tree|$O(2 \log n)$|
|Distance Tree|$O(n \log n)$|
|||

### Wireless Protocols

## Questions

### Identify Cut Edges

For every edge $e$ in spanning tree $T$, if there is edge $e'$ such that $e' + T$ creates cycle, then $e$ is not a cut edge.

### Identify Connected Components

Each node forms its own component, with self as leader. Every node $v$ selects neighbor $u$ in different component, sends this information to leader. Leader merges the two components, informs about new leader. In every step, a new component contains two old components, thus taking $O(\log n)$ rounds.

### Scheduling

Assign $K$ tasks to $n$ nodes such that each node has at least $\log n$ tasks assigned to it but not to any of its neighbors.

### Asynchronous Broadcast

* Maximal running time of async broadcast: Radius, i.e. minimal distance to each node.
* Echo: Maximal length back to origin.