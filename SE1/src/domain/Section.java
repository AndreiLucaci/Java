package domain;

public class Section {
    private String name;
    private int id;
    public Section()
    {
        name = "";
        id = -1;
    }

    public Section(int id, String name)
    {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    
    public int getId() { return id; }
    
    @Override
    public String toString(){
    	return name;
    }
    
    @Override
    public boolean equals(Object o){
    	return ((Section)o).getId() == this.id && ((Section)o).getName().equals(this.name);
    }
}
