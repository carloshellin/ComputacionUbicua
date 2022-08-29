package db;

public class Topics 
{
    private String idTopic;
    private String idMaceta;
    private String idSensor;
    private String value;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idSensor = null;
    	this.setValue(null);
    }
    public Topics(String idTopic, String idSensor, String idAlert, String value) 
    {
    	this.idTopic = idTopic;
    	this.idSensor = idSensor;
    	this.setValue(value);
    }

	public String getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}

	public String getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getIdMaceta() {
		return idMaceta;
	}
	
	public void setIdMaceta(String idMaceta) {
		this.idMaceta = idMaceta;
	}
    
 }
