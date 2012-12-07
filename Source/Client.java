/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tly
 */

import java.util.Date;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener,Runnable
{
    JButton b1,b2,b3,b4,open,save,Fsend;
    JTextField tf2,tf3,tf1,tf4,tf5,tf6,tf8,tf10;

    Socket s;
    Dialog dialog,dialog1,dialogm,dialogc,dialogm1,dialogfs,dialogfs1,dialogfs10;
    FileDialog fd1,fd2;
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
     JLabel lb1 = new JLabel("Connect As:");
	 lb1.setBounds(30,30,120,50);
     JLabel lb2 = new JLabel("Enter Port Number:");
	 lb2.setBounds(30,60,120,50);
     JLabel lb3 = new JLabel("Send Messages To:");
	 lb3.setBounds(30,90,120,50);
     JLabel lb4 = new JLabel("Message To Be Sent:");
	 lb4.setBounds(30,120,127,50);
     JLabel lb8 = new JLabel("Message Received:");
	 lb8.setBounds(30,150,120,50);
     JLabel lb5 = new JLabel("File Request:");
	 lb5.setBounds(30,190,120,50);
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
     open = new JButton("OPEN");
	 open.setBounds(50,280,100,20);
     save = new JButton("SAVE");
     Fsend = new JButton("FILE SEND");
	 Fsend.setBounds(290,280,100,20);
     fd1 = new FileDialog(this,"Open File",FileDialog.LOAD);
     fd2 = new FileDialog(this,"Save File",FileDialog.SAVE);
     b4 = new JButton("FILEREQ");
	 b4.setBounds(170,280,100,20);
     cnt.add(ggp);
     cnt.add(lb1);   cnt.add(tf1);
	 cnt.add(lb2);   cnt.add(tf2);
     cnt.add(lb3);   cnt.add(tf3);
     cnt.add(lb4);   cnt.add(tf4);
     cnt.add(lb8);   cnt.add(tf8);
     cnt.add(lb5);   cnt.add(tf5);
     cnt.add(jl1);
     cnt.add(b1);cnt.add(b2); cnt.add(b3);cnt.add(b4);
	 cnt.add(Fsend); cnt.add(open);
     b1.addActionListener(this);
     validate();
    }//end of constructor
    public void actionPerformed(ActionEvent e)
    {
	 if(e.getSource() == open)
	 {
	   fd1.setVisible(true);
	   s5=fd1.getDirectory()+fd1.getFile();
	   tf5.setText(s5);
	 }
     //READING & SENDING THE FILE TO BE SENT
	 if (e.getSource() == Fsend)
	 {
		//str10 is dest. member address
		str10 = tf3.getText();
        if(str10.equals(""))
        {
          long len;
          try
          {
            out1.println(clientid+"#"+"");
			str3 =tf5.getText();
            br1 = new BufferedReader(new FileReader(str3));
          }
		  catch(Exception f){}
          System.out.println("Reading File \n" + str3);
          String r="";
          test="";
          System.out.println("READ:");
		  do
		  {
			 try
			 {
			  r = br1.readLine();//reading from file
			 }catch(Exception i){};
			 System.out.println(r);
			 System.out.println("\n");
			 if(r!=null)
             {
			  test = test+r;
			  //System.out.println("TEXT" +test);
			  //System.out.println("\n");
			 }

          }while(r!=null);
		  System.out.println("TEXT" +test);
		  System.out.println("\n");
          r=null;
		  //sending the read file contents
          out1.println(clientid+"#"+test);
		  System.out.println("SENT"+test);
          dialogfs = new Dialog(this,"Message...",true);
          dialogfs.setLayout(new FlowLayout());
		  dialogfs.setBackground(Color.white);
          dialogfs.setSize(300,200);
          labelfs = new Label("File sent to all the client's ");
          dialogfs.add(labelfs);
          okfs = new Button("OK");
          okfs.addActionListener(this);
          dialogfs.add(okfs);
          dialogfs.setVisible(true);
        }//end of inner if
        else
        {

             id=str10+ "#" +clientid;
             out1.println(id);
			 System.out.println("Requesting File From "+id);
             out1.flush();
			 str3 = tf5.getText();
			 str1 = str3+"!";
		     //System.out.println(str1);
	         //System.out.println("Button b4");
             out1.println(str1);
             out1.flush();
         }//end of else

     }//end of outer if
	if(e.getSource() == okfs)
	{
	 dialogfs.setVisible(false);
	}

	if(e.getSource() == save)
	{
	 fd2.setVisible(true);
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
	 System.out.println("client id is"+str3);
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
             b4.addActionListener(this);
             open.addActionListener(this);
             Fsend.addActionListener(this);
             save.addActionListener(this);
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
    if(e.getSource()==b4)            //File
    {
      str10 = tf3.getText();//msg sent to
      id=clientid+ "#" +str10;
      out1.println(id);
      System.out.println("Requesting File From "+id);
      out1.flush();
      str3 = tf5.getText();
      str1 = str3+"!";
      System.out.println(str1);
      //System.out.println("Button b4");
      out1.println(str1);
	  out1.flush();
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
         char c;
         c=line1.charAt(length-1);
         //System.out.println(s );
         if(c=='^')       //adding the Client name to the list box
         {
          	jl1.add(line1);
            //System.out.println("added");
            //System.out.println("out of rec");
            tf3.setText("");
            str1 = "";
         }
         else if(c=='@')                     //MESSAGE
         {
             tf4.setText("");
             tf8.setText(line1.substring(0,((line1.length())-1)));
             //System.out.println("rec message");
             System.out.println(line1);
          }
         else if(c=='!')                             //FILE READING
         {
           long len;
           try
           {
             br1 = new BufferedReader(new FileReader(line1.substring(0,((line1.length())-1))));
           }catch(Exception f){};
           System.out.println("Reading File " + line1.substring(0,((line1.length())-1)));
           String r="";
           test="";
           reqby1=clientid+"#"+reqby;
           out1.println(reqby1);
           System.out.println("REQBY1"+reqby1);
           out1.println("fxr");
           System.out.println("fxr");
           do
           {
             r = br1.readLine();//reading from file
             System.out.println("READ" +r);
             if(r!=null)
             {
               test = test+r;
               System.out.println("TEST" +test);
             }
            }while(r!=null);
            r=null;
            out1.println(reqby+"#"+test);      //sending the read file contents
            System.out.println("SENT"+reqby+"#"+test);
            dialogfs1 = new Dialog(this,"Message...",true);
            dialogfs1.setLayout(new FlowLayout());
	        dialogfs1.setBackground(Color.white);
            dialogfs1.setSize(200,100);
            labelfs1 = new Label("File Sent Successfully To "+reqby);
            dialogfs1.add(labelfs1);
            okfs1 = new Button("OK");
            dialogfs1.add(okfs1);
            okfs1.addActionListener(
            new ActionListener()
            {
             public void actionPerformed(ActionEvent z1)
             {
              if(z1.getSource()==okfs1)                                                                          if(z1.getSource() ==okfs1)
               {
                 dialogfs1.setVisible(false);
               }
             }
             });
             dialogfs1.setVisible(true);
         }
         else                    //SAVING FILE
         {
           dialog1=new Dialog(this,"FILE RECEIVED...",true);
           dialog1.setLayout(new FlowLayout());
		   dialog1.setBackground(Color.white);
           dialog1.setSize(200,100);
           label = new Label(" If you want to save file Click OK else Click Cancel");
           labeli=new Label("Received by CLIENTid : " +clientid);
           dialog1.add(label);
           dialog1.add(labeli);
           ok1 = new Button("    OK    ");
           C = new Button("CANCEL");
           int flen = line1.length();
           System.out.println("File Length : "+flen);
           Date d=new Date();
           int d1,y1,m1,h,m,s1;
           d1=d.getDate();   m1=1+d.getMonth();   y1=1900+d.getYear();
           h =d.getHours();m=d.getMinutes();s1=d.getSeconds();
           String dat,tim;
           dat=d1+" : "+m1+" : "+y1; tim=h+" : "+m+" : "+s1;
           System.out.println("current date: "+d1+" : "+m1+" : "+y1);
           System.out.println("current Time: "+h+" : "+m+" : "+s1);
           l1 = new Label("Date : " + dat);
           l2 = new Label("Time : " + tim+"    ");
           l3 = new Label("File Length   :   "+flen+" bytes ");
           dialog1.add(l1);
           dialog1.add(l2);
           dialog1.add(l3);
           dialog1.add(ok1);
           dialog1.add(C);
           ok1.addActionListener(
                           new ActionListener()
                           {
                               public void actionPerformed(ActionEvent z)
                                {

                                   if(z.getSource() ==ok1)
                                   {
                                        fd2.setVisible(true);

                                        sF=fd2.getDirectory()+fd2.getFile();
                                      //  tf5.setText(s5);

                                      filename=sF;
                                        System.out.println("creating new file"+filename);
                                        try{
                                         output= new DataOutputStream(new FileOutputStream(filename));
                                               System.out.println(line1);
                                               output.writeChars(line1);
                                               output.close();
                                               }catch(Exception da){};

                                           dialog1.dispose();
                                   }

                                }
                           }
                          );
          C.addActionListener(
                           new ActionListener()
                           {
                               public void actionPerformed(ActionEvent za)
                                {
                                   line1="";
                                   if(za.getSource() ==C)
                                   {
                                    dialog1.dispose();

                                   }

                                }
                           }
                          );
          dialog1.show();
         }
	   }//end of while
    }//end of try
    catch(IOException c)
	{
    }

  } //end of run method
public static void main(String args[])
{
  Client f = new Client();
  f.pack();
  f.setTitle("Client");
  f.setSize(700,350);
  f.setVisible(true);
}
}