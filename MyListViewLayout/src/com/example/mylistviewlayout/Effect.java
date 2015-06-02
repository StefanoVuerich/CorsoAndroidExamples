package com.example.mylistviewlayout;

public class Effect {
	
	private int imageId;
	private String titolo;
	private String tipoEffetto;
	private int soundID;
	private int soundStreamId;
	private float soundRate;
	private boolean isLoaded;
	private float soundVolume;
	
	public Effect(int imageId, String titolo, String tipoEffetto, int soundID) {
		this.imageId = imageId;
		this.titolo = titolo;
		this.tipoEffetto = tipoEffetto;
		this.soundID = soundID;
		this.setSoundRate(1.0f);
		this.setSoundStreamId(0);
		this.setLoaded(false);
		this.soundVolume = 0.5f;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTipoEffetto() {
		return tipoEffetto;
	}
	public void setTipoEffetto(String tipoEffetto) {
		this.tipoEffetto = tipoEffetto;
	}
	public int getSoundId() {
		return soundID;
	}
	public void setSoundId(int soundID) {
		this.soundID = soundID;
	}
	public float getSoundRate() {
		return soundRate;
	}
	public void setSoundRate(float soundRate) {
		this.soundRate = (float)soundRate;
	}
	public boolean isLoaded() {
		return isLoaded;
	}
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
	public int getSoundStreamId() {
		return soundStreamId;
	}
	public void setSoundStreamId(int soundStreamId) {
		this.soundStreamId = soundStreamId;
	}
	public float getSoundVolume() {
		return soundVolume;
	}
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
}
