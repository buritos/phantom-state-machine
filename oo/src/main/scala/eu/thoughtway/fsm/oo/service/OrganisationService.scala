package eu.thoughtway.fsm.oo.service

import eu.thoughtway.fsm.oo.model.Organisation

trait OrganisationService {
  def define(id: String): Organisation
  def approve(org: Organisation): Unit
  def credit(org: Organisation, amount: BigDecimal): Unit
  def debit(org: Organisation, amount: BigDecimal): Unit
  def suspend(org: Organisation): Unit
  def resume(org: Organisation): Unit
  def disable(org: Organisation): Unit

  def expedite(amount: BigDecimal)(id: String): Organisation = {
    val org = define(id)
    approve(org)
    credit(org, amount)
    org
  }
}

object OrganisationService extends OrganisationService {
  override def define(id: String): Organisation = new Organisation(id)

  override def approve(org: Organisation): Unit = org.approve()

  override def credit(org: Organisation, amount: BigDecimal): Unit =
    org.credit(amount)

  override def debit(org: Organisation, amount: BigDecimal): Unit =
    org.debit(amount)

  override def suspend(org: Organisation): Unit = org.suspend()

  override def resume(org: Organisation): Unit = org.resume()

  override def disable(org: Organisation): Unit = org.disable()
}
