package com.kleggett.persistence.mongo

import com.kleggett.persistence.Persistable
import com.kleggett.persistence.cache.DBCaches

/**
 * This trait extends the [[MongoCrudDAO]] with caching support.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 3:33 PM)
 */
trait CachedMongoCrudDAO[ID, M <: Persistable[ID]] extends MongoCrudDAO[ID, M]
{
  this: DBCaches[M] =>

  override def deleteById(id: ID): Int = {
    getById(id).foreach(x => removeObjectFromCaches(x))
    super.deleteById(id)
  }

  override def save(obj: M, forceInsert: Boolean): M = {
    val saved = super.save(obj, forceInsert)
    removeObjectFromCaches(saved)
    saved
  }

  override def drop() {
    super.drop()
    clearCaches()
  }

  def getCachedObjById(idCacheName: String, id: ID): Option[M] = cacheQuery(idCacheName, id, getById)

  protected def removeObjectFromCaches(obj: M)
}
