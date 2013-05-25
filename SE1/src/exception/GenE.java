package exception;

public class GenE extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public GenE(String msg){
		super(msg);
		message = msg;
	}
	
	public String getMsg(){ return message; }
	
}
