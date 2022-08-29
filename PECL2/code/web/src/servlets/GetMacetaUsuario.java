package servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
 * SERVLET QUE BUSCA LA MACETA DE UN USUARIO ALMACENADAS EN BASE DE DATOS
 */
@WebServlet("/GetMacetaUsuario")
public class GetMacetaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetMacetaUsuario() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Maceta information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try 
		{
			int id = Integer.parseInt(request.getParameter("id"));
			int usuario_id = Integer.parseInt(request.getParameter("usuario_id"));
			
			Maceta value =Logic.getMacetaFromUsuario(id, usuario_id);
			String jsonMaceta = new Gson().toJson(value);
			Log.log.info("JSON Values=> {}", jsonMaceta);
			out.println(jsonMaceta);
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
