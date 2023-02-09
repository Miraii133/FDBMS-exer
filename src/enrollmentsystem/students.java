/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

/**
 *
 * @author User1
 */
public class students {
   int studid;
   String studname;
   String studadd;
   String studcrs;
   String studgender;
   String yrlvl;
   
  DBConnect a = new DBConnect(); 
  DBConnect b = new DBConnect(); 
 
  public void AddStud(int id, String name, String addr, String course, String gender, String yr){ 
 
    String query = "insert into students values(" + id + ",'" + name + "','" + addr + "','" + course + "','" + gender + "','" + yr + "')";

        try {
            a.st.executeUpdate(query);
            System.out.println("Insert success!!!");
            
        } catch (Exception ex) {
            System.out.println("Failed to insert");
        }
    
    
    
    
  }
  
    public String DelStud(String delete){ 
    String delusers="";
    if(delete.equals(";")){
    delete="";   
    }

   
        try {
            String query1="select concat(studid,studname) as x  from students where studid in (select studid from (select * from students " + delete + ") as x)";
            a.rs = a.st.executeQuery(query1);

            while (a.rs.next())
                {
                    String query2 = "drop user '" + a.rs.getString("x") + "'@'localhost'";
                    delusers +=a.rs.getString("x") + ",";
                    b.st.executeUpdate(query2);
                }
        } catch(Exception ex)
        {
            System.out.println(ex);
        }
        
   String query="delete from students where studid in (select studid from (select * from students " + delete + ") as x)";    
    
   try {
   a.st.executeUpdate(query);
   } catch (Exception ex) {
      System.out.println("Failed to Delete" + ex);
   }
  return delusers;
  }
    public void EditStud(String name, String addr, String course, String gender, String yr, String update){ 
    String query="";
    if(!name.equals("")){
        query = "update  students set studname='" + name + "' where studid in (select studid from (select * from students " + update + ") as x)";
    }
    if(!name.equals("") && !addr.equals("")){
        query = "update  students set studname='" + name + "',studadd='"+ addr +"' where studid in (select studid from (select * from students " + update + ") as x)";
    }
    
    try {
        if(query.equals(""))
          System.out.println("Query Empty");    
        else
            a.st.executeUpdate(query);
   } catch (Exception ex) {
      System.out.println("Failed to Update" + ex);
        System.out.println();
      
   }
    
   
  }
    
}
