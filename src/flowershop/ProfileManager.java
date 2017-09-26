package flowershop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.FileManager;
import enginex.GameObject;
import enginex.Util;

@SuppressWarnings("serial")
public class ProfileManager extends GameObject {
	Game													game;
	Profile												currentProfile	= null;
	ArrayList<Profile>						profiles				= new ArrayList<>();
	ArrayList<ProfileManagerItem>	plist						= new ArrayList<>();
	int														x;
	int														y;
	int														w;
	int														h;
	boolean												visible					= false;
	FileManager fm;
	
	ProfileManager(Game game) {
		super(game);
		this.game = game;
		loadProfiles();
		this.w = 350;
		this.h = 50;
		this.x = game.width / 2 - this.w / 2;
		this.y = 100;
		fm = new FileManager();
		dummyProfiles();
	}
	
	public void toggleOff() {
		if(visible)
			visible = false;
	}
	
	public void toggleOn() {
		if(!visible)
			visible = true;
	}
	
	public void dummyProfiles() {
		profiles.add(new Profile(game, "Charlie"));
		profiles.add(new Profile(game, "Manju"));
		profiles.add(new Profile(game, "Leona"));
		profiles.add(new Profile(game, "Teona"));
		profiles.add(new Profile(game, "Terona"));
		profiles.add(new Profile(game, "Derona"));
		profiles.add(new Profile(game, "Jacqueline"));
		
		for(int i = 0; i < profiles.size(); i++) {
			plist.add(new ProfileManagerItem(game, x, y + h + (h * i), w, h, profiles.get(i).name, this, profiles.get(i)));
		}
	}
	
	public void setProfile() {
		
	}
	
	public ArrayList<Profile> getProfiles() {
		return profiles;
	}
	
	public Profile getProfile(int index) {
		Profile selectedProfile = null;
		
		return selectedProfile;
	}
	
	public Profile getCurrentProfile() {
		return currentProfile;
	}
	
	// Load all profiles into profiles ArrayList...
	private void loadProfiles() {
		// Code Needed...
	}
	
	public void loadProfile(Profile selectedProfile) {
		currentProfile = selectedProfile;
		visible = false;
	}
	
	public void update() {
		for(ProfileManagerItem p:plist) {
			p.update();
		}
	}
	
	public void render(Graphics2D g) {
		if(visible) {
			// Render Top
			g.setColor(new Color(63 / 255f, 84 / 255f, 117 / 255f, 1f));
			g.fillRect(x, y, w, h);
			
			// Render Title
			g.setColor(Color.white);
			Util.drawText("Profiles", x + 10, y + 37, 30, g);
			
			for(ProfileManagerItem p:plist) {
				p.render(g);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		for(ProfileManagerItem p:plist) {
			if(p.hover) {
				loadProfile(p.profile);
			}
		}
	}
}
