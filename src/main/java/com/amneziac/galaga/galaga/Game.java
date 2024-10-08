package com.amneziac.galaga.galaga;

import com.amneziac.galaga.fleets.ExplosionFleet;
import com.amneziac.galaga.fleets.FighterFleet;
import com.amneziac.galaga.fleets.Fleet;
import com.amneziac.galaga.fleets.ShipFleet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.amneziac.galaga.io.ImageLoader;
import com.amneziac.galaga.objects.Explosion;
import com.amneziac.galaga.objects.Fighter;
import com.amneziac.galaga.objects.Ship;
import com.amneziac.galaga.objects.VisibleObject;
import com.amneziac.galaga.objects.ammo.BlueLaser;


public class Game extends JPanel {

    //public static final Dimension PANEL_SIZE = new Dimension(1435,835);
    public static final Dimension PANEL_SIZE = new Dimension(1435,780);
    static final String backgroundPath = "background.jpg";

    public static int wager;
    public static final int TIME_DELAY = 20;
    public static final int AMMO_DELAY = 50;


    public static final Rectangle RECTANGLE = new Rectangle(new Point(0,0), PANEL_SIZE);
    public static final Font font = new Font("monospaced", Font.BOLD, 20);

    public static boolean leftArrow = false;
    public static boolean rightArrow = false;

    public static Timer timer;
    Level1 level;
    public ArrayList<Fleet> fleets;
    static Image background;

    public Game (int wager)
    {
        this.wager = wager;

        initBackground();

        setSize(PANEL_SIZE);

        timer = new Timer(TIME_DELAY, new TimerListener());

        fleets = Level1.fleets;
        initializeAudio();


        level = new Level1(this);
    }

    public Game ()
    {
        initBackground();

        setSize(PANEL_SIZE);

        timer = new Timer(TIME_DELAY, new TimerListener());

        fleets = Level1.fleets;
        initializeAudio();

        level = new Level1(this);
    }

    private static void initBackground() {
        background = ImageLoader.load(backgroundPath).getScaledInstance(PANEL_SIZE.width, PANEL_SIZE.height, 1);
    }

    public int getScore()
    {
        return level.enemiesDestroyed*100;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);

        g.setColor(Color.yellow);
        g.setFont(font);
        g.drawString("SCORE: " + getScore(), 30, PANEL_SIZE.height - 50);

        g.drawString("HEALTH", PANEL_SIZE.width - 100 - 40*Ship.STARTING_HEALTH, PANEL_SIZE.height - 70);

        if(fleets.size() > 1 && !fleets.get(1).isEmpty())
        {
            int health = Ship.class.cast(fleets.get(1).get(0)).getHealth();

            switch (health)
            {
                case 1:
                    g.setColor(Color.RED);
                    break;
                case 2:
                    g.setColor(Color.YELLOW);
                    break;
                case 3:
                    g.setColor(Color.ORANGE);
                    break;
                case 4:
                    g.setColor(Color.BLUE);
                    break;
                default:
                    g.setColor(Color.GREEN);
                    break;
            }

            g.fillRoundRect(PANEL_SIZE.width - 100 - 40*Ship.STARTING_HEALTH, PANEL_SIZE.height - 40,
                40*health, 10, 15, 15);
        }

        for (int i = 0; i < fleets.size(); i++)
        {
            Fleet fleet = fleets.get(i);

            for (int j = 0; j < fleet.size(); j++)
            {
                VisibleObject obj = fleet.get(j);
                obj.paint(g);
            }
        }
    }

    public static boolean collision(Rectangle obj1, Rectangle obj2)
    {
        return (obj1.intersects(obj2));
    }

    public static void initializeAudio()
    {
        BlueLaser.initializeAudio();
        Explosion.initializeAudio();
        Fighter.initializeAudio();
    }


    private class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            for (int i = 0; i < fleets.size(); i++)
            {
                fleets.get(i).actionPerformed();
            }

            removeDeadObjects();

            repaint();
        }
    }

    private void removeDeadObjects()
    {
        ExplosionFleet.class.cast(fleets.get(0)).removeDeadObjects();

        ShipFleet.class.cast(fleets.get(1)).removeDeadObjects();

            for (int i = 2; i < fleets.size(); i++)
            {
                FighterFleet.class.cast(fleets.get(i)).removeDeadObjects();
                if (fleets.get(i).isEmpty())
                    fleets.remove(i);
            }
    }


}
