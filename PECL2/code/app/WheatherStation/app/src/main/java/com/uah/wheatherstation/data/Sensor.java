package com.uah.wheatherstation.data;

public class Sensor
{
    private int id;
    private String descripcion;
    private int minValue;
    private int maxValue;
    private String unidad;
    private int maceta_id;

    // constructors
    public Sensor()
    {
        this.id = 0;
        this.descripcion = null;
        this.minValue = 0;
        this.maxValue = 0;
        this.unidad = null;
        this.maceta_id = 0;
    }
    public Sensor(int id, String descripcion, int minValue, int maxValue, String unidad, int maceta_id)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unidad = unidad;
        this.maceta_id = maceta_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getMaceta_id() {
        return maceta_id;
    }

    public void setMaceta_id(int maceta_id) {
        this.maceta_id = maceta_id;
    }

}