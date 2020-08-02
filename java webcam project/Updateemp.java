import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

class Updateemp extends JFrame{
Container c;
JButton btnSave;
JButton btnBack;
JLabel lblId;
JLabel lblName;
JLabel lblSal;
JTextField txtId;
JTextField txtName;
JTextField txtSal;

Updateemp(){
c = getContentPane();
c.setLayout(new FlowLayout());
btnSave = new JButton("Save");
btnBack = new JButton("Back");

lblId = new JLabel("ID:          ");
lblName = new JLabel("NAME:     ");
lblSal = new JLabel("SALARY:  ");

txtId = new JTextField(20);
txtName = new JTextField(20);
txtSal = new JTextField(20);

// adding components
c.add(lblId);
c.add(txtId);
c.add(lblName);
c.add(txtName);
c.add(lblSal);
c.add(txtSal);
c.add(btnSave);
c.add(btnBack);

// action listener
// back button
btnBack.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
ems e = new ems();
dispose();
}
}); 

// save button
btnSave.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Configuration cfg = new Configuration();
cfg.configure("hibernate.cfg.xml");
SessionFactory sfact = cfg.buildSessionFactory();
Session session = sfact.openSession();
Transaction t = null;
try{
String Id = txtId.getText();
String name = txtName.getText();
String Sal = txtSal.getText();
boolean hasDigit = name.matches(".*\\d+.*");
if(Id.equals("") || name.equals("") || Sal.equals(""))
{
JOptionPane.showMessageDialog(c,"Enter all Details!");
}
else if(Integer.parseInt(Id)<0)
{
JOptionPane.showMessageDialog(c,"Id should be positive!");
txtId.requestFocus();
txtId.setText("");
}
else if(hasDigit)
{
JOptionPane.showMessageDialog(c,"Name field should not contain a digit or a number");
txtName.requestFocus();
txtName.setText("");
}
else if(name.length()<2)
{
JOptionPane.showMessageDialog(c,"Employee should be of minimum 2 characters");
txtName.requestFocus();
txtName.setText("");
}
else if(Integer.parseInt(Sal)<8000)
{
JOptionPane.showMessageDialog(c,"Salary should be greater than 8000!");
txtSal.requestFocus();
txtSal.setText("");
}
else if(!Id.matches("^[0-9]+"))
{
  JOptionPane.showMessageDialog(c,"Enter proper id!");
  txtId.requestFocus();
txtId.setText("");
}
else if(!Sal.matches("^[0-9]+"))
{
  JOptionPane.showMessageDialog(c,"Enter proper Salary!");
  txtSal.requestFocus();
txtSal.setText("");
}
else
{
int id = Integer.parseInt(Id);
double sal = Double.parseDouble(Sal);

// updating records
 
t = session.beginTransaction();
Employee emp = (Employee)session.get(Employee.class , id);
if(emp != null)
{
emp.setName(name);
emp.setSalary(sal);
session.save(emp);
t.commit();
JOptionPane.showMessageDialog(c,"Records Updated Successfully!");
txtId.requestFocus();
txtId.setText("");
txtName.requestFocus();
txtName.setText("");
txtSal.requestFocus();
txtSal.setText("");
}// end of if
else
{
JOptionPane.showMessageDialog(c,"Record Does not Exist!");
}// end of else
}// end of else
}// end of try
catch(NumberFormatException nf)
{
JOptionPane.showMessageDialog(c,"Enter Proper values: id/name/salary!");
}
catch(Exception ex)
{
JOptionPane.showMessageDialog(c,"Error:/n"+ex);
}
}
}); 

// setting size
setTitle("Update Employee");
setSize(300,300);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
public static void main(String args[])
{
Updateemp a = new Updateemp();
}//end of main()
}//end of class:javaproject