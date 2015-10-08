package com.benheron.game.state;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.benheron.game.main.Resources;
import com.benheron.game.model.ball;
import com.benheron.game.model.paddle;
import com.benheron.game.model.tile;
import com.benheron.game.model.worldObject;
import com.benheron.game.model.mousePoint;

public class PlayState extends State {

	private paddle myPaddle;
	private paddle enPaddle;
	private ball myBall;
	private tile tiles[];
	//player tiles/shield
	private tile plTiles[];
	//enemy tiles/shield
	private tile enTiles[];
	private int padSpeed = 8;
	private int ballSpeed = 5;
	private boolean won, lost = false;
	private int numTiles, numBarrier;
	private boolean moveUp, moveDown, moveRight, moveLeft, plShield;
	private boolean enShield;
	int score, counter;
	int plScore, enScore;
    private mousePoint mp;
    boolean freeze;
	
	@Override
	public void init() {
		numTiles = 7;
        numBarrier = 35;
		tiles = new tile[numBarrier];
		plTiles = new tile[numTiles];
		enTiles = new tile[numTiles];
		myPaddle = new paddle();
		enPaddle = new paddle();
		myBall = new ball();
        mp = new mousePoint();
		freeze = false;
		enPaddle.setX(780-enPaddle.getWidth());
		counter = 0;

		int tileNum = 0;
		for (int i = 0;i<5;i++)
		{
			for (int j = 0; j < 7; j++)
			{
				tiles[tileNum] = new tile(i*20+600, j*55 + 30);
				tileNum++;
			}
		}


		
		for (int i = 0; i < numTiles; i++)
		{
			plTiles[i] = new tile(85, i*55 + 30);
			enTiles[i] = new tile(715, i*55 + 30);
		}
		
		plScore = 0;
		enScore = 0;
		
		reset();
		
	}

	@Override
	public void update() {
		if (!won && !lost)
		{
			control();
			updateBall();
			updatePaddle();
			showEnShield();

            System.out.println(counter);




			if (plScore > 5)
			{
				won = true;
			}
			
			if (enScore > 5)
			{
				lost = true;
			}
            mp.setX(0);
            mp.setY(0);
		}
	}
	
	private void control()
	{
        double tx = myPaddle.getX();
		double ty = myPaddle.getY();
        double vx = myPaddle.getVelX();
        double vy = myPaddle.getVelY();
        double sx = myPaddle.getSpeedX();
        double sy = myPaddle.getSpeedY();
        double a = myPaddle.getAcceleration();
        double ms = myPaddle.getMaxSpeed();

        //speed limiters
        if(vy > ms )
        {
            vy = ms;
        }

        if (vy < -ms)
        {
            vy = -ms;
        }

        if(vx > ms )
        {
            vx = ms;
        }

        if (vx < -ms)
        {
            vx = -ms;
        }

        //acceleration physics and movement
        if (moveUp)
		{
            vy -=a;
			//myPaddle.setY(ty-s);
		}
		if (moveDown)
		{
            vy+=a;
		}

        if (moveRight)
        {
            vx +=a;
            //myPaddle.setY(ty-s);
        }
        if (moveLeft)
        {
            vx-=a;
        }

        if (!moveUp && !moveDown)
        {
           if (vy < 0)
           {
               vy +=0.7;
           } else {
               vy -=0.7;
           }
            sy -=0.5;
            if (sy < 0)
            {
                vy = 0;
            }
        }

        if (!moveLeft && !moveRight)
        {
            if (vx < 0)
            {
                vx +=0.7;
            } else {
                vx -=0.7;
            }
            sx -=0.5;
            if (sx < 0)
            {
                vx = 0;
            }
        }

        myPaddle.setX(tx+vx);
        myPaddle.setY(ty+vy);

        myPaddle.setVelX(vx);
        myPaddle.setVelY(vy);

        //stop from going off the top
		if (myPaddle.getY() < 0)
        {
			myPaddle.setY(0);
            myPaddle.setVelY(0);
		}

        //stop from going off the bottom
		if ((myPaddle.getY()+ myPaddle.getHeight()) > 450)
		{
			double ytmp = 450 - myPaddle.getHeight();
			myPaddle.setY(ytmp);
            myPaddle.setVelY(0);
		}

        if (myPaddle.getX()<0)
        {
            myPaddle.setX(0);
            myPaddle.setVelX(0);
        }

        if ((myPaddle.getX() + myPaddle.getWidth()) > 300)
        {
            double xtmp = 300 - myPaddle.getWidth();
            myPaddle.setX(xtmp);
            myPaddle.setVelX(0);
        }

	}
	
	private void updateBall()
	{
		float xBallS = myBall.getXSpeed();
		float yBallS = myBall.getYSpeed();
		int bxPos = (int)myBall.getX();
		int byPos = (int)myBall.getY();
		
		if (xBallS > 15)
		{
			xBallS = 15;
		}
		
		if (byPos < 0 || byPos > 450)
		{
			yBallS *=-1;
		}

        //check when the ball hits player paddle
		//next frame intersect
		if (intersectNF(myBall, myPaddle))
		{
            //only collide if travelling towards the player
			if (xBallS < 0) {
                //find position hit on paddle
                int tmp = ((int) myPaddle.getY() + ((int) myPaddle.getHeight() / 2)) - ((int) myBall.getY() + (int) myBall.getHeight() / 2);
                if (tmp < 0) {
                    yBallS = 3 * tmp / 25 + 1;
                } else {
                    yBallS = -3 * tmp / 25 - 1;
                }
                //set x to edge of paddle

                //check if paddle is behind or ahead of the ball
                if (myPaddle.getX() < myBall.getX()) {
                    //if behind, behave normally
                    myBall.setX(myPaddle.getX() + myPaddle.getWidth());
                    xBallS *= - 1;
                    //add extra speed of hit
                    xBallS += myPaddle.getVelX();
                    yBallS += myPaddle.getVelY();
                } else {
                    //hit towards your own end
                    myBall.setX(myPaddle.getX());
                    xBallS += myPaddle.getVelX();
                    yBallS += myPaddle.getVelY();
                }
            }
		}
		
		
		if (intersectNF(myBall, enPaddle))
		{
			if (xBallS > 0)
			{
				int tmp = ((int)enPaddle.getY()+((int)enPaddle.getHeight()/2)) - ((int)myBall.getY()+(int)myBall.getHeight()/2);
				if (tmp < 0)
				{
					yBallS = 3 * tmp/25 + 1;
				} else {
					yBallS = -3 *tmp/25 - 1;
				}
			//set x to edge of paddle
				myBall.setX(enPaddle.getX()-myBall.getWidth());
			
				xBallS *=-1;
			}
		}
		
		for (int i=0;i<numTiles;i++)
		{
			if (intersectNF(myBall, plTiles[i]))
			{
				if (!plTiles[i].isDest())
				{
					if (plShield)
					{
						xBallS *=-1;
						plTiles[i].setDest(true);
						score += 100;
					}
				}
			}
			
			if (intersectNF(myBall, enTiles[i]))
			{
				if (!enTiles[i].isDest())
				{
					if (enShield)
					{
						xBallS *=-1;
						enTiles[i].setDest(true);
						score += 100;
					}
				}
			}
		}

        //ball with barrier
        for (int i =0;i<35;i++)
        {
            if (intersectNF(myBall, tiles[i]))
            {
                if (!tiles[i].isDest())
                {
                    xBallS *=-1;
                    tiles[i].setDest(true);
                }
            }


            if(intersect(mp, tiles[i]))
            {
                tiles[i].setDest(true);
            }
        }

        if (intersect(mp, enPaddle))
        {
            freeze = true;
        }

        if (freeze)
        {
            counter++;
            if(counter > 100)
            {
                counter = 0;
                freeze = false;
            }
        }



		
		bxPos += xBallS;
		byPos += yBallS;
		
		//update positions and speed
		myBall.setX(bxPos);
		myBall.setY(byPos);
		
		myBall.setXSpeed(xBallS);
		myBall.setYSpeed(yBallS);
		
		if (bxPos > 800)
		{
			//xBallS *=-1;
			reset();
			plScore += 1;
		}
		
		if (bxPos < 0)
		{
			reset();
			enScore +=1;
			//lost = true;
		}
	}
	
	private void updatePaddle()
	{
		float tmy = (float)enPaddle.getY()+(enPaddle.getHeight()/2);
        //move paddle up
		if (myBall.getY() > tmy + 15)
		{
            if (!freeze) {
                int tmp = (int) enPaddle.getY();
                tmp += 2;
                enPaddle.setY(tmp);
            }
		}
		//move paddle down
		if (myBall.getY() < tmy - 15)
		{
            if (!freeze) {
                int tmp = (int) enPaddle.getY();
                tmp -= 2;
                enPaddle.setY(tmp);
            }
		}
	}
	
	private void showEnShield()
	{
		//check distance
		if ((enPaddle.getX()-myBall.getX()) < 150)
		{
			if (myBall.getXSpeed() > 9 || myBall.getYSpeed() > 2 || myBall.getYSpeed() < -2)
			{
				enShield = true;
			}
			
		} else {
			enShield = false;
		}
		//System.out.println(enShield);
	}
	
	private void reset()
	{
		Random random = new Random();
		int rint = random.nextInt(2);
		
		switch(rint)
		{
		case 0:
			myBall.setXSpeed(-3);
			break;
		case 1:
			myBall.setXSpeed(3);
			break;
		}
		
		int rint2 = random.nextInt(2);
		
		switch(rint2)
		{
		case 0:
			myBall.setYSpeed(-3);
			break;
		case 1:
			myBall.setYSpeed(3);
			break;
		}
		int ry = random.nextInt(450);
		myBall.setX(400);
		myBall.setY(ry);
		
		for (int i=0;i<numTiles;i++)
		{
			plTiles[i].setDest(false);
			enTiles[i].setDest(false);
		}

        for (int i=0;i<numBarrier;i++)
        {
            tiles[i].setDest(false);
        }
	}

	@Override
	public void render(Graphics g) {

		if (!won && !lost)
		{
            //draw background
			g.setColor(Resources.darkBlue);
			g.fillRect(0, 0, 800, 450);

            //draw player paddle
			g.setColor(Color.WHITE);
			g.fillRect((int)myPaddle.getX(), (int)myPaddle.getY(),(int) myPaddle.getWidth(), (int)myPaddle.getHeight());

            //draw enemy paddle
			g.fillRect((int)enPaddle.getX(), (int)enPaddle.getY(), (int)enPaddle.getWidth(), (int)enPaddle.getHeight());

            //draw ball
			g.setColor(Color.RED);
			g.fillRect((int)myBall.getX(), (int)myBall.getY(),(int) myBall.getWidth(),(int) myBall.getHeight());

            //draw tiles
			g.setColor(Color.BLACK);
			for (int i=0;i<numTiles;i++)
			{
				if(!plTiles[i].isDest())
				{
					if (plShield)
					{
						g.fillRect((int)plTiles[i].getX(), (int)plTiles[i].getY(), (int)plTiles[i].getWidth(), (int)plTiles[i].getHeight());
					}
				}
				
				if(!enTiles[i].isDest())
				{
					if (enShield)
					{
						g.fillRect((int)enTiles[i].getX(), (int)enTiles[i].getY(), (int)enTiles[i].getWidth(), (int)enTiles[i].getHeight());
					}
				}
			}
            //draw barrier
            g.setColor(Color.GRAY);
            for (int i=0;i<numBarrier;i++)
            {
                if (!tiles[i].isDest())
                {
                    g.fillRect((int)tiles[i].getX(), (int)tiles[i].getY(), (int)tiles[i].getWidth(), (int)tiles[i].getHeight());
                }
            }
            //draw scores
			g.drawString("Player Score: " + String.valueOf(plScore), 25, 25);
			
			g.drawString("Enemy Score: " + String.valueOf(enScore), 650, 25);
            /*
            //draw mouse pointer
            g.setColor(Color.ORANGE);
            g.fillRect((int)mp.getX(), (int)mp.getY(),30,30);*/
		} else if(won)
		{
			g.drawString("You won!", 380, 200);
			g.drawString("Click to go to splash screen!", 380, 400);
		} else if(lost)
		{
			g.drawString("You lost :(", 380, 200);
			g.drawString("Click to go to splash screen!", 380, 400);
		}
		
	
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		if (won || lost)
		{
			setCurState(new MenuState());
		}
        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();

        int px = e.getX();
        int py = e.getY();



        mp.setX(px);
        mp.setY(py);

	}

	@Override
	public void onKeyPress(KeyEvent e) {
		switch(e.getKeyCode())
		{
            case 37:
                moveLeft = true;
                break;
            case 65:
                moveLeft = true;
                break;
            case 39:
                moveRight = true;
                break;
            case 68:
                moveRight = true;
                break;
            case 38:
                moveUp = true;
                break;
            case 87:
                moveUp = true;
                break;
            case 40:
                moveDown = true;
                break;
            case 83:
                moveDown = true;
                break;
            case 32:
                plShield = true;
                break;
		}
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		switch(e.getKeyCode())
		{
            case 37:
                moveLeft = false;
                break;
            case 65:
                moveLeft = false;
                break;
            case 39:
                moveRight = false;
                break;
            case 68:
                moveRight = false;
                break;
            case 38:
                moveUp = false;
                break;
            case 87:
                moveUp = false;
                break;
            case 40:
                moveDown = false;
                break;
            case 83:
                moveDown = false;
                break;
            case 32:
                plShield = false;
                break;
		}
		
	}

    @Override
    public void onMouseMoved(MouseEvent e) {
        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();


        int px = (int) p.getX();
        int py = (int) p.getY();

        mp.setX(px);
        mp.setY(py);
    }




    private boolean intersect(worldObject object1, worldObject object2)
	{
		int t1x = (int)object1.getX();
		int t1y = (int)object1.getY();
		int t1w = (int)object1.getWidth();
		int t1h = (int)object1.getHeight();
		
		int t2x = (int)object2.getX();
		int t2y = (int)object2.getY();
		int t2w = object2.getWidth();
		int t2h = object2.getHeight();
		
		
		if (t1x + t1w < t2x ||
			t1x > t2x + t2w ||
			t1y + t1h < t2y ||
			t1y > t2y + t2h) 
			{
				return false;
			}
			return true;
	}
	
	private boolean intersectNF(worldObject object1, worldObject object2)
	{
		//next frame intersect
        double xBallS = myBall.getXSpeed();
        double yBallS = myBall.getYSpeed();

        double t1x = object1.getX() + xBallS;
        double t1y = object1.getY();
        double t1w = object1.getWidth();
        double t1h = object1.getHeight();

        double t2x = object2.getX();
        double t2y = object2.getY();
        double t2w = object2.getWidth();
        double t2h = object2.getHeight();
		
		
		if (t1x + t1w < t2x ||
			t1x > t2x + t2w ||
			t1y + t1h < t2y ||
			t1y > t2y + t2h) 
			{
				return false;
			}
			return true;
	}

}
