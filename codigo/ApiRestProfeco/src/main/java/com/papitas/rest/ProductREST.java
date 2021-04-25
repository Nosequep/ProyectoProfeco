package com.papitas.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.ProductoDAO;
import entities.Producto;

@RestController
@RequestMapping("products")
public class ProductREST {
	
	
	@GetMapping
	public ResponseEntity<List<Producto>> getProduct(){
		ProductoDAO daoProducto = new ProductoDAO(); 
		
		return ResponseEntity.ok(daoProducto.obtener("chetis"));
	}

	@RequestMapping(value="{nombreProducto}")
	public ResponseEntity<List<Producto>> getProductById(@PathVariable("nombreProducto")String nombre){
		ProductoDAO daoProducto = new ProductoDAO(); 
		
		return ResponseEntity.ok(daoProducto.obtener(nombre));
	}
}
