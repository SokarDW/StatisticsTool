package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
	String fileName = "Data.txt";

	@FXML
	private Button btnSave;
	@FXML
	private Button btnSearch;
	@FXML
	private PieChart pie;

	@FXML
	private TextField txRepairNumber;

	@FXML
	private TextField txBrand;

	@FXML
	private TextField txState;

	@FXML
	private TextField txFailureText;

	@FXML
	private TableView<TVHandler> tvData;

	@FXML
	private TableColumn<TVHandler, String> tvRepairNumber;

	@FXML
	private TableColumn<TVHandler, String> tvBrand;

	@FXML
	private TableColumn<TVHandler, String> tvstate;

	@FXML
	private TableColumn<TVHandler, String> tvFailure;

	@FXML
	private TableView<TVHandler> tv1Data;

	@FXML
	private TableColumn<TVHandler, String> tv1State;

	@FXML
	private TableColumn<TVHandler, Integer> tv1Count;

	@FXML
	private TableView<TVHandler> tv2Data;

	@FXML
	private TableColumn<TVHandler, String> tv2State;

	@FXML
	private TableColumn<TVHandler, Double> tv2Count;

	public PieChart getPie() {
		return pie;
	}

	@FXML
	void save(ActionEvent event) {

		dataIo();
		tvData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// DataIo dataIo = new DataIo();
		// dataIo.run();

	}

	@FXML
	void search(ActionEvent event) {
		dataSearch();
	}

	public void dataIo() {

		String dateTextout = null;
		String sharp = "#";
		String newLine = "\n";

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateTextout = dateFormat.format(date); // 2016/11/16

		ObservableList<TVHandler> data = FXCollections.observableArrayList();
		data.clear();

		if (txRepairNumber.getText().equals("") || txBrand.getText().equals("") || txState.getText().equals("")
				|| txFailureText.getText().equals("")) {
			// was passiert wenn eine TextView leer ist
		} else {
			data.add(new TVHandler(txRepairNumber.getText(), txBrand.getText(), txState.getText(),
					txFailureText.getText()));

		}
		data.setAll(data());
		tvSetData(data);

		ObservableList<TVHandler> tv1DataList = FXCollections.observableArrayList();
		tv1DataList.setAll(data1());
		tv1SetData(tv1DataList);

		if (txRepairNumber.getText().equals("") || txBrand.getText().equals("") || txState.getText().equals("")
				|| txFailureText.getText().equals("")) {
			// was passiert wenn eine TextView leer ist

		} else {

			try {
				BufferedWriter output = new BufferedWriter(new FileWriter(fileName, true));
				output.write(txRepairNumber.getText() + sharp + txBrand.getText() + sharp + txState.getText() + sharp
						+ txFailureText.getText() + sharp + dateTextout + newLine);
				System.out.print(txRepairNumber.getText() + sharp + txBrand.getText() + sharp + txState.getText()
						+ sharp + txFailureText.getText() + sharp + dateTextout + newLine);
				output.close();
				clearTextView();
			} catch (IOException e) {
				System.out.println("Daten konnten nicht gesichert werden.");
				clearTextView();
			}
		}

	}

	public void dataSearch() {

		ObservableList<TVHandler> data = FXCollections.observableArrayList();
		data.clear();

		data.setAll(data());

		for (int x = 0; x < data.size(); x++) {
			String repNrSearch = txRepairNumber.getText();
			String brandSearch = txBrand.getText();
			String stateSearch = txState.getText();
			String failureSearch = txFailureText.getText();

			if (repNrSearch.equals(data.get(x).getRepairNumber())) {
				deliverSearch(data, x);
			} else if (brandSearch.equals(data.get(x).getBrand())) {
				deliverSearch(data, x);
			} else if (stateSearch.equals(data.get(x).getState())) {
				deliverSearch(data, x);
			} else if (failureSearch.equals(data.get(x).getFailureText())) {
				deliverSearch(data, x);
			}

		}

	}

	public ObservableList<TVHandler> data() {
		String repairNumber = null;
		String brand = null;
		String state = null;
		String failureText = null;
		String dateTextin = null;
		Scanner input = null;
		String[] split;

		try {
			input = new Scanner(Paths.get(fileName));

		} catch (IOException e) {
			System.err.println("Datei nicht gefunden!");

		}
		ObservableList<TVHandler> data = FXCollections.observableArrayList();
		data.clear();

		while (input.hasNext()) {

			split = input.nextLine().split("#");

			repairNumber = split[0];
			brand = split[1];
			state = split[2];
			failureText = split[3];
			dateTextin = split[4];

			data.add(new TVHandler(repairNumber, brand, state, failureText));

		}
		input.close();

		return data;
	}

	public ObservableList<TVHandler> data1() {

		Scanner input = null;
		String[] split;
		Integer qState = 0;
		Integer uState = 0;
		Double percent = 0.0;

		try {
			input = new Scanner(Paths.get(fileName));

		} catch (IOException e) {
			System.err.println("Datei nicht gefunden!");

		}
		ObservableList<TVHandler> data = FXCollections.observableArrayList();
		data.clear();

		while (input.hasNext()) {

			split = input.nextLine().split("#");



			if (split[2].equals("Q")) {
				qState += 1;
			}
			if (split[2].equals("U")) {
				uState += 1;
			}

			data.clear();
			data.add(new TVHandler("Q", qState));
			data.add(new TVHandler("U", uState));
			data.add(new TVHandler("Summe", qState + uState));
			percent = (((double) (qState)) / (((double) (qState)) + ((double) (uState))) * 100.0);

		}
		ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(new PieChart.Data("Q", qState),
				new PieChart.Data("U", uState));

		pie.setData(pieData);
		input.close();
		return data;
	}

	public void tvSetData(ObservableList<TVHandler> data) {
		tvData.setItems(data);
		tvRepairNumber.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("repairNumber"));
		tvBrand.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("brand"));
		tvstate.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		tvFailure.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("failureText"));
	}

	public void tv1SetData(ObservableList<TVHandler> data) {
		tv1Data.setItems(data);
		tv1State.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		tv1Count.setCellValueFactory(new PropertyValueFactory<TVHandler, Integer>("count"));
	}

	public void tv2SetData(ObservableList<TVHandler> data) {
		tv2Data.setItems(data);
		tv2State.setCellValueFactory(new PropertyValueFactory<>("state"));
		tv2Count.setCellValueFactory(new PropertyValueFactory<>("percent"));
	}

	public void deliverSearch(ObservableList<TVHandler> data, int x) {
		ObservableList<TVHandler> dataFiltered = FXCollections.observableArrayList();
		dataFiltered.add(new TVHandler(data.get(x).getRepairNumber(), data.get(x).getBrand(), data.get(x).getState(),
				data.get(x).getFailureText()));
		tvSetData(dataFiltered);
	}

	public void clearTextView() {
		txRepairNumber.clear();
		txBrand.clear();
		txState.clear();
		txFailureText.clear();
	}

}
