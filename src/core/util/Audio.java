package core.util;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOgg;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class Audio {
	
	public static boolean aLCompatible;
	public static boolean jSCompatible;
	
	public static SoundSystem soundsystem;
	
	public static float rollOff = SoundSystemConfig.getDefaultRolloff() / 4;
	
	public Audio(){
		try {
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class); // LWJGL OpenAL [prioritise]
			SoundSystemConfig.addLibrary(LibraryJavaSound.class ); // Java Sound [backup]
			SoundSystemConfig.setCodec( "wav", CodecWav.class ); // .wav CODEC
			SoundSystemConfig.setCodec( "ogg", CodecJOgg.class ); // .ogg CODEC
			
			//SoundSystemConfig.setSoundFilesPackage("Resources/Sounds/");

			aLCompatible = SoundSystem.libraryCompatible( LibraryLWJGLOpenAL.class );
			jSCompatible = SoundSystem.libraryCompatible( LibraryJavaSound.class );
			
			if( aLCompatible )
			    soundsystem = new SoundSystem(LibraryLWJGLOpenAL.class);   // OpenAL
			else if( jSCompatible )
				soundsystem = new SoundSystem(LibraryJavaSound.class);  // Java Sound
			else
				soundsystem = new SoundSystem(Library.class);  // "No Sound, Silent Mode"
			

		}
		catch( SoundSystemException e )
		{
		    e.printStackTrace();
		}
		
		//soundsystem.backgroundMusic("Music", "audio.wav", true);
	}
	
	public static void loadSound(String filename){
		soundsystem.loadSound(filename);
		System.out.println("audio '" + filename + "' loaded");
	}
	
	public static String playSoundEffect(String filename, float volume, float x, float y, float z){
		boolean priority = false;
		boolean loop = false;
		int aModel = SoundSystemConfig.ATTENUATION_ROLLOFF;
		String tempSource = soundsystem.quickPlay( priority, filename, loop, x, y, z, aModel, rollOff);
		soundsystem.setVolume(tempSource, volume);
		return tempSource;
	}
	
	public static String playSoundEffect(String filename, float x, float y, float z){
		return playSoundEffect(filename, 1, x, y, z);
	}

}
