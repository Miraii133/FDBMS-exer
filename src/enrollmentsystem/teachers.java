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
public class teachers {
int Tid;
String Tname;
String TDept;
String TAddress;
String TContact;
String TStatus;
DBConnect a = new DBConnect();
public void NewTeacher(int id, String name, String dept,String addr, String contact, String status){
    String query = "insert into teachers values(" + id + ",'" + name + "','" + dept + "','" + addr + "','" + contact  + "','" + status + "')";

        String query2 = "create user if not exists '" + id+name + "'@'localhost' IDENTIFIED BY 't" + id+name + "'";
        String query3 = "grant select, update, insert on "+ a.db+".* to '" + id+name + "'@'localhost'";
        try {
            a.st.executeUpdate(query);
            System.out.println("Insert success!!!");
            a.st.executeUpdate(query2);
            a.st.executeUpdate(query3);
            System.out.println("user created");
            
        } catch (Exception ex) {
            System.out.println("Failed to insert");
        }

}



}
