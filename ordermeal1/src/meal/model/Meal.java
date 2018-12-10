package meal.model;

import java.awt.Image;
import java.awt.Toolkit;

public class Meal {
	private String name;//
	private double price;
	private String description;
	private Image image;

	public Meal(String name, String description, double price, String pfilename) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.description = description;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setImage(toolkit.createImage(pfilename));

	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	public String toString() {
		return name;
	}
}
