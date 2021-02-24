# 1

## a)

This makes sense if the source and target nodes are modified after the class was constructed.

## b)

Yes, it always exists, but it is possible that the list is empty.

## c)

### (i)

We could compute the shortest path list not when the `shortestPath` method is executed, but rather when we set a new source and target using the `setST` or `addEdge` methods or the constructor such that the shortest path is only computed when the graph or the source or target changes.

### (ii)

This change in design doesn't change the interface provided. The only difference visible tho the client is are the runtimes of the provided methods.

# 2

## a)

An unnecessary cloning operation would be done if we set the entry of a shared list where we don't actually need the other reference to the `elems` array.

## b)

## c)

# 3

## a)

## b)

## c)