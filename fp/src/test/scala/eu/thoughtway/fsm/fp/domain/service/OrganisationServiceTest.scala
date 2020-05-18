package eu.thoughtway.fsm.fp.domain.service

import java.time.LocalDateTime

import eu.thoughtway.fsm.fp.domain.model.Organisation.{
  Defined,
  Disabled,
  Enabled
}
import org.scalatest.{FunSpec, Matchers, ParallelTestExecution}

class OrganisationServiceTest
    extends FunSpec
    with Matchers
    with ParallelTestExecution {

  describe("Given an OrganisationService") {

    object OrganisationService extends OrganisationServiceImpl
    import OrganisationService._

    describe("When defining an Organisation given an id") {
      val defined = define("test")

      it("should have its id set to the given id") {
        defined.id should be("test")
      }

      it("should have its created date set to a date in the past") {
        defined.created.compareTo(LocalDateTime.now()) should be <= 0
      }

      it("should have its status set to the Defined Status") {
        defined.status should be(Defined)
      }

      it("should have its balance set to zero") {
        defined.balance should be(0)
      }

      it("should not allow credit") {
        "credit(defined)(10)" shouldNot typeCheck
      }

      it("should not allow debit") {
        "debit(defined)(10)" shouldNot typeCheck
      }

      it("should not allow disable") {
        "disable(defined)" shouldNot typeCheck
      }

      it("should not allow recover") {
        "recover(defined)" shouldNot typeCheck
      }

      describe("When a defined Organisation is approved") {
        val approved = approve(defined)

        it("should have its id remain unchanged") {
          approved.id should be(defined.id)
        }

        it("should have its created date remain unchanged") {
          approved.created should be(defined.created)
        }

        it("should have its status set to the Enabled Status") {
          approved.status should be(Enabled)
        }

        it("should have its balance remain unchanged") {
          approved.balance should be(defined.balance)
        }

        it("should not allow approve") {
          "approve(approved)" shouldNot typeCheck
        }

        it("should not allow recover") {
          "recover(defined)" shouldNot typeCheck
        }

        describe(
          "When an approved Organisation is credited with a given amount"
        ) {
          val credited = credit(approved)(10)

          it("should have its id remain unchanged") {
            credited.id should be(approved.id)
          }

          it("should have its created date remain unchanged") {
            credited.created should be(approved.created)
          }

          it("should have its status remain unchanged") {
            credited.status should be(approved.status)
          }

          it("should have its balance incremented by the given amount") {
            credited.balance should be(approved.balance + 10)
          }
        }

        describe("When an approved Organisation is debited") {
          val debited = debit(approved)(10)

          it("should have its id remain unchanged") {
            debited.id should be(approved.id)
          }

          it("should have its created date remain unchanged") {
            debited.created should be(approved.created)
          }

          it("should have its status remain unchanged") {
            debited.status should be(approved.status)
          }

          it("should have its balance decremented by the given amount") {
            debited.balance should be(approved.balance - 10)
          }
        }

        describe("When an approved Organisation is disabled") {
          val disabled = disable(approved)

          it("should have its id remain unchanged") {
            disabled.id should be(approved.id)
          }

          it("should have its created date remain unchanged") {
            disabled.created should be(approved.created)
          }

          it("should have its status set to the Disabled Status") {
            disabled.status should be(Disabled)
          }

          it("should have its balance remain unchanged") {
            disabled.balance should be(approved.balance)
          }

          it("should not allow approve") {
            "approve(disabled)" shouldNot typeCheck
          }

          it("should not allow credit") {
            "credit(disabled)(10)" shouldNot typeCheck
          }

          it("should not allow debit") {
            "debit(disabled)(10)" shouldNot typeCheck
          }

          it("should not allow disable") {
            "disable(disabled)" shouldNot typeCheck
          }

          describe("When a disabled Organisation is recovered") {
            val recovered = recover(disabled)

            it("should have its id remain unchanged") {
              recovered.id should be(approved.id)
            }

            it("should have its created date remain unchanged") {
              recovered.created should be(approved.created)
            }

            it("should have its status set to the Enabled Status") {
              recovered.status should be(Enabled)
            }

            it("should have its balance remain unchanged") {
              recovered.balance should be(approved.balance)
            }
          }
        }
      }
    }

    describe(
      "When expediting the definition of an Organisation given an amount and the id"
    ) {
      val expedited = expedite("test")(10)

      it("should have its id set to the given id") {
        expedited.id should be("test")
      }

      it(
        "should have its created date set to a date value that is less than or equal to now"
      ) {
        expedited.created.compareTo(LocalDateTime.now()) should be <= 0
      }

      it("should have its status set to the Enabled Status") {
        expedited.status should be(Enabled)
      }

      it("should have its balance set to the given amount") {
        expedited.balance should be(10)
      }
    }
  }
}
