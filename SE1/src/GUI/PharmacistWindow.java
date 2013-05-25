package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import controller.PharmacistController;
import domain.Doctor;
import domain.Medicine;
import domain.Order;
import domain.Pharmacist;
import domain.Section;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PharmacistWindow extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private final JList list = new JList();
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private PharmacistController pcont = null;
	private Pharmacist p = null;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_5;

	public PharmacistWindow(final PharmacistController pcont, final Pharmacist p) {
		this.p = p;
		this.pcont = pcont;
		pcont.subscribe(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 216, 169);
		getContentPane().add(scrollPane);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedIndex()<0) return;
				Order obj = (Order) list.getSelectedValue();
				textField.setText(Integer.toString(obj.getId()));
				textField_1.setText(obj.getDoc());
				textField_2.setText(obj.getMed());
				textField_3.setText(Integer.toString(obj.getCount()));
				textField_4.setText(obj.getStatusS());
				textField_5.setText(getSection(obj.getDoc()).getName());
			}
		});
		scrollPane.setViewportView(list);
		
		JButton btnHonor = new JButton("Honor");
		btnHonor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() < 0) return;
				Order obj = (Order) list.getSelectedValue();
				pcont.honorOrder(obj.getId(), p.getId());
			}
		});
		btnHonor.setBounds(10, 228, 83, 23);
		getContentPane().add(btnHonor);
		
		JLabel lblAladdinnn = new JLabel("Id, Doctor, Medicine, Count, Status");
		lblAladdinnn.setBounds(10, 31, 216, 14);
		getContentPane().add(lblAladdinnn);
		
		JPanel panel = new JPanel();
		panel.setBounds(311, 49, 263, 169);
		getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
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
		
		JLabel lblOrderId = new JLabel("Order id:");
		panel.add(lblOrderId, "2, 2, right, default");
		
		textField = new JTextField();
		textField.setEditable(false);
		panel.add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JLabel lblDoctor = new JLabel("Doctor:");
		panel.add(lblDoctor, "2, 4, right, default");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel.add(textField_1, "4, 4, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblMedicine = new JLabel("Medicine:");
		panel.add(lblMedicine, "2, 6, right, default");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		panel.add(textField_2, "4, 6, fill, default");
		textField_2.setColumns(10);
		
		JLabel lblCount = new JLabel("Count:");
		panel.add(lblCount, "2, 8, right, default");
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		panel.add(textField_3, "4, 8, fill, default");
		textField_3.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status:");
		panel.add(lblStatus, "2, 10, right, default");
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		panel.add(textField_4, "4, 10, fill, default");
		textField_4.setColumns(10);
		
		JLabel lblSection = new JLabel("Section:");
		panel.add(lblSection, "2, 12, right, default");
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		panel.add(textField_5, "4, 12, fill, default");
		textField_5.setColumns(10);
		
		JLabel lblOrderDetail = new JLabel("Order detail:");
		lblOrderDetail.setBounds(311, 31, 176, 14);
		getContentPane().add(lblOrderDetail);
		
		JLabel lblFilterBy = new JLabel("Filter by:");
		lblFilterBy.setBounds(10, 260, 99, 14);
		getContentPane().add(lblFilterBy);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String item = (String) arg0.getItem();
				if (item.equals("<<None>>")) {
					comboBox_1.setEnabled(false);
					comboBox_2.setEnabled(false);
					comboBox_3.setEnabled(false);
				}
				else if (item.equals("Medicines")) {
					comboBox_1.setEnabled(false);
					comboBox_2.setEnabled(false);
					comboBox_3.setEnabled(true);
				}
				else if (item.equals("Sections")) {
					comboBox_1.setEnabled(true);
					comboBox_2.setEnabled(false);
					comboBox_3.setEnabled(false);
				}
				else if (item.equals("Doctors")){
					comboBox_1.setEnabled(false);
					comboBox_2.setEnabled(true);
					comboBox_3.setEnabled(false);
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"<<None>>", "Sections", "Doctors", "Medicines"}));
		comboBox.setBounds(109, 257, 89, 20);
		getContentPane().add(comboBox);
		
		JLabel lblLoggedAs = new JLabel("Logged as: "+p.getName());
		lblLoggedAs.setBounds(311, 11, 176, 14);
		getContentPane().add(lblLoggedAs);
		
		JLabel lbllogOut = new JLabel("[Log out]");
		lbllogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				dispose();
			}
		});
		lbllogOut.setBounds(495, 11, 79, 14);
		getContentPane().add(lbllogOut);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(pcont.getSections()));
		comboBox_1.setBounds(109, 285, 89, 20);
		getContentPane().add(comboBox_1);
		comboBox_1.setEnabled(false);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(pcont.getDoctors()));
		comboBox_2.setBounds(109, 316, 89, 20);
		getContentPane().add(comboBox_2);
		comboBox_2.setEnabled(false);
		
		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(pcont.getMedicines()));
		comboBox_3.setBounds(109, 347, 89, 20);
		getContentPane().add(comboBox_3);
		comboBox_3.setEnabled(false);
		
		JButton btnGo = new JButton("Go!");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String item = (String) comboBox.getSelectedItem();
				if (item == "<<None>>") reloadLists();
				else if (item == "Medicines") reloadListM();
				else if (item == "Sections") reloadListS();
				else if (item == "Doctors") reloadListD();
			}
		});
		btnGo.setBounds(10, 378, 83, 23);
		getContentPane().add(btnGo);
		
		JLabel lblSection_1 = new JLabel("Section:");
		lblSection_1.setBounds(10, 288, 99, 14);
		getContentPane().add(lblSection_1);
		
		JLabel lblDoctor_1 = new JLabel("Doctor:");
		lblDoctor_1.setBounds(10, 319, 99, 14);
		getContentPane().add(lblDoctor_1);
		
		JLabel lblMedicine_1 = new JLabel("Medicine:");
		lblMedicine_1.setBounds(10, 350, 99, 14);
		getContentPane().add(lblMedicine_1);
	
		reloadLists();
	}

	
	private void reloadLists(){
		Vector<Order> ords = pcont.getOrders();
		DefaultListModel model = new DefaultListModel();
		for (Order obj: ords) model.addElement(obj);
		list.setModel(model);
		clearOrderInfo();
	}
	
	private void reloadListM(){
		Vector<Order> ords = pcont.getOrdersFilteredByMedicine(((Medicine)comboBox_3.getSelectedItem()).getName());
		DefaultListModel model = new DefaultListModel();
		for (Order obj: ords) model.addElement(obj);
		list.setModel(model);
		clearOrderInfo();
	}
	
	private void reloadListD(){
		Vector<Order> docs = pcont.getOrdersFilteredByDoctor(((Doctor)comboBox_2.getSelectedItem()).getName());
		DefaultListModel model = new DefaultListModel();
		for (Order obj: docs) model.addElement(obj);
		list.setModel(model);
		System.out.println(docs.size());
		clearOrderInfo();
	}
	
	private void reloadListS(){
		Vector<Order> ords = pcont.getOrdersFilteredBySection(((Section)comboBox_1.getSelectedItem()).getId());
		DefaultListModel model = new DefaultListModel();
		for (Order obj: ords) model.addElement(obj);
		list.setModel(model);
		clearOrderInfo();
	}
	
	private void clearOrderInfo(){
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
	}
	
	private Section getSection(String docname){
		int previndex = comboBox_2.getSelectedIndex(), index = 0;
		while (!((Doctor)comboBox_2.getSelectedItem()).getName().equals(docname)) comboBox_2.setSelectedIndex(index++);
		Section obj = ((Doctor)comboBox_2.getSelectedItem()).getSection();
		if (previndex < 0) previndex = 0;
		comboBox_2.setSelectedIndex(previndex);
		return obj;
	}


	@Override
	public void update(Observable o, Object arg) {
		String item = (String) comboBox.getSelectedItem();
		if (item == "<<None>>") reloadLists();
		else if (item == "Medicines") reloadListM();
		else if (item == "Sections") reloadListS();
		else if (item == "Doctors") reloadListD();
	}
}
