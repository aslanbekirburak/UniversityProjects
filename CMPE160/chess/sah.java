package chess;
import java.awt.Color;
import java.awt.Graphics;

public class sah extends Piece{
	
	public sah(boolean isBlack)
	{
		this.isBlack = isBlack;
	}

	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		// TODO Auto-generated method stub
		
		
		if(isBlack)
		{
			g.setColor(Color.black);
		}
		else
		{
			g.setColor(Color.white);
		}
		g.fillOval(positionX+(int)(squareWidth*7.57/12.0), 
				(int)(positionY+squareWidth/7.5), 
				(int)(squareWidth/6), (int)(squareWidth/6));
		g.fillOval(positionX+(int)(squareWidth*3./12.0), 
				(int)(positionY+squareWidth/7.5), 
				(int)(squareWidth/6), (int)(squareWidth/6));
		g.fillOval(positionX+(int)(squareWidth*5.07/12.0), 
				(int)(positionY+squareWidth/7.5), 
				(int)(squareWidth/6), (int)(squareWidth/6));
		/*
		g.fillRect(positionX+(int)(squareWidth*4.3/10.0), 
				positionY+squareWidth/4, 
				squareWidth/3, (int) (squareWidth/3));*/
		g.fillRect(positionX+(int)(squareWidth*1./4.0), 
				positionY+(int)(squareWidth*1.5/5.0), 
				squareWidth/2, (int)(squareWidth/1.8));
		
	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub
		//System.out.println("girdi mi?");
		if(isBlack)
		{	System.out.println("girdi mi?");
			if((y == 1 && x == 0)||(y == -1 && x == 0)||(y == 0 && x == 1)||(y == 0 && x == -1)||
					   (x==1&&y==1)||(x==1&&y==-1)||(x==-1&&y==1)||(x==-1&&y==-1) )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if((y == 1 && x == 0)||(y == -1 && x == 0)||(y == 0 && x == 1)||(y == 0 && x == -1)||
					   (x==1&&y==1)||(x==1&&y==-1)||(x==-1&&y==1)||(x==-1&&y==-1) )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}

	@Override
	public boolean canCapture(int x, int y) {
		// TODO Auto-generated method stub
		if(isBlack)
		{
			if((y == 1 && x == 0)||(y == -1 && x == 0)||(y == 0 && x == 1)||(y == 0 && x == -1)||
					   (x==1&&y==1)||(x==1&&y==-1)||(x==-1&&y==1)||(x==-1&&y==-1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if((y == 1 && x == 0)||(y == -1 && x == 0)||(y == 0 && x == 1)||(y == 0 && x == -1)||
					   (x==1&&y==1)||(x==1&&y==-1)||(x==-1&&y==1)||(x==-1&&y==-1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	

}
