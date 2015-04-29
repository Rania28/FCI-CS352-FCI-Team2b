package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MessageEntity {
	private String message;
	private int senID;
	private int resID;
	public MessageEntity(String message,int senID,int resID)
	{
	this.message=message;
	this.resID=resID;
	this.senID=senID;
	}
	
	public void set_senID(int senID)
	{
		
		
		this.senID=senID;
	}
	public void set_resID(int resID)
	{
		
	this.resID=resID;
	}
public String get_message()
{
	return message;
	
}
public int get_resID()
{
	return resID;
	
}
public int get_senID()
{
	return senID;
	}
public Boolean saveMessage() {
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Message");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

	Entity employee = new Entity("Message", list.size() + 1);

	employee.setProperty("message", this.message);
	employee.setProperty("senID", this.senID);
	employee.setProperty("resID", this.resID);
	datastore.put(employee);

	return true;

}












}
