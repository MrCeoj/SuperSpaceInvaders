package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;

public class Space extends AbstractScreen {

    private final MainGame g;

    boolean haveDest = false;
    boolean stage2 = false;
    boolean stage3 = false;
    SpriteBatch batch;
    Texture two = new Texture("Fase2.jpg");
    Texture thr = new Texture("Fase3.jpg");
    Texture back = new Texture("spaceback.jpg");
    Texture playa = new Texture("Player.png");
    Texture red = new Texture("red_1.png");
    Texture yel = new Texture("yellow.png");
    Texture blu = new Texture("blue.png");
    Texture imgBullet = new Texture("Bullet.png");
    Texture alienShot = new Texture("alienshot.png");
    Texture imgBoss = new Texture("purple.png");
    Texture dest = new Texture("destroyersmol.gif");
    Texture blank = new Texture("blank.png");
    Music bgm;
    Music shot;
    Music pup;

    ArrayList<Explosion> ex;
    ArrayList<BigExplosion> uex;
    ArrayList<Bullet> bullets;
    ArrayList<Alien> reds;
    ArrayList<ToughAlien> yels;
    ArrayList<Biggies> blues;
    AlienJefe aboss;
    Player player;
    Destroyer destroyer;

    int widthReds = 11;
    int heightReds = 5;
    int spacingReds = 35;
    int aliveReds = 0;
    float redSpeed = 100;

    int widthYel = 8;
    int heightYel = 3;
    int spacingYel = 60;
    int aliveYels = 0;
    float yelSpeed = 150;

    int widthBlu = 5;
    int heightBlu = 1;
    int spacingBlu = 120;
    int aliveBlu = 0;
    float bluSpeed = 30;

    int direction_aliens = 1;
    float passed = 1;
    static final float PLAYER_SPEED = 400;
    static final float PURPLE_SPEED = 200;
    static final float BULLET_SPEED = 800;
    static final float FALL_SPEED = -100;
    static final float DESTROY_SPEED = 1000;
    static final float willnotnotice = 10000;

    public Space(MainGame g) {
        super(g);
        this.g = g;
        batch = new SpriteBatch();
        player = new Player(new Vector2(0, 0), playa, Color.GREEN, PLAYER_SPEED);
        reds = new ArrayList<>();
        yels = new ArrayList<>();
        blues = new ArrayList<>();
        bullets = new ArrayList();
        ex = new ArrayList<>();
        uex = new ArrayList<>();
        bgm = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        bgm.setLooping(true);
        shot = Gdx.audio.newMusic(Gdx.files.internal("shot.mp3"));
        pup = Gdx.audio.newMusic(Gdx.files.internal("pup.mp3"));
    }

    @Override
    public void show() {
        g.cliente.setSpace(this);
        for (int y = 0; y < heightReds; y++) {
            for (int x = 0; x < widthReds; x++) {
                Vector2 position = new Vector2(x * spacingReds, y * spacingReds);
                position.x += Gdx.graphics.getWidth() / 2;
                position.y += Gdx.graphics.getHeight();
                position.x -= (widthReds / 2) * spacingReds;
                position.y -= (heightReds) * spacingReds;
                reds.add(new Alien(position, red, Color.GREEN, redSpeed));
                aliveReds++;
            }
        }

        destroyer = new Destroyer(Vector2.Zero, blank, Color.GREEN, PURPLE_SPEED);
        bgm.play();
    }

    @Override
    public void render(float f) {
        super.render(f);
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        passed += deltaTime;
        batch.begin();
        gameLogic(deltaTime);
        batch.draw(back, 0, 0);
        player.draw(batch);

        if (stage2) {
            batch.draw(two, 200, 200);
            for (ToughAlien y : yels) {
                if (y.isAlive) {
                    y.draw(batch);
                }
            }
        }

        if (stage3) {
            two.dispose();
            batch.draw(thr, 200, 200);
            for (Biggies b : blues) {
                if (b.isAlive) {
                    b.draw(batch);
                }
            }
        }

        for (Alien alien : reds) {
            if (alien.isAlive) {
                alien.draw(batch);
            }
        }

        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        for (Explosion e : ex) {
            e.draw(batch, deltaTime);
        }

        for (BigExplosion be : uex) {
            be.draw(batch, deltaTime);
        }

        if (aboss != null) {
            aboss.draw(batch);
        }
        if (destroyer != null) {
            destroyer.draw(batch);
        }

        batch.end();
    }

    void gameLogic(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && passed >= 0.3f) {
            passed = 0;
            bullets.add(new Bullet(new Vector2(player.getPosition().x + 6, player.getPosition().y), imgBullet, Color.GREEN, BULLET_SPEED));
            shot.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (haveDest) {
                destroyer = new Destroyer(new Vector2(player.position.x + 6, player.position.y + 40), dest, Color.CLEAR, DESTROY_SPEED);
                haveDest = false;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(deltaTime);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(deltaTime);
        }

        abossLogic(deltaTime);
        alienLogic(deltaTime);
        yelLogic(deltaTime);
        blueLogic(deltaTime);
        bulletLogic(deltaTime);
        destroyerLogic(deltaTime);
        playerLogic(deltaTime);
    }

    void playerLogic(float deltaTime) {
        player.update(deltaTime);

        }

    void alienLogic(float deltaTime) {

        for (int i = 0; i < reds.size(); i++) {
            if (reds.get(i).isAlive == false) {
                reds.remove(i);
            }
        }

        for (Alien alien : reds) {
            alien.update(deltaTime, direction_aliens);
            if (alien.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
                gameover();
            }
        }

        for (int j = 0; j < reds.size(); j++) {
            if (reds.get(j).isAlive) {
                for (Bullet bull : bullets) {
                    if (bull.sprite.getBoundingRectangle().overlaps(reds.get(j).sprite.getBoundingRectangle()) && bull.isAlive) {
                        bullets.get(bullets.indexOf(bull)).isAlive = false;
                        reds.get(j).isAlive = false;
                        aliveReds--;
                        ex.add(new Explosion(reds.get(j).position.x, reds.get(j).position.y));
                        if (aliveReds <= 0) {
                            stage2 = true;
                            destroyer = new Destroyer(new Vector2(400, 600), dest, Color.GREEN, FALL_SPEED);
                            for (int y = 0; y < heightYel; y++) {
                                for (int x = 0; x < widthYel; x++) {
                                    Vector2 position = new Vector2(x * spacingYel, y * spacingYel);
                                    position.x += Gdx.graphics.getWidth() / 2;
                                    position.y += Gdx.graphics.getHeight();
                                    position.x -= (widthYel / 2) * spacingYel;
                                    position.y -= (heightYel) * spacingYel;
                                    yels.add(new ToughAlien(position, yel, Color.GREEN, yelSpeed));
                                    aliveYels++;
                                }
                            }
                            red.dispose();
                        }
                    }
                }

            }
        }

        for (int i = 0; i < reds.size(); i++) {
            if (reds.get(i).position.x + reds.get(i).sprite.getWidth() >= Gdx.graphics.getWidth()) {
                direction_aliens = -1;
            }
            if (reds.get(i).position.x <= 0) {
                direction_aliens = 1;
            }
            if (reds.get(i).position.y <= 0) {
                gameover();
            }
        }
    }

    void yelLogic(float deltaTime) {

        for (int i = 0; i < yels.size(); i++) {
            if (yels.get(i).isAlive == false) {
                yels.remove(i);
            }
        }

        for (ToughAlien y : yels) {
            y.update(deltaTime, direction_aliens);
            if (y.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
                gameover();
            }
        }

        for (int j = 0; j < yels.size(); j++) {
            if (yels.get(j).isAlive) {
                if (destroyer.sprite.getBoundingRectangle().overlaps(yels.get(j).sprite.getBoundingRectangle())) {
                    yels.get(j).isAlive = false;
                    ex.add(new Explosion(yels.get(j).position.x, yels.get(j).position.y));
                    aliveYels--;
                }
                for (Bullet bull : bullets) {
                    if (bull.sprite.getBoundingRectangle().overlaps(yels.get(j).sprite.getBoundingRectangle()) && bull.isAlive) {
                        bullets.get(bullets.indexOf(bull)).isAlive = false;
                        yels.get(j).hitAlien();
                        if (!yels.get(j).isAlive) {
                            ex.add(new Explosion(yels.get(j).position.x, yels.get(j).position.y));
                            aliveYels--;
                        }
                        if (aliveYels <= 0) {
                            stage3 = true;
                            destroyer = new Destroyer(new Vector2(400, 600), dest, Color.CLEAR, FALL_SPEED);
                            for (int y = 0; y < heightBlu; y++) {
                                for (int x = 0; x < widthBlu; x++) {
                                    Vector2 position = new Vector2(x * spacingBlu, y * spacingBlu);
                                    position.x += Gdx.graphics.getWidth() / 2;
                                    position.y += Gdx.graphics.getHeight();
                                    position.x -= (widthBlu / 2) * spacingBlu;
                                    position.y -= (heightBlu) * spacingBlu;
                                    blues.add(new Biggies(position, blu, Color.GREEN, bluSpeed));
                                    aliveBlu++;
                                }
                            }
                            yel.dispose();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < yels.size(); i++) {
            if (yels.get(i).position.x + yels.get(i).sprite.getWidth() >= Gdx.graphics.getWidth()) {
                direction_aliens = -1;
            }
            if (yels.get(i).position.x <= 0) {
                direction_aliens = 1;
            }
            if (yels.get(i).position.y <= 0) {
                gameover();
            }
        }
    }

    void blueLogic(float deltaTime) {

        for (int i = 0; i < blues.size(); i++) {
            if (blues.get(i).isAlive == false) {
                blues.remove(i);
            }
        }

        for (Biggies b : blues) {
            {
                b.update(deltaTime, direction_aliens);
                if (b.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
                    gameover();
                }
            }
        }

        for (int j = 0; j < blues.size(); j++) {
            if (destroyer.sprite.getBoundingRectangle().overlaps(blues.get(j).sprite.getBoundingRectangle())) {
                blues.get(j).isAlive = false;
                aliveBlu--;
                uex.add(new BigExplosion(blues.get(j).position.x, blues.get(j).position.y));
                g.cliente.enviar("Boss");
            }
            if (blues.get(j).isAlive) {
                for (Bullet bull : bullets) {
                    if (bull.sprite.getBoundingRectangle().overlaps(blues.get(j).sprite.getBoundingRectangle()) && bull.isAlive) {
                        bullets.get(bullets.indexOf(bull)).isAlive = false;
                        blues.get(j).hitAlien();
                        if (!blues.get(j).isAlive) {
                            uex.add(new BigExplosion(blues.get(j).position.x, blues.get(j).position.y));
                            aliveBlu--;
                            boss();
//                            g.cliente.enviar("Boss");
                        }
                        if (aliveBlu <= 0) {
                            g.setScreen(new Win(this.g));
                            g.cliente.enviar("Won");
                            dispose();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < blues.size(); i++) {
            if (blues.get(i).position.x + blues.get(i).sprite.getWidth() >= Gdx.graphics.getWidth()) {
                direction_aliens = -1;
            }
            if (blues.get(i).position.x <= 0) {
                direction_aliens = 1;
            }
            if (blues.get(i).position.y <= 0) {
                gameover();
            }
        }
    }

    void bulletLogic(float deltaTime) {
        for (int i = 0; i < bullets.size(); i++) {
            if (!bullets.get(i).isAlive) {
                bullets.remove(i);
            }
        }

        for (int i = 0; i < ex.size(); i++) {
            if (ex.get(i).effect.isComplete()) {
                ex.remove(i);
            }
        }

        for (int i = 0; i < uex.size(); i++) {
            if (uex.get(i).effect.isComplete()) {
                uex.remove(i);
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }
    }

    void destroyerLogic(float deltaTime) {
        destroyer.update(deltaTime);
        if (destroyer == null) {
            return;
        }

        if (destroyer.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
            destroyer = new Destroyer(new Vector2(800, 600), blank, Color.CLEAR, willnotnotice);
            pup.play();
            haveDest = true;
        }
    }

    void abossLogic(float deltaTime) {
        if (aboss != null) {

            if (!aboss.isAlive) {
                aboss = null;
                return;
            }

            aboss.update(deltaTime, player.position.x);
            
            if(destroyer.sprite.getBoundingRectangle().overlaps(aboss.sprite.getBoundingRectangle())){
                aboss.isAlive = false;
            }
            
            for (Bullet bullet : bullets) {
                if (bullet.sprite.getBoundingRectangle().overlaps(aboss.sprite.getBoundingRectangle()) && bullet.isAlive) {
                    bullets.get(bullets.indexOf(bullet)).isAlive = false;
                    aboss.hitAlien();
                }
            }
            if (aboss.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
                gameover();
            }

        }
    }

    public void boss() {
        aboss = new AlienJefe(new Vector2(player.getPosition().x, Gdx.graphics.getHeight()), imgBoss, Color.GREEN, PURPLE_SPEED);
        System.out.println("BOSSSSSSSS");
    }

    public void gameover() {
        g.setScreen(new GameOver(this.g));
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBullet.dispose();
        imgBoss.dispose();
        playa.dispose();
        two.dispose();
        thr.dispose();
        back.dispose();
        alienShot.dispose();
        yel.dispose();
        dest.dispose();
        blu.dispose();
        shot.dispose();
        bgm.dispose();
        pup.dispose();
    }
}
