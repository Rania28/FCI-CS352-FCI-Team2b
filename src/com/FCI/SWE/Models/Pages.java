package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Pages {

	private String pageName;
	private String pageType;
	private String pageCategory;
	private int numOfLikes;
	private String pageOwner;
	private Object numberOfLikes;
	
	public Pages(String pageName,String pageType,String pageCategory,int numOfLikes,String pageOwner)
	{
	this.pageName=pageName;
	this.pageType=pageType;
	this.pageCategory=pageCategory;
	this.numOfLikes=numOfLikes;
	this.pageOwner=pageOwner;
	}

	public Pages(String pageName,String pageType,String pageCategory,String pageOwner)
	{
	this.pageName=pageName;
	this.pageType=pageType;
	this.pageCategory=pageCategory;
	this.pageOwner=pageOwner;
	}
	
	public Pages() {
		// TODO Auto-generated constructor stub
	}

	public Boolean checkPage() 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("pageName").toString().equals(pageName)) 
			{
				return true;
			}
		}
		return false;
	}
	public Boolean savePage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity pages = new Entity("Page", list.size() + 1);

		pages.setProperty("pageName", this.pageName);
		pages.setProperty("pageType", this.pageType);
		pages.setProperty("pageCategory", this.pageCategory);
		pages.setProperty("numberOfLikes", this.numberOfLikes);
		pages.setProperty("pageOwner", this.pageOwner);
		
		
		datastore.put(pages);

		return true;

	}
	public boolean update(String name)
	{

		//Vector<String>post=new Vector<String>();
		//String temp = "";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("pageName").equals(name))
			{   
				int likes = Integer.parseInt(entity.getProperty("numberOfLikes").toString());
				entity.setProperty("numberOfLikes", likes+1);
				datastore.put(entity);
			}
		}

		return true;
		
	}
}
