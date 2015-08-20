package com.kleggett.persistence.mongo

import com.kleggett.persistence.{CrudDAO, HasCopy, Persistable}

/**
 * This trait provides the implementations for the basic CRUD operations using MongoDB.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 2:52 PM)
 */
trait MongoCrudDAO[ID, M <: Persistable[ID] with HasCopy[ID, M]]
  extends MongoDAO[ID, M] with CrudDAO[ID, M]
{
  override def getById(id: ID): Option[M] = MongoDAO.queryById(collection, id)

  override def deleteById(id: ID): Int = {
    val rs = collection.removeById(id)
    rs.getN
  }

  override def save(obj: M, forceInsert: Boolean): M = {
    val preparedObj = populateIdIfNeeded(obj)
    val rs = {
      if (forceInsert) {
        concernOverride match {
          case Some(c) => collection.insert(preparedObj, c)
          case None => collection.insert(preparedObj)
        }
      }
      else {
        concernOverride match {
          case Some(c) => collection.save(preparedObj, c)
          case None => collection.save(preparedObj)
        }
      }
    }
    rs.getSavedObject
  }
}
