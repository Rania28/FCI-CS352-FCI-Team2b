package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HashtagEntity {
   
	String name;
	String owner;
	//String post;
	long id;
	Integer numPosts;
	
	public HashtagEntity(String name,String owner/*,String post,long id*/){
		this.name=name;
		this.owner=owner;
		//this.post=post;
		//this.id=id;
	}
	
	public HashtagEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setName(String name){
		this.name=name;
	}
	public void setOwner(String owner){
		this.owner=owner;
	}
	/*public void setPost(String post){
		this.post=post;
	}*/
	public void setId(long id){
		this.id=id;
	}
	
	public void setNumPosts(Integer numPosts)
	{
		this.numPosts  = numPosts;
	}
	public String getName(){
		return name;
	}
	public String getOwner(){
		return owner;
	}
	/*public String getPost(){
		return post;
	}*/
	public long getId(){
		return id;
	}
	public Integer getNumPosts()
	{
		return numPosts;
	}
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
        
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").equals(this.name))
			{   
				numPosts = Integer.parseInt(entity.getProperty("numPosts").toString());
				entity.setProperty("numPosts", numPosts+1);
				datastore.put(entity);
				return true;
			}
		}
		Entity employee = new Entity("Hashtag", list.size() + 1);

		employee.setProperty("name", this.name);
		employee.setProperty("owner", this.owner);
		employee.setProperty("numPosts", 1);
		//employee.setProperty("post", this.post);
		datastore.put(employee);

		return true;

	}
	
	public Vector<String> getPosts(String id){
		 
		Vector<String>post=new Vector<String>();
		String temp = "";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("id").equals(id))
			{   
				temp=entity.getProperty("owner").toString() + "\n" + entity.getProperty("post").toString();
				post.add(temp);
			}
		}
	
		return post;
	}
	public List<HashtagEntity> getTopTrends()
	{
		List<HashtagEntity> hashTags = new ArrayList<HashtagEntity>();
		
		List<HashtagEntity> TopTrends = new ArrayList<HashtagEntity>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			HashtagEntity hashTag = new HashtagEntity(entity.getProperty("name").toString(),
					entity.getProperty("owner").toString());
			hashTag.setNumPosts(Integer.parseInt(entity.getProperty("numPosts").toString()));
			
			hashTags.add(hashTag);	
		}
		
		Collections.sort(hashTags, new Comparator<HashtagEntity>(){
	           public int compare (HashtagEntity h1, HashtagEntity h2){
	               return h1.getNumPosts().compareTo(h2.getNumPosts());
	           }
	       });
		Collections.sort(hashTags, Collections.reverseOrder());
		for(int i=0 ;i<10 ;i++)
		{
			TopTrends.add(hashTags.get(i));
		}
		
		return TopTrends;
	}
	
	
	
	
	public static HashtagEntity parsehashtaginfo(String json)
	{
		JSONParser parser = new JSONParser();
		HashtagEntity hashtag=new HashtagEntity();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			
			hashtag.setName(object.get("name").toString());
			
			
			//return user;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashtag;

	}
	
	
	
	
	
	
	
}
