package com.example.mynativeaudiore;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	static int numChannelsUri = 0;
	static final int CLIP_PLAYBACK = 4;
	static AssetManager assetManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		assetManager = getAssets();
		
		createEngine();
		Log.v("jajaja", "engine created");
		createBufferQueueAudioPlayer();
		Log.v("jajaja", "buffer created");

		Button getChannels = (Button) findViewById(R.id.getChannel);
		getChannels.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("jajaja", "get channels click");
				if (numChannelsUri == 0) {
					numChannelsUri = getNumChannelsUriAudioPlayer();
				}
				Toast.makeText(MainActivity.this,
						"Channels: " + numChannelsUri, Toast.LENGTH_SHORT)
						.show();
			}
		});

		Button record = (Button) findViewById(R.id.record);
		getChannels.setOnClickListener(new View.OnClickListener() {

			boolean created = false;

			public void onClick(View view) {
				if (!created) {
					created = createAudioRecorder();
				}
				if (created) {
					startRecording();
				}
			}
		});
		
		((Button) findViewById(R.id.playback)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // ignore the return value
                selectClip(CLIP_PLAYBACK, 3);
            }
        });
	}

	public static native void createEngine();
	public static native int getNumChannelsUriAudioPlayer();
	public static native void createBufferQueueAudioPlayer();
	public static native boolean createAudioRecorder();
    public static native void startRecording();
    public static native boolean selectClip(int which, int count);


	static {
		System.loadLibrary("native-audio-jni");
	}
}
