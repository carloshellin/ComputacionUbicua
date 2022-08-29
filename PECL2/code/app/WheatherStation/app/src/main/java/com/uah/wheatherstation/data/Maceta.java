package com.uah.wheatherstation.data;

public class Maceta
{
    private int id;
    private String descripcion;
	private int usuario_id;
	private int tipo_id;

    // constructors
	public Maceta()
	{
		this.id = 0;
		this.descripcion = null;
		this.usuario_id = 0;
		this.tipo_id = 0;
	}
	public Maceta(int id, String descripcion, int usuario_id, int tipo_id)
	{
		this.id = id;
		this.descripcion = descripcion;
		this.usuario_id = usuario_id;
		this.tipo_id = tipo_id;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String name)
	{
		this.descripcion = descripcion;
	}

	public int getUsuario_id() { return usuario_id; }

	public void setUsuario_id(int usuario_id) { this.usuario_id = usuario_id; }

	public int getTipo_id() { return tipo_id; }

	public void setTipo_id(int tipo_id) { this.tipo_id = tipo_id; }
 }
