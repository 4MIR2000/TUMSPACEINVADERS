package main.java.model;

import java.time.LocalDateTime;

public interface AnalyserInterface {
	public Skill getPlayerSkill(); 
	public LocalDateTime getGameStartTime(); 
	public void setGameStartTime(LocalDateTime startTime);
}
