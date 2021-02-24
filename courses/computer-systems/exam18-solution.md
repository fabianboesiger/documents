---
title: Distributed Systems Exam
---

# 1 Multiple Choice

# 2 Binary Value Broadcast

## a)

## b)

Since $f < n/3$, there are at least $2f + 1$ correct nodes. Because the binary input, either $0$ or $1$ will be the input $x$ for a majority of correct nodes. Thus, at least $f + 1$ messages $\text{msg}(u, x)$ contain the same bit $x$ and all the $2f + 1$ correct nodes have to broadcast $\text{echo}(w, x)$ and all correct nodes have to accept this value $x$.

## c)

If one correct node has accepted exactly one bit $x$, there must have been less than $2f + 1$ nodes which echoed the opposite bit $y$ and thus less than $f + 1$ nodes which broadcasted $y$ in the first round. But if there are less than $f + 1$ nodes which broadcast $y$ in the first round, the value $y$ will not be accepted by any other correct node.

## d)

```plain
if n - f sets X_v only contain bit x
    x_u = x
    decided = true
else
if n - f sets X_v contain bit x
and no n - f sets X_v contain bit 1 - x
    x_u = x
else
    x_u = c
```

# 3

## a)

$$|\theta + 11ms| = |h - 1m|/c$$
$$|\theta + 11ms| = |h - 3m|/c$$
$$|\theta - 1ms| = |h - 5m|/c$$

## b)

## c)

$$\theta = 4$$
$$\theta = -2$$
$$\theta = 4$$

The solution is B.