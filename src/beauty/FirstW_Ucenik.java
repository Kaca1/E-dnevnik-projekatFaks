package beauty;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import engine.CurrentUser;
import entities.Izostanak;
import entities.Ocjena;
import entities.OcjenaPredmeta;
import entities.Pitanje;
import entities.PredmetUSkoli;
import entities.Ucenik;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import table.IzostanciTable;
import table.OcjeneTable;
import table.ProfesorComboBox;

public class FirstW_Ucenik extends Application {
	private Ucenik user = (Ucenik) CurrentUser.getUser();

	private VBox root = new VBox(10);
	private Scene scene = new Scene(root, 500, 600);

	private VBox root2 = new VBox(10);
	private VBox root3 = new VBox(10);

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			setRoot();
			setRootIzostanci();
			setRootOcjenaPredmetnogProf();

			primaryStage.setScene(scene);
			primaryStage.setTitle("Ucenik");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ----------1. GLAVNA--------------------------------------------
	private void setRoot() {

		// promjena sifre
		Button btnPromjeniSifru = new Button("Promjeni sifru");
		makePromjeniSifru(btnPromjeniSifru);

		// glavni dio
		root.setPadding(new Insets(10, 10, 10, 10));
		HBox hbTop = new HBox(10);
		VBox vbLevi = new VBox(10);
		VBox vbRadios = new VBox(10);

		// KREIRANJE

		// dugmad
		Button btnOcjeniProf = new Button("Ocjeni profesora");
		Button btnIzostanci = new Button("Prikazi Izostanke za sve predmete");

		// label
		String ime = user.getIme();
		String prezime = user.getPrezime();
		String naziv_skole = user.getSkola().getNaziv();
		String mjesto = user.getSkola().getMjesto();
		String razred = user.getRazred();

		Label lblime = new Label("ime: " + ime);
		Label lblPrezime = new Label("prezime: " + prezime);
		Label lblNazivSkole = new Label("naziv skole: " + naziv_skole);
		Label lblMjesto = new Label("mjesto: " + mjesto);
		Label lblRazred = new Label("razred: " + razred);
		Label lblPredmeti = new Label("-predmeti iz kojih ucenik ima ocjene ili izostanak-");

		// RADIOS
		ToggleGroup tgroup = new ToggleGroup();
		makeRadioButtons(vbRadios, tgroup);

		// TABLE FOR ROOT (Ocjene)
		TableView<OcjeneTable> tableViewOcjene = new TableView<OcjeneTable>();
		ArrayList<OcjeneTable> sveOcjeneIzostanci = new ArrayList<>();

		makeTableOcjene(tableViewOcjene, sveOcjeneIzostanci);

		// DODAVANJE u hijerarhiju
		vbLevi.getChildren().addAll(lblime, lblPrezime, lblNazivSkole, lblMjesto, lblRazred, lblPredmeti);
		hbTop.getChildren().addAll(vbLevi);
		root.getChildren().addAll(btnPromjeniSifru, hbTop, vbRadios, tableViewOcjene, btnOcjeniProf, btnIzostanci);

		// action on buttons
		btnIzostanci.setOnAction(e -> {
			scene.setRoot(root2);
		});

		btnOcjeniProf.setOnAction(e -> {
			scene.setRoot(root3);
		});

		// action on radio buttons
		tgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

				RadioButton rb = (RadioButton) tgroup.getSelectedToggle();

				String s = rb.getText();

				tableViewOcjene.getItems().clear();
				if (s != "Svi predmeti")
					for (OcjeneTable p : sveOcjeneIzostanci) {
						if (s.contains(p.getNazivPredmeta()))
							tableViewOcjene.getItems().add(p);
					}
				else
					for (OcjeneTable p : sveOcjeneIzostanci) {
						tableViewOcjene.getItems().add(p);
					}
				tableViewOcjene.sort();
				tableViewOcjene.refresh();

			}
		});
	}

	// =================pomocne funkcije za gornju funkciju=========
	private void makeRadioButtons(VBox vbRadios, ToggleGroup tgroup) {
		RadioButton rb;
		rb = new RadioButton("Svi predmeti");
		rb.setSelected(true);
		rb.setToggleGroup(tgroup);
		vbRadios.getChildren().addAll(rb);
		for (PredmetUSkoli p : user.getPredmetiUSkoli()) {
			rb = new RadioButton(p.getPredmet().getNaziv() + " ( " + p.getProfesor() + " )");
			rb.setToggleGroup(tgroup);
			vbRadios.getChildren().addAll(rb);
		}
	}

	private void makePromjeniSifru(Button btnPromjeniSifru) {

		btnPromjeniSifru.setOnAction(e -> {

			TextInputDialog td = new TextInputDialog("123");
			td.setContentText("Nova sifra: ");
			td.setHeaderText("Promjena sifre");
			Optional<String> sifra = td.showAndWait();
			if (sifra.isPresent()) {
				try {
					CurrentUser.changePassword(sifra.get());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Nova sifra: " + sifra.get());

					alert.showAndWait();
				} catch (NoSuchAlgorithmException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Updejt sifre nije uspio!");
					alert.setContentText(e1.getMessage());

					alert.showAndWait();
				} catch (Exception e1) {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Updejt sifre nije uspio!");
					alert.setContentText(e1.getMessage());

					alert.showAndWait();
				}
			}
		});
	}

	private void makeTableOcjene(TableView<OcjeneTable> tableViewOcjene, ArrayList<OcjeneTable> sveOcjene) {
		TableColumn<OcjeneTable, String> column11 = new TableColumn<>("Predmet");
		column11.setCellValueFactory(new PropertyValueFactory<>("nazivPredmeta"));

		TableColumn<OcjeneTable, Integer> column22 = new TableColumn<>("Ocjena");
		column22.setCellValueFactory(new PropertyValueFactory<>("ocjena"));

		TableColumn<OcjeneTable, Date> column33 = new TableColumn<>("Datum");
		column33.setCellValueFactory(new PropertyValueFactory<>("datum"));

		tableViewOcjene.getColumns().add(column11);
		tableViewOcjene.getColumns().add(column22);
		tableViewOcjene.getColumns().add(column33);

		tableViewOcjene.getSortOrder().add(column33);

		for (Ocjena p : user.getOcjene()) {
			sveOcjene.add(new OcjeneTable(p));
		}

		for (Izostanak p : user.getIzostanci()) {
			sveOcjene.add(new OcjeneTable(p));
		}

		for (OcjeneTable p : sveOcjene) {
			tableViewOcjene.getItems().add(p);
		}

		tableViewOcjene.sort();
	}

	// ====================================================================
	// -------------1. GLAVNA - izostanci -----------
	private void setRootIzostanci() {
		root2.setPadding(new Insets(10, 10, 10, 10));

		// dugme za root2
		Button btnNazad = new Button("Nazad");

		// TABLE FOR ROOT2

		TableView<IzostanciTable> tableView = new TableView<IzostanciTable>();

		TableColumn<IzostanciTable, String> column1 = new TableColumn<>("Predmet");
		column1.setCellValueFactory(new PropertyValueFactory<>("nazivPredmeta"));

		TableColumn<IzostanciTable, Date> column2 = new TableColumn<>("Datum");
		column2.setCellValueFactory(new PropertyValueFactory<>("datum"));

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);

		for (Izostanak p : user.getIzostanci()) {
			tableView.getItems().add(new IzostanciTable(p.getNazivPredmeta(), p.getDatum()));
		}
		tableView.getSortOrder().add(column2);
		tableView.sort();

		// DODAVANJE ROOT2
		root2.getChildren().addAll(btnNazad, tableView);

		// action on buttons root2
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});

	}

	// -----------3. GLAVNI - ocjeni profesora ------
	private void setRootOcjenaPredmetnogProf() {
		root3.setPadding(new Insets(10, 10, 10, 10));

		VBox pitanja = new VBox(10);
		VBox odgovori = new VBox(10);

		// dugme za root3
		Button btnNazad = new Button("Nazad");
		Button btnSave = new Button("Save");

		// pitanja
		int broj = 0;
		ArrayList<Pitanje> svaPitanja = Pitanje.getSvaPitanja();
		for (Pitanje p : svaPitanja) {
			broj++;
			pitanja.getChildren().addAll(new Label(Integer.toString(broj) + ". " + p.getPitanje()));
		}

		// odgovori
		broj = 0;
		HBox linija;
		Label redniBroj;
		RadioButton rb = new RadioButton(); // da bi mogla koristiti setSelected

		// ovo bi mogla biti mapa tako da mapira String pitanja sa toggleGroup
		ArrayList<ToggleGroup> listaTG = new ArrayList<>(svaPitanja.size());

		for (int p = 0; p < svaPitanja.size(); p++) {
			linija = new HBox(10);

			broj++;
			redniBroj = new Label();
			redniBroj.setText(Integer.toString(broj) + ". pitanje");
			linija.getChildren().addAll(redniBroj);

			listaTG.add(new ToggleGroup());

			for (int i = 1; i < 6; i++) {

				rb = new RadioButton(Integer.toString(i));
				rb.setToggleGroup(listaTG.get(listaTG.size() - 1));
				linija.getChildren().addAll(rb);

			}
			rb.setSelected(true);
			odgovori.getChildren().addAll(redniBroj, linija);

		}

		// ComboBox
		ArrayList<PredmetUSkoli> predmeti = user.getPredmetiUSkoli();

		ArrayList<ProfesorComboBox> odabraniProfesori = ProfesorComboBox.napraviListuProf(predmeti);

		ObservableList<ProfesorComboBox> options = FXCollections.observableArrayList(odabraniProfesori);

		ComboBox<ProfesorComboBox> comboBox = new ComboBox<ProfesorComboBox>(options);
		comboBox.getSelectionModel().selectFirst();

		// rezultat
		Label rezultat = new Label();

		// rez set tekst
		updateLabelRezultat(btnSave, rezultat, comboBox);

		// DODAVANJE ROOT3
		root3.getChildren().addAll(btnNazad, comboBox, pitanja, odgovori, rezultat, btnSave);

		// action on buttons root3
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});

		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				PredmetUSkoli predmetUSkoli = comboBox.getValue().getPredmetUSK();
				Ucenik ucenik = user;

				int indeks = 0;
				for (Pitanje pitanje : svaPitanja) {
					RadioButton chk = (RadioButton) listaTG.get(indeks).getSelectedToggle();
					int ocjena = Integer.parseInt(chk.getText());
					indeks++;

					OcjenaPredmeta.createNewOcjenaPredmeta(ucenik, predmetUSkoli, pitanje, ocjena);
				}

				updateLabelRezultat(btnSave, rezultat, comboBox);

			}

		});

		// action za combobox
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				updateLabelRezultat(btnSave, rezultat, comboBox);

			}

		});
	}

	private void updateLabelRezultat(Button btnSave, Label rezultat, ComboBox<ProfesorComboBox> comboBox) {
		String rezultatString = OcjenaPredmeta.getStringForProfesorOdUcenika(user, comboBox.getValue().getProfesor());
		rezultat.setText(rezultatString);

		if (!rezultatString.isEmpty())
			btnSave.setDisable(true);
		else
			btnSave.setDisable(false);
	}

}
