package com.uah.wheatherstation.data;

public class SensorType
{
    private int id;
    private String name;
    private String units;
    private int available;
    private int value;
    private int label; //0-low, 1-normal, 2-high

    // constructors
    public SensorType()
    {
        this.id = 0;
        this.name = null;
        this.units = null;
        this.available = 0;
        this.value = 0;
        this.label= 1;
    }
    public SensorType(int id, String name, String units, int available, int value, int label)
    {
        this.id = id;
        this.name = name;
        this.units = units;
        this.available = available;
        this.value = value;
        this.label= label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int isAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public int getLabel() {
        return label;
    }
    public void setLabel(int label) {
        this.label = label;
    }
    public int getAvailable() {
        return available;
    }
}