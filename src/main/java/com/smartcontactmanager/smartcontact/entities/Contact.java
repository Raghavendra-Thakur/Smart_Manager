package com.smartcontactmanager.smartcontact.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String cname;
    private String cemail;
    private String cnickname;
    private String cimage;
    private String about;
    private String phone;
    @ManyToOne
    @JsonIgnore
    private UserEntity userEntity;
    
    
    
	public Contact() {
		
	}
	
	public Contact(int cid, String cname, String cemail, String cnickname, String cimage, String about, String phone,
			UserEntity userEntity) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.cemail = cemail;
		this.cnickname = cnickname;
		this.cimage = cimage;
		this.about = about;
		this.phone = phone;
		this.userEntity = userEntity;
	}
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCemail() {
		return cemail;
	}
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}
	public String getCnickname() {
		return cnickname;
	}
	public void setCnickname(String cnickname) {
		this.cnickname = cnickname;
	}
	public String getCimage() {
		return cimage;
	}
	public void setCimage(String cimage) {
		this.cimage = cimage;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", cname=" + cname + ", cemail=" + cemail + ", cnickname=" + cnickname
				+ ", cimage=" + cimage + ", about=" + about + ", phone=" + phone + ", userEntity=" + userEntity + "]";
	}

    
}
