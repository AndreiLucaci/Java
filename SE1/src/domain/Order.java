package domain;

public class Order {
	private int id;
	private int count;
	private String doc;
	private String med;
	private boolean status;
	private Pharmacist pharm;

    public Order()
    {
        id = count = -1;
        doc = med = "";
        status = false;
    }

    public Order(int id, String doc, String med, int count, boolean status)
    {
        this.id = id;
        this.doc = doc;
        this.med = med;
        this.count = count;
        this.status = status;
        this.pharm = new Pharmacist(-1, "", "");
    }
    
    public String getDoc() { return doc; }
    public int getId() { return id; }
    public int getCount() { return count; }
    public Pharmacist getPharm() { return pharm; }
    public boolean getStatus() { return status; }
    public String getStatusS() {
    	if (status) return "Honored";
    	return "Pending";
    }
    public String getMed() { return med; }
    
    public void setPharmacist(Pharmacist obj){ this.pharm = obj; }
    
    @Override
    public String toString(){
    	String sts = "Pending";
    	if (status) sts = "Honored";
    	if (pharm.getName().equals("")) return Integer.toString(id) + ", " + doc + ", " + med + ", " + Integer.toString(count) + ", " + sts;
    	return Integer.toString(id) + ", " + doc + ", " + med + ", " + Integer.toString(count) + ", " + sts + ", " + pharm.getName();
    }
}
