package eu.thoughtway.fsm.proc

import java.time.LocalDateTime

import eu.thoughtway.fsm.proc.Organisation.State

class Organisation(
    var id: String,
    var created: LocalDateTime,
    var state: State,
    var balance: BigDecimal
)

object Organisation {

  sealed trait State
  case object Defined extends State
  case object Enabled extends State
  case object Disabled extends State

  def define(id: String): Organisation =
    new Organisation(id, LocalDateTime.now(), Defined, 0)

  def approve(org: Organisation): Unit =
    if (org.state.equals(Defined)) {
      org.state = Enabled
    } else {
      throw new IllegalStateException(
        s"Can't approve Organisation(${org.id}, ${org.state})"
      )
    }

  def credit(org: Organisation, amount: BigDecimal): Unit =
    if (org.state.equals(Enabled)) {
      org.balance = org.balance + amount
    } else {
      throw new IllegalStateException(
        s"Can't credit Organisation(${org.id}, ${org.state})"
      )
    }

  def debit(org: Organisation, amount: BigDecimal): Unit =
    if (org.state.equals(Enabled)) {
      org.balance = org.balance - amount
    } else {
      throw new IllegalStateException(
        s"Can't debit Organisation(${org.id}, ${org.state})"
      )
    }

  def disable(org: Organisation): Unit =
    if (org.state.equals(Enabled)) {
      org.state = Disabled
    } else {
      throw new IllegalStateException(
        s"Can't debit Organisation(${org.id}, ${org.state})"
      )
    }

  def expedite(amount: BigDecimal)(id: String): Organisation = {
    val org = define(id)
    approve(org)
    credit(org, amount)
    org
  }

  def recover(org: Organisation): Unit =
    if (org.state.equals(Disabled)) {
      org.state = Enabled
    } else {
      throw new IllegalStateException(
        s"Can't recover Organisation(${org.id}, ${org.state})"
      )
    }
}
