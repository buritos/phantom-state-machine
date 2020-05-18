package eu.thoughtway.fsm.fp.domain.model

import java.time.LocalDateTime

final case class Organisation[Status](
    id: String,
    created: LocalDateTime,
    status: Status,
    balance: BigDecimal
)

object Organisation {

  sealed trait Status

  sealed trait Defined extends Status
  case object Defined extends Defined

  sealed trait Enabled extends Status
  case object Enabled extends Enabled

  sealed trait Suspended extends Status
  case object Suspended extends Suspended

  sealed trait Disabled extends Status
  case object Disabled extends Disabled

  type OrganisationDefined   = Organisation[Defined]
  type OrganisationEnabled   = Organisation[Enabled]
  type OrganisationSuspended = Organisation[Suspended]
  type OrganisationDisabled  = Organisation[Disabled]

  def apply(id: String): OrganisationDefined =
    new Organisation(id, LocalDateTime.now(), Defined, 0)
}
