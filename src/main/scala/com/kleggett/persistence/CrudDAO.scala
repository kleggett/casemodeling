package com.kleggett.persistence

/**
 * This trait defines the basic crud methods of a DAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/13/15 5:05 PM)
 */
trait CrudDAO[ID, M <: Persistable[ID] with HasCopy[ID, M]]
{
  def getById(id: ID): Option[M]

  def deleteById(id: ID): Int

  def save(obj: M, forceInsert: Boolean = false): M

  protected[persistence] def populateIdIfNeeded(obj: M): M = {
    obj.id match {
      case Some(_) => obj
      case None => obj.withId(generateId)
    }
  }

  protected def generateId: ID
}
