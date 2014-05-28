package core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright EndoPlasm Gaming ©2013
 * Edited 14/12/2013
 */
public class ModelLoader {
	
	public static Model load(String key){
		Model model = new Model();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(new File("Resources/Model/" + key + ".obj")));
			BufferedReader br = new BufferedReader(in);
			
			String line = "";
			
			while((line = br.readLine()) != null){
				String s[] = line.split(" ");
				if(line.startsWith("vt")){
					model.vt.add(new Vertex2f(Float.parseFloat(s[1]), Float.parseFloat(s[2])));
				}else if(line.startsWith("v")){
					model.v.add(new Vertex3f(Float.parseFloat(s[1]),Float.parseFloat(s[2]),Float.parseFloat(s[3])));
				}else if(line.startsWith("f")){
					Vertex2i v1 = new Vertex2i(Integer.parseInt(s[1].split("/")[0]),Integer.parseInt(s[1].split("/")[1]));
					Vertex2i v2 = new Vertex2i(Integer.parseInt(s[2].split("/")[0]),Integer.parseInt(s[2].split("/")[1]));
					Vertex2i v3 = new Vertex2i(Integer.parseInt(s[3].split("/")[0]),Integer.parseInt(s[3].split("/")[1]));
					model.t.add(new Triangle(v1, v2, v3));
				}
			}
			
			br.close();
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("model loaded with " + model.t.size() + " triangles");
		return model;
	}

}
