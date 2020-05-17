# The Phantom of the Stateful Entity

###### A journey from Procedural to Object-Oriented, to Functional type-level implementations for managing state.

## Abstract

Most bugs in software, apart from configuration errors, are attributed to mismanagement 
of state. Effective and safe management of state in a program can make it or break it for 
the success and maintainability of a system. Manage state in your programs too loose, 
and you are severely limiting your ability to safely evolve your code to meet changing business needs. 
Several techniques have been developed over the years to make it more reasonable and 
safe to manage state in software. In this repository, you will find three different approaches 
for managing state. A procedural approach to state management, an Object-Oriented approach 
(State pattern) and a functional implementation using type-level programming techniques (Phantom types). 
The goal is to compare these approaches and gather feedback on which approach is the most 
pragmatic and maintainable in the long run.

## Programming Language

The programing language is Scala. Scala is a hybrid Object-Oriented and Functional programming language 
which makes it ideal for the purposes of this demonstration. It should be relatively straight-forward 
to follow the examples, even if you are not familiar with Scala. 
Some exposure to Functional programming and type-level programming techniques may prove advantageous to 
fully grasp how the techniques used in the Functional example come together to implement state management. 
It is encouraged to point out parts that are difficult to understand in issues. 
My best effort will be made to address those areas with better syntax and/or documentation.

## Repository Structure

The project uses `sbt` and each example implementation is in its own sub-project.
Each sub-project follows the standard `sbt` project structure, and you can see 
the usage of each example in the corresponding tests implemented in the test folder. 
Each sub-project comes with its own `README` file, explaining the approach, 
techniques used and comparison of the approach with its predecessor.
The recommended order to navigate is to start from the procedural example (`proc`), 
then the object-oriented (`oo`), followed by the functional example (`fp`).

Here are links to the README files for your convenience

| Solution                          | Opinionated Title     | Opinionated Description   |
| ---                               | ---                   | ---                       |
| [Procedural](proc/README.md)      | The Dark Age          | The code is minging       |
| [Object-Oriented](oo/README.md)   | The Renaissance       | Holy craft                |
| [Functional](fp/README.md)        | Age of Enlightenment  | Return of the Jedi        |

## Example Problem

I chose a simple but realistic example to demonstrate the techniques.

The problem domain is about a department that deals with Organisations that 
can be credited and debited amounts.

In order to demonstrate the impact of changes in each of the implementations we 
will pretend the department makes a couple of change requests after we 
deliver the initial implementation of our solution.

Each change request will be implemented in a branch with a corresponding pull request 
so that we can compare the latest version of the solution with the previous one. 
You will find links to the pull requests inline with the descriptions of the change requests below.

#### V1

An `Organisation` can be created given an user defined `id` value.

Someone from the department must then approve the `Organisation` enabling it to 
accept `credit` and `debit` requests.

At some later point, an `Organisation` can be disabled, prohibiting further 
processing of `credit` or `debit` requests.   

The department also wants to be able to expedite the creation of an Organisation, 
so that it can accept `credit` and `debit` requests immediately after its creation.

This is the initial definition of the problem domain which we used to implement the three example programs.

The following state diagram depicts one possible solution for the above requirements.

(v1 solution diagram)

#### V2

The department wants us to add a single new behaviour that is acceptable only 
when an `Organisation` is in the `Disabled` state.

In this second version, the department want's us to add a `recover` behaviour 
that will transition a previously `Disabled` `Organisation` back to the `Enabled` state.

Here is the updated diagram with the new behaviour added.

(v2 solution diagram)

#### V3

Next, we will look into a change request that affects the number of valid states that an 
`Organisation` can be in.

The department wants us to implement a `suspend` behaviour for an `Organisation`, 
prohibiting the issuing of `credit` requests, but allowing the processing of `debit` requests.

At some later point, the department may choose to `resume` a suspended `Organisation`, 
so that they can continue processing further `credit` requests for that `Organisation`.

Following is the updated state diagram with the new state and associated behaviours added.

(v3 solution diagram)  

## Feedback

You are encouraged to leave feedback in the form of issues. 
I don't advocate for any of these techniques as being superior over the others in every context, 
and I would love to hear your input on what you think of the approach, mistakes I've made, 
as well as concerns that I haven't documented in the comparisons and descriptions.

## How to run locally

- install `sbt` following the instructions found on the [official sbt website](https://www.scala-sbt.org/download.html);
- `clone` the repository and `cd` into the project root directory;
- start `sbt` by typing `sbt` in the console;
- run the tests for all projects by typing `tests` inside the sbt console;
- run individual sub-project tests by prefixing the `sbt` task with the sub-project's name (e.g. `fp/test`).

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