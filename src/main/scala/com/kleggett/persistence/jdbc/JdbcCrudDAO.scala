package com.kleggett.persistence.jdbc

import java.sql.PreparedStatement

import com.kleggett.db.util.ScalaSqlUtils._
import com.kleggett.persistence.{CrudDAO, Persistable}

/**
 * This trait provides the implementations for the basic CRUD operations using JDBC.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 4:32 PM)
 */
trait JdbcCrudDAO[ID, M <: Persistable[ID]] extends JdbcDAO[ID, M] with CrudDAO[ID, M]
{
  protected def insertSQL: String

  protected def updateSQL: String

  protected def deleteByIdSQL: String

  protected def getByIdSQL: String

  protected def prepInsert(obj: M): (PreparedStatement) => Unit

  protected def prepUpdate(obj: M): (PreparedStatement) => Unit

  protected def prepId(id: ID): (PreparedStatement) => Unit

  override def getById(id: ID): Option[M] = {
    executeQuery(connection, getByIdSQL, prepId(id), populate _).headOption
  }

  override def deleteById(id: ID): Int = {
    executeUpdate(connection, deleteByIdSQL, prepId(id))
  }

  override def save(obj: M, forceInsert: Boolean): M = {
    if (forceInsert || !obj.persisted) {
      val prepedObj = populateIdIfNeeded(obj)
      executeUpdate(connection, insertSQL, prepInsert(prepedObj))
    }
    else {
      executeUpdate(connection, updateSQL, prepUpdate(obj))
    }
    obj
  }
}
