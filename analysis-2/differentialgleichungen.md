# Berechung von Differentialgleichungen

## Vorwort

Dieses kurze Skript soll anschaulich und schrittweise erläutern, wie lineare Differentialgleichungen erster Ordnung sowie lineare Differentialgleichungen beliebiger Ordnung mit konstanten Koeffizienten gelöst werden.

## Vorkenntnisse

Im Folgenden wird angenommen, dass der Leser mit einigen Konzepten vertraut ist. Diese beinhalten:

* Differentialrechnung
* Integralrechnung
* Komplexe Zahlen

## Differentialgleichungen identifizieren

Differentialgleichungen sind Gleichungen, für die keine Zahl, sondern eine Funktion gesucht wird.

Es werden folgende Notationen verwendet:

$$f(x) = y$$
$$f(x)' = y' = y^{(1)}$$
$$f(x)'' = y'' = y^{(2)}$$
$$...$$

Ein Beispiel einer Differentialgleichung:

$$y' = y$$

Es muss also gelten, das die erste Ableitung unserer gesuchten Funktion der Funktion selbst gleicht. Wer sich bereits mit Ableitungen beschäftigt hat, weiss dass $f(x) = e^x$ diese Voraussetzung erfüllt und somit unsere Lösung für die obige Gleichung ist.

## Differentialgleichungen klassifizieren

Zunächst sollten wir die Differentialgleichung klassifizieren, indem wir sie auf Linearität und Ordnung untersuchen. 

Der erste Schritt besteht darin, zu erkennen ob die Differentialgleichung eine *gewöhnliche Differentialgleichung (Ordinary Differential Equation, ODE)* ist. Diese hat die allgemeine Form.

$$f(x, y, y', ..., y^{(n)}) = 0$$

Falls es nicht möglich ist, die Differentialgleichung in diese Form zu bringen, ist es eine *partielle Differentialgleichung*. Partielle Differentialgleichungen werden hier nicht betrachtet.

Die *Ordnung* der Differentialgleichung wird bestimmt durch die höchste vorkommende Ableitung der zu findenden Funktion. In der obigen allgemeinen Form ist die Ordnung somit $n$.

Als Nächstes untersuchen wir die Differentialgleichung auf Linearität. Dazu bringen wir sie in folgende Form:

$$y^{(k)} + a_{(k-1)}(x)y^{(k-1)} + ... + a_1(x)y' + a_0(x)y = b(x)$$

Wobei $b, a_0, ..., a_{(k-1)}$ differenzierbar sind.

Falls es möglich ist, die Differentialgleichung in diese Form zu bringen, nennen wir sie eine *lineare Differentialgleichung*.

Wenn für die lineare Differentialgleichung gilt, dass $b(x) = 0$, ist die Differentialgleichung *homogen*, ansonsten isNein, da $-e^{x^2}$ in der Gleichung vorkommt.t sie *inhomogen*.

Einige Beispiele:

|Gleichung|Gewöhnlich|Linear|Linear homogen|
|---|---|---|---|
|$f'(x) = f(x+1)$|Nein, da $f$ und $f'$ an verschieden Punkten ausgewertet werden.|Nein, da nicht gewöhnlich.|Nein, da nicht liLösennear.|
|$y^2 = y'y''$|Ja|Nein, da $y^2$ in der Gleichung vorkommt.|Nein, da nicht linear.|
|$y'' + cos(y)y' + y = x^2$|Ja|Nein, da $cos(y)$ in der Gleichung vorkommt.|Nein, da nicht linear.|
|$y'' + 2y = -e^{x^2}$|Ja|Ja|Nein, da $-e^{x^2}$ in der Gleichung vorkommt.|
|$y^{(3)} + 6y' + y = 0$|Ja|Ja|Ja|

## Differentialgleichungen berechnen

### Lineare Differentialgleichungen erster Ordnung

Sämtliche lineare Differentialgleichungen erster Ordung können in die folgende Form gebracht werden:

$$y' + a(x)y = b(x)$$

#### Schritt 1: Homogene Differentialgleichung lösen

Betrachten wir die entsprechende homogene Gleichung und versuchen wir, diese zu lösen:

$$y' + a(x)y = 0$$
$$y' = -a(x)y$$
$$\frac{y'}{y} = -a(x)$$
$$log(|y|)' = -a(x)$$
$$log(|y|) = -\int a(x)dx + c$$
$$log(|y|) = -A(x)$$
$$y = ze^{-A(x)}$$

Wobei $z$ und $z$ konstant sind. Somit ist $y = ze^{-A(x)}$ unsere allgemeine Lösung für homogene lineare Differentialgleichungen erster Ordnung. Diese Lösung nennen wir die *homogene Lösung*.

Zu beachten ist, dass die triviale Lösung $y = 0$ immer eine Lösung der homogenen Gleichung ist.

#### Schritt 2: Inhomogene Differentialgleichung lösen

Um inhomogene Differentialgleichungen erster Ordnung zu Lösen, verwenden wir eine Methode namens *Variation der Konstanten*. Dazu setzen wir die homogene Lösung in die inhomogene Differentialgleichung ein, ersetzen aber die Konstante $z$ mit einer Funktion $z(x)$, wir setzen also $y = z(x)e^{-A(x)}$ ein:

$$y' + a(x)y = b(x)$$
$$(z(x)e^{-A(x)})' + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} + z(x)(-a(x))e^{-A(x)} + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} - a(x)z(x)e^{-A(x)} + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} = b(x)$$$$y^{(k)} + a_{(k-1)}(x)y^{(k-1)} + ... + a_1(x)y' + a_0(x)y = b(x)$$
$$z(x)' = b(x)e^{A(x)}$$
$$z(x) = \int b(x)e^{A(x)}dx$$

Wenn wir jetzt diese Lösung in $y = z(x)e^{-A(x)}$ einsetzen, erhalten wir unsere allgemeine Lösung für lineare Differentialgleichungen erster Ordnung:

$$y = (\int b(x)e^{A(x)}dx)e^{(-A(x))}$$

#### Tipps und Tricks

Ein Trick, den man anwenden kann für Differentialgleichungen mit der Form:

$$y' + a(x)y = b_1(x) + b_2(x)$$

Wenn wir die Lösungen $y_1$ für $y' + ay = b_1$ und $y_2$ für $y' + ay = b_2$ kennen, dann ist die Lösung der obigen Gleichung $y = y_1 + y_2$.

Ein anderes hilfreiches Mittel ist, eine Substitution vorzunehmen. Ist beispielsweise eine Differentialgleichung der Form $y'' + a(x)y' = b(x)$ gegeben, kann $y' = u(x)$ gesetzt werden, um die Differentialgleichung wie oben gegeben zu lösen. Um anschliessend das Resultat für $y$ zu erhalten, gilt $y = \int u(x) dx$.

#### Beispiel

## Lineare Differentialgleichungen mit konstanten Koeffizienten

Lineare Differentialgleichungen mit konstanten Koeffizienten haben die Form:

$$y^{(k)} + a_{(k-1)}y^{(k-1)} + ... + a_1y' + a_0y = b(x)$$

Zu beachten ist, dass $a_i, i \in \{0, ..., (k-1)\}$ keine Funktionen, sondern Konstanten sind, $b(x)$ aber immer noch eine Funktion sein kann.

##### Schritt 1: Charachteristisches Polynom herleiten

Zunächst lösen wir wieder die entsprechende homogene Gleichung:

$$y^{(k)} + a_{(k-1)}y^{(k-1)} + ... + a_1y' + a_0y = 0$$

Wir nehmen an, dass die Lösung die Form $y = e^{bx}$ hat. Somit hat die $k$-te Ableitung die Form $y^{(k)} = b^ke^{bx}$ hat. Wenn wir diese Lösungsform in die allgemeine homogene Gleichung einsetzen, erhalten wir:

$$y^{(k)} + a_{(k-1)}y^{(k-1)} + ... + a_1y' + a_0y = 0$$
$$b^ke^{bx} + a_{(k-1)}b^{(k-1)}e^{bx} + ... + a_1be^{bx} + a_0e^{bx} = 0$$
$$(b^k + a_{(k-1)}b^{(k-1)} + ... + a_1b + a_0)e^{bx} = 0$$

Wie vorher ist die triviale Lösung $y = 0$ immer eine Lösung der homogenen Gleichung.

Wir können die obige Gleichung weiter vereinfachen:

$$b^k + a_{(k-1)}b^{(k-1)} + ... + a_1b + a_0 = 0$$

Diese Gleichung nennen wir die *charakteristische Gleichung* der gewöhnlichen Differentialgleichung. Einige Beispiele:

|Differentialgleichung|Charakteristische Gleichung|
|---|---|
|$y'' + 2y' - 3y = 0$|$b^2 + 2b - 3 = 0$|
|$y''' - 2y'' - 4y' + 8y = 0$|$b^3 - 2b^2 - 4b + 8 = 0$|

##### Schritt 2: Charakteristisches Polynom lösen

Nach dem Fundamentalsatz der Algebra hat das entsprechende *charakteristische Polynom* im Bereich der komplexen Zahlen mindestens eine Nullstelle. Um die Lösung der charakteristischen Gleichung zu finden, bringen wir es in die folgende Form:

$$(b - \alpha_1) ... (b - \alpha_k) = 0$$

Zu bemerken ist, falls $\alpha = \beta + i\gamma$ eine Lösung des charakteristischen Polynoms ist, ist $\overline\alpha$ immer auch eine Lösung.

**Fall (a): Keine gemeinsamen Nullstellen**

Falls $\alpha_i \neq \alpha_j$ für alle $i \neq j$ gilt, hat das charakteristische Polynom keine gemeinsamen Nullstellen. Die Lösungen der homogenen Differentialgleichung sind $y_i = e^{\alpha_ix}, 1 \leq i \leq k$. Die allgemeine Lösung der homogenen Differentialgleichung ist folgendermassen:

$$y = z_1y_1 + ... + z_ky_k$$

Wobei $z_i, i \in \{1, ..., k\}$ beliebige komplexe Zahlen sind.

#### Schritt 2: Inhomogene Differentialgleichung lösen

Als nächstens lösen wir die eigentliche inhomogene lineare Differentialgleichung.

#### Beispiele

##### (a)

Gegeben ist die Differentialgleichung $y' - 2y = 0$ und $f(0) = 4$.

1. Bestimme die charakteristische Gleichung $b - 2 = 0$.
2. Bestimme das charakteristische Polynom $(b - \alpha_1) = 0$, wobei $\alpha_1 = 2$.
3. Die homogene Lösung hat die Form $y = z_1 e^{2x}$.
4. Da nach Aufgabenstellung $f(0) = 4$, gilt $f(0) = z_1 e^{2x} = 4$, woraus folgt, dass $z_1 = 4$. Somit ist die gesuchte Lösung $y = 4 e^{2x}$.

##### (b)