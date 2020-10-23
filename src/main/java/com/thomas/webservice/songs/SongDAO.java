package com.thomas.webservice.songs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SongDAO {
	//TODO List of Songs
	private static int counter = 5;
	private static final ArrayList<Song> songsList = new ArrayList<Song>();
	
	/*
	 * static { songsList.add(new Song(1, "Liberty Song", "Aruvi",
	 * LocalDate.of(2018, 1, 1))); songsList.add(new Song(2, "Anbin Kodi", "Aruvi",
	 * LocalDate.of(2018, 1, 1))); songsList.add(new Song(3, "Vellaipura Ondru",
	 * "Cover", LocalDate.of(2019, 1, 1))); songsList.add(new Song(4,
	 * "Oru Nooru Murai", "Cover", LocalDate.of(2018, 1, 1))); }
	 */	
	public List<Song> getAllSongs(){
		return songsList;
	}
	
	public Song addSong(Song song) {
		if (song.getId() == null) {
			song.setId(counter++);
		}
		
		songsList.add(song);
		
		return song;
	}
	
	public Song getSong(String pattern) {
		for (Song song : songsList) {
			if (song.getName().contains(pattern)) {
				return song;
			}
		}
		return null;
	}
	
	public Song getSong(Integer id) {
		for (Song song : songsList) {
			if (song.getId()==id) {
				return song;
			}
		}
		return null;
	}
	
	public Song deleteSong(Integer id) {
		Song temp = null;
		for (Song song : songsList) {
			if (song.getId()==id) {
				temp = song;	
				songsList.remove(song);
				return temp;
			}
		}
		return temp;
	}

}