package com.springboot.swt.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.swt.project.ServiceImpl.BatchServiceImpl;
import com.springboot.swt.project.ServiceImpl.EmailSenderImpl;
import com.springboot.swt.project.ServiceImpl.StudentServiceImpl;
import com.springboot.swt.project.ServiceImpl.UserServiceImpl;
import com.springboot.swt.project.entity.Batch;
import com.springboot.swt.project.entity.Student;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private UserServiceImpl userserviceimpl;

	@Autowired
	private StudentServiceImpl studentServiceImpl;

	@Autowired
	private BatchServiceImpl batchservicesimpl;

	@Autowired
	EmailSenderImpl emailsenderimp;

	@RequestMapping("/dashboard")
	public String getAdminDashboard(HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		return "admindashboard";
	}

	@RequestMapping("/allBatches")
	public String createBatchPage(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		model.addAttribute("batches", batchservicesimpl.getAllBatches());
		return "newbatch";
	}

	@RequestMapping("/startbatch")
	public ResponseEntity startbatch(@RequestParam("id") String batchId, HttpServletRequest request, Model model) {
		batchservicesimpl.startBatchByID(batchId);
		return new ResponseEntity(batchservicesimpl.sendAllBatches(), HttpStatus.OK);
	}

	@RequestMapping("/endbatch")
	public ResponseEntity endbatch(@RequestParam("id") String batchId, HttpServletRequest request, Model model) {
		batchservicesimpl.endBatchByID(batchId);
		return new ResponseEntity(batchservicesimpl.sendAllBatches(), HttpStatus.OK);
	}

	@RequestMapping("/approval")
	public String getNotAllowedUsers(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		model.addAttribute("data", userserviceimpl.getNotAllowedUsers());
		return "approval";
	}

	@ResponseBody
	@RequestMapping("/approval/allow")
	public String allowByID(@RequestParam("id") String id, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		userserviceimpl.allowOrBlockUserByID(id, "Allowed");
		return "Allowed";
	}

	@ResponseBody
	@RequestMapping("/approval/block")
	public String blockByID(@RequestParam("id") String id, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		userserviceimpl.allowOrBlockUserByID(id, "Blocked");
		return "Blocked";
	}

	@ResponseBody
	@RequestMapping("/newbatch")
	public ResponseEntity<String> newBatch(@RequestParam String name, HttpServletRequest request) {

		Batch batch = new Batch();
		batch.setBatchTopic(name);
		Batch temp = batchservicesimpl.newBatch(batch);
		if (temp == null) {

			return new ResponseEntity<>("Something Went Wrong", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("Batch Created SuccessFully ", HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping("/sendemail")
	public ResponseEntity<String> sendEmail(@RequestParam String to, @RequestParam String subject,
			@RequestParam String massage) {
		return new ResponseEntity<>(emailsenderimp.sendEmail(to, subject, massage), HttpStatus.OK);
	}

	@RequestMapping("/VolunteerApproval")
	public String VApproval(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		// model.addAttribute("data", studentAttendanceServiceImpl.findAllStudent());
		return "VolunteerApproval";
	}

	@RequestMapping("/ManageBatches")
	public String ManageBatches(HttpServletRequest request, Model model) {
		return "manageBatches";
	}

	@RequestMapping("/AllStudentDetails")
	public String AllStudentDetail(HttpServletRequest request) {
		return "AllStudentDetails";
	}

	@ResponseBody
	@RequestMapping("/VolunteerApproval/allow")
	public String approveVolunteerByID(@RequestParam("id") String id, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		userserviceimpl.allowOrBlockVolunteerByID(id, "Volunteer");
		return "Volunteer";
	}

	@ResponseBody
	@RequestMapping("/VolunteerApproval/block")
	public String blockVolunteerByID(@RequestParam("id") String id, HttpServletRequest request) {
		if (request.getSession().getAttribute("admin") == null)
			return "redirect:/swt/login";
		userserviceimpl.allowOrBlockVolunteerByID(id, "Student");
		return "Student";
	}

	@RequestMapping("/getBatchDetails")
	public String getBatchDetails(@RequestParam("id") String batchId, HttpServletRequest request) {

		List<Student> studentList = studentServiceImpl.findByBatch(batchId);
		HttpSession session = request.getSession();
		session.setAttribute("studentList", studentList);

		return "batchDetails";
	}

	@RequestMapping("/deletebatch")
	public void deletebatch(@RequestParam("id") String batchId, HttpServletRequest request) {
		batchservicesimpl.deleteBatchByID(batchId);

	}

	@RequestMapping("/getBatch")
	public ResponseEntity getBatch(Model model, HttpServletRequest request) {
		return new ResponseEntity<>(batchservicesimpl.sendAllBatches(), HttpStatus.OK);
	}

	@RequestMapping("/getAllStudentByBatchId")
	public ResponseEntity getAllStudent(@RequestParam("batchId") String batchId) {
		System.out.println(studentServiceImpl.findByBatch(batchId));
		return new ResponseEntity<>(studentServiceImpl.findByBatch(batchId), HttpStatus.OK);
	}

	@RequestMapping("/findActivebatches")
	public ResponseEntity findActivebatches(HttpServletRequest session) {
		List<Batch> batchlist = batchservicesimpl.findByCurrentStatus("Active");

		return new ResponseEntity(batchlist, HttpStatus.OK);
	}

}
