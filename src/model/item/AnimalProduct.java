package model.item;

public class AnimalProduct extends Item {
	private AnimalProductGrade grade;
	
    public AnimalProduct(String name, int price, AnimalProductGrade grade) {
    	super(name, price);
    	this.grade = grade;
    }

	public AnimalProductGrade getGrade() {
		return grade;
	}

	public void setGrade(AnimalProductGrade grade) {
		this.grade = grade;
	}

	public double getSellingPrice() {
		return getPrice();
	}

	@Override
	public boolean canStackWith(Item other) {
		if (!super.canStackWith(other)) return false;
		return this.grade == ((AnimalProduct) other).getGrade();
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getName(), grade.getLevel());
	}
}
