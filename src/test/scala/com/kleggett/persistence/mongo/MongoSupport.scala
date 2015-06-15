package com.kleggett.persistence.mongo

import com.mongodb.casbah.MongoDB
import org.scalatest.{BeforeAndAfterEach, Suite}

/**
 * This trait provides mongo-related support to unit tests, including dropping
 * all of the collections in database (poor man's rollback).
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 10:25 PM)
 */
trait MongoSupport extends BeforeAndAfterEach
{
  this: Suite =>

  override protected def afterEach() {
    super.afterEach()
    dropAllCollections(db)
  }

  def dropAllCollections(db: MongoDB) = {
    db.getCollectionNames().filterNot(_.contains("system.")).foreach(c => db.getCollection(c).drop())
  }

  protected[this] def db: MongoDB = ???
}
