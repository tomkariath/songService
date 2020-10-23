package com.thomas.webservice.songs;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thomas.webservice.exception.ArtistNotFoundException;

@RestController
public class ArtistController {

	@Autowired
	ArtistRepository artistRepo;

	@Autowired
	SongsRepository songRepo;

	@GetMapping(path = "/artists/{artistId}/songs")
	public MappingJacksonValue getSongsByArtist(@PathVariable Integer artistId) {
		Optional<Artist> artist = artistRepo.findById(artistId);

		if (!artist.isPresent()) {
			throw new ArtistNotFoundException("Artist with id: " + artistId + " doesn't exist");
		}

		List<Song> songsList = artist.get().getSongsList();

		MappingJacksonValue mapping = new MappingJacksonValue(songsList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", 
				"album", "releasedYear");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SongFilter", filter);
		mapping.setFilters(filters);

		return mapping;
	}

	@GetMapping(path = "/artists/{artistId}")
	public MappingJacksonValue getArtist(@PathVariable Integer artistId) {
		new SimpleFilterProvider().setFailOnUnknownId(false);
		Optional<Artist> artist = artistRepo.findById(artistId);

		if (!artist.isPresent()) {
			throw new ArtistNotFoundException("Artist with id: " + artistId + " doesn't exist");
		}

		MappingJacksonValue mapping = new MappingJacksonValue(artist);
		FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false);
		mapping.setFilters(filters);

		return mapping;
	}
	
	@GetMapping(path = "/artists")
	public MappingJacksonValue getAllArtists() {
		new SimpleFilterProvider().setFailOnUnknownId(false);
		List<Artist> artists = artistRepo.findAll();

		MappingJacksonValue mapping = new MappingJacksonValue(artists);
		FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false);
		mapping.setFilters(filters);

		return mapping;
	}

	@PostMapping(path = "/artists")
	public ResponseEntity<Object> addArtist(@Valid @RequestBody Artist artist) {
		Artist newArtist = artistRepo.save(artist);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{artistId}")
				.buildAndExpand(newArtist.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@PostMapping(path = "/artists/{artistId}/songs")
	public ResponseEntity<Object> addArtist(@PathVariable Integer artistId, 
			@Valid @RequestBody Song song) {
		Optional<Artist> artist = artistRepo.findById(artistId);

		if (!artist.isPresent()) {
			throw new ArtistNotFoundException("Artist with id: " + artistId + " doesn't exist");
		}

		song.setArtist(artist.get());
		songRepo.save(song);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{artistId}/songs")
				.buildAndExpand(artistId)
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
