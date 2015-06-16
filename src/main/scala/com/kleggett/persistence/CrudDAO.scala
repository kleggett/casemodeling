package com.kleggett.persistence

/**
 * This trait defines the basic crud methods of a DAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/13/15 5:05 PM)
 */
trait CrudDAO[ID, M <: Persistable[ID]]
{
  def getById(id: ID): Option[M]

  def deleteById(id: ID): Int

  def save(obj: M, forceInsert: Boolean = false): M

  def saveBatch(batch: Traversable[M], forceInsert: Boolean = false): Traversable[M]

  def generateId: ID

  def populateIdIfNeeded(obj: M, id: () => ID): M
}
