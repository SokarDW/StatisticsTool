package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

	@FXML
	private Button btnSave;
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

	public void setPie(PieChart pie) {
		this.pie = pie;
	}

	public TextField getTxRepairNumber() {
		return txRepairNumber;
	}

	public void setTxRepairNumber(TextField txRepairNumber) {
		this.txRepairNumber = txRepairNumber;
	}

	public TextField getTxBrand() {
		return txBrand;
	}

	public void setTxBrand(TextField txBrand) {
		this.txBrand = txBrand;
	}

	public TextField getTxState() {
		return txState;
	}

	public void setTxState(TextField txState) {
		this.txState = txState;
	}

	public TextField getTxFailureText() {
		return txFailureText;
	}

	public void setTxFailureText(TextField txFailureText) {
		this.txFailureText = txFailureText;
	}

	public TableView<TVHandler> getTvData() {
		return tvData;
	}

	public void setTvData(TableView<TVHandler> tvData) {
		this.tvData = tvData;
	}

	public TableColumn<TVHandler, String> getTvRepairNumber() {
		return tvRepairNumber;
	}

	public void setTvRepairNumber(TableColumn<TVHandler, String> tvRepairNumber) {
		this.tvRepairNumber = tvRepairNumber;
	}

	public TableColumn<TVHandler, String> getTvBrand() {
		return tvBrand;
	}

	public void setTvBrand(TableColumn<TVHandler, String> tvBrand) {
		this.tvBrand = tvBrand;
	}

	public TableColumn<TVHandler, String> getTvstate() {
		return tvstate;
	}

	public void setTvstate(TableColumn<TVHandler, String> tvstate) {
		this.tvstate = tvstate;
	}

	public TableColumn<TVHandler, String> getTvFailure() {
		return tvFailure;
	}

	public void setTvFailure(TableColumn<TVHandler, String> tvFailure) {
		this.tvFailure = tvFailure;
	}

	public TableView<TVHandler> getTv1Data() {
		return tv1Data;
	}

	public void setTv1Data(TableView<TVHandler> tv1Data) {
		this.tv1Data = tv1Data;
	}

	public TableColumn<TVHandler, String> getTv1State() {
		return tv1State;
	}

	public void setTv1State(TableColumn<TVHandler, String> tv1State) {
		this.tv1State = tv1State;
	}

	public TableColumn<TVHandler, Integer> getTv1Count() {
		return tv1Count;
	}

	public void setTv1Count(TableColumn<TVHandler, Integer> tv1Count) {
		this.tv1Count = tv1Count;
	}

	public TableView<TVHandler> getTv2Data() {
		return tv2Data;
	}

	public void setTv2Data(TableView<TVHandler> tv2Data) {
		this.tv2Data = tv2Data;
	}

	public TableColumn<TVHandler, String> getTv2State() {
		return tv2State;
	}

	public void setTv2State(TableColumn<TVHandler, String> tv2State) {
		this.tv2State = tv2State;
	}

	public TableColumn<TVHandler, Double> getTv2Count() {
		return tv2Count;
	}

	public void setTv2Count(TableColumn<TVHandler, Double> tv2Count) {
		this.tv2Count = tv2Count;
	}

	@FXML
	void save(ActionEvent event) {

		dataIo();
		tvData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
//		DataIo dataIo = new DataIo();
//		dataIo.run();

	}

	public void dataIo() {
		String fileName = "Data.txt";
		String repairNumber = null;
		String brand = null;
		String state = null;
		String failureText = null;
		String dateTextout = null;
		String dateTextin = null;
		String sharp = "#";
		String newLine = "\n";
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

			clearTextView();
		}

		tvData.setItems(data);

		tvRepairNumber.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("repairNumber"));
		tvBrand.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("brand"));
		tvstate.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		tvFailure.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("failureText"));

		ObservableList<TVHandler> tv1DataList = FXCollections.observableArrayList();
		tv1DataList.clear();

		tv1Data.setItems(tv1DataList);

		tv1State.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		tv1Count.setCellValueFactory(new PropertyValueFactory<TVHandler, Integer>("count"));

		ObservableList<TVHandler> tv2DataList = FXCollections.observableArrayList();

		tv2Data.setItems(tv2DataList);

		tv2State.setCellValueFactory(new PropertyValueFactory<>("state"));
		tv2Count.setCellValueFactory(new PropertyValueFactory<>("percent"));

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
			} catch (IOException e) {
				System.out.println("Daten konnten nicht gesichert werdne.");
			}
		}
		qState = 0;
		uState = 0;

		while (input.hasNext()) {
			tv2DataList.clear();
			split = input.nextLine().split("#");

			repairNumber = split[0];
			brand = split[1];
			state = split[2];
			failureText = split[3];
			dateTextin = split[4];

			if (split[2].equals("Q")) {
				qState += 1;
			}
			if (split[2].equals("U")) {
				uState += 1;
			}

			data.add(new TVHandler(repairNumber, brand, state, failureText));
			tv1DataList.clear();
			tv1DataList.add(new TVHandler("Q", qState));
			tv1DataList.add(new TVHandler("U", uState));
			tv1DataList.add(new TVHandler("Summe", qState + uState));
			percent = (((double) (qState)) / (((double) (qState)) + ((double) (uState))) * 100.0);

			tv2DataList.add(new TVHandler("Erfolgequote", percent));

		}

		ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(new PieChart.Data("Q", qState),
				new PieChart.Data("U", uState));

		pie.setData(pieData);

		input.close();

	}

	public void clearTextView() {
		txRepairNumber.clear();
		txBrand.clear();
		txState.clear();
		txFailureText.clear();
	}

}
