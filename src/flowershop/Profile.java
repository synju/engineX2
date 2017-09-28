package flowershop;

import enginex.FileManager;

public class Profile {
	Game				game;
	String			name			= "Bob";
	String			quantity	= "4120";
	String			grain			= "10";
	String			data;
	FileManager	fm;
	ProfileData pd;
	
	Profile(Game game, ProfileData pd) {
		this.game = game;
		this.pd = pd;
	}
}