/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author roly_rf
 */
public class ESF {
    private int name; 
    private String description;

    public ESF(int name, String desc)
    {
        this.description = desc;
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return ""+name+" -- "+description;
    }
    
}
