/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;
import java.sql.*;
import java.util.*;


/**
 *
 * @author roly_rf
 */
public class DBConnector {
    
    private String username;
    
    private static DBConnector instance = null;
    private Connection conn;
    public User authenticatedUser = null;
    
    public static DBConnector getInstance()
    {
        if(instance == null)
        {
            instance = new DBConnector();
        }
        
        return instance;
    }
    
    private DBConnector()
    {
        String url = "jdbc:mysql://academic-mysql.cc.gatech.edu/";
        String dbName = "cs4400_Group33";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "cs4400_Group33"; 
        String password = "7mJVoCfJ";
          
        conn = null; // JDBC Connection -- Chris
        try 
        {
              //System.out.println("Attempting to connect 1");
          Class.forName(driver).newInstance ();
          //System.out.println("Attempting to connect 2");
          conn = DriverManager.getConnection(url+dbName,userName,password);
          System.out.println("Connected to the database");
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }
    }
    
    public String login(String username, String password)
    {
        this.username = username;
        int count = 0;
        ResultSet rs = null;
        
        try{
            Statement st = conn.createStatement();
            System.out.println("Statement created");
            String query = "SELECT COUNT(*) FROM USER where USER.USERNAME = '";
            query += username;
            query += "' AND PASSWORD = '";
            query += password;
            query += "';";
            System.out.println(query);
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid
            System.out.println("Sent to DB");
            rs.first();
            count = rs.getInt(1);
            System.out.println("Results parsed");
        }
        catch(SQLException s){
            System.out.println("Failed to create a connection");
            s.printStackTrace(); // this is lazy. --chris
        }
        
        if(count > 0){ //if login is invalid, return the results empty
            authenticatedUser = new User();
            authenticatedUser.setUsername(username);
        }
        else
        {
           username = null;
        }
        
        return username;
    }
    
    public ArrayList<ESF> getAllESF()
    {
        ArrayList<ESF> results = new ArrayList<ESF>();
        String query = "SELECT DISTINCT NAME, DESCRIPTION FROM ESF";
        ResultSet rs = null;
        
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                ESF esf = new ESF(rs.getInt(1), rs.getString(2));
                results.add(esf);
                
            }

        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
        return results;
    }
    
        public ArrayList<String> getAllCostPer()
    {
        ArrayList<String> results = new ArrayList<String>();
        String query = "SELECT DISTINCT COST_PER from RESOURCE";
        ResultSet rs = null;
 
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                String cost = rs.getString(1);
                results.add(cost);
                
            }

        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
        return results;
    }
    
    
    public ArrayList<Incident> incidentsDescAndId()
    {
        String query = "SELECT DISTINCT INCIDENT_ID, DESCRIPTION FROM INCIDENT;";
        ResultSet rs = null;
        ArrayList<Incident> results = new ArrayList<Incident>();
        
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Incident inc = new Incident(rs.getInt(1) , rs.getString(2));
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
        
        return results;
    }
    
        public boolean insertResource(String name, int esf, String secondaryESFs, String model, String capabilities, Float lat, Float lon, int cost, String cost_per)
    {
        
        
        /*String name, 
         * int esf, 
         * String secondaryESFs, 
         * String model, 
         * String capabilities, 
         * Float lat, 
         * Float lon, 
         * String cost_per
         * 
         */
        
        String query = "INSERT INTO RESOURCES ";
        query += "(NAME, MODEL, COST, LATITUDE, LONGITUDE, PRIMARY_ESF, OWNER, COST_PER) ";
        query += "VALUES( ";
        query += name + ", ";
        query += model + ", ";
        query += cost + ", ";
        query += lat + ", ";
        query += lon + ", ";
        query += esf + ", ";
        query += username + ", ";
        
        
        
        
        

        ResultSet rs = null;
        ArrayList<Incident> results = new ArrayList<Incident>();
        
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Incident inc = new Incident(rs.getInt(1) , rs.getString(2));
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
            return false;
        }
        
        return true;
    }
    
    public ResultSet findResources(String keyWordField, int selectedEsfNum)
    {
        String query = "SELECT res.RESOURCEID FROM ";
        query += "RESOURCE as res ";
        query += "JOIN SECONDARY_ESF as esf ON res.RESOURCE_ID = esf.RESOURCE_ID ";
        query += "JOIN RESOURCE_CAPABILITIES as cap ON res.RESOURCE_ID = cap.RESOURCE_ID ";
        query += "WHERE ";
        
        
        //assembling WHERE clause below
        
        if (!keyWordField.equals("")){
            //Add the keyword to the WHERE clause
            query += "res.NAME LIKE '%" + keyWordField + "%' OR ";
            query += "res.model LIKE '%" + keyWordField + "%' OR ";
            query += "cap.CAPABILITY LIKE '%" + keyWordField + "%' AND";
        }
        
        if (selectedEsfNum != 0){
            query += "res.PRIMARY_ESF = " + selectedEsfNum + " OR ";
            query += "esf.NUMBER = " + selectedEsfNum + " AND ";
        }
        
        query += "TRUE;";
        
        ResultSet rs = null;
                
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); //resultset rs will contain 1 if the username+pass is valid

            
            if(true) // IF the distance entered is "", don't do this. Writeme
            {
                rs.beforeFirst();
                while (rs.next()){
                    getResourceLatLongProp(rs.getInt(1));
                }
            }
            
        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
        finally{
            return rs;
        }
    }
    
    public void getResourceLatLongProp(int resourceId)
    {
        try
        {
           
            Statement st = conn.createStatement();
            String askForResourceLoc = "SELECT LATITUDE, LONGITUDE from RESOURCE where RESOURCE_ID = " + resourceId;

            ResultSet distance = st.executeQuery(askForResourceLoc);
        }
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
    }
    
    
    public ArrayList<Resources> getResourceStatusInuse(String username){
        String query = "SELECT RES.RESOURCE_ID, RES.NAME,  INC.DESCRIPTION, RES.OWNER FROM RESOURCE RES JOIN REQUEST REQ ON RES.RESOURCE_ID = RES.RESOURCE_ID JOIN INCIDENT INC ON REQ.INCIDENT_ID = INC.INCIDENT_ID WHERE REQ.STATUS = 'ACTIVE' AND INC.DECLARED_BY = "+ username + ";";
        ArrayList<Resources> results = new ArrayList<Resources>();
        ResultSet rs = null;
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); 
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Resources inc = new Resources();
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); 
        }
        return results;
    }
    
        public ArrayList<Resources> getResourceStatusRequested(String username){
        String query = "SELECT RES.RESOURCE_ID, RES.NAME,  INC.DESCRIPTION, RES.OWNER FROM RESOURCE RES JOIN REQUEST REQ ON RES.RESOURCE_ID = REQ.RESOURCE_ID JOIN INCIDENT INC ON REQ.INCIDENT_ID = INC.INCIDENT_ID WHERE REQ.STATUS = 'REQUESTED' AND INC.DECLARED_BY = " + username + ";" ;
        ArrayList<Resources> results = new ArrayList<Resources>();
        ResultSet rs = null;
        
        try{
            Statement sat = conn.createStatement();
            rs = sat.executeQuery(query); 
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Resources inc = new Resources();
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); 
        }
        return results;
    }
        
         public ArrayList<Resources> getResourceStatusRecieved(String username){
             ArrayList<Resources> results = new ArrayList<Resources>();
             ResultSet rs = null;
             String query = "SELECT RES.RESOURCE_ID, RES.NAME,  INC.DESCRIPTION, RES.OWNER FROM RESOURCE RES JOIN REQUEST REQ ON RES.RESOURCE_ID = RES.RESOURCE_ID JOIN INCIDENT INC ON REQ.INCIDENT_ID = INC.INCIDENT_ID WHERE REQ.STATUS = 'ACTIVE' AND INC.DECLARED_BY = "+ username + ";";
        
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); 
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Resources inc = new Resources();
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); 
        }
        return results;
    }
         
    public ArrayList<Resources> getTotalResources(String username){
        ArrayList<Resources> results = new ArrayList<Resources>();
        ResultSet rs = null;
        String query = "CREATE VIEW V1 AS SELECT RES.PRIMARY_ESF, F.DESCRIPTION COUNT (*) AS TOTALRES FROM ESF F LEFT OUTER JOIN RESOURCE AS RES WHERE RES.OWNER =" + username + " GROUP BY PRIMARY_ESF, DESCRIPTION;" ;
        
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); 
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Resources inc = new Resources();
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); 
        }
        return results;
    }     
    
    public ArrayList<Resources> getResourcesInUse(String username)
    {
        ArrayList<Resources> results = new ArrayList<Resources>();
        ResultSet rs = null; 
        String query = "CREATE VIEW V2 AS SELECT RES.PRIMARY_ESF, F.DESCRIPTION , COUNT (*) AS INUSETOT FROM ((ESF F LEFT OUTER JOIN RESOURCE RES) NATURAL JOIN REQUEST REQ) WHERE RES.OWNER = " + username + " AND REQ.STATUS = 'ACTIVE' GROUP BY PRIMARY_ESF, DESCRIPTION;";
        
             try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query); 
            
            
            rs.beforeFirst();
            
            while(rs.next()){
             
                Resources inc = new Resources();
                results.add(inc);
            }

        }
        catch(SQLException s){
            s.printStackTrace(); 
        }
        return results;
    }

    public void insertIncident(String myDate, String description, float lat, float longitude)
    {
        
        try
        {           
            Statement st = conn.createStatement();
            String insertMyIncid = "INSERT INTO INCIDENT (IncidentDT, DESCRIPTION, LATITUDE, LONGITUDE, DECLARED_BY) VALUES( '" + myDate + "', '"+ description +"' , " + lat +" , " + longitude + " , '" + username + "' );";
            
            System.out.print(insertMyIncid);
            st.executeUpdate(insertMyIncid);
        }
        
        catch(SQLException s){
            s.printStackTrace(); // this is lazy. --chris
        }
    }
    
    public String getUserName(){
        return username;
    }
    
 
}

