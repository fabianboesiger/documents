### Cange of Variable

Replace a function $f(x)$ by $h(x) = f(g(x))$.


#### Example

### Separation of Variable

If a differential equation of order 1 can be written in the form $(g(y))' = g'(y) y' = b(x)$, this can be solved by writing $y = g^{-1}(B(x))$.

#### Example

Given the equation $\cos(y) y' = x$. We can rewrite it to $\sin(y) = \frac{1}{2} x^2 + c$ and inverting $y = \arcsin(\frac{1}{2} x^2) + c$

$$
f(x, y) =
\begin{pmatrix}
\cos(x^2 + y) \\
e^{\sin(\pi x y)}-1 \\
y + \frac{1}{x^2 + 1}
\end{pmatrix}
, J_f(x, y) =
\begin{pmatrix}
-2x \sin(x^2 + y) & -\sin(x^2 + y) \\
\pi y \cos(\pi x y) e^{\sin(\pi x y)} & \pi x \cos(\pi x y) e^{\sin(\pi x y)} \\
\frac{-2x}{(1 + x^2)^2} & 1
\end{pmatrix}
$$

$$
f(x, y, z) = x^2 y - \cos(x z^3)
$$
$$
\partial_x f = 2xy + z^3 \sin(xz^3), \partial_y f = x^2, \partial_z f = 3xz^2 \sin(xz^3)
$$
$$
H_f(x, y, z) =
\begin{pmatrix}
2y + z^6 \sin(xz^3) & 2x & 3z^2 \sin(xz^3) + xz^6 \cos(xz^3) \\
2x & 0 & 0 \\
3z^2 \sin(xz^3) + xz^6 \cos(xz^3) & 0 & 6xz \sin(xz^3) + 9x^2z^6 \cos(xz^3)
\end{pmatrix}
$$

### Taylor Polynomials


The *line integeral* of $f$, where $f$ is often called a *vector field*, along $\gamma: [a, b] \to \mathbb{R}^n$ is denoted:

$$\int_\gamma f(s) ds = \int_a^b f(\gamma(t)) \gamma'(t) dt$$

A vector field $f$ is *conservative*, if for any $x_1$, $x_2$, the line integral from $x_1$ to $x_2$ is independent from the choice of $\gamma$. Equivalently, $f$ is conservative if and only if the line integral over $\gamma$ is zero if $\gamma(a) = \gamma(b)$.

If $f$ is conservative, then there exists a $C^1$ function $g$ such that $f = (grad) g$.

Further, if $f$ is conservative, then we have $\frac{\partial f_i}{\partial x_j} = \frac{\partial f_j}{\partial x_i}$ for all $i \neq j$.

A subset $X \subset \mathbb{R}^n$ is *star-shaped* around $x_0$ if there exists an $x_0 \in X$ such that, for all $x \in X$, the line segment joining $x_0$ to $x$ is contained in $X$.

If $\frac{\partial f_i}{\partial x_j} = \frac{\partial f_j}{\partial x_i}$ for all $i \neq j$ on a star-shaped open subset of $\mathbb{R}^n$, then $f$ is conservative.