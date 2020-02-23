# Rigorous Software Engineering

## Requirements Elicitation

### Requirements

**Definition**: Requirements define features that the system must have or a constraint that it must satisfy to be accepted by the client.

**Result**: Requirements Specification that defines:

* Functional Requirements
  * Functionality (Relationship of outputs to inputs, exact sequence of operations, validity checks)
  * Environmental conditions (Interfaces)
  * User interaction
  * Error handling
* Nonfunctional Requirements
  * Performance
    * Static Numerical Requirements (Number of terminals supported, number of simultaneous users, ...)
    * Dynamic Numerical Requirements (Number of transactions within a certain time period)
  * Attributes (Portability, Correctness, Security ...)
  * Design Constraints
* Pseudo Requirements
  * Implementation Requirements
  * Legal Requirements

It does not define:

* System structure
* Implementation technology
* System design
* Development methodology

Requirements should be:

* Correct
* Consistent
* Complete
* Clear
* Realistic
* Verifiable
* Traceable

Requirements are usually validated using:

* Reviews
* Prototypes

### Activities

#### Identifying Actors

* Which user groups are supported by the system?
* Which user groups execute the system’s main functions?
* Which user groups perform secondary functions (maintenance, administration)?
* With what external hardware and software will the system interact?

#### Identifying Scenarios

A narrative description of what people do and experience as they try to make use of computer systems and applications.

* What are the tasks the actor wants the system to perform?
* What information does the actor access'
* Which external changes does the actor need to inform the system about? 
* Which events does the system need to inform the actor about?

#### Identifying Use Cases

A list of steps describing the interaction between an actor and the system, to achieve a goal.

#### Identifying Nonfunctional Requirements

Nonfunctional requirements are defined together with functional requirements because of dependencies, they typically contain conflicts.

## Informal Modeling

## Formal Modeling

## Patterns

## Functional Testing

## Structural Testing

## Automatic Test Case Generation

## Dynamic Program Analysis

## Static Program Analysis