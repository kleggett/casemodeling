package com.kleggett.persistence.mongo

import com.kleggett.persistence.{CrudDAO, Persistable}

/**
 * This trait provides the implementations for the basic CRUD operations using MongoDB.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 2:52 PM)
 */
trait MongoCrudDAO[ID, M <: Persistable[ID]] extends MongoDAO[ID, M] with CrudDAO[ID, M]
{
  override def getById(id: ID): Option[M] = MongoDAO.queryById(collection, id)

  override def deleteById(id: ID): Int = {
    val rs = collection.removeById(id)
    rs.getN
  }

  override def save(obj: M, forceInsert: Boolean): M = {
    val populated = populateIdIfNeeded(obj, generateId _)
    val rs = {
      if (forceInsert) {
        concernOverride match {
          case Some(c) => collection.insert(populated, c)
          case None => collection.insert(populated)
        }
      }
      else {
        concernOverride match {
          case Some(c) => collection.save(populated, c)
          case None => collection.save(populated)
        }
      }
    }
    rs.getSavedObject
  }

  override def saveBatch(batch: Traversable[M], forceInsert: Boolean): Traversable[M] = {
    import scala.collection.JavaConversions._
    var rs: org.mongojack.WriteResult[M, ID] = null
    if (forceInsert) {
      val populated = batch.map(m => populateIdIfNeeded(m, generateId _))
      concernOverride match {
        case Some(c) => rs = collection.insert(populated.toList, c)
        case None => rs = collection.insert(populated.toList)
      }
      rs.getSavedObjects.toList
    }
    else {
      batch.map(x => {
        concernOverride match {
          case Some(c) => rs = collection.save(x, c)
          case None => rs = collection.save(x)
        }
        rs.getSavedObject
      })
    }
  }
}
