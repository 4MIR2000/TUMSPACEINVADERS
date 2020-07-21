package main.java.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class DataObject {
	private LocalDateTime startTime; 
	private LocalDateTime endTime; 
	private Skill skill; 
	
	public DataObject(LocalDateTime startTime,LocalDateTime endTime, Skill skill){
		this.endTime = endTime; 
		this.startTime = startTime;
		this.skill = skill; 
	}
	
	public LocalDateTime getStartTime() {
		return startTime; 
	}
	
	public LocalDateTime getEndTime() {
		return endTime; 
	}
	
	public Skill getSkill() {
		return skill; 
	}
	
	public String toString() {
		StringBuilder message = new StringBuilder(); 
	    long durationInMinutes = Duration.between(startTime,endTime).toMinutes();
	    long durationInSeconds = Duration.between(startTime,endTime).toSeconds();
	    
	    message.append("Der Spieler hat am "); 
	    message.append(startTime); 
	    message.append(" "); 
	    
	    if(durationInMinutes==0) {
	    	message.append(durationInSeconds);
	    	message.append(" Sekunden lang das Spiel gespielt und war ");
	    }else {
	    	message.append(durationInMinutes);
	    	message.append(" Minuten lang das Spiel gespielt und war ");
	    }
	    
	    message.append(skill);
	    message.append(" darin."); 
	    message.append("\n");
	    return message.toString();
	}
}
