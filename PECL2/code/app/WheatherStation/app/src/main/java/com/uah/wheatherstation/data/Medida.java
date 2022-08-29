package com.uah.wheatherstation.data;

import java.util.Date;

public class Medida
{
    private int id;
    private Date fecha;
    private double valor;
    private int sensor_id;

    public Medida(int id, Date fecha, double valor, int sensor_id)
    {
        this.id = 0;
        this.fecha = null;
        this.valor = 0;
        this.sensor_id = 0;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public int getSensor_id()
    {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id)
    {
        this.sensor_id = sensor_id;
    }

}

