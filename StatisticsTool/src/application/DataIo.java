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
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataIo {

	public void run() {
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

		Controller controller = new Controller();

		
		ObservableList<TVHandler> data = FXCollections.observableArrayList();
		data.clear();
		
		TextField txRepairnumber = new TextField();
		txRepairnumber = controller.getTxRepairNumber();
		
		TextField txBrand = new TextField();
		txBrand = controller.getTxBrand();
		
		TextField txState = new TextField();
		txState = controller.getTxState();
		
		TextField txFailureText = new TextField();
		txFailureText = controller.getTxFailureText();
		
		
		
		if (txRepairnumber.getText().equals("") || txBrand.getText().equals("")
				|| txState.getText().equals("") || txFailureText.getText().equals("")) {
			// was passiert wenn eine TextView leer ist
		} else {
			data.add(new TVHandler(txRepairnumber.getText(), txBrand.getText(),
					txState.getText(), txFailureText.getText()));

			//controller.clearTextView();
		}



		TableView<TVHandler> tvData = new TableView<TVHandler>();
		tvData.setItems(data);
		controller.setTvData(tvData);

		TableColumn<TVHandler, String> tvRepairNumber = new TableColumn<TVHandler, String>();
		tvRepairNumber.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("repairNumber"));
		controller.setTvRepairNumber(tvRepairNumber);

		TableColumn<TVHandler, String> tvBrand = new TableColumn<TVHandler, String>();
		tvBrand.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("brand"));
		controller.setTvBrand(tvBrand);

		TableColumn<TVHandler, String> tvstate = new TableColumn<TVHandler, String>();
		tvstate.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		controller.setTvstate(tvstate);

		TableColumn<TVHandler, String> tvFailure = new TableColumn<TVHandler, String>();
		tvFailure.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("failureText"));
		controller.setTvFailure(tvFailure);

		ObservableList<TVHandler> tv1DataList = FXCollections.observableArrayList();
		tv1DataList.clear();

		TableView<TVHandler> tv1Data = new TableView<TVHandler>();
		tv1Data.setItems(tv1DataList);
		controller.setTv1Data(tv1Data);

		TableColumn<TVHandler, String> tv1State = new TableColumn<TVHandler, String>();
		tv1State.setCellValueFactory(new PropertyValueFactory<TVHandler, String>("state"));
		controller.setTv1State(tv1State);

		TableColumn<TVHandler, Integer> tv1Count = new TableColumn<TVHandler, Integer>();
		tv1Count.setCellValueFactory(new PropertyValueFactory<TVHandler, Integer>("count"));
		controller.setTv1Count(tv1Count);

		ObservableList<TVHandler> tv2DataList = FXCollections.observableArrayList();

		TableView<TVHandler> tv2Data = new TableView<TVHandler>();
		tv2Data.setItems(tv2DataList);
		controller.setTv2Data(tv2Data);
		
		TableColumn<TVHandler, String> tv2State = new TableColumn<TVHandler, String>();
		tv2State.setCellValueFactory(new PropertyValueFactory<>("state"));
		controller.setTv2State(tv2State);

		TableColumn<TVHandler, Double> tv2Count = new TableColumn<TVHandler, Double>();
		tv2Count.setCellValueFactory(new PropertyValueFactory<>("percent"));
		controller.setTv2Count(tv2Count);

		if (controller.getTxRepairNumber().getText().equals("") || controller.getTxBrand().getText().equals("")
				|| controller.getTxState().getText().equals("") || controller.getTxFailureText().getText().equals("")) {
			// was passiert wenn eine TextView leer ist
		} else {

			try {
				BufferedWriter output = new BufferedWriter(new FileWriter(fileName, true));
				output.write(controller.getTxRepairNumber().getText() + sharp + controller.getTxBrand().getText()
						+ sharp + controller.getTxState().getText() + sharp + controller.getTxFailureText().getText()
						+ sharp + dateTextout + newLine);

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

		PieChart pie = new PieChart();
		pie.setData(pieData);
		controller.setPie(pie);

		input.close();

	}

}
