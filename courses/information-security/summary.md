---
title: Information Security
author: Fabian Bösiger
geometry: margin=2cm
toc: yes
---

## Perfect Secrecy

An encryption scheme is perfectly secret if for some random variables $M$, $C$
and every $m$, $c$: $P(M = m) = P(M = m \mid C = c)$.

Equivalently: $M$ and $C$ are independet.

Equivalently: The distribution of $C$ does not depend on $M$.

Equivalently: For every $m_0$, $m_1$ we have that $\Enc(k, m_0)$ and
$\Enc(K, m_1)$ have the same distribution.

In every perfectly secret encryption scheme, we have $|\mathcal{K}| \geq |\mathcal{M}|$.

## Historical Ciphers

### Shift Cipher

### Substitution Cipher

### Vigenere Cipher

## Block Ciphers

### ECB

### CTR

### OFB

### CBC

### CFB

## Stream Ciphers