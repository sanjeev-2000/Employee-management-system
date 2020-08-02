class Employee{
private int id;
private String name;
private double salary;
private byte[] image;

public int getId(){
return id;
}

public void setId(int id)
{
this.id = id;
}

public String getName()
{
return name;
}
public void setName(String name)
{
this.name = name;
}

public double getSalary()
{
return salary;
}
public void setSalary(double salary)
{
this.salary = salary;
}

public byte[] getImage()
{
   return image;
}
public void setImage( byte[] image )
{
   this.image = image;
}

}