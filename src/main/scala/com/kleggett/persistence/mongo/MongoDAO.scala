package com.kleggett.persistence.mongo

import com.fasterxml.jackson.databind.ObjectMapper
import com.kleggett.persistence.Persistable
import com.mongodb.WriteConcern
import com.mongodb.casbah.MongoDB
import org.mongojack.{DBCursor, DBQuery, JacksonDBCollection}

/**
 * This trait marks a DAO that is using MongoDB for persistence.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 2:26 PM)
 */
trait MongoDAO[ID, M <: Persistable[ID]]
{
  val collection: JacksonDBCollection[M, ID]

  def db: MongoDB

  def collectionName: String

  def concernOverride: Option[WriteConcern] = None

  def drop() = collection.drop()
}

object MongoDAO
{
  def dbCollection[ID, M <: Persistable[ID]](db: MongoDB, collectionName: String, modelClass: Class[M], keyClass: Class[ID])
                                            (implicit objMapper: ObjectMapper): JacksonDBCollection[M, ID] = {
    JacksonDBCollection.wrap(db(collectionName).underlying, modelClass, keyClass, objMapper)
  }

  def results[M](rs: DBCursor[M]): List[M] = {
    var results: List[M] = Nil
    while (rs.hasNext) results = rs.next() :: results
    results.reverse
  }

  def queryByName[M <: Persistable[_]](coll: JacksonDBCollection[M, _], name: String, fieldName: String = "name"): Option[M] = {
    Option(coll.findOne(DBQuery.is(fieldName, name)))
  }

  def queryById[ID, M <: Persistable[ID]](coll: JacksonDBCollection[M, ID], id: ID): Option[M] = {
    Option(coll.findOneById(id))
  }

  def deleteById[ID, M <: Persistable[ID]](coll: JacksonDBCollection[M, ID], id: ID) = {
    coll.removeById(id)
  }
}
