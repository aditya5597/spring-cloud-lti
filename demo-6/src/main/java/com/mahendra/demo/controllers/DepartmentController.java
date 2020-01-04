package com.mahendra.demo.controllers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendra.demo.daos.DepartmentDAO;
import com.mahendra.demo.models.Department;

@RestController
@RequestMapping("/depts")
public class DepartmentController {

	@Autowired private DepartmentDAO dao;
	
	@GetMapping
	public List<Department> getAll(){
		System.out.println("get all departments");
		return dao.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Department> find(@PathVariable int id)
	{
		Optional<Department> dept = dao.findById(id);
		if(dept.isPresent()) {
			return new ResponseEntity<>(dept.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<String> save(@RequestBody Department dept){
		try {
		Department temp = dao.save(dept);
		return new ResponseEntity<>("Saved new dept "+temp.getDeptId(), HttpStatus.CREATED);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>("Unable to save!",HttpStatus.BAD_REQUEST);
		}
	}
}
