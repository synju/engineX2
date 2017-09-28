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
	
	ProfileManager(Game game) {
		super(game);
		this.game = game;
		this.w = 350;
		this.h = 50;
		this.x = game.width / 2 - this.w / 2;
		this.y = 100;
		fm = new FileManager();
		test();
	}
	
	public void test() {
		ArrayList<String> names = getFileNames();
		for(String n:names) {
			profiles.add(new Profile(game, deserialize(n)));
		}
		
		// ProfileData pd = new ProfileData("moo", 19, new ArrayList<Flower>());
		// serialize(pd);
	}
	
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
	
	public void serialize(ProfileData pd) {
		try {
			FileOutputStream fileOut = new FileOutputStream("/saveData/" + pd.name + ".profile");
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
	
	public ProfileData deserialize(String name) {
		ProfileData pd = null;
		try {
			FileInputStream fileIn = new FileInputStream("/saveData/" + name + ".profile");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			pd = (ProfileData)in.readObject();
			in.close();
			fileIn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return pd;
	}
	
	public void toggleOff() {
		if(visible)
			visible = false;
	}
	
	public void toggleOn() {
		if(!visible)
			visible = true;
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
	
	@SuppressWarnings("unused")
	private void loadProfiles() {
		for(int i = 0; i < profiles.size() - 1; i++) {
			plist.add(new ProfileManagerItem(game, x, y + h + (h * i), w, h, profiles.get(i).name, this, profiles.get(i)));
		}
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
