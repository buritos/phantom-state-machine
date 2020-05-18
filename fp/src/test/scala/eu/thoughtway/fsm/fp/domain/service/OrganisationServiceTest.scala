package eu.thoughtway.fsm.fp.domain.service

import java.time.LocalDateTime

import eu.thoughtway.fsm.fp.domain.model.Organisation.{
  Defined,
  Disabled,
  Enabled,
  Suspended
}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class OrganisationServiceTest extends AnyFunSpec with Matchers {

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

      it("should not allow suspend") {
        "suspend(defined)" shouldNot compile
      }

      it("should not allow resume") {
        "resume(defined)" shouldNot compile
      }

      it("should not allow disable") {
        "disable(defined)" shouldNot typeCheck
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

        it("should not allow resume") {
          "resume(approved)" shouldNot compile
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

        describe("When an approved Organisation is suspended") {
          val suspended = suspend(approved)

          it("should have its id remain unchanged") {
            suspended.id should be(approved.id)
          }

          it("should have its created date remain unchanged") {
            suspended.created should be(approved.created)
          }

          it("should have its status set to Suspended") {
            suspended.status should be(Suspended)
          }

          it("should have its balance remain unchanged") {
            suspended.balance should be(approved.balance)
          }

          it("should not allow credit") {
            "val a: String = 1" shouldNot typeCheck
          }

          it("should not allow debit") {
            "debit(10)(suspended)" shouldNot typeCheck
          }

          it("should not allow suspend") {
            "suspend(suspended)" shouldNot compile
          }

          it("should not allow resume") {
            "resume(suspended)" shouldNot compile
          }

          it("should not allow disable") {
            "disable(suspended)" shouldNot compile
          }

          describe("When a suspended Organisation is debited") {
            val debited = debit(suspended)(10)

            it("should have its id remain unchanged") {
              debited.id should be(suspended.id)
            }

            it("should have its created date remain unchanged") {
              debited.created should be(suspended.created)
            }

            it("should have its status remain unchanged") {
              debited.status should be(suspended.status)
            }

            it("should have its balance decremented by the given amount") {
              debited.balance should be(suspended.balance - 10)
            }
          }

          describe("When a suspended Organisation is resumed") {
            val resumed = resume(suspended)

            it("should have its id remain unchanged") {
              resumed.id should be(suspended.id)
            }

            it("should have its created date remain unchanged") {
              resumed.created should be(suspended.created)
            }

            it("should have its status set to Enabled") {
              resumed.status should be(Enabled)
            }

            it("should have its balance remain unchanged") {
              resumed.balance should be(suspended.balance)
            }
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
