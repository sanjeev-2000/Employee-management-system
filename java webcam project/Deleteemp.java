import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

class Deleteemp extends JFrame{
Container c;
JButton btnDelete;
JButton btnBack;
JLabel lblId;
JTextField txtId;


Deleteemp(){
c = getContentPane();
c.setLayout(new FlowLayout());
btnDelete = new JButton("Delete");
btnBack = new JButton("Back");

lblId = new JLabel("ID:          ");

txtId = new JTextField(20);

// adding components
c.add(lblId);
c.add(txtId);
c.add(btnDelete);
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

btnDelete.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Configuration cfg = new Configuration();
cfg.configure("hibernate.cfg.xml");
SessionFactory sfact = cfg.buildSessionFactory();
Session session = sfact.openSession();
Transaction t = null;
try{
String Id = txtId.getText();
if(Id.equals("") || Integer.parseInt(Id) < 0)
{
JOptionPane.showMessageDialog(c,"Enter Id, should be positive integer!");
txtId.requestFocus();
txtId.setText("");
}
else if(!Id.matches("^[0-9]+"))
{
  JOptionPane.showMessageDialog(c,"Enter proper id!");
  txtId.requestFocus();
txtId.setText("");
}
else
{
int id = Integer.parseInt(Id);
// deleting employee
t = session.beginTransaction();
Employee e = (Employee)session.get(Employee.class , id);
if(e != null)
{
session.delete(e);
JOptionPane.showMessageDialog(c,"Record deleted successfully!");
t.commit();
txtId.requestFocus();
txtId.setText("");
}// end of if
else
{
JOptionPane.showMessageDialog(c,"Record does not exist!");
}// end of else
}// end of else
}// end of try
catch(NumberFormatException nf)
{
JOptionPane.showMessageDialog(c,"ID field requires a positive integer value");
txtId.requestFocus();
txtId.setText("");
}
catch(Exception ex)
{
t.rollback();
JOptionPane.showMessageDialog(c,"Error:/n"+ex);
txtId.requestFocus();
txtId.setText("");
}
finally{
session.close();
}
}
}); 

// setting size
setTitle("Delete Employee");
setSize(300,300);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
public static void main(String args[])
{
Deleteemp a = new Deleteemp();
}//end of main()
}//end of class:javaproject