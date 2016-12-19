package application;

import java.util.Observable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TVHandler extends Observable {

	private StringProperty repairNumber;
	private StringProperty brand;
	private StringProperty state;
	private StringProperty failureText;
	private SimpleIntegerProperty count;
	private SimpleDoubleProperty percent;

	public TVHandler(String repairNumber, String brand, String state, String failureText) {
		this.repairNumber = new SimpleStringProperty(repairNumber);
		this.brand = new SimpleStringProperty(brand);
		this.state = new SimpleStringProperty(state);
		this.failureText = new SimpleStringProperty(failureText);

	}

	public TVHandler(String state, Integer count) {
		this.state = new SimpleStringProperty(state);
		this.count = new SimpleIntegerProperty(count);

	}

	public TVHandler(String state, Double percent) {
		this.state = new SimpleStringProperty(state);
		this.percent = new SimpleDoubleProperty(percent);

	}

	public String getRepairNumber() {
		return repairNumber.get();
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber.set(repairNumber);
	}

	public StringProperty repairNumberProperty() {
		return repairNumber;
	}

	public String getBrand() {
		return brand.get();
	}

	public void setBrand(String brand) {
		this.brand.set(brand);
	}

	public StringProperty brandProperty() {
		return brand;
	}

	public String getState() {
		return state.get();
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public StringProperty stateProperty() {
		return state;
	}

	public String getFailureText() {
		return failureText.get();
	}

	public void setFailureText(String failureText) {
		this.failureText.set(failureText);
	}

	public StringProperty failureTextProperty() {
		return failureText;
	}

	public Integer getCount() {
		return count.get();
	}

	public void setCount(Integer count) {
		this.count.set(count);
	}

	public IntegerProperty countProperty() {
		return count;
	}

	public Double getPercent() {
		return percent.get();
	}

	public void setPercent(Double percent) {
		this.percent.set(percent);
	}

	public DoubleProperty percentProperty() {
		return percent;
	}

}
