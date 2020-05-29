package com.api.library;

import java.text.Normalizer;
import java.util.regex.Pattern;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		String texto = "Consciência Quântica: Uma nova visão sobre o amor, a morte, e o sentido da vida";
		String nfdNormalizedString = Normalizer.normalize(texto, Normalizer.Form.NFD); 
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		texto = pattern.matcher(nfdNormalizedString).replaceAll("");
		texto = texto.toLowerCase().replaceAll("[^a-zZ-Z1-9]", "-");
		texto = texto.replaceAll("--", "-");
		System.err.println(texto);
	}
}
