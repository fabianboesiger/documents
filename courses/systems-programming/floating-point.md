---
title: "Floating Point Representation IEEE Standard 754"
---

# Precisions

|Precision|Sign Field $s$|Exponent Field $exp$|Fraction Field $frac$|
|---|---|---|---|
|Half|1 Bit|5 Bits|16 Bits|
|Single|1 Bit|8 Bits|23 Bits|
|Double|1 Bit|11 Bits|52 Bits|

Further, let $e$ be the number of exponent field bits and let $bias$ be $2^{e-1}-1$.



# Numerical Form

$$(-1)^s \cdot M \cdot 2^E$$

# Encoding

|Values|Condition|Exponent Encoding|Significand Encoding|
|---|---|---|---|
|Normalized Values|$exp \neq 000...0$, $exp \neq 111...1$|$exp - bias$|Implied leading one|
|Denormalized Values|$exp = 000...0$|$1 - bias$, *not* $0 - bias$|Implied leading zero|
Special Values|$exp = 111...1$|||

# Conversion

## Usual Numbers

The following examples are for floating point numbers with 4 exponent bits and 3 fraction bits.

### Normalize

Adjust binary numbers so that numbers to have a leading one.

|Value|Binary|Fraction|Exponent|
|---|---|---|---|
|128|10000000|1.0000000|7|
|15|00001101|1.1010000|3|

### Rounding

We have binary

|Value|Fraction|GRS|Increment|Rounded|
|---|---|---|---|---|
|128|1.0000000|000

## Special Values

|Value|Sign|Exponent Field|Fraction Field|
|---|---|---|---|
|Infinity|Depends|$exp = 111...1$|$frac = 000...0$|
|Not-a-Number|Doesn't matter|$exp = 111...1$|$frac \neq 000...0$

# Computing
