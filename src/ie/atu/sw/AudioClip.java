package ie.atu.sw;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/**
 * This class allows you to play audio files in the game, such as *.wav files.
 * <br>
 * Example:<br>
 * <br>
 * AudioClip audioClip = AudioClip.loadAudioClip("./resources/sounds/victory.wav");<br>
 * audioClip.play();<br>
 * 
 * @author Declan Kelly (g00378925@atu.ie)
 *
 */
public class AudioClip {
	private Clip audioClip; // Internal Clip object
	
	/** This method is used to help reset the Clip object */
	private void resetAudioClip() {
		// Flush buffer and reset frame position
		this.audioClip.flush();
		this.audioClip.setFramePosition(0);
	}
	
	/**
	 * This is used as a callback method,
	 * it will reset the audioClip when it stops playing,
	 * so we can play it again.
	 * 
	 * @param event Current {@code LineEvent} object.
	 */
	private void lineListener(LineEvent event) {
		var eventType = event.getType();
		if (eventType == LineEvent.Type.STOP) {
			// When stopped playing, reset the clip.
			this.resetAudioClip();
		}
	}
	
	/**
	 * You can't instantiate this object directly,
	 * you should use the loadAudioClip factory method instead.
	 * 
	 * @param ais This contains the input stream for an audio file.
	 */
	private AudioClip(AudioInputStream ais) {
		try {
			// Set internal audioClip attribute
			this.audioClip = AudioSystem.getClip();
			this.audioClip.open(ais);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf("Problem opening clip %s\n", ais.toString());
		}

	}
	
	/**
	 * This factory method returns an instance of the {@link AudioClip} object
	 * and loads the audio file from the string parameter audioClipLocation.
	 * 
	 * @param audioClipLocation This is the string location of the audio file.
	 * @return Returns a {@link AudioClip} object containing the loaded sound file.
	 */
	public static AudioClip loadAudioClip(String audioClipLocation) {
		File audioFile = new File(audioClipLocation);
		try {
			// This factory method will return an instance of AudioClip
			return new AudioClip(AudioSystem.getAudioInputStream(audioFile));
		} catch (IOException e) {
			System.err.printf("Failed to open: %s\n", audioClipLocation);
		} catch (UnsupportedAudioFileException e) {
			System.err.printf("The format that %s is, is not supported\n", audioClipLocation);
		}
		return null;
	}
	
	/**
	 * Calling play will start playing the sound.
	 */
	public void play() {
		// Set the lineListener callback, it will reset the line
		// when done playing.
		this.audioClip.addLineListener(this::lineListener);
		this.audioClip.start();
	}
}
