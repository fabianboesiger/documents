# Design of Digital Circuits

## Introduction

* **Moores Law**: Number of transistors double every tow years
* **Power of Abstraction**: A higher level only needs to know about the interface to the lower level, not how the lower level is implemented
* **Hamming Distance**: Number of locations in which the corresponding symbols of two equal-length strings are different

|Abstraction Levels|Description|
|---|---|
|Problem|
|Algorithm|Step-by-Step procedure that is guaranteed to terminate, each step is precisely stated and can be carried out by a computer|
|Program/Language|
|Runtime System Software|
|Software/Hardware Interface (ISA)|
|Microarchitecture|Implementation of the ISA
|Logic|Building blocks of micro-architecture
|Devices|
|Electrons|

## Four Mysteries

### Meltdown and Spectre

* Someone can steal secret data from the system even though
  * Your program and data are perfectly correct
  * Your hardware behaves according to the specification and
  * There are no software vulnerabilities/bugs
* Modern processors speculatively execute code to improve performance for branches
* **Speculative execution leaves traces of secret data in the processor’s cache**
* A malicious program can inspect the contents of the cache to infer secret data
* A malicious program can inspect the contents of the cache to infer secret data

### Rowhammer

* **Repeatedly reading a memory row enough times before the memory gets refreshed induces disturbance errors in adjacent rows in most real DRAM chips you can buy today**
* DRAM cells are too close to each other, they are not electrically isolated from each other
* When we activate (apply high voltage) to a row, an adjacent row gets slightly activated as well
* Vulnerable cells in that slightly-activated row lose a little bit of charge
* If this row hammer happens enough times, charge in such cells gets drained
* One exploit uses rowhammer-induced bit flips to gain kernel privileges

### Memory Performance Attacks

* **DRAM controllers are vulnerable to denial of service attacks, we can write programs to exploit unfairness**

### DRAM Refresh

* DRAM capacitor charge leaks over time
* **The memory controller needs to refresh each row periodically to restore charge**
* Downsides of refresh
  * Energy consumption: Each refresh consumes energy
  * Performance degradation: DRAM rank/bank unavailable while refreshed
  * QoS/predictability impact: (Long) pause times during refresh
  * Refresh rate limits DRAM capacity scaling

#### Eliminating Unnecessary DRAM Refreshes

* Observation: The overwhelming majority of DRAM rows can be refreshed much less often without losing data
* Idea: Refresh weak rows more frequently, all other rows less frequently

#### Example

A supercomuter has a DRAM-based memory system with the following configuration:

* Total capacity: 1 EB
* DRAM row size: 8 KB
* In order to ensure that no data is lost, every DRAM row is refreshed once every 64 ms

1. How many DRAM rows does the memory system have?
   * `1 EB = 2^60, 8 KB = 2^13, 2^60 / 2^13 = 2^47 rows`
2. How many DRAM refreshes happen in 64ms?
   * Because each row needs to be refreshed every 64ms, all rows need to be refreshed within 64ms.Thus, the number of refreshes in 64 ms is equal to the number of rows 2^47
3. What is the power consumption of DRAM refresh?
   * Let `P_refresh` be the power consumption of the DRAM device while performing only refresh operations. To find that number, we can use `P_refresh = IDD5B ∗ V_DD`, where `IDD5B` is the average current consumption while the DRAM device is being continuously refreshed. Both `IDD5B` and `V_DD` can be found in the [datasheet](https://safari.ethz.ch/digitaltechnik/spring2019/lib/exe/fetch.php?media=1gb_ddr3_sdram.pdf). There are many different `IDD5B` parameters to pick from in the datasheet depending on the types of DRAM we are assuming. If we assume DDR3-1066 with `IDD5B = 160mA` (as can be seen in Table 19 in the datasheet), `P_refresh = 160 mA ∗ 1.5 V = 240 mW`.
4. What is the total energy consumption of DRAM refresh during a refresh cycle? And during a day?
   * `Energy = Power * Time`. `Power` is P_refresh from part 3. It `Time` is 64 ms (a refresh cycle), `Energy` is `E = 240 mW * 64 ms = 1.54 * 10^-2 J`. If `Time` is a day, `Energy` is `E = 2.07 * 10^4 J`.

A machine has a 4 GB DRAM main memory system. Each row is refreshed every 64 ms.

1. The machine’s designer runs two applications A and B (each run alone) on the machine. Although applications A and B have a similar number of memory requests, application A spends a surprisingly larger fraction of cycles stalling for memory than application B does? What might be the reasons for this?
   * A large number of application A’s memory requests are row-buffer conflicts, whereas a large number of application B’s memory requests are row-buffer hits. Hence, application A’s requests take longer to service and it spends more time stalling for memory.
2. Application A also consumes a much larger amount of memory energy than application B does. What might be the reasons for this?
   * A row-buffer conflict consumes more energy than a row-buffer hit. A row-buffer conflict requires a precharge, an activate and a read/write, whereas a row-buffer hit only requires a read/write. Hence, application A consumes more memory energy.
3. When applications A and B are run together on the machine, application A’s performance degrades significantly, while application B’s performance does not degrade as much. Why might this happen?
   * When the applications are run together, they interfere with each other. Hence, both applications’ performance degrades when they run together. However, if a memory scheduler that favors row-buffer hits over row-buffer conflicts (like FR-FCFS) is used, it would favor application B’s requests over application A’s requests. Therefore, application A’s performance degrades more.
4. The designer decides to use a smarter policy to refresh the memory. A row is refreshed only if it has not been accessed in the past 64 ms. Do you think this is a good idea? Why or why not?
   * This can reduce refresh energy significantly if a large fraction of the rows in memory contain data and are accessed (within the 64 ms refresh window), as these rows do not have to be refreshed explicitly. However, if only a small number of rows contain data and only these rows are accessed, this policy will not provide much reduction in refresh energy as a large fraction of rows are still refreshed at the 64 ms rate.
5. When this new refresh policy is applied, the refresh energy consumption drops significantly during a run of application B. In contrast, during a run of application A, the refresh energy consumption reduces only slightly. Is this possible? Why or why not?
   * This is possible. If application B has a large working set, it could access a large fraction of the memory rows (within the 64 ms refresh window) and hence these rows do not have to be refreshed explicitly. On the other hand, application A could have a much smaller working set and hence a large fraction of rows still have to be refreshed at the 64 ms rate.



## What is a Computer

* A computer has three main components
  * Computation
  * Communication
  * Memory

### FPGAs

* Field Programmable Gate Array
* Reconfigurable, flexible hardware
* Two main building blocks
  * **Look-Up Tables (LUT)**: Can implement any 3 bit input function
  * **Switches**: Connect the LUTs
* Programmable with Verilog or VHDL

### Microprocessors

* Common building block of comupters
* Programmable with C, C++, Java, ...

### MOS Transistors

*Abstraction Layer: Devices*

* **M**etal **O**xide **S**emiconductors are combined to build a transistor
* By combining many transistors we can build simple logic gates
* N-Type Transistor: If the gate is supplied with a high voltage, the connection from source to drain acts like a piece of wire, otherwise the connection is broken
* P-Type Transistor: This transistor works analogous to the N-Type Transistor, except the gate is open when supplied with a low voltage

### Logic Gates

*Abstraction Layer: Logic*

* Basic logic structures are constructed out of individual MOS transistors
* Logic gates implement simple boolean functions

|Gate|MOS Transistor Construction|
|---|---|
|NOT|![](not-gate.png)|
|NAND|![](nand-gate.png)|
|AND|![](and-gate.png)|

* General form used to construct any inverting logic gate
  * The networks may consist of transistors in series or in parallel
  * When transistors are in parallel, the network is *ON* if one of the transistors is *ON*
  * When transistors are in series, the network is *ON* only if all transistors are *ON*
  * pMOS transistors are used for pull-up, they pass high voltages well
  * nMOS transistors are used for pull-down, they pass low volatges well
  * Exactly one network should be *ON*, and the other network should be *OFF* at any given time
  * Series connections are slower than parallel connections, pseudo nMOS logic can be used as an alternative
* **Dynamic Power Consumption**: `C * V^2 * f`, `C` is the capacitance of the circuit, `V` is the supply voltage, `f` is the charging frequency of the capacitor
* **Static Power Consumption**: `V * I_leakage`

![](logic-gates.png)

* Logic gates can be extended to three or more inputs

### Logic Circuits

* A logic circuit is composed of inputs and outputs
* The **functional specification** describes relationship between inputs and outputs
* The **timing specification** describes the delay between inputs changing and outputs responding

### Boolean Algebra

* not A: `Ā` (In this summary: `¬A`) 
* A and B: `A • B` (In this summary: `A * B`)
* A or B: `A + B`
* Duality: A dual of a Boolean expression is derived by replacing
  * Every AND operation with an OR operation
  * Every OR operation with an AND operation
  * Every constant 1 with a constant 0
  * Every constant 0 with a constant 1
  * Don’t change any of the literals or play with the complements

Operations with 0 and 1
```
X + 0 = X
X + 1 = 1
X * 1 = X
X * 0 = 0
```

Idempotent Law
```
X + X = X
X * X = X
```

Involution Law
```
¬(¬X) = X
```

Laws of Complementarity
```
X + ¬X = 1
X * ¬X = 0
```

Commutative Law
```
X + Y = Y + X
X * Y = Y * X
```

Associative Laws
```
(X + Y) + Z = X + (Y + Z) = X + Y + Z
(X * Y) * Z = X * (Y * Z) = X * Y * Z
```

Distributive Laws
```
X * (Y + Z) = (X * Y) + (X * Z)
X + (Y * Z) = (X + Y) * (X + Z)
```

Simplification Theorems
```
X * Y + X * ¬Y = X
X + X * Y = X
(X + ¬Y) * Y = X * Y
(X + Y) * (X + ¬Y) = X
X * (X + Y) = X
(X * ¬Y) + Y = X + Y
```

De Morgan's Law
```
¬(X + Y + Z + ...) = ¬X * ¬Y * ¬Z * ...
¬(X * Y * Z * ...) = ¬X + ¬Y + ¬Z + ...
```

* **Complement**: Inverted variables (`¬A`)
* **Literal**: Variables or complements (`A`, `¬A`)
* **Implicant**: Product of literals (`¬A * B`)
* **Minterm**: Product that includes all input variables (`A * B * ¬C`)
* **Maxterm**: Sum that includes all input variables (`A + ¬B + ¬C`)

**Sum of Products Form, Disjunctive Normal Form, Minterm Expansion**

Example
```
(¬A * B * C) + (A * ¬B * ¬C) + (A * ¬B * C)
```

In the truth table, we can build a minterm from each row, when a variable is zero, we write the variable inverted. We add all minterms where the result is true.

Example
|A|B|C|Minterms|
|---|---|---|---|
|0|0|0|`¬A * ¬B * ¬C = m1`|
|1|0|0|`A * ¬B * ¬C = m2`|
|...|||

Canonical form
```
F(A, B, C) = ∑m(3, 4, 5, 6, 7) = m3 + m4 + m5 + m6 + m7
```

**Product of Sums Form, Conjunctive Normal Form, Maxterm Expansion**
Example

```
(¬A + B + C) * (A + ¬B + ¬C) * (A + ¬B + C)
```

In the truth table, we can build a maxterm from each row, when a variable is one, we write the variable inverted. We multiply all maxterms where the result is false.

Example
|A|B|C|Maxterms|
|---|---|---|---|
|0|0|0|`A * B * C = M1`|
|1|0|0|`¬A * B * C = M2`|
|...|||

Canonical form
```
F(A, B, C) = ΠM(0, 1, 2) = m1 + m2 + m3
```