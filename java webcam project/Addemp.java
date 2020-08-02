import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JFrame;
import java.util.*;
import java.awt.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import java.awt.Dimension;
import java.io.File;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Dimension;

class Addemp extends JFrame{
Container c;
JButton btnSave;
JButton btnBack;
JLabel lblId;
JLabel lblName;
JLabel lblSal;
JTextField txtId;
JTextField txtName;
JTextField txtSal;
JButton btnCapture;
JButton btnPreview;

Addemp(){
c = getContentPane();
c.setLayout(new FlowLayout());
btnSave = new JButton("        Save         ");
btnBack = new JButton("        Back         ");
btnCapture = new JButton("        Capture Image        ");
btnPreview = new JButton("        Preview Image        ");

lblId = new   JLabel("ID:             ");
lblName = new JLabel("NAME:      ");
lblSal = new  JLabel("SALARY:  ");

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
c.add(btnCapture);
c.add(btnPreview);
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

// preview button
btnPreview.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
	ImageIcon i;
	File f = new File("profilepic.png");
	if(f.exists())
		i = new ImageIcon("profilepic.png");
	else
		i = new ImageIcon("noimg.png");
	JFrame preview = new JFrame("Image Preview");
	JButton ok = new JButton("   Ok   ");
	JLabel img = new JLabel("", i, SwingConstants.HORIZONTAL);
	preview.setLayout(new FlowLayout());
	ok.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae)
		{
			preview.dispose();
		}
		});
	preview.add(img);
	preview.add(ok);
	preview.setResizable(true);
	preview.setSize(700,600);
	preview.setLocationRelativeTo(null);
	preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	preview.setVisible(true);
	i = null;
}
}); 

//capture button
btnCapture.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		JFrame window = new JFrame("Image Capture");
		JButton cap = new JButton("Capture");
		window.setLayout(new FlowLayout());
		
		// code for cature
		cap.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae)
		{
		try{
		ImageIO.write(webcam.getImage(), "PNG", new File("profilepic.png"));
		JOptionPane.showMessageDialog(window,"Picture saved Successfully");
		window.dispose();
		}// end of try
		catch(Exception ex)
		{
		JOptionPane.showMessageDialog(window,"ERROR:\n"+ex);
		}// end of catch
		finally
		{
		webcam.close();
		}// end of finally
		}
		});
		window.add(panel);
		window.add(cap);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
}
});
 
//save button
btnSave.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
try{
String Id = txtId.getText();
String name = txtName.getText();
String Sal = txtSal.getText();
boolean hasChar = Sal.matches(".*\\d+.*");
boolean hasDigit = name.matches(".*\\d+.*");
File fi = new File("profilepic.png");
// validations 
if(Id.equals("") || name.equals("") || Sal.equals(""))
{
JOptionPane.showMessageDialog(c,"Enter all Details!");
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
else if(!Sal.matches("^[0-9]+"))
{
  JOptionPane.showMessageDialog(c,"Enter proper id!");
  txtId.requestFocus();
txtId.setText("");
}
else if(Integer.parseInt(Id)<0)
{
JOptionPane.showMessageDialog(c,"Id should be positive!");
txtId.requestFocus();
txtId.setText("");
}
else if(!fi.exists())
{
  JOptionPane.showMessageDialog(c,"Profile Image Not Taken!");
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
else
{
int id = Integer.parseInt(Id);
double sal = Double.parseDouble(Sal);
byte[] imageInBytes = new byte[(int)fi.length()];
FileInputStream inputStream = new FileInputStream(fi);
inputStream.read(imageInBytes);
inputStream.close();
// hibernate
Configuration cfg = new Configuration();
cfg.configure("hibernate.cfg.xml");
SessionFactory sfact = cfg.buildSessionFactory();
Session session = sfact.openSession();
Transaction t = null;
// adding entered details in database
try{
System.out.println("begin");
t = session.beginTransaction();
Employee e = new Employee();
e.setId(id); 
e.setName(name);
e.setSalary(sal);
e.setImage(imageInBytes);
session.save(e);
t.commit();
JOptionPane.showMessageDialog(c,"details inserted successfully!");
fi.delete();
txtId.requestFocus();
txtId.setText("");
txtName.requestFocus();
txtName.setText("");
txtSal.requestFocus();
txtSal.setText("");
}// end of try
catch(Exception e)
{
t.rollback();
JOptionPane.showMessageDialog(c,"ERROR:\n "+e);
}// end of catch
finally{
session.close();
}
}// end of else
}// end of try
catch(Exception ex)
{
JOptionPane.showMessageDialog(c,"Error:/n"+ex);
}
}
}); 
// setting size
setTitle("Add Employee");
setSize(300,300);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
public static void main(String args[])
{
Addemp a = new Addemp();
}//end of main()
}//end of class:javaproject