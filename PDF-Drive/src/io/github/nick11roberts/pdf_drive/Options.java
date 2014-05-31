package io.github.nick11roberts.pdf_drive;

import java.io.Serializable;

@SuppressWarnings("serial") // Hide warnings. 
public class Options implements Serializable {
	
	// Option variables. 
	private String title;
	private String folder;
	private int numberOfPages;
	
	// Constructor
	Options(){ // Sets initial values.
		title = "";
		folder = "";
		numberOfPages = 0;
	}
	
	// Setters.
	public void setTitle(String input){
		title = input;
	}
	
	public void setFolder(String input){
		folder = input;
	}
	
	public void setNumberOfPages(int input){
		numberOfPages = input;
	}
	
	// Getters.
	public String getTitle(){
		return title;
	}
	
	public String getFolder(){
		return folder;
	}
	
	public int getNumberOfPages(){
		return numberOfPages;
	}
	
}
