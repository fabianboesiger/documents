# Design of Digital Circuits

## Introduction

* **Moores Law**: Number of transistors double every two years
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

#### Exercise

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

## Boolean Algebra

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

The sum-of-products form and the product-of-sums form can be easily implemented with logic gates.

Example: `Y = (¬A * ¬B * ¬C) + (A * ¬B * ¬C) + (A * ¬B * C)`

![](images/logic-to-gates.png)

Minterms and maxterms can be easily translated, for example `∑m(3, 4, 5, 6, 7) = ΠM(0, 1, 2)`, all indices except the ones in the first term are used to build the second term, and the other way around.

Furthermore, if `F(A, B, C) = ∑m(3, 4, 5, 6, 7)`, then `¬F(A, B, C) = ∑m(0, 1, 2)`.

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
* A set of gates is logically complete if we can build a circuit for any truth table we wish without any other kind of gate (Example: AND, OR, NOT)

|Gate|MOS Transistor Construction|
|---|---|
|NOT|![](images/not-gate.png)|
|NAND|![](images/nand-gate.png)|
|AND|![](images/and-gate.png)|

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

![](images/logic-gates.png)

* Logic gates can be extended to three or more inputs

#### Exercise

Design the following circuits using only CMOS transistors (p-type and n-type MOS transistors).

|Gate|MOS Transistor Construction|
|---|---|
|XOR|![](images/xor-gate.png)|
|XNOR|![](images/xnor-gate.png)|

We know that the gates AND, OR and NOT are logically complete. To show that the set of only a NOR gate is logically complete, build the AND, OR and NOT gates using only NOR gates.

![](images/nor-gate-logically-complete.png)

### Logic Circuits

* A logic circuit is composed of inputs and outputs
* The **functional specification** describes relationship between inputs and outputs
* The **timing specification** describes the delay between inputs changing and outputs responding

### Combinational Building Blocks

#### Decoder

It has `n` inputs and `n^2` outputs, where exactly one output is 1 and the rest are 0s.

![](images/decoder.png)

#### Multiplexer

Selects one of the `n` inputs to connect it to the output, needs a `log_2(n)`-bit control input.

![](images/multiplexer.png)

#### Full Adder

Does binary addition, similar to decimal addition.

![](images/full-adder.png)

#### Tri-State Buffer

Enables gating of different signals onto a wire.

![](images/tri-state-buffer.png)

`Z`: Signal that is not driven by any circuit

#### Basic Storage Elements

##### R-S Latch

![](images/r-s-latch.png)

##### Gated D Latch

![](images/gated-d-latch.png)

##### Gated-D-Latch-Based Register

Multiple gated D latches to store more data.

![](images/gated-d-latch-register.png)

##### D Flip-Flop

Modification of gated D latch to work with finite state machines. When the clock is low, the master propagates the signal to the slave, only when the clock is high, the slave latches the signal.

![](images/d-flip-flop.png)

###### Enabled Flip-Flops

The enable input (EN) controls when new data is stored.

![](images/enabled-flip-flop.png)

###### Resettable Flip-Flops

The reset is used to set the output to 0, the **synchronous** resettable flip-flop resets only at the clock edge, the **asynchronous** resettable flip-flop resets immediately when reset is 1.

![](images/resettable-flip-flop.png)

###### Settable Flip-Flop

If set is 1, the output is set to 1.

![](images/settable-flip-flop.png)

##### D-Flip-Flop-Based Register

Multiple D flip-flops to store more data.

![](images/d-flip-flop-register.png)

#### Programmable Logic Array

With a PLA, any logic function can be implemented. The PLA consists of an array of AND gates followed by an array of OR gates, where the AND gates form the products in the sum-of-products form, and the OR gates form the sum.

![](images/programmable-logic-array.png)

#### Exercise

Draw an 8-input multiplexer on the gate level using only AND, OR and NOT gates. Use as few gates as possible.

![](images/8-1-multiplexer-gate-level.png)

Draw an 8-input multiplexer on the module level using only 2-input multiplexers. Use as few multiplexers as possible.

![](images/8-1-multiplexer-module-level.png)

### Memory

* Memory is locations that can be written to or read from
* Every unique location in memory is indexed with a unique **address**. `n` locations require `log_2(n)` address bits.
* **Addressability**: the number of bits of information stored in each location.
* The entire set of unique locations in memory is referred to as the **address space**

![](images/memory.png)

* **Banking**: Reduce the latency of memory by dividing it into multiple banks and thus enabling multiple accesses in parallel
* **DRAM**: Capacitor charge state indicates stored value, needs to be refreshed as it looses charge over time
  * Slower access
  * Lower cost
  * Higher density
  * Requires Refresh
* **SRAM**: Two cross coupled inverters store a single bit
  * Faster access
  * Higher cost
  * Lower density
  * No need hor refresh

#### Virtual vs. Physical Memory

* Programmer sees virtual memory, can assume it is infinite
* Physical memory size is much smaller than what the programmer assumes
* The system maps virtual memory addresses to physical memory
  * Address generated by each instruction in a program is a virtual address
  * An address translation mechanism maps this address to a physical address
* This enables
  * Relocation
  * Protection and Isolation
  * Sharing
* Virtual address space divided into pages
* Physical address space divided into frames
* A virtual page is mapped to a physical frame by the page table, if the page is in physical memory or a location in disk, otherwise
* Page Table contains an entry for each virtual page
* If an accessed virtual page is not in memory, but on disk, the virtual memory system brings the page into a physical frame and adjusts the mapping, this is called demand paging
* Physical memory is a cache for pages stored on disk
* Each page table entry has
  * Valid bit: whether the virtual page is located in physical memory (if not, it must be fetched from the hard disk 
  * Physical page number: where the virtual page is located in physical memory
  * Replacement policy
  * Dirty bits
  * Protection bits
* Multi-level page tableskeep the page table size in check

![](images/address-translation.png)
![](images/address-translation-detail.png)

|Cache|Virtual Memory|
|---|---|
|Block|Page|
|Block Size|Page Size|
|Block Offset|Page Offset|
|Miss|Page Fault|
|Tag|Virtual Page Number|

* Cache the page table entries in the translation lookaside buffer in the processor to speed up address translation
* Virtual memory requires both HW/SW support
  * Memory management unit (MMU) does the hardware stuff
* Page Replacement
  * Clock Page Replacement Algorithm
    * Keep a circular list of physical frames in memory (OS does)
    * Keep a pointer (hand) to the last-examined frame in the list
    * When a page is accessed, set the R bit in the PTE
    * When a frame needs to be replaced, replace the first frame that has the reference (R) bit not set, traversing the circular list starting from the pointer (hand) clockwise
    * During traversal, clear the R bits of examined frames
    * Set the hand pointer to the next frame in the list
* Virtual Cache: Cache gets accessed before address translation
* Physical Cache: Cache gets accessed after address translation
* Issues with a virtually addressed cache
  * Homonym: Same VA can map to two different PAs
  * Synonym: Different VAs can map to the same PA
    * Limit cache size to page size times associativity
    * On a write to a block, search all possible indices that can contain the same physical block, and update/invalidate
    * Restrict page placement in OS

##### Memory Protection

* Each process has its own page table
* Each process can use entire virtual address space without worrying about where other programs are
* A process can only access physical pages mapped in its page table
* Each process has its own virtual address space
* Not every process is allowed to access every page
* Check permission bits on each access and during a page fault
* Rowhammer exploit can be used to flip protect bits

#### Memory Hierarchy

* Have multiple levels of storage and ensure most of the data the processor needs is kept in the faster levels

![](images/memory-hierarchy.png)

* How to determine which data is most used
  * **Temporal Locality**: If you just did something, it is very likely that you will do the same thing again soon
  * **Spatial Locality**: If you did something, it is very likely you will do something similar/related
* Latency can be defined recursively with `T_i = t_i + m_i * T_(i+1)`, where `T_i` is the percieved access time of the `i`-th level, `t_i` is the technology-intrinsic access time of this level and `m_i` is the miss rate of this level
  * Keep `m_i`, ``T_(i+1)` low

#### Caches

* **Capacity (`C`)**: The number of data bytes a cache stores
* **Block size (`b`)**: bytes of data brought into cache at once
* **Number of blocks (`B = C/b`)**: number of blocks in cache: `B= C/b`
* **Degree of associativity (`N`)**: number of blocks in a set
* **Number of sets (`S = B/N`)**: each memory address maps to exactly one cache set
* Cache organized into S sets
* Each memory address maps to exactly one set
* Caches categorized by number of blocks in a set
  * Direct mapped: 1 block per set
  * N-way set associative: N blocks per set
  * Fully associative: all cache blocks are in a single set

Direct Mapped Cache

![](images/direct-mapped-cache.png)

N-Way Set Associative Cache

![](images/n-way-set-associative-cache.png)

* Caching to exploit spatial locality
  * Logically divide memory into equal size blocks
  * Fetch to cache the accessed block in its entirety
* Cache hierarchies are used to make caches larger while keeping the speed
* Each block maps to a location in the cache, determined by the index bits in the address
* Accessing the cache
  * Index into the tag and data stores with index bits in address
  * Check valid bit in tag store
  * Compare tag bits in address with the stored tag in tag store


![](images/cache-access.png)

* Two blocks in memory that map to the same index in the cache cannot be present in the cache at the same time
  * This can lead to 0% hit rate
  * Degree of associativity: How many blocks can map to the same index?
  * In fully associative caches, a block can be placed in any cache location
  * Each block has a priority, the least recently used blocks get evicted first


Fully associative cache

![](images/fully-associative-cache.png)

* A tag store contains
  * Valid bit
  * Tag
  * Replacement policy bits
  * Dirty bit
* When do we write the modified data in a cache to the next level
  * Write-Through: At the time the write happens
  * Write-Back: When the block is evicted
    * Need for a dirty bit
* Data and instruction caches are mostly unified
* Cache misses
  * Compulsory Miss: First reference to an address always results in a miss
  * Capacity Miss: Cache is too small to hold everything needed
  * Conflict Miss: Any miss that is neither a compulsory nor a capacity miss
* Three fundamental goals
  * Reduce miss rate
    * More associativity
    * Better replacement/insertion policies
  * Reduce miss latency or miss cost
  * Reduce hit latency or hit cost
  * Software approaches for higher hit rate
    * Restructing data access patterns (`x[i+1, j]` follows `x[i, j]`, thus iterate through `i` first)
    * Divide the working set so that each piece fits in the cache
* Caches in a multi-core system
  * **Private Cache**: Cache belongs to one core
  * **Shared Cache**: Cache is shared by multiple cores
    * Advantage: When a resource is left idle by one thread, another thread can use it, no need to replicate shared data
    * Disadvantage: When the resource is not idle, another thread cannot use it
* Cache Coherence
  * Snoopy Bus: A processor/cache broadcasts its write/update to a memory location to all other processors, another cache that has the location either updates or invalidates its local copy
  * Directory Based

## Sequential and Combinational Logic Circuits

### Combinational Logic Circuits

* Only depends on current inputs

#### Combinational Timing

* Digital logic is an abstraction, in reality, outputs are delayed from the inputs as the transistors take a finite amount of time to switch
* Delay is caused by
  * The capacitance and resistance in a circuit
  * The speed of light
* The delay also depends on the voltage and temparature
* **Contamination Delay** (`t_cd`): Delay until the output starts changing, the total contamination delay of a circuit is calculated by adding all the contamination delays in the shortest path
* **Propagation Delay** (`t_pd`): Delay until the output finishes changing, the total propagation delay of a circuit is calculated by adding all the propagation delays in the longest path

#### Glitches

* **Glitch**: One input transition causes multiple output transitions
* Glitches are visible in K-Maps

![](images/glitch.png)

* Glitches can be avoided by adding in the consensus term

![](images/avoided-glitch.png)

* We don't necessarily have to fix glitches as the circuit eventually is guaranteed to converge to the right value

### Sequential Logic Circuits

* Depends on current and past inputs
* The state of the logic circuits is a snapshot of all relevant elements at this moment
* A clock dictates when to change state, at the start of the clock cycle, the system state changes

![](images/sequential-system-design.png)

#### Sequential Timing

* Signals must be stable when sampled by a flip-flop, specifically, the input must be stable at least `t_setup` before the clock edge and `t_hold` after the clock edge
* **Setup Time** (`t_setup`): Time before the clock edge that data must be stable
* **Hold Time** (`t_hold`): Time after the clock edge that data must be stable
* **Aperture Time** (`t_a`): Time around the clock edge that data must be stable (`t_a = t_setup + t_hold`)

![](images/sequential-timing.png)

* **Contamination Delay clock-to-q** (`t_ccq`): Earliest time after the clock edge that the output of a flip-flop starts changing
* **Propagation Delay clock-to-q** (`t_pcq`): Latest time after the clock edge that the output of a flip-flop stops changing

![](images/flip-flop-output-timing.png)

* **Sequencing Overhead**: The time wasted with not doing useful work `t_pcq + t_setup`
* **Critical Path**: Path with the longest `t_pd`
  * If too long, the design will run slowly
  * If too short, each cycle will do very little useful work
* **Clock Skew**: The clock does not reach all parts of the chip at the same time
* **Safe Timing** 
  * D2 must be stable for at least `t_setup` before the clock edge: Total time `T_c` of a sequential circuit is `T_c > t_pcq + t_pd + t_setup`
  * D2 must be stable for at least `t_hold` after the clock edge: `t_ccq + t_cd > t_hold`, thus there needs to be a minimum contamination delay `t_cd > t_hold - t_ccq`, this is not dependant on `T_c`
  * The maximum frequency `f_max = 1 / T_c`
  * Requires considering the worst-case clock skew, which increases `t_setup` and `t_hold` by `t_skew`, the worst case skew

![](images/total-sequential-time.png)

### Circuit Verification

* Is the circuit functionally corrrect?
* Does the hardware meet all timing constraints?

#### Functional Verification

* Test the logical correctness of the design
* Tested on a high level for performance gain

```
module testbench();
    reg a, b, c;
    wire y;
    
    sillyfunction dut(.a(a), .b(b), .c(c), .y(y));
    initial begin
        a = 0; b = 0; c = 0; #10; // apply input, wait 10ns
        if(y !== 1) $display("000 failed."); // check result
        c = 1; #10;
        if(y !== 0) $display("001 failed.");
        b = 1; c = 0; #10;
        if(y !== 0) $display("010 failed.");
    end
endmodule
```

#### Timing Verification

* Test the timing specifications of the design
* Can be made with `#x` statements on a high level or on a low level after synthesizing the design to acutal circuits

### Exercise

Is the following code sequential or combinational?

```
module concat (input clk, input  data_in1, input data_in2, output reg  [1:0] data_out);
    always @ (posedge clk, data_in1) if (data_in1)
        data_out = {data_in1, data_in2};
    else if (data_in2)
        data_out = {data_in2, data_in1};
endmodule
```

This code results in a sequential circuit, because `data_in2` is not in the sensitivity list, and thus `data_out` will always be `{data_in1, data_in2}`.

### Finite State Machines

* Finite state machines are dicrete-time models of a stateful system
* A FSM shows the set of all possible states of a system and how the system transitions from one state to another
* A FSM consists of five elements
  * A finite number of states
  * A finite number of external inputs
  * A finite number of external outputs
  * An explicit specification of all state transisitons
  * An explicit specification of what determines each external output value
* A FSM consists of three separate parts
  * Next state logic
  * State register
  * Output logic
* States can be encoded in different ways
  * **Fully Encoded**: Minimizes the amount of flip-flops, but not necessarily the output logic (00, 01, 10, 11)
  * **1-Hot Encoded**: Minimizes the next state logic, but maximizes the amount of flip-flops (0001, 0010, 0100, 1000)
  * **Output Encoded**: Minimizes the output logic as the outputs are directly accessible in the state encoding, only works for moore machines (001, 010, 100, 110 for the swiss traffic light)

* **Moore FSM**: Outputs depend only on the current state

![](images/moore-finite-state-machine.png)
![](images/moore-finite-state-machine-diagram.png)

* **Mealy FSM**: Outputs depend on the current state and the inputs

![](images/mealy-finite-state-machine.png)
![](images/mealy-finite-state-machine-diagram.png)

#### Transition Diagrams, State Transition Tables, Output Tables

![](images/finite-state-machine-transition-diagram.png)

|Current State|Inputs||Next State|
|---|---|---|---|
|S|T_A|T_B|S'|
|S0|0|X|S1|
|S0|1|X|S0|
|S1|X|X|S2|
|S2|X|0|S3|
|S2|X|1|S2|
|S3|X|X|S0|

|State|Encoding|
|---|---|
|S0|00|
|S1|01|
|S2|10|
|S3|11|

```
S'_1 = (¬S_1 * S_0) + (S_1 * ¬S_0 * ¬T_B) + (S_1 * ¬S_0 * T_B) = S_1 XOR S_0
S'_0 = (¬S_1 * ¬S_0 * ¬T_A) + (S_1 * ¬S_0 * ¬T_B)
```

|Current State|Outputs||
|---|---|---|
|S|L_A|L_B|
|S0|green|red|
|S1|yellow|red|
|S2|red|green|
|S3|red|yellow|

|Output|Encoding|
|---|---|
|green|00|
|yellow|01|
|red|10|

```
L_A1 = S_1
L_A0 = ¬S_1 * S_0
L_B1 = ¬S_1
L_B0 = S_1 * S_0
```

From this information we can implement this finite state machine as a logic circuit. Furthermore we can draw the following timing diagram for any given clock, reset, and input signals.

![](images/finite-state-machine-timing-diagram.png)

### Exercise

![](images/finite-state-machine-no-reset.png)

1. Which critical component is missing in the FSM diagram above?
   * The reset indication for the inital state.
2. What kind of FSM is this?
   * Moore FSM

## Karnaugh Maps

* Boolean expressions can be reduced to fewer terms
* K-Maps help to visualize adjacencies in up to six dimensions

![](images/k-map.png)

## Tradeoffs in Circuit Design

* **Area**: Circuit area is proportional to the cost of the device
* **Speed**: Circuit should be as fast as possible
* **Power**: Power consumption should be as small as possible
* **Design Time**: Design time should be as small as possible, designers are expensive

## Verilog

* Verilog is a hardware description language (HDL)
* Can be used to simulate the behavior of circuits and synthesize portions of it
* Modules are the main building block in Verilog
* **Structural HDL Implementation**: Module body contains gate-level description of the circuit
* **Behavioral HDL Implementation**: Module body contains functional description of the circuit (Higher level of abstraction than structural HDL implementation)

![](images/verilog-modules.png)

Modules

```
module test 
(
    input a,
    input b,
    output y 
);

endmodule
```

Multi-Bit Input/Outputs

```
input [31:0] a;
```

Wires

* Wires are used to connect modules within other modules

```
module top (A, SEL, C, Y);
    input A, SEL, C;
    output Y;
    wire n1;

    small i_first(.A(A), .B(SEL), .Y(n1));
    small i_second(.A(n1), .B(C), .Y(Y));

endmodule
```

Bitwise Operators

```
module example (a, b, c, y);
    input a;
    input b;
    input c;
    output y;
    
    assign y1 = a & b;    // AND
    assign y2 = a | b;    // OR
    assign y3 = a ^ b;    // XOR
    assign y4 = ~(a & b); // NAND
    assign y5 = ~(a | b); // NOR

endmodule
```

Reduction Operators

```
module and8 (
    input [7:0] a,
    output y
);

    assign y = &a;

endmodule
```

Conditional Assignment
```
module mux2(
    input [3:0] d0, d1,
    input s,
    output [3:0] y
);

    assign y = s ? d1 : d0;

endmodule
```

Precedence of Operations

* The operators on top have the highest precedence

|Symbol|Operation|
|---|---|
|~|NOT|
|*, /, %|mult, div, mod|
|+, -|add, sub|
|<<, >>|shift|
|<<<, >>>|arithmetic shift|
|<, <=, >, >=|comparison|
|==, !=|equal, not equal|
|&, ~&|AND, NAND|
|^, ~^|XOR, XNOR|
|\|, ~\||OR, NOR|
|?:|ternary operator|

Express Numbers

`N'Bxx`
* `N` Number of bits
* `B` Base, can be `b` (binary), `h` (hexadecimal), `d` (decimal), `o` (octal)
* `xx` Number, can have `X` and `Z` as values, underscores can be used to improve readability

|Verilog|Stored Number|
|---|---|
|`4'b1001`|1001|
|`4'd5`|0101|
|`12'hFA3`|111110100011|
|`12'h0`|000000000000|

Parameterized Modules

```
module mux2 #(parameter width = 8) (
    input [width-1:0] d0, d1,
    input s,
    output [width-1:0] y);
     
    assign y = s ? d1 : d0;

endmodule

// instantiation

mux2 mux2_8 (d0, d1, s, out);
mux #(12) mux2_12 (d0, d1, s, out);
```

Sequential Logic

* `posedge` defines a rising edge, the statement is executed when the stated signal rises
* `negedge` defines a falling edge, the statement is executed when the stated signal falls
* `always @ (*)` can be used to add all right-hand side signals to the sensitivity list
* Assigned variables need to be declared as `reg`
* `<=` describes a non-blocking assignment, all non-blocking assignments are made at the end of the block in parallel
* `=` describes a blocking assignment, they are made immediately and the process blocks progress
* `if ... else` can only be used in `always` blocks
* The `always` block is combinational only if all `reg`s within the block are always assigned to a signal
* `always @ (posedge clk)` and non-blocking assignments `<=` are used to model sequential logic
* `always @ (*)` and blocking assignments `=` are used to model combinational logic
* Assignments to the same signal cannot be made in more than one `always` block

```
module flop(
    input clk, input[3:0] d, output reg[3:0] q);
    
    always@ (posedge clk)
        q <= d;
        
endmodule
```

* **Little Endian Format**: Bits are numbered from right to left, starting at the little end
* **Big Endian Format**: Bits are numbered from left to right, starting at the big end

## Instruction Set Architecture

* **Instruction Set Architecture**: Agreed upon interface between software and hardware
* **Microarchitecture**: Implementation of an ISA, not visible to software
* **Instruction**: Most basic unit of computer processing
  * Operate instructions execute instructions in the ALU
  * Data movement instructions read or write from memory
  * Control flow instructions change the sequence of execution

* The ISA specifies
  * The memory organization
  * The register set
  * The instruction set

|Instruction|High Level|MIPS|LC-3|
|---|---|---|---|
|Addition|`a = b + c;`|`add $s0, $s1, $s2`|`ADD R0, R1, R2`|
|Load Word|`a = A[2];`|`lw $s3, 2($s0)`|`LDR R3, R0, #2`|
|Load Word|`a = A[2];`|`lw $s3, 2($s0)`|`LDR R3, R0, #2`|
|Jump||`j target`|`JMP R2`|
|Substract|`a = b + c - d`|`add $t0, $s0, $s1`<br/>`sub $s3, $t0, $s2`|`ADD R2, R0, R1`<br/>`NOT R4, R3    `<br/>`ADD R5, R4, #1`<br/>`ADD R6, R2, R5`|
|Load Immediate|`a = 0x6d5e4f3c;`|`lui $s0, 0x6d5e`<br/>`ori $s0, 0x4f3c`||

### Programming Constructs

* To simplify programming, we break a task down to three basic constructs
  * Sequential Construct: A task can be broken down into two subtasks, one following the other
  * Conditional Construct: A task consists of doing either one of two subtasks
  * Iterative Construct: A task consists of doing a subtask a number of times, as long as a condition is true

### Instruction Cycle

* Sequence of phases, that an instruction goes through
  * Fetch
    * Load MAR and Increment PC
    * Access Memory
    * Load IR with the conent of MDR
  * Decode
    * Identify the instruction
  * Evaluate Address
    * Calculate the address
  * Fetch Operands
    * Loads MAR and places the results in the MDR
  * Execute
    * Executes the instruction or changes the PC
  * Store Result
    * loads MDR into DR
* Not all instructions have those six phases

### LC-3

![](images/lc-3-computer.png)

* Unique address for each 16-bit data word
* Has 8 general purpose registers
* Only supports 2's complement integers

#### Condition Codes

* Each time a general purpose register is written, three single-bit registers are updatede
  * If the written value is negative, N is set, Z and P are cleared
  * If the written value is zero, Z is set, N and P are cleared
  * If the written value is positive, P is set, Z and N are cleared
* Conditional branch instructions check these registers to determine wether the code branches or not

### MIPS Instruction Set Architecture

* Unique address for each 32-bit data word
* MIPS is byte-addressable
* has 32 registers for different things
* Supports 2's complement integers, unsigned integers, floating point

**R-Type Instruction Format**: 3 Register operands

|Opcode (0)|Source Register (rs)|Source Register (rt)|Destination Register (rd)|Shift Amount (shamt)|Operation in R-Type Instructions (funct)|
|---|---|---|---|---|---|
|6 bits|5 bits|5 bits|5 bits|5 bits|6 bits|

**I-Type Instruction Format**: 2 Register operands and immediate

|Opcode (0)|Source Register (rs)|Source Register (rt)|Immediate (imm)|
|---|---|---|---|
|6 bits|5 bits|5 bits|16 bits|

**I-Type Instruction Format**: Only immediate

|Opcode (0)|Immediate (imm)|
|---|---|
|6 bits|26 bits|

#### Function Calls

* By convention, temporary registers `$t0 - $t9` are saved by the caller if needed, and registers `$s0 - $s7` are callee-saved
* **Caller**
  * Save further needed register to the stack by increasing the stack size `addi $sp, $sp, -8` to the needed amount, saving said registers with `sw $t0, 4($sp)`, `sw $t1, 0($sp)`
  * Passes arguments to argument registers `$a0 - $a3`
  * Jumps to callee using jump and link `jal`
  * When the function returns, load the needed registers from the stack with `lw $t1, 0($sp)`, `lw $t0, 4($sp)` and free up space on the stack `addi $sp, $sp 8`

* **Callee**
  * Performs the procedure
  * Returns the result to the caller by using value registers `$v0 - $v1`
  * Returns to the point of call using jump register to return address `jr $ra`
  * Must not overwrite registers or memory needed by the caller

### Exercise

Rewrite the following program in MIPS assembly.

```
int fib(int n){
    int a = 0;
    int b = 1;
    int c = a + b;
    while (n > 1) {
        c = a + b;
        a = b;
        b = c;
        n--;
    }
    return c;
}
```

```
fib:
    addi $sp, $sp, -16 // allocate stack space
    sw   $16, 0($sp)   // save r16
    add  $16, $4,  $0  // r16 for arg n
    sw   $17, 4($sp)   // save r17
    add  $17, $0,  $0  // r17 for a, init to 0
    sw   $18, 8($sp)   // save r18
    addi $18, $0,  1   // r18 for b, init to 1
    sw   $31, 12($sp)  // save return address
    add  $2,  $17, $18 // c = a + b
branch:
    slti $3,  $16, 2   // use r3 as temp
    bne  $3,  $0,  done
    add  $2,  $17, $18 // c = a + b
    add  $17, $18, $0  // a = b
    add  $18, $2,  $0  // b = c
    addi $16, $16, -1  // n = n - 1j    branchdone:
    lw   $31, 12($sp)  // restore r31
    lw   $18, 8($sp)   // restore r18
    lw   $17, 4($sp)   // restore r17
    lw   $16, 0($sp)   // restore r16
    addi $sp, $sp, 16  // restore stack pointer
    jr   $31           // return to caller
```

## The Von Neumann Model

* Dominant way of how a computer operates
* A computer consists of
  * Memory
  * Processing unit
  * Input
  * Output
  * Control unit
* Instructions are stored in a linear memory array
* The instructions are processed sequentially

![](images/von-neumann-model.png)

### Memory

* The memory stores data and programs
* The memory contains bits that are grouped into bytes and words
* **Addressability**: Determines how the bits are addressed (word-addressabel or byte-addressable)
* **Address Space**: Total number of addresses
* **Reading**
  * The memory address register (MAR) is loaded with the address
  * The data is placed into the memory data register (MDR)
* **Writing**
  * Load the MAR with the address and the MDR with the data
  * Activate the write enable signal

### Processing Unit

* Can consists of many functional units
* Registers ensure fast access to operands

#### Arithmetic and Logic Unit

* Responsible for adding, substracting ...

### Input and Output

* Inputs are keyboards, mouses, scanners, disks ...
* Outputs are monitors, printers, disks ...

### Control Unit

* Conducts the step-by-step process of executing a program
* Keeps track of the instruction being executed in the instruction register (IR)
* Keeps track of the next instruction to execute in the program counter (PC)

* **Control Flow Order** (Used in the Von Neumann model): Instructions are fetched as specified by the instruction pointer, sequential unless explicit control flow instruction
* **Data Flow Order**: Instructions are fetched when its operands are ready, there is no instruction pointer

## Exercise

What does the following dataflow program do?

![](images/dataflow-gcd.png)

It is an implementation of the greatest common divisor.

## Microarchitecture

* Implementation of the ISA under specific design constraints and goals
* Anything done in hardware without exposure to software
* ISA defines an abstract finite state machine
* **Single-Cycle Machine**
  * Each instruction takes a single clock cycle
  * All state updates are made at the end of an instruction's execution
  * Disadvantage: The slowest instruction determines cycle time, long clock cycle time
  * Control signals are generated in the same clock cycle as the one during which data signals are operated on
  * Cycles per instruction `CPI = 1`
* **Multi-Cycle Machines**:
  * Instructions broken into multiple stages
  * The behavior of the entire processor is specified fully by a finite state machine
  * State updates can be made during an instruction’s execution
  * Advantage: The slowest stage determines cycle time, higher clock frequency
  * Disadvantage: Hardware overhead for registers
  * Control signals needed in the next cycle can be generated in the current cycle
  * `CPI` varies for each instruction
* Our design executes `CPI * (1/f)` instructions per second where `CPI` is the cycles per instruction and `f` is the clock cycle time
* An instruction processing engine consists of two components
  * **Datapath**: Consists of hardware elements that deal with and transform data signals
    * Functional units
    * Hardware structures that enable flow of data
    * Storage units
  * **Control Logic**: Consists of hardware elements that determine control signals that specify what the datapath elements should do to the data

* 5 generic steps
  * Instruction Fetch (IF)
  * Instruction decode, register operand fetch (ID/RF)
  * Execute/Evaluate memory address (EX/AG)
  * Memory operand fetch (MEM)
  * Store/writeback result (WB)

![](images/microarchitecture-simple.png)

Now let's build it!

1. Datapath for R-Type instructions

![](images/r-type-datapath.png)

2. Adding I-Type instructions

![](images/r-type-i-type-datapath.png)

3. Adding load/store functionality

![](images/r-type-i-type-load-store-datapath.png)

4. Adding branching

![](images/r-type-i-type-load-store-branch-datapath.png)

5. Putting it all together (JAL, JAR, JALR is omitted)

![](images/microarchitecture-full.png)

6. Another design

![](images/microarchitecture-full-another.png)

7. Making it multi-cycle

![](images/microarchitecture-full-another-multi-cycle.png)

|Control Signal|When De-Asserted|When Asserted|Equation|
|---|---|---|---|
|`RegDest`|Register write select according to `rt` (inst[20:16])|Register write select according to `rd` (inst[15:11])|`opcode == 0`|
|`ALUSrc`|Second ALU input from second Register read port|Second ALU input fom sign-extended 16-bit immediate|`opcode != 0 && opcode != BEQ && opcode != BNE`|
|`MemtoReg`|Steer ALU result to register write port|Steer memory load to register write port|`opcode == LW`|
|`RegWrite`|Register write disabled|Register write enabled|`opcode != SW && opcode != Bxx && opcode != J && opcode != JR`|
|`MemRead`|Memory read disabled|Memory read port return load value|`opcode == LW`|
|`MemWrite`|Memory write disabled|Memory write enabled|`opcode == SW`|
|`PCSrc_1`|According to `PCSrc_2`|Nex PC is based on 26-bit immediate jump target|`opcode == J || opcode = JAL`|
|`PCSrc_2`|`PC = PC + 4`|Next PC is based on 16-bit immediate branch target|`opcode == Bxx && branch condition satisfied`|

## Exercise

Classify the following attributes as either a property of its microarchitecture or ISA.

|Attribute|Microarchitecture|ISA|
|---|---|---|
|The machine does not have a subtract instruction||x|
|The ALU of the machine does not have a subtract unit|x||
|The machine does not have condition codes||x|
|A 5-bit immediate can be specified in an ADD instruction||x|
|It takes n cycles to execute an ADD instruction|x||
|There are 8 general purpose registers||x|
|A 2-to-1 mux feeds one of the inputs to ALU|x||
|The register file has one input port and two output ports|x||

1. If a given program runs on a processor with a higher frequency, does it imply that the processor always executes more instructions per second, compared to a processor with a lower frequency?
   * No, it also depends on the amount of instructions per cycle (IPC).
2. If a processor executes more of a given program’s instructions per second, does it imply that the processor always finishes the program faster, compared to a processor that executes fewer instructions per second?
   * No, the total number of instructions required to execute a program may vary depending on the specific ISA.

Modify the datapath we build to support the JAL instruction.

![](images/jal-modified-datapath.png)

## Approaches to Instruction Level Concurrency

### Data Flow Programs

* Program consists of data flow nodes
* Availability of data determines order of execution
* A node fires when all its inputs are ready
* Data Flow implementations at the microarchitecture level while preserving Von Neumann semantics have been very successful (Out-of-order execution)
* Advantage: Very good at exploiting irregular parallelism, only real dependencies constrain processing
* Disadvantage: No precise state semantics

### Pipelining

![](images/pipelined-processor.png)

* Idea
  * Divide the instruction processing cycle into distinct stages of processing
  * Ensure there are enough hardware resources to process one instruction in each stage
  * Process a different instruction in each stage
* **Latency**: Time taken for an instruction to process
* **Throughput**: Quantity of instructions within a unit of time
* Register renaming eliminates false dependencies

#### Exceptions vs. Interrupts

||Exceptions|Interrupts|
|---|---|---|
|Cause|Internal to the running thread|External to the running thread|
|Handling|When detected, process level|When covenient, system level|
|Cause|Internal to the running thread|External to the running thread|

* **Precise Exceptions**: Ensure that the architectural state is precise

#### Dependencies

* **Resource Contention**: Two stages need the same resource, can be prevented by eliminating the cause of contention (separate instruction and data memories, multiple ports), or stall the pipeline
* **Dependencies**: Resource is not avaible when needed
  * **Data Dependence**
    * **Flow Dependence**: Read after write, they always need to obeyed because they constitute a true dependence
    * **Output Dependence**: Write after write
    * **Anti Dependence**: Write after read
    * Anti and output dependencies are dependencies on a name, not a value, thus they are not true dependencies
  * **Control Dependence**
* **Long-Latency Operations**

![](images/resource-dependence.png)

Flow Dependence
```
r3 <= r1 op r2
r5 <= r3 op r4
```

Anti Dependence
```
r3 <= r1 op r2
r1 <= r4 op r5
```

Output Dependence
```
r3 <= r1 op r2
r4 <= r5 op r6
r3 <= r7 op r8
```

* If a stage takes longer than other stages, the workload of this stage can be divided to hardware blocks
* The goal is to increase throughput with little increase in cost

#### Approaches to Dependence Detection

##### Scoreboarding

* Each register has a valid bit associated with it
* An instruction that is writing to the register resets the valid bit
* An instruction in the decode stage checks if all its source and destination registers are valid
* Advantage: Simple, one bit per register
* Disadvantage: Need to stall for all types of dependencies

##### Combinational Dependence Check Logic

* Advantage: No need to stall on anti and output dependencies
* Disadvantage: Logic is more complex than a scoreboard

#### How to Handle Data dependencies

* Detect and Wait (Stalling)
* Detact and Forward/Bypass
* Detect and Eliminate (Software level)
* Predict and Verify (See branch prediction)
* Do someting else (Switch to another thread)

##### Data Forwarding/Bypassing

* Add additional dependence check logic and data forwarding paths to supply the producer’s value to the consumer right after the value is available
* Forward to execute stage from either the memory stage or the writeback stage (the memory stage has priority) if that stage will write a destination register that matches the source register from a following instruction

##### Stalling

* Insert NOPs on compile-time
* Adding enable input to the fetch and decode pipeline registers

##### Early Branch Resolution

##### Reorder Buffer

* **Reorder Buffer**: Complete instructions out-of-order, reorder them before making results visible to the architectural state
  * Aids software debugging
  * Enables easy recovery from exceptions
  * Enables easy restartable processes
  * Enables software implemented opcodes
  * Advantages: Simple, can eliminate false dependencies
  * Disadvantages: Reorder buffer needs to be accessed to get the results that are yet to be written to the register file

![](images/reorder-buffer.png)

Reorder Buffer Entry

|Valid|Destination Register ID|Destination Register Value|Store Address|Store Data|Program Counter|Valid Bits and Control Bits|Exception|
|---|---|---|---|---|---|---|---|

* How to access the reorder buffer if a later instruction needs a value in it
  * Access the register file fist to check if it is valid
  * If it is not valid, the register file stores the ID of the reorder buffer entry that will contain the value of the register

![](images/reorder-buffer-access.png)

* In-Order Pipeline with reorder buffer
  * Decode: Access regfile/ROB, allocate entry in ROB, check if instruction can execute, if so, dispatch instruction (send it to a functional unit)
  * Execute: Executions can be complete out-of-order
  * Completion: Write result to ROB
  * Commit: Check for exceptions, if none, write result to architectural register file or memory, else, flush pipeline and start from exception handler

##### Tomasulo's Algorithm

* The dataflow graph is limited to the instruction window (All decoded, but not yet commited instructions)

General Out-of-Order Pipeline

![](images/out-of-order.png)

Tomasulo's Machine

![](images/tomasulos-algorithm.png)

Register Rename Table

||Tag|Value|Valid|
|---|---|---|---|
|R0||||
|R1||||
|R2||||
|...||||

Example

![](images/tomasulos-algorithm-cycle-0.png)
![](images/tomasulos-algorithm-cycle-1.png)
![](images/tomasulos-algorithm-cycle-2.png)
![](images/tomasulos-algorithm-cycle-3.png)
![](images/tomasulos-algorithm-cycle-4.png)
![](images/tomasulos-algorithm-cycle-5.png)
![](images/tomasulos-algorithm-cycle-6.png)
![](images/tomasulos-algorithm-cycle-7.png)
![](images/tomasulos-algorithm-cycle-8-1.png)
![](images/tomasulos-algorithm-cycle-8-2.png)
![](images/tomasulos-algorithm-cycle-8-3.png)

##### Exercise

A machine has the following properties:

* 8 functional units where each functional unit has a dedicated separate tag and data broadcast bus
* 32 64-bit architectural registers
* 16 reservation station entries per functional unit
* Each reservation station entry can have two source registers

1. What is the number of tag comparators per reservation station entry?
   * `8 data broadcast buses * 2 source registers = 16`
2. What is the total number of tag comparators in the entire machine?
   * `16 comparators per reservation station entry * 16 reservation station entries per functional unit * 8 functional units + 32 RAT size * 8 functional units = 2304`
3. What is the (minimum possible) size of the tag?
   * `log(16 reservation station entries per functional unit ∗ 8 functional units) = 7 bits`
4. What is the (minimum possible) size of the register alias table (or, frontend register file) in bits?
   * `(64 bits for data + 7 bits for tag + 1 valid bit) * 32 registers = 2304 bits`
5. What is the total (minimum possible) size of the tag storage in the entire machine in bits?
   * `7 bits * (32 RAT size + 2 source registers * 16 reservation station entries per functional unit * 8 functional units) = 2016 bits`

Based on the following image, complete the dataflow graph below for the six instructions that have been fetched. Please appropriately connect the nodes using edges and specify the direction of each edge. Label each edge with the destination architectural register and the corresponding Tag.

![](images/tomasulos-algorithm-snapshot.png)
![](images/tomasulos-algorithm-dataflow.png)

##### Memory Dependence Handling

* Need to obey memory dependencies in an out-of-order machine
* Problem: Memory address is not known until a load/store executes
* Approaches for dependence detecting
  * Wait until all previous stores committed
  * Keep a list of pending stores in a store buffer and check whether load address matches a previous store address
* Approaches for scheduling
  * Assume load dependent on all previous stores
  * Assume load independent of all previous stores
  * Predict the dependence of a load on an outstanding store
* Modern processors use a load queue (LQ) and a store queue (SQ) for this
  * When a store instruction finishes execution, it writes its address and data in its reorder buffer entry or SQ entry
  * When a later load instruction generates its address, it searches the SQ with its address, accesses memory with its address, receives the value from the youngest older instruction that wrote to that address either from ROB or memory

### Exercise

Given the following code 

```
MUL R3, R1, R2
ADD R5, R4, R3
ADD R6, R4, R1
MUL R7, R8, R9
ADD R4, R3, R7
MUL R10 , R5, R6
```

Calculate the number of cycles it takes to execute the given code on the following models, where

* Each instruction is specified with the destination register first
* For all machine models, use the basic instruction cycle as follows
  * Fetch (1 clock cycle)
  * Decode (1 clock cycle)
  * Execute (MUL takes 6, ADD takes 4 clock cycles)
  * Write-Back (1 clock cycle)

1. Non-Pipelined machine

```
MUL: 1 + 1 + 6 + 1 = 9 cycles
ADD: 1 + 1 + 4 + 1 = 7 cycles
3 * 9 + 3 * 7 = 49 cycles
```

2. Pipelined machine with scoreboarding, five adders and five multipliers without data forwarding

![](images/pipelined-machine-scoreboarding-five-adders-five-multipliers-no-data-forwarding.png)

3. Pipelined machine with scoreboarding, five adders and five multipliers with data forwarding

![](images/pipelined-machine-scoreboarding-five-adders-five-multipliers-data-forwarding.png)

4. Pipelined machine with scoreboarding, one adder and one multiplier without data forwarding

![](images/pipelined-machine-scoreboarding-one-adder-one-multiplier-no-data-forwarding.png)

5. Pipelined machine with scoreboarding, one adder and one multiplier with data forwarding

![](images/pipelined-machine-scoreboarding-one-adder-one-multiplier-data-forwarding.png)

### Superscalar Execution

* Idea: Fetch, decode, execute, retire multiple instructions per cycle
* Need to add the hardware resources for doing so
* Hardware performs the dependence checking between concurrently-fetched instructions
* Multiple copies of datapath: Can fetch/decode/execute multiple instructions per cycle
* Advantages: Higher instructions per cycle
* Disadvantages: Higher complexity for dependency checking

### VILW

* Compiler packs independent instructionsin a larger instruction bundles to be fetched and executed concurrently
* No need for hardware dependency checking between concurrently-fetched instructions in the VLIW model
* VLIW simplifies hardware, but requires complex compiler techniques
* Packed instructions can be logically unrelated
* Lock-step (all or none) execution: If any operation in a VLIW instruction stalls, all instructions stall
* Advantages
  * No need for dynamic scheduling hardware
  * No need for dependency checking within a VLIW instruction
* Disadvantages
  * Compiler needs to find N independent operations per cycle
  * Recompilation required when execution width (N), instruction latencies or functional units change
  * Lockstep execution causes independent operations to stall

### SIMD Processing

* Single instruction operates on multiple data elements
* Concurrency arises from performing the same operation on different pieces of data
* Vector/SIMD machines are good at exploiting regular data-level parallelism
* Performance improvement limited by vectorizabilityof code

![](images/array-processor-vs-vector-processor.png)

#### Array Processor

* Instruction operates on multiple data elements at the same time using different spaces

#### Vector Processor

* Instruction operates on multiple data elements in consecutive time steps using the same space
* No dependencies within a vector
* Highly regular memory access pattern
* Very inefficient if parallelism is irregular
* A loop is vectorizableif each iteration is independent of any other

##### Vector Registers

* Each vector data register holds N M-bit values
* Vector Mask Register VMASK Indicates which elements of vector to operate on

##### Vector Functional Units

* Use a deep pipeline to execute element operations with a fast clock cycle

##### Loading/Storing Vectors from/to Memory

* Elements can be loaded in consecutive cycles if we can start the load of one element per cycle
* If the memory takes more than one cycle to access
  * Memory is divided into banksthat can be accessed independently, banks share address and data buses
  * Can sustain N parallel accesses if all N go to different banks

## Branch Prediction

|Type|Direction|Number of next possible fetch addresses|When is the address resolved|
|---|---|---|---|
|Conditional|Unknown|2|Execution|
|Unconditional|Always taken|1|Decode|
|Call|Always taken|1|Decode|
|Return|Always taken|Many|Execution|
|Indirect|Always taken|Many|Execution|

* Potential solutions
  * Stall the pipeline
  * Guess the next fetch address (Branch prediction)
  * Employ delayed branching (branch delay slot)
  * Do something else (fine-grained multithreading)
  * Eliminate control-flow (predicated execution)instructions
  * Fetch from both possible paths (multipath execution)

### Static Branch Prediction

#### Always Not-Taken

* Simple to implement: no need for BTB, no direction prediction
* Low accuracy
* Compiler can layout code such that the likely path is the not-taken path
* 30-40% accuracy

#### Always Taken

* No direction prediction
* Better accuracy
* 60-70% accuracy

#### Backward Taken, Forward Not Taken

* Predict backward (loop) branches as taken, others not-taken

#### Profile-Based

* Compiler determines likely direction for each branch using a profile run
* Encodes that direction as a hint bit in the branch instruction format

#### Program-Based

* Use heuristics based on program analysis to determine statically-predicted direction
* Does not require profiling
* Heuristics might be not representative or good

#### Programmer-Based

* Programmer provides the statically-predicted direction via pragmas
* Does not require profiling or program analysis
* Programmer may know some branches and their program better than other analysis techniques
* Requires programming language, compiler, ISA support

### Dynamic Branch Prediction

#### Last Time Prediction

* A single or more bits store wether the branch was taken the last time
* Always mispredicts the last iteration and the first iteration of a loop branch
* 85-90% accuracy

#### Two Level Global Branch Prediction

* Associate branch outcomes with global history of all branches
* Make a prediction based on the outcome of the branch the last time the same global branch history was encountered
* First level: Global branch history register stores the direction of the last N branches
* Second level: Table of saturating counters for each history entry that indicates the direction the branch took the last time the same history was seen
* Add more context information to the global predictor to take into account which branch is being predicted, by hashing the GHR with the Branch PC, for example

#### Two Level Local Branch Prediction

* Have a per-branch history register
* Make a prediction based on the outcome of the branch the last time the same local branch history was encountered
* First level: A set of local history registers, select the history register based on the PC of the branch
* Second level: Table of saturating counters for each history entry that indicates the direction the branch took the last time the same history was seen

#### Hybrid Branch Predictors

* There is heterogeneity in predictability behavior of branches, exploit that heterogeneity by designing heterogeneous (hybrid) branch predictors
* Better accuracy: different predictors are better for different branches
* Reduced warmup time
* Needs a selector to decide which predictor to use
* 90-97% accuracy

#### Other Branch Predictors

* Loop branch detector and predictor
  * Loop iteration count detector/predictor
* Perceptron branch predictor
  * Learns the direction correlations between individual branches
  * Assigns weights to correlations
* Hybrid history length based predictor
  * Uses different tables with different history lengths

### Delayed Branching

* Change the semantics of a branch instruction
  * Branch after N instructions
  * Branch after N cycles
* Keeps the pipeline full with useful instructions in a simple way
* Disadvantage: How to find instructions to fill the delay slots

### Systolic Arrays

* Data flows from the computer memory in a rhythmic fashion, passing through many processing elements before it returns to memory
* Advantage: Efficiently makes use of limited memory bandwidth, improved efficiency, simple design
* Disadvantage: Not generally applicable

![](images/systolic-arrays.png)

### Decoupled Access/Execute 

* Decouple operand access and execution via two separate instruction streams that communicate via ISA-visible queues
* Execute stream can run ahead of the access stream and vice versa
* If A is waiting for memory, E can perform useful work

### Fine-Grained Multithreading

* Hardware has multiple thread contexts (PC+registers), Each cycle, fetch engine fetches from a different thread
* Switch to another thread every cycle such that no two instructions from a thread are in the pipeline concurrently
* Advantages
  * No logic needed for handling control and data dependences within a thread
  * No need for dependency checking between instructions
  * No need for branch prediction logic
* Disadvantages
  * Single thread performance suffers
  * Extra logic for keeping thread contexts

## GPUs

* The instruction pipeline operates like a SIMD pipeline
* However, the programming is done using threads, not SIMD instructions
* **Programming Model**: How the programmer expresses the code
* **Execution Model**: How the hardware executes the code underneath
* **SPMD**: Single Program Multiple Data
* **SIMT**: Single Instruction Multiple Threads
* A GPU is a SIMD (SIMT) machine, except it is programmed using threads in the SPMD programming model
* Each thread executes the same code but operates a different piece of data
* **Warp**: A set of threads that executethe same instruction
* A set of threads executing the same instruction are dynamically grouped into a warp by the hardware
* Two Major SIMT Advantages
  * Threads can be treated separately (MIMD processing)
  * Threads can be grouped into warps flexibly
* Latency can be hidden via warp-level fine-grained multithreading
* Same instruction in different threads uses the thread id to index and access different data elements
* Warps are not exposed to GPU programmers
* Blocks consist of multiple warps
* SIMT does not have to be lock step, contrary to SIMD, each thread can be treated individually
* Each thread can have conditional control flow instructions (Branch divergence)
  * We can find individual threads that are at the same PC
  * We can group them together into a single warp dynamically
  * This reduces branch divergence and improves SIMD utilization

![](images/warp-merging.png)