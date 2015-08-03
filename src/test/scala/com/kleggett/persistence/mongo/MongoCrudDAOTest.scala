package com.kleggett.persistence.mongo

import com.kleggett.BaseTest
import com.kleggett.persistence.Persistable

/**
 * This is the basis for all mongo-based crud DAO tests. It takes care of running
 * tests on the basic crud methods as long as you implement the abstract functions
 * and call `mongoCrudDAO` as a behavior.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 10:41 PM)
 */
trait MongoCrudDAOTest[ID, M <: Persistable[ID]] extends BaseTest with MongoSupport
{
  def dao: MongoCrudDAO[ID, M]

  def testData(): M

  def updateData(): M

  val assertEquals: (M, M) => Unit

  def mongoCrudDAO() {
    it("should insert new data properly") {
      val actual = dao.save(testData())
      assert(actual != null)
      assert(actual.persisted)
      findEquals(actual)
    }

    it("should force insert new data properly") {
      val model = testData()
      dao.populateIdIfNeeded(model)
      val actual = dao.save(model, forceInsert = true)
      assert(actual != null)
      assert(actual.persisted)
      findEquals(actual)
      assertEquals(actual, model)
    }

    it("should update existing data properly") {
      existingData
      val update = dao.populateIdIfNeeded(updateData())
      val actual = dao.save(update)
      assert(actual != null)
      assert(actual.persisted)
      assertEquals(actual, update)
      findEquals(update)
    }

    it("should find data properly") {
      val existing = existingData
      val actual = dao.getById(existing.id.get)
      assert(actual.isDefined)
      assertEquals(actual.get, existing)
    }

    it("should delete data properly") {
      val existing = existingData
      dao.deleteById(existing.id.get)
      assert(dao.getById(existing.id.get).isEmpty)
    }
  }

  def existingData: M = dao.save(testData())

  def findEquals(obj: M) {
    dao.getById(obj.id.get) match {
      case None => fail("Failed to find: " + obj)
      case Some(x) => assertEquals(x, obj)
    }
  }
}
