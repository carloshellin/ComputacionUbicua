package logic;

import java.util.ArrayList;

import db.ChartMeasurements;
import db.ConectionDDBB;
import db.Maceta;
import db.Medida;
import db.Sensor;
import db.Tipo;
import db.Topics;
import db.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logic 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @return El usuario almacenado en bd
	 */
	public static Usuario getUsuarioFromDB(String nombre, String contrasena)
	{
		Usuario usuario = new Usuario();
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetUsuario(con);
			ps.setString(1, nombre);
			ps.setString(2, contrasena);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			usuario.setId(rs.getInt("ID"));
			usuario.setNombre(rs.getString("NOMBRE"));
			usuario.setContrasena(rs.getString("CONTRASENA"));
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			usuario = new Usuario();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			usuario = new Usuario();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			usuario = new Usuario();
		} finally
		{
			conector.closeConnection(con);
		}
		return usuario;
	}
	
	public static int storeNewUsuario(String nombre, String contrasena)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertUsuario(con);
			ps.setString(1, nombre);
			ps.setString(2, contrasena);
			Log.log.info("Query to store Usuario=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			return -1;
		} finally
		{
			conector.closeConnection(con);
		}
		
		return 0;
	}
	
	/**
	 * 
	 * @return La lista de todas las macetas almacenadas en bd de un usuario
	 */
	public static ArrayList<Maceta> geMacetasFromUsuario(int usuario_id)
	{
		ArrayList<Maceta> macetas = new ArrayList<Maceta>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMacetasFromUsuario(con);
			ps.setInt(1, usuario_id);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Maceta maceta = new Maceta();
				maceta.setId(rs.getInt("ID"));
				maceta.setDescripcion(rs.getString("DESCRIPCION"));
				maceta.setUsuario_id(rs.getInt("USUARIO_ID"));
				maceta.setTipo_id(rs.getInt("TIPO_ID"));
				macetas.add(maceta);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			macetas = new ArrayList<Maceta>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			macetas = new ArrayList<Maceta>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			macetas = new ArrayList<Maceta>();
		} finally
		{
			conector.closeConnection(con);
		}
		return macetas;
	}
	
	/**
	 * 
	 * @return La maceta almacenada en bd de un usuario
	 */
	public static Maceta getMacetaFromUsuario(int id, int usuario_id)
	{
		Maceta maceta = new Maceta();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMacetaFromUsuario(con);
			ps.setInt(1, id);
			ps.setInt(2, usuario_id);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			maceta.setId(rs.getInt("ID"));
			maceta.setDescripcion(rs.getString("DESCRIPCION"));
			maceta.setUsuario_id(rs.getInt("USUARIO_ID"));
			maceta.setTipo_id(rs.getInt("TIPO_ID"));	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			maceta = new Maceta();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			maceta = new Maceta();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			maceta = new Maceta();
		} finally
		{
			conector.closeConnection(con);
		}
		return maceta;
	}
	
	public static int storeNewMaceta(String descripcion, int usuario_id, int tipo_id)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertMaceta(con);
			ps.setString(1, descripcion);
			ps.setInt(2, usuario_id);
			ps.setInt(3, tipo_id);
			Log.log.info("Query to store Maceta=> {}", ps.toString());
			ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int maceta_id = rs.getInt(1);
                Logic.storeNewSensor("agua", 0, 10, "cm", maceta_id);
                Logic.storeNewSensor("humedad en la tierra", 0, 100, "porcentaje", maceta_id);
                Logic.storeNewSensor("humedad en el ambiente", 0, 100, "porcentaje", maceta_id);
                Logic.storeNewSensor("temperatura", 0, 50, "grados", maceta_id);
            }
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			return -1;
		} finally
		{
			conector.closeConnection(con);
		}
		
		return 0;
	}
	
	public static int storeNewSensor(String descripcion, int minValue, int maxValue, String unidad, int maceta_id)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertSensor(con);
			ps.setString(1, descripcion);
			ps.setInt(2, minValue);
			ps.setInt(3, maxValue);
			ps.setString(4, unidad);
			ps.setInt(5, maceta_id);
			Log.log.info("Query to store Sensor=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			return -1;
		} finally
		{
			conector.closeConnection(con);
		}
		
		return 0;
	}
	
	public static int deleteMacetaFromUsuario(int id, int usuario_id)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.DeleteMacetaFromUsuario(con);
			ps.setInt(1, id);
			ps.setInt(2, usuario_id);
			Log.log.info("Query to delete Maceta=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			return -1;
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			return -1;
		} finally
		{
			conector.closeConnection(con);
		}
		
		return 0;
	}	
	
	/**
	 * 
	 * @return La lista de todos los sensores almacenadas en bd de una maceta
	 */
	public static ArrayList<Sensor> getSensoresFromMaceta(int maceta_id)
	{
		ArrayList<Sensor> sensores = new ArrayList<Sensor>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetSensoresFromMaceta(con);
			ps.setInt(1, maceta_id);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Sensor sensor = new Sensor();
				sensor.setId(rs.getInt("ID"));
				sensor.setDescripcion(rs.getString("DESCRIPCION"));
				sensor.setMinValue(rs.getInt("MINVALUE"));
				sensor.setMaxValue(rs.getInt("MAXVALUE"));
				sensor.setUnidad(rs.getString("UNIDAD"));
				sensor.setMaceta_id(rs.getInt("MACETA_ID"));
				sensores.add(sensor);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			sensores = new ArrayList<Sensor>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			sensores = new ArrayList<Sensor>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			sensores = new ArrayList<Sensor>();
		} finally
		{
			conector.closeConnection(con);
		}
		return sensores;
	}
	
	/**
	 * 
	 * @return La lista de todas medidas almacenadas en bd de un sensor
	 */
	public static ArrayList<Medida> getMedidasFromMaceta(int sensor_id)
	{
		ArrayList<Medida> medidas = new ArrayList<Medida>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMedidasFromSensor(con);
			ps.setInt(1, sensor_id);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Medida medida = new Medida();
				medida.setId(rs.getInt("ID"));
				medida.setFecha(rs.getDate("FECHA"));
				medida.setValor(rs.getDouble("VALOR"));
				medida.setSensor_id(rs.getInt("SENSOR_ID"));
				medidas.add(medida);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			medidas = new ArrayList<Medida>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			medidas = new ArrayList<Medida>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			medidas = new ArrayList<Medida>();
		} finally
		{
			conector.closeConnection(con);
		}
		return medidas;
	}
	
	/**
	 * 
	 * @return Un tipo almacenada en bd
	 */
	public static Tipo getTipo(int id)
	{
		Tipo tipo = new Tipo();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetTipo(con);
			ps.setInt(1, id);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			tipo.setId(rs.getInt("ID"));
			tipo.setNombre(rs.getString("NOMBRE"));
			tipo.setDescripcion(rs.getString("DESCRIPCION"));
			tipo.setLuzMax(rs.getInt("LUZMAX"));
			tipo.setLuzMin(rs.getInt("LUZMIN"));
			tipo.setAguaMax(rs.getInt("AGUAMAX"));
			tipo.setAguaMin(rs.getInt("AGUAMIN"));
			tipo.setHumedadMax(rs.getInt("HUMEDADMAX"));
			tipo.setHumedadMin(rs.getInt("HUMEDADMIN"));
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			tipo = new Tipo();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			tipo = new Tipo();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			tipo = new Tipo();
		} finally
		{
			conector.closeConnection(con);
		}
		return tipo;
	}
	
	/**
	 * 
	 * @return La lista de todos los tipos almacenadas en bd
	 */
	public static ArrayList<Tipo> getTipos()
	{
		ArrayList<Tipo> tipos = new ArrayList<Tipo>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetTipos(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Tipo tipo = new Tipo();
				tipo.setId(rs.getInt("ID"));
				tipo.setNombre(rs.getString("NOMBRE"));
				tipo.setDescripcion(rs.getString("DESCRIPCION"));
				tipo.setLuzMax(rs.getInt("LUZMAX"));
				tipo.setLuzMin(rs.getInt("LUZMIN"));
				tipo.setAguaMax(rs.getInt("AGUAMAX"));
				tipo.setAguaMin(rs.getInt("AGUAMIN"));
				tipo.setHumedadMax(rs.getInt("HUMEDADMAX"));
				tipo.setHumedadMin(rs.getInt("HUMEDADMIN"));
				tipos.add(tipo);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			tipos = new ArrayList<Tipo>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			tipos = new ArrayList<Tipo>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			tipos = new ArrayList<Tipo>();
		} finally
		{
			conector.closeConnection(con);
		}
		return tipos;
	}

	public static void storeNewMedida(Topics newTopic)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertMedida(con);
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ps.setString(1, sdf.format(timestamp));
			ps.setString(2, newTopic.getValue());
			ps.setString(3, newTopic.getIdSensor());
			ps.setString(4, sdf.format(timestamp));
			ps.setString(5, newTopic.getValue());
			ps.setString(6, newTopic.getIdSensor());
			Log.log.info("Query to store Medida=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
		} finally
		{
			conector.closeConnection(con);
		}
	}
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getLastWeekStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementLastDays(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			ps.setInt(3, 7); //ES:Number of Days to search //ES:Número de días a buscar
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("dayofweek")) {
					case 1:
						measurement.getLabels().add("Sunday " + rs.getString("date"));
					break;
					case 2:
						measurement.getLabels().add("Monday " + rs.getString("date"));
					break;
					case 3:
						measurement.getLabels().add("Tuesday " + rs.getString("date"));
					break;
					case 4:
						measurement.getLabels().add("Wednesday " + rs.getString("date"));
					break;
					case 5:
						measurement.getLabels().add("Thursday " + rs.getString("date"));
					break;
					case 6:
						measurement.getLabels().add("Friday " + rs.getString("date"));;
					break;
					case 7:
						measurement.getLabels().add("Saturday " + rs.getString("date"));
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getMonthStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementMonth(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("date")) {
					case 1:
						measurement.getLabels().add("Jan");
					break;
					case 2:
						measurement.getLabels().add("Feb");
					break;
					case 3:
						measurement.getLabels().add("Mar");
					break;
					case 4:
						measurement.getLabels().add("Apr");
					break;
					case 5:
						measurement.getLabels().add("May");
					break;
					case 6:
						measurement.getLabels().add("Jun");
					break;
					case 7:
						measurement.getLabels().add("Jul");
					break;
					case 8:
						measurement.getLabels().add("Aug");
					break;
					case 9:
						measurement.getLabels().add("Sep");
					break;
					case 10:
						measurement.getLabels().add("Oct");
					break;
					case 11:
						measurement.getLabels().add("Nov");
					break;
					case 12:
						measurement.getLabels().add("Dec");
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}	
}
