---
title: "Theoretische Informatik Serie 9"
author:
- "Benjamin Simmonds"
- "Dario Näpfer"
- "Fabian Bösiger"
geometry: margin=4cm
---

## 24 a)

Wir konstruieren eine eine MTM $A$ wobei gilt $L(A) = L(B)$ und die Berechnungen von $M$ simuliert. Wir können laut Satz $6.6$ annehmen, dass für jedes Wort $w \in L(M)$ es nur eine eindeutige Konfiguration gibt. Somit müssen wir nur erkennen ob $C_{accept(w)}$ von $C_{start}$ erreichbar ist. Wir wissen, dass die kürzeste akzeptierende Berechnung von $M$ auf $w$ höchstens Länge $|w|^2 * c$ besitzt für eine Konstante $c$. (da $Time_M(n)\in O(n^2)$)

Wir werden das Prozedere Reachable vom Beweis von Savitch benutzen um zu erkennen ob $C_{accept(w)}$ von $C_{start}$ in $|w|^2 * c$ Schritten erreichbar ist. Wir erkenne, dass Reachable Platzkonstruierbar ist da die benötigte Zeit $log(c * n^2) = 2log(n) + log(c)$ beträgt. Somit kann unsere MTM $A$ , für jedes Wort, den Wert $|w|^2 * c$ mit $2log(n) + log(c)$ Speicherplatz ausrechnen und speichern.

Laut Aufgabenstellung kann jede innere Konfiguration einer Berechnung in $d * |w|$ Platz gespeichert werden. Das Prozedere Reachable muss höchsten $O(log(|w|))$ Konfigurationen aufs Mal speichern (Divide and Conquer). Somit können wir mit der gleichen Argumentation wie beim Beweis von Savitch den Platzbedarf von $A$ in $O(|w| * log(|w|))$ begründen.

## 24 b)

Für jede Sprache $L$ in $NSPACE(f(n)) \cap NTIME(f(n)^k)$ gilt $L \in NSPACE(f(n))$ und $L \in NTIME(f(n)^k)$. Also gibt es eine nichtdeterministische MTM $M_1$ mit $L(M_1) = L$ und $SpaceM_1(n) \in O(f(n))$ und auch eine nichtdeterministische MTM $M_2$ mit $L(M_2)= L$ und $TimeM_2(n) \in O((f(n))^k)$.

 Es kann also sein, dass es eine MTM gibt, die $L$ mit kleiner Platzkomplexität entscheidet, und eine andere MTM, die $L$ mit geringer Zeitkomplexität entscheidet.