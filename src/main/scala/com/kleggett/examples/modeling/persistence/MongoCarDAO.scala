package com.kleggett.examples.modeling.persistence

import com.kleggett.examples.modeling.model.Car
import com.mongodb.casbah.MongoDB

/**
 * Example implementation of the MongoCrudDAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:08 PM)
 */
class MongoCarDAO(override val db: MongoDB, override val collectionName: String = "cars")
  extends MongoVehicleDAO[Car]
{
  override protected def modelClass = classOf[Car]
}
