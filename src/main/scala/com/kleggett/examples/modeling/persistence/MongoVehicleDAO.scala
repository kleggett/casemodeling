package com.kleggett.examples.modeling.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.kleggett.examples.modeling.model.{DBVehicle, Vehicle}
import com.kleggett.persistence.mongo.{MongoCrudDAO, MongoDAO}
import org.mongojack.internal.MongoJackModule

/**
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:04 PM)
 */
trait MongoVehicleDAO[V <: DBVehicle] extends MongoCrudDAO[String, V]
{
  implicit val objMapper = MongoJackModule.configure(new ObjectMapper())

  override val collection = MongoDAO.dbCollection(db, collectionName, modelClass, classOf[String])

  protected def modelClass: Class[V]

  override def generateId: String = Vehicle.newVIN()
}
