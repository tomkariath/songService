package com.thomas.webservice.songs;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFilter("SongFilter")
@Entity
public class Song {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Artist artist;
	
	@Size(min = 2, message = "Album name should have minimum of 2 characters ")
	private String album;
	
	@Past(message = "Date should not be future date")
	private LocalDate releasedYear;
	
	public Song() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public LocalDate getReleasedYear() {
		return releasedYear;
	}

	public void setReleasedYear(LocalDate releasedYear) {
		this.releasedYear = releasedYear;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", artist=" + artist.getName() + 
				", album=" + album + ", releasedYear=" + releasedYear + "]";
	}

}
