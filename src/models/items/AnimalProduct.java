package models.items;

public class AnimalProduct extends Item {
	private final int grade;
	
    public AnimalProduct(String name, int price, int grade) {
    	super(name, price);
    	this.grade = grade;
    }

	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return String.format("%s (Grade %d) - $%.0f", getName(), grade, getPrice());
	}
}
