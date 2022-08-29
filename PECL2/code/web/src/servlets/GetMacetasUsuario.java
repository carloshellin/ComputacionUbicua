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

import db.Maceta;
import logic.Log;
import logic.Logic;

/**
 * SERVLET QUE BUSCA TODAS LAS MACETAS DE UN USUARIO ALMACENADAS EN BASE DE DATOS
 */
@WebServlet("/GetMacetasUsuario")
public class GetMacetasUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetMacetasUsuario() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Macetas information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try 
		{
			int usuario_id = Integer.parseInt(request.getParameter("usuario_id"));
			
			ArrayList<Maceta> values =Logic.geMacetasFromUsuario(usuario_id);
			String jsonMacetas = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonMacetas);
			out.println(jsonMacetas);
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
