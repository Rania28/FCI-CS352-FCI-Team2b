package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class RequestEntity {
	private String sendName;
	private long sendID;
	private String recName;
	private long recID;
	private String status;
	private long reqID;

//	private long sendId;

	/**
	 * Constructor accepts user data
	 * 
	 * @param sendName
	 *            user sendName
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public RequestEntity(String sendName, long sendID,String recName, long recID,String status) {
		this.sendName = sendName;
		this.sendID= sendID;
		this.recID = recID;
		this.status=status;
		this.recName=recName;
		
	}
 
	public RequestEntity(String string) {
	// TODO Auto-generated constructor stub
}
	public RequestEntity(String send_name, String rec_name) {
		// TODO Auto-generated constructor stub
		this.recName=rec_name;
		this.sendName=send_name;
	}

	public RequestEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setrecName(String recName)
	{
		this.recName=recName;
	}
	public String getrecName()
	{
		return recName;
	}
	public String getsendName()
	{
		return sendName;
	}
	public void setsendName(String sendName)
	{
		this.sendName=sendName;
	}
	
	private void setSendId(long sendID){
		this.sendID = sendID;
	}
	
	public long getsendID(){
		return sendID;
	}

	private void setrecId(long recID){
		this.recID = recID;
	}
	
	public long getRecID(){
		return recID;
	}
	public void setStatus(String Status){
	this.status=Status;
		
	}
	public String getStatus()
	{
		return status;
	}
	public long getReqID(){
		return reqID;
	}
	public Boolean saveRequest() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Requests", list.size() + 1);

		employee.setProperty("sendName", this.sendName);
		employee.setProperty("sendID", this.sendID);
		employee.setProperty("recID", this.recID);
		employee.setProperty("status", this.status);
		employee.setProperty("recname", this.recName);
		employee.setProperty("requestID", this.reqID);
		datastore.put(employee);

		return true;

	}
	

	public String getRequest(long recID){
		
		//Vector<String> FriendsNames=new Vector<String> (); 
		String name = "";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("recID").equals(recID))
			{   
				name=entity.getProperty("recname").toString() + " wants to be friend ";
				//FriendsNames.add(name);
			}
		}
	
		return name;
	}
	

	public  boolean getRequest(String send_name ,String  rec_name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
       // ArrayList<RequestEntity> matches=new ArrayList<RequestEntity>(); 
		Query gaeQuery = new Query("Requests");
		//System.out.println("S : " + send_name + " R" + rec_name);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("sendName").toString().equals(send_name) &&
					entity.getProperty("recname").toString().equals(rec_name))
			{
				entity.setProperty("status", "active");
				datastore.put(entity);
				return true;
			}
		}
		return false;
	}
	
	public boolean isFriend(String send,String rec)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
       // ArrayList<RequestEntity> matches=new ArrayList<RequestEntity>(); 
		Query gaeQuery = new Query("Requests");
		//System.out.println("S : " + send_name + " R" + rec_name);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if ((entity.getProperty("sendName").toString().equals(send) || entity.getProperty("sendName").toString().equals(rec))
					&&
					(entity.getProperty("recName").toString().equals(send) || entity.getProperty("recName").toString().equals(rec))
					&& entity.getProperty("status").toString().equals("active"))
			{
				
				return true;
			}
		}
		return false;
	}
	
	}