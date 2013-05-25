package util;

import java.util.Observable;

public class Notifier extends Observable{
	public void change() { setChanged(); }
	public void not() { notifyObservers(); }
	public void doTheWork() {
		setChanged();
		notifyObservers();
	}
}
