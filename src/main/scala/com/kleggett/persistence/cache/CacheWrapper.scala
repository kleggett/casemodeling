package com.kleggett.persistence.cache

/**
 * This is a wrapper around whatever caching implementation you choose to use.
 *
 * @author K. Leggett
 * @since 1.1 (6/13/15 5:21 PM)
 */
trait CacheWrapper[M]
{
  def name: String

  def get(key: Any): Option[M]

  def add(key: Any, value: M)

  def remove(key: Any): Boolean

  def clear()

  def keys: Set[Any]
}
