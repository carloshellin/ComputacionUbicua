package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import db.Medida;
import logic.Log;
import logic.Logic;

/**
 * SERVLET QUE BUSCA TODOS LAS MEDIDAS DE UNA MACETA ALMACENADAS EN BASE DE DATOS
 */
@WebServlet("/GetMedidasMaceta")
public class GetMedidasMaceta extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetMedidasMaceta() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Sensores information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try 
		{
			int sensor_id = Integer.parseInt(request.getParameter("sensor_id"));
			
			ArrayList<Medida> values =Logic.getMedidasFromMaceta(sensor_id);
			String jsonMedidas = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonMedidas);
			out.println(jsonMedidas);
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
