## Bounds

### Coloring 

|Type|Rounds|
|---|---|
|Rooted Tree|$O(log^* n)$|
|General Graph with $O(\Delta^2)$ Colors (Linial's Algorithm)|$O(\log^* n)$|
|General Graph with $O(\Delta+1)$ Colors|$O(\Delta \log \Delta + \log^* n)$|
|$O(\log \log n)$ coloring of graph with $\Delta = O(1)$|$O(1)$|

*Unrooted tree to rooted tree: $O(\log n)$ (?)*

### Algorithms

|Type|Rounds|Message Complexity|
|---|---|---|
|Minimum Spanning Tree (MST)|$O(n \log n)$|$O(m \log n)$|
||$O(n)$|$O(m + n \log n)$|
|Maximal Independent Set (MIS) TODO|$O(\log n)$, WHP||
|Maximal Independent Set (MIS) on Directed Cycle|O(\log^* n)$||
|Approximatie Min Cut|$\Omega(D)$||
|Network Decomposition|$O(poly(\log n))$||
|Comput Diameter|$O(n)$|$O(\log n)$|

$\Delta$-Cover-Free Family: No set in the family is a subset of the union of $\Delta$ other sets

*Use Cases: Color Reduction, Interferring Radio Transmissions*

Network Decomposition into Strong diameter clusters with $C = O(\log n)$, $D = O(\log n)$ can be done in $(\log^8 n)$ rounds.

*Provided a network decomposition exists, then:*

* *We can compute a $\Delta+1$ coloring in $O(CD)$ time*

*Assuming an Algorithm which computes MIS in $f(n) rounds exists, then:$*

* *We can compute a $\Delta + 1$ coloring in $f(n (\Delta + 1))$ rounds*
* *We can compute a maximal matching in $f(n (\Delta + 1)) rounds$*

### Shared Memory

|Type|Worst Case Running Time|
|---|---|
|Ivy|$\log n$|
|Arrow|$D$ (Diameter of Tree)|

### Labeling

|Type|Label Size|
|---|---|
|Adjacency Tree|$2 \log n$|
|Adjacency General Graph|$\Omega(n)$|
|Ancestor Tree|$O(2 \log n)$|
|Distance Tree|$O(n \log n)$|
|||

### Wireless Protocols

|Algorithm|Time|
|---|---|
|Leader Election ALOHA|$O(1)$ expected, $O(\log n)$ WHP|

## Definitions

Radius $r(u)$ of node $u$ in $G$ is the maximum of distances from $u$ to all other nodes. The radius $r(G)$ is the minimum of the radii of all nodes in $G$.

*If the radius is 1, there must be a node connected to all other nodes.*

Diameter

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

### Sorting Networks

## Math

#### Markov Inequality

$P[X \geq a] \leq \frac{ E[X]}{a}$

#### Chernoff Bound

$P[X > (1 + \delta) \mu] < (\frac{e^\delta}{(1 + \delta)^{(1 + \delta)}})^\mu$

#### E Approximation

$(1 - x)^t \leq e^{-xt}$


