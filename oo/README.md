# Object-Oriented implementation of stateful entity management

## Description

An object-oriented solution of the problem described in the root project's [README](../README.md#example-problem). 

## Techniques

Implementation of the State pattern to represent the `Organisation` state machine.

The solution throws `IllegalStateException` at runtime to signal failure to process operations 
that should not be accepted by the current `State`. 

## Comparison
#### Advantages

- Makes it easier to reason about what behaviour is acceptable when and how state transitions take place;
- Forces the developer to think about what the implementation of a behaviour should be in every state. 

#### Disadvantages

- Too much ceremony to get it going and maintain.


## Trade-offs

Adding behaviours to the `State` interface requires us to implement those in every implementation, 
even though in most cases those are noops or simply throw `IllegalStateException`. 
Although this could be avoided by providing default implementations on the interface 
and override only what is relevant for each state, we chose to force the developer 
to consider each behaviour in every case separately. This decision comes at the cost 
of verbosity as pointed out already in the comparison.      

## Limitations

Although the representation of each state as a separate class makes it easier 
to navigate and reason about our code, checks for illegal state transitions 
and behaviour invocation  are a runtime concern.

The state pattern does not help us to enforce the correct usage of our stateful API.

---
## LICENSE

The code is distributed under the GNU GPLv3 license (see [COPYING](COPYING)).
The choice of GPLv3 is to enforce sharing of improvements to the examples.

The supporting documentation is distributed under the Attribution-ShareAlike Creative Commons License 
([CC BY-SA](https://creativecommons.org/licenses/by-sa/4.0/)).

![GPL]

![CC]

[CC]: https://licensebuttons.net/l/by-sa/3.0/88x31.png "Attribution-ShareAlike Creative Commons License"
[GPL]: https://www.gnu.org/graphics/gplv3-with-text-84x42.png "GNU GPLv3 Logo"