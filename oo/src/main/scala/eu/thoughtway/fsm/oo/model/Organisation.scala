package eu.thoughtway.fsm.oo.model

import java.time.LocalDateTime

class Organisation(private[this] val _id: String)
    extends Organisation.Organisation {

  def id: String = _id

  private[this] var _created: LocalDateTime = LocalDateTime.now()
  def created: LocalDateTime                = _created

  private[this] var _balance: BigDecimal = BigDecimal(0)
  def balance: BigDecimal                = _balance

  private[this] val _defined = new Defined()
  def defined: Defined       = _defined

  private[this] val _enabled = new Enabled()
  def enabled: Enabled       = _enabled

  private[this] val _disabled = new Disabled()
  def disabled: Disabled      = _disabled

  private[this] var _state: State = _defined
  def state: State                = _state

  override def approve(): Unit = _state.approve()

  override def credit(amount: BigDecimal): Unit = _state.credit(amount)

  override def debit(amount: BigDecimal): Unit = _state.debit(amount)

  override def disable(): Unit = _state.disable()

  sealed trait State extends Organisation.Organisation

  final class Defined() extends State {
    override def approve(): Unit = _state = _enabled

    override def credit(amount: BigDecimal): Unit =
      throw new IllegalStateException(
        "Can't credit Organisation in Defined state"
      )

    override def debit(amount: BigDecimal): Unit =
      throw new IllegalStateException(
        "Can't debit Organisation in Defined state"
      )

    override def disable(): Unit =
      throw new IllegalStateException(
        "Can't disable Organisation in Defined state"
      )
  }

  final class Enabled() extends State {
    override def approve(): Unit =
      throw new IllegalStateException(
        "Can't approve Organisation in Enabled state"
      )

    override def credit(amount: BigDecimal): Unit =
      _balance = balance + amount

    override def debit(amount: BigDecimal): Unit =
      _balance = balance - amount

    override def disable(): Unit = _state = _disabled
  }

  final class Disabled() extends State {
    override def approve(): Unit =
      throw new IllegalStateException(
        "Can't approve Organisation in Disabled state"
      )

    override def credit(amount: BigDecimal): Unit =
      throw new IllegalStateException(
        "Can't credit Organisation in Disabled state"
      )

    override def debit(amount: BigDecimal): Unit =
      throw new IllegalStateException(
        "Can't debit Organisation in Disabled state"
      )

    override def disable(): Unit =
      throw new IllegalStateException(
        "Can't disable Organisation in Disabled state"
      )
  }
}

object Organisation {
  sealed trait Organisation {
    def approve(): Unit
    def credit(amount: BigDecimal): Unit
    def debit(amount: BigDecimal): Unit
    def disable(): Unit
  }
}
