package controller;

import domain.Doctor;
import domain.Medicine;
import domain.Order;
import domain.Section;

import java.util.Observer;
import java.util.Vector;

import javax.swing.JFrame;

import repository.PharmacistRepository;
import util.Notifier;

public class PharmacistController {
	private PharmacistRepository prep;
	
	public PharmacistController(PharmacistRepository prep){
		this.prep = prep;
	}
	
	public void honorOrder(int orderId, int pId){
		prep.honorOrder(orderId, pId);
	}
	
	public void subscribe(Observer item){
		prep.notifier.addObserver(item);
	}
	
	
	public Vector<Order> getOrdersFilteredByMedicine(String medname){
		return prep.getOrdersFilterByMedicine(medname);
	}
	
	public Vector<Order> getOrdersFilteredBySection (int section){
		return prep.getOrdersFilterBySection(section);
	}
	
	public Vector<Order> getOrdersFilteredByDoctor (String docname){
		return prep.getOrdersFilterByDoctor(docname);
	}
	
	public Vector<Order> getOrders(){
		return prep.getOrders();
	}
	
	public Vector<Medicine> getMedicines(){
		return prep.getMedicines();
	}
	
	public Vector<Doctor> getDoctors(){
		return prep.getDoctors();
	}
	
	public Vector<Section> getSections(){
		return prep.getSections();
	}
}
