package MVC;

import java.awt.EventQueue;

import repository.DoctorRepository;
import repository.LoginRepository;
import repository.PharmacistRepository;
import util.Notifier;

import controller.DoctorController;
import controller.LoginController;
import controller.PharmacistController;


import GUI.gui1;

public class MVC {
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Notifier notifier = new Notifier();
				DoctorRepository drep = new DoctorRepository(notifier);
				DoctorController dcont = new DoctorController(drep);
				PharmacistRepository prep = new PharmacistRepository(notifier);
				PharmacistController pcont = new PharmacistController(prep);
				LoginRepository r = new LoginRepository();
				LoginController lc = new LoginController(r);
				gui1 window = new gui1(lc, dcont, pcont);
				window.getFrame().pack();
				window.getFrame().setVisible(true);
				window.getFrame().setSize(500,400);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
}
