package java_project;

import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Mcq {

//	private static final String connection = "";
//	private static final String dbclassname = "";
//	public static ResultSet 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String dbURL = "jdbc:mysql://localhost:3306/java_lab";
		String dbURL = "jdbc:mysql://root:YuAMQoRZCiD7n9ZrhA6D@containers-us-west-43.railway.app:7842/railway";
		String username = "root";
		String password = "YuAMQoRZCiD7n9ZrhA6D";
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\tStarted...");
		System.out.println("\tConnecting To DataBase...");
		 
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		 
		    Connection conn = DriverManager.getConnection(dbURL, username, password);
		    Statement stmt = conn.createStatement();
		 
		    if (conn != null) {
		        System.out.println("\tConnected To DataBase");
		    }
		    
		    ResultSet rs = stmt.executeQuery("SELECT * FROM `info`");
//	        while(((ResultSet) rs).next())
//	         {
//	        	 int id = rs.getInt("id");
//	        
//	        	 System.out.println("ID :- "+id);
//	         }
		    rs.next();
		    String pass = rs.getString("password");
	        
//	        	 System.out.println("ID :- "+id);
		    
	        Start s = new Start(conn,stmt,pass,sc);
	        s.run();
	        
	        System.out.println("\tExit..");
		    
		    
		        
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	}

}


class Start{
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Scanner sc;
	private String pass;
	private int score;
	private String userName;
	
	Start(Connection c,Statement s,String p,Scanner sc1)
	{
		conn = c;
		stmt = s;
		sc = sc1;
		pass = p;
		score = 0;
		userName = "";
		
	}
	
	private int getLastQueNo()
	{
		try {
			rs = stmt.executeQuery("SELECT * FROM `javap` ORDER BY id DESC LIMIT 1");
			rs.next();
		    int id = rs.getInt("id");
		    return id;
		    
		    
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	
	private int getLastScoreId()
	{
		try {
			rs = stmt.executeQuery("SELECT * FROM `highscore` ORDER BY id DESC LIMIT 1");
			rs.next();
		    int id = rs.getInt("id");
		    return id;
		    
		    
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	private int getHighScore()
	{
		try {
			rs = stmt.executeQuery("SELECT * FROM `highscore` ORDER BY score DESC LIMIT 1");
			rs.next();
		    int score = rs.getInt("score");
		    return score;
		    
		    
		}
		catch(Exception e)
		{
			return -999;
		}
	}
	
	private void insert()
	{
		String pasMatch;	
		
		
		
		System.out.println("Enter the password..");
		pasMatch =sc.next();
		
		if(pasMatch.equalsIgnoreCase(pass))
		{
			
			
			try {
				int id = getLastQueNo() + 1;
				
//				System.out.println("ID :- "+id);
				sc.nextLine();
				System.out.println("Enter the Que..");
				
				String que = sc.nextLine();
				String opt[] = {"","","","",""};
				for(int i=0;i<4;i++)
				{
					System.out.print("Enter the option "+(i+1)+" :- ");
					opt[i] = sc.nextLine();
				}
				System.out.print("Enter the Answer Option :- ");
				int ans = sc.nextInt();
				String sql = "INSERT INTO javap VALUES ("+id+", '"+que+"', '"+opt[0]+"','"+opt[1]+"','"+opt[2]+"','"+opt[3]+"',"+ans+",'"+userName+"')";
				stmt.executeUpdate(sql);
				System.out.println("Que is Successfully Inserted");
			    
			}
			catch(Exception e)
			{
				System.out.println("Error Occured "+e.getMessage());
			}
			
		}
		else {
			System.out.println("Permission Denied : You are Not Authorized To Insert");
		}
		System.out.println("Press Enter Key To Continue");
   	   sc.nextLine();
   	    sc.nextLine();
	}
	
	private void delet()
	{
	String pasMatch;	
		
		
		
		System.out.println("Enter the password..");
		pasMatch =sc.next();
		
		if(pasMatch.equalsIgnoreCase(pass))
		{
			
			
			try {
				
				
//				System.out.println("ID :- "+id);
				sc.nextLine();
				System.out.println("Enter the Que ID to delet :- ");
				
				int id = sc.nextInt();
				String sql = "DELETE FROM `javap` WHERE id="+id+"";
				stmt.executeUpdate(sql);
				System.out.println("Que is Successfully Deleted");
			    
			}
			catch(Exception e)
			{
				System.out.println("Error Occured "+e.getMessage());
			}
			
		}
		else {
			System.out.println("Permission Denied : You are Not Authorized To Delet");
		}
		System.out.println("Press Enter Key To Continue");
   	   sc.nextLine();
   	    sc.nextLine();
	}
	
	private void view()
	{
		try {
			  rs = stmt.executeQuery("SELECT * FROM `javap`");
			  int i=0;
			  System.out.println("The Que are as follows");
		         while(((ResultSet) rs).next())
		         {
		        	 
		        	 String que = rs.getString("que");
		        	 String addedBy = rs.getString("add_by");
		        	 int id = rs.getInt("id");
		        	
		        	 System.out.println((i+1)+") "+que);
		        	 System.out.println("Added By :- "+addedBy+ "  Id "+id);
		        	 i++;
		        	 
		        	 
		         }
		         System.out.println("Press Enter Key To Continue");
	        	 sc.nextLine();
	        	 sc.nextLine();
		}
		catch(Exception e)
		{
			 System.out.println("Unable To Retrive The Que Plz Try Again");
		}
	}
	
	private void play()
	{
		try {
			score=0;
			System.out.println("Enter The No Of Que To Play : - ");
			int n = sc.nextInt();
			  rs = stmt.executeQuery("SELECT * FROM `javap`");
			  int i=0;
			  System.out.println("Starting....");
		         while(((ResultSet) rs).next() && i<n)
		         {
		        	 
		        	 String que = rs.getString("que");
		        	 String op1 = rs.getString("opt1");
		        	 String op2 = rs.getString("opt2");
		        	 String op3 = rs.getString("op3");
		        	 String op4 = rs.getString("op4");
		        	 int ans = rs.getInt("ans");
		        	 
		        	 System.out.println((i+1)+") "+que);
		        	 System.out.println("1) "+op1+"\t2) "+op2);
		        	 System.out.println("3) "+op3+"\t4) "+op4);
		        	 System.out.print("Enter The Option :- ");
		        	 int a = sc.nextInt();
		        	 score = (a==ans) ? score+4 : score-1;
		        	 System.out.println();
		        	 
		        	 i++;
		        	 
		        	 
		         }
		         if(i<n)
		         {
		        	 System.out.println("Quetions Limit Crossed . New Question Will Be Added Soon");
		         }
		         System.out.println("Your Score is :- "+score);
//		         System.out.println("High Score is :- "+getHighScore());
		         if(score>=getHighScore())
		         {
		        	 System.out.println("Congratulation You Have Broke The High Score");
		        	 System.out.println("New High Score Is : "+score);
		         }
		         else
		         {
		        	 System.out.println("Oops! Better Luck Next Time");
		        	 System.out.println("High Score Is : "+getHighScore());
		         }
		         int scoreId = getLastScoreId() +1;
		         String sql = "INSERT INTO highscore VALUES("+scoreId+", '"+userName+"', "+score+")";
		         stmt.executeUpdate(sql);
		         System.out.println("Press Enter Key To Continue");
	        	 sc.nextLine();
	        	 sc.nextLine();
		}
		catch(Exception e)
		{
			 System.out.println("Unable To Retrive The Que Plz Try Again");
		}
	}
	
	private void highScoreDis()
	{
		try {
			
			
			rs = stmt.executeQuery("SELECT * FROM `highscore` ORDER BY score DESC LIMIT 5");
			System.out.println("\n\n\t\t High-Score \n");
		    int i=1;
		    System.out.printf("\tS.no  Name             Score\n\n");
			   while(((ResultSet) rs).next())
		         {
		        	 
				   int score = rs.getInt("score");
				    String name = rs.getString("name");
		        	
		        	 System.out.printf("\t%d     %-15s %4d\n",i,name,score);
		        	 
		        	 i++;
		        	 
		        	 
		         }
			   System.out.println("\n\n\tPress Enter Key To Continue");
	        	 sc.nextLine();
	        	 sc.nextLine();
	        	 
		    
		    
		}
		catch(Exception e)
		{
			System.out.println(" Network Issue Plz Try Again ");
		}
	}
	
	private void about()
	{
	try {
			
			
	    rs = stmt.executeQuery("SELECT * FROM `info`");

	    rs.next();
	    String made_by = rs.getString("made_by");
	    String created_at = rs.getString("created_at").replace("_","/");
	    String version = rs.getString("version");
	    String topic = rs.getString("topic");
	    String github_link = rs.getString("github_link");
	    System.out.println("\n\n\n\n\n");
	    System.out.println("\t  About Us");
	    System.out.println("\n\t Created By :- "+made_by);
	    System.out.println("\n\t Creation Date :- "+created_at);
	    System.out.println("\n\t Topic :- "+topic);
	    System.out.println("\n\t Version :- "+version);
	    System.out.println("\n\t Github Link :- "+github_link);
	    
	    
	    
	    
		System.out.println("\n\n\tPress Enter Key To Continue");
	        	 sc.nextLine();
	        	 sc.nextLine();
	        	 
		    
		    
		}
		catch(Exception e)
		{
			System.out.println(" Network Issue Plz Try Again ");
		}
	}
	
	public void run()
	{
		
		System.out.println("\t\tMCQ Database");
		System.out.print("\tEnter Your Name :- ");
		userName = sc.nextLine();
		do {
			System.out.println("\n\n\n\n\n");
			System.out.println("\t\tJava Mcq JDBC\n");
			System.out.println("\t 1 - Add Que");
			System.out.println("\t 2 - Delet Que");
			System.out.println("\t 3 - View Que");
			System.out.println("\t 4 - Play");
			System.out.println("\t 5 - High Score");
			System.out.println("\t 6 - About Us");
			System.out.println("\t 7 - Exit");
			System.out.print("\tEnter Your choice :- ");
			int a = sc.nextInt();
			switch(a)
			{
			case 1: insert();
				break;
			case 2: delet();
			break;
			case 3: view();
			break;
			case 4: play();
			break;
			case 5: highScoreDis();
			break;
			case 6: about();
			break;
			case 7: return;
		
				
			}
			
		}while(true);
		
	}
}
