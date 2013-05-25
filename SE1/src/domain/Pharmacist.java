package domain;

public class Pharmacist implements Person {
	private int id;
	private String name, username;
	
	public Pharmacist(int id, String name, String username){
		this.id = id;
		this.name = name;
		this.username = username;
	}
	
	public Pharmacist(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String getName() { return name; }
	@Override
	public String getUsername() { return username; }
	public int getId() { return id; }
}
