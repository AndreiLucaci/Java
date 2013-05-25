package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import util.Notifier;

import domain.Doctor;
import domain.Medicine;
import domain.Order;
import domain.Pharmacist;
import domain.Section;

public class PharmacistRepository {
	static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String url = "jdbc:sqlserver://SIN-PC\\SIN;databaseName=SE;";
	static final String username = "sin";
	static final String password = "boss";
	private Connection conn = null;
	
	public Notifier notifier = null;
	
	private Vector<Pharmacist> pharmacists = new Vector<Pharmacist>();
	private Vector<Doctor> doctors = new Vector<Doctor>();
	private Vector<Medicine> medicines = new Vector<Medicine>();
	private Vector<Section> sections = new Vector<Section>();
	private Vector<Order> orders = new Vector<Order>();
	
	
	public PharmacistRepository(Notifier notifier){
		this.notifier = notifier;
		loadPharmacists();
		loadMedicines();
		loadSections();
		loadDoctors();
		loadOrders();
	}
	
	private void loadOrders(){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status from orders o "+
						 "inner join doctor d on o.DoctorId = d.Id "+
						 "inner join medicine m on o.MedicineId = m.Id "+
						 "where status = 0";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				orders.add(new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status")));
			}
		} catch (Exception e){
			
		}
	}
	
	public Vector<Order> getOrdersFilterByMedicine(String medname){
		try{
			Vector<Order> ords = new Vector<Order>();
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status from orders o "+
						"inner join doctor d on o.DoctorId = d.Id "+
						"inner join medicine m on o.MedicineId = m.Id "+
						"where status = 0 and m.name = ?";
			PreparedStatement prp = conn.prepareStatement(sql);
			prp.setString(1, medname);
			ResultSet rs = prp.executeQuery();
			while (rs.next()){
				ords.add(new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status")));
			}
			return ords;
		} catch (Exception e){
			return null;
		}
	}
	
	public Vector<Order> getOrdersFilterBySection(int section){
		try{
			Vector<Order> ords = new Vector<Order>();
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status from orders o "+
						"inner join doctor d on o.DoctorId = d.Id "+
						"inner join medicine m on o.MedicineId = m.Id "+
						"where status = 0 and d.sectionid = ?";
			PreparedStatement prp = conn.prepareStatement(sql);
			prp.setInt(1, section);
			ResultSet rs = prp.executeQuery();
			while (rs.next()){
				ords.add(new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status")));
			}
			return ords;
		} catch (Exception e){
			return null;
		}
	}
	
	public Vector<Order> getOrdersFilterByDoctor(String docname){
		try{
			Vector<Order> ords = new Vector<Order>();
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status from orders o "+
						"inner join doctor d on o.DoctorId = d.Id "+
						"inner join medicine m on o.MedicineId = m.Id "+
						"where status = 0 and d.name = ?";
			PreparedStatement prp = conn.prepareStatement(sql);
			prp.setString(1, docname);
			ResultSet rs = prp.executeQuery();
			while (rs.next()){
				ords.add(new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status")));
			}
			return ords;
		} catch (Exception e){
			return null;
		}
	}
	
	private void loadPharmacists(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select * from Pharmacists";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				String sql1 = "select u.Username from usernames u where id = " + Integer.toString(rs.getInt("Username"));
				ResultSet rs1 = conn.createStatement().executeQuery(sql1);
				rs1.next();
				pharmacists.add(new Pharmacist(rs.getInt("Id"), rs.getString("Name"), rs1.getString("Username")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void honorOrder(int orderId, int pId){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "exec dbo.finish ?, ?";
			PreparedStatement prp = conn.prepareStatement(sql);
			prp.setInt(1, orderId);
			prp.setInt(2, pId);
			prp.executeUpdate();
			notifier.doTheWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadMedicines(){
		try {
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select * from Medicine";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				int quantity = rs.getInt("Number");
				medicines.add(new Medicine(id, name, quantity));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void loadSections(){
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

		}
	}
	
	public Vector<Medicine> getMedicines(){
		if (!medicines.isEmpty()) medicines.clear();
		loadMedicines();
		return medicines;
	}
	
	public Vector<Order> getOrders(){
		if (!orders.isEmpty()) orders.clear();
		loadOrders();
		return orders;
	}
	
	public Vector<Section> getSections(){
		if (!sections.isEmpty()) sections.clear();
		loadSections();
		return sections;
	}
	
	public Vector<Doctor> getDoctors(){
		if (!sections.isEmpty()) sections.clear();
		loadSections();
		if (!doctors.isEmpty()) doctors.clear();
		loadDoctors();
		return doctors;
	}
	
	public Section getSection(int id){
		for (Section o: sections) if (o.getId() == id) return o;
		return null;
	}
	
	private void loadDoctors(){
		try {
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select * from doctor";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				doctors.add(new Doctor(id, name, getSection(rs.getInt("SectionId"))));
			}
		} catch (Exception e) {

		}
	}
}
