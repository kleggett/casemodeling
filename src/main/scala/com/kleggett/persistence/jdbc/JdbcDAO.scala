package com.kleggett.persistence.jdbc

import java.sql.{Connection, ResultSet}

import com.kleggett.persistence.Persistable

/**
 * This trait marks a DAO that is using JDBC for persistence.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 2:27 PM)
 */
trait JdbcDAO[ID, M <: Persistable[ID]]
{
  def connection: Connection

  protected def populate(rs: ResultSet): M

  def results(rs: ResultSet): List[M] = {
    var results: List[M] = Nil
    while (rs.next()) results = results :+ populate(rs)
    results
  }
}