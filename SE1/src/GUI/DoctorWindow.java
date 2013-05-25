package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import domain.Doctor;
import domain.Medicine;
import domain.Order;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JButton;

import repository.DoctorRepository;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JOptionPane;
import controller.DoctorController;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorWindow extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	JFrame frame;
	Doctor d = null;
	DoctorController dcont = null;
	private JPanel contentPane;
	private final JList list = new JList();
	private final JPanel panel = new JPanel();
	private final JList list_1 = new JList();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JComboBox comboBox;
	private JSpinner spinner;
	private final JList list_2 = new JList();

	public DoctorWindow(final DoctorController dcont, final Doctor d) {
		this.d = d;
		this.dcont = dcont;
		dcont.subscribe(this);
		frame = new JFrame();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(502, 50, 272, 219);
		contentPane.add(scrollPane);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedIndex()<0) return;
				Order obj = (Order) list.getSelectedValue();
				textField.setText(Integer.toString(obj.getId()));
				textField_1.setText(obj.getDoc());
				textField_2.setText(obj.getMed());
				textField_3.setText(Integer.toString(obj.getCount()));
				textField_4.setText(obj.getStatusS());
				textField_5.setText(obj.getPharm().getName());
				setComboBox(obj.getMed());
				spinner.setValue(obj.getCount());
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblLoggedAs = new JLabel("Logged as: "+d.getName());
		lblLoggedAs.setBounds(10, 11, 134, 14);
		contentPane.add(lblLoggedAs);
		
		JLabel lbllogOut = new JLabel("[Log out]");
		lbllogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				dispose();
			}
		});
		lbllogOut.setBounds(148, 11, 66, 14);
		contentPane.add(lbllogOut);
		panel.setBounds(10, 46, 212, 160);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblMedicine = new JLabel("Medicine:");
		lblMedicine.setBounds(6, 11, 67, 14);
		panel.add(lblMedicine);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 0, 0, 0);
		panel.add(horizontalStrut_1);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setBounds(0, 0, 0, 0);
		panel.add(horizontalStrut_2);
		
		comboBox = new JComboBox(dcont.getMedicines());
		comboBox.setBounds(83, 8, 96, 20);
		panel.add(comboBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 0, 0, 0);
		panel.add(horizontalStrut);
		
		JLabel lblCount = new JLabel("Count:");
		lblCount.setBounds(6, 41, 67, 14);
		panel.add(lblCount);
		
		spinner = new JSpinner();
		spinner.setBounds(83, 38, 96, 20);
		panel.add(spinner);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex()<0) return;
				Order obj = (Order)list.getSelectedValue();
				if ((Integer)spinner.getValue()>((Medicine)comboBox.getSelectedItem()).getQuantity()) {
					JOptionPane.showMessageDialog(null, "Not enough items on stock.");
					return;
				}
				if ((Integer)spinner.getValue()<=0){
					JOptionPane.showMessageDialog(null, "Invalid number provided.");
					return;
				}
				if (!obj.getDoc().equals(d.getName())){
					JOptionPane.showMessageDialog(null, "Cannot update other doctor's order.");
					return;
				}
				dcont.updateOrder(obj.getId(), (Integer)spinner.getValue());
			}
		});
		btnUpdate.setBounds(6, 126, 96, 23);
		panel.add(btnUpdate);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((Integer)spinner.getValue() <= 0) {
					JOptionPane.showInternalMessageDialog(null, "Invalid number provided.");
					return;
				}
				dcont.addOrder(d.getName(), ((Medicine)comboBox.getSelectedItem()).getName(), (Integer)spinner.getValue());
				
			}
		});
		btnAdd.setBounds(6, 92, 67, 23);
		panel.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex()<0) return;
				Order obj = (Order)list.getSelectedValue();
				if (!obj.getDoc().equals(d.getName())){
					JOptionPane.showMessageDialog(null, "Cannot remove other doctor's order.");
					return;
				}
				dcont.removeOrder(obj.getId());
			}
		});
		btnRemove.setBounds(113, 126, 89, 23);
		panel.add(btnRemove);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(502, 332, 272, 219);
		contentPane.add(scrollPane_1);
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list_1.getSelectedIndex()<0) return;
				Order obj = (Order) list_1.getSelectedValue();
				textField.setText(Integer.toString(obj.getId()));
				textField_1.setText(obj.getDoc());
				textField_2.setText(obj.getMed());
				textField_3.setText(Integer.toString(obj.getCount()));
				textField_4.setText(obj.getStatusS());
				textField_5.setText(obj.getPharm().getName());
			}
		});
		scrollPane_1.setViewportView(list_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(273, 30, 212, 167);
		contentPane.add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOrderId = new JLabel("Order Id:");
		panel_1.add(lblOrderId, "2, 2, right, default");
		
		textField = new JTextField();
		textField.setEditable(false);
		panel_1.add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JLabel lblDoctor = new JLabel("Doctor:");
		panel_1.add(lblDoctor, "2, 4, right, default");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel_1.add(textField_1, "4, 4, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblMedicine_1 = new JLabel("Medicine:");
		panel_1.add(lblMedicine_1, "2, 6, right, default");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		panel_1.add(textField_2, "4, 6, fill, default");
		textField_2.setColumns(10);
		
		JLabel lblCount_1 = new JLabel("Count:");
		panel_1.add(lblCount_1, "2, 8, right, default");
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		panel_1.add(textField_3, "4, 8, fill, default");
		textField_3.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status:");
		panel_1.add(lblStatus, "2, 10, right, default");
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		panel_1.add(textField_4, "4, 10, fill, default");
		textField_4.setColumns(10);
		
		JLabel lblPharmacist = new JLabel("Pharmacist:");
		panel_1.add(lblPharmacist, "2, 12, right, default");
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		panel_1.add(textField_5, "4, 12, fill, default");
		textField_5.setColumns(10);
		
		JLabel lblIdDoctorMedicine = new JLabel("Id, Doctor, Medicine, Count, Status");
		lblIdDoctorMedicine.setBounds(502, 25, 218, 14);
		contentPane.add(lblIdDoctorMedicine);
		
		JLabel lblIdDoctorMedicine_1 = new JLabel("Id, Doctor, Medicine, Count, Status, Pharmacist");
		lblIdDoctorMedicine_1.setBounds(502, 307, 272, 14);
		contentPane.add(lblIdDoctorMedicine_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 275, 124, 192);
		contentPane.add(scrollPane_2);
		list_2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list_2.getSelectedIndex()<0) return;
				String name = ((String)list_2.getSelectedValue()).split(", ")[1];
				int index = 0;
				while (true){
					if (((Medicine)comboBox.getSelectedItem()).getName().equals(name)) break;
					comboBox.setSelectedIndex(index++);
				}
			}
		});
		scrollPane_2.setViewportView(list_2);
		
		JLabel lblNameCount = new JLabel("Id, Name, Quantity");
		lblNameCount.setBounds(20, 255, 124, 14);
		contentPane.add(lblNameCount);
		
		JLabel lblOrderList = new JLabel("Order List");
		lblOrderList.setBounds(502, 11, 172, 14);
		contentPane.add(lblOrderList);
		
		JLabel lblHonoredOrderList = new JLabel("Honored order list:");
		lblHonoredOrderList.setBounds(502, 292, 218, 14);
		contentPane.add(lblHonoredOrderList);
		
		JLabel lblMedicineList = new JLabel("Medicine list:");
		lblMedicineList.setBounds(20, 239, 124, 14);
		contentPane.add(lblMedicineList);
		reloadLists();
		reloadMed();
	}
	
	private void reloadMed(){
		DefaultComboBoxModel model = new DefaultComboBoxModel(dcont.getMedicines());
		comboBox.setModel(model);
	}
	
	private void reloadLists(){
		Vector<Order> ords = dcont.getOrders(d.getSection().getId());
		DefaultListModel model = new DefaultListModel();
		for (Order obj: ords) model.addElement(obj);
		list.setModel(model);
		Vector<Order> ordsH = dcont.getHonoredOrders(d.getSection().getId());
		DefaultListModel modelH = new DefaultListModel();
		for (Order obj: ordsH) { modelH.addElement(obj);}
		list_1.setModel(modelH);
		Vector<Medicine> meds = dcont.getMedicines();
		DefaultListModel modelM = new DefaultListModel();
		for (Medicine m: meds) { modelM.addElement(m.info());}
		list_2.setModel(modelM);
		clearOrderInfo();
	}
	
	private void setComboBox(String medname){
		int index = 0;
		while (true){
			if (((Medicine)comboBox.getSelectedItem()).getName().equals(medname)) break;
			comboBox.setSelectedIndex(index++);
		}
	}
	
	private void clearOrderInfo(){
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		reloadLists();
		reloadMed();
	}
}
