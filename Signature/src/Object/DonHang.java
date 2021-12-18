package Object;

import java.io.Serializable;

public class DonHang implements Serializable {
	private String id;
	private String name;
	private int quantity;
	
	public DonHang(String id, String name, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "DonHang [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
	}
	

}
