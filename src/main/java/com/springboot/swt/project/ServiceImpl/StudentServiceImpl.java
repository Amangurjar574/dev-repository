package com.springboot.swt.project.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.swt.project.Service.StudentService;
import com.springboot.swt.project.entity.Batch;
import com.springboot.swt.project.entity.Student;
import com.springboot.swt.project.repo.BatchRepo;
import com.springboot.swt.project.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	BatchRepo batchRepo;

	@Override
	public Student markAttendance(String rollNo, String batchId) {

		Batch batch = (batchRepo.findById(batchId)).get();
		Student student = studentRepo.findByRollNoAndBatch(rollNo, batch);

		if (student == null)
			return null;
		student.setAttendanceCount(student.getAttendanceCount() + 1);
		studentRepo.save(student);

		return student;
	}

	@Override
	public List<Student> findByBatch(String batchId) {
		List<Student> list = studentRepo.findAll();
		List<Student> currentlist = new ArrayList<>();
		for (Student s : list) {
			if (batchId.equals(s.getBatch().getBatchId()))
				currentlist.add(s);

		}
		return currentlist;
	}

	@Override
	public List<Student> findAllStudent(String name) {
		return studentRepo.findAll();
	}

	@Override
	public Student setMarks(String rollNo, String batchId, Integer marks) {
		Batch batch = (batchRepo.findById(batchId)).get();
		Student student = studentRepo.findByRollNoAndBatch(rollNo, batch);

		if (student == null)
			return null;
		ArrayList<Integer> tempList = new ArrayList<>(student.getMarks());

		tempList.add(marks);
		student.setMarks(tempList);
		studentRepo.save(student);
		return student;
	}

}
