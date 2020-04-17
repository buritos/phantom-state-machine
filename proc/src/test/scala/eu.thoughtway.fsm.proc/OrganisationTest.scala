package eu.thoughtway.fsm.proc

import java.time.LocalDateTime

import org.scalatest.{FunSpec, Matchers, ParallelTestExecution}

class OrganisationTest
    extends FunSpec
    with Matchers
    with ParallelTestExecution {

  describe("Given an Organisation") {

    import Organisation._

    describe("When defining an Organisation given an id") {
      val org = define("test")

      it("should have its id set to the given id") {
        org.id should be("test")
      }

      it("should have its created date set to a date in the past") {
        org.created.compareTo(LocalDateTime.now()) should be <= 0
      }

      it("should have its status set to the Defined Status") {
        org.state should be(Defined)
      }

      it("should have its balance set to zero") {
        org.balance should be(0)
      }

      it("should throw IllegalStateException on credit") {
        an[IllegalStateException] shouldBe thrownBy(credit(org, 10))
      }

      it("should throw IllegalStateException on debit") {
        an[IllegalStateException] shouldBe thrownBy(debit(org, 10))
      }

      it("should throw IllegalStateException on disable") {
        an[IllegalStateException] shouldBe thrownBy(disable(org))
      }

      it("should throw IllegalStateException on recover") {
        an[IllegalStateException] shouldBe thrownBy(recover(org))
      }
    }

    describe("When a defined Organisation is approved") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its state set to the Enabled State") {
        org.state should be(Enabled)
      }

      it("should have its balance remain unchanged") {
        org.balance should be(balanceOriginal)
      }

      it("should throw IllegalStateException on approve") {
        an[IllegalStateException] shouldBe thrownBy(approve(org))
      }

      it("should throw IllegalStateException on recover") {
        an[IllegalStateException] shouldBe thrownBy(recover(org))
      }
    }

    describe(
      "When an approved Organisation is credited with a given amount"
    ) {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)

      credit(org, 10)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its status remain unchanged") {
        org.state should be(Enabled)
      }

      it("should have its balance incremented by the given amount") {
        org.balance should be(balanceOriginal + 10)
      }
    }

    describe("When an approved Organisation is debited") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)

      debit(org, 10)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its status remain unchanged") {
        org.state should be(Enabled)
      }

      it("should have its balance decremented by the given amount") {
        org.balance should be(balanceOriginal - 10)
      }
    }

    describe("When an approved Organisation is disabled") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)

      disable(org)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its status set to the Disabled Status") {
        org.state should be(Disabled)
      }

      it("should have its balance remain unchanged") {
        org.balance should be(balanceOriginal)
      }

      it("should throw IllegalStateException on approve") {
        an[IllegalStateException] shouldBe thrownBy(approve(org))
      }

      it("should throw IllegalStateException on credit") {
        an[IllegalStateException] shouldBe thrownBy(credit(org, 10))
      }

      it("should throw IllegalStateException on debit") {
        an[IllegalStateException] shouldBe thrownBy(debit(org, 10))
      }

      it("should throw IllegalStateException on disable") {
        an[IllegalStateException] shouldBe thrownBy(disable(org))
      }
    }

    describe("When a disabled Organisation is recovered") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)
      disable(org)
      recover(org)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its status set to the Enabled Status") {
        org.state should be(Enabled)
      }

      it("should have its balance remain unchanged") {
        org.balance should be(balanceOriginal)
      }
    }

    describe(
      "When expediting the definition of an Organisation given an amount and the id"
    ) {
      val org = expedite(10)("test")

      it("should have its id set to the given id") {
        org.id should be("test")
      }

      it("should have its created date set to a date in the past") {
        org.created.compareTo(LocalDateTime.now()) should be <= 0
      }

      it("should have its status set to the Enabled Status") {
        org.state should be(Enabled)
      }

      it("should have its balance set to the given amount") {
        org.balance should be(10)
      }
    }
  }
}
