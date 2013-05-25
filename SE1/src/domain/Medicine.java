package domain;

public class Medicine {
	private int id;
	private String name;
	private int quantity;
	
	public Medicine(){}
	
	public Medicine(int id, String name, int quantity){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}
	
	public int getId(){ return id;}
	public String getName() { return name;}
	public int getQuantity() { return quantity;}
	
	@Override
	public String toString(){
		return name;
	}
	
	public String info(){
		return Integer.toString(id) + ", " + name + ", " + Integer.toString(quantity);
	}
}
