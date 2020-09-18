package idv.david.lambda.iteration;

import java.util.ArrayList;
import java.util.List;

public class Pen {
	private String brand;
	private double price;
	private int stock;
	private Color color;
	private boolean canErase;
	
	private Pen() {
		super();
	}
	
	private Pen(Pen.Builder builder) {
		brand = builder.brand;
		price = builder.price;
		stock = builder.stock;
		color = builder.color;
		canErase = builder.canErase;
	}
	
	public static class Builder {
		private String brand = "";
		private double price = 0.0;
		private int stock = 0;
		private Color color = Color.BLUE;
		private boolean canErase = false;
		
		public Pen.Builder setBrand(String brand) {
			this.brand = brand;
			return this;
		}
		public Pen.Builder setPrice(double price) {
			this.price = price;
			return this;
		}
		public Pen.Builder setStock(int stock) {
			this.stock = stock;
			return this;
		}
		public Pen.Builder setColor(Color color) {
			this.color = color;
			return this;
		}
		public Pen.Builder setCanErase(boolean canErase) {
			this.canErase = canErase;
			return this;
		}
		
		public Pen build() {
			return new Pen(this);
		}
	}

	public String getBrand() {
		return brand;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public Color getColor() {
		return color;
	}

	public boolean isCanErase() {
		return canErase;
	}

	@Override
	public String toString() {
		return "Pen [brand=" + brand + ", price=" + price + ", stock=" + stock + ", color=" + color + ", canErase="
				+ canErase + "]";
	}
	
	public static List<Pen> initPens() {
		List<Pen> penList = new ArrayList<>();
		penList.add(
				new Pen.Builder()
					.setBrand("SKB")
					.setPrice(10)
					.setStock(50)
					.setColor(Color.BLUE)
					.setCanErase(false)
					.build()
				);
		
		penList.add(
				new Pen.Builder()
					.setBrand("Staedtler") // 施德樓鉛筆
					.setPrice(10)
					.setStock(20)
					.setColor(Color.BLACK)
					.setCanErase(true)
					.build()
				);
		
		penList.add(
				new Pen.Builder()
					.setBrand("Pentel")
					.setPrice(50)
					.setStock(100)
					.setColor(Color.BLUE)
					.setCanErase(false)
					.build()
				);
		
		penList.add(
				new Pen.Builder()
					.setBrand("Pilot")
					.setPrice(25)
					.setStock(80)
					.setColor(Color.RED)
					.setCanErase(false)
					.build()
				);
		
		penList.add(
				new Pen.Builder()
					.setBrand("Cartire")
					.setPrice(5000)
					.setStock(50)
					.setColor(Color.BLACK)
					.setCanErase(false)
					.build()
				);
		
		penList.add(
				new Pen.Builder()
					.setBrand("Mont Blanc")
					.setPrice(14000)
					.setStock(10)
					.setColor(Color.BLACK)
					.setCanErase(false)
					.build()
				);
		return penList;
	}
	
	// 新增方法
	public void printDetails() {
		System.out.println(
			"Brand = " + brand + "\n" +	
			"Price = " + price + "\n" +	
			"Stock = " + stock + "\n" +	
			"Color = " + color + "\n" +	
			"Can be erased = " + canErase);
	}
	
}
