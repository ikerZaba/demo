package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
	private static final GestorBDD gestorBDD = new GestorBDD();
	private static final String API_KEY = "731e41f";
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		gestorBDD.crearTablas();
		ArrayList<Pelicula> peliculas = pedirPeliculasStarWars();
		gestorBDD.insertarListaPeliculas(peliculas);	
		//gestorBDD.getInfo();		
	}

	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }


	private static ArrayList<Pelicula> pedirPeliculasStarWars() {
		String urlPeticionPeliculasTodas = "http://www.omdbapi.com/?apikey="+API_KEY+"&s=Star+Wars";
		
		try {
			//SpringApplication.run(DemoApplication.class, args);
			HttpRequest getPeliculas = HttpRequest.newBuilder()
					.uri(new URI(urlPeticionPeliculasTodas))
					.build();
			HttpClient httpClient = HttpClient.newHttpClient();

			HttpResponse<String> getPeliculasRespuesta = httpClient.send(getPeliculas, HttpResponse.BodyHandlers.ofString()); 
							
			JSONObject jsonRespuesta = new JSONObject(getPeliculasRespuesta.body());
			//System.out.println(jsonPeliculasStarWars);
			JSONArray jsonPeliculasStarWars = jsonRespuesta.getJSONArray("Search");
			ArrayList<Pelicula> listaPeliculas = new ArrayList();

			for (int i=0; i<jsonPeliculasStarWars.length(); i++) {
				JSONObject linea = jsonPeliculasStarWars.getJSONObject(i);
				listaPeliculas.add(new Pelicula(linea.getString("imdbID"),
												linea.getString("Title"),
												Integer.parseInt(linea.getString("Year"))));
			}
			//La petición a la API de tipo SEARCH nos da la opción de buscar todas las películas que contienen Star Wars en su nombre
			//Pero en el cuerpo de su respuesta no se encuentra el atributo PLOT (trama) que es uno de los que se nos pide registrar en la BDD
			String urlPeticionPeliculaIndividual = "http://www.omdbapi.com/?apikey="+API_KEY+"&t=";
			for (Pelicula pelicula : listaPeliculas) {		
			
				HttpRequest getPelicula = HttpRequest.newBuilder()
					.uri(new URI(urlPeticionPeliculaIndividual+pelicula.getTitulo().replace(" ", "+")))//cambiar los espacios por sumas para la peticion
					.build();

				HttpResponse<String> getPeliculaRespuesta = httpClient.send(getPelicula, HttpResponse.BodyHandlers.ofString()); 
							
				jsonRespuesta = new JSONObject(getPeliculaRespuesta.body());
				String trama = jsonRespuesta.getString("Plot");
				pelicula.setTrama(trama);
				}
/* 			for (Pelicula pelicula : listaPeliculas) {
					System.out.println("Titulo: "+pelicula.getTitulo()+" Año: "+pelicula.getAño()+" Trama: "+pelicula.getTrama());
				}*/
			return listaPeliculas;
		} catch (URISyntaxException ex) {
			System.out.println(ex.getMessage());
			System.exit(0); 
		} catch (IOException | InterruptedException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return null;
	}

}
