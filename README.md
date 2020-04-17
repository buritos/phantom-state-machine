# The Phantom of the State Machine

###### A journey from procedural to Object-Oriented, to functional implementations of Finite State Machines.

## Abstract

Finite state machines (FSM) have been around since ancient times. Although the 
tools for encoding FSMs into application code has significantly evolved since then, 
the concept remains largely the same. A process starts in the real world and can 
be in a single, well-defined state at any given point in time until it completes 
and finally discarded in yet another singular, well-defined state. As a process 
lives, within the boundaries of the system that it belongs, it may transition 
from any given state to some other state several times until it completes, 
according to the laws of that system. The rules of such a system are what define 
the number of possible, legal states the process can be in, as well as which are 
valid transitions in-between those states. The journey will begin at a time when 
such states and transitions took the form of conditional logic statements. 
We will then move to an era when objects ruled the world of software systems 
and state, as well as the transitions between, could be encoded in a more graphic, 
less error-prone style in our code. Finally, we will look at a time when 
functional programming challenges the more mainstream object-oriented (OO) 
approach, allowing us to write safer programs which are easier to reason about. 
Are functional programming techniques appropriate for the implementation of FSM, 
or OO conventions still rule when it comes to modelling stateful processes?
