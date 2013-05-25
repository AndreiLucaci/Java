package domain;

public class Doctor implements Person{
	private int id;
	private String name;
	private String username;
	private Section section;

    public Doctor()
    {
        name = username = "";
        id = 0;
    }
    
    public Doctor(int id, String name, Section section){
    	this.id = id;
    	this.name = name;
    	username = "";
    	this.section = section;
    }

    public Doctor(String name, String usr, Section sec)
    {
        this.name = name;
        username = usr;
        section = sec;
    }
    
    @Override
	public String getName() {
		return this.name;
	}
    
    public Section getSection() {
    	return this.section;
    }

	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		return ((Doctor)o).getName().equals(this.name) &&
				((Doctor)o).getUsername().equals(this.username) &&
				((Doctor)o).getSection().equals(this.section);
	}
}
