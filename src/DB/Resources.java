/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author roly_rf
 */
public class Resources 
{
    private int id;
    private String namel;
    private String model;
    private float cost;
    private float latitude;
    private float longitude;
    private ESF esf;
    private User owner;
    private String Cost_per;

    public String getCost_per() {
        return Cost_per;
    }

    public void setCost_per(String Cost_per) {
        this.Cost_per = Cost_per;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public ESF getEsf() {
        return esf;
    }

    public void setEsf(ESF esf) {
        this.esf = esf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNamel() {
        return namel;
    }

    public void setNamel(String namel) {
        this.namel = namel;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public boolean AmIOwner(String id)
    {
        if(owner.getId().equals(id))
            return true;
        else
            return false;
    }
    
}
