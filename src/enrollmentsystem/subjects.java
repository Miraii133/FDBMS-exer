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
public class subjects {
    int id;
    String code;
    String desc;
    int units;
    String Sched;

DBConnect a = new DBConnect();
public void NewSubj(int subjid, String scode, String sdesc,int sunits, String sched){
    String query = "insert into subjects values(" + subjid + ",'" + scode + "','" + sdesc + "'," + sunits + ",'" + sched  + "')";
    try {
    a.st.executeUpdate(query);
   } catch (Exception ex) {
      System.out.println("Failed to insert" + ex);
   }    
} 
}
