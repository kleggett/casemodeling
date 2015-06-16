package com.kleggett.persistence

/**
 * This trait marks those objects that can be persisted and indicates their current state.
 *
 * @author K. Leggett
 * @since 1.0 (6/8/15 8:47 PM)
 */
trait Persistable[ID <: Any]
{
  def id: Option[ID]

  def persisted: Boolean = id.isDefined
}
