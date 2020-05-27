---
title: Wahrscheinlichkeit und Statistik Prüfungsnotizen
author: Fabian Bösiger
geometry: margin=2cm
fontsize: 10pt
papersize: a5
toc: yes
toc-depth: 3
toc-title: Inhalt
header-includes:
- \usepackage[document]{ragged2e}
---

\newpage

## Wahrscheinlichkeiten

### Grundbegriffe

#### Ereignisraum 

$\Omega$: Menge aller möglichen elementaren Ereignissen.
  
*Beispiel*: Bei einem Würfelwurf sind die Elementarereignisse $\Omega = \{1, 2, 3, 4, 5, 6\}$.

#### Potenzmenge

$\mathcal P(\Omega)$ oder $2^\Omega$: Menge aller Teilmengen von $\Omega$.

#### Klasse aller beobachtbaren Ereignisse

$\mathcal F$: $\mathcal F \subseteq \mathcal P(\Omega)$ und $\mathcal F$ ist eine $\sigma$-Algebra. Bei diskreten, d.h. endlichen bzw. abzählbaren Wahrscheinlichkeitsräumen wird $\mathcal F = \mathcal P(\Omega)$ gewählt.

$\sigma$-Algebra: $\mathcal F \subseteq \mathcal P(\Omega)$ ist eine $\sigma$-Algebra, wenn gilt:

1. $\Omega \in \mathcal F$
2. $A \in \mathcal F \implies A^c \in \mathcal F$
3. $(A_n)_{n \in \mathbb N}, A_n \in \mathcal F \implies \bigcup \limits_{n=1}^\infty A_n \in \mathcal F$

*Beispiel*: Jemand wirft einen Würfel und teilt uns mit, ob die gewürfelte Zahl gerade oder ungerade ist.

Wir könnten den Grundraum $\Omega_1 = \{G, U\}$ wählen mit $G$ für gerade und $U$ für ungerade. In diesem Fall wäre $\mathcal F = \{\emptyset, \Omega_1, \{G\}, \{U\}\}$.

Jedoch könnten wir auch den Grundraum $\Omega_2 = \{1, 2, 3, 4, 5, 6\}$ wählen. Dann wäre $\mathcal F = \{\emptyset, \Omega_2, \{2, 4, 6\}, \{1, 3, 5\}\} \neq \mathcal P(\Omega_2)$, da beispielsweise das prinzipielle Ereignis $\{1\}$ nie beobachtbar ist.

#### Wahrscheinlichkeitsmass

$P: \mathcal F \to [0, 1]$: $P[A] \in \mathcal F] \in [0, 1]$ ist die Wahrscheinlichkeit, dass $A$ eintritt. Dabei muss gelten:

1. $\forall A \in \mathcal F: P[A] \geq 0$
2. $P[\Omega] = 1$
3. $P[\bigcup \limits_{i=1}^\infty A_i] = \sum \limits_{i=1}^\infty P[A_i]$, sofern die $A_i \in \mathcal F$ paarweise disjunkt sind.

Folgende Rechenregeln lassen sich herleiten:

1. $P[A^c] = 1 - P[A]$
2. $P[\emptyset] = 0$
3. Für $A \subseteq B$ gilt $P[A] \leq P[B]$
4. Additionsregel: $P[A \cup B] = P[A] + P[B] - P[A \cap B]$

### Diskrete Wahrscheinlichkeitsräume

Bei diskreten, d.h. endlichen bzw. abzählbaren Wahrscheinlichkeitsräumen gilt $\mathcal F = \mathcal P(\Omega)$ und $P[A] = \sum \limits_{w_i \in A} P[\{w_i\}]$.

#### Laplace-Raum

$\Omega$ ist endlich und ich alle Elementarereignisse $\Omega = \{w_1, \ldots, w_N\}$ sind gleich wahrscheinlich mit $p_1 = \cdots = p_N = \frac 1 N$.

#### Diskrete Gleichverteilung

In einem Laplace-Raum gilt für beliebige $A \subseteq \Omega$: $P[A] = \frac{|A|}{|\Omega|}$.

*Beispiel*: Beim zweimaligen Münzwurf ist $\Omega = \{KK, KZ, ZK, ZZ\}$, also $|\Omega| = 4$ und damit $p_i = \frac 1 4$. Dann ist $P[\text{Mindestens einmal Kopf}] = P[\{KK, KZ, ZK\}] = \frac 3 4$.

### Bedingte Wahrscheinlichkeiten

#### Bedingte Wahrscheinlichkeit

Wahrscheinlichkeit, dass $B$ eintritt, unter der Bedingung, dass $A$ eintritt: $P[B \mid A] := \frac{P[B \cap A]}{P[A]}$.

#### Satz von der totalen Wahrscheinlichkeit (Satz 1.1)

Sei $A_1, \dots, A_n$ eine Zerlegung von $\Omega$ in paarweise disjunkte Ereignisse, dann gilt für beliebiege Ereignisse $B$: $P[B] = \sum \limits_{i=1}^n P[B \mid A_i] P[A_i]$.

#### Formel von Bayes (Satz 1.2)

Ist $A_1, \dots, A_n$ eine Zerlegung von $\Omega$ mit $P[A_i] > 0$ und $B$ ein Ereignis mit $P[B] > 0$, so gilt für jedes $k$: $P[A_k \mid B] = \frac{P[B \mid A_k] P[A_k]}{\sum \limits_{i=1}^n P[B \mid A_i] P[A_i]}$.

### Unabhängigkeit

#### Stochastische Unabhängigkeit

Zwei Ereignisse $A$, $B$ heissen stochastisch unabhängig, falls $P[A \cap B] = P[A] P[B]$.

Allgemeiner: Zwei Ereignisse $A$, $B$ heissen stochastisch unabhängig, wenn für jede endliche Teilfamile $\{k_1, \dots, k_m\} \subseteq \{1, \dots, n\}$ gilt, dass $P\bigg[\bigcap \limits_{i=1}^m A_{k_i}\bigg] = \prod \limits_{i=1}^m P[A_{k_i}]$.

Ist $P[A] = 0$ oder $P[B] = 0$, so sind $A$ und $B$ immer unabhängig.

Für $P[A] \neq 0$ gilt: $A$, $B$ unabhängig $\iff$ $P[B \mid A] = P[B]$.

## Diskrete Zufallsvariablen und Verteilungen

### Grundbegriffe

#### Diskrete Zufallsvariable

Funktion $X: \Omega \to \mathbb R$, $W(X)$: Wertebereich von $X$.

#### Indikatorfunktion

Für jede Teilmente $A \subset \Omega$ gilt: 
$I_A(w) := \begin{cases}
    1 & w \in A\\
    0 & w \in A^c
\end{cases}$

#### Verteilungsfunktion

$F_x: \mathbb R \to [0, 1]$, $F_X(t) := P[X \leq t] = P[\{w \mid X(w) \leq t\}]$.

#### Gewichtsfunktion

$p_x: W(X) \to [0, 1]$, $p_X(x_k) := P[X = x_k] = P[\{w \mid X(w) = x_k\}]$.

Es gilt $F_X(t) = P[X \leq t] = \sum \limits_{x_k \leq t} p_X(x_k)$

### Erwartungswerte

#### Erwartungswert

$E[X] := \sum \limits_{x_k \in W(X)} x_k p_X(x_k)$. Es gilt:

1. Monotonie: Ist $X \leq Y$ (d.h. $\forall w: X(w) \leq Y(w)$), so gilt auch $E[X] \leq E[Y]$
2. Linearität: $E[aX + b] = a E[X] + b$
3. Falls $X \geq 0$, so gilt $E[X] = \sum \limits_{j=1}^\infty P[X \geq j]$

#### Varianz

 $\text{Var}[X] := E[(X - E[X])^2]$. Es gilt:

1. $\text{Var}[X] = E[X^2] - (E[X])^2$
2. $\text{Var}[aX + b] = a^2 \text{Var}[X]$

#### Standardabweichung

$\sigma(X) = \sqrt{\text{Var}[X]}$.

### Gemeinsame Verteilungen und unabhängige Zufallsvariablen

#### Gemeinsame Verteilungsfunktion

$F: \mathbb{R}^n \to [0, 1]$, $F(x_1, \dots, x_n) := P[X_1 \leq x_1, \dots, X_n \leq x_n]$

#### Gemeinsame Gewichtsfunktion

$p: \mathbb{R}^n \to [0, 1]$, $p(x_1, \dots, x_n) := P[X_1 = x_1, \dots, X_n = x_n]$

$F(x_1, \dots, x_n) = P[X_1 \leq x_1, \dots, X_n \leq x_n] = \sum \limits_{y_1 \leq x_1, \dots, y_n \leq x_n} P[X_1 = y_1, \dots, X_n = y_n] = \sum \limits_{y_1 \leq x_1, \dots, y_n \leq x_n} p(y_1, \dots, y_n)$

#### Randverteilung

Verteilungsfunktion der Randverteilung von $X$: $F_X(x) := P[X \leq x] = P[X \leq x, Y < \infty] = \lim \limits_{y \to \infty} F(x, y)$

Gewichtsfunktion der Randverteilung von $X$: $p_X(x) := P[X = x] = \sum \limits_{y_j \in W(Y)} P[X = x, Y = y_j] = \sum \limits_{y_j \in W(Y)} p(x, y_j)$

#### Unabhängigkeit

$X_1, \dots, X_n$ heissen unabhängig, falls $F(x_1, \dots, x_n) = F_{X_1}(x_1) \cdots F_{X_n}(x_n)$ beziehungsweise $p(x_1, \dots, x_n) = p_{X_1}(x_1) \cdots p_{X_n}(x_n)$.

### Funktionen von mehreren Zufallsvariablen

#### Linearität (Satz 2.4)

Seien $X_1, \dots, X_n$ diskrete Zufallsvariablen mit endlichen Erwartungswerten. Sei $Y = a + \sum \limits_{l=1}^n b_l X_l$ mit Konstanten $a, b_1, \dots, b_n$. Dann gilt $E[Y] = a + \sum \limits_{l=1}^n b_l E[X_l]$.

#### Kovarianz

$\text{Cov}(X_1, X_2) := E[X_1 X_2] - E[X_1] E[X_2]$

$\text{Cov}(X, X) = Var[X]$

#### Unkorreliertheit

$X_1$ und $X_2$ sind unkorreliert, wenn gilt $\text{Cov}(X_1, X_2) = 0$.

#### Produkte von Zufallsvariablen (Satz 2.5)

Seien $X_1, \dots, X_n$ diskrete Zufallsvariablen mit endlichen Erwartungswerten. Falls $X_1, \dots, X_n$ unabhängig sind, so gilt: $E\bigg[ \prod \limits_{i=1}^n X_i \bigg] = \prod \limits_{i=1}^n E[X_i]$. Ausserdem sind dann $X_1, \dots, X_n$ unkorreliert und es gilt: $\text{Var} \bigg[ \prod \limits_{i=1}^n X_i \bigg] = \prod \limits_{i=1}^n \text{Var}[X_i]$.

### Bedingte Verteilungen

#### Bedingte Gewichtsfunktion

Seien $X$ and $Y$ diskrete Zufallsvariablen mit gemeinsamer Gewichtsfunktion $p(x, y)$. Die bedingte Gewichtsfunktion von $X$, gegeben dass $Y = y$, is te definiert durch $p_{X \mid Y}(x \mid y) := P[X = x \mid Y = y] = \frac{P[X = x, Y = y]}{P[Y = y]} = \frac{p(x, y)}{p_Y(y)}$ für $p_Y(y) > 0$ und $0$ sonst.

## Wichtige diskrete Verteilungen

|Verteilung|Kurz|Gewichtsfunktion $p_X(k) = P[X = k]$|Erwartungswert $E[X]$|Varianz $\text{Var}[X]$|
|---|---|---|---|---|
|Diskrete Gleichverteilung||$\frac{1}{N}$||
|Bernoulli-Verteilung|$X \sim \text{Be}(p)$|$p^k (1 - p)^{1-k}$|$p$|$p(1 - p)$|
|Binomialverteilung|$X \sim \text{Bin}(n, p)$|${n \choose k} p^k (1 - p)^{n-k}$|$np$|$np(1 - p)$|
|Geometrische Verteilung|$X \sim \text{Geom}(p)$|$p (1 - p)^{k-1}$|$\frac{1}{p}$|$\frac{1 - p}{p^2}$|
|Negativbinomiale Verteilung|$X \sim \text{NB}(r, p)$|${{k - 1} \choose {r - 1}} p^r (1 - p)^{k-r}$|$\frac{r}{p}$|$\frac{r(1 - p)}{p^2}$|
|Hypergeometrische Verteilung||$\frac{{r \choose k} {{n - r} \choose {m - k}}}{{n \choose m}}$|||
|Poisson-Verteilung|$X \sim \mathcal{P}(\lambda)$|$e^{-\lambda} \frac{\lambda^k}{k!}$|$\lambda$|$\lambda$|

## Allgemeine Zufallsvariablen

### Grundbegriffe

||Diskrete Zufallsvariablen|Allgemeine Zufallsvariablen|
|---|---|---|
|Zufallsvariable||
|Verteilungsfunktion|$F_X: \mathbb{R} \to [0, 1] \newline F_X(t) := P[X \leq t] := P[\{w \mid X(w) \leq t\}] = \sum \limits_{x_k \leq t} p_X(x_k)$|$F_X: \mathbb{R} \to [0, 1] \newline F_X(t) := P[X \leq t] := P[\{w \mid X(w) \leq t\}] = \int \limits_{-\infty}^t f_X(s) ds$|
|Gemeinsame Verteilungsfunktion|$F: \mathbb{R}^n \to [0, 1] \newline F(x_1, \dots, x_n) = P[X_1 \leq x_1, \dots, X_n \leq x_n] = \sum \limits_{y_1 \leq x_1, \dots, y_n \leq x_n} p(y_1, \dots, y_n)$|$F: \mathbb{R}^n \to [0, 1] \newline F(x_1, \dots, x_n) = P[X_1 \leq x_1, \dots, X_n \leq x_n] = \int \limits_{-\infty}^{x_1} \cdots \int \limits_{-\infty}^{x_n} f(t_1, \dots, t_n) dt_n \cdots dt_1$|
|*Monoton wachsend*|$\forall s \leq t: F_X(s) \leq F_X(t)$|Analog|
|*Rechtsstetig*|$\forall u > t, u \to t: F_X(u) \to F_X(t)$|Analog|
||$\lim \limits_{t \to \infty} F_X(t) = 1$, $\lim \limits_{t \to -\infty} F_X(t) = 0$|Analog|
|Verteilung|$\mu_X(B) := P[X \in B] = \sum \limits_{x_k \in B} p_X(x_k)$|$\mu_X(B) := P[X \in B] = \int \limits_B f_X(s) ds$|
|Gewichtsfunktion, Dichtefunktion|$p_X(x_k) := P[X = x_k] = P[\{w \mid X(w) = x_k\}]$|$f_X(t) = \frac{d}{dt} F_X(t)$|
|||$f_X \geq 0$, $f_X = 0$ ausserhalb von $W(X)$|
|||$f(x_1, \dots, x_n) \geq 0$, $f(x_1, \dots, x_n) = 0$ ausserhalb von $W(X_1, \dots, X_n)$|
|||$\int \limits_{-\infty}^{\infty} f_X(s) ds = 1$|
|||$\int \limits_{-\infty}^{\infty} \cdots \int \limits_{-\infty}^{\infty} f(x_1, \dots, x_n) dx_n \cdots dx_1 = 1$|
|Erwartungswert|$E[X] = \sum \limits_{-\infty}^{\infty} x f(x) dx$|$E[X] = \int \limits_{-\infty}^{\infty} x f(x) dx$|
|Varianz|$\text{Var}(X) = E[X^2] - E[X]^2$|Analog|

### Wichtige stetige Verteilungen

|Verteilung|Kurz|Wertebereich $W(X)$|Dichtefunktion $f_X(t)$|Verteilungsfunktion $F_X(t)$|Erwartungswert $E[X]$|Varianz $\text{Var}[X]$|
|---|---|---|---|---|---|---|
|Gleichverteilung|$X \sim \mathcal{U}(a, b)$|$[a, b]$|$\begin{cases} \frac{1}{b - a} & a \leq t \leq b \\ 0 & \text{sonst} \end{cases}$|$\begin{cases} 0 & t < a \\ \frac{t - a}{b - a} & a \leq t \leq b \\ 1 & t > b \end{cases}$|$\frac{a + b}{2}$|
|Exponentialverteilung|$X \sim \text{Exp}(\lambda)$|$[0, \infty)$|$\begin{cases} \lambda e^{-\lambda t} & t \geq 0 \\ 0 & t < 0 \end{cases}$|$\begin{cases} 1 - e^{-\lambda t} & t \geq 0 \\ 0 & t < 0 \end{cases}$|
|Normalverteilung|$X \sim \mathcal{N}(\lambda, \sigma^2)$|$\mathbb{R}$|$\frac{1}{\sigma \sqrt{2 \pi}} \exp({-\frac{(t-\mu)^2}{2 \sigma^2}})$||$\mu$|$\sigma^2$|

#### Standard-Normalverteilung

Für die Standard-Normalverteilung $\mathcal{N}(0, 1)$ gilt $F_X(t) = \Phi(t)$.

Ist $X \sim \mathcal{N}(\mu, \sigma^2)$, so ist $\frac{X - \mu}{\sigma} \sim \mathcal{N}(0, 1)$.

|$t$|$\Phi(t)$|
|---|---|

TODO: Tabelle

#### Abhängige Zufallsvariablen (Satz 4.1)

Sei $X$ eine Zufallsvariable und $Y = g(X)$ eine weitere Zufallsvarable. Ist $X$ stetig mit Dichte $f_X(x)$, so ist $E[Y] = E[g(X)] = \int \limits_{-\infty}^\infty g(x) f_X(x) dx$.

### Gemeinsame Verteilungen und unabhängige Zufallsvariablen

### Funktionen und Transformationen von Zufallsvariablen

## Ungleichungen und Grenzwertsätze

### Ungleichungen

#### Markov-Ungleichung

Sei $X$ eine Zufallsvariable und $g: W(X) -> [0, \infty)$ eine wachsende Funktion. Dann gilt für jedes $c \in \mathbb{R}$ mit $g(c) > 0$: $F[X \geq c] \leq \frac{E[g(X)]}{g(c)}$.

#### Chebyshev-Ungleichung

Sei $Y$ eine Zufallsvariable mit endlicher Varianz. Für jedes $b > 0$ gilt dann:
 $$\boxed{P[|Y - E[Y]| \geq b] \leq \frac{Var[Y]}{b^2}}$$

### Das Gesetz der grossen Zahlen

#### Schwaches Gesetz der grossen Zahlen

Sei $X_1, X_2, \dots$ eine Folge von unabhängigen Zufallsvariablen mit dem gleichen Erwartungswert $E[X_i] = \mu$ und der gleichen Varianz $\text{Var}[X_i] = \sigma^2$. Sei $\overline X_n = \frac{1}{n} \sum \limits_{i = 1}^n X_i$. Dann konvergiert $\overline X_n$ für $n \to \infty$ stochastisch gegen $\mu = E[X_i]$, d.h.:

$$\boxed{\forall \epsilon > 0: P[|\bar X_n - \mu| > \varepsilon] \xrightarrow{n \to \infty} 0}$$

*Beweis mit Chebyshev-Ungleichung.*

#### Starkes Gesetz der grossen Zahlen

Sei $X_1, X_2, \dots$ eine Folge von unabhängigen Zufallsvariablen mit dem gleichen **endlichen** Erwartungswert $E[X_i] = \mu$ und der gleichen Verteilung. Sei $\overline X_n = \frac{1}{n} \sum \limits_{i = 1}^n X_i$. Dann konvergiert $\overline X_n$ für $n \to \infty$ fastsicher gegen $\mu = E[X_i]$, d.h.: 

$$\boxed{P[\{\omega \in \Omega \mid \bar X_n(\omega) \xrightarrow{n \to \infty} \mu\}] = 1}$$

### Der Zentrale Grenzwertsatz

Sei $X_1, X_2, \dots$ eine Folge von Zufallsvariablen mit $E[X_i] = \mu$ und $\text{Var}[X_i] = \sigma^2$. Für die Summe $S_n = \sum \limits_{i = 1}^n X_i$ gilt dann $\lim \limits_{n \to \infty} P[\frac{S_n - n \mu}{\sigma \sqrt{n}} \leq x] = \Phi(x)$ für alle $x \in \mathbb{R}$.

$S_n$ hat den Erwartungswert $E[S_n] = n \mu$ und Varianz $\text{Var}[S_n] = n \sigma^2$. Also ist $S_n^* = \frac{S_n - n \mu}{\sigma \sqrt{n}} = \frac{S_n - E[S_n]}{\sqrt{\text{Var}[S_n]}}$ die Standartisierung von $S_n$ mit $E[S_n^*] = 0$ und $\text{Var}[S_n] = 1$. Deshalb gilt für grosse $n$:

$$\boxed{P[S_n^* \leq x] \approx \Phi(x) \quad S_n^* \sim \mathcal{N}(0, 1)}$$

### Grosse Abweichungen und Chernoff-Schranken

TODO: Ist dieses Kapitel Prüfunsrelevant?

#### Momenterzeugende Funktion

Die momenterzeugende Funktion einer Zufallsvariable $X$ ist $M_X(t) := E[e^{tX}]$ für $t \in \mathbb{R}$.

#### (Satz 5.6)

## Schätzer

### Grundbegriffe

#### Stichprobe

Gesamtheit der Beobachungen $x_1, \dots, x_n$ oder Zufallsvariablen $X_1, \dots, X_n$. Die Anzahl $n$ heisst Stichprobenanzahl.

#### Stochastischer Mechanismus

$P_\vartheta$ ist ein konkreter stochastischer Mechanismus, der besagt, wie sich $X_1, \dots, X_n$ verhalten. Dabei wird der Parameter $\vartheta$ zu bestimmen versucht.

#### Schätzer

Die Schätzer $T_1, \dots, T_m$ schätzen die Parameter $\vartheta_1, \dots, \vartheta_m$. Sie sind Zufallsvariablen mit der Form $T_j = t_j(X_1, \dots, X_n)$.

#### Schätzwert

$T_j(\omega) = t_j(X_1(\omega), \dots, X_n(\omega))$ eines konkreten Experiments $\omega$.

#### Erwartungstreuheit

Ein Schätzer $T$ heisst erwartungstreu für $\vartheta \in \Theta$, falls gilt $E_\vartheta[T] = \vartheta$

#### Bias

$E_\vartheta[T] - \vartheta$

#### Mittlerer quardratischer Schätzfehler

$\text{MSE}_\vartheta[T] := E_\vartheta[(T - \vartheta)^2]$ = $Var_\vartheta[T] + (E_\vartheta[T] - \vartheta)^2$

#### Konsistenz

Eine Folge von Schätzern $T^{(n)}$ heisst konsistent für $\vartheta$, falls für jedes $\vartheta \in \Theta$ und jedes $\varepsilon > 0$ gilt: $\lim \limits_{n \to \infty} P_\vartheta[|T^{(n)} - \vartheta| > \varepsilon] = 0$. *Beweisen mit Chebychev-Ungleichung.*

### Die Maximum-Likelihood-Methode

#### Likelihood-Funktion

$L(x_1, \dots, x_n; \vartheta) := \begin{cases} p(x_1, \dots, x_n; \vartheta) & \text{Im diskreten Fall} \\ f(x_1, \dots, x_n; \vartheta) & \text{Im stetigen Fall} \end{cases}$

#### ML-Schätzer

Der ML-Schätzer $T$ für $\vartheta$ wird definiert als Maximierung von $L(X_1, \dots, X_n; \vartheta)$ als Funktion von $\vartheta$.

#### Momentenschätzer

ML-Schätzer für $\vartheta = (\mu, \sigma^2)$ ist $T = (T_1, T_2)$ mit $T_1 = \frac{1}{n} \sum \limits_{i=1}^n X_i = \overline X_n$ und $T_2 = \frac{1}{n} \sum \limits_{i=1}{n} (X_i - \overline X_n)^2 = \sum \limits_{i=1}{n} X_i^2 - \overline X_n^2$

### Verteilungsaussagen

#### Mehrere Normalverteilte Variablen (Satz 7.1)

Seien $X_1, \dots, X_n \sim \mathcal{N}(\mu, \sigma^2)$. Dann gilt:

1. $\overline X_n$ ist normalverteilt gemäss $\sim \mathcal{N}(\mu, \frac{1}{n} \sigma^2)$, und $\frac{\overline X_n - \mu}{\sigma / \sqrt{n}} \sim \mathcal{N}(0, 1)$.
2. $\frac{n - 1}{\sigma^2} S^2 = \frac{1}{\sigma^2} \sum \limits_{i = 1}^n (X_i - \overline X_n)^2$ ist $\mathcal{X}^2$-verteilt mit $n-1$ Freiheitsgraden.
3. $\overline X_n$ und $S^2$ sind unabhänging.
4. 

## Tests