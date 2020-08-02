import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;

class ems extends JFrame{
Container c;
JButton btnAdd;
JButton btnView;
JButton btnUpdate;
JButton btnDel;

ems(){
c = getContentPane();
c.setLayout(new FlowLayout());
btnAdd = new JButton("Add");
btnView = new JButton("View");
btnUpdate = new JButton("Update");
btnDel = new JButton("Delete");

// adding components
c.add(btnAdd);
c.add(btnView);
c.add(btnUpdate);
c.add(btnDel);

// setting size
setTitle("E.M.S");
setSize(250,300);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);

// action listener
// add button
btnAdd.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Addemp Ae = new Addemp();
dispose();
}
}); 

// View add 
btnView.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Viewemp ve = new Viewemp();
dispose();
}
}); 

// Update button
btnUpdate.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Updateemp ue = new Updateemp();
dispose();
}
}); 

// delete button
btnDel.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
Deleteemp de = new Deleteemp();
dispose();
}
}); 
}// end of constructor

public static void main(String args[])
{
ems a = new ems();
}//end of main()
}//end of class:javaproject