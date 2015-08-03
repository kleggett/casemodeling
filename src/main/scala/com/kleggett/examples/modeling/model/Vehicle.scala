package com.kleggett.examples.modeling.model

import com.kleggett.persistence.Persistable

import scala.util.Random

/**
 * Example models for CASE domain modeling presentation.
 *
 * @author K. Leggett
 * @since 1.0 (6/7/15 4:55 PM)
 */
sealed trait Vehicle extends Persistable[String]
{
  override type ModelType <: Vehicle

  override def id: Option[String] = Option(vin)

  def vin: String

  def make: String

  def model: String
}

object Vehicle
{
  def newVIN() = Random.nextString(17)
}

case class Car(vin: String, make: String, model: String, nDoors: Int)
  extends Vehicle
{
  override type ModelType = Car

  override def withId(newId: String): Car = copy(vin = newId)
}

