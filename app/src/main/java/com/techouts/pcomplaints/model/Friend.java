package com.techouts.pcomplaints.model;

import java.io.Serializable;

/**
 * Created by TO-OW109 on 26-03-2018.
 */

public class Friend implements Serializable {
	private String id;
	private String name;
	private String email;
	private String photo;


	public Friend(String id, String name,String email, String photo) {
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

	public String getEmail(){ return  email; }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoto() {
		return photo;
	}
}
