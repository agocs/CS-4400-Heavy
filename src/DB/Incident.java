/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author roly_rf
 */
public class Incident {
    private int incidentId;
    private String date;
    private String description;
    private float longitude;
    private float latitude;
    private String reported_by;
    
    public Incident(int id, String desc)
    {
        this.incidentId = id;
        this.description = desc;
    }
    
    public Incident(int id, String desc, String date, float lo, float la, String reported)
    {
        this(id, desc);
        
        //need to to add all other stuff here
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }
    
}
