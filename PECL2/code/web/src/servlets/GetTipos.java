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

import db.Tipo;
import logic.Log;
import logic.Logic;

/**
 * SERVLET QUE BUSCA TODOS LOS TIPOS ALMACENADAS EN BASE DE DATOS
 */
@WebServlet("/GetTipos")
public class GetTipos extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetTipos() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Tipos information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try 
		{
				
			ArrayList<Tipo> values =Logic.getTipos();
			String jsonTipos = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonTipos);
			out.println(jsonTipos);
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