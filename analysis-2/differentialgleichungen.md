# Berechung von Differentialgleichungen

## Vorkenntnisse

Im Folgenden wird angenommen, dass der Leser mit einigen Konzepten vertraut ist. Diese beinhalten:

* Differentialrechnung
* Integralrechnung

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

## Differentialgleichungen identifizieren

Zunächst sollten wir die Differentialgleichung klassifizieren, indem wir sie auf Linearität und Ordnung untersuchen. 

Der erste Schritt besteht darin, zu erkennen ob die Differentialgleichung eine *gewöhnliche Differentialgleichung (Ordinary Differential Equation, ODE)* ist. Diese hat die allgemeine Form.

$$f(x, y, y', ..., y^{(n)}) = 0$$

Falls es nicht möglich ist, die Differentialgleichung in diese Form zu bringen, ist es eine *partielle Differentialgleichung*.

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

Betrachten wir die entsprechende homogene Gleichung und versuchen wir, diese zu lösen:

$$y' + a(x)y = 0$$
$$y' = -a(x)y$$
$$\frac{y'}{y} = -a(x)$$
$$log(|y|)' = -a(x)$$
$$log(|y|) = -\int a(x)dx + c$$
$$log(|y|) = -A(x)$$
$$y = ze^{-A(x)}$$

Wobei $z$ und $z$ konstant sind. Somit ist $y = ze^{-A(x)}$ unsere allgemeine Lösung für homogene lineare Differentialgleichungen erster Ordnung. Diese Lösung nennen wir die *homogene Lösung*.

Um inhomogene Differentialgleichungen erster Ordnung zu Lösen, verwenden wir eine Methode namens *Variation der Konstanten*. Dazu setzen wir die homogene Lösung in die inhomogene Differentialgleichung ein, ersetzen aber die Konstante $z$ mit einer Funktion $z(x)$, wir setzen also $y = z(x)e^{-A(x)}$ ein:

$$y' + a(x)y = b(x)$$
$$(z(x)e^{-A(x)})' + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} + z(x)(-a(x))e^{-A(x)} + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} - a(x)z(x)e^{-A(x)} + a(x)z(x)e^{-A(x)} = b(x)$$
$$z(x)'e^{-A(x)} = b(x)$$
$$z(x)' = b(x)e^{A(x)}$$
$$z(x) = \int b(x)e^{A(x)}dx$$

Wenn wir jetzt diese Lösung in $y = z(x)e^{-A(x)}$ einsetzen, erhalten wir unsere allgemeine Lösung für lineare Differentialgleichungen erster Ordnung:

$$y = (\int b(x)e^{A(x)}dx)e^{(-A(x))}$$

Ein Trick, den man anwenden kann für Differentialgleichungen mit der Form:

$$y' + a(x)y = b_1(x) + b_2(x)$$

Wenn wir die Lösungen $y_1$ für $y' + ay = b_1$ und $y_2$ für $y' + ay = b_2$ kennen, dann ist die Lösung der obigen Gleichung $y = y_1 + y_2$.

## Lineare Differentialgleichungen mit konstanten Koeffizienten

Lineare Differentialgleichungen mit konstanten Koeffizienten haben die Form:

$$y^{(k)} + a_{(k-1)}y^{(k-1)} + ... + a_1y' + a_0y = b(x)$$

Zu beachten ist, dass $a_i, i \in \{0, ..., (k-1)\}$ keine Funktionen, sondern Konstanten sind, $b(x)$ aber immer noch eine Funktion sein kann.

Zunächst lösen wir wieder die entsprechende homogene Gleichung:

$$y^{(k)} + a_{(k-1)}y^{(k-1)} + ... + a_1y' + a_0y = 0$$