package controller;

import java.util.Observer;
import java.util.Vector;

import domain.Medicine;
import domain.Order;

import repository.DoctorRepository;

public class DoctorController {
	private DoctorRepository r;
	
	public DoctorController(DoctorRepository r){
		this.r = r;
	}
	
	public void subscribe(Observer item){
		r.notifier.addObserver(item);
	}
	
	public void addOrder(String dname, String mname, int count){
		r.addOrder(dname, mname, count);
	}
	
	public void removeOrder(int orderId){
		r.removeOrder(orderId);
	}
	
	public void updateOrder(int orderId, int count){
		r.updateOrder(orderId, count);
	}
	
	public DoctorRepository getR(){ return r;}
	
	public Vector<Order> getOrders(int id){
		return r.getOrders(id);
	}
	
	public Vector<Medicine> getMedicines(){
		return r.getMedicines();
	}
	
	public Vector<Order> getHonoredOrders(int id){
		return r.getHonoredOrders(id);
	}
}
