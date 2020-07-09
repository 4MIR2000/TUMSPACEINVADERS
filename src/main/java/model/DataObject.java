package main.java.model;

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
}
