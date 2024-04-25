/**
 * 
 */
package com.micro.alianza.client.util;

/**
 * @author psych
 *
 */
public enum Constants {	
	CLIENT_SUCCESSFUL("The client was created successful"),
	ERROR_GENERAL("error when executing the process"),
	EXIST_CLIENT("This client is already registered ");
	

	private String valueString;
	private int valueInt;
	
	Constants(){}
    
    Constants(String valueString) {
    	this.valueString = valueString;
    }
    
    Constants(int valueInt){
    	this.valueInt = valueInt;
    }
    
    public String getValueString() {
		return valueString;
	}

	public int getValueInt() {
		return valueInt;
	}
}
