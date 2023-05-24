package com.smartcontactmanager.smartcontact.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.apache.coyote.http11.Http11InputBuffer;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.smartcontactmanager.smartcontact.Dao.ContactRepo;
import com.smartcontactmanager.smartcontact.Dao.UserRepo;
import com.smartcontactmanager.smartcontact.entities.Contact;
import com.smartcontactmanager.smartcontact.entities.UserEntity;
import com.smartcontactmanager.smartcontact.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepo userRepo ;
	
	@Autowired
	ContactRepo contactRepo;

	private String name;
	
	@ModelAttribute
	public 	void commanAttri(Model model ,Principal principal) {
		String username = principal.getName();
    	
//    	System.out.println(username);
    	
    	//get user using username
    	
    	UserEntity user = userRepo.getuserEntitybyname(username);
    	
//    	System.out.println(user);
    	
    	model.addAttribute("user" , user);
	}
	
    @RequestMapping("/index")
    public String dashboard(Model model ) {
    	
    	
    	
        return "normal/User_dashboard";
    }
    
    @GetMapping("/addcontact")
    public String openAddContact(Model model) {
    	
    	model.addAttribute("contact", new Contact());
    	
    	
    	
    	return "normal/contactform";
		
	}
    
    
    
    @PostMapping(value = ("/addcontactpro") )
    public String processAddContactForm(
    		@ModelAttribute Contact contact,
    		@RequestParam("profileimage")MultipartFile file,
    		HttpSession session,Model m,Principal principal ) {
    	
    	try {	
    	String	name = principal.getName();
  
    	UserEntity user = this.userRepo.getuserEntitybyname(name);
    	contact.setUserEntity(user);
    	
    	//uploading file
    	
    	if(file.isEmpty()) {
//    		System.out.println("empty");
    		contact.setCimage("contact.png");
    	}else {
    		//process file
    		
    		contact.setCimage(file.getOriginalFilename());
    		
    		File file2 = new ClassPathResource("static/image").getFile();
    		
    		Path path = Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
    		
    		Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
    		
    		System.out.println("image uploaded");
    	}
    	
    	
    	
    	user.getContact().add(contact);
    	
    	this.userRepo.save(user);
    	
    	
    	session.setAttribute("message", new message("Your Contact is added" , "alert-success"));
    	
    	m.addAttribute("session" , session);
    		
    	
    		return "normal/contactform";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	    	session.setAttribute("message", new message("Internal Error" , "alert-danger"));

			return "normal/User_dashboard";
		}
    }
    
    @GetMapping(value = "/showcontact/{page}")
    public String showContactHandler(@PathVariable("page")Integer page, Model m ,Principal principal) {
    	String name2 = principal.getName();
    	
    	UserEntity user = userRepo.getuserEntitybyname(name2);
    	
    	Pageable pageRequest = PageRequest.of(page, 5);
    	
    	Page<Contact> contacts = contactRepo.findByUserEntity(user , pageRequest );
    	
    	System.out.println(contacts.toString());
    	m.addAttribute ("contacts" ,contacts);
    	m.addAttribute("currentpage", page);
    	m.addAttribute("totalpage", contacts.getTotalPages());
    	
    	return "normal/showcontacts";
    	
    }
    
    @GetMapping(value = "/contact/{cid}")
    public String getContactDetails(@PathVariable("cid") Integer cid , Model m ,MultipartFile file , Principal principal) {
    	
    	String name2 = principal.getName();
    	
    	UserEntity user = userRepo.getuserEntitybyname(name2);
    	
    	
    	Contact contact = contactRepo.findByCidAndUserEntity(cid, user);
    	
    	
    	m.addAttribute("contact", contact);
    	
    	
    	
    	return "normal/contactDetails";
    }
    
    @RequestMapping(value = "deletecontact/{cid}" , method = {RequestMethod.POST , RequestMethod.GET})
    public String deleteContactDetails(@PathVariable("cid") Integer cid , Model m , Principal principal) {
    	
    	String name2 = principal.getName();
    	
    	UserEntity user = userRepo.getuserEntitybyname(name2);
    	
    	
    	Long deleteCon = contactRepo.deleteByCidAndUserEntity(cid, user);
    	
    	return "redirect:/user/showcontact/0";
    }
    
    @PostMapping(value = "updatecontactpro/{cid}")
 public String updateContactDetails(@PathVariable("cid") Integer cid,
		 @RequestParam("profileimage")MultipartFile file
		 ,@ModelAttribute("con") Contact con, Model m , Principal principal) throws IOException {
    	
    	String name2 = principal.getName();
    	
    	UserEntity user = userRepo.getuserEntitybyname(name2);
    	var contact1 = contactRepo.findByCidAndUserEntity(cid, user);
    	
    	if(file.isEmpty()) {
//    		System.out.println("empty");
    		contact1.setCimage(contact1.getCimage());
    	}else {
    		//process file
    		
    		contact1.setCimage(file.getOriginalFilename());
    		
    		File file2 = new ClassPathResource("static/image").getFile();
    		
    		Path path = Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
    		
    		Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
    		
//    		System.out.println("image uploaded");
    	}
    	

    	
    	
    	contact1.setCname(con.getCname());
    	contact1.setCemail(con.getCemail());
    	contact1.setCnickname(con.getCnickname());
    	if(con.getAbout() == null) {
    	contact1.setAbout(contact1.getAbout());
    	}
    	else {
    		contact1.setAbout(con.getAbout());
    	}
    	
    	contact1.setPhone(con.getPhone());
    	
    	System.out.println(cid);
    	
    	contactRepo.save(contact1);
    	
   
    	
    	return "redirect:/user/showcontact/0";
    }
}
