import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import org.hibernate.*;
import org.hibernate.cfg.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Dimension;

class Viewemp extends JFrame{
Container c;

JButton btnBack;
JLabel lid;
JLabel lname;
JLabel lsalary;
JLabel limage;

Viewemp(){
c = getContentPane();

btnBack = new JButton("Back");
lid = new JLabel("ID");
lname = new JLabel("Name");
lsalary = new JLabel("Salary");
limage = new JLabel("Image");

// adding components
c.add(lid);
c.add(lname);
c.add(lsalary);
c.add(limage);

// retreiving contents from database
Configuration cfg = new Configuration();
cfg.configure("hibernate.cfg.xml");
SessionFactory sfact = cfg.buildSessionFactory();
Session session = sfact.openSession();
Transaction t = null;
try{
List<Employee> emp = new ArrayList<>();
int len = emp.size();
c.setLayout(new GridLayout(len,4));
emp = session.createQuery("from Employee").list();
for(Employee em: emp)
{
	c.add(new JLabel(""+em.getId()));
	c.add(new JLabel(""+em.getName()));
	c.add(new JLabel(""+em.getSalary()));
	byte[] getImageInBytes = em.getImage();
	System.out.println("Byte:"+getImageInBytes);
	File imageFile = new File("myImage.png");
	FileOutputStream outputStream = new FileOutputStream(imageFile);
	outputStream.write(getImageInBytes); // image write in "myImage.jpg" file 
	outputStream.close(); // close the output stream
	ImageIcon i = new ImageIcon("myImage.png");
	c.add(new JLabel("", i, SwingConstants.HORIZONTAL));
	imageFile.delete();
	
}
System.out.println("end");
}// end of try
catch(Exception ex)
{
JOptionPane.showMessageDialog(c,"ERROR:\n"+ex);
}// end of catch
finally{
session.close();
}
// finished retreiving contents

// action listener
// back button
btnBack.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
ems e = new ems();
dispose();
}
}); 

// adding components
c.add(btnBack);

// setting size
setTitle("View Employee");
setSize(300,300);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
public static void main(String args[])
{
Viewemp a = new Viewemp();
}//end of main()
}//end of class:javaproject