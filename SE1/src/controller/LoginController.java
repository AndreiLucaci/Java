package controller;

import java.util.Vector;

import domain.Section;
import exception.GenE;

import repository.LoginRepository;

public class LoginController {

	private LoginRepository lrpo;
	
	public LoginController(LoginRepository r) {
		this.lrpo = r;
	}
	
	public Object logIn(String username, String password){
		return lrpo.logIn(username, password);
	}
	
	public Vector<String> getSectionV(){
		try {
			return lrpo.getSectionsV();
		} catch (GenE e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
