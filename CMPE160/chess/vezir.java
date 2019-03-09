package chess;
import java.awt.Color;
import java.awt.Graphics;

public class vezir extends Piece{

	public vezir(boolean isBlack)
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
		g.fillRect(positionX+(int)(squareWidth*1.0/4.0), 
				positionY+(int)(squareWidth*2.0/7.0), 
				squareWidth/2, squareWidth/8);		
		g.fillRect(positionX+(int)(squareWidth*5.0/12.0), 
				positionY+(int)(squareWidth/3), 
				squareWidth/5, squareWidth/2);
		g.fillRect(positionX+(int)(squareWidth*1./4.0), 
				positionY+(int)(squareWidth*4.0/5.0), 
				squareWidth/2, squareWidth/7);

	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub

		if((y==0 && x< 7)||(x==0 && y<7)||(x==-y) || (x==y) || (-x==y) ||(-x==-y))
		{
			return true;
		}
		else
		{
			return false;
		}


	}

	@Override
	public boolean canCapture(int x, int y) {
		// TODO Auto-generated method stub
		
		 
			if((y==0 && x< 7)||(x==0 && y<7)||(x==-y) || (x==y) || (-x==y) ||(-x==-y))
			{
				return true;
			}
			else
			{
				return false;
			}
		
		 
		}


}
