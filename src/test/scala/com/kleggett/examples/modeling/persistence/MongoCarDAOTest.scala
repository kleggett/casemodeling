package com.kleggett.examples.modeling.persistence

import com.kleggett.examples.modeling.model.Car
import com.kleggett.persistence.mongo.{MongoCrudDAO, MongoCrudDAOTest}

/**
 * An example test for the MongoCarDAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 10:53 PM)
 */
class MongoCarDAOTest extends MongoCrudDAOTest[String, Car]
{
  override def dao: MongoCrudDAO[String, Car] = new MongoCarDAO(db)

  override def testData(): Car = Car(None, "Toyota", "Avalon", 2500, 4)

  override def updateData(): Car = {
    val c = testData()
    Car(c.vin, "Toyota", "Avalon-X", 3000, 2)
  }

  override val assertEquals: (Car, Car) => Unit = (actual, existing) => { actual === existing }

  describe("MongoCrudDAO") {
    it should behave like mongoCrudDAO()
  }
}
