package com.kleggett.persistence

/**
 * This trait marks those objects that can be persisted.
 *
 * @author K. Leggett
 * @since 1.1 (6/8/15 8:47 PM)
 */
trait Persistable[A <: Any]
{
  def id: A

  def id_=(value: A): Unit

  def idAsOption: Option[A] = if (persisted) Some(id) else None

  /**
   * Returns true if this object is "persisted"; false otherwise.
   * @return true if the object is persisted; false if it is not
   */
  def persisted: Boolean

  def idEquals(other: Any) = other match {
    case that: Persistable[A] => this.id == that.id
    case _ => false
  }

  def idHashCode() = if (idAsOption.isDefined) id.hashCode() else 0
}
