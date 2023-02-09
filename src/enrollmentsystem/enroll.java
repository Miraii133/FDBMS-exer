/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import static enrollmentsystem.StudentsForm.stdid;
import static enrollmentsystem.StudentsForm.subjid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;  
import javax.swing.JOptionPane;
/**
 *
 * @author User1
 */
public class enroll {
DBConnect db = new DBConnect();
     
     public int GetStudenteid(int studid, int subjid){
         // studenteid value defaults to 1 if there are no existing enrolled subjects
         int studenteid = 0;
         String studenteidSQL =
                 "SELECT enroll.eid FROM enroll, students, subjects WHERE students.studid=enroll.studid AND subjects.subjid=enroll.subjid AND students.studid='" + studid + "' ORDER BY enroll.eid ASC";
         try {
             ResultSet resultSet = db.st.executeQuery(studenteidSQL);
             
             while (resultSet.next() != false){
                   studenteid = Integer.parseInt(resultSet.getString("eid"));
             }
             studenteid++;
              
         } catch(SQLException ex){
             System.out.println("Could not get Studenteid");
            
         }
           
         return studenteid;
     }
     private boolean IsScheduleConflicting(String subjectToEnrollSchedule, List<String> existingSubjectsScheduleList){
         String day=subjectToEnrollSchedule.replaceAll("[0-9]+-[0-9]+$", "");  
         String removedWordsToTime= subjectToEnrollSchedule.replaceAll("[\\sa-zA-Z]", "");  
         
         String[] splitString = removedWordsToTime.split("-");
         int durationStart = Integer.parseInt(splitString[0]);
         int durationEnd = Integer.parseInt(splitString[1]);
         
         for (String subjectSchedules : existingSubjectsScheduleList){
             String subjectDay = subjectSchedules.replaceAll("[0-9]+-[0-9]+$", "");  
             String subjectTimeDuration = subjectSchedules.replaceAll("[\\sa-zA-Z]", ""); 
             String[] existingSubjectSplitString = subjectTimeDuration.split("-");
             int existingSubjectStart = Integer.parseInt(existingSubjectSplitString[0]);
             int existingSubjectEnd = Integer.parseInt(existingSubjectSplitString[1]);
             
             
             if (subjectDay.equalsIgnoreCase(day)){
                 System.out.println("To Enroll: " + durationStart + " " + durationEnd );
                 System.out.println("Existing: " + existingSubjectStart + " " + existingSubjectEnd);
                     if (durationStart <= existingSubjectStart && durationEnd >= existingSubjectEnd ||
                             durationStart <= existingSubjectEnd && durationEnd >= existingSubjectStart
                             
                             ){
                         System.out.println("Conflicting");
                         return true;
                 }
             }
         }
         return false;
     }
        
     private boolean GetSchedules(int studid, int subjIdOfSubjectToEnroll){
         
         String getSchedOfSubjectQuery = "SELECT subjsched FROM Subjects WHERE subjid = " + subjIdOfSubjectToEnroll + ";";
         String subjectSchedule = "";
         try {
           ResultSet resultSet = db.st.executeQuery(getSchedOfSubjectQuery);
           //if (!resultSet.isBeforeFirst()) return ;
             while (resultSet.next())
                {
                    subjectSchedule = resultSet.getString("subjsched");
                }
         } catch (SQLException ex){
                ex.printStackTrace();
         }
         
         String getSchedOfExistingSubjectsQuery = "SELECT Subjects.subjsched FROM Students, Subjects, Enroll WHERE Subjects.subjid = Enroll.subjid AND Students.studid = Enroll.studid AND Students.studid =" + studid + ";";
         List<String> existingSchedulesList=new ArrayList<>();  
         try {
             ResultSet resultSet = db.st.executeQuery(getSchedOfExistingSubjectsQuery);
             //if (!resultSet.isBeforeFirst()) return;
             while (resultSet.next())
                {
                    String schedule = resultSet.getString("subjsched");
                    existingSchedulesList.add(schedule);
                }
         } catch (SQLException ex){
             ex.printStackTrace();
         }
                
       return IsScheduleConflicting(subjectSchedule, existingSchedulesList);   
     }

    public void EnrollStud(int studid, int subjid){
        int studenteid = GetStudenteid(studid, subjid);
        
        // checks if conflicting schedules or not
        if (GetSchedules(studid, subjid)) return;
        String query = "insert into enroll values(" + studenteid + "," + studid + "," + subjid +")";
        try {
            
            db.st.executeUpdate(query);
            System.out.println("Insert success!!!");

        } catch (Exception ex) {
            System.out.println("Failed to insert");
            ex.printStackTrace();
        }


    }    
    public void DropSubj(int studid, int subjid){


        String query = "delete from enroll where studid=" + studid + " and subjid=" + subjid;
        try {
            db.st.executeUpdate(query);
            System.out.println("Delete success!!!");

        } catch (Exception ex) {
            System.out.println("Failed to Delete");
        }


    }     
}
