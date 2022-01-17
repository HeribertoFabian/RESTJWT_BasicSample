package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityController {
	
	@GetMapping("/acceso_solo_jwt")
	public ResponseEntity<?> getInformacionBancaria(){
		List<String> movimientos = obtenerUltimosMovimientosBancarios();
		if(movimientos != null) {
			return new ResponseEntity<>(movimientos, HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	
	public List<String> obtenerUltimosMovimientosBancarios(){
		List<String> movimientos = new ArrayList<>();
		
		movimientos.add("20MXN");
		movimientos.add("-20MXN");
		movimientos.add("4500MXN");
		movimientos.add("660MXN");
		movimientos.add("2MXN");
		movimientos.add("1220MXN");
		
		return movimientos;
	}

}
