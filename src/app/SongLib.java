/*
 * @author Ashvi Patel, ap1463
 * @author Nicole McGowan, ncm71
 */

package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.Song;
import view.songLibController;

public class SongLib extends Application {
	
	@Override
	public void start (Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader(); //loads the .fxml file and creates the GUI components the file declares
		loader.setLocation(getClass().getResource("/view/songlib.fxml")); //location of fxml file
		AnchorPane root = (AnchorPane)loader.load();
		songLibController songController = loader.getController();
		songController.start(primaryStage);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					File file = new File("data.txt");
					PrintWriter pw = new PrintWriter(file);
					
					String s;
					String or = "|";
					for(int i = 0; i < songController.observableList.size(); i++) {
						s = "";
						Song song = songController.observableList.get(i);
						if(song.getAlbum().equals("")) {
							song.setAlbum(" ");
						}
						if(song.getYear().equals("")) {
							song.setYear(" ");
						}
						s = s + song.getName() + or + song.getArtist() + or + song.getAlbum() + or + song.getYear() + "\n";
						pw.write(s);
					}
					pw.close();
					
				}
				catch(Exception e){
					System.out.println("Error opening the file to save the song list.");
					return;
				}

			}
		});

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
