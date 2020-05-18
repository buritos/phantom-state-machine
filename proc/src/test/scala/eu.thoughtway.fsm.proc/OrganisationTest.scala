package eu.thoughtway.fsm.proc

import java.time.LocalDateTime

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class OrganisationTest extends AnyFunSpec with Matchers {

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

      it("should throw IllegalStateException on suspend") {
        an[IllegalStateException] shouldBe thrownBy(suspend(org))
      }

      it("should throw IllegalStateException on resume") {
        an[IllegalStateException] shouldBe thrownBy(resume(org))
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

      it("should throw IllegalStateException on resume") {
        an[IllegalStateException] shouldBe thrownBy(resume(org))
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

    describe("When an approved Organisation is suspended") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)
      suspend(org)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its state set to the Suspended state") {
        org.state should be(Suspended)
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

      it("should throw IllegalStateException on suspend") {
        an[IllegalStateException] shouldBe thrownBy(suspend(org))
      }
    }

    describe("When an suspended Organisation is debited") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)
      suspend(org)
      debit(org, 10)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its state remain unchanged") {
        org.state should be(Suspended)
      }

      it("should have its balance decremented by the amount debited") {
        org.balance should be(balanceOriginal - 10)
      }
    }

    describe("When an suspended Organisation is resumed") {

      val org = define("test")

      val createdOriginal = org.created
      val balanceOriginal = org.balance

      approve(org)
      suspend(org)
      resume(org)

      it("should have its id remain unchanged") {
        org.id should be("test")
      }

      it("should have its created date remain unchanged") {
        org.created should be(createdOriginal)
      }

      it("should have its state set to the Enabled state") {
        org.state should be(Enabled)
      }

      it("should have its balance remain unchanged") {
        org.balance should be(balanceOriginal)
      }
    }

    describe("When a suspended Organisation is disabled") {

      val org = define("test")

      approve(org)
      suspend(org)
      disable(org)

      it("should have its state set to the Disabled state") {
        org.state should be(Disabled)
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

      it("should throw IllegalStateException on resume") {
        an[IllegalStateException] shouldBe thrownBy(resume(org))
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
