package idv.david.lambda.iteration;

public class Book {
	private String name;

	public Book() {
		super();
	}

	public Book(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		System.out.println("Book [name=" + name + "]");
		return "";
	}
	
}
