---
title: Computer Networks Summary
author: Fabian Bösiger
geometry: margin=2cm
fontsize: 10pt
papersize: a5
toc: yes
toc-depth: 3
header-includes:
- \usepackage[document]{ragged2e}
---

## Overview and Principles

### Delays

#### Transmission Delay

Amount of time required to put all bits onto the link.

$$D_\text{transmission} = \frac{\text{Packet Size}}{\text{Link Bandwith}}$$

#### Propagation Delay

Amount of time required for a bit to travel to the end of the link.

$$D_\text{propagation} = \frac{\text{Link Length}}{\text{Propagation Speed}}$$

#### Processing Delay

Amount of time required to process a packet

#### Queuing Delay

Amount of time a packet waits in a buffer to be transmitted on a link.

#### Total Delay

$$D_\text{total} = D_\text{transmission} + D_\text{propagation} + D_\text{processing} + D_\text{queuing}$$

#### Traffic Intensity

$a$: Average packet arrival rate.

$R$: Transmission rate of outgoing link.

$L$: Fixed packet length.

$La$: Average bits arrival rate.

$\frac{La}{R}$: Traffic intensity. If $\frac{La}{R} > 1$, the queue will increase without a bound, and so will the queuing delay.

#### Network Characterization

A network is characterized by its delay, loss rate and throughput.

$\text{Average Throughput} = \frac{\text{Data Size}}{\text{Transfer Time}}$

#### Circuit-Switching

* Predictable performance
* Simple and fast switching once the circuit is established
* Inefficient if the traffic is bursty or short
* Complex circuit setup and teardown
* Requires new circuit upon failure

#### Packet-Switching

### Layer Overview

|Layer||
|---|---|
|Application||
|Transport||
|Network||
|Link||
|Physical||

## Application Layer

### DNS

The Domain Name Service (DNS) system is a distributed database which enables resolving a name to an IP address.

Names can be mapped to more than one IP and the other way around.

The DNS system is hierarchically administered, and thus trivially avoids name collisions. There are 13 professionally managed root servers. Each server above knows the address of the servers below that previously had to register themselves.

To find the closest root server, operators rely on BGP anycast. That is, if sereval locations announce the same prefix, then routing wiill deliver the packets to the closest location.

DNS queries and replies use UDP, reliability is implemented by repeating requests.

DNS can be implemented recursively or iteratively. In practice, the iterative implementation is chosen do reduce DNS server load.

To further reduce resolution times, DNS relies on caching on all levels. The cached entries have an associated time to live (TTL). After the TTL is reached, the entry must be cleared.

### BGP

### HTTP

#### Request Layout

```text
<method> <URL relative to server> <version>
<header field name>:<value>
...
<header field name>:<value>

<body>
```

#### Response Layout

```text
<version> <status> <phrase>
<header field name>:<value>
...
<header field name>:<value>

<body>
```

#### Methods

|Method|Function|
|---|---|
|`GET`|Return a resource.|
|`HEAD`|Return headers only.|
|`POST`|Send data to server.|

#### URL

A Uniform Resource Locator refers to an internet resource.

```text
protocol://hostname[:port]/path/to/resource
```

#### Status

|Response Code|||Reason Phrase|
|---|---|---|---|
|`1xx`|Informational|||
|`2xx`|Success|`200`|`OK`|
|`3xx`|Redirection|`301`|`Moved Permanently`|
|||`303`|`Moved Temporarily`|
|||`304`|`Not Modified`|
|`4xx`|Informational|`404`|`Not Found`|
|`5xx`|Informational|`505`|`Internal Server Error`|

#### Headers

|Name|Functionality|
|---|---|
|`User-Agent`|Client software.|
|`Location`|For redirection.|
|`Content-Encoding`|Encoding format.|
|`Content-Length`|Length of the content.|
|`Content-Type`|Type of the content, for example HTML.|
|`Expires`|Used for caching.|
|`Last-Modified`|Used for caching.|

#### Cookies

Because HTTP is a stateless protocol, cookies make the client to maintain the state.

#### Usage with TCP

As most Websites load multiple resources, HTTP reuses the underlying TCP connection multiple times.

#### CDNs

Content distribution networks can be used to replicate content around the globe in order to speed up propagation.

This is achieved either by using a customized DNS response based on the client location and server load, or by using BGP anycast.

## Transport Layer

### UDP

### TCP

### QUIC

QUIC is built on top of UDP and is in the process of being standardisized.

It replaces TCP with the following advantages:

* TCP handshake and TLS handshake in one.
* Connection does not have to be specific to the source IP as it is built on UDP. Connection IDs are separate from IP adresses.
* TCP is difficult to evolve.

## Network Layer

## Link Layer

### Framing

Given a stream of bits, how do we interpret it as a sequence of frames?

#### Byte Count

#### Byte Stuffing

#### Bit Stuffing

## Physical Layer