package com.thomas.webservice.songs;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SongJPAController {

	@Autowired
	SongsRepository songsRepo;
	
	@GetMapping(path = "/jpa/songs", produces  = "application/hypocritus-v1+json")
	public MappingJacksonValue getAllSongsV1() {
		List<Song> songsList =  songsRepo.findAll();

		MappingJacksonValue mapping = new MappingJacksonValue(songsList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","artist","album");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		return mapping;		
	}
	
	@GetMapping(path = "/jpa/songs", produces  = "application/hypocritus-v2+json")
	public MappingJacksonValue getAllSongsV2() {
		List<Song> songsList =  songsRepo.findAll();

		MappingJacksonValue mapping = new MappingJacksonValue(songsList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","artist","album","releasedYear");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		return mapping;		
	}
	
	@GetMapping(path = "/jpa/songs/{id}")
	public MappingJacksonValue getSong(@PathVariable Integer id) {
		Optional<Song> song = songsRepo.findById(id);
		
		if (!song.isPresent()) {
		 throw new SongNotFoundException("Song with id: "+ id +" doesn't exist");
		}
		
		MappingJacksonValue mapping = new MappingJacksonValue(song);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","artist","album");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);
		
		return mapping;
		
		
		/*
		 * EntityModel<MappingJacksonValue> entity = EntityModel.of(mapping);
		 * entity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass(
		 * )).getAllSongsV2()).withRel("all-songs"));
		 * 
		 * return entity;
		 */
	}
	
	@PostMapping(path = "/jpa/songs")
	public ResponseEntity<Object> addSong(@Valid @RequestBody Song song) {
		Song newSong = songsRepo.save(song);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newSong.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(path = "/jpa/songs/{id}")
	public void deleteSong(@PathVariable Integer id) {
		songsRepo.deleteById(id);
	}
}
