package com.kleggett.examples.modeling.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.kleggett.examples.modeling.model.Vehicle
import com.kleggett.persistence.mongo.{MongoCrudDAO, MongoDAO}
import org.mongojack.internal.MongoJackModule

/**
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:04 PM)
 */
trait MongoVehicleDAO[V <: Vehicle] extends MongoCrudDAO[String, V]
{
  // In real life this is probably a custom ObjectMapper w/scala support
  implicit val objMapper = MongoJackModule.configure(new ObjectMapper())

  override val collection = MongoDAO.dbCollection(db, collectionName, modelClass, classOf[String])

  override def generateId: String = Vehicle.newVIN()

  protected def modelClass: Class[V]
}
