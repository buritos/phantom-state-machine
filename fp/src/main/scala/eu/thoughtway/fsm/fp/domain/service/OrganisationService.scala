package eu.thoughtway.fsm.fp.domain.service

import eu.thoughtway.fsm.fp.domain.model.Organisation
import eu.thoughtway.fsm.fp.domain.model.Organisation._

trait OrganisationService {

  def define(id: String): OrganisationDefined
  def approve(org: OrganisationDefined): OrganisationEnabled
  def credit(org: OrganisationEnabled)(
      amount: BigDecimal
  ): OrganisationEnabled
  def debit[A <: Enabled | Suspended](org: Organisation[A])(
      amount: BigDecimal
  ): Organisation[A]
  def suspend(
      org: OrganisationEnabled | OrganisationDisabled
  ): OrganisationSuspended
  def resume(org: OrganisationSuspended): OrganisationEnabled
  def disable(
      org: OrganisationEnabled | OrganisationSuspended
  ): OrganisationDisabled

  def expedite(id: String)(amount: BigDecimal): OrganisationEnabled =
    (define _ andThen approve _ andThen credit)(id)(amount)
}

trait OrganisationServiceImpl extends OrganisationService {
  override def define(id: String): OrganisationDefined =
    Organisation(id)

  override def approve(org: OrganisationDefined): OrganisationEnabled =
    org.copy(status = Enabled)

  override def credit(org: OrganisationEnabled)(
      amount: BigDecimal
  ): OrganisationEnabled =
    org.copy(balance = org.balance + amount)

  override def debit[A <: Enabled | Suspended](org: Organisation[A])(
      amount: BigDecimal
  ): Organisation[A] =
    org.copy(balance = org.balance - amount)

  override def suspend(
      org: OrganisationEnabled | OrganisationDisabled
  ): OrganisationSuspended =
    org.copy(status = Suspended)

  override def resume(org: OrganisationSuspended): OrganisationEnabled =
    org.copy(status = Enabled)

  override def disable(
      org: OrganisationEnabled | OrganisationSuspended
  ): OrganisationDisabled =
    org.copy(status = Disabled)
}
