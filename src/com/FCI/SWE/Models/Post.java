package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {
	
	private String postOwner;
	private int numOFlike;
	private String Content;
	private int postID;
	private String postType;
    private int numberOfSeen;
    private String Feelings;
	public Post(String postOwner,int numOFlike,String Content,int postID ,String postType)
	{
	this.postOwner=postOwner;
	this.numOFlike=numOFlike;
	this.Content=Content;
	this.postID=postID;
	this.postType=postType;
	}
	public Post(String postOwner,int numOFlike,String Content)
	{
	this.postOwner=postOwner;
	this.numOFlike=numOFlike;
	this.Content=Content;
	
	}
	public void set_postOwner(String postOwner)
	{
		
	this.postOwner=postOwner;
	}  
      public String get_postOwner(){
          return postOwner;
      }
      /*********************/
      public void set_numOFlike(int numOFlike)
	{
		
	this.numOFlike=numOFlike;
	}  
      public int get_numOFlike(){
          return numOFlike;
      }
      /*****************/
      
      public void set_Content(String Content)
	{
		
	this.Content=Content;
	}  
      public String get_Content(){
          return Content;
      }
      /*******************/
       public void set_postID(int postID)
	{
		
	this.postID=postID;
	}  
      public int get_postID(){
          return postID;
      }
      public void set_postType(String postType)
  	{
  		
  	this.postType=postType;
  	}  
        public String get_postType(){
            return postType;
        }
        
        public void set_Feelings(String Feelings)
    	{
    		
    	this.Feelings=Feelings;
    	}  
          public String get_Feelings(){
              return Feelings;
          }    
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Posts", list.size() + 1);

		employee.setProperty("postOwner", this.postOwner);
		employee.setProperty("numOFlike", this.numOFlike);
		employee.setProperty("Content", this.Content);
		employee.setProperty("postID", this.postID);
		employee.setProperty("postType", this.postType);
		datastore.put(employee);

		return true;

	}
	public Boolean savePagePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Posts", list.size() + 1);

		employee.setProperty("postOwner", this.postOwner);
		employee.setProperty("numOFlike", this.numOFlike);
		employee.setProperty("Content", this.Content);
		employee.setProperty("postID", this.postID);
		employee.setProperty("postType", this.postType);
		employee.setProperty("numberOfSeen", this.numberOfSeen);
		
		datastore.put(employee);

		return true;

	}
	
	public Boolean saveUserPost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Posts", list.size() + 1);

		employee.setProperty("postOwner", this.postOwner);
		employee.setProperty("numOFlike", this.numOFlike);
		employee.setProperty("Content", this.Content);
		employee.setProperty("postID", this.postID);
		employee.setProperty("postType", this.postType);
		employee.setProperty("Feelings", this.Feelings);
		
		datastore.put(employee);

		return true;

	}
	
}
