/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tly
 */

import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java .awt.event.*;

public class ServerLocal extends JFrame
{
 Date d;
 JTree mytree;
 DefaultMutableTreeNode line1,first,ssp ;
 DefaultTreeModel tree1;
 static String strservername="";
 JTextField textField;
 Container contentPane ;
 int i=-1,j;
 Vector ex=new Vector();
 ServerSocket me;
 Socket s,sser;
 PrintWriter out2 = null;
 BufferedReader in2 = null;
 static String reqby;
 ServerLocal.servertry t[] = new ServerLocal.servertry[50];
 String line="",linet="";
 boolean f1,bxr;
 public ServerLocal(ServerSocket s1)
 {
  me=s1;
  try
  {
   InetAddress iaser =me.getInetAddress();
   sser = new Socket(iaser,6060);
  }
  catch(Exception o)
  {
	try
	{
	 FileOutputStream fos7=new FileOutputStream("./ExceptionPage.txt",true);
	 fos7.write(" ".getBytes());
	 fos7.write(("\n The Exception Occured is "+o+" while connecting to the super server ").getBytes());
	 fos7.close();
	}
	catch(Exception e78)
	{
	}
  }
  contentPane = getContentPane();
  contentPane.setLayout(new BorderLayout());
  first = new DefaultMutableTreeNode("Server");
  tree1=new DefaultTreeModel(first);
  System.out.println("connected to super server");
  try
  {
   while(true)
   {
    s = me.accept();
    System.out.println("Client Connected to server");
    BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
    PrintWriter op=null;
    op=new PrintWriter(s.getOutputStream(),true);
	line=br.readLine();
    System.out.println("connection received from: "+line);
	ssp=new DefaultMutableTreeNode(line);
	mytree=new JTree(tree1);
	tree1.insertNodeInto(ssp,first,0);
    bxr=true;
    while(bxr)
	{
      //line=br.readLine();
      br = null;
      linet = line.substring(0,((line.length())-1));
      f1=false;
      System.out.println("SIZE OF EX : "+(ex.size()+1));
      for(int r =0;r<ex.size();r++)
      {
        f1 = ex.elementAt(r).equals(linet);
        System.out.println("value of f1 : "+f1);
        System.out.println("i :"+ex.elementAt(r));
        if(f1==true)
        r=ex.size();
      }
      if(f1==true)
      {
        op.println("present");
        bxr=false;
      }
      else
      {
        op.println("notpresant");
        i++;
        ex.addElement(linet);
        line1 = new DefaultMutableTreeNode(linet);
        mytree =new JTree(first);
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane jsp = new JScrollPane(mytree,v,h);
        contentPane.add(jsp,BorderLayout.CENTER);
        textField = new JTextField(" ",20);
        contentPane.add(textField,BorderLayout.SOUTH);
        setTitle("Server1");
        setSize(350,300);
        setVisible(true);
        //System.out.print(i);
        System.out.print("received: ");
        System.out.println(line);
        (t[i]=new ServerLocal.servertry(s,sser,t,line,ex,strservername)).start();
        bxr=false;
      }//else
    }      //inner while
   } //end of outer while
  }
  catch(Exception t)
  {
   try
   {
	 FileOutputStream fos8=new FileOutputStream("./ExceptionPage.txt",true);
     fos8.write(" ".getBytes());
     fos8.write(("\n The Exception Occured is "+t+" while connecting to the server ").getBytes());
     fos8.close();
   }
   catch(Exception e34)
   {
   }
  }
 }//end of constructor
 public static void main(String args[])
 {
  ServerSocket server;
  Socket ss;
  try
  {
	 BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
	 System.out.print("Enter port number : ");
	 String optind=in.readLine();
	 int portnumber = Integer.parseInt(optind);
	 System.out.print("Enter the server name :");
	 strservername=in.readLine();
	 System.out.println('\n');
	 System.out.println("enter super server IPaddress:");
	 String name=(new DataInputStream(System.in)).readLine();
	 System.out.println('\n');
	 System.out.println("Server listning  at port number :"+portnumber);
	 System.out.println("sever name is :"+strservername);
	 ss= new Socket(name,6060);
     PrintWriter out1=new PrintWriter(ss.getOutputStream(),true);
     out1.println(strservername);
     server = new ServerSocket(portnumber);
     ServerLocal f = new ServerLocal(server);
  }catch(Exception g){System.exit(1); }
 }//end of main


class servertry extends Thread
{
 PrintStream op=null,ops = null;
 Socket s,sser;
 servertry t[] =new servertry[50];
 String new1="",strsserver;
 DataOutputStream output=null;
 Vector ex =new Vector();

 String id,id1,line1,line10,reqbyx,idx;
 public servertry(Socket s,Socket sser ,servertry[] t,String new1,Vector ex,String strsserver)
 {
  this.s = s;
  this.sser = sser;
  this.t = t;
  this.new1 = new1;
  this.ex = ex;
  this.strsserver=strsserver;
  System.out.println(new1);
 }
 public void run()
 {
  //System.out.println("servertry run");
  int length1;
  try
  {
   BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
   op=new PrintStream(s.getOutputStream());
   System.out.println("new1 : "+new1);
   System.out.println("connected to server");
   FileOutputStream fos2=new FileOutputStream("c:\\log.txt",true);
   fos2.write(" ".getBytes());
   fos2.write(("\n The user "+new1+" connected to the server").getBytes());
   fos2.close();
   String line="";
   for(int j = 0;j<(ex.size()-1);j++)
   {
    try
	{
      t[j].sleep(200);
    }
	catch(Exception s)
	{
	  FileOutputStream fos3=new FileOutputStream("./ExceptionPage.txt",true);
      fos3.write(" ".getBytes());
   	  fos3.write(("\n The Exception Occured is "+s+" while connecting to the server ").getBytes());
      fos3.close();
	}
    if((ex.elementAt(j))!=new1)
	{
      op.println(ex.elementAt(j)+"#"+ex.elementAt(j)+'^');
    }
   }
   for(int j = 0;j<ex.size();j++)
   {
    if(t[j]!=null)
    {
     try
	 {
          t[j].sleep(200);
     }
	 catch(Exception s1)
	 {
	  FileOutputStream fos4=new FileOutputStream("./ExceptionPage.txt",true);
	  fos4.write(" ".getBytes());
	  fos4.write(("\n The Exception Occured is "+s1).getBytes());
	  fos4.close();
	  }
      t[j].op.println(new1+"#"+new1);
      //System.out.println("sending" +new1);
     }//end of if
    }//end of for
    while(true)
    {
	  System.out.println("The user is"+new1);
	  d=new Date();
      int d1,y1,m1,h,m2,s1;
      d1=d.getDate();   m1=1+d.getMonth();   y1=1900+d.getYear();
      h =d.getHours();m2=d.getMinutes();s1=d.getSeconds();
      String dat,tim;
      dat=d1+" : "+m1+" : "+y1; tim=h+" : "+m2+" : "+s1;
      System.out.println("current date: "+d1+" : "+m1+" : "+y1);
      System.out.println("current Time: "+h+" : "+m2+" : "+s1);
      line = br.readLine();
	  System.out.println("line"+line);
	  System.out.println("The message is  "+line);
	  length1 = line.length();
	  FileOutputStream fos=new FileOutputStream("c:\\log.txt",true);
      fos.write(" ".getBytes());
	  fos.write(("\n   The user :"+new1).getBytes());
      fos.write(("\n Sent the message. Length of the message is "+":"+length1+" Bytes").getBytes());
      fos.write("  ".getBytes());
      fos.write(("\nDate is "+dat).getBytes());
      fos.write("  ".getBytes());
 	  fos.write(("\nTime is "+tim).getBytes());
      fos.close();
      id1 = line;
      line = "";
      System.out.println(line);
      int pos=id1.indexOf('#');
      reqby= id1.substring(0,pos);
      id=id1.substring(pos+1);
      if(line.startsWith("/quit@"))
      break;
      if(id.equals(""))              //for broadcasting
      {
		System.out.println("value of id withen else: "+id);
		line = br.readLine();
	    System.out.println("2"+line);
        if(line.endsWith("@"))    //MESSAGE PROCESSING IN THIS IF LOOP
        {
         for(int j = 0;j<ex.size();j++)
         {
           System.out.println(reqby);
           line10=reqby+"#"+line;
           System.out.println(line10);
           t[j].op.println(line10);
          }   //for loop within if
        }
        else
        {
          if(line!=null)
          {
            System.out.println(line);
		    System.out.println("The file length is"+line.length());
			int l1=0;
			l1=line.length();
            FileOutputStream fos1=new FileOutputStream("./log.txt",true);
     		fos1.write(("\n The user "+new1+" is sending the file ").getBytes());
            fos1.write(("\n The length of the file is  :"+l1+" bytes").getBytes());
			fos1.close();
	        System.out.println(i);
            for(int j = 0;j<ex.size();j++)
            {
             t[j].op.println(line);
            }
            line="";
          }  //if line null
          line="";
           i=10;
        }//end of else
      }//end of broadcasting
      else                                        //for specific person
      {
	   System.out.println("value of id : "+id);
	   for(int i = 0;i<ex.size();i++)
       {
	    System.out.println("forloop");
	    System.out.println("comparing" +ex.elementAt(i) + "with" + id);
		boolean f = ex.elementAt(i).equals(id);
		if(f==true)
		{
		 System.out.println("F");
		 line = br.readLine();
		 System.out.println("2"+line);
         if(line.endsWith("@"))    //MESSAGE PROCESSING IN THIS IF LOOP
         {
           System.out.println(reqby+"MESSAGE");
           line10=reqby+"#"+line;
           System.out.println(line10);
           t[i].op.println(line10);
           line = "";
           i=10;
           System.out.println(i);
         }
         else if(line.endsWith("!"))       //FILE PROCESSING
         {
           System.out.println("FileRequested"+line+"FILE");
           line1=reqby+"#"+line;
           t[i].op.println(line1);          //sending the req person
           System.out.println(line1);
           line = "";
           i=10;
           System.out.println(i);
          }  //else if
         else
         {
           System.out.println("within else");
           System.out.println("do");
           line = br.readLine();
           System.out.println("3"+line);
           if(line!=null)
           {
           if(line!="Exit")
           {
              System.out.println(line);
              System.out.println(i);
              t[i].op.println(line);
              line="";
           }
           else
           {
             System.out.println(line);
             System.out.println(reqby);
             ex.removeElement(reqby);
             //ex.add(i,"logout");
             for(int m=0;m<ex.size();m++)
             {
               System.out.println(ex.elementAt(m));
              }
           }   //if exit
         }    //if line null
         System.out.println("exit from while");
         line="";
         i=10;
        } //end of else
     }//if
   }//for
  }
}//while

}
catch(Exception c)
{
 ex.removeElement(reqby);
 //ex.add(i,"logout");
 try
 {
   FileOutputStream fos6=new FileOutputStream("./ExceptionPage.txt",true);
   fos6.write(" ".getBytes()); //move to EOF
   fos6.write((" The Exception Occured is "+c).getBytes());
   fos6.close();
 }
 catch(Exception eq)
 {
 }
}
}
}
}