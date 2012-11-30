/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tly
 */


import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Client extends JFrame implements ActionListener,Runnable
{
    JButton b1,b2,b3,open,save,Fsend;
    JTextField tf2,tf3,tf1,tf4,tf5,tf6,tf8,tf10;

    Socket s;
    Dialog dialog,dialog1,dialogm,dialogc,dialogm1,dialogfs,dialogfs1,dialogfs10;
    Button ok,yes,no,ok1,C,okm,okc,okm1,okfs,okfs1,okfs10;
    Label label,label1,l1,l2,labelm,l3,l8,labelc,labeli,labelm1,labelfs,labelfs1,labelfs10;
    List jl1;
    Container cnt;
    BufferedReader br =null,br1= null;
    PrintWriter out1= null;
	DataOutputStream output = null;
	String line1 = null,line10=null;
	String str1,str3,id,clientid,str10,reqby,reqby1;
	Thread t;
	String s5="",s6="",test="",filename,sF;
	FileInputStream fin;
	Label ggp;
	public Client()
	{
	   try
	   {
		 ggp = new Label("CLIENT TO SERVER PROTOCOL");
		 ggp.setBounds(300,10,250,20);
		 InetAddress ia=InetAddress.getLocalHost();
	     String name=ia.getHostName();
		 System.out.println(name);
		 System.out.println(ia);
		 t = new Thread(this);
	   }
       catch(UnknownHostException a)
       {
        System.out.println("unknown host exception");
        System.exit(1);
       }
       catch(IOException b)
       {
        System.out.println("ioexception");
        System.exit(1);
       }
     cnt = this.getContentPane();
     cnt.setLayout(null);
     cnt.setBackground(Color.green);
     JLabel lb1 = new JLabel("Connect As :  ");
	 lb1.setBounds(30,30,120,50);
     JLabel lb2 = new JLabel("Enter Port Number : ");
	 lb2.setBounds(30,60,120,50);
     JLabel lb3 = new JLabel(" Send Messages To : ");
	 lb3.setBounds(30,90,125,50);
     JLabel lb4 = new JLabel("Message To Be Sent:");
	 lb4.setBounds(30,120,120,50);
     JLabel lb8 = new JLabel("Message Received  : ");
	 lb8.setBounds(30,150,120,50);

	 tf1 = new JTextField(10);
	 tf1.setBounds(170,45,170,20);
	 tf2=new JTextField(10);
	 tf2.setBounds(170,75,170,20);
     tf3 = new JTextField(10);
	 tf3.setBounds(170,105,170,20);
     tf4 = new JTextField(10);
	 tf4.setBounds(170,135,170,20);
     tf5 = new JTextField(10);
	 tf5.setBounds(170,205,170,20);
     tf6 = new JTextField(10);
     tf8 = new JTextField(10);
	 tf8.setBounds(170,165,170,20);
     b1 = new JButton("CONNECT");
     b1.setBounds(350,45,100,20);
     jl1 = new List(3,false);
	 jl1.setBounds(530,30,150,270);
     b3 = new JButton("SEND");
	 b3.setBounds(350,125,100,20);
	 b2 = new JButton("Exit");
	 b2.setBounds(400,280,100,20);

  
     cnt.add(ggp);
     cnt.add(lb1);   cnt.add(tf1);
     cnt.add(lb2);   cnt.add(tf2);
     cnt.add(lb3);   cnt.add(tf3);
     cnt.add(lb4);   cnt.add(tf4);
     cnt.add(lb8);   cnt.add(tf8);
                     cnt.add(tf5);
     cnt.add(jl1);
     cnt.add(b1);cnt.add(b2); cnt.add(b3);

     b1.addActionListener(this);
     validate();
    }//end of constructor
    public void actionPerformed(ActionEvent e)
    {
	
  
	if(e.getSource() == okfs)
	{
	 dialogfs.setVisible(false);
	}

	

	if (e.getSource() == b1)             //CONNECT
	{
	 try
	 {
      String name=JOptionPane.showInputDialog("Enter the IPaddress of server:");
	  String strport;
	  strport=tf2.getText();
	  int portnumber;
	  portnumber=Integer.parseInt(strport);
      s=new Socket(name,portnumber);
	  System.out.println("connected");
	 }
	 catch(Exception f)
	 {
		 System.out.println(f);
	 }
     str3 = tf1.getText();
	 System.out.println(" client id is"+str3);
     clientid = str3;
     str1 = str3+"^";
     //System.out.println(str1);
     if((tf1.getText()).equals(""))
     {
        dialog = new Dialog(this,"Message...",true);
        dialog.setLayout(new FlowLayout());
		dialog.setBackground(Color.white);
        dialog.setSize(200,100);
        label = new Label("You have to enter client ID ");
        dialog.add(label);
        ok = new Button("OK");
        ok.addActionListener(this);
        dialog.add(ok);
        dialog.setVisible(true);
     }
	 else
	 {
       try
       {
         out1=new PrintWriter(s.getOutputStream(),true);
         out1.println(str1);
         br=new BufferedReader(new InputStreamReader(s.getInputStream()));
         String validity = br.readLine();
         br=null;
         boolean v;
         v = validity.equals("present");
         System.out.println("VALIDITY : "+ validity);
         System.out.println("V :"+v);
         if( v==true)
         {
           tf1.setText("");
           dialogc = new Dialog(this,"Message...",true);
           dialogc.setLayout(new FlowLayout());
		   dialogc.setBackground(Color.white);
           dialogc.setSize(200,100);
           labelc = new Label("client ID  already present enter new one");
           dialogc.add(labelc);
           okc = new Button("OK");
           okc.addActionListener(this);
           dialogc.add(okc);
           dialogc.setVisible(true);
          }
          else
          {
             tf1.setEnabled(false);
			 tf2.setEnabled(false);
             out1.flush();
             //System.out.println(str1);
             b1.removeActionListener(this);
             b2.addActionListener(this);
             b3.addActionListener(this);
             t.start();
          }//end of else
       }//end of try within else
       catch(IOException b)
       {
         System.out.println("ae ioexception");
         System.exit(1);
       }
     }//end of else
    }//end of if (connect)

	if(e.getSource()==okc)
    {
     dialogc.setVisible(false);
     System.exit(1);
    }
    if(e.getSource()==b3) //b3=send          //Message Processing
    {
     try
     {
      out1=new PrintWriter(s.getOutputStream(),true);
      System.out.println(str1);
     }
     catch(IOException b)
     {
      System.out.println("ae ioexception");
     }
     str10 = tf3.getText();//destination
	 str3 = tf4.getText();//msg to be sent
     if(str3.equals(""))//if no message written in text field
     {
        System.out.println("Message cannot be processed");
        dialogm = new Dialog(this,"Message...",true);
        dialogm.setLayout(new FlowLayout());
		dialogm.setBackground(Color.white);
        dialogm.setSize(200,100);
		dialogm.setBackground(Color.white);
        labelm = new Label("You have to enter Message");
        dialogm.add(labelm);
        okm = new Button("    OK    ");
        okm.addActionListener(this);
        dialogm.add(okm);
        dialogm.setVisible(true);
     }//end of if
     else //if message written in text field
     {
        id=clientid+ "#" +str10;
        out1.println(id);
		System.out.println("Sending message between "+id);
        out1.flush();
        str1 = str3+"@";//str3= message to be sent
        //System.out.println("Button b3");
        out1.println(str1);
		System.out.println("message : "+str1);
        out1.flush();
        tf3.setText("");
        tf4.setText("");
        dialogm1 = new Dialog(this,"Message...",true);
        dialogm1.setLayout(new FlowLayout());
		dialogm1.setBackground(Color.white);
        dialogm1.setSize(200,100);
		dialogm1.setBackground(Color.white);
        System.out.println("Sending message to " +str10);
		labelm1 = new Label("Message is sent to "+str10);
		dialogm1.add(labelm1);
        okm1 = new Button("    OK    ");
        okm1.addActionListener(this);
        dialogm1.add(okm1);
        dialogm1.setVisible(true);
     }//end of else
    }//end of if (message processing)
    if(e.getSource()==okm1)
    {
      dialogm1.setVisible(false);
    }

    if(e.getSource()==okm)
    {
      dialogm.setVisible(false);
    }
 
    if(e.getSource() == b2)       //exit button
    {
     System.out.println(clientid+" Exiting");
     out1.println(clientid+"#"+clientid);
     //System.out.println(clientid);
     out1.println("Exit");
     System.exit(1);
    }
    if(e.getSource() == ok)
	{
      dialog.dispose();
    }
   }//end of action performed

  public void run()
  {
    //System.out.println("run method");
    jl1.add("Client's connected to server are : ");
    try
     {
       while(true)
       {
         br=new BufferedReader(new InputStreamReader(s.getInputStream()));
         //System.out.println("start of while");
		 line10=br.readLine();
		 //System.out.println("hello");
         //System.out.println("line10 : "+line10);
         int pos = line10.indexOf('#');
         reqby = line10.substring(0,pos);
         line1 = line10.substring(pos+1);
         int length = line1.length();
         //System.out.println(length +"length");
         char s;
         s=line1.charAt(length-1);
         //System.out.println(s );
         if(s=='^')       //adding the client name to the list box
         {
          	jl1.add(line1);
            //System.out.println("added");
            //System.out.println("out of rec");
            tf3.setText("");
            str1 = "";
         }
         else if(s=='@')                     //MESSAGE
         {
             tf4.setText("");
             tf8.setText(line1.substring(0,((line1.length())-1)));
             //System.out.println("rec message");
             System.out.println(line1);
          }
        

	  }//end of while
    }//end of try
    catch(IOException c)
	{
    }

  } //end of run method
  

}









/*long len;
try
{
   br1 = new BufferedReader(new FileReader(s5));


  }catch(Exception f){};
  System.out.println("Reading File " + s5);
  String r="";

  do
  {
  try
  {
  r = br1.readLine();

     System.out.println(r);

  }catch(Exception R){};
  if(r !=null)
  {
          try
         {
             out1=new PrintWriter(s.getOutputStream(),true);
             out1.println(r);
             out1.flush();


          }
            catch(IOException b)
            {
            System.out.println("ae ioexception");
           }


   }
  }while(r!=null);
  */