package eu.thoughtway.fsm.fp.domain.service

import cats.data.Reader
import eu.thoughtway.fsm.fp.domain.model.Organisation
import eu.thoughtway.fsm.fp.domain.model.Organisation._

trait OrganisationService[IdT, DefinedT, EnabledT, DisabledT, AmountT] {

  type ServiceOp[I, O] = Reader[I, O]

  def define: ServiceOp[IdT, DefinedT]
  def approve: ServiceOp[DefinedT, EnabledT]
  def credit(amount: AmountT): ServiceOp[EnabledT, EnabledT]
  def debit(amount: AmountT): ServiceOp[EnabledT, EnabledT]
  def disable: ServiceOp[EnabledT, DisabledT]

  def expedite(amount: AmountT): ServiceOp[IdT, EnabledT] =
    define andThen approve andThen credit(amount)
}

trait OrganisationServiceImpl
    extends OrganisationService[String,
                                OrganisationDefined,
                                OrganisationEnabled,
                                OrganisationDisabled,
                                BigDecimal] {
  override def define: ServiceOp[String, OrganisationDefined] = Reader { id =>
    Organisation.apply(id)
  }

  override def approve: ServiceOp[OrganisationDefined, OrganisationEnabled] =
    Reader { org =>
      org.copy(status = Enabled)
    }

  override def credit(
      amount: BigDecimal
  ): ServiceOp[OrganisationEnabled, OrganisationEnabled] =
    Reader { org =>
      org.copy(balance = org.balance + amount)
    }

  override def debit(
      amount: BigDecimal
  ): ServiceOp[OrganisationEnabled, OrganisationEnabled] =
    Reader { org =>
      org.copy(balance = org.balance - amount)
    }

  override def disable: ServiceOp[OrganisationEnabled, OrganisationDisabled] =
    Reader { org =>
      org.copy(status = Disabled)
    }
}
