package flowershop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import enginex.Button;
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
	FileManager										fm;
	Profile												defaultProfile;
	
	// Buttons
	ArrayList<Button>							buttons;
	Button												createProfileButton;
	
	// Constructor
	ProfileManager(Game game) {
		super(game);
		this.game = game;
		this.w = 350;
		this.h = 50;
		this.x = game.width / 2 - this.w / 2;
		this.y = 100;
		fm = new FileManager();
		loadProfiles();
		setInitialProfile();
		createProfileManagerItems();
		createButtons();
	}
	
	// Initialize Initial Profile
	void setInitialProfile() {
		defaultProfile = new Profile(game, "default");
		
		if(profiles.size() > 0) {
			setCurrentProfile(profiles.get(0));
		}
		else {
			profiles.add(defaultProfile);
			setCurrentProfile(defaultProfile);
		}
	}
	
	// Create Buttons
	void createButtons() {
		buttons = new ArrayList<>();
		
		createProfileButton = new Button(game, 50, 50);
		buttons.add(createProfileButton);
	}
	
	// Load All Profiles
	void loadProfiles() {
		// Clear Profiles Loaded...
		profiles = new ArrayList<>();
		plist = new ArrayList<>();
		
		// Get Profiles Available
		ArrayList<String> names = getFileNames();
		
		// Create them all from save data
		for(String n:names) {
			profiles.add(loadProfile(n));
		}
	}
	
	// Create ProfileManagerItems
	public void createProfileManagerItems() {
		for(int i = 0; i < profiles.size(); i++) {
			plist.add(new ProfileManagerItem(game, x, y + h + (h * i), w, h, profiles.get(i).name, this, profiles.get(i)));
		}
	}
	
	// Get list of profiles saved on disc
	public ArrayList<String> getFileNames() {
		ArrayList<String> fileNames = new ArrayList<String>();
		
		try {
			DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("./saveData/"), path->path.toString().endsWith(".profile"));
			for(Path p:paths) {
				fileNames.add(p.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<String> newFileNames = new ArrayList<>();
		for(String n:fileNames) {
			n = n.replace(".\\saveData\\", "");
			n = n.replace(".profile", "");
			newFileNames.add(n);
		}
		
		return newFileNames;
	}
	
	// Save Profile
	public void saveProfile(Profile profile) {
		ProfileData pd = profile.getProfileData();
		try {
			FileOutputStream fileOut = new FileOutputStream("./saveData/" + pd.name + ".profile");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(pd);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /saveData/" + pd.name + ".profile");
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	// Load Profile
	public Profile loadProfile(String name) {
		Profile profile = null;
		
		ProfileData pd = null;
		try {
			FileInputStream fileIn = new FileInputStream("./saveData/" + name + ".profile");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			pd = (ProfileData)in.readObject();
			in.close();
			fileIn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			game.exit();
		}
		
		profile = new Profile(game, pd);
		
		return profile;
	}
	
	// Hide Profile Manager
	public void toggleOff() {
		if(visible)
			visible = false;
	}
	
	// Show Profile Manager
	public void toggleOn() {
		if(!visible)
			visible = true;
	}
	
	public ArrayList<Profile> getProfiles() {
		return profiles;
	}
	
	public Profile getCurrentProfile() {
		return currentProfile;
	}
	
	public void setCurrentProfile(Profile selectedProfile) {
		currentProfile = selectedProfile;
		visible = false;
	}
	
	public void update() {
		// ProfileManagerItems
		for(ProfileManagerItem p:plist) {
			p.update();
		}
		
		// Buttons
		for(Button b:buttons) {
			b.update();
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
			
			// Render Profiles
			for(ProfileManagerItem p:plist) {
				p.render(g);
			}
			
			// Buttons
			for(Button b:buttons) {
				b.render(g);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		// Buttons (Done Individually!!!)
		if(createProfileButton.hover)
			System.out.println("click");
		
		// Profiles
		for(ProfileManagerItem p:plist) {
			if(p.hover) {
				setCurrentProfile(p.profile);
			}
		}
	}
}
