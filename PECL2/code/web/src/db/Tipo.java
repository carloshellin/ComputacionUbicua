package db;

public class Tipo 
{
	private int id;
	private String nombre;
	private String descripcion;
	private int luzMax;
	private int luzMin;
	private int aguaMax;
	private int aguaMin;
	private int humedadMax;
	private int humedadMin;
	
	public Tipo()
	{
		this.id = 0;
		this.nombre = null;
		this.descripcion = null;
		this.luzMax = 0;
		this.luzMin = 0;
		this.aguaMax = 0;
		this.aguaMin = 0;
		this.humedadMax = 0;
		this.humedadMin = 0;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getNombre() 
	{
		return nombre;
	}
	
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public String getDescripcion() 
	{	
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public int getLuzMax() 
	{
		return luzMax;
	}
	
	public void setLuzMax(int luzMax) 
	{
		this.luzMax = luzMax;
	}
	
	public int getLuzMin() 
	{
		return luzMin;
	}
	
	public void setLuzMin(int luzMin) 
	{
		this.luzMin = luzMin;
	}
	
	public int getAguaMax() 
	{
		return aguaMax;
	}
	
	public void setAguaMax(int aguaMax) 
	{
		this.aguaMax = aguaMax;
	}
	
	public int getAguaMin() 
	{
		return aguaMin;
	}
	
	public void setAguaMin(int aguaMin) 
	{
		this.aguaMin = aguaMin;
	}
	
	public int getHumedadMax() 
	{
		return humedadMax;
	}
	
	public void setHumedadMax(int humedadMax) 
	{
		this.humedadMax = humedadMax;
	}
	
	public int getHumedadMin() 
	{
		return humedadMin;
	}
	
	public void setHumedadMin(int humedadMin) 
	{
		this.humedadMin = humedadMin;
	}
}
