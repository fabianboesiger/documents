# Algorithmen und Wahrscheinlichkeit Übersicht

## Graphentheorie

* k-Zusammenhängend
* k-Kanten-Zusammenhängend
* **Satz von Menger**: Sei `G = (V, E)` ein Graph, dann gilt
  1. `G` ist genau dann `k`-zusammenhängend, wenn es für alle Paare von Knoten `u != v` mindestens `k` intern-knotendisjunkte `u`-`v`-Pfade gibt.
  2. `G` ist genau dann `k`-kanten-zusammenhängend, wenn es für alle Paare von Knoten `u != v` mindestens `k` kantendisjunkte `u`-`v`-Pfade gibt.
* Bäume
* Pfade
* Zusammenhang
* Kreise
* Matchings
  * **Inklusionsmaximal**: Es gibt keine weitere Kante, die hinzugefügt werden kann, ohne das Matching zu zerstören.
  * **Kardinalitätsmaximal**: Es gibt kein Matching mit einer grösseren Kardinalität.
* Färbungen
  * **Chromatische Zahl `x(G)`**: Minimale Anzahl Farben, die für eine Knotenfärbung von `G` benötigt wird.
* **Satz von Hall, Heiratssatz**: Für einen bipartiten Graphen `G = A + B` gibt es genau ein Matching `M` der Kardinalität `|M| = |A|`, wenn gilt `N(X) >= |X|` für alle Teilmengen `X` in `A`.
* **Diracs Theorem**: Wenn `G = (V, E)` ein Graph mit `|V| >= 3` Knoten ist, dn dem jeder Knoten mindestens `|V|/2` Nachbarn hat, dann ist `G` hamiltonisch.
* **Satz von Brooks**: Ist `G = (V, E)` ein zusammenhängender Graph, der weder vollständig noch ein ungerader Kreis ist, so gilt die chromatische Zahl `x(G)` ist kleiner oder gleich der maximalen Kantenzahl eines Knotens `∆(G)`.

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
* Brücken berechnen

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

**`hamiltoninanCycle(G = ([n], E))`**, Laufzeit `O(|V|^2 * 2^|V|)`
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
  * Laufzeit `O(|V|^3)`
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

## Wahrscheinlichkeitstheorie

* Prinzip von Laplace: Alle Elemetarereignisse sind gleich wahrscheinlich.
* Siebformel
* Bedingte Wahrscheinlichkeit
* **Satz der totalen Wahrscheinlichkeit**: Seien `A_1, ..., A_n` paarweise disjunkt und es gelte `B = ⊆ A_1 ∪ ... ∪ A_n`, dann folgt `Pr[B] = ∑ Pr[B|A_i] * Pr[A_i]`.
* **Satz von Bayes**: Seien `A_1, ..., A_n` paarweise disjunkt und es gelte `B = ⊆ A_1 ∪ ... ∪ A_n` mit `Pr[B] > 0`, dann gilt für ein beliebiges `i = 1, ..., n`, dass `Pr[A_i|B] = Pr[A_i ∩ B] / Pr[B] = (Pr[B|A_i] * Pr[A_i]) / (∑ (Pr[B|A_j] * Pr[A_j]))`.
* **Unabhängigkeit**: Die Ereignisse `A` und `B` heissen unabhängig, falls `Pr[A ∩ B] = Pr[A]* Pr[B]`.
* Monte-Carlo Algorithmen
* Las-Vegas Algorithmen

### Verteilungen

|Verteilung|Beschreibung|Notation|Erwartungswert|Varianz|
|---|---|---|---|---|
|Bernoulli-Verteilung|Ein Ereignis tritt mit Wahrscheinlichkeit `p` ein|`X ~ Bernoulli(p)`|`E[X] = p`|`Var[X] = p(1 -p)`|
|Binomialverteilung|Ein Ereignis tritt mit Wahrscheinlichkeit `p` ein, wir zählen wie oft dieses Ereignis eintritt bei `n` Versuchen|`X ~ Bin(n, p)`|`E[X] = np`|`Var[X] = np(1 - p)`|
|Poisson-Verteilung|Die Poisson-Verteilung resultiert aus dem Grenzwert der Binomialverteilung, wenn `n` gegen unendlich konvergiert und `p` sehr klein ist|`X ~ Po(p)`|`E[X] = p`|`Var[X] = p`|
|Geometrische Verteilung|Wie oft muss ein Experiment mit Erfolgswahrscheinlichkeit `p` wiederholt werden, bis es Erfolgreich ist|`X ~ Geo(p)`|`E[X] = 1 / p`|`Var[X] = (1 - p) / p^2`|

### Abschätzen von Wahrscheinlichkeiten

### Probleme

* **Coupon-Collector Problem**: Sei `X` die Anzahl der Käufe bis zur Komplettierung der Sammlung. Wir Teilen die Zeit in Phasen ein, wobei Phase `i` die die Schritte bis zum erwerb vom `i`-ten Bildes, beginnend bei `i = 0`, bezeichne. Sei `X_i` die Anzahl der Käufe in Phase `i`. `X_i` ist geometrisch verteilt mit `X_i ~ Geo((n - i) / n)` und es gilt somit `E[X_i] = n / (n - i)`. Wegen der Linearität des Erwartungswerts folgt somit, dass `E[X] = sum(E[X_i], 0, n) = sum((n / (n - i)), 0, n) = n * sum(1 / i, 1, n + 1) = n * ln(n) + O(n)`, da `sum(1 / i, 1, n + 1)` die `n`-te harmonische Zahl bezeichnet, die `ln(n) + O(1)` ist.
* **Geburtstagsproblem**: Wie gross ist die Wahrscheinlichkeit, dass in einer `m`-köpfigen Gruppe zwei Personen am selben Tag Geburtstag haben? Man werfe `m` Bälle zufällig und gleich wahrscheinlich in `n` Körbe. Wie gross ist die Wahrscheinlichkeit,dass nach dem Experiment jeder Ball alleine in seinem Korb liegt? Für `m > n` folgt aus dem Schubfachprinzip, dass es immer einen Korb mit mehr als einem Ball gibt. Für `m <= n` gilt. `A_i` bezeichne das Ereignis "Ball i landet in einem leeren Korb". Das gesuchte Ereignis "Alle Bälle liegen allein in einem Korb" bezeichen wir als `A`. Wir berechnen `Pr[A] = Pr[∩ A_i] = A[A_i] * Pr[A_2|A_1] * Pr[A_3|A_2 ∩ A_1] * ... * Pr[A_m|∩ A_i]`. Die Wahrscheinlichkeit, dass der `j`-te Ball in einem leeren Korb landet, wenn bereits die vorherigen `j - 1` Bälle jeweils allein in einem Korb gelandet sind, somit muss der `j`-te Ball in einem der `n - (j - 1)` leeren Körbe landen. Somit gilt `Pr[A_j|∩ A_i] = (n - (j - 1)) / n = 1 - (j - 1) / n`. Durch die Abschätzung `1 - x <= exp(-x)` und da `Pr[A_1] = 1` erhalten wir `Pr[A] = ∏(1 - (j - 1) / n) <= ∏exp(-(j - 1) / n) = exp(-m(m - 1) / (2n))`.

### Randomisierte Algorithmen

* Reduktion der Fehlerwahrscheinlichkeit
* Sortieren und Selektieren
* Primzahltest
* Target-Shooting
* Finden von Duplikation

* Median

### Geomtrische Algorithmen

* Kleinster umschliessender Kreis
* Konvexe Hülle
