# Procedural implementation of stateful entity management

## Description

A procedural solution to the problem described in the root project's [README](../README.md#example-problem).

## Techniques

We chose to represent data with a class holding mutable references to the `Organisation` fields.

We implement behaviour with static methods that mutate the `Organisation` instances that 
are passed in as the first argument to each of these methods. 

Each method asserts that the behaviour applies for the current `Organisation` state, 
using `if` statements, and it either mutates the state of the `Organisation` object, 
or signals error by throwing an `IllegalArgumentException`.   

## Trade-offs

The implementation is terse, fast to write and add new behaviours at the cost 
of making it harder to read and reason about.

## Limitations

There is nothing to remind developers that they should consider the validity 
of a new behaviour in every state an `Organisation` may be, making the addition 
of new behaviours a particularly error-prone endeavour. Adding a new state may 
also introduce bugs since the assertions of already implemented behaviours may 
be too loose in the light of the additional state.

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