package com.thomas.webservice.songs;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thomas.webservice.exception.SongNotFoundException;

@RestController
public class SongController {

	@Autowired
	SongDAO songdao;
	
	@GetMapping(path = "/songs", produces  = "application/hypocritus-v1+json")
	public MappingJacksonValue getAllSongsV1() {
		List<Song> songsList =  songdao.getAllSongs();

		MappingJacksonValue mapping = new MappingJacksonValue(songsList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","album");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		return mapping;		
	}
	
	@GetMapping(path = "/songs", produces  = "application/hypocritus-v2+json")
	public MappingJacksonValue getAllSongsV2() {
		List<Song> songsList =  songdao.getAllSongs();

		MappingJacksonValue mapping = new MappingJacksonValue(songsList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","album","releasedYear");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		return mapping;		
	}
	
	@GetMapping(path = "/songs/{id}")
	public EntityModel<MappingJacksonValue> getSong(@PathVariable Integer id) {
		Song song = songdao.getSong(id);
		
		if (song == null) {
		 throw new SongNotFoundException("Song with id: "+ id +" doesn't exist");
		}
		
		MappingJacksonValue mapping = new MappingJacksonValue(song);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","album","releasedYear");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		EntityModel<MappingJacksonValue> entity = EntityModel.of(mapping);
		entity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSongsV2()).withRel("all-songs"));
		
		return entity;
	}
	
	@PostMapping(path = "/songs")
	public ResponseEntity<Object> addSong(@Valid @RequestBody Song song) {
		Song newSong = songdao.addSong(song);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newSong.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(path = "/songs/{id}")
	public void deleteSong(@PathVariable Integer id) {
		Song song = songdao.deleteSong(id);
		
		if (song == null) {
		 throw new SongNotFoundException("Song with id: "+ id +" doesn't exist");
		}
	}
}
