/*
 * @author Ashvi Patel, ap1463
 * @author Nicole McGowan, ncm71
 */

package view;

public class Song implements Comparable <Song> {
	
	private String name;
	private String artist;
	private String album;
	private String year;
	
	
	public Song(String name, String artist, String album, String year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	//returns -1 if the instance should come before the argument in the song list
	//returns 1 if the instance should come after the argument in the song list
	//returns 0 if the instance and argument have same name and artist - error condition
	
	public int compareTo(Song s){
		int x = name.compareToIgnoreCase(s.name);
		if(x < 0){
			return -1;
		}else if(x > 0){
			return 1;
		}
		
		x = artist.compareToIgnoreCase(s.artist);
		if(x < 0){
			return -1;
		}else if(x > 0){
			return 1;
		}
		
		return 0;
	}
	
	public String toString() {
		
		
		return "\""+this.name+"\""+" by "+this.artist;
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public void setArtist(String artist){
		this.artist = artist;
	}
	
	public String getAlbum(){
		return album;
	}
	
	public void setAlbum(String album){
		this.album = album;
	}
	
	public String getYear(){
		return year;
	}
	
	public void setYear(String year){
		this.year = year;
	}
	
}
