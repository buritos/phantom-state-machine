# Functional, Type-level implementation of stateful entity management

## Description

A Functional, Type-level solution to the problem described in the root project's [README](../README.md#example-problem).

## Techniques

ADTs to represent data;
State transitions and behaviours as referentially transparent functions; 
Phantom Types to constraint the inputs as well as the outputs for each transition.

## Comparison
#### Advantages

- Even easier to reason about and test due to referential transparency;
- Catches illegal state transition attempts at compile time;
- Concise without compromising safety or expressiveness.

#### Disadvantages

- Adds additional type checking requirements upstream;
- Referentially transparency for state management may prove confusing 
for people who are more used to mutable state.

## Trade-offs

Compile time safety at the `Service` boundaries push runtime checks upstream.

## Limitations

- Non-binary transitions are cumbersome to represent. 

Currently, best bet for Scala 2 programs is subtyping which can get unwindly pretty fast (see <link to PR>). 
In Scala 3, Union types are a more suitable language feature for representing such transitions (see <link to PR>).  

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