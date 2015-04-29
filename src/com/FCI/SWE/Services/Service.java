package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.UserEntity;

import java.io.IOException;  
import java.io.PrintWriter;

import com.FCI.SWE.Models.AddFriendRequest;
  



import com.FCI.SWE.Models.HashtagEntity;
import com.FCI.SWE.Models.MessageEntity;
import com.FCI.SWE.Models.Pages;
import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.PostEntity;
import com.google.cloud.sql.jdbc.Connection;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
/*
	@POST
	@Path("/signout")
	public 
	*/
	
	 @POST
	@Path("/AddFriendService")
	public String addFriendService(@FormParam("SenderName") String SenderName,
			@FormParam("ReceiverName") String ReceiverName) {
		JSONObject object = new JSONObject();
		UserEntity user1 = UserEntity.getUsers(SenderName);
		UserEntity user2 = UserEntity.getUsers(ReceiverName);
		if (user1 == null ||user2 == null) {
			object.put("Status", "Failed");

		} else {
		object.put("Status", "OK");
		AddFriendRequest obj=new AddFriendRequest
		(user1.getName(),user2.getName(),user1.getEmail(),user2.getEmail(),"pinding");
		obj.saveRequest();
		}

		return object.toString();

	}
	 
	 @POST
		@Path("/AcceptFriendService")
		public String AcceptFriendService(@FormParam("SenderName") String SenderName,
				@FormParam("ReceiverName") String ReceiverName) {
			JSONObject object = new JSONObject();
			AddFriendRequest obj=new AddFriendRequest(SenderName,ReceiverName);
			if (!obj.Acept_request(SenderName, ReceiverName)) {
				object.put("Status", "Failed");

			} else {
			object.put("Status", "you are now friends");
			
		
			}

			return object.toString();

		}
		 
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	@POST
	@Path("/SearchService")
	public String SearchService(@FormParam("uname") String uname) {
		Vector<UserEntity> users = UserEntity.SearchUser(uname);
		JSONArray returnedJson = new JSONArray();
		System.out.println(users);
		for(UserEntity user: users)
		{
			JSONObject object=new JSONObject();
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			
			returnedJson.add(object) ;
		}

		return returnedJson.toString();

	}
	 @POST
		@Path("/CreatePostService")
	
	 public String CreatePostService(@FormParam("postOwner") String postOwner,
				@FormParam("Feelings") String Feelings , @FormParam("Content") String Content) {
		 String urlParameters = "postOwner="+ postOwner   +"&Content="+Content + "&Feelings="+Feelings;
			String serviceUrl = "http://localhost:8888/rest/createPostService";
			String retJson = Connection.connect(
					"http://localhost:8888/rest/createPostService", urlParameters,
					"POST", "application/x-www-form-urlencoded;charset=UTF-8");	
			JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("OK"))
					return "Post created Successfully";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "Failed";
			

		}

		@POST
		@Path("/createpageService")
		public String CreatePage(@FormParam("pageName") String pageName,
				@FormParam("pageType") String pageType,
				@FormParam("pageCategory") String pageCategory,
				@FormParam("pageOwner") String pageOwner) {
			JSONObject object = new JSONObject();
			Pages page = new Pages(pageName, pageType, pageCategory,
					pageOwner	);
			if (!page.checkPage()) 
			{
				Pages page1 = new Pages(pageName, pageType, pageCategory,
						pageOwner	);
			} 
			else 
			{
				object.put("Status",  "This Page Name is already exist");
			}
			return object.toString();
		}
		
		@POST
		@Path("/MsgService")
		public String MSG(@FormParam("uname") String send_name,@FormParam("uname2") String rec_name,@FormParam("content") String Content) {
			
	        JSONObject json = new JSONObject();
	        UserEntity userEntity =  new UserEntity(); 
	       userEntity = userEntity.getUser(send_name);
	       // userEntity = User.getCurrentActiveUser();
	        
	        UserEntity userEntity2 =   new UserEntity();
	        userEntity2= userEntity2.getUser(rec_name);
	        
	        if (userEntity==null && userEntity2==null && Content==null) {
				json.put("Status", "Failed");
			}
			else {
				json.put("Status", "OK");
				MessageEntity Msg= new MessageEntity 
						(send_name,userEntity.getId(),rec_name,userEntity2.getId(),Content);
				Msg. saveMessage();
			}
			
				return json.toString();
		
	}
		
		@POST
		@Path("/viewTimelineService")
		public String viewTimeline(@FormParam("hashTagName") String hashTagName) {
			PostEntity post  = new PostEntity();
			Vector<PostEntity> posts = post.getPosts(hashTagName);
			
			JSONArray array = new JSONArray();
			//if(posts == null)
				//json.put("Status", "Failed");
			//else
			//{
				//json.put("Status", "OK");
				for(int i=0 ; i<posts.size() ; i++)
				{
					JSONObject json = new JSONObject();
					json.put("owner", posts.get(i).getOwner());
					json.put("content", posts.get(i).getContent());
					array.add(json);
				}
			//}
				
			return array.toJSONString();
		}
		
		@POST
		@Path("/viewTopTrendsService")
		public String viewTopTrends() {
			HashtagEntity hashTag  = new HashtagEntity();
			List<HashtagEntity> TopTrends = hashTag.getTopTrends();
			
			JSONArray array = new JSONArray();
			//if(posts == null)
				//json.put("Status", "Failed");
			//else
			//{
				//json.put("Status", "OK");
				for(int i=0 ; i<TopTrends.size() ; i++)
				{
					JSONObject json = new JSONObject();
					json.put("name", TopTrends.get(i).getName());
					array.add(json);
				}
			//}
				
			return array.toJSONString();
		}
		@POST
		@Path("/LikePageService")
		public String likePage(@FormParam("name") String name ){
			
			Pages page = new Pages();
			JSONObject json = new JSONObject();
			if(page.update(name))
				json.put("Status", "OK");
			else
				json.put("Status", "Failed");
			return json.toJSONString();
		}

}