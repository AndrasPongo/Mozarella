package com.hybridtheory.mozarella.pet;

import java.sql.Date;

public interface Pet {

	public void setLevel();
	
	public void setExtras();
	
	public void setLastFeedTime();
	public Date getLastFeedTime();
	
	public void feed(String typeOfFood, int amountOfFood);

	public String manifest();
	
	
	
}
