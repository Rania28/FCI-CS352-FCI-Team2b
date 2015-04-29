package com.FCI.SWE.Models;

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

public class PostEntity {
	
	String owner;
	long owner_id;
	String content;
	String type;
	String timeline;
	int likes_number;
	int seen_number;
	String privacy;
	String feeling;
	
	
	public PostEntity(String owner,String content){
		this.owner=owner;
		this.content=content;
	}
	public PostEntity(String owner,long owner_id,String content,String type,String timeline,int likes_number,int seen_nember,String privacy){
		this.owner=owner;
		this.owner_id=owner_id;
		this.content=content;
		this.type=type;
		this.timeline=timeline;
		this.likes_number=likes_number;
		this.seen_number=seen_nember;
		this.privacy=privacy;
	}
	
	public PostEntity(String owner,long owner_id,String content,String type,String timeline,String privacy,String feeling){
		this.owner=owner;
		this.owner_id=owner_id;
		this.content=content;
		this.type=type;
		this.timeline=timeline;
		this.privacy=privacy;
		this.feeling=feeling;
	}
	
	public PostEntity(String owner,long owner_id,String content,String type,String timeline){
		this.owner=owner;
		this.owner_id=owner_id;
		this.content=content;
		this.type=type;
		this.timeline=timeline;
		
	}
	
	public PostEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setOwner(String owner){
		this.owner=owner;
	}
	public void setOwner_id(long owner_id){
		this.owner_id=owner_id;
	}
	public void setContent(String content){
		this.content=content;
	}
	public void setType(String type){
		this.type=type;
	}
	public void setTimeline(String timeline){
		this.timeline=timeline;
	}
	public void setLikes_number(int likes_number){
		this.likes_number=likes_number;
	}
	public void setSeen_number(int seen_number){
		this.seen_number=seen_number;
	}
	public void setPrivacy(String privacy){
		this.privacy=privacy;
	}
	public String getOwner(){
		return owner;
	}
	public long getOwner_id(){
		return owner_id;
	}
	public String getContent(){
		return content;
	}
	public String getType(){
		return type;
	}
	public String getTimeline(){
		return timeline;
	}
	public int likes_number(){
		return likes_number;
	}
	public int seen_number(){
		return seen_number;
	}
	public String getPrivacy(){
		return privacy;
	}
	public void setFeeling(String feeling){
		this.feeling=feeling;
	}
	public String getFeeling(){
		return feeling;
	}
	
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Posts", list.size() + 1);

		employee.setProperty("owner", this.owner);
		employee.setProperty("owner_id", this.owner_id);
		employee.setProperty("content", this.content);
		employee.setProperty("type", this.type);
		employee.setProperty("timeline", this.timeline);
		//employee.setProperty("likes_number", this.likes_number);
		//employee.setProperty("seen_number", this.seen_number);
		employee.setProperty("privacy", this.privacy);
		employee.setProperty("feeling", this.feeling);
		datastore.put(employee);

		return true;

	}
	
	

	public Vector<PostEntity> getPosts(String timeline){
		 
		Vector<PostEntity>posts=new Vector<PostEntity>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("timeline").toString().equals(timeline))
			{   
				PostEntity post=new PostEntity(entity.getProperty("owner").toString() ,entity.getProperty("content").toString());
				posts.add(post);
			}
		}
	
		return posts;
	}
	
	public static PostEntity parsePostinfo(String json)
	{
		JSONParser parser = new JSONParser();
	    PostEntity post=new PostEntity();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			
			post.setOwner(object.get("owner").toString());
			post.setContent(object.get("content").toString());
			
			
			//return user;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;

	}
	public Vector<PostEntity> getHomePost(String user){
		 RequestEntity request =new RequestEntity();
		Vector<PostEntity>posts=new Vector<PostEntity>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("owner").toString().equals(user) || 
					request.isFriend(user, entity.getProperty("owner").toString()))
			{   
				PostEntity post=new PostEntity(entity.getProperty("owner").toString() ,entity.getProperty("content").toString());
				posts.add(post);
			}
		}
	
		return posts;
	}
	
	public Vector<PostEntity> getProfilePost(String timeline){
		 RequestEntity request =new RequestEntity();
		Vector<PostEntity>posts=new Vector<PostEntity>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService(); 
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("timeline").toString().equals(timeline))
			{   
				PostEntity post=new PostEntity(entity.getProperty("owner").toString() ,entity.getProperty("content").toString());
				posts.add(post);
			}
		}
	
		return posts;
	}
	
	
		
	

}
