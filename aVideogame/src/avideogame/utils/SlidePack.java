package avideogame.utils;

import android.graphics.Color;

public class SlidePack {
	public String text;
	public int background;
	public int textcolor;
	public int slideanimid;
	public int sound;
	public int bgmusic;
	
	public SlidePack(String text, int background, int textcolor,int slide,int snd,int bgmus) {
		super();
		this.text = text;
		this.background = background;
		this.textcolor = textcolor;
		this.slideanimid = slide;
		this.sound = snd;
		this.bgmusic = bgmus;
	}
}
