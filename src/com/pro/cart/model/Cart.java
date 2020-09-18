package com.pro.cart.model;

public class Cart implements java.io.Serializable{


	private static final long serialVersionUID = 1L;

	public Cart() {
		setOrder_Id("");
		Product_Id = "";
		Product_Name = "";
//		Product_Spic = a;
		order_buymount = 0;
		Product_Price = 0;
	}
	private String Order_Id;
	private String Product_Id;
	private String Product_Name;
//	private byte[] Product_Spic;
	private int order_buymount;
	private int Product_Price;

	public String getProduct_Id() {
		return Product_Id;
	}
	public void setProduct_Id(String product_Id) {
		Product_Id = product_Id;
	}
	public String getProduct_Name() {
		return Product_Name;
	}
	public void setProduct_Name(String product_Name) {
		Product_Name = product_Name;
	}
//	public byte[] getProduct_Spic() {
//		return Product_Spic;
//	}
//	public void setProduct_Spic(byte[] product_Spic) {
//		Product_Spic = product_Spic;
//	}
	public int getOrder_buymount() {
		return order_buymount;
	}
	public void setOrder_buymount(int order_buymount) {
		this.order_buymount = order_buymount;
	}
	public int getProduct_Price() {
		return Product_Price;
	}
	public void setProduct_Price(int product_Price) {
		Product_Price = product_Price;
	}
	public String getOrder_Id() {
		return Order_Id;
	}
	public void setOrder_Id(String order_Id) {
		Order_Id = order_Id;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Product_Id == null) ? 0 : Product_Id.hashCode());
		result = prime * result + ((Product_Name == null) ? 0 : Product_Name.hashCode());
		result = prime * result + Product_Price;
		result = prime * result + order_buymount;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (Product_Id == null) {
			if (other.Product_Id != null)
				return false;
		} else if (!Product_Id.equals(other.Product_Id))
			return false;		
		return true;
	}
	
	
	}
	
	