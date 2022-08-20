package miniplayertest;

import java.net.URL;

public class BasicPlayer {

    public static final int UNKNOWN = 0;
    public static final int OPENED = 1;
    public static final int PLAYING = 2;
    public static final int STOPPED = 3;
    
    int status;
    double gain;
    
    
	public BasicPlayer() {
		System.out.println("MBP - Creating BasicPlayer object with status UNKNOWN");
	}
	
	
	public void open(URL url) {
		status = OPENED;
		System.out.println("Opening URL " + url);
	}
	public int getStatus() {
		System.out.println("MBP - Getting status - status is " + status);
		return status;
	}

	public void play() throws BasicPlayerException {
		if(status == PLAYING)
			stop();
		
		status = PLAYING;
		System.out.println("Playing...");
	}
	
	public void stop() throws BasicPlayerException {
		status = STOPPED;
		System.out.println("Stopping play");
	}
	public void setGain(double d) {
		
		gain = d;
		System.out.println("Setting gain to " + gain);
	}

	public void resume() throws BasicPlayerException {
		
		System.out.println("Resuming playback");
	}


}
