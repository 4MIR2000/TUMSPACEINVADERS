package main.java.model;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import main.java.view.GameboardUI;
import main.java.view.MainMenu;

@RunWith(EasyMockRunner.class)
public class DataCollectionTest {
	
	@TestSubject
	private MainMenu mainMenu = new MainMenu(); 
	
	@Mock 
	AnalyserInterface analyserMockWon;
	
	@Mock 
	AnalyserInterface analyserMockLost;
	
	
	@Before 
	public void initialize() {
		expect(analyserMockWon.getPlayerSkill()).andReturn(Skill.good).anyTimes(); 
		expect(analyserMockWon.getGameStartTime()).andReturn(LocalDateTime.now()).anyTimes(); //not important 
		expect(analyserMockWon.getGameEndTime()).andReturn(LocalDateTime.now()).anyTimes(); //not important 
		replay(analyserMockWon);
		
		expect(analyserMockLost.getPlayerSkill()).andReturn(Skill.bad).anyTimes(); 
		expect(analyserMockLost.getGameStartTime()).andReturn(LocalDateTime.now()).anyTimes(); //not important 
		expect(analyserMockLost.getGameEndTime()).andReturn(LocalDateTime.now()).anyTimes(); //not important 
		replay(analyserMockLost);
	}
	
	@Test 
	public void dataAddTest() {
		mainMenu.setHighscore(0);
		mainMenu.setWinStreak(0);
		
		//simulate game ended, and player won 
		mainMenu.Callback(analyserMockWon);
		
		//new data was added? 
		assertEquals(mainMenu.getCollectedData().size(),1);
	}
	
	@Test
	public void winstreakUpdateTest() {
		mainMenu.setWinStreak(0);
		
		//player has won 
		mainMenu.Callback(analyserMockWon);
		assertEquals(mainMenu.getWinstreak(),1);
		
		//player has won again
		mainMenu.Callback(analyserMockWon);
		assertEquals(mainMenu.getWinstreak(),2);
		
		//player has lost 
		mainMenu.Callback(analyserMockLost);
		assertEquals(mainMenu.getWinstreak(),0);
	}
	
	@Test
	public void highscoreUpdateTest() {
		mainMenu.setWinStreak(0);
		mainMenu.setHighscore(0);
		
		//player has won 
		mainMenu.Callback(analyserMockWon);
		assertEquals(mainMenu.getHeighScore(),1);
		//player has lost 
		mainMenu.Callback(analyserMockLost);
		//player has won 
		mainMenu.Callback(analyserMockWon);
		//player has won 
		mainMenu.Callback(analyserMockWon);
		assertEquals(mainMenu.getHeighScore(),2);
	}
	
	
}
