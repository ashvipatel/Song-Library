/*
 * @author Ashvi Patel, ap1463
 * @author Nicole McGowan, ncm71
 */

package view;

import javafx.collections.FXCollections;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import view.Song;


public class songLibController {

	@FXML ListView <Song> ListViewLib;
	@FXML Button ButtonAdd;
	@FXML Button ButtonDelete;
	@FXML Button BUttonEdit;
	@FXML Button BUttonSave;
	@FXML Button ButtonCancel;
	@FXML Text TextName;
	@FXML Text TextArtist;
	@FXML Text TextAlbum;
	@FXML Text TextYear;
	@FXML Text TextDName;
	@FXML Text TextDArtist;
	@FXML Text TextDAlbum;
	@FXML Text TextDYear;
	@FXML TextField TextFieldName;
	@FXML TextField TextFieldArtist;
	@FXML TextField TextFieldAlbum;
	@FXML TextField TextFieldYear;
	@FXML HBox HBoxAED;
	@FXML HBox HBoxSC;
	
	int flag;
	
	public ObservableList <Song> observableList;
	
	public void start (Stage mainStage)  throws IOException {
		
		File file = new File("data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String s;
		observableList = FXCollections.observableArrayList();
		while((s = br.readLine()) != null) {
			String[] sA = s.split("[|]");
			sA[2] = sA[2].trim();
			sA[3] = sA[3].trim();

			Song song = new Song(sA[0], sA[1], sA[2], sA[3]);
			observableList.add(song);
		}
		br.close();
		
		
		ListViewLib.setItems(observableList);
		ListViewLib.getSelectionModel().select(0);
		
		if(observableList.isEmpty() == true) {
			TextDName.setText("");
			TextDArtist.setText("");
			TextDAlbum.setText("");
			TextDYear.setText("");
			
		}
		else {

			Song currentSong = ListViewLib.getSelectionModel().getSelectedItem();
			TextDName.setText(currentSong.getName());
			TextDArtist.setText(currentSong.getArtist());
			TextDAlbum.setText(currentSong.getAlbum());
			TextDYear.setText(currentSong.getYear());
		}
		
		ListViewLib.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal,newVal) -> changeDetails(mainStage));
		
	}
	

	
	private void changeDetails(Stage mainStage) {
		if(observableList.isEmpty() == true) {
		    TextDName.setText("");
			TextDArtist.setText("");
			TextDAlbum.setText("");
			TextDYear.setText("");
		}else {
			Song currentSong = ListViewLib.getSelectionModel().getSelectedItem();
			TextDName.setText(currentSong.getName());
			TextDArtist.setText(currentSong.getArtist());
			TextDAlbum.setText(currentSong.getAlbum());
			TextDYear.setText(currentSong.getYear());
		}
		
	}

	public void addSong (ActionEvent addSong) {
		
		HBoxAED.setVisible(false);
		HBoxSC.setVisible(true);
		
		TextDName.setVisible(false);
		TextDArtist.setVisible(false);
		TextDAlbum.setVisible(false);
		TextDYear.setVisible(false);
		
		TextFieldName.setVisible(true);
		TextFieldArtist.setVisible(true);
		TextFieldAlbum.setVisible(true);
		TextFieldYear.setVisible(true);
		
		TextFieldName.setText("");
		TextFieldArtist.setText("");
		TextFieldAlbum.setText("");
		TextFieldYear.setText("");
		
		flag = 1;
		
			
	}
	
	public void editSong (ActionEvent editSong) {
		
		if (ListViewLib.getItems().isEmpty() == true) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Edit Error");
			alert.setHeaderText("Cannot edit an empty list.");
			alert.showAndWait();
			return;

		}
		
		HBoxAED.setVisible(false);
		HBoxSC.setVisible(true);
		
		TextDName.setVisible(false);
		TextDArtist.setVisible(false);
		TextDAlbum.setVisible(false);
		TextDYear.setVisible(false);
		
		TextFieldName.setVisible(true);
		TextFieldArtist.setVisible(true);
		TextFieldAlbum.setVisible(true);
		TextFieldYear.setVisible(true);
		
		TextFieldName.setText(TextDName.getText());
		TextFieldArtist.setText(TextDArtist.getText());
		TextFieldAlbum.setText(TextDAlbum.getText());
		TextFieldYear.setText(TextDYear.getText());
		
		flag = 2;
		
	}
	
	public void saveSong(ActionEvent saveSong) {
		if (flag == 1) {
			addHelper();
		}
		else if (flag == 2){
			editHelper();
		}
		
	}
	
	private void addHelper() {
		String name = TextFieldName.getText().trim();
		String artist = TextFieldArtist.getText().trim();
		String album = TextFieldAlbum.getText().trim();
		String year = TextFieldYear.getText().trim();
		
		
		if("".equals(name)||"".equals(artist)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Save Error");
			alert.setHeaderText("Name and artist must be specified.");
			alert.showAndWait();
			return;
		}
		
		if(!year.equals("")) {
			try {
				int y =Integer.parseInt(year);
				if( y < 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Save Error");
					alert.setHeaderText("Year must be a positive integer!");
					alert.showAndWait();
					return;
				}
				
			}
			catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Save Error");
				alert.setHeaderText("Year must be a positive integer!");
				alert.showAndWait();
				return;
			}		
		}
		
		
		Song s = new Song(name,artist,album,year); 
		
		int index=0;
		
		boolean append = true;
				
		for(int i = 0; i < (ListViewLib.getItems().size()); i++) {
			index= i;
			int x = s.compareTo(ListViewLib.getItems().get(i));


			if(x==0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Save Error");
				alert.setHeaderText("Name and artist must be unique.");
				alert.showAndWait();
				return;
			}else if(x==-1){
				
				append = false;
				break;
			}

		}	
		
		if(append && (ListViewLib.getItems().isEmpty() == false)) {
			ListViewLib.getItems().add(s);
			ListViewLib.getSelectionModel().select(index+1);

		}
		else{
			ListViewLib.getItems().add(index,s);
			ListViewLib.getSelectionModel().select(index);

		}	
		
				
		HBoxAED.setVisible(true);
		HBoxSC.setVisible(false);
		
		TextDName.setVisible(true);
		TextDArtist.setVisible(true);
		TextDAlbum.setVisible(true);
		TextDYear.setVisible(true);
		
		TextFieldName.setVisible(false);
		TextFieldArtist.setVisible(false);
		TextFieldAlbum.setVisible(false);
		TextFieldYear.setVisible(false);
	}
	
	
	
	private void editHelper() {
		
		
		String name = TextFieldName.getText().trim();
		String artist = TextFieldArtist.getText().trim();
		String album = TextFieldAlbum.getText().trim();
		String year = TextFieldYear.getText().trim();
		
		if("".equals(name)||"".equals(artist)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Save Error");
			alert.setHeaderText("Name and artist must be specified.");
			alert.showAndWait();
			return;
		}
		
		if(!year.equals("")) {
			try {
				int y =Integer.parseInt(year);
				if( y < 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Save Error");
					alert.setHeaderText("Year must be a positive integer!");
					alert.showAndWait();
					return;
				}
				
			}
			catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Save Error");
				alert.setHeaderText("Year must be a positive integer!");
				alert.showAndWait();
				return;
			}		
		}
		
		int index = ListViewLib.getSelectionModel().getSelectedIndex();
		Song s = new Song(name, artist, album, year);
		
		if(editCheck(s, index)) {
			return;
		}
		
		ListViewLib.getItems().remove(index);
		
		index = 0;			
		boolean append = true;
				
		for(int i = 0; i < (ListViewLib.getItems().size()); i++) {
			index= i;
			int x = s.compareTo(ListViewLib.getItems().get(i));

			if(x==-1){
				
				append = false;
				break;
			}

		}
		

		if(append && (ListViewLib.getItems().isEmpty() == false)) {
			ListViewLib.getItems().add(s);
			ListViewLib.getSelectionModel().select(index+1);

		}
		else {
			ListViewLib.getItems().add(index,s);
			ListViewLib.getSelectionModel().select(index);

		}		
				
		HBoxAED.setVisible(true);
		HBoxSC.setVisible(false);
		
		TextDName.setVisible(true);
		TextDArtist.setVisible(true);
		TextDAlbum.setVisible(true);
		TextDYear.setVisible(true);
		
		TextFieldName.setVisible(false);
		TextFieldArtist.setVisible(false);
		TextFieldAlbum.setVisible(false);
		TextFieldYear.setVisible(false);
		
	}
	
	private boolean editCheck(Song s, int index) {
		
		for(int i = 0; i < (ListViewLib.getItems().size()); i++) {
			
			int x = s.compareTo(ListViewLib.getItems().get(i));

			if(x==0 && (i!=index)) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Save Error");
				alert.setHeaderText("Name and artist must be unique.");
				alert.showAndWait();
				return true;
			}
		}
		return false;
	}
	
	public void cancel (ActionEvent cancel) {
		
		HBoxAED.setVisible(true);
		HBoxSC.setVisible(false);
		
		TextDName.setVisible(true);
		TextDArtist.setVisible(true);
		TextDAlbum.setVisible(true);
		TextDYear.setVisible(true);
		
		TextFieldName.setVisible(false);
		TextFieldArtist.setVisible(false);
		TextFieldAlbum.setVisible(false);
		TextFieldYear.setVisible(false);
		
	}
	

	public void deleteSong (ActionEvent deleteSong) {
		
		if (ListViewLib.getItems().isEmpty() == true) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delete Error");
			alert.setHeaderText("Cannot delete from an empty list.");
			alert.showAndWait();
			return;

		}
		   
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Delete Item");
		   alert.setHeaderText("Are you sure you want to delete this song?");
		   Optional<ButtonType> result = alert.showAndWait();
		   
		   if (result.get() == ButtonType.OK) {
			   
			   int index = ListViewLib.getSelectionModel().getSelectedIndex();
	
			   ListViewLib.getItems().remove(index);
			   ListViewLib.getSelectionModel().select(index);
			   
		   }
		   else {
			   return;
		   }  
			
	   }
	
	
}








