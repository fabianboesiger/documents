---
title: Visual Computing
toc: yes
---

## The Digital Image

### Image

An image is a pattern of a value varying in space and time.

Mathematically, an image can be represented as a function:

$$f: \mathbb R^n \to S$$

A pixel is *not* a square, but rather a value in a point in space and time.

#### Image Resolution

|||
|---|---|
|Geometric Resolution|How many pixels per area|
|Radiometric Resolution|How many bits per pixel|

#### Image Noise

|||
|---|---|
|Additive Gaussian Noise|$I(x, y) = f(x, y) + c \textbf{, where } c \sim \mathcal{N}(0, \sigma^2)$|
|Poisson Noise|$I(x, y) = f(x, y) + c \textbf{, where } c \sim \mathcal{P}(\lambda)$|
|Rician Noise||
|Multiplicative Noise|$I(x, y) = f(x, y) + f_c(x, y)$|
|Quantization Errors||
|Salt-and-Pepper Noise||

The *signal to noise ratio* is an index of image quality:

$$s = \frac{F}{\sigma} \textbf{, where } F = \frac{1}{XY} \sum \limits_{x=1}^{X} \sum \limits_{y=1}^{Y} f(x, y)$$

The *peak signal to noise* ratio:

$$s_{peak} = \frac{F_{max}}{\sigma}$$

### Sampling

Continuous functions can be stored by sampling some points of the function. If we *undersample*, some information can get lost.

The continuous signal can be reconstructed by using methods of interpolation.

#### Bilinear Interpolation

$$f(x, y) = (1-a)(1-b) + a (1-b) + ab + (1-a)b$$

#### Nyquist Frequency

### Quantization

Real valued function will be mapped to digital values. Information will always be lost, unlike sampling.

## Image Segmentation

The goal is to partition an image into regions of interest.

### Thresholding

Lable each pixel in or out of the region of interest by comparing the greylevel with some threshold.

$$
B(x, y) =
\begin{cases}
    1 & I(x, y) \geq T \\
    0 & I(x, y) < T
\end{cases}
$$

#### ROC Analysis

An ROC (Receiver Operating Characteristic) characterizes the performance of a binary classifier.

$$
P = \textbf{total positives} \\
N = \textbf{total negatives} \\
TP = \frac{\textbf{true positive count}}{P} \\
FP = \frac{\textbf{false positive count}}{N}
$$

### Region Growing

Start from a seed point or region and add neighboring pixels that satisfy the criteria defining a region until there are no satisfying pixels left.

#### Inclusion Criteria

* Greylevel thresholding
* Greylevel distribution model
* Color or texture information

### Snakes

A snake is an ordered set of points joined up by lines

### Markov Random Fields

Image as graph, find Min-Cut.

### Morphological Operations

A binary image $I$ can be thougt of sets containing pixels with value 1.

A structuring element $S$ is a binary array similar to a binary image.

|||
|---|---|
|$S$ fits $I$ at $x$|$\{y: y = x + s\} \subset I$|
|$S$ hits $I$ at $x$|$\{y: y = x - s\} \cap I \neq \empty$|
|$S$ misses $I$ at $x$|$\{y: y = x - s\} \cap I = \empty$|

#### Erosion

Used to remove noise and smoothen region boundaries for shape analysis.

$$E = I \ominus S$$

$$
E(x) =
\begin{cases}
    1 & \textbf{if } S \textbf{ fits } I \textbf{ at } x \\
    0 & \textbf{otherwise}
\end{cases}
$$

#### Dilation

$$D = I \oplus S$$

$$
D(x) =
\begin{cases}
    1 & \textbf{if } S \textbf{ hits } I \textbf{ at } x \\
    0 & \textbf{otherwise}
\end{cases}
$$

#### Opening

$$I \circ S = (I \ominus S) \oplus S$$

#### Closing

$$I \bullet S = (I \ominus S) \oplus S$$

#### Hit-and-Miss Transform

Searches for an exact match of the structuring element.

$$H = I \otimes S$$

#### Thinning

$$I \oslash S = I \backslash (I \otimes S)$$

#### Thickening

$$I \odot S = I \cup (I \otimes S)$$

## Convolution and Filtering

Linear Filtering: Pixels are linear combination of neighbors.

Shift-invariant: Doing the same for each pixel.

Separable kernels: Kernels are separable if they can be written as: $K(m,n) = f(m)g(n)$, and the rank is 1.

### Correlation

Template matching: $I'(x, y) = \sum \limits \limits_{(i,j)} K(i, j) I(x+i, y+j)$

### Convolution

Point spread function: $I'(x, y) = \sum \limits \limits_{(i,j)} K(i, j) I(x-i, y-j)$

Same as correlation with reversed kernel.

## Image Features

### Operators

|Operator||Example Matrix|
|---|---|---|
|Prewitt||$\begin{bmatrix}-1&0&1\\-1&0&1\\-1&0&1\end{bmatrix}$|
|Sobel||$\begin{bmatrix}-1&0&1\\-2&0&2\\-1&0&1\end{bmatrix}$|
|Roberts||$\begin{bmatrix}1&0\\0&-1\end{bmatrix}$|

### Hough Transform

### Harris Corner Detector

### Canny Edge Detection

## Fourier Transforms

### Convolution Theorem

* The Fourier transform of the convolution of two functions is the product of their Fourier transforms $F(g).F(h)=F(g**h)$
* The Fourier transform of the product of two functions is the concolution of the Fourier transforms $F(g)**F(h) = F(g.h)$

### Nyquist sampling theorem

The sampling frequency must be at least twice the highest frequency (otherwise use low-pass filter beforehand).

## Unitary Transforms

### Principal Component Analysis

## Pyramids and Wavelets

## Optical Flow

### Kucas-Kanade Algorithm

Assume *brightness constancy (intensity of objects in the scene do not change in time)* . Brightness of pixel at old and new position is the same:

$$I(x, y, t) = I(x + dx, y + dy, t + dt)$$

Assume *small motion (objects move very slowly from frame to frame)* and approximate with Taylor expansion:

$$I(x, y, t) = I(x, y, t) + \frac{\partial I}{\partial x} dx + \frac{\partial I}{\partial y} dy + \frac{\partial I}{\partial t} dt$$

$$\implies \frac{\partial I}{\partial x} dx + \frac{\partial I}{\partial y} dy + \frac{\partial I}{\partial t} dt = 0$$

$$\frac{\partial I}{\partial x} \frac{dx}{dt} + \frac{\partial I}{\partial y} \frac{dy}{dt} + \frac{\partial I}{\partial t} = 0$$

$$I_x * u + I_y * v + I_t = 0$$

Assume *spatial coherence (all points in neighborhood have the same motion)* and use multiple points because we have two unknowns:

$$
\begin{bmatrix}
    I_x(p_1) & I_y(p_1) \\
    I_x(p_2) & I_y(p_2) \\
\end{bmatrix} *
\begin{bmatrix}
    u \\
    v \\
\end{bmatrix} =
\begin{bmatrix}
    I_t(p_1) \\
    I_t(p_2) \\
\end{bmatrix}
$$

Solve using least square approximation $(A^TA)x = A^Tb$.

## Transforms

## Geometry and Textures

### Geometry

#### Explicit Representations

All points are given directly.

Pros: Easier to modes shapes, points are given.

Cons: Less compact description, not exact for simple shapes.

* Point cloud
* Polygon mesh

#### Implicit Representations

Points aren't known directly, but satisfy some relationship.

Pros: Compact description, exact for simple shapes.

Cons: Hard to model complex shapes, expensive to find all poins.

* Level set
* Algebraic surface
* L-systems

## The Graphics Pipeline

### Barycentric Coordinates

Interpolation between three values.

## Rendering Equation

### Phong Reflection



## Ray Tracing

## Intro to Animation

## Rigging

## Physically-Based Animation









## Filtering 3

## Principal Component Analysis 3

## Optical Flow 3

## Fourier Transform 2

## Geometry (OpenGL) and Rendering 3

## Transformations 3

## Animation 1

## Optimization 1

## Light, Color and Ray Tracing 2

## Rigid Bodies 1

## Sampling 1

## Morphological Operators and Texture 1

## Image Features 1

## Physics Simulation 1

## Edge Detection 1

## Video Compression 1

