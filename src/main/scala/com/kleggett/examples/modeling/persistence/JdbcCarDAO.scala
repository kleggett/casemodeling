package com.kleggett.examples.modeling.persistence

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.kleggett.db.util.ScalaSqlUtils._
import com.kleggett.examples.modeling.model.Car

/**
 * Example implementation of the JdbcCrudDAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:33 PM)
 */
class JdbcCarDAO(override val connection: Connection) extends JdbcVehicleDAO[Car]
{
  override protected val insertSQL =
    """insert into cars(vin, make, model, doors)
      |values (?, ?, ?, ?)
    """.stripMargin

  override protected val updateSQL =
    """update cars set
          |make = ?,
          |model = ?,
          |doors = ?
      |where vin = ?
    """.stripMargin

  override protected val deleteByIdSQL = "delete from cars where vin = ?"

  override protected val getByIdSQL = "select * from cars where vin = ?"

  override protected def populate(rs: ResultSet): Car = {
    Car(rs.getString("vin"), rs.getString("make"), rs.getString("model"), rs.getInt("doors"))
  }

  override protected def prepInsert(t: Car): (PreparedStatement) => Unit = {
    (ps: PreparedStatement) => {
      ps.setString(1, t.vin)
      ps.setString(2, t.make)
      ps.setString(3, t.model)
      ps.setInt(4, t.nDoors)
    }
  }

  override protected def prepUpdate(t: Car): (PreparedStatement) => Unit = {
    (ps: PreparedStatement) => {
      ps.setString(1, t.make)
      ps.setString(2, t.model)
      ps.setInt(4, t.nDoors)
      ps.setString(5, t.vin)
    }
  }

  override protected def prepId(id: String): (PreparedStatement) => Unit = prepSingleString(id)
}
