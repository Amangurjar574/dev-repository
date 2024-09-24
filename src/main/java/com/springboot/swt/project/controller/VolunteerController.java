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

import com.springboot.swt.project.ServiceImpl.BatchServiceImpl;
import com.springboot.swt.project.ServiceImpl.StudentServiceImpl;
import com.springboot.swt.project.ServiceImpl.UserServiceImpl;
import com.springboot.swt.project.entity.Batch;
import com.springboot.swt.project.entity.Student;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/valunteer")
public class VolunteerController {

	@Autowired
	BatchServiceImpl batchservice;
	@Autowired
	UserServiceImpl userserviceimpl;
	@Autowired
	StudentServiceImpl StudentServiceImpl;

	@RequestMapping("/findActivebatches")
	public ResponseEntity findActivebatches(Model model, HttpServletRequest session) {
		List<Batch> batchlist = batchservice.findByCurrentStatus("Active");
		if (batchlist.size() == 0) {
			model.addAttribute("activebatch", batchlist);
			return null;
		}
		return new ResponseEntity(batchlist, HttpStatus.OK);
	}

	@RequestMapping("/findallstudent")
	public String findallstudent(@RequestParam("batchId") String batchId, Model model, HttpServletRequest session) {
		List<Student> studentlist = StudentServiceImpl.findByBatch(batchId);
		// List<Student> absentlist = StudentAttendanceServiceImpl.findByBatch(batchId);
		if (studentlist.size() != 0)
			model.addAttribute("studentlist", studentlist);
		// model.addAttribute("absentlist", absentlist);
		return "userattendance";
	}

	@RequestMapping("/userattendance")
	public String userattendance(HttpServletRequest session) {

		return "userattendance";
	}

	@RequestMapping("/studentattendance/present")
	public ResponseEntity markAttendancepresent(@RequestParam("rollNo") String rollNo,
			@RequestParam("batchId") String batchId, HttpServletRequest session) {
		Student student = userserviceimpl.markAttendancepresent(rollNo, batchId);
		if (student != null)
			return new ResponseEntity<>(student, HttpStatus.OK);
		return new ResponseEntity<>(student, HttpStatus.FORBIDDEN);

	}

	@RequestMapping("/studentattendance/absent")
	public ResponseEntity markAttendanceAbsent(@RequestParam("rollNo") String rollNo,
			@RequestParam("batchId") String batchId, HttpServletRequest session) {
		Student student = userserviceimpl.markAttendanceAbsent(rollNo, batchId);
		if (student != null)
			return new ResponseEntity<>(student, HttpStatus.OK);
		return new ResponseEntity<>(student, HttpStatus.FORBIDDEN);

	}

	@RequestMapping("/volunteerMarks")
	public String volunteerMarks(HttpServletRequest request) {
		List<Batch> activebatches = batchservice.findByCurrentStatus("Active");
		HttpSession session = request.getSession();
		session.setAttribute("activebatch", activebatches);
		return "volunteerMarks";
	}

	@RequestMapping("/setmarks")
	public ResponseEntity setStudentMarks(@RequestParam String rollNo, String batchId, String marks,
			HttpServletRequest request) {
		Student student = StudentServiceImpl.setMarks(rollNo, batchId, Integer.parseInt(marks));
		if (student == null)

			return new ResponseEntity(student, HttpStatus.BAD_REQUEST);

		return new ResponseEntity(student, HttpStatus.OK);

	}
}
