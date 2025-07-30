package com.nataliapavez.libralia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraliaApplicationController {

    public static void main(String[] args) {

		SpringApplication.run(LibraliaApplicationController.class, args);
	}
}









//		// PRUEBA SOLO CREACIÓN DE USUARIO
//		Usuario usuario = Principal.crearUsuarioDesdeConsola();
//		usuario.mostrarInfoUsuario();	principal.ejecutar();
//	}
//}
//
//		// PRUEBAa SOLO BÚSQUEDA DE LIBROS Y AGREGAR UNO
//		Principal.buscarLibro(usuario);
//
//		// VERIFICAMOS SI EL LIBRO FUE AGREGADO
//		System.out.println("Libros leídos por el usuario:");
//		usuario.getListaLibrosLeidos().forEach(libro ->
//				System.out.println("- " + libro.getTituloConAutor())
//		);


// PROXIMA MEJORA
//		var consumoApi = new ConsumoAPI();
//		var jsonLibro = consumoApi.obtenerDatos("https://www.googleapis.com/books/v1/volumes?q=yo+antes+de+ti");
//		System.out.println(jsonLibro);
//
//		ConversorLibroService conversor = new ConversorLibroService();
//
//		var infoLibro = conversor.convertir(jsonLibro, VolumeInfo.class);
//		System.out.println(infoLibro);
//
//		var jsonAutor = consumoApi.obtenerDatos("https://openlibrary.org/authors/OL5365446A.json");
//		System.out.println(jsonAutor);
//
//		var infoAutor = conversor.convertir(jsonAutor, AutorInfoOpenLibrary.class);
//		System.out.println(infoAutor);
//


