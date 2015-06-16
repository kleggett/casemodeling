package com.kleggett.examples.modeling.persistence

import com.kleggett.examples.modeling.model.{DBVehicle, Vehicle}
import com.kleggett.persistence.jdbc.JdbcCrudDAO

/**
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:36 PM)
 */
trait JdbcVehicleDAO[V <: DBVehicle] extends JdbcCrudDAO[String, V]
{
  override def generateId: String = Vehicle.newVIN()
}
