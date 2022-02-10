package engine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import entities.Izostanak;
import entities.Ocjena;
import entities.OcjenaPredmeta;
import entities.Pitanje;
import entities.Predmet;
import entities.PredmetUSkoli;
import entities.PristupniPodaci;
import entities.Profesor;
import entities.Skola;
import entities.Ucenik;

public class ConnectDB {

	public static void connect() {

		ResultSet resultSet = null;
		try {
			String DB_user = "root";
			String DB_password = "12345";
			String connectionUrl = "jdbc:mysql://localhost";
			int port = 3306;
			String DB_name = "ors1_opp_2021_2022";
			connectionUrl = connectionUrl + ":" + port + "/" + DB_name;

			Connection connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);

			Statement statement = connection.createStatement();

			// -----------------------------------------------------------------------------------------
			// PITANJE
			String selectSql = "SELECT * FROM pitanje";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String tekstPitanja = resultSet.getString("pitanje");
				Pitanje.addPitanje(id, tekstPitanja);
			}

			// SKOLA
			selectSql = "SELECT * FROM skola";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String naziv = resultSet.getString("naziv");
				String grad = resultSet.getString("grad");
				String mjesto = resultSet.getString("mjesto");
				String drzava = resultSet.getString("drzava");
				Skola.addSkola(id, naziv, grad, mjesto, drzava);
			}

			// PREDMET
			selectSql = "SELECT * FROM predmet";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String naziv = resultSet.getString("naziv");
				int razred = Integer.parseInt(resultSet.getString("razred"));
				Predmet.addPredmet(id, naziv, razred);
			}

			// PRISTUPNI PODACI
			selectSql = "SELECT * FROM pristupni_podaci";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String korisnicko_ime = resultSet.getString("korisnicko_ime");
				String sifra = resultSet.getString("sifra");
				String email = resultSet.getString("email");

				PristupniPodaci.addPristupniPodatak(id, korisnicko_ime, sifra, email);
			}
			PristupniPodaci.printSviPristupniPodaci();
			// UCENIK
			selectSql = "SELECT * FROM ucenik";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				int pol = Integer.parseInt(resultSet.getString("pol"));
				int pristupni_podatak_id = Integer.parseInt(resultSet.getString("pristupni_podaci_id"));

				Ucenik.addUcenik(id, ime, prezime, pol, pristupni_podatak_id);
				;
			}

			// PROFESOR
			selectSql = "SELECT * FROM profesor";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				int pol = Integer.parseInt(resultSet.getString("pol"));
				int pristupni_podatak_id = Integer.parseInt(resultSet.getString("pristupni_podaci_id"));

				Profesor.addProfesor(id, ime, prezime, pol, pristupni_podatak_id);
			}

			// PREDMET U SKOLI
			selectSql = "SELECT * FROM predmet_u_skoli";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				int predmet_id = Integer.parseInt(resultSet.getString("predmet_id"));
				int skola_id = Integer.parseInt(resultSet.getString("skola_id"));
				int profesor_id = Integer.parseInt(resultSet.getString("profesor_id"));

				PredmetUSkoli.addPredmet(id, predmet_id, skola_id, profesor_id);
			}

			// IZOSTANCI
			selectSql = "SELECT * FROM izostanci";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				int predmet_u_sk_id = Integer.parseInt(resultSet.getString("predmet_u_skoli_id"));
				int ucenik_id = Integer.parseInt(resultSet.getString("ucenik_id"));
				Date datum = Date.valueOf(resultSet.getString("datum"));

				Izostanak.addIzostanak(id, predmet_u_sk_id, ucenik_id, datum);
			}

			// OCJENA PREDMETA
			selectSql = "SELECT * FROM ocjena_predmeta";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				int ucenik_id = Integer.parseInt(resultSet.getString("ucenik_id"));
				int predmet_u_sk_id = Integer.parseInt(resultSet.getString("predmet_u_skoli_id"));
				int pitanje_id = Integer.parseInt(resultSet.getString("Pitanje_id"));
				int ocjena = Integer.parseInt(resultSet.getString("ocjena"));

				OcjenaPredmeta.addOcjenaPredmeta(id, ucenik_id, predmet_u_sk_id, pitanje_id, ocjena);
			}

			// OCJENA
			selectSql = "SELECT * FROM ocjena";
			resultSet = statement.executeQuery(selectSql);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				int ucenik_id = Integer.parseInt(resultSet.getString("ucenik_id"));
				int predmet_u_sk_id = Integer.parseInt(resultSet.getString("predmet_u_skoli_id"));
				int ocjena = Integer.parseInt(resultSet.getString("ocjena"));
				Date datum = Date.valueOf(resultSet.getString("datum"));

				Ocjena.addOcjena(id, ucenik_id, predmet_u_sk_id, ocjena, datum);
			}

			// ----------------------------------------------------------------------------------------------------------

			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void insertData(String nazivTabele, String[] vrijednosti) {
		try {
			String DB_user = "root";
			String DB_password = "12345";
			String connectionUrl = "jdbc:mysql://localhost";
			int port = 3306;
			String DB_name = "ors1_opp_2021_2022";
			connectionUrl = connectionUrl + ":" + port + "/" + DB_name;

			Connection connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);

			Statement statement = connection.createStatement();
			// -----------------------------------------------------------------------------------------

			String selectSql = "INSERT INTO " + nazivTabele + " VALUES " + "(";
			for (String s : vrijednosti) {
				selectSql += "'" + s + "'" + ",";
			}
			selectSql = selectSql.substring(0, selectSql.length() - 1) + ");"; // ovo izbacuje zadnji zarez a dodaje );

			System.out.println(selectSql);
			statement.execute(selectSql);

			// ----------------------------------------------------------------------------------------------------------

			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void delete(String nazivTabele, int id) {
		try {
			String DB_user = "root";
			String DB_password = "12345";
			String connectionUrl = "jdbc:mysql://localhost";
			int port = 3306;
			String DB_name = "ors1_opp_2021_2022";
			connectionUrl = connectionUrl + ":" + port + "/" + DB_name;

			Connection connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);

			Statement statement = connection.createStatement();
			// -----------------------------------------------------------------------------------------

			String zahtjev = "DELETE FROM " + nazivTabele + " WHERE id=" + id + ";";

			System.out.println(zahtjev);
			statement.execute(zahtjev);

			// ----------------------------------------------------------------------------------------------------------

			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void changePassword(int pristupniPodatakID, String sifra) {
		try {
			String DB_user = "root";
			String DB_password = "12345";
			String connectionUrl = "jdbc:mysql://localhost";
			int port = 3306;
			String DB_name = "ors1_opp_2021_2022";
			connectionUrl = connectionUrl + ":" + port + "/" + DB_name;

			Connection connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);

			Statement statement = connection.createStatement();
			// -----------------------------------------------------------------------------------------

			String zahtjev = "UPDATE pristupni_podaci SET sifra=" + "MD5('" + sifra + "') WHERE id="
					+ pristupniPodatakID + ";";

			System.out.println(zahtjev);
			statement.execute(zahtjev);

			// ----------------------------------------------------------------------------------------------------------

			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}