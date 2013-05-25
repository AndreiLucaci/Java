package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import repository.DoctorRepository;
import repository.LoginRepository;
import repository.PharmacistRepository;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import controller.DoctorController;
import controller.LoginController;
import controller.PharmacistController;

import domain.Doctor;
import domain.Pharmacist;
import domain.Section;
import exception.GenE;

import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JDesktopPane;

public class gui1 {

	private JFrame frame;
	private JComboBox comboBox;
	private JTextField usr_ln;
	private JPasswordField pas_ln;
	private LoginController c = null;
	private DoctorController dcont = null;
	private PharmacistController pcont = null;
	
	public gui1(LoginController c, DoctorController dcont, PharmacistController pcon){
		this.c = c;
		this.dcont = dcont;
		this.pcont = pcon;
		initialize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Frame getFrame(){ return this.frame;}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
//		comboBox = null;
//		try {
//			final DefaultComboBoxModel model = new DefaultComboBoxModel(r.getSectionsV());
//		    
//		} catch (GenE e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//			comboBox = new JComboBox();
//		}
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(123, 69, 84, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(123, 94, 84, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setBounds(158, 121, 89, 23);
		frame.getContentPane().add(btnLogIn);
		
		usr_ln = new JTextField();
		usr_ln.setBounds(204, 66, 86, 20);
		frame.getContentPane().add(usr_ln);
		usr_ln.setColumns(10);
		
		pas_ln = new JPasswordField();
		pas_ln.setBounds(204, 91, 86, 20);
		frame.getContentPane().add(pas_ln);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (usr_ln.getText() == "" || pas_ln.getPassword().toString() == "") JOptionPane.showMessageDialog(null, "One or more fields are empty.");
				else {
					Object o = c.logIn(usr_ln.getText(), new String(pas_ln.getPassword()));
					
					if (o == null) JOptionPane.showMessageDialog(null, "Invalid login creditials.");
					else if (o instanceof Doctor) {
						DoctorWindow dw = new DoctorWindow(dcont, (Doctor)o);
						dw.pack();
						dw.setVisible(true);
						dw.setSize(800, 600);
					}
					else if (o instanceof Pharmacist) {
						PharmacistWindow pw = new PharmacistWindow(pcont, (Pharmacist)o);
						pw.pack();
						pw.setVisible(true);
						pw.setSize(600, 450);
					}
				}
			}
		});
	}
}
