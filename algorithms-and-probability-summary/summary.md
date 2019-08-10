# Algorithmen und Wahrscheinlichkeit Übersicht

## Graphentheorie

* **Satz von Menger**: Sei `G = (V, E)` ein Graph, dann gilt
  1. `G` ist genau dann `k`-zusammenhängend, wenn es für alle Paare von Knoten `u != v` mindestens `k` intern-knotendisjunkte `u`-`v`-Pfade gibt.
  2. `G` ist genau dann `k`-kanten-zusammenhängend, wenn es für alle Paare von Knoten `u != v` mindestens `k` kantendisjunkte `u`-`v`-Pfade gibt.
* Matchings
  * Inklusionsmaximal: Es gibt keine weitere Kante, die hinzugefügt werden kann, ohne das Matching zu zerstören.
  * Kardinalitätsmaximal: Es gibt kein Matching mit einer grösseren Kardinalität.
* Färbungen
  * Chromatische Zahl `x(G)`: Minimale Anzahl Farben, die für eine Knotenfärbung von `G` benötigt wird.
* **Satz von Hall, Heiratssatz**: Für einen bipartiten Graphen `G = A + B` gibt es genau ein Matching `M` der Kardinalität `|M| = |A|`, wenn gilt `N(X) >= |X|` für alle Teilmengen `X` in `A`.
* **Diracs Theorem**: Wenn `G = (V, E)` ein Graph mit `|V| >= 3` Knoten ist, dn dem jeder Knoten mindestens `|V|/2` Nachbarn hat, dann ist `G` hamiltonisch.
* **Satz von Brooks**: Ist `G = (V, E)` ein zusammenhängender Graph, der weder vollständig noch ein ungerader Kreis ist, so gilt die chromatische Zahl `x(G)` ist kleiner oder gleich der maximalen Kantenzahl eines Knotens `∆(G)`.
* **Maxflow-Mincut Theorem**: Jedes Netzwerk `N = (V, A, c, s, t)` erfüllt `max(val(f)) = min(cap(S, T))`.
* Restnetzwerk: Es enthält auschliesslich die folgenden Kanten
  * Ist 
* Minimale Schnitte als Flussproblem

### Graphenalgorithmen

* Algorithmus von Prim

**`prim(G, c)`**, Laufzeit `O(|E| log(|V|))`
```
v = V.randomElement()
S <- Set
S.insert(v)
for x in V\S
    if N(v).contains(x)
        s[x] = c(v, x)
        pred[x] = v
    else
        s[x] = INFINITY
        pred[x] = NULL


T <- SpanningTree
Q <- PriorityQueue
for x in V\S
    Q.insert(x, s[x])
while S != V
    xMin = Q.extractMin()
    S.insert(xMin)
    T.insert(xMin, pred[xMin])
    for x in N(xMin) intersects V\S
        if(c(xMin, x) < s[x])
            s[x] = c(xMin, x)
            pred[x] = xMin
            Q.decreaseKey(x, s[x])
return T
```

* Algorithmus von Kruskal

**`kruskal(G, c)`**, Laufzeit `O(|E| log(|V|))`
```
sort(E)
T <- SpanningTree
U <- UnionFind
for v in V
    X.insert(v)
for i in 1..m
    e[i] = {x, y}
    if X.find(x) != X.find(y)
    T.insert(e[i])
    X.union(x, y)
```

* Tiefensuche

**`dfsVisit(G, v)`**, Laufzeit `O(|V| + |E|)`
```
v.visited = TRUE
for {v, w} in E
    if !w.visited
        dfsVisit(G, w)
```

* Artikulationsknoten berechnen

**`articulationVertex(G, v)`**, Laufzeit `O(|V| + |E|)`
```
for v in V
    dfs[v] = 0
num = 0
T = {}
modifiedDfsVisit(G, s)
if s hat in T mindestens Grad zwei
    isArtVert[s] = true
else
    isArtVert[s] = false
```

**`modifiedDfsVisit(G, v)`**
```
num++
dfs[v] = num
low[v] = dfs[v]
isArtVert[v] = false
for {v, w} in E do
    if dfs[w] = 0 // not yet visited
        T.insert({v, w})
        val = dfsVisit(G, w)
        if val >= dfs[v]
            isArtVert[v] = true
        low[v] = min(low[v], val)
    if dfs[w] != 0 // already visited
        low[v] = min(low[v], dfs[w])
return low[v]
```

* Brücken berechnen
  * Laufzeit `O(|E|)`
  * Berechne Artikulationsknoten
  * Für jede Kante, falls beide Knoten Artikulationsknoten sind oder grad eins haben, ist diese Kante eine Brücke

* Eulertour

**`eulerTour(G, vStart)`**, Laufzeit `O(|E|)`
```
W = randomTour(vStart)
vSlow = W.front()
while vSlow != W.back()
    v = W.next(vSlow)
    if N(v) != EMPTY
        WNew = randomTour(v)
        W1 = W.subset(W.front(), v)
        W2 = W.subset(v, W.back())
        W = W1 + WNew + N2
    vSlow = W.next(vSlow)
return W
```

**`randomTour(vStart)`**
```
v = vStart
W <- List
W.add(v)
while N(v) != EMPTY
    vNext = random(N(v))
    W.add(vNext)
    e = {v, vNext}
    G.delete(e)
    v = vNext
return W
```

* Hamiltonkreis

TODO

**`hamiltoninanCycle(G = ([n], E))`**, Laufzeit `O(n^2 * 2^n)`
```
// Initialisierung
for x in [n], n != 1
    P[{1, x}][x] = E.contains({1, x}) ? 1 : 0

// Rekursion
for s in 3..n
     for all S in [n], 1 
// Ausgabe
```

Dieser Algorithmus kann auch verwendet werden, um Gray Codes zu finden.

* Travelling Salesman Problem
  * Laufzeit `O(n^3)`
  * Mit diesem Algorithmus kann ein Hamiltonkreis mit weniger als 3/2 Mal der Länge des kürzesten Hamiltonkreises gefunden werden
  * Finde minimalen Spannbaum, verdopple die Kanten
  * Finde Eulertour im Spannbaum
  * Lasse bereits besuchten Knoten aus

* Greedy-Matching

**`greedyMatching(G)`**, Laufzeit `O(|E|)`
```
M <- List
while(E != EMPTY)
    e = random(E)
    M.add(e)
    Lösche e und alle inzidenten Kanten
```

Mit augmentierenden Pfaden kann das resultierende inklusionsmaximale Matching noch vergrössert werden.

Zudem kann bei `2^k`-regulären bipartiten Graphen in der Zeit `O(|E|)` ein perfektes Matching mit einem modifizierten `eulerTour`-Algorithmus gefunden werden.

* Ford Fulkerson Algorithmus

**`fordFulkerson(V, A, c, s, t)`**
```
f = 0 // Setze Fluss auf null
while s-t-Pfad P in (V, A_f) // Falls ein augmentierender Pfad im Restnetzwerk existiert
    Erhöhe den Fluss f entlang P
return f
```

## Wahrscheinlichkeitstheorie

* Prinzip von Laplace: Alle Elemetarereignisse sind gleich wahrscheinlich.
* Bedingte Wahrscheinlichkeit
* **Satz der totalen Wahrscheinlichkeit**: Seien `A_1, ..., A_n` paarweise disjunkt und es gelte `B = ⊆ A_1 ∪ ... ∪ A_n`, dann folgt `Pr[B] = ∑(Pr[B|A_i] * Pr[A_i])`.
* **Satz von Bayes**: Seien `A_1, ..., A_n` paarweise disjunkt und es gelte `B = ⊆ A_1 ∪ ... ∪ A_n` mit `Pr[B] > 0`, dann gilt für ein beliebiges `i = 1, ..., n`, dass `Pr[A_i|B] = Pr[A_i ∩ B] / Pr[B] = (Pr[B|A_i] * Pr[A_i]) / (∑(Pr[B|A_j] * Pr[A_j]))`.
* Unabhängigkeit: Die Ereignisse `A` und `B` heissen unabhängig, falls `Pr[A ∩ B] = Pr[A]* Pr[B]`.
* Zufallsvariablen: Eine Abbildung `X: O -> R`, wobei `O` die Ergebnismengie eines Wahrscheinlichkeitsraumes ist.
* Erwartungswert: `E[X] := ∑(x * Pr[X = x])`
* Varianz: `Var[X] := E[(X - E[X])^2] = ∑((x - E[X])^2 * Pr[X = x])`
* Standardabweichung: `σ := sqrt(Var[X])`
* Dichtefunktion: `f_X: R -> [0, 1], x -> Pr[X = x]`
* Verteilungsfunktion: `F_X: R -> [0, 1], x -> Pr[X <= x]`
* Linearität des Erwartungswerts: Für Zufallsvariablen `X_1, ..., X_n` und `X := a_1 * X_1 + ... + a_n * X_n` gilt `E[X] = a_1 * E[X_1] + ... + a_n * E[X_n]`.
* Gedächtnislosigkeit: Eigenschaft der geometrischen Verteilung, Anzahl der Versuche hat keinen Einfluss auf den Erfolg des nächsten Versuchs
* Unabhängigkeit von Zufallsvariablen: Zufallsvariablen `X_1, ..., X_n` heissen unabhänngig, genau dann wenn für alle `x_1, ..., x_n` gilt, dass `Pr[X_1 = x_1, ..., X_n = x_n] = Pr[X_1 = x_1] * ... * Pr[X_n = x_n]`
* Momente zusammengesetzter Zufallsvariablen: Für unabhängige Zufallsvariablen `X_1, ..., X_n` gilt
  * `E[X_1 * ... * X_n] = E[X_1] * ... * E[X_n]`
  * `Var[X_1 + ... + X_n] = Var[X_1] + ... + Var[X_n]`
* Varianz für Coupon-Collector Problem
* **Waldsche Identität**: `N` und `X` seinen zwei unabhängige Zufallsvariablen, wobei für den Wertebereich von `N`  gilt, dass er eine Teilmenge der natürlichen Menge ist. Weiter sei `Z = ∑X_i`, wobei `X_1, X_2, ...` unabhähige Kopien von `X` sind. Dann gilt `E[Z] = E[N] * E[X]`.
* Monte-Carlo Algorithmen: Konstante Laufzeit, Fehler in der Ausgabe
* Las-Vegas Algorithmen: Variable Laufzeit (muss nicht terminieren), Ausgebe immer korrekt
* Wiederholung randomisierter Algorithmen zur Reduktion der Fehlerwahrscheinlichkeit:
  * Sei `A` ein Las-Vegas Algorithmus mit `Pr[A korrekt] >= e` und `A_d`, `d > 0` der Algorithmus, der `A` solange aufruft, bis entweder ein Wert verschieden von `???` ausgegeben wird oder `N = e^(-1) * ln(d^(-1))` mal `???` ausgegeben wurde, so gilt für `A_d`, dass `Pr[A_d korrekt] >= 1 - d`.
  * Sei `A` ein Monte-Carlo Algorithmus, der immer entweder `true` oder `false` ausgibt, wobei <br/> `Pr[A(I) = true] = 1`, falls `I` eine `true`-Instanz ist, und <br/> `Pr[A(I) = false] >= e`, falls `I` eine `false`-Instanz ist. <br/> Sei `A_d`, `d > 0` der Algorithmus, der `A` solange aufruft, bis entweder der Wert `false` ausgegeben wird oder `N = e^(-1) * ln(d^(-1))` mal `true` ausgegeben wurde, so gilt für `A_d`, dass `Pr[A_d korrekt] >= 1 - d`.

### Verteilungen

|Verteilung|Beschreibung|Notation|Erwartungswert|Varianz|
|---|---|---|---|---|
|Bernoulli-Verteilung|Ein Ereignis tritt mit Wahrscheinlichkeit `p` ein|`X ~ Bernoulli(p)`|`E[X] = p`|`Var[X] = p(1 -p)`|
|Binomialverteilung|Ein Ereignis tritt mit Wahrscheinlichkeit `p` ein, wir zählen wie oft dieses Ereignis eintritt bei `n` Versuchen|`X ~ Bin(n, p)`|`E[X] = np`|`Var[X] = np(1 - p)`|
|Poisson-Verteilung|Die Poisson-Verteilung resultiert aus dem Grenzwert der Binomialverteilung, wenn `n` gegen unendlich konvergiert und `p` sehr klein ist|`X ~ Po(p)`|`E[X] = p`|`Var[X] = p`|
|Geometrische Verteilung|Wie oft muss ein Experiment mit Erfolgswahrscheinlichkeit `p` wiederholt werden, bis es Erfolgreich ist|`X ~ Geo(p)`|`E[X] = 1 / p`|`Var[X] = (1 - p) / p^2`|

### Abschätzen von Wahrscheinlichkeiten

|Schranke|Anwendungsbereich|Formel|
|---|---|---|
|Markov|Sei `X` eine Zufallsvariable, die nur nicht-negative Werte annimmt, dann kann für alle `t > 0` die Markov-Schranke angewendet werden.|`Pr[X >= t] <= E[X] / t` <br/> `Pr[X >= t * E[X]] <= 1 / t`|
|Chebyshev|Sei `X` eine Zufallsvariable, dann kann für alle `t > 0` die Chebyshev-Schranke angewendet werden.|`Pr[|X - E[X]| >= t] <= Var[X] / t^2` <br/> `Pr[|X - E[X]|] >= t * sqrt(Var[X]) <= 1 / t^2`|
|Chernoff|Seien `X_1, ..., X_n` unabhängige Bernoulliverteilte Zufallsvariablen mit `Pr[X_i = 1] = p_i` und `Pr[X_i = 0] = 1 - p_i`, dann kann für `X := ∑X_i` die Chernoff-Schranke angewendet werden.|(i) `Pr[X >= (1 + d) * E[X]] <= exp(-1/3 * d^2 * E[X])` für alle `0 < d <= 1` <br/> (ii) `Pr[X <= (1 - d) * E[X]] <= exp(-1/2 * d^2 * E[X])` für alle `0 < d <= 1` <br/> (iii) `Pr[X >= t] <= 2^(-t)` für `t >= 2eE[X]`

### Probleme

* **Coupon-Collector Problem**: Sei `X` die Anzahl der Käufe bis zur Komplettierung der Sammlung. Wir Teilen die Zeit in Phasen ein, wobei Phase `i` die die Schritte bis zum erwerb vom `i`-ten Bildes, beginnend bei `i = 0`, bezeichne. Sei `X_i` die Anzahl der Käufe in Phase `i`. `X_i` ist geometrisch verteilt mit `X_i ~ Geo((n - i) / n)` und es gilt somit `E[X_i] = n / (n - i)`. Wegen der Linearität des Erwartungswerts folgt somit, dass `E[X] = sum(E[X_i], 0, n) = sum((n / (n - i)), 0, n) = n * sum(1 / i, 1, n + 1) = n * ln(n) + O(n)`, da `sum(1 / i, 1, n + 1)` die `n`-te harmonische Zahl bezeichnet, die `ln(n) + O(1)` ist.
* **Geburtstagsproblem**: Wie gross ist die Wahrscheinlichkeit, dass in einer `m`-köpfigen Gruppe zwei Personen am selben Tag Geburtstag haben? Man werfe `m` Bälle zufällig und gleich wahrscheinlich in `n` Körbe. Wie gross ist die Wahrscheinlichkeit,dass nach dem Experiment jeder Ball alleine in seinem Korb liegt? Für `m > n` folgt aus dem Schubfachprinzip, dass es immer einen Korb mit mehr als einem Ball gibt. Für `m <= n` gilt. `A_i` bezeichne das Ereignis "Ball i landet in einem leeren Korb". Das gesuchte Ereignis "Alle Bälle liegen allein in einem Korb" bezeichen wir als `A`. Wir berechnen `Pr[A] = Pr[∩ A_i] = A[A_i] * Pr[A_2|A_1] * Pr[A_3|A_2 ∩ A_1] * ... * Pr[A_m|∩ A_i]`. Die Wahrscheinlichkeit, dass der `j`-te Ball in einem leeren Korb landet, wenn bereits die vorherigen `j - 1` Bälle jeweils allein in einem Korb gelandet sind, somit muss der `j`-te Ball in einem der `n - (j - 1)` leeren Körbe landen. Somit gilt `Pr[A_j|∩ A_i] = (n - (j - 1)) / n = 1 - (j - 1) / n`. Durch die Abschätzung `1 - x <= exp(-x)` und da `Pr[A_1] = 1` erhalten wir `Pr[A] = ∏(1 - (j - 1) / n) <= ∏exp(-(j - 1) / n) = exp(-m(m - 1) / (2n))`.

### Randomisierte Algorithmen

* Primzahltest
* Target-Shooting
* Finden von Duplikation
* Min-Cut Algorithmus
* Miller-Rabin Primzahltest

* Lange Pfade

* Quickselect

**`quickSelect(A, l, r, k)`**, Laufzeit `O(n)`
```
p = random(l, r)
t = partition(A, l, r, p) // umsortieren der Elemente und Ausgabe der neuen Position von A[p]
if k = t
    return A[t]
else if k < t
    return quickSelect(A, l, t - 1, k) // gesuchtes Element ist links
else
    return quickSelect(A, t + 1, r, k - t) // gesuchtes Element ist rechts
```

### Geomtrische Algorithmen

* Kleinster umschliessender Kreis

**`smallestEnclosingCircle(P)`**, Laufzeit `O(n log(n))`
```
while true
    Q = randomSubset(P, 11)
    c = C(Q)
    if c.contains(P)
        return c
    verdopple Punkte von P ausserhalb von c
```

* Konvexe Hülle

**`jarvisWrap(P)`**, Laufzeit `O(nh)`, wobei `h` die Anzahl Ecken der Hülle bezeichnet
```
h = 0
pNow = Punkt mit kleinster x-Koordinate
do
    q[h] = pNow
    pNow = findNext(q[h])
    h++
while pNow = q[0]
return q
```

**`findNext(q)`**
```
p[0] = random(P \ {q})
qNext = p[0]
for p in (P \ {q})
    if p rechts von qNext
        qNext = p
return qNext
```