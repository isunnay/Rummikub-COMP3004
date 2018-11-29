package com.COMP3004.Rummikub.models;

public interface Subject {

	public void registerObserver(Observer o);
		
	public void removeObserver(Observer o);
		
	public void notifyObservers();
	
	//public void getUpdate(Observer o);

}
