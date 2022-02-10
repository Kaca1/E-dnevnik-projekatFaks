package beauty;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import engine.CurrentUser;
import engine.SendMail;
import entities.Izostanak;
import entities.Ocjena;
import entities.OcjenaPredmeta;
import entities.Pitanje;
import entities.Predmet;
import entities.PredmetUSkoli;
import entities.Profesor;
import entities.Skola;
import entities.Ucenik;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FirstW_Prof extends Application {
	private Profesor user = (Profesor) CurrentUser.getUser();

	private VBox root = new VBox(10);
	private Scene scene = new Scene(root, 500, 600);

	private VBox rootDodajUcenika = new VBox(10);
	private VBox rootDodajPredmet = new VBox(10);
	private VBox rootDodajSkolu = new VBox(10);
	private VBox rootDodajProfesora = new VBox(10);
	private VBox rootOdaberiPredmet = new VBox(10);
	private VBox rootOcjene = new VBox(10);

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			setRoot();
			setRootDodajUcenika();
			setRootDodajPredmet();
			setRootDodajSkolu();
			setRootDodajProfesora();
			setRootOdaberiPredmet();

			primaryStage.setScene(scene);
			primaryStage.setTitle("Profesor");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// home za profesora
	private void setRoot() {

		// promjena sifre
		Button btnPromjeniSifru = makeBtnPromjeniSifru();

		root.setPadding(new Insets(10, 10, 10, 10));
		VBox hbTop = new VBox(10);
		HBox dugmici = new HBox(10);
		HBox ocjene = new HBox(10);
		HBox ocjeniUcenika = new HBox(10);

		// KREIRANJE

		// dugmad
		Button btnDodajUcenika = new Button("Novi ucenik");
		Button btnDodajPredmet = new Button("Novi Predmet");
		Button btnDodajSkolu = new Button("Nova Skola");
		Button btnDodajProfesora = new Button("Novi Profesor");
		Button btnOdaberiPredmet = new Button("Odaberi predmet");

		Button btnOcjeni = new Button("ocjeni ucenika ili upisi izostanak ->");

		// label info o useru
		String ime = user.getIme();
		String prezime = user.getPrezime();
		String naziv_skola_i_predmeti = user.getSkoleIPredmeteString();

		Label lblime = new Label("ime: " + ime);
		Label lblPrezime = new Label("prezime: " + prezime);
		Label lblNazivSkole = new Label("skole: " + naziv_skola_i_predmeti);
		Label lblocjene = new Label("ocjene: ");

		// table
		TableView<Ucenik> tableView = makeTableUcenici();

		// table2-ocjenePredmeta
		TableView<OcjenaPredmeta> tableView2 = makeTableOcjenaPredmeta();

		// combobox
		ComboBox<PredmetUSkoli> comboBox = makeComboPredmeti(tableView, tableView2);

		// combobox2-ucenici koji nemaju ocjenu
		ComboBox<Ucenik> comboBox2 = new ComboBox<Ucenik>();
		updateComboNedefinisaniUcenici(comboBox2, comboBox);

		// DODAVANJE U HIJERARHIJU
		ocjeniUcenika.getChildren().addAll(comboBox2, btnOcjeni);
		ocjene.getChildren().addAll(tableView, lblocjene);
		dugmici.getChildren().addAll(btnDodajUcenika, btnDodajPredmet, btnDodajSkolu, btnDodajProfesora,
				btnOdaberiPredmet);
		hbTop.getChildren().addAll(lblime, lblPrezime, lblNazivSkole);
		root.getChildren().addAll(btnPromjeniSifru, hbTop, dugmici, comboBox, ocjene, ocjeniUcenika, tableView2);

		// action on buttons
		btnDodajUcenika.setOnAction(e -> {
			rootDodajUcenika = new VBox(10);
			setRootDodajUcenika();
			scene.setRoot(rootDodajUcenika);
		});
		btnDodajPredmet.setOnAction(e -> {
			rootDodajPredmet = new VBox(10);
			setRootDodajPredmet();
			scene.setRoot(rootDodajPredmet);
		});
		btnDodajSkolu.setOnAction(e -> {
			rootDodajSkolu = new VBox(10);
			setRootDodajSkolu();
			scene.setRoot(rootDodajSkolu);
		});
		btnDodajProfesora.setOnAction(e -> {
			rootDodajProfesora = new VBox(10);
			setRootDodajProfesora();
			scene.setRoot(rootDodajProfesora);
		});
		btnOdaberiPredmet.setOnAction(e -> {
			rootOdaberiPredmet = new VBox(10);
			setRootOdaberiPredmet();
			scene.setRoot(rootOdaberiPredmet);
		});
		btnOcjeni.setOnAction(e -> {
			Ucenik ucenik = tableView.getSelectionModel().getSelectedItem();

			if (ucenik == null) {
				ucenik = comboBox2.getValue();
			}
			if (ucenik != null) {
				PredmetUSkoli predmetUSk = comboBox.getValue();

				rootOcjene = new VBox(10);
				setRootOcjeni(ucenik, predmetUSk);
				scene.setRoot(rootOcjene);
			}

		});

		// ako double klikne na ucenika updejtuje se labela sa ocjenama i izostancima
		tableView.setRowFactory(tv -> {
			TableRow<Ucenik> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Ucenik ucenik = row.getItem();
					PredmetUSkoli predmetUSk = comboBox.getValue();

					setLblOcjene(lblocjene, ucenik, predmetUSk);
				}
			});
			return row;
		});

		// action za combobox
		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateTables(comboBox, tableView, tableView2);
				updateComboNedefinisaniUcenici(comboBox2, comboBox);
			}

		});
		comboBox2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tableView.getSelectionModel().clearSelection();
				Ucenik ucenik = comboBox2.getValue();
				if (ucenik != null) {
					lblocjene.setText(ucenik.getIme() + " " + ucenik.getPrezime());
				}

			}

		});

	}

	// ===================pomocne funkcije za Root============
	private void updateComboNedefinisaniUcenici(ComboBox<Ucenik> comboBox2, ComboBox<PredmetUSkoli> comboBox) {
		comboBox2.getItems().clear();

		PredmetUSkoli predmetUSk2 = comboBox.getValue();
		// neki prof jos ne predaju nista pa ovo
		if (predmetUSk2 != null) {
			ArrayList<Ucenik> ucenici = Ucenik.getNedefinisaniUcenici(predmetUSk2);

			ObservableList<Ucenik> options2 = FXCollections.observableArrayList(ucenici);

			comboBox2.getItems().addAll(options2);
		}

	}

	private ComboBox<PredmetUSkoli> makeComboPredmeti(TableView<Ucenik> tableView,
			TableView<OcjenaPredmeta> tableView2) {
		ArrayList<PredmetUSkoli> predmeti = user.getPredmeti();
		ObservableList<PredmetUSkoli> options = FXCollections.observableArrayList(predmeti);
		ComboBox<PredmetUSkoli> comboBox = new ComboBox<PredmetUSkoli>(options);
		comboBox.getSelectionModel().selectFirst();
		updateTables(comboBox, tableView, tableView2);
		return comboBox;
	}

	private void updateTables(ComboBox<PredmetUSkoli> comboBox, TableView<Ucenik> tableView,
			TableView<OcjenaPredmeta> tableView2) {
		PredmetUSkoli predmetUSk = comboBox.getValue();

		// ucenici table
		tableView.getItems().clear();

		for (Ucenik p : Ucenik.getUceniciPredmeta(predmetUSk)) {
			tableView.getItems().add(p);
		}
		tableView.refresh();

		// ocjene predmeta table
		tableView2.getItems().clear();

		for (OcjenaPredmeta p : OcjenaPredmeta.getOcjenePredmeta(predmetUSk)) {
			tableView2.getItems().add(p);
		}
		tableView2.refresh();
	}

	private void setLblOcjene(Label lblocjene, Ucenik ucenik, PredmetUSkoli predmetUSk) {
		ArrayList<Ocjena> ocjeneUcenika = ucenik.getOcjene();
		String rez = ucenik.getIme() + " " + ucenik.getPrezime() + " ocjene: \n";
		for (Ocjena o : ocjeneUcenika) {
			if (o.getPredmetUSkoli().equals(predmetUSk)) {
				rez += o.getOcjena() + " " + o.getDatum() + " \n";
			}
		}
		for (Izostanak o : ucenik.getIzostanci()) {
			if (o.getPredmetUSkoli().equals(predmetUSk)) {
				rez += o.getDatum() + " - izostanak" + " \n";
			}
		}

		lblocjene.setText(rez);
	}

	private TableView<OcjenaPredmeta> makeTableOcjenaPredmeta() {
		TableView<OcjenaPredmeta> tableView2 = new TableView<OcjenaPredmeta>();

		TableColumn<OcjenaPredmeta, Pitanje> column11 = new TableColumn<>("Pitanje");
		column11.setCellValueFactory(new PropertyValueFactory<>("pitanje"));
		column11.setPrefWidth(400);

		TableColumn<OcjenaPredmeta, Integer> column21 = new TableColumn<>("Ocjena");
		column21.setCellValueFactory(new PropertyValueFactory<>("ocjena"));

		tableView2.getColumns().add(column11);
		tableView2.getColumns().add(column21);

		tableView2.setMaxHeight(150);
		return tableView2;
	}

	private TableView<Ucenik> makeTableUcenici() {
		TableView<Ucenik> tableView = new TableView<Ucenik>();

		TableColumn<Ucenik, String> column1 = new TableColumn<>("Ime");
		column1.setCellValueFactory(new PropertyValueFactory<>("ime"));

		TableColumn<Ucenik, String> column2 = new TableColumn<>("Prezime");
		column2.setCellValueFactory(new PropertyValueFactory<>("prezime"));

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);

		tableView.setMaxHeight(125);
		return tableView;
	}

	private Button makeBtnPromjeniSifru() {
		Button btnPromjeniSifru = new Button("Promjeni sifru");

		btnPromjeniSifru.setOnAction(e -> {
			TextInputDialog td = new TextInputDialog();
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
		return btnPromjeniSifru;
	}
//==============================================================

	// ---------DODAJ UCENIKA VIEW-------------------------------------
	private void setRootDodajUcenika() {

		rootDodajUcenika.setPadding(new Insets(10, 10, 10, 10));
		VBox hbTop = new VBox(10);
		VBox input = new VBox(10);
		HBox pol = new HBox(10);

		// KREIRANJE

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");

		// input
		TextField inputIme = new TextField("ime ucenika");
		TextField inputPrezime = new TextField("prezime ucenika");
		TextField inputKorIme = new TextField("kor ime");
		TextField inputMail = new TextField("mail");

		// radio button
		ToggleGroup tgroup = new ToggleGroup();

		RadioButton rbMusko = new RadioButton("Musko");
		RadioButton rbZensko = new RadioButton("Zensko");
		rbMusko.setToggleGroup(tgroup);
		rbZensko.setToggleGroup(tgroup);
		rbMusko.setSelected(true);

		// label
		Label uspjesnost = new Label("");

		// DODAVANJE
		pol.getChildren().addAll(rbMusko, rbZensko);
		input.getChildren().addAll(inputIme, inputPrezime, pol, inputKorIme, inputMail);
		hbTop.getChildren().addAll();

		rootDodajUcenika.getChildren().addAll(btnNazad, hbTop, input, btnSave, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});
		btnSave.setOnAction(e -> {
			String ime = inputIme.getText();
			String prezime = inputPrezime.getText();
			String korIme = inputKorIme.getText();
			String mail = inputMail.getText();
			String sifra = korIme + "123";

			RadioButton rb = (RadioButton) tgroup.getSelectedToggle();
			String m = rb.getText();
			int polInt = 0;
			if (m.equals("Musko"))
				polInt = 1;

			try {

				Ucenik.createNoviUcenik(ime, prezime, polInt, korIme, mail, sifra);
				uspjesnost.setText("Ucenik je kreiran");
				btnSave.setDisable(true);

				String sadrzaj = "Usjesno ste registrovani kao " + "\nkorIme: " + korIme + "\nsifra: " + sifra;
				SendMail.sendMail(mail, sadrzaj);

			} catch (Exception e1) {
				uspjesnost.setText("Ucenik nije kreiran, desila se greska. " + e1.getMessage());
				// e1.printStackTrace();

			}

		});

	}

	private void setRootDodajPredmet() {

		rootDodajPredmet.setPadding(new Insets(10, 10, 10, 10));
		VBox input = new VBox(10);

		// KREIRANJE

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");

		// input
		TextField inputRazred = new TextField("razred");
		TextField inputNaziv = new TextField("naziv");

		// label
		Label uspjesnost = new Label("");

		// DODAVANJE
		input.getChildren().addAll(inputNaziv, inputRazred);
		rootDodajPredmet.getChildren().addAll(btnNazad, input, btnSave, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});
		btnSave.setOnAction(e -> {
			try {
				Predmet.createNoviPredmet(inputNaziv.getText(), inputRazred.getText());
				uspjesnost.setText("Predmet je kreiran");
				btnSave.setDisable(true);

			} catch (Exception e1) {
				uspjesnost.setText("Predmet nije kreiran, desila se greska: " + e1.getMessage());

			}
		});
	}

	private void setRootDodajSkolu() {

		rootDodajSkolu.setPadding(new Insets(10, 10, 10, 10));
		VBox input = new VBox(10);

		// KREIRANJE

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");

		// input

		TextField inputNaziv = new TextField("naziv");
		TextField inputGrad = new TextField("grad");
		TextField inputMjesto = new TextField("mjesto");
		TextField inputDrzava = new TextField("drzava");

		// label
		Label uspjesnost = new Label("");

		// DODAVANJE
		input.getChildren().addAll(inputNaziv, inputGrad, inputMjesto, inputDrzava);
		rootDodajSkolu.getChildren().addAll(btnNazad, input, btnSave, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});
		btnSave.setOnAction(e -> {
			try {
				Skola.createNovaSkola(inputNaziv.getText(), inputGrad.getText(), inputMjesto.getText(),
						inputDrzava.getText());
				uspjesnost.setText("Skola je kreirana");
				btnSave.setDisable(true);

			} catch (Exception e1) {
				uspjesnost.setText("Skola nije kreirana, desila se greska: " + e1.getMessage());
			}
		});

	}

	private void setRootDodajProfesora() {

		rootDodajProfesora.setPadding(new Insets(10, 10, 10, 10));
		VBox hbTop = new VBox(10);
		VBox input = new VBox(10);
		HBox pol = new HBox(10);

		// KREIRANJE

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");

		// input
		TextField inputIme = new TextField("ime prof");
		TextField inputPrezime = new TextField("prezime prof");
		TextField inputKorIme = new TextField("kor ime");
		TextField inputMail = new TextField("mail");

		// radio button
		ToggleGroup tgroup = new ToggleGroup();

		RadioButton rbMusko = new RadioButton("Musko");
		RadioButton rbZensko = new RadioButton("Zensko");
		rbMusko.setToggleGroup(tgroup);
		rbZensko.setToggleGroup(tgroup);
		rbMusko.setSelected(true);

		// label
		Label uspjesnost = new Label("");

		// DODAVANJE
		pol.getChildren().addAll(rbMusko, rbZensko);
		input.getChildren().addAll(inputIme, inputPrezime, pol, inputKorIme, inputMail);
		hbTop.getChildren().addAll();

		rootDodajProfesora.getChildren().addAll(btnNazad, hbTop, input, btnSave, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});
		btnSave.setOnAction(e -> {
			String ime = inputIme.getText();
			String prezime = inputPrezime.getText();
			String korIme = inputKorIme.getText();
			String mail = inputMail.getText();
			String sifra = korIme + "123";

			RadioButton rb = (RadioButton) tgroup.getSelectedToggle();
			String m = rb.getText();
			int polInt = 0;
			if (m.equals("Musko"))
				polInt = 1;

			try {
				Profesor.createNoviProfesor(ime, prezime, polInt, korIme, mail, sifra);
				uspjesnost.setText("Prof je kreiran");
				btnSave.setDisable(true);

				String sadrzaj = "Usjesno ste registrovani kao " + "\nkorIme: " + korIme + "\nsifra: " + sifra;
				SendMail.sendMail(mail, sadrzaj);

			} catch (Exception e1) {
				uspjesnost.setText("Prof nije kreiran, desila se greska " + e1.getMessage());
				// e1.printStackTrace();
			}

		});
	}

	private void setRootOdaberiPredmet() {

		VBox input = new VBox(10);

		// KREIRANJE
		Label lpredmeti = new Label("Predmeti:");
		Label lskole = new Label("Skole:");

		// kombobox predmeti
		ComboBox<Predmet> comboBoxPredmeti = new ComboBox<Predmet>();
		comboBoxPredmeti.getItems().addAll(Predmet.getAllPredmeti());

		// kombobox skole
		ComboBox<Skola> comboBoxSkole = new ComboBox<Skola>();
		comboBoxSkole.getItems().addAll(Skola.getAllSkole());

		rootOdaberiPredmet.setPadding(new Insets(10, 10, 10, 10));

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");

		// label
		Label uspjesnost = new Label("");

		// DODAVANJE
		input.getChildren().addAll(lpredmeti, comboBoxPredmeti, lskole, comboBoxSkole);
		rootOdaberiPredmet.getChildren().addAll(btnNazad, input, btnSave, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			scene.setRoot(root);
		});

		btnSave.setOnAction(e -> {
			try {
				Predmet predmet = comboBoxPredmeti.getValue();
				Skola skola = comboBoxSkole.getValue();

				PredmetUSkoli.createNoviPredmet(predmet, skola, user);
				uspjesnost.setText("Predmet je izabran");
				btnSave.setDisable(true);

				root = new VBox(10);
				setRoot();// refresujemo home jer je tamo lista predmeta koje prof predaje pa da se
				// azurira ta lista

			} catch (Exception e1) {
				uspjesnost.setText("Predmet  nije izabarn, desila se greska: " + e1.getMessage());

			}
		});

	}

	private void setRootOcjeni(Ucenik ucenik, PredmetUSkoli predmetUSk) {
		VBox input = new VBox(10);
		HBox dugmici = new HBox(10);

		// dugmad
		Button btnSave = new Button("Save");
		Button btnNazad = new Button("Nazad");
		Button btnIzostanak = new Button("Upisi izostanak");

		// label
		Label imePrezime = new Label(ucenik.getIme() + " " + ucenik.getPrezime());
		Label uspjesnost = new Label();

		// input
		ChoiceBox<Integer> ocjenaChoice = new ChoiceBox<Integer>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
		ocjenaChoice.setValue(1);
		DatePicker datePicker = new DatePicker();

		// DODAVANJE
		input.getChildren().addAll(ocjenaChoice, datePicker);
		dugmici.getChildren().addAll(btnSave, btnIzostanak);
		rootOcjene.getChildren().addAll(btnNazad, imePrezime, input, dugmici, uspjesnost);

		// actions on buttons
		btnNazad.setOnAction(e -> {
			root = new VBox(10);
			setRoot();
			scene.setRoot(root);
		});

		btnSave.setOnAction(e -> {
			try {
				int ocjena = ocjenaChoice.getValue();
				if (datePicker.getValue() != null) {
					Date datum = Date.valueOf(datePicker.getValue());

					Ocjena.createNovaOcjena(ocjena, datum, ucenik, predmetUSk);
					uspjesnost.setText("Ocjena upisana");
				} else {
					throw new Exception("datum nije izabran");
				}

			} catch (Exception e1) {
				uspjesnost.setText("Ocjena  nije upisana, desila se greska: " + e1.getMessage());

			}
		});
		btnIzostanak.setOnAction(e -> {
			try {

				if (datePicker.getValue() != null) {
					Date datum = Date.valueOf(datePicker.getValue());

					Izostanak.createNoviIzostanak(datum, ucenik, predmetUSk);
					uspjesnost.setText("Izostanak upisan");
				} else {
					throw new Exception("datum nije izabran");
				}

			} catch (Exception e1) {
				uspjesnost.setText("Izostanak  nije upisan, desila se greska: " + e1.getMessage());

			}
		});

	}
}
