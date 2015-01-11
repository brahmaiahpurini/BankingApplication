package edu.pitt.bank;

public class ShireyException extends RuntimeException{
	public ShireyException(){
		
	}

	//see LoginView initialize
	
	public ShireyException(String errorMessage){
		System.out.println(errorMessage);
	}
}
