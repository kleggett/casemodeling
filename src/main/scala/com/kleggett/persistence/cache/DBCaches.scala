package com.kleggett.persistence.cache

/**
 * A trait for mixing in caches to the persistence layer.
 *
 * @author K. Leggett
 * @since 1.0 (6/13/15 5:21 PM)
 */
trait DBCaches[M]
{
  protected val caches = initCaches()

  protected def initCaches(): Map[String, CacheWrapper[M]]

  def getFromCache(cacheName: String, key: Any): Option[M] = caches.get(cacheName).flatMap(c => c.get(key))

  def addToCache(cacheName: String, key: Any, value: M) {
    caches.get(cacheName).foreach(_.add(key, value))
  }

  def removeFromCache(cacheName: String, key: Any): Boolean = {
    caches.get(cacheName).exists(_.remove(key))
  }

  def clearCache(cacheName: String) {
    caches.get(cacheName).foreach(_.clear())
  }

  def clearCaches() {
    caches.values.foreach(_.clear())
  }

  def cacheQuery[K](cacheName: String, key: K, q: (K) => Option[M]): Option[M] = {
    getFromCache(cacheName, key).orElse({
      val result = q(key)
      result.foreach(addToCache(cacheName, key, _))
      result
    })
  }
}
