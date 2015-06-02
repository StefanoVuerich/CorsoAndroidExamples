package com.example.mylistviewlayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class EffectsDeck extends Fragment{
	
	public static List<Effect> effects = new ArrayList<Effect>();
	private boolean loaded = false;
	public static SoundPool pool;
	private static AudioManager audioManager;
	public final static String LOG_TEXT = "TestingApp";
	private int lastPlayed;
	private String lastUsedEffect = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.effects_deck_layout, container,false);
		
		//INIZIO
		
		pool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		audioManager = (AudioManager) getActivity().getSystemService(MainActivity.AUDIO_SERVICE);

		populateEffectList();
		populateListView(rootView);
		//registerClick();
		return rootView;
	}

	private void populateEffectList() {

		effects.add(new Effect(R.drawable.play, "Pull It Up", "Jingles",
				R.raw.pullitup));
		effects.add(new Effect(R.drawable.play, "More Fire", "Jingles",
				R.raw.morefire));
		effects.add(new Effect(R.drawable.play, "Radar Siren", "Sirens",
				R.raw.airraid));
		effects.add(new Effect(R.drawable.play, "Disco Laser", "Lasers",
				R.raw.discolaser));

		for (Effect effect : effects) {
			int soundID = pool.load(getActivity(), effect.getSoundId(), 0);
			effect.setSoundId(soundID);
			effect.setLoaded(true);
			pool.setOnLoadCompleteListener(new MyLoadListener());
			
		}
		
		
		/*for (int i = 0; i < effects.size(); i++) {
			int soundStreamID = pool.load(getActivity(), effects.get(i)
					.getSoundId(), 0);
			effects.get(i).setSoundStreamId(soundStreamID);
			effects.get(i).setLoaded(true);
			pool.setOnLoadCompleteListener(new MyLoadListener());
		}*/
	}

	private void populateListView(View rootView) {
		ArrayAdapter<Effect> adapter = new MyListAdapter();
		ListView listview = (ListView) rootView.findViewById(R.id.effectList);
		listview.setAdapter(adapter);

	}

	private class MyListAdapter extends ArrayAdapter<Effect> {

		public MyListAdapter() {
			super(getActivity(), R.layout.effect_layout, effects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View effectView = convertView;
			if (effectView == null) {
				effectView = getActivity().getLayoutInflater().inflate(
						R.layout.effect_layout, parent, false);
			}
			final Effect currentEffect = effects.get(position);

			ImageView imageView = (ImageView) effectView
					.findViewById(R.id.effect_image);
			imageView.setImageResource(currentEffect.getImageId());

			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					playEffect();
				}

				private void playEffect() {

					if (currentEffect.isLoaded()) {
						if (lastUsedEffect.equals(currentEffect.getTitolo())) {
							pool.stop(lastPlayed);
							Log.v(LOG_TEXT, "track before stopped "
									+ lastPlayed);
						}
						lastPlayed = pool.play(
								currentEffect.getSoundId(),
								currentEffect.getSoundVolume(),
								currentEffect.getSoundVolume(), 0, 0,
								currentEffect.getSoundRate());

						Log.v(LOG_TEXT, "Played: " + lastPlayed);
						currentEffect.setSoundStreamId(lastPlayed);
						lastUsedEffect = currentEffect.getTitolo();
					}
				}
			});

			TextView titleText = (TextView) effectView
					.findViewById(R.id.effect_title);
			titleText.setText(currentEffect.getTitolo());

			TextView typeText = (TextView) effectView
					.findViewById(R.id.effect_type);
			typeText.setText(currentEffect.getTipoEffetto());

			SeekBar pitchSeekbar = (SeekBar) effectView
					.findViewById(R.id.pitchSeek);
			pitchSeekbar.setMax(100);
			pitchSeekbar.setProgress(60);
			pitchSeekbar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							Log.v(LOG_TEXT, "seek stopped");
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							Log.v(LOG_TEXT, "seek start");
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {

							float soundRate = 0.5f + (progress / 100.0f);
							if (soundRate > 2f)
								soundRate = 2f;
							
							pool.setRate(lastPlayed, soundRate);
							currentEffect.setSoundRate(soundRate);

							Log.v(LOG_TEXT,
									"seek progress "
											+ currentEffect.getSoundRate());
						}
					});

			SeekBar volumeSeekbar = (SeekBar) effectView
					.findViewById(R.id.volumeSeek);
			volumeSeekbar.setMax(110);
			volumeSeekbar.setProgress(50);
			volumeSeekbar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							Log.v(LOG_TEXT, "seek volume stopped");
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							Log.v(LOG_TEXT, "seek volume start");
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							
							float volume = progress / 100.0f;
							pool.setVolume(lastPlayed, volume, volume);
							currentEffect.setSoundVolume(volume);
							
							Log.v(LOG_TEXT, "seek volume progress " + progress);
						}
					});

			return effectView;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void masterGainVolumeListener(int percentuale) {
		int size = EffectsDeck.effects.size();
		for (Effect effect : effects) {
			String z = effect.getTitolo();
			float h = effect.getSoundVolume();
			float updatedVolTrack = (float)(effect.getSoundVolume() * percentuale ) / 100;
			int i = effect.getSoundStreamId();
			//pool.pause(effect.getSoundStreamId());
			EffectsDeck.pool.setVolume(effect.getSoundStreamId(), updatedVolTrack, updatedVolTrack);
		}
		
		Log.v(EffectsDeck.LOG_TEXT, "dalla effect deck " + percentuale);
	}
}
