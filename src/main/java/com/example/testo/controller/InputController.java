package com.example.testo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testo.entity.Barang;
import com.example.testo.repository.BarangRepository;
import com.example.testo.utility.Response;

@RestController
@RequestMapping("/greeting")
public class InputController {
	
	@Autowired
	private BarangRepository repos;
	
	@GetMapping
	public ResponseEntity findAll(@RequestParam Map<String, String> request)  {
		return Response.ok(repos.findAll());
	}
	
	@PostMapping
	public ResponseEntity add(@RequestBody Barang em) {
		return Response.ok(repos.save(em));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") Long id) {
		return Response.ok(repos.findById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateEmpl(@PathVariable("id") Long id, @RequestBody Barang em) {
		Barang b = repos.findById(id).orElseThrow();
		b.setHarga(em.getHarga());
		b.setNama(em.getNama());
		b.setStok(em.getStok());
		
		return Response.ok(repos.save(b));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteEmpl(@RequestParam("id")Long id) {
		repos.deleteById(id);
		return Response.ok("Deleted Successfully");
	}
	
}
