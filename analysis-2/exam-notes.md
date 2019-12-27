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

## Common Derivatives

|Function|Derivative|
|---|---|
|$\sin(x)$|$\cos(x)$|$\arcsin(x)$|
|$\cos(x)$|$-\sin(x)$|$\arccos(x)$|
|$\tan(x)$|$\frac{1}{\cos^2(x)}$|
|$\arcsin(x)$|$\frac{1}{\sqrt{1-x^2}}$|
|$\arccos(x)$|$-\frac{1}{\sqrt{1-x^2}}$|
|$\arctan(x)$|$\frac{1}{1+x^2}$|

## Common Antiderivatives

|Function|Antiderivative|
|---|---|
|$\sin(x)$|$-\cos(x) + C$|
|$\cos(x)$|$\sin(x) + C$|
|$\frac{1}{x}$|$\log(|x|) + C$|

## Differentiation Rules

## Integration Rules

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

# Differential Calculus in $\mathbb{R}^n$

## Continuity in $\mathbb{R}^n$

### Continuity of Functions

$f$ is *continuous* at $x_0$ if for all $\epsilon > 0$, there exists $\delta > 0$ such that, if $\|x - x_0\| < \delta$, then $\|f(x) - f(x_0)\| < \epsilon$.

$f$ is *continuous* on $X$, if it is continuous for all $x \in X$.

### Limit

$f$ has the *limit* $\lim_{x \to x_0} (f(x))y$, if for every $\epsilon > 0$, there exists $\delta > 0$, such that for all $x \neq x_0$, $\|x - x_0\| < \delta$, we have $\|f(x) - y\| < \epsilon$.

We have $\lim_{x \to x_0} (f(x))y$ if and only if for every sequence $(x_k)$ that converges to $x$, the sequence $(f(x_k))$ converges to $y$.

### Bounded, Closed and Compact Sets

A subset $X \subset \mathbb{R}^n$ is *bounded* if the set of $\|x\|$ for $x \in X$ is bounded in $\mathbb{R}$.

A subset $X \subset \mathbb{R}^n$ is *closed* if for every sequence $(x_k)$ in $X$ that converges in $\mathbb{R}^n$ to some vector $y$, we have $y \in X$.

A subset $X$ is *compact* if it is bounded and closed in $X$.

## Partial Derivatives

### Jacobi Matrix

For $f(x) = (f_1(x), \dotsc, f_m(x))$, the Jacobi matrix is defined as $J_f(x) = (\partial_{x_j} f_i(x))_{1 \leq i \leq m, 1 \leq j \leq n}$

### Gradient

$\nabla f(x_0) = \begin{pmatrix}  \partial_{x_1} f(x_0) \\ \cdots \\ \partial_{x_n} f(x_0) \end{pmatrix}$

### Hessian Matrix

$H_f(x) = (\partial_{x_i, x_j} f)_{1 \leq i, j \leq n}$

## The Differential

$f$ is *differentiable* at $x_0$ with the differential $u$ if $\lim_{x \to x_0} \frac{1}{\|x - x_0\|} (f(x) - f(x_0) - u(x - x_0)) = 0$. We denote $df(x_0) = u$.

$f$ is *differentiable* on $X$ if $f$ is differentiable for all $x \in X$.

### Directional Derivative

$w = \lim_{t \to 0} \frac{f(x_0 + tv) - f(x_0)}{t}$ is the directional derivative in the direction $v$, where $g(t) = f(x_0 + tv)$.

This means the directional derivative is $D_u f(a) = \frac{d}{dt} f(a + tu)$ with $t = 0$.

We can simply compute it using the gradient $D_u f(a) = \nabla f(a) \cdot u$.

## Higher Derivatives

## Change of Variable

## Taylor Polynomials

## Critical Points

## Lagrange Multipliers

### Directional Derivative

# Integration in $\mathbb{R}^n$
