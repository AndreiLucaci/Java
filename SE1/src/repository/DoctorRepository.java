package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JOptionPane;

import util.Notifier;

import domain.Medicine;
import domain.Order;
import domain.Pharmacist;
import domain.Section;
import exception.GenE;
public class DoctorRepository {

	private Vector<Medicine> medicines = new Vector<Medicine>();
	private Vector<Order> orders = new Vector<Order>();
	private Vector<Order> honoredorders = new Vector<Order>();
	static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String url = "jdbc:sqlserver://SIN-PC\\SIN;databaseName=SE;";
	static final String username = "sin";
	static final String password = "boss";
	private Connection conn = null;
	
	public Notifier notifier = null;
	
	public DoctorRepository(Notifier notifier){
		this.notifier = notifier;
		loadMedicines();
	}
	
	public void addOrder(String doc, String med, int count){
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "exec dbo.addOrder ?, ?, ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setString(1, doc);
			prp.setString(2, med);
			prp.setInt(3, count);
			prp.executeUpdate();
			notifier.doTheWork();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrder(int orderId, int count){
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "exec dbo.updateOrd ?, ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setInt(1, orderId);
			prp.setInt(2, count);
			prp.executeUpdate();
			notifier.doTheWork();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void removeOrder(int orderId){
		try {
			conn = DriverManager.getConnection(url, username, password);
			String query = "exec dbo.removeOrd ?";
			PreparedStatement prp = conn.prepareStatement(query);
			prp.setInt(1, orderId);
			prp.executeUpdate();
			notifier.doTheWork();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Vector<Medicine> getMedicines(){
		if (!medicines.isEmpty()) medicines.clear();
		loadMedicines();
		return medicines;
	}
	
	public Vector<Order> getOrders(int id){
		if (!orders.isEmpty()) orders.clear();
		loadOrders(id);
		return orders;
	}
	
	public Vector<Order> getHonoredOrders (int id){
		if (!honoredorders.isEmpty()) honoredorders.clear();
		loadHonoredOrders(id);
		return honoredorders;
	}
	
	private void loadMedicines(){
		try {
			Class.forName(driver);
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
	
	private void loadOrders(int id){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status from orders o "+
						 "inner join doctor d on o.DoctorId = d.Id "+
						 "inner join medicine m on o.MedicineId = m.Id "+
						 "where status = 0 and d.sectionId = "+Integer.toString(id);
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				orders.add(new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status")));
			}
		} catch (Exception e){
			
		}
	}
	
	private void loadHonoredOrders(int id){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			String sql = "select o.id as Oid, d.name as Dname, m.name as Mname, o.Count, o.Status, p.id as Pid from orders o "+
						"inner join doctor d on o.DoctorId = d.Id "+
						"inner join medicine m on o.MedicineId = m.Id "+
						"inner join pharmacists p on o.PharmacistId = p.Id "+
						"where status = 1 and d.SectionId = "+Integer.toString(id);
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				String sql1 = "select p.id, p.name from Pharmacists p where id = "+Integer.toString(rs.getInt("Pid"));
				ResultSet rs1 = conn.createStatement().executeQuery(sql1);
				rs1.next();
				Pharmacist p = new Pharmacist(rs1.getInt("Id"), rs1.getString("Name"));
				Order obj = new Order(rs.getInt("Oid"), rs.getString("Dname"), rs.getString("Mname"), rs.getInt("Count"), rs.getBoolean("Status"));
				obj.setPharmacist(p);
				honoredorders.add(obj);
			}
		} catch (Exception e){
			
		}
	}
}
