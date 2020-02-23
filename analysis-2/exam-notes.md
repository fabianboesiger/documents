---
title: Exam Notes for Analysis II
author: Fabian Bösiger
papersize: a5
toc: yes
toc-depth: 2
geometry: margin=1cm
fontsize: 10pt
---

\newpage

# Lookup Table

## Differentiation and Integration

### Differentiation Rules

$$y = \frac{u}{v} \Rightarrow y' = \frac{u'v - v'u}{v^2}$$

### Integration Rules

$$\int_a^b f(x) g(x) dx = \Big[F(x) g(x)\Big]_a^b - \int_a^b F(x) g'(x) dx$$


### Common Derivatives

|Function|Derivative|
|---|---|
|$\sin(x)$|$\cos(x)$|$\arcsin(x)$|
|$\cos(x)$|$-\sin(x)$|$\arccos(x)$|
|$\tan(x)$|$\frac{1}{\cos^2(x)}$|
|$\arcsin(x)$|$\frac{1}{\sqrt{1-x^2}}$|
|$\arccos(x)$|$-\frac{1}{\sqrt{1-x^2}}$|
|$\arctan(x)$|$\frac{1}{1+x^2}$|

### Common Antiderivatives

|Function|Antiderivative|
|---|---|
|$\sin(x)$|$-\cos(x) + C$|
|$\cos(x)$|$\sin(x) + C$|
|$\arcsin(x)$|$x \arcsin(x) + \sqrt{1 - x^2} + C$|
|$\arccos(x)$|$x \arccos(x) - \sqrt{1 - x^2} + C$|
|$\frac{1}{x}$|$\log(|x|) + C$|

## Trigonometric Functions

### Trigonometric Values

||$0$|$\frac{\pi}{6}$|$\frac{\pi}{4}$|$\frac{\pi}{3}$|$\frac{\pi}{2}$|
|---|---|---|---|---|---|
|$\sin$|$0$|$\frac{1}{2}$|$\frac{\sqrt2}{2}$|$\frac{\sqrt3}{2}$|$1$|
|$\cos$|$1$|$\frac{\sqrt3}{2}$|$\frac{\sqrt2}{2}$|$\frac{1}{2}$|$0$|
|$\tan$|$0$|$\frac{1}{\sqrt3}$|$1$|$\sqrt3$|undefined|

### Taylor Series of Trigonometric Functions

$$\sin(x) = \sum_{n=0}^\infty (-1)^n \frac{x^{2n+1}}{(2n + 1)!}$$
$$\cos(x) = \sum_{n=0}^\infty (-1)^n \frac{x^{2n}}{(2n)!}$$

### Euler Formulas

$$\sin(x) = \frac{e^{ix} - e^{-ix}}{2i}$$
$$\cos(x) = \frac{e^{ix} + e^{-ix}}{2}$$

### Trigonometric Equations

$$\sin^2(x) = \frac{1}{2} (1-\cos(2x))$$
$$\sin^3(x) = \frac{1}{4} (3 \sin(x)-\sin(3x))$$
$$\cos^2(x) = \frac{1}{2} (1+\cos(2x))$$
$$\cos^3(x) = \frac{1}{4} (3 \cos(x)+\cos(3x))$$

$$\sin(2x) = 2 \sin(x) \cos(x)$$
$$\cos(2x) = \cos^2(x)- \sin^2(x) = 2 \cos^2(x) - 1 = 1 - 2 \sin^2(x)$$
$$\sin(3x) = 3 \sin(x) - 4 \sin^3(x)$$
$$\cos(3x) = 4 \cos^3(x) - 3 \cos(x)$$
 
### Derivatives and Antiderivatives of Squared Trigonometric Functions

$$(\sin^2(x))' = 2 \cos(x) \sin(x)$$
$$(\cos^2(x))' = -2 \cos(x) \sin(x)$$
$$(\sin(x) \cos(x))' = \cos^2(x) - \sin^2(x)$$

$$\int \sin^2(x) dx = \frac{2x - \sin(2x)}{4} + C$$
$$\int \cos^2(x) dx = \frac{\sin(2x) + 2x}{4} + C$$
$$\int \sin(x) \cos(x) dx = \frac{\sin^2(x)}{2} + C = - \frac{\cos^2(x)}{2}$$

## Other Formulas

### Solution Formula for Quadratic Equations

To solve $ax^2 + bx + c = 0$:

$$x_1, x_2 = \frac{-b \pm \sqrt{b^2 - 4ac}}{2a}$$

# Ordinary Differential Equations

Differential equations are called *ordinary*, if the unknown function and its derivatives are evaluated at the same point.

## Reducing an ODE to an Equation of Order 1

An *ODE* $y^{(k)} + a_{(k-1)} y^{(k-1)} + \dotsb + a_1y' + a_0y = b$ can be rewritten into a ODE of order 1. We first introduce $y_0 = y, y_1 = y' = y_0', \dotsc, y_{k-1} = y^{(k-1)} = y_{k-2}', y_{k-1}' = y^{(k)}$, furthermore let $Y = (y_0, \dotsc, y_{k-1})^T$. We now can write:

$$
Y' =
\begin{pmatrix}
0 & 1 & 0 & \cdots & 0 \\
0 & 0 & 1 & \cdots & 0 \\
\vdots & & & \ddots & \vdots \\
0 & 0 & 0 & \cdots & 1 \\
-a_0 & -a_1 & -a_2 & \cdots & -a_{k-1}
\end{pmatrix}
Y +
\begin{pmatrix}
0 \\
0 \\
\vdots \\
0 \\
b
\end{pmatrix}
$$

## Linear Differential Equations

### Homogeneous and Inhomogeneous Linear Differential Equations

*Inhomogeneous linear differential equations* are of the form $y^{(k)} + a_{(k-1)} y^{(k-1)} + \dotsb + a_1y' + a_0y = b$. If $b = 0$, we call the equation *homogeneous*. Examples:

|Equation|Ordinary|Linear|Linear Homogeneous|
|---|---|---|---|
|$f'(x) = f(x+1)$|No, because $f$ and $f'$ are evaluated at different points.|No, because not ordinary.|No, because not linear.|
|$y^2 = y'y''$|Yes|No, because $y^2$ is not linear.|No, because not linear.|
|$y'' + 2y = -e^{x^2}$|Yes|Yes|No, because $-e^{x^2}$ is in the equation.|
|$y^{(3)} + 6y' + y = 0$|Yes|Yes|Yes|

If we can find any solution $f_0$, then all the other solutions are of the form $f + f_0$.

## Solving Linear Differential Equations of Order 1

A linear differential equation of order 1 has the form $y' + ay = b$. Because all solutions are of the form $f + f_0$, we first find $f$ by solving the homogeneous equation $y' + ay = 0$, then find a solution $f_0$ of the inhomogeneous equation.

### Solving the Homogeneous Equation

$$y' + a(x)y = 0$$
$$y' = -a(x)y$$
$$\frac{y'}{y} = -a(x)$$
$$(\log(|y|))' = -a(x)$$
$$\log(|y|) = -\int a(x)dx$$
$$\log(|y|) = -A(x) + c$$
$$y = ze^{-A(x)}$$

The so the solution is $f = ze^{-A(x)}$, for some $z$.

### Solving the Inhomogeneous Equation

To solve the inhomogeneous equation, we use the method *variation of constants*, where we assume that $z$ is a function $z(x)$. We insert the solution of the homogeneous equation for $y$.

$$y' + a(x)y = b(x)$$
$$(z(x)e^{-A(x)})' + a(x)(z(x)e^{-A(x)}) = b(x)$$
$$(z(x)'e^{-A(x)} + z(x)(-a(x))e^{-A(x)}) + a(x)(z(x)e^{-A(x)}) = b(x)$$
$$z(x)'e^{-A(x)} - a(x)z(x)e^{-A(x)} + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} = b(x)$$
$$z(x)' = b(x)e^{A(x)}$$
$$z(x) = \int b(x)e^{A(x)}dx$$

This gives us the result:

$$f_0 = \Big(\int b(x)e^{A(x)}dx\Big) e^{-A(x)}$$

### The Final Result

The full solution space is now $f + f_0 = ze^{-A(x)} + (\int b(x)e^{A(x)}dx) e^{-A(x)} = e^{-A(x)} (z + \int b(x)e^{A(x)}dx)$ for some $z$. If we have some initial condition given for $f$, we can now simply insert it and solve for $z$.

Theoretically, we can now compute all ODEs using the solution above and the reduction of ODEs to ODEs of Order 1 as seen previously. But for calcualtion by hand, this is mostly too complicated.

## Solving Linear Differential Equations with Constant Coefficients

Linear ODEs with constant coefficients are of the form $y^{(k)} + a_{(k-1)}y^{(k-1)} + \dotsb + a_1y' + a_0y = b(x)$.

### Solving the Homogeneous Equation

We first solve the homogeneous equation $y^{(k)} + a_{(k-1)}y^{(k-1)} + \dotsb + a_1y' + a_0y = 0$. Because we know that the coefficients are constants, we know that the solutions are of the form $f = e^{\alpha x}$. because of this, $fy^{(k)} + a_{(k-1)}f^{(k-1)} + \dotsb + a_1f' + a_0f = e^{\alpha x} (\alpha^k + a_{k-1} \alpha^{k-1} + \dotsb + a_1 \alpha + a_0) = 0$.

#### Solving the Characteristic Polynomial

The *characteristic polynomial* has the form $P(X) = X^k + a_{k-1} X^{k-1} + \dotsb + a_1 X + a_0$. From the assumption above, we know that $f$ is a solution of the ODE if and only if $P(\alpha) = 0$.

From the *fundamental theorem of Algebra*, we know that this polynomial of degree $k$ has $k$ complex roots $\alpha_1, \dotsc, \alpha_k$ such that $P(X) = (X - \alpha_1) \dotsm (X - \alpha_k)$.

Suppose that a root $\alpha = \beta + i \gamma$ is not in the real space, so that $\gamma$ is non-zero. In this case, the conjugate $\bar \alpha = \beta - i \alpha$ is also a root of $P$. We can replace the two solutions $f_1 = e^{\alpha x}, f_2 = e^{\bar \alpha x}$ with the real-valued functions $\tilde f_1 = e^{\beta x} \cos(\gamma x), f_2 = e^{\beta x} \sin(\gamma x)$.

#### Case 1: No Multiple Roots

If $\alpha_i \neq \alpha_j$ for all $i \neq j$, the homogeneous solution is $f = z_1 f_1 + \dotsb + z_k f_k = z_1 e^{\alpha_1 x} + \dotsb + z_k e^{\alpha_k x}$, such that $f$ spans the full space of solutions for the homogeneous ODE.

#### Case 2: Multiple Roots

Suppose that $\alpha$ is a multiple root of order $j$. Then the $j$ functions $f_{a, 0} = e^{\alpha x}, f_{a, 1} = xe^{\alpha x}, f_{a, j-1} = x^{j-1} e^{\alpha x}$ give a basis of the space of solutions.

### Solving the Inhomogeneus Equation

#### Special Tricks to Avoid Variation of Constants

1. If $b(x) = x^d e^{\beta x}$ for some integer $d \geq 0$ and some number $\beta$ which is not a root of $P$, then we look for a solution of the form $f(x) = Q(x) e^{\beta x}$ where $Q$ is a polynomial of degree $d$.
2. If $b(x) = x^d \cos(\beta x)$ or $b(x) = x^d \sin(\beta x)$ for some integer $d \geq 0$ and some number $\beta$ which is not a root of $P$, then we look for a solution of the form $f(x) = Q_1(x) \cos(\beta x) + Q_2(x) \sin(\beta x)$ where $Q_1$ and $Q_2$ are polynomials of degree $d$.
3. If 1. or 2. apply with $\beta$ is a root of $P$, then we look for an analogue solution but with $Q$ being a polynomial of degree $d + 1$.
4. If $\beta = 0$ and $0$ is a root of $P$, we look for a $Q$ of degree $d + j$, where $j$ is the multiplicity of $0$ as a root of $P$.

#### Variation of Constants

Given the equation $y^{(k)} + a_{(k-1)}y^{(k-1)} + \dotsb + a_1y' + a_0y = b(x)$. We search for a solution of the form $f = z_1(x) f_1(x) + \dotsb + z_k(x) f_k(x)$, where $z_i$ are now functions instead of variables, and the solutions to the homogeneous equation $f_i$ are known. We know that:

$$
\begin{pmatrix}
f_1 & \cdots & f_k \\
\vdots & & \vdots \\
f_1^{(k-2)} & \cdots & f_k^{(k-2)}
\end{pmatrix}
\begin{pmatrix}
z_1' \\
\vdots \\
z_k'
\end{pmatrix}
= 0
$$

This gives us $k - 1$ equations, plus the original $f = z_1 f_1 + \dotsb + z_k f_k$, so in total $k$ equations for $k$ unknowns.

Without loss of generalization, let's consider the case $k = 2$. We have:

$$y'' + a_1 y' + a_0 y = b$$
$$f = z_1 f_1 + z_2 f_2$$
$$z_1' f_1 + z_2' f_2 = 0$$

By differentiation, we get:

$$f' = z_1' f_1 + z_2' f_2 + z_1 f_1' + z_2 f_2' = z_1 f_2' + z_2 f_2'$$
$$f'' = z_1' f_1' + z_2' f_20 + z_1 f_1'' + z_2 f_2''$$

We now insert:

$$y'' + a_1 y' + a_0 y = (z_1' f_1' + z_2' f_20 + z_1 f_1'' + z_2 f_2'') + a_1 (z_1 f_2' + z_2 f_2') + a_0 (z_1 f_1 + z_2 f_2)$$
$$y'' + a_1 y' + a_0 y = z_1 (f_1'' + a_1 f_1' + a_0 f_1) + z_2 (f_2'' + a_1 f_2' + a_0 f_2) + z_1' f_1' + z_2' f_2'$$

Because $f_1$ and $f_2$ solve the homogeneous equation, we get:

$$y'' + a_1 y' + a_0 y = z_1' f_1' + z_2' f_2' = b$$

Finally we get:

$$
\begin{pmatrix}
f_1 & f_2 \\
f_1' & f_2'
\end{pmatrix}
\begin{pmatrix}
z_1' \\
z_2'
\end{pmatrix}
=
\begin{pmatrix}
0 \\
b
\end{pmatrix}
$$

$$
\begin{pmatrix}
z_1' \\
z_2'
\end{pmatrix}
= \frac{1}{f_1 f_2' - f_2 f_1'}
\begin{pmatrix}
f_2' & -f_2 \\
-f_1' & f_1
\end{pmatrix}
\begin{pmatrix}
0 \\
b
\end{pmatrix}
$$

Now, we just have to insert $f_0 = \int_0^x z_1(t) dt f_1 + \int_0^x z_2(t) dt f_2$.

### The Final Result

The full solution space is again $f + f_0$. If we have some initial conditions given, we can insert them and solve for the unknowns.

## Other Methods

### Change of Variable

If we replace $f(x)$ with $h(x) = f(g(x))$ and we can find a result for $h(x)$, then $f(x) = h(g^{-1}(x))$.

### Separation of Variable

If a differential equation of order 1 can be written as $(g(y))' = g(y)' y' = b$, this can be solved by writing $g(f(x)) = B(x)$ and then $f(x) = g^{-1}(B(x))$.

# Differential Calculus in $\mathbb{R}^n$

## Continuity in $\mathbb{R}^n$

### Continuity of Functions

A sequence $(x_k)$ *converges* to $y$ as $k \to \infty$ if for all $\epsilon > 0$, there exists $N \geq 1$ such that for all $n \geq N$, we have $\|x_k - y\| \leq \epsilon$.

$f$ is *continuous* at $x_0$ if for all $\epsilon > 0$, there exists $\delta > 0$ such that, if $\|x - x_0\| < \delta$, then $\|f(x) - f(x_0)\| < \epsilon$.

$f$ is *continuous* on $X$, if it is continuous for all $x \in X$.

The composite of continuous functions is continuous.

### Limit

$f$ has the *limit* $\lim_{x \to x_0} (f(x))y$, if for every $\epsilon > 0$, there exists $\delta > 0$, such that for all $x \neq x_0$, $\|x - x_0\| < \delta$, we have $\|f(x) - y\| < \epsilon$.

We have $\lim_{x \to x_0} (f(x))y$ if and only if for every sequence $(x_k)$ that converges to $x$, the sequence $(f(x_k))$ converges to $y$.

### Bounded, Closed and Compact Sets

A subset $X \subset \mathbb{R}^n$ is *bounded* if the set of $\|x\|$ for $x \in X$ is bounded in $\mathbb{R}$.

A subset $X \subset \mathbb{R}^n$ is *closed* if for every sequence $(x_k)$ in $X$ that converges in $\mathbb{R}^n$ to some vector $y$, we have $y \in X$.

A subset $X$ is *compact* if it is bounded and closed.

If $f$ is continuous and $Y$ is closed, then $f^{-1}(Y)$ is closed.

## Partial Derivatives

### Open Sets

A subset $X \subset R^n$ is *open* if, for any $x = (x_1, \dotsc, x_n) \in X$, there exists $\delta > 0$ such that the set $\{y = (x_1, \dotsc, y_n) \in R^n: |x_i - y_i| < \delta \text{ for all } i\}$ is contained in $X$.

A set $X \subset R^n$ is open if and only if the complement $Y = \{x \in R^n: x \in X\}$ is closed.

If $f$ is continuous and $Y$ is open, then $f^{-1}(Y)$ is open.

### Derivatives

$\frac{\partial f}{\partial x_i}(x) = \partial_{x_i} f(x) = \partial_i f(x)$ is the derivative of $f$ in respect to the $i$-th variable.

### Jacobi Matrix

For $f(x) = (f_1(x), \dotsc, f_m(x))$, the Jacobi matrix is defined as $J_f(x) = (\partial_{x_j} f_i(x))_{1 \leq i \leq m, 1 \leq j \leq n}$. An example:

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

### Gradient

$$\nabla f(x_0) = \begin{pmatrix}  \partial_{x_1} f(x_0) \\ \cdots \\ \partial_{x_n} f(x_0) \end{pmatrix}$$

## The Differential

$f$ is *differentiable* at $x_0$ with the differential $u$ if $\lim_{x \to x_0} \frac{1}{\|x - x_0\|} (f(x) - f(x_0) - u(x - x_0)) = 0$. We denote $df(x_0) = u$.

$f$ is *differentiable* on $X$ if $f$ is differentiable for all $x \in X$.

If $X$ is open and $f: X \to R^m$ a function that is differentiable on $X$, then $f$ is continuous on $X$ and admits derivatives with respect to each variable.

### Directional Derivative

$w = \lim_{t \to 0} \frac{f(x_0 + tv) - f(x_0)}{t}$ is the directional derivative in the direction $v$, where $g(t) = f(x_0 + tv)$.

This means the directional derivative is $D_u f(a) = \frac{d}{dt} f(a + tu)$ with $t = 0$.

We can simply compute it using the gradient $D_u f(a) = \nabla f(a) \cdot u$.

## Higher Derivatives

For higher derivatives, we have commutativity, $\partial_{x, y} f = \partial_{y, x} f$, $\partial_{x, y, z} f = \partial_{y, x, z} f = \partial_{z, x, y} f = \dotsb$ and so on.

### Hessian Matrix

The Hessian Matrix is defined as $H_f(x) = (\partial_{x_i, x_j} f)_{1 \leq i, j \leq n}$. An example:

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

## Change of Variable

The derivative of $h = f \circ g$ is given by:

$$\partial_{y_1}h = \frac{\partial f}{\partial x_1} \frac{\partial g_1}{\partial y_1} + \dotsb + \frac{\partial f}{\partial x_n} \frac{\partial g_n}{\partial y_1}$$

Or often written as:

$$\partial_{y_1}f = \frac{\partial f}{\partial x_1} \frac{\partial x_1}{\partial y_1} + \dotsb + \frac{\partial f}{\partial x_n} \frac{\partial x_n}{\partial y_1}$$

## Taylor Polynomials

Let $m! = m_1! \dotsm m_n!$, $|m| = m_1 + \dotsb + m_n$, $y^m  = y_1^{m_1} \dotsm y_n^{m_n}$ ($y = x - x_0$ for approximations of $f$ at point $x$):

$$T_k f(y; x_0) = \sum_{|m| \leq k} \frac{1}{m!} \partial_x^m f(x_0) y^m$$

$$T_2 f(y; x_0) = f(x_0) + \nabla f(x_0) y + \frac{1}{2} y^t H_f(x_0) y$$

## Critical Points

The point $x$ is a *critical point*, if $\nabla f(x) = 0$.

Let $p$ and $q$ be the number of positive and negative eigenvalues of $H_f(x)$:

1. If $p = n$, $f$ has a local minimum at $x$.
2. If $q = n$, $f$ has a local maximum at $x$.
3. If $pq \neq 0$, $f$ has a saddle point at $x$.

Or in other words:

1. $\det(H_f(x)) > 0$ and $f_{xx}(x) > 0$, then $x$ is a local minimum of $f$.
2. $\det(H_f(x)) > 0$ and $f_{xx}(x) < 0$, then $x$ is a local maximum of $f$.
3. $\det(H_f(x)) < 0$, then $x$ is a saddle point of $f$.
4. $\det(H_f(x)) = 0$, then the test is inconclusive.

# Integration in $\mathbb{R}^n$

## Line Integrals

The *line integeral* of $f$, where $f$ is often called a *vector field*, along $\gamma: [a, b] \to \mathbb{R}^n$ is denoted:

$$\int_\gamma f(s) ds = \int_a^b f(\gamma(t)) \gamma'(t) dt$$

### Conservative Vector Fields

A vector field $f$ is *conservative*, if for any $x_1$, $x_2$, the line integral from $x_1$ to $x_2$ is independent from the choice of $\gamma$. Equivalently, $f$ is conservative if and only if the line integral over $\gamma$ is zero if $\gamma(a) = \gamma(b)$.

If $f$ is conservative, then there exists a $C^1$ function $g$ such that $f = \nabla g$.

Further, if $f$ is conservative, then we have $\frac{\partial f_i}{\partial x_j} = \frac{\partial f_j}{\partial x_i}$ for all $i \neq j$.

A subset $X \subset \mathbb{R}^n$ is *star-shaped* around $x_0$ if there exists an $x_0 \in X$ such that, for all $x \in X$, the line segment joining $x_0$ to $x$ is contained in $X$.

If $\frac{\partial f_i}{\partial x_j} = \frac{\partial f_j}{\partial x_i}$ for all $i \neq j$ on a star-shaped open subset of $\mathbb{R}^n$, then $f$ is conservative.

The *potential* $\omega(x, y)$ is the scalar field whose gradient is the given vector field $f = \nabla \omega(x, y)$.

### Curl

$$\text{curl}(f) = \nabla \times f =
\begin{pmatrix}
\partial_y f_3 - \partial_z f_2 \\
\partial_z f_1 - \partial_x f_3 \\
\partial_x f_2 - \partial_y f_1
\end{pmatrix}
 = \det
\begin{pmatrix}
e_1 & e_2 & e_3 \\
\partial_x & \partial_y & \partial_z \\
f_1 & f_2 & f_3
\end{pmatrix}
$$

$f$ is conservative if and only if $\text{curl}(f) = 0$.

## The Riemann Integral in $\mathbb{R}^n$

## Improper Integrals

$$\lim_{x \to \infty} \int_{[a, x] \times I} f(x, y) dx dy = \lim_{x \to \infty} \int_a^x \Big( \int_I f(x, y) dy \Big) dx = \lim_{x \to \infty} \int_I \Big( \int_a^x f(x, y) dx \Big) dy$$

## The Change of Variable Formula

Let $\varphi: X \to Y$, $f$ a continuous function. We have:

$$\int_X f(\varphi(x)) |\det(J_{\varphi}(x))|dx = \int_Y f(y)dy$$

From this follows:

$$\text{Vol}(AX) = |\det(A)|\text{Vol}(X)$$

We have:

$$
\gamma =
\begin{pmatrix}
r \cos(\varphi) \\
r \sin(\varphi)
\end{pmatrix},
|\det(J_\gamma)| = r
$$

$$
\gamma =
\begin{pmatrix}
r \cos(\theta) \sin(\varphi) \\
r \sin(\theta) \sin(\varphi) \\
r \cos(\varphi)
\end{pmatrix},
|\det(J_\gamma)| = r^2 \sin(\varphi)
$$

## Geometric Applications of Integrals

### Center of Mass

Let $\bar x = (\bar x_n, \dotsc, \bar x_n)$ be the *center of mass* of $X$.

$$\bar x_i = \frac{1}{\text{Vol}(X)} \int_X x_i dx$$

### Surface Area

The *surface area* of the set $\Gamma = \{(x, y, z) \in \mathbb{R}^3: (x, y) \in [a, b] \times [c, d], z = f(x, y)\}$ where $f: [a, b] \times [c, d] \to \mathbb{R}$ is given by:

$$\int_a^b \int_c^d \sqrt{1 + (\partial_x f(x, y))^2 + (\partial_y f(x, y))^2} dx dy$$

## The Green Formula

$$\int_X \Big( \frac{\partial f_2}{\partial x} - \frac{\partial f_1}{\partial y} \Big) dx dy = \sum_{i=1}^k \int_{\gamma_i} f \cdot ds = \sum_{i=1}^k \int_{a_i}^{b_i} f(\gamma_i(t)) \cdot \gamma_i'(t) t$$

If we want to compute the area, $\Big( \frac{\partial f_2}{\partial x} - \frac{\partial f_1}{\partial y} \Big) = 1$ and thus $f = (0, x)$, $f = (0, -y)$, and infinitely others.

From this, we get:

$$\text{Vol}(X) = \sum_{i=1}^k \int_{a_i}^{b_i} \gamma_{i,1}(t) \gamma_{i,2}'(t)dt$$
