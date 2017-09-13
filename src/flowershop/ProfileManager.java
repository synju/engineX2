package flowershop;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ProfileManager {
	Game game;
	Profile currentProfile = null;
	ArrayList<Profile> profiles = new ArrayList<>();
	int x;
	int y;
	int w;
	int h;
	boolean visible = false;
	
	ProfileManager(Game game) {
		this.game = game;
		loadProfiles();
	}
	
	public void setProfile() {
		
	}
	
	public Profile getProfile(int index) {
		Profile selectedProfile = null;
		
		return selectedProfile;
	}
	
	public Profile getCurrentProfile() {
		return currentProfile;
	}
	
//Load all profiles into profiles ArrayList...
	private void loadProfiles() {
		// Code Needed...
	}
	
	public void loadProfile() {
		Profile selectedProfile = null;
		
		// Code needed...
		
		currentProfile = selectedProfile;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		if(visible) {
			
		}
	}
}
