package com.maia.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maia.cursomc.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

	@GetMapping
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1, "Cursos");
		Categoria cat2 = new Categoria(2, "Escritório");

		List<Categoria> list = new ArrayList<>();
		list.add(cat1);
		list.add(cat2);

		return list;
	}
}
