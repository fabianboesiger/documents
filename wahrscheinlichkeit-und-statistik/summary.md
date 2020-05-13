---
title: "Wahrscheinlichkeit und Statistik"
geometry: "margin=2cm"
---

## Wahrscheinlichkeiten

### Grundbegriffe

**Ereignisraum $\Omega$**: Menge aller möglichen elementaren Ereignissen.
  
*Beispiel*: Bei einem Würfelwurf sind die Elementarereignisse $\Omega = \{1, 2, 3, 4, 5, 6\}$.

**Potenzmenge $\mathcal P(\Omega)$ oder $2^\Omega$**: Menge aller Teilmengen von $\Omega$.

**Klasse aller beobachtbaren Ereignisse $\mathcal F$**: $\mathcal F \subseteq \mathcal P(\Omega)$ und $\mathcal F$ ist eine $\sigma$-Algebra. Bei diskreten, d.h. endlichen bzw. abzählbaren Wahrscheinlichkeitsräumen wird $\mathcal F = \mathcal P(\Omega)$ gewählt.

**$\sigma$-Algebra**: $\mathcal F \subseteq \mathcal P(\Omega)$ ist eine $\sigma$-Algebra, wenn gilt:

1. $\Omega \in \mathcal F$
2. $A \in \mathcal F \implies A^c \in \mathcal F$
3. $(A_n)_{n \in \mathbb N}, A_n \in \mathcal F \implies \bigcup \limits_{n=1}^\infty A_n \in \mathcal F$

**Beispiel**: Jemand wirft einen Würfel und teilt uns mit, ob die gewürfelte Zahl gerade oder ungerade ist.

Wir könnten den Grundraum $\Omega_1 = \{G, U\}$ wählen mit $G$ für gerade und $U$ für ungerade. In diesem Fall wäre $\mathcal F = \{\emptyset, \Omega_1, \{G\}, \{U\}\}$.

Jedoch könnten wir auch den Grundraum $\Omega_2 = \{1, 2, 3, 4, 5, 6\}$ wählen. Dann wäre $\mathcal F = \{\emptyset, \Omega_2, \{2, 4, 6\}, \{1, 3, 5\}\} \neq \mathcal P(\Omega_2)$, da beispielsweise das prinzipielle Ereignis $\{1\}$ nie beobachtbar ist.

**Wahrscheinlichkeitsmass $P: \mathcal F \to [0, 1]$**: $P[A] \in \mathcal F] \in [0, 1]$ ist die Wahrscheinlichkeit, dass $A$ eintritt. Dabei muss gelten:

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

**Laplace-Raum**: $\Omega$ ist endlich alle Elementarereignisse $\Omega = \{w_1, \ldots, w_N\}$ sind gleich wahrscheinlich mit $p_1 = \cdots = p_N = \frac 1 N$.

**Diskrete Gleichverteilung**: In einem Laplace-Raum gilt für beliebige $A \subseteq \Omega$: $P[A] = \frac{|A|}{|\Omega|}$.

*Beispiel*: Beim zweimaligen Münzwurf ist $\Omega = \{KK, KZ, ZK, ZZ\}$, also $|\Omega| = 4$ und damit $p_i = \frac 1 4$. Dann ist $P[\text{Mindestens einmal Kopf}] = P[\{KK, KZ, ZK\}] = \frac 3 4$.

### Bedingte Wahrscheinlichkeiten

### Unabhängigkeit

## Diskrete Zufallsvariablen und Verteilungen

### Grundbegriffe

**Diskrete Zufallsvariable**: Funktion $X: \Omega -> \mathbb R$, $W(X)$: Wertebereich von $X$.

**Verteilungsfunktion** $F_x: \mathbb R \to [0, 1]$: $F_X(t) := P[X \leq t] := P[\{w \mid X(w) \leq t\}]$.

**Gewichtsfunktion** $p_x: \mathbb W(X) \to [0, 1]$: $p_X(x_k) := P[X = x_k] = P[\{w \mid X(w) = x_k\}]$.

### Erwartungswerte

**Erwartungswert**: $E[X] := \sum \limits_{x_k \in W(X)} x_k p_X(x_k)$. Es gilt:

1. Monotonie: Ist $X \leq Y$ (d.h. $\forall w: X(w) \leq Y(w)$), so gilt auch $E[X] \leq E[Y]$
2. Linearität: $E[aX + b] = a E[X] + b$
3. Falls $X \geq 0$, so gilt $E[X] = \sum \limits_{j=1}^\infty P[X \geq j]$

**Varianz**: $\text{Var}[X] := E[(X - E[X])^2]$. Es gilt:

1. $\text{Var}[X] = E[X^2] - (E[X])^2$
2. $\text{Var}[aX + b] = a^2 \text{Var}[X]$

**Standardabweichung**: $\sigma(X) = \sqrt{\text{Var}[X]}$.

### Gemeinsame Verteilungen und unabhängige Zufallsvariablen



||Diskrete Zufallsvariablen|Allgemeine Zufallsvariablen|
|---|---|---|
|Verteilungsfunktion|$F_X(t) := P[X \leq t] := P[\{w \mid X(w) \leq t\}]$|Analog zum diskreten Fall mit $F_X$ stetig|
|Monoton wachsend|$\forall s \leq t: F_X(s) \leq F_X(t)$||
|Rechtsstetig|$\forall u > t, u \to t: F_X(u) \to F_X(t)$||
||$\lim \limits_{t \to \infty} F_X(t) = 1$, $\lim \limits_{t \to -\infty} F_X(t) = 0$|$$|
|Gewichtsfunktion, Dichtefunktion|$p_X(x_k) := P[X = x_k] = P[\{w \mid X(w) = x_k\}]$|$f_X(t) = \frac{d}{dt} F_X(t)$|
|||$\int \limits_{-\infty}^{\infty} f_X(s) ds = 1$|
|Erwartungswert|$E[X] = \sum \limits_{-\infty}^{\infty} x f(x) dx$|$E[X] = \int \limits_{-\infty}^{\infty} x f(x) dx$|
|Varianz|$\text{Var}(X) = E[X^2] - E[X]^2$|Analog|
||Geometrische Verteilung|Exponentialverteilung|
