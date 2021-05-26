package com.profeco.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.profeco.entidades.Usuario;
import com.profeco.modulocomercio.ModuloComercio;
import com.profeco.moduloconsumidor.ModuloConsumidor;
import com.profeco.moduloprofeco.ModuloProfeco;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profeco")
public class ProfecoREST {

    @PostMapping("login")
    public ResponseEntity<String> logear(@RequestBody Usuario usuario) {
        System.out.println("Metodo login");
        boolean status = ValidarLogin.validar(usuario);
        String token = null;
        if (status) {

            try {
                Algorithm algorithm = Algorithm.HMAC256("millave");
                token = JWT.create()
                        .withIssuer("auth0")
                        .sign(algorithm);
                System.out.println(token);
            } catch (JWTCreationException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping("subirProducto")
    public ResponseEntity subirProducto(@RequestBody String producto) {
        System.out.println("Agregando oprooducto " + producto);
        try {
            ModuloComercio modulo = new ModuloComercio();
            modulo.subirProducto(producto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("desplegarSanciones")
    public ResponseEntity<String> desplegarSanciones(){
        System.out.println("Desplegar sanciones");
        try {
            ModuloComercio modelo = new ModuloComercio();
            String jsonString = modelo.desplegarSanciones();
            return ResponseEntity.ok(jsonString);
        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("subirSancion")
    public ResponseEntity subirSancion(@RequestBody String sancion) {
        System.out.println("Agregando sancion " + sancion);
        try {
            ModuloProfeco modulo = new ModuloProfeco();
            modulo.subirSanciones(sancion);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("eliminarProducto")
    public ResponseEntity eliminarProducto(@RequestBody String idProducto){
        System.out.println("Eliminando producto " + idProducto);
        try {
            ModuloComercio modulo = new ModuloComercio();
            modulo.eliminarProducto(idProducto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("desplegarOfertas")
    public ResponseEntity<String> desplegarOferta() {
        System.out.println("mostrando ofertas");
        try {

            ModuloConsumidor modulo = new ModuloConsumidor();
            String jsonString = modulo.desplegarOfertas();
            return ResponseEntity.ok(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

//    @GetMapping("ofertas")
//    public ResponseEntity<String> obtenerOfertas() {
//        if (!daoProducto.obtenerOfertas().isEmpty()) {
//            Gson gson = new Gson();
//            String json = gson.toJson(daoProducto.obtenerOfertas());
//            return ResponseEntity.ok(json);
//        }
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
    /*
    @GetMapping("consumidor/{nombreProducto}")
    public ResponseEntity<List<Producto>> compararProductos(@PathVariable("nombreProducto")String nombre){
            //Consumidor consumidor = new Consumidor(this.daoProducto);
            System.out.println("Entrando a");
            return ResponseEntity.ok(consumidor.getProductByName(nombre));
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
     */
}
