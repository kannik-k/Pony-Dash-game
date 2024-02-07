package ee.taltech.pony_dash_for_spikes_salvation;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ee.taltech.pony_dash_for_spikes_salvation.pony_dash_for_spikes_salvation_game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("pony-dash-for-spikes-salvation");
		new Lwjgl3Application(new pony_dash_for_spikes_salvation_game(), config);
	}
}
