package repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import domain.Doctor;
import domain.Pharmacist;
import domain.Section;

import exception.GenE;
public class LoginRepository {
	static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String url = "jdbc:sqlserver://SIN-PC\\SIN;databaseName=SE;";
	static final String username = "sin";
	static final String password = "boss";
	ArrayList<Doctor>doctors = new ArrayList<Doctor>();
	ArrayList<Section>sections = new ArrayList<Section>();
	Connection conn = null;
	public LoginRepository(){
		try {
			loadSections();
		} catch (GenE e) {
			e.printStackTrace();
		}
	}
	
	public int getSecIdFromName(String name) {
		if (sections.isEmpty()) return -1;
		for (Section obj : sections){
			if (name == obj.getName()) return obj.getId();
		}
		return -1;
	}
	
	public Section getSection(int id){
		for (Section o: sections) if (o.getId() == id) return o;
		return null;
	}
	
	public Doctor logInDoc(int usrid, String usr){
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "Select * from doctor where username = ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setInt(1, usrid);
			ResultSet rs = prp.executeQuery();
			if (rs.next()){
				return new Doctor(rs.getString("Name"), usr, getSection(rs.getInt("SectionId")));
			}
			else return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public Pharmacist logInPhr(int usrid, String usr){
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "Select * from pharmacists where username = ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setInt(1, usrid);
			ResultSet rs = prp.executeQuery();
			if (rs.next()){
				return new Pharmacist(rs.getInt("Id"), rs.getString("Name"), usr);
			}
			else return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object logIn(String usr, String pass) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "Select * from usernames where username = ? and password = ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setString(1, usr);
			prp.setString(2, pass);
			ResultSet rs = prp.executeQuery();
			if (rs.next()){
				boolean type = rs.getBoolean("Type");
				if (!type) return logInDoc(rs.getInt("Id"), usr);
				else return logInPhr(rs.getInt("Id"), usr);
			}
			else return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
//        try
//        {
//        	int secID = getSecIdFromName(secname);
//        	Class.forName(driver);
//  	        conn = DriverManager.getConnection(url,username, password);
//            String stsm = "select * from Doctor d inner join Usernames u on d.Username = u.Id where u.Username = '" +
//                    usr + "' and u.password = '" + pass + "' and d.SectionId = " + Integer.toString(secID);
//            ResultSet res = conn.createStatement().executeQuery(stsm);
//            int check = 0;
//            if (res.next()) check = res.getInt(1);
//            if (check != 0)
//            {   
//            	String stsm1 = "select d.Name from Doctor d inner join Usernames u on d.Username = u.Id where u.Username = '" + usr + "'";
//            	ResultSet res1 = conn.createStatement().executeQuery(stsm1);
//                String name = "";
//                if (res1.next()){
//	                name = res1.getString("Name");
//	                if (!name.equals(""))
//	                {
//	                    String stsm2 = "select s.Name from Sectii s where s.Id = " + Integer.toString(secID);
//	                    ResultSet res2 = conn.createStatement().executeQuery(stsm2);
//	                    if (res2.next()) {
//		                    String sectionName = "";
//		                    sectionName = res2.getString("Name");
//		                    if (!sectionName.equals(""))
//		                    {
//		                        Doctor doc = new Doctor(name, usr, sectionName);
//		                        doctors.add(doc);
//		                        return doc;
//		                    }
//	                    }
//	                }
//                }
//            }
//            return null;
//        }
//        catch (Exception ex)
//        {
//            return null;
//        }
//    }
	
	private void loadSections() throws GenE{
		 try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select * from Sectii";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				sections.add(new Section(id, name));
			}
		} catch (Exception e) {
			throw new GenE("LoadSections" + e.getMessage());
		}
	}
	
	public Vector<String> getSectionsV() throws GenE{
		Vector<String> sec = new Vector<String>();
		if (sections.isEmpty()) loadSections();
		for (Section obj : sections){
			sec.add(obj.getName());
		}
		return sec;
	}
	
	public ArrayList<Section> getSections() throws GenE{
		if (sections.isEmpty()) loadSections();
		return sections;
	}
}
