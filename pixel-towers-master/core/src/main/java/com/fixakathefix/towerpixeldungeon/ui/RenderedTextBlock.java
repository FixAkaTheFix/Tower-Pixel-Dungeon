/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.fixakathefix.towerpixeldungeon.ui;

import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class RenderedTextBlock extends Component {

	private int maxWidth = Integer.MAX_VALUE;
	public int nLines;

	private static final RenderedText SPACE = new RenderedText();
	private static final RenderedText NEWLINE = new RenderedText();
	
	protected String text;
	protected String[] tokens = null;
	protected ArrayList<RenderedText> words = new ArrayList<>();
	protected boolean multiline = false;

	private int size;
	private float zoom;
	private int color = -1;
	public float[] padding = new float[2];
	
	private int hightlightColor = Window.TITLE_COLOR;
	private int brightredHightlightColor = 0xFF0000;
	private int redHightlightColor = 0xAA0000;
	private int orangeHightlightColor = 0xFF5500;
	private int greenHightlightColor = 0x00AA00;

	private int lightblueHightlightColor = 0x99EEFF;
	private int blueHightlightColor = 0x5555FF;
	private int purpleHightlightColor = 0xBB00BB;

	private int greyHightlightColor = 0xAAAAAA;
	private int necroticgreenHightlightColor = 0x00dd11;
	private int holyHightlightColor = 0xFFFFAA;
	private boolean highlightingEnabled = true;

	public static final int LEFT_ALIGN = 1;
	public static final int CENTER_ALIGN = 2;
	public static final int RIGHT_ALIGN = 3;
	private int alignment = LEFT_ALIGN;
	
	public RenderedTextBlock(int size){
		this.size = size;
	}

	public RenderedTextBlock(String text, int size){
		this.size = size;
		text(text);
	}

	public void text(String text){
		this.text = text;

		if (text != null && !text.equals("")) {


			/*
			String[] allLetters = new String[text.length()];
			char[] allLetChars = text.toCharArray();

			for (int i = 0; i < text.length();i++){
				allLetters[i] = String.valueOf(allLetChars[i]);
			}
			tokens = allLetters;
			*/


			tokens = Game.platform.splitforTextBlock(text, multiline);
			
			build();
		}
	}

	//for manual text block splitting, a space between each word is assumed
	public void tokens(String... words){
		StringBuilder fullText = new StringBuilder();
		for (String word : words) {
			fullText.append(word);
		}
		text = fullText.toString();

		tokens = words;
		build();
	}

	public void text(String text, int maxWidth){
		this.maxWidth = maxWidth;
		multiline = true;
		text(text);
	}

	public String text(){
		return text;
	}

	public void maxWidth(int maxWidth){
		if (this.maxWidth != maxWidth){
			this.maxWidth = maxWidth;
			multiline = true;
			text(text);
		}
	}

	public int maxWidth(){
		return maxWidth;
	}

	private synchronized void build(){
		if (tokens == null) return;
		
		clear();
		words = new ArrayList<>();
		boolean highlighting = false;
		boolean brightredhighlighting = false;
		boolean redhighlighting = false;
		boolean orangehighlighting = false;
		boolean greenhighlighting = false;
		boolean lightbluehighlighting = false;
		boolean bluehighlighting = false;
		boolean purplehighlighting = false;
		boolean greyhighlighting = false;
		boolean necrohighlighting = false;
		boolean preparecustomhighlighting = false;
		for (String str : tokens){

			if (str.equals("#")) {
				preparecustomhighlighting = true;
			} else if (preparecustomhighlighting){
				if      (str.equals("0")) brightredhighlighting = !brightredhighlighting;
				else if (str.equals("1")) redhighlighting = !redhighlighting;
				else if (str.equals("2")) orangehighlighting = !orangehighlighting;
				else if (str.equals("3")) highlighting = !highlighting;
				else if (str.equals("4")) greenhighlighting = !greenhighlighting;
				else if (str.equals("5")) lightbluehighlighting = !lightbluehighlighting;
				else if (str.equals("6")) bluehighlighting   = !bluehighlighting;
				else if (str.equals("7")) purplehighlighting = !purplehighlighting;
				else if (str.equals("8")) greyhighlighting = !greyhighlighting;
				else if (str.equals("9")) necrohighlighting = !necrohighlighting;
				preparecustomhighlighting = false;
			} else if (str.equals("_")){
				highlighting = !highlighting;
			} else if (str.equals("}")) {
				 purplehighlighting = !purplehighlighting;
			} else if (str.equals("]")) {
				 greenhighlighting = !greenhighlighting;
			} else if (str.equals("`")) {
				 lightbluehighlighting = !lightbluehighlighting;
			}   else if (str.equals("\n")){
				words.add(NEWLINE);
			} else if (str.equals(" ")){
				words.add(SPACE);
			} else {
				RenderedText word = new RenderedText(str, size);
				
				if (highlighting) word.hardlight(hightlightColor);

				else if (brightredhighlighting) word.hardlight(brightredHightlightColor);
				else if (redhighlighting) word.hardlight(redHightlightColor);
				else if (orangehighlighting) word.hardlight(orangeHightlightColor);
				else if (greenhighlighting) word.hardlight(greenHightlightColor);
				else if (lightbluehighlighting) word.hardlight(lightblueHightlightColor);
				else if (bluehighlighting) word.hardlight(blueHightlightColor);
				else if (purplehighlighting) word.hardlight(purpleHightlightColor);
				else if (greyhighlighting) word.hardlight(greyHightlightColor);
				else if (necrohighlighting) word.hardlight(necroticgreenHightlightColor);

				else if (color != -1) word.hardlight(color);
				word.scale.set(zoom);
				
				words.add(word);
				add(word);
				
				if (height < word.height()) height = word.height();
			}
		}
		layout();
	}

	public synchronized void zoom(float zoom){
		this.zoom = zoom;
		for (RenderedText word : words) {
			if (word != null) word.scale.set(zoom);
		}
		layout();
	}

	public synchronized void hardlight(int color){
		this.color = color;
		for (RenderedText word : words) {
			if (word != null) word.hardlight( color );
		}
	}
	
	public synchronized void resetColor(){
		this.color = -1;
		for (RenderedText word : words) {
			if (word != null) word.resetColor();
		}
	}
	
	public synchronized void alpha(float value){
		for (RenderedText word : words) {
			if (word != null) word.alpha( value );
		}
	}
	
	public synchronized void setHightlighting(boolean enabled){
		setHightlighting(enabled, Window.TITLE_COLOR);
	}

	
	public synchronized void setHightlighting(boolean enabled, int color){
		if (enabled != highlightingEnabled || color != hightlightColor) {
			hightlightColor = color;
			highlightingEnabled = enabled;
			build();
		}
	}

	public synchronized void invert(){
		if (words != null) {
			for (RenderedText word : words) {
				if (word != null) {
					word.ra = 0.77f;
					word.ga = 0.73f;
					word.ba = 0.62f;
					word.rm = -0.77f;
					word.gm = -0.73f;
					word.bm = -0.62f;
				}
			}
		}
	}

	public synchronized void align(int align){
		alignment = align;
		layout();
	}

	@Override
	protected synchronized void layout() {
		super.layout();
		float x = this.x;
		float y = this.y;
		float height = 0;
		nLines = 1;

		ArrayList<ArrayList<RenderedText>> lines = new ArrayList<>();
		ArrayList<RenderedText> curLine = new ArrayList<>();
		lines.add(curLine);

		width = 0;
		for (int i = 0; i < words.size(); i++){
			RenderedText word = words.get(i);
			if (word == SPACE){
				x += 1.667f;
			} else if (word == NEWLINE) {
				//newline
				y += height+2f;
				x = this.x;
				nLines++;
				curLine = new ArrayList<>();
				lines.add(curLine);
			} else {
				if (word.height() > height) height = word.height();

				float fullWidth = word.width();
				int j = i+1;

				//this is so that words split only by highlighting are still grouped in layout
				//Chinese/Japanese always render every character separately without spaces however
				/*while (Messages.lang() != Languages.CHINESE && Messages.lang() != Languages.JAPANESE
						&& j < words.size() && words.get(j) != SPACE && words.get(j) != NEWLINE){
					fullWidth += words.get(j).width() - 0.667f;
					j++;
				}*/

				if ((x - this.x) + fullWidth - 0.001f > maxWidth && !curLine.isEmpty()){
					y += height+2f;
					x = this.x;
					nLines++;
					curLine = new ArrayList<>();
					lines.add(curLine);
				}

				word.x = x;
				word.y = y;
				PixelScene.align(word);
				x += word.width();
				curLine.add(word);

				if ((x - this.x) > width) width = (x - this.x);
				
				//Note that spacing currently doesn't factor in halfwidth and fullwidth characters
				//(e.g. Ideographic full stop)
				x -= 0.667f;

			}
		}
		this.height = (y - this.y) + height;

		if (alignment != LEFT_ALIGN){
			for (ArrayList<RenderedText> line : lines){
				if (line.size() == 0) continue;
				float lineWidth = line.get(line.size()-1).width() + line.get(line.size()-1).x - this.x;
				if (alignment == CENTER_ALIGN){
					for (RenderedText text : line){
						text.x += (width() - lineWidth)/2f;
						PixelScene.align(text);
					}
				} else if (alignment == RIGHT_ALIGN) {
					for (RenderedText text : line){
						text.x += width() - lineWidth;
						PixelScene.align(text);
					}
				}
			}
		}
	}
}
