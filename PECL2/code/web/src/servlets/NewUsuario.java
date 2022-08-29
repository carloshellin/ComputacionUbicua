package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import logic.Log;
import logic.Logic;

/**
 * SERVLET QUE INSERTA UN USUARIO EN LA BASE DE DATOS
 */
@WebServlet("/NewUsuario")
public class NewUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public NewUsuario() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- New Usuario to DB--");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try 
		{
			String nombre = request.getParameter("nombre");
			String contrasena = request.getParameter("contrasena");
			
			int value =Logic.storeNewUsuario(nombre, contrasena);
			String jsonUsuario = new Gson().toJson(value);
			Log.log.info("JSON Values=> {}", jsonUsuario);
			out.println(jsonUsuario);
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