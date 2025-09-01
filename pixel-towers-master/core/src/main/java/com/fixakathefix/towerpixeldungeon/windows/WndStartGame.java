package com.fixakathefix.towerpixeldungeon.windows;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroSubClass;
import com.fixakathefix.towerpixeldungeon.journal.Journal;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GamemodeSelectionScene;
import com.fixakathefix.towerpixeldungeon.scenes.InterlevelScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.SkeletonSprite;
import com.fixakathefix.towerpixeldungeon.ui.Button;
import com.fixakathefix.towerpixeldungeon.ui.IconButton;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.RedButton;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class WndStartGame extends Window {

    private static final int WIDTH    = 120;
    private static final int HEIGHT   = 140;

    public WndStartGame(final int slot){

        Badges.loadGlobal();
        Journal.loadGlobal();

        RenderedTextBlock title = new RenderedTextBlock(Messages.get(this, "title"), 12 );
        title.hardlight(Window.TITLE_COLOR);
        title.setPos((WIDTH - title.width())/2f, 2);
        add(title);

        float heroBtnSpacing = (WIDTH - 4*HeroBtn.WIDTH)/5f;

        float curX = heroBtnSpacing;
        for (HeroClass cl : HeroClass.values()){
            HeroBtn button = new HeroBtn(cl);
            button.setRect(curX, title.bottom() + 4, HeroBtn.WIDTH, HeroBtn.HEIGHT);
            curX += HeroBtn.WIDTH + heroBtnSpacing;
            add(button);
        }

        ColorBlock separator = new ColorBlock(1, 1, 0xFF222222);
        separator.size(WIDTH, 1);
        separator.x = 0;
        separator.y = title.bottom() + 6 + HeroBtn.HEIGHT;
        add(separator);

        HeroPane ava = new HeroPane();
        ava.setRect(20, separator.y + 2, WIDTH-30, 80);
        add(ava);

        RedButton start = new RedButton(Messages.get(this, "start")){
            @Override
            protected void onClick() {
                super.onClick();

                if (GamesInProgress.selectedClass == null) return;

                Game.switchScene( GamemodeSelectionScene.class);
            }

            @Override
            public void update() {
                if( !visible && GamesInProgress.selectedClass != null){
                    visible = true;
                }
                super.update();
            }
        };
        start.visible = false;
        start.setRect(0, HEIGHT - 20, WIDTH, 20);
        add(start);
        Dungeon.challenges = 0;
        SPDSettings.challenges(0);

        resize(WIDTH, HEIGHT);

    }

    private static class HeroBtn extends Button {

        private HeroClass cl;

        private Image hero;

        private static final int WIDTH = 24;
        private static final int HEIGHT = 16;

        HeroBtn ( HeroClass cl ){
            super();
            this.cl = cl;
            if (cl == HeroClass.WARRIOR){
                hero = new Image(Assets.Sprites.WARRIOR, 0, 90, 12, 15);
            } else if (cl == HeroClass.MAGE){
                hero = new Image(Assets.Sprites.MAGE, 0, 90, 12, 15);
            } else if (cl == HeroClass.ROGUE){
                hero = new Image(Assets.Sprites.ROGUE, 0, 90, 12, 15);
            } else if (cl == HeroClass.HUNTRESS){
                hero = new Image(Assets.Sprites.HUNTRESS, 0, 90, 12, 15);
            } else if (cl == HeroClass.NECROHERO){
                hero = new Image(Assets.Sprites.NECROHERO, 0, 90, 12, 15);
            } else if (cl == HeroClass.DUELIST){
                hero = new Image(Assets.Sprites.DUELIST, 0, 90, 12, 15);
            } else if (cl == HeroClass.TANK){
                hero = new Image(Assets.Sprites.TANK, 0, 90, 12, 15);
            } else if (cl == HeroClass.PRIEST){
                hero = new Image(Assets.Sprites.PRIEST, 0, 90, 12, 15);
            }
            update();
        }

        @Override
        protected void layout() {
            super.layout();
            if (hero != null){
                hero.x = x + (width - hero.width()) / 2f;
                hero.y = y + (height - hero.height()) / 2f;
                PixelScene.align(hero);
            }
        }

        @Override
        public void update() {
            super.update();
            hero.brightness(1f);
        }

        @Override
        protected void onClick() {
            super.onClick();
            GamesInProgress.selectedClass = cl;
        }
    }

    private class HeroPane extends Component {

        private HeroClass cl;

        private Image avatar;

        private IconButton heroItem;
        private IconButton heroLoadout;
        private IconButton heroMisc;
        private IconButton heroInfoGeneral;

        private RenderedTextBlock name;

        private static final int BTN_SIZE = 20;

        @Override
        protected void createChildren() {
            super.createChildren();

            avatar = new Image(Assets.Sprites.AVATARS);
            avatar.scale.set(2f);
            add(avatar);

            heroInfoGeneral = new IconButton(Icons.get(Icons.INFO)){
                @Override
                protected void onClick() {
                    if (cl == null) return;
                    String msg = Messages.get(cl, cl.name() + "_desc");
                    for (HeroSubClass sub : cl.subClasses()){
                        msg += "\n\n" + sub.desc();
                    }
                    ShatteredPixelDungeon.scene().add(new WndMessage(msg));
                }
            };
            heroInfoGeneral.setSize(BTN_SIZE, BTN_SIZE);
            add(heroInfoGeneral);

            heroItem = new IconButton(){
                @Override
                protected void onClick() {
                    if (cl == null) return;
                    ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_item")));
                }
            };
            heroItem.setSize(BTN_SIZE, BTN_SIZE);
            add(heroItem);

            heroLoadout = new IconButton(){
                @Override
                protected void onClick() {
                    if (cl == null) return;
                    ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_loadout")));
                }
            };
            heroLoadout.setSize(BTN_SIZE, BTN_SIZE);
            add(heroLoadout);

            heroMisc = new IconButton(){
                @Override
                protected void onClick() {
                    if (cl == null) return;
                    ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(cl, cl.name() + "_desc_misc")));
                }
            };
            heroMisc.setSize(BTN_SIZE, BTN_SIZE);
            add(heroMisc);



            name = new RenderedTextBlock(12);
            add(name);

            visible = false;
        }

        @Override
        protected void layout() {
            super.layout();

            avatar.x = x;
            avatar.y = y + (height - avatar.height() - name.bottom() - 2)/2f;
            PixelScene.align(avatar);

            name.setPos(x + (avatar.width() - name.width())/2f, avatar.y + avatar.height() + 2);
            PixelScene.align(name);

            heroItem.setPos(x + width - BTN_SIZE, y);
            heroLoadout.setPos(x + width - BTN_SIZE, heroItem.bottom());
            heroMisc.setPos(x + width - BTN_SIZE, heroLoadout.bottom());
            heroInfoGeneral.setPos(x + width - BTN_SIZE, heroMisc.bottom());
        }

        @Override
        public synchronized void update() {
            super.update();
            if (GamesInProgress.selectedClass != cl){
                cl = GamesInProgress.selectedClass;
                if (cl != null) {
                    avatar.frame(cl.ordinal() * 24, 0, 24, 32);

                    name.text(Messages.capitalize(cl.title()));

                    switch(cl){
                        case WARRIOR:
                            heroItem.icon(new ItemSprite(ItemSpriteSheet.LONGSWORD, null));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_TREATWOUNDS, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.RATION, null));
                            break;
                        case MAGE:
                            heroItem.icon(new ItemSprite(ItemSpriteSheet.MAGES_STAFF, null));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_GIBBERISH, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.WAND_MAGIC_MISSILE, null));
                            break;
                        case ROGUE:
                            heroItem.icon(new ItemSprite(ItemSpriteSheet.ARTIFACT_CLOAK, null));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_SWIFT, null));
                            heroMisc.icon(Icons.get(Icons.DEPTH));
                            break;
                        case HUNTRESS:
                            heroItem.icon(new ItemSprite(ItemSpriteSheet.SPIRIT_BOW, null));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_LASHER, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.DART, null));
                            break;
                        case DUELIST:
                            heroItem.icon(new ItemSprite(ItemSpriteSheet.RAPIER, null));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_BANNER, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.DART, null));
                            break;
                        case NECROHERO:
                            heroItem.icon(Icons.get(Icons.NECROHERO));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_OBELISK, null));
                            heroMisc.icon(new SkeletonSprite());
                            break;
                        case TANK:
                            heroItem.icon(Icons.get(Icons.TANK));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_WALLSTANCE, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.EXOTIC_HEAL, null));
                            break;
                        case PRIEST:
                            heroItem.icon(Icons.get(Icons.PRIEST));
                            heroLoadout.icon(new ItemSprite(ItemSpriteSheet.HEROSPELL_SUN, null));
                            heroMisc.icon(new ItemSprite(ItemSpriteSheet.EXOTIC_BANISHMENT, null));
                            break;
                    }

                    layout();

                    visible = true;
                } else {
                    visible = false;
                }
            }
        }
    }

}