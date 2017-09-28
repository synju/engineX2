package flowershop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enginex.FileManager;

public class Profile {
	Game		game;
	String		name		= "Bob";
	String		quantity	= "4120";
	String		grain		= "10";
	String		data;
	FileManager	fm;

	Profile(Game game, String name) {
		this.game = game;
		this.name = name;
	}

	Profile(Game game, String path, boolean moo) {
		this.game = game;

		fm = new FileManager();

		String[] inventory = new String[] {"water", "10", "compost", "5"};
		String[] plants = new String[] {"Rose", "15", "Sunflower", "16"};

		String[][] allData = new String[][] {inventory, plants};
		data = String.join(",", Arrays.deepToString(allData));

		List<String> mydata = new ArrayList<>();
		mydata.add(data);
		fm.save(path, mydata);

		List<String> newData = fm.load(path);

		for(String s:newData) {
			String[] t = s.split(",");
			for(int i = 0; i < 2; i++) {
				// Inventory
				if(i == 0) {
					inventory = t[0].split(",");
				}

				// Plants
				if(i == 1) {
					plants = t[1].split(",");
				}
			}
		}

		for(String r:inventory)
			System.out.println(r);

		for(String r:plants)
			System.out.println(r);
	}
}