package com.kleggett.persistence

/**
 * This trait marks ''Persistable'' objects that can be copied.
 *
 * @author K. Leggett
 * @since 1.0 (8/19/15 8:32 PM)
 */
trait HasCopy[ID, +M <: Persistable[ID]]
{
  def withId(newId: ID): M
}
