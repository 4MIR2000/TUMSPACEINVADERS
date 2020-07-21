package main.java.model;

public enum Skill {
	veryBad, bad, good, veryGood;
	
	public String toString() {
		switch(this) {
		case veryBad: 
			return "sehr schlecht"; 
		case bad: 
			return "schlecht"; 
		case good: 
			return "gut"; 
		case veryGood:
			return "sehr gut"; 
		default:
			return "nicht definiert"; 
		}
	}
}
