
package com.amneziac.galaga.objects;

import com.amneziac.galaga.fleets.ShipFleet;
import com.amneziac.galaga.galaga.Game;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.amneziac.galaga.io.ImageLoader;
import com.amneziac.galaga.objects.ammo.BlueLaser;
import com.amneziac.galaga.objects.guns.Gun;


public class Ship extends Fighter implements KeyListener {
    
    public static final Dimension DEFAULT_SIZE = new Dimension(60, 70);
    public static final int STARTING_HEALTH = 5;
    
    protected boolean leftArrow = false;
    protected boolean rightArrow = false;

    public Ship (Point startingLocation, ShipFleet parent)
    {
        super(startingLocation, parent, DEFAULT_SIZE);
        
        healthPoints = STARTING_HEALTH;
        speed = 10;
        
        Point leftGun = new Point(-1, height/2 - 9);
        Point rightGun = new Point(width - 6, height/2 - 9);
        ammoVelocity = -1;
        guns.add(new Gun(leftGun, ammoVelocity, BlueLaser.class, this));
        guns.add(new Gun(rightGun, ammoVelocity, BlueLaser.class, this));

        image = ImageLoader.load("Ship.gif");
        image = image.getScaledInstance(width, height, 1);
        
//        galaga.Game.timer.addActionListener(this);
    }

    public void actionPerformed() {
        
        super.actionPerformed();
        
        if (leftArrow && x > 0)
        {
            x -= speed;
        }

        if (rightArrow && x < Game.PANEL_SIZE.getWidth() - width)
        {
            x += speed;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                leftArrow = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightArrow = true;
                break;
            case KeyEvent.VK_SPACE:
                if(!parent.isEmpty())
                    fire();
                break;
            case KeyEvent.VK_P:
                if(Game.timer.isRunning())
                    Game.timer.stop();
                else
                    Game.timer.start();
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                leftArrow = false;
                break;

            case KeyEvent.VK_RIGHT:
                rightArrow = false;
                break;
            case KeyEvent.VK_SPACE:
                for (Gun gun : guns)
                {
                    gun.setCanFire(true);
                }
        }
    }
}
