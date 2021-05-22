package com.profeco.rest;

import com.profeco.controladores.UsuarioJpaController;
import com.profeco.entidades.Usuario;
import componente.comercio.Comercio;
import componente.consumidor.Consumidor;
import componente.profeco.Profeco;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.ProductoDAO;
import dao.SancionDAO;
import entities.Producto;
import entities.Sancion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("profeco")
public class ProfecoREST {
	private ProductoDAO daoProducto;
        private SancionDAO daoSancion;
        
        public ProfecoREST(){
            this.daoProducto = new ProductoDAO();
            this.daoSancion = new SancionDAO();
        }

        @GetMapping
        public void hola(){
            
        }
        
	@GetMapping("consumidor/{nombreProducto}")
	public ResponseEntity<List<Producto>> compararProductos(@PathVariable("nombreProducto")String nombre){
		Consumidor consumidor = new Consumidor(this.daoProducto);
                System.out.println("Entrando a");
		return ResponseEntity.ok(consumidor.getProductByName(nombre));
	}
        
       
        @PostMapping()
	public ResponseEntity<Sancion> determinarSancion(@RequestBody String sancion){
            System.out.println("Entra posteo" + sancion);
            Profeco profeco = new Profeco(this.daoSancion);
            Sancion s = profeco.sancionar(sancion);
            return ResponseEntity.ok(s);
	}
        
        @RequestMapping(value="comercio")
	public ResponseEntity<List<Sancion>> showSanciones(){
            Comercio comercio = new Comercio(this.daoSancion);
            return ResponseEntity.ok(comercio.leerSanciones());
	}
        
        
        @RequestMapping(value="prueba")
	public ResponseEntity<Usuario> obtenerUsuario(){
            UsuarioJpaController jpa = new UsuarioJpaController();
		return ResponseEntity.ok(jpa.findUsuario(1));
	}
        
        
}
