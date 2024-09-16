package com.springboot.swt.project.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.swt.project.ServiceImpl.BatchServiceImpl;
import com.springboot.swt.project.ServiceImpl.UserServiceImpl;
import com.springboot.swt.project.entity.Batch;
import com.springboot.swt.project.entity.Student;
import com.springboot.swt.project.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userserviceimpl;
	@Autowired
	private BatchServiceImpl batchservicesimpl;

	// Login Controller
	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, @RequestParam String email, String password) {
		ModelAndView modal = new ModelAndView();
		User temp = userserviceimpl.login(email, password);
		if (temp != null && temp.getAllowed().equals("Allowed")) {
			HttpSession session = request.getSession();
			if(temp.getRole().equalsIgnoreCase("Student")||temp.getRole().equalsIgnoreCase("Volunteer"))
			{				
 				session.setAttribute("user", temp) ;
				modal.setViewName("redirect:dashboard");
				return modal;
			}
			if(temp.getRole().equalsIgnoreCase("Admin"))
			{					
				session.setAttribute("admin", temp);
				modal.setViewName("redirect:/admin/dashboard");
				return modal;
			}
		}

		if (temp == null) {
			modal.addObject("error", "Incorrect Email or password ");
		} else if (temp.getAllowed().equals("Not Allowed")) {
			modal.addObject("error", "Wait For Approval");
		} else {
			modal.addObject("error", "Account Blocked Contact Admin Portal");
		}
		modal.setViewName("redirect:/swt/login");
		return modal;
	}

	@RequestMapping("/dashboard")
	public String getDashBoard(HttpServletRequest request) {
		if(request.getSession().getAttribute("user")==null) return "redirect:/swt/login";
		return "dashboard";
	}

	@RequestMapping("/regis")
	public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
		user.setRole("Student");
		User temp = userserviceimpl.register(user);
		ModelAndView modal = new ModelAndView();
		if (temp == null) {
			modal.addObject("error", "Something Went Wrong try Again Later");
			modal.setViewName("redirect:/swt/regis");
			return modal;
		}
		modal.setViewName("redirect:/swt/login");
		return modal;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/swt/";
	}

	@RequestMapping("/dashboard/attendance")
	public  String getAttendance(@RequestParam("student") String encodedJson,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("user")==null) return "redirect:/swt/login";
		HttpSession session = request.getSession();
		 try {
		 ObjectMapper objectMapper = new ObjectMapper();
		 Student studentObject = objectMapper.readValue(encodedJson, Student.class);
 		model.addAttribute("studentdecodedobject", studentObject);    
		 }catch(Exception e) {}
		return "attendance";
	}
	
//	finding user by email for otp verification 
	@RequestMapping("/otp")
	public ModelAndView demo(@ModelAttribute("user") User user, BindingResult bindingResult) {
		if(userserviceimpl.finder(user)) // if email is found then ok otherwise error will be presented 
		{
		userserviceimpl.otpSend(user.getEmail());

		return new ModelAndView("redirect:/views/Otp.jsp","email",user.getEmail()); // now it will redirect the page to otp.jsp 
		}
		
	else return new ModelAndView("redirect:/views/forgetPassword.jsp" , "error" , "email is not registered");
	}
	@RequestMapping("/verify")
	public ResponseEntity<Map<String, Object>> otpVerify(@RequestParam String otp, String email1) {
	    User user = userserviceimpl.getUser(email1);

	    if (user.getOtp().equals(otp)) { // if otp is matched 
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("redirectUrl", "/views/PasswordReset.jsp?email="+email1+"");
	        response.put("email", user.getEmail());
	        return ResponseEntity.ok(response);
	    } else {
	      
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", false);
	        response.put("errorMessage", "OTP is wrong");
	        response.put("email", email1);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	
	@RequestMapping("/resetpass")
	public ModelAndView resetPassword(@RequestParam("email") String email , String pass1 , String pass2)
	{
		
		userserviceimpl.resetPassword(email , pass1);
		return new ModelAndView("redirect:/swt/login" , "success" , "password successfully is changed ");
	
	}
	

	@RequestMapping("/marks")
	public String getStudentMarks(HttpServletRequest request,@RequestParam String id) {
		if(request.getSession().getAttribute("user")==null) return "redirect:/swt/login";
		List<Integer> marksList = userserviceimpl.getMarksList(id);
		HttpSession session=request.getSession();
 		session.setAttribute("marksList",marksList) ;

		return "studentmarks";
	}
	
	@RequestMapping("/allbatches")
	public ResponseEntity createBatchPage(HttpServletRequest request, Model model) {
		
		List<Batch> batches = batchservicesimpl.getAllBatches();
		return new ResponseEntity<>(batches, HttpStatus.OK);
	}
	@RequestMapping("/enrollstudent")
	public  ResponseEntity enrollstudent(@RequestParam String batchId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User session1 = (User) session.getAttribute("user");
 		if (session1 != null)
		{
			String cuurentstatus=  userserviceimpl.enrollstudent(batchId, session1);
			return  new ResponseEntity<>(cuurentstatus, HttpStatus.OK);
		}
		return new ResponseEntity<>(null ,HttpStatus.FORBIDDEN);
	}
	@RequestMapping("/findVolunteer")
	public ResponseEntity findVolunteer (@RequestParam String name,HttpServletRequest request)
	{
		List<Student> list =(List<Student>)userserviceimpl.getAllStudent(name);
		return  new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	@RequestMapping("/find/student/batch")
	public ResponseEntity findStudentBatch(HttpServletRequest request, Model model ) {
		HttpSession session = request.getSession();
		User session1 = (User) session.getAttribute("user");
		 List<Student> batches = userserviceimpl.findStudentBatch(session1);
		return new ResponseEntity<>(batches, HttpStatus.OK);
	}

}
