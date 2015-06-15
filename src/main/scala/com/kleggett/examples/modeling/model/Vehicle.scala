package com.kleggett.examples.modeling.model

import com.kleggett.persistence.Persistable
import org.apache.commons.lang3.StringUtils

import scala.util.Random

/**
 * Example models for CASE domain modeling presentation.
 *
 * @author K. Leggett
 * @since 1.0 (6/7/15 4:55 PM)
 */
sealed trait Vehicle
{
  var vin: Option[String]

  def make: String

  def model: String

  def weight: Int
}

object Vehicle
{
  def newVIN() = Random.nextString(17)
}

trait PersistableVehicle extends Vehicle with Persistable[String]
{
  override def id_=(value: String): Unit = vin = Option(StringUtils.trimToNull(value))

  override def id: String = vin.orNull

  override def persisted: Boolean = vin.isDefined
}

case class Car(override var vin: Option[String], make: String, model: String, weight: Int, numDoors: Int)
  extends PersistableVehicle

case class Truck(override var vin: Option[String], make: String, model: String, weight: Int, numWheels: Int)
  extends PersistableVehicle
