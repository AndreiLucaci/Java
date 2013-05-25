package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import repository.DoctorRepository;
import repository.LoginRepository;
import repository.PharmacistRepository;
import util.Notifier;
import controller.DoctorController;
import controller.LoginController;
import controller.PharmacistController;
import domain.Doctor;
import domain.Section;

public class testDoctor {
	private static final String user = "doc1";
	private static final String pass = "doc1";
	private static final String dname = "Doctor1";
	private static final String sname = "Section1";
	Notifier notifier = new Notifier();
	DoctorRepository drep = new DoctorRepository(notifier);
	DoctorController dcont = new DoctorController(drep);
	LoginRepository r = new LoginRepository();
	LoginController lc = new LoginController(r);
	Section sec = new Section(1, sname);
	Doctor doc = new Doctor(dname, user, sec);
	
	@Test
	public void testDoctorInfo() {
		assertTrue(doc.getName().equals(dname));
		assertTrue(doc.getUsername().equals(user));
		assertTrue(doc.getSection().getId() == sec.getId());
	}
	
	@Test
	public void testLogIn(){
		assertTrue(lc.logIn("", "") == null);
		assertTrue(lc.logIn(doc.getUsername(), doc.getUsername()).equals(doc));
	}
	
	@Test
	public void testLoadSections(){
		assertTrue(lc.getSectionV().toArray()[0].equals(sec.getName()));
	}
	
	@Test
	public void testLoadOrders(){
		assertTrue(dcont.getOrders(sec.getId()).size() != 0);
	}

}
