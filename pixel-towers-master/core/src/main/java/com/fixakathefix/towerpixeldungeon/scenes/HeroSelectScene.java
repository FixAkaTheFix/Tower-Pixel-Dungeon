package com.fixakathefix.towerpixeldungeon.scenes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.scenes.InterlevelScene;
import com.fixakathefix.towerpixeldungeon.scenes.LevelSelectScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.scenes.TitleScene;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.journal.Journal;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.Archs;
import com.fixakathefix.towerpixeldungeon.ui.ExitButton;
import com.fixakathefix.towerpixeldungeon.ui.IconButton;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.StyledButton;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.fixakathefix.towerpixeldungeon.utils.DungeonSeed;
import com.fixakathefix.towerpixeldungeon.windows.WndChallenges;
import com.fixakathefix.towerpixeldungeon.windows.WndHeroInfo;
import com.fixakathefix.towerpixeldungeon.windows.WndKeyBindings;
import com.fixakathefix.towerpixeldungeon.windows.WndMessage;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;
import com.fixakathefix.towerpixeldungeon.windows.WndTextInput;
import com.watabou.gltextures.TextureCache;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.PointerArea;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;
import com.watabou.utils.RectF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HeroSelectScene extends PixelScene {

	private NinePatch backgroundPane;
	private Image background;

	private Archs archs;
	private IconButton btnFade; //only on landscape

	//fading UI elements
	private RenderedTextBlock title;
	private ArrayList<StyledButton> heroBtns = new ArrayList<>();
	private RenderedTextBlock heroName;
	private RenderedTextBlock heroDesc;
	private StyledButton startBtn;
	private IconButton infoButton;
	private IconButton btnOptions;
	private GameOptions optionsPane;
	private IconButton btnExit;

	@Override
	public void create() {
		super.create();

		Dungeon.hero = null;

		Badges.loadGlobal();
		Journal.loadGlobal();

		background = new Image(Assets.Sprites.AVATARS){
			@Override
			public void update() {
				if (GamesInProgress.selectedClass != null) {
					if (rm > 1f) {
						rm -= Game.elapsed;
						gm = bm = rm;
					} else {
						rm = gm = bm = 1;
					}
				}
			}
		};
		background.scale.set(Camera.main.height/background.height);
		background.tint(0x2d2f31, 1f);

		background.x = (Camera.main.width - background.width())/2f;
		background.y = (Camera.main.height - background.height())/2f;
		PixelScene.align(background);
		add(background);
		background.frame(0, 0, 24, 32);


		title = PixelScene.renderTextBlock(Messages.get(this, "title"), 12);
		title.hardlight(Window.TITLE_COLOR);
		PixelScene.align(title);
		add(title);

		startBtn = new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
			@Override
			protected void onClick() {
				super.onClick();

				if (GamesInProgress.selectedClass == null) return;

				Game.switchScene( GamemodeSelectionScene.class);
			}
		};
		startBtn.icon(Icons.get(Icons.ENTER));
		startBtn.setSize(80, 21);
		startBtn.textColor(Window.TITLE_COLOR);
		add(startBtn);
		startBtn.visible = startBtn.active = false;

		infoButton = new IconButton(Icons.get(Icons.INFO)){
			@Override
			protected void onClick() {
				super.onClick();
				Window w = new WndHeroInfo(GamesInProgress.selectedClass);
				if (landscape()){
					w.offset(Camera.main.width/6, 0);
				}
				ShatteredPixelDungeon.scene().addToFront(w);
			}

			@Override
			protected String hoverText() {
				return Messages.titleCase(Messages.get(WndKeyBindings.class, "hero_info"));
			}
		};
		infoButton.visible = infoButton.active = false;
		infoButton.setSize(20, 21);
		add(infoButton);

		for (HeroClass cl : HeroClass.values()){
			if (cl != HeroClass.FIX || DeviceCompat.isDebug()) {
				HeroBtn button = new HeroBtn(cl);
				add(button);
				heroBtns.add(button);
			}
		}

		optionsPane = new GameOptions();
		optionsPane.visible = optionsPane.active = false;
		optionsPane.layout();
		add(optionsPane);

		btnOptions = new IconButton(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF)){
			@Override
			protected void onClick() {
				super.onClick();
				ShatteredPixelDungeon.scene().addToFront(new WndChallenges(SPDSettings.challenges(), true) {
					public void onBackPressed() {
						super.onBackPressed();
						icon(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
						updateOptionsColor();
					}
				} );
				//optionsPane.visible = !optionsPane.visible; TODO this may be necessary in the future
				//optionsPane.active = !optionsPane.active;
			}

			@Override
			protected void onPointerDown() {
				super.onPointerDown();
			}

			@Override
			protected void onPointerUp() {
				updateOptionsColor();
			}

			@Override
			protected String hoverText() {
				return Messages.get(HeroSelectScene.class, "options");
			}
		};
		updateOptionsColor();
		btnOptions.visible = false;

		if (false){ // Badges.isUnlocked(Badges.Badge.VICTORY)){
			add(btnOptions);
		} else {
			Dungeon.challenges = 0;
			SPDSettings.challenges(0);
			SPDSettings.customSeed("");
		}
		heroName = renderTextBlock(9);
		heroName.setPos(0, heroBtns.get(heroBtns.size()-1).bottom()+5);
		add(heroName);

		heroDesc = renderTextBlock(6);

		heroDesc.align(RenderedTextBlock.CENTER_ALIGN);
		heroDesc.setPos(0, heroName.bottom()+5);
		add(heroDesc);

		if (landscape()){
			float leftArea = Math.max(100, Camera.main.width/3f);
			float uiHeight = Math.min(Camera.main.height-20, 300);
			float uiSpacing = (uiHeight-120)/2f;

			if (uiHeight >= 160) uiSpacing -= 5;
			if (uiHeight >= 180) uiSpacing -= 6;

			background.x += leftArea/6f;

			title.setPos( (leftArea - title.width())/2f, (Camera.main.height-uiHeight)/2f);
			align(title);

			int btnWidth = HeroBtn.MIN_WIDTH;
			int btnHeight = HeroBtn.HEIGHT;
			if (uiHeight >= 180){
				btnHeight += 6;
			}

			int cols = (int)Math.ceil(heroBtns.size()/2f);
			float curX = (leftArea - btnWidth * cols + (cols-1))/2f;
			float curY = title.bottom() + uiSpacing;

			int count = 0;
			for (StyledButton button : heroBtns){
				button.setRect(curX, curY, btnWidth, btnHeight);
				align(button);
				curX += btnWidth+1;
				count++;
				if (count >= (1+heroBtns.size())/2){
					curX -= btnWidth*count + count;
					curY += btnHeight+1;
					if (heroBtns.size()%2 != 0){
						curX += btnWidth/2f;
					}
					count = 0;
				}
			}

			heroName = renderTextBlock(9);
			heroName.setPos(0, heroBtns.get(heroBtns.size()-1).bottom()+5);
			add(heroName);

			if (uiHeight >= 160){
				heroDesc = renderTextBlock(6);
			} else {
				heroDesc = renderTextBlock(5);
			}
			heroDesc.align(RenderedTextBlock.CENTER_ALIGN);
			heroDesc.setPos(0, heroName.bottom()+5);
			add(heroDesc);

			startBtn.text(Messages.titleCase(Messages.get(this, "start")));
			startBtn.setSize(startBtn.reqWidth()+8, 21);
			startBtn.setPos((leftArea - startBtn.width())/2f, title.top() + uiHeight - startBtn.height());
			align(startBtn);

			btnFade = new IconButton(Icons.COMPASS.get()){
				@Override
				protected void onClick() {
					enable(false);
					parent.add(new Tweener(parent, 0.5f) {
						@Override
						protected void updateValues(float progress) {
							uiAlpha = 1 - progress;
							updateFade();
						}
					});
				}
			};
			btnFade.icon().originToCenter();
			btnFade.icon().angle = 270f;
			btnFade.visible = btnFade.active = false;
			btnFade.setRect(startBtn.left()-20, startBtn.top(), 20, 21);
			align(btnFade);
			add(btnFade);

			btnOptions.setRect(startBtn.right(), startBtn.top(), 20, 21);
			optionsPane.setPos(btnOptions.right(), btnOptions.top() - optionsPane.height() - 2);
			align(optionsPane);
		} else {



			background.scale.set(0.45f * Camera.main.height/background.height);
			background.visible = false;
			background.x = (Camera.main.width - background.width())/2;
			background.y = (Camera.main.height - background.height())/2;
			align(background);

			int halfindex = heroBtns.size()/2;
			//for all buttons to be equal, we need some space
			int btnWidth = Camera.main.width/(halfindex + 1);
			float curX = (Camera.main.width - halfindex*btnWidth)*0.5f;
			float curY = Camera.main.height - HeroBtn.HEIGHT*2;
			for (StyledButton button : heroBtns) {
				if (curX + btnWidth > Camera.main.width){
					curY+=HeroBtn.HEIGHT;
					curX = (Camera.main.width - (heroBtns.size() - heroBtns.indexOf(button))*btnWidth)*0.5f;
				}
				button.setRect(curX, curY, btnWidth, HeroBtn.HEIGHT);
				curX += btnWidth;
			}

			title.setPos((Camera.main.width - title.width()) / 2f, (Camera.main.height - HeroBtn.HEIGHT * 2 - title.height() - 4));

			btnOptions.setRect(heroBtns.get(0).left() + 16, Camera.main.height-HeroBtn.HEIGHT-16, 20, 21);
			optionsPane.setPos(heroBtns.get(0).left(), 0);
		}


		btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		btnExit.visible = btnExit.active = !SPDSettings.intro();

		PointerArea fadeResetter = new PointerArea(0, 0, Camera.main.width, Camera.main.height){
			@Override
			public boolean onSignal(PointerEvent event) {
				if (event != null && event.type == PointerEvent.Type.UP){
					if (uiAlpha == 0 && landscape()){
						parent.add(new Tweener(parent, 0.5f) {
							@Override
							protected void updateValues(float progress) {
								uiAlpha = progress;
								updateFade();
							}

							@Override
							protected void onComplete() {
								resetFade();
							}
						});
					} else {
						resetFade();
					}
				}
				return false;
			}
		};
		add(fadeResetter);
		resetFade();


		archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );

		if (GamesInProgress.selectedClass != null){
			setSelectedHero(GamesInProgress.selectedClass);
		} else archs.tint(0xFF666666, 0xFF222222);

		addToBack( archs );
		fadeIn();

	}

	private void updateOptionsColor(){
		if (!SPDSettings.customSeed().isEmpty()){
			btnOptions.icon().hardlight(1f, 1.5f, 0.67f);
		} else if (SPDSettings.challenges() != 0){
			btnOptions.icon().hardlight(2f, 1.33f, 0.5f);
		} else {
			btnOptions.icon().resetColor();
		}
	}

	private void setSelectedHero(HeroClass cl){
		GamesInProgress.selectedClass = cl;

		background.frame(cl.ordinal() * 24, 0, 24, 32);
		background.visible = true;
		background.hardlight(1.5f,1.5f,1.5f);

		archs.tint(cl.getBrightColor(), cl.getDarkColor());

		float leftPortion = Math.max(100, Camera.main.width/3f);

		if (landscape()) {

			heroName.text(Messages.titleCase(cl.title()));
			heroName.setPos((leftPortion - heroName.width() - 20)/2f, heroName.top());
			align(heroName);

			heroDesc.text(cl.shortDesc());
			heroDesc.maxWidth(80);
			heroDesc.setPos((leftPortion - heroDesc.width())/2f, heroName.bottom() + 5);
			align(heroDesc);

			btnFade.visible = btnFade.active = true;

			startBtn.visible = startBtn.active = true;

			infoButton.visible = infoButton.active = true;
			infoButton.setPos(heroName.right(), heroName.top() + (heroName.height() - infoButton.height())/2f);
			align(infoButton);

			btnOptions.visible = btnOptions.active =true; //!SPDSettings.intro();

		} else {

			heroName.text(Messages.titleCase(cl.title()));
			heroDesc.text(cl.shortDesc());
			heroDesc.maxWidth((int)background.width());

			heroName.setPos((Camera.main.width - heroName.width())/2, background.y/2 - (heroName.height()+heroDesc.height()+5)/2);
			align(heroName);
			//add(heroName);

			heroDesc.setPos((Camera.main.width - heroDesc.width())/2, heroName.bottom() + 5);
			align(heroDesc);
			//add(heroDesc);

			title.visible = false;

			startBtn.visible = startBtn.active = true;
			startBtn.text(Messages.titleCase(cl.title()));
			startBtn.setSize(startBtn.reqWidth() + 8, 21);

			startBtn.setPos((Camera.main.width - startBtn.width())/2f, (Camera.main.height - HeroBtn.HEIGHT*2 + 2 - startBtn.height()));
			PixelScene.align(startBtn);

			infoButton.visible = infoButton.active = true;
			infoButton.setPos(startBtn.right(), startBtn.top());

			btnOptions.visible = btnOptions.active = true;//!SPDSettings.intro();
			btnOptions.setPos(startBtn.left()-btnOptions.width(), startBtn.top());

			optionsPane.setPos(heroBtns.get(0).left(), startBtn.top() - optionsPane.height() - 2);
			align(optionsPane);
		}

		updateOptionsColor();
	}

	private float uiAlpha;

	@Override
	public void update() {
		super.update();
		btnExit.visible = btnExit.active = !SPDSettings.intro();
		//do not fade when a window is open
		for (Object v : members){
			if (v instanceof Window) resetFade();
		}
		if (!PixelScene.landscape() && GamesInProgress.selectedClass != null) {
			if (uiAlpha > 0f){
				uiAlpha -= Game.elapsed/4f;
			}
			updateFade();
		}
	}

	private void updateFade(){
		float alpha = GameMath.gate(0f, uiAlpha, 1f);
		title.alpha(alpha);
		for (StyledButton b : heroBtns){
			b.enable(alpha != 0);
			b.alpha(alpha);
		}
		if (heroName != null) {
			heroName.alpha(alpha);
		}
		if (heroDesc !=null){
			heroDesc.alpha(alpha);
		}
		if (btnFade !=null){
			btnFade.enable(alpha != 0);
			btnFade.icon().alpha(alpha);
		}
		startBtn.enable(alpha != 0);
		startBtn.alpha(alpha);
		btnExit.enable(alpha != 0);
		btnExit.icon().alpha(alpha);
		optionsPane.active = optionsPane.visible && alpha != 0;
		optionsPane.alpha(alpha);
		btnOptions.enable(alpha != 0);
		btnOptions.icon().alpha(alpha);
		infoButton.enable(alpha != 0);
		infoButton.icon().alpha(alpha);

		if (landscape()){

			background.x = (Camera.main.width - background.width())/2f;

			float leftPortion = Math.max(100, Camera.main.width/3f);

			background.x += (leftPortion/2f)*alpha;

		}
	}

	private void resetFade(){
		//starts fading after 4 seconds, fades over 4 seconds.
		uiAlpha = 2f;
		updateFade();
	}

	@Override
	protected void onBackPressed() {
		if (btnExit.active){
			ShatteredPixelDungeon.switchScene(TitleScene.class);
		} else {
			super.onBackPressed();
		}
	}

	private class HeroBtn extends StyledButton {

		private HeroClass cl;

		private static final int MIN_WIDTH = 20;
		private static final int HEIGHT = 24;

		HeroBtn ( HeroClass cl ){
			super(Chrome.Type.GREY_BUTTON_TR, "");

			this.cl = cl;

			icon(new Image(cl.spritesheet(), 0, 90, 12, 15));

		}

		@Override
		public void update() {
			super.update();
			if (cl != GamesInProgress.selectedClass){
				if (!cl.isUnlocked()){
					icon.brightness(0.1f);
				} else {
					icon.brightness(0.6f);
				}
			} else {
				icon.brightness(1f);
			}
		}

		@Override
		protected void onClick() {
			super.onClick();

			if( !cl.isUnlocked() ){
				ShatteredPixelDungeon.scene().addToFront( new WndMessage(cl.unlockMsg()));
			} else if (GamesInProgress.selectedClass == cl) {
				Window w = new WndHeroInfo(cl);
				if (landscape()){
					w.offset(Camera.main.width/6, 0);
				}
				ShatteredPixelDungeon.scene().addToFront(w);
			} else {
				setSelectedHero(cl);
			}
		}
	}

	private class GameOptions extends Component {

		private NinePatch bg;

		private ArrayList<StyledButton> buttons;
		private ArrayList<ColorBlock> spacers;

		@Override
		protected void createChildren() {

			bg = Chrome.get(Chrome.Type.GREY_BUTTON_TR);
			add(bg);

			buttons = new ArrayList<>();
			spacers = new ArrayList<>();
			if (DeviceCompat.isDebug() || Badges.isUnlocked(Badges.Badge.VICTORY)){
				StyledButton seedButton = new StyledButton(Chrome.Type.BLANK, Messages.get(HeroSelectScene.class, "custom_seed"), 6){
					@Override
					protected void onClick() {
						String existingSeedtext = SPDSettings.customSeed();
						ShatteredPixelDungeon.scene().addToFront( new WndTextInput(Messages.get(HeroSelectScene.class, "custom_seed_title"),
								Messages.get(HeroSelectScene.class, "custom_seed_desc"),
								existingSeedtext,
								20,
								false,
								Messages.get(HeroSelectScene.class, "custom_seed_set"),
								Messages.get(HeroSelectScene.class, "custom_seed_clear")){
							@Override
							public void onSelect(boolean positive, String text) {
								text = DungeonSeed.formatText(text);
								long seed = DungeonSeed.convertFromText(text);

								if (positive && seed != -1){

									for (GamesInProgress.Info info : GamesInProgress.checkAll()){
										if (info.customSeed.isEmpty() && info.seed == seed){
											SPDSettings.customSeed("");
											icon.resetColor();
											ShatteredPixelDungeon.scene().addToFront(new WndMessage(Messages.get(HeroSelectScene.class, "custom_seed_duplicate")));
											return;
										}
									}

									SPDSettings.customSeed(text);
									icon.hardlight(1f, 1.5f, 0.67f);
								} else {
									SPDSettings.customSeed("");
									icon.resetColor();
								}
								updateOptionsColor();
							}
						});
					}
				};
				seedButton.leftJustify = true;
				seedButton.icon(Icons.get(Icons.SEED));
				if (!SPDSettings.customSeed().isEmpty()) seedButton.icon().hardlight(1f, 1.5f, 0.67f);;
				//buttons.add(seedButton);
				//add(seedButton);

				StyledButton dailyButton = new StyledButton(Chrome.Type.BLANK, Messages.get(HeroSelectScene.class, "daily"), 6){

					private static final long SECOND = 1000;
					private static final long MINUTE = 60 * SECOND;
					private static final long HOUR = 60 * MINUTE;
					private static final long DAY = 24 * HOUR;

					@Override
					protected void onClick() {
						super.onClick();

						long diff = (SPDSettings.lastDaily() + DAY) - Game.realTime;
						if (diff > 24*HOUR){
							ShatteredPixelDungeon.scene().addToFront(new WndMessage(Messages.get(HeroSelectScene.class, "daily_unavailable_long", (diff / DAY)+1)));
							return;
						}

						for (GamesInProgress.Info game : GamesInProgress.checkAll()){
							if (game.daily){
								ShatteredPixelDungeon.scene().addToFront(new WndMessage(Messages.get(HeroSelectScene.class, "daily_existing")));
								return;
							}
						}

						Image icon = Icons.get(Icons.CALENDAR);
						if (diff <= 0)  icon.hardlight(0.5f, 1f, 2f);
						else            icon.hardlight(1f, 0.5f, 2f);
						ShatteredPixelDungeon.scene().addToFront(new WndOptions(
								icon,
								Messages.get(HeroSelectScene.class, "daily"),
								diff > 0 ?
										Messages.get(HeroSelectScene.class, "daily_repeat") :
										Messages.get(HeroSelectScene.class, "daily_desc"),
								Messages.get(HeroSelectScene.class, "daily_yes"),
								Messages.get(HeroSelectScene.class, "daily_no")){
							@Override
							protected void onSelect(int index) {
								if (index == 0){
									if (diff <= 0) {
										long time = Game.realTime - (Game.realTime % DAY);

										//earliest possible daily for v1.4.0 is Sept 10 2022
										//which is 19,245 days after Jan 1 1970
										time = Math.max(time, 19_245 * DAY);

										SPDSettings.lastDaily(time);
										Dungeon.dailyReplay = false;
									} else {
										Dungeon.dailyReplay = true;
									}

									Dungeon.hero = null;
									Dungeon.daily = true;
									InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

									Game.switchScene( InterlevelScene.class );
								}
							}
						});
					}

					private long timeToUpdate = 0;

					private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ROOT);
					{
						dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					}

					@Override
					public void update() {
						super.update();

						if (Game.realTime > timeToUpdate && visible){
							long diff = (SPDSettings.lastDaily() + DAY) - Game.realTime;

							if (diff > 0){
								if (diff > 30*HOUR){
									text("30:00:00+");
								} else {
									text(dateFormat.format(new Date(diff)));
								}
								timeToUpdate = Game.realTime + SECOND;
							} else {
								text(Messages.get(HeroSelectScene.class, "daily"));
								timeToUpdate = Long.MAX_VALUE;
							}
						}

					}
				};
				dailyButton.leftJustify = true;
				dailyButton.icon(Icons.get(Icons.CALENDAR));
				//add(dailyButton);
				//buttons.add(dailyButton);

				StyledButton challengeButton = new StyledButton(Chrome.Type.BLANK, Messages.get(WndChallenges.class, "title"), 6){
					@Override
					protected void onClick() {
						ShatteredPixelDungeon.scene().addToFront(new WndChallenges(SPDSettings.challenges(), true) {
							public void onBackPressed() {
								super.onBackPressed();
								icon(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
								updateOptionsColor();
							}
						} );
					}
				};
				challengeButton.leftJustify = true;
				challengeButton.icon(Icons.get(SPDSettings.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
				add(challengeButton);
				buttons.add(challengeButton);
			}

			for (int i = 1; i < buttons.size(); i++){
				ColorBlock spc = new ColorBlock(1, 1, 0xFF000000);
				add(spc);
				spacers.add(spc);
			}
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;

			int width = 0;
			for (StyledButton btn : buttons){
				if (width < btn.reqWidth()) width = (int)btn.reqWidth();
			}
			width += bg.marginHor();

			int top = (int)y + bg.marginTop() - 1;
			int i = 0;
			for (StyledButton btn : buttons){
				btn.setRect(x+bg.marginLeft(), top, width - bg.marginHor(), 16);
				top = (int)btn.bottom();
				if (i < spacers.size()) {
					spacers.get(i).size(btn.width(), 1);
					spacers.get(i).x = btn.left();
					spacers.get(i).y = PixelScene.align(btn.bottom()-0.5f);
					i++;
				}
			}

			this.width = width;
			this.height = top+bg.marginBottom()-y-1;
			bg.size(this.width, this.height);

		}

		private void alpha( float value ){
			bg.alpha(value);

			for (StyledButton btn : buttons){
				btn.alpha(value);
			}

			for (ColorBlock spc : spacers){
				spc.alpha(value);
			}
		}
	}

}