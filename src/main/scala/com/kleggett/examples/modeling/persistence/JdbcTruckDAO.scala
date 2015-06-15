package com.kleggett.examples.modeling.persistence

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.kleggett.db.util.ScalaSqlUtils._
import com.kleggett.examples.modeling.model.Truck

/**
 * Example implementation of the JdbcCrudDAO.
 *
 * @author K. Leggett
 * @since 1.0 (6/14/15 5:33 PM)
 */
class JdbcTruckDAO(override val connection: Connection)
  extends JdbcVehicleDAO[Truck]
{
  override protected val insertSQL =
    """insert into trucks(vin, make, model, weight, num_wheels)
      |values (?, ?, ?, ?, ?)
    """.stripMargin

  override protected val updateSQL =
    """update trucks set
          |make = ?,
          |model = ?,
          |weight = ?,
          |num_wheels = ?
      |where vin = ?
    """.stripMargin

  override protected val deleteByIdSQL = "delete from trucks where vin = ?"

  override protected val getByIdSQL = "select * from trucks where vin = ?"

  override protected def populate(rs: ResultSet): Truck = {
    Truck(Option(rs.getString("vin")), rs.getString("make"), rs.getString("model"),
          rs.getInt("weight"), rs.getInt("num_wheels"))
  }

  override protected def prepInsert(t: Truck): (PreparedStatement) => Unit = {
    (ps: PreparedStatement) => {
      ps.setString(1, t.id)
      ps.setString(2, t.make)
      ps.setString(3, t.model)
      ps.setInt(4, t.weight)
      ps.setInt(5, t.numWheels)
    }
  }

  override protected def prepUpdate(t: Truck): (PreparedStatement) => Unit = {
    (ps: PreparedStatement) => {
      ps.setString(1, t.make)
      ps.setString(2, t.model)
      ps.setInt(3, t.weight)
      ps.setInt(4, t.numWheels)
      ps.setString(5, t.id)
    }
  }

  override protected def prepId(id: String): (PreparedStatement) => Unit = prepSingleString(id)
}
