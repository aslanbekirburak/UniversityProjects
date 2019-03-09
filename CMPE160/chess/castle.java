package chess;
import java.awt.Color;
import java.awt.Graphics;

public class castle extends Piece{
	
	public castle(boolean isBlack)
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
		
		g.fillRect(positionX+(int)(squareWidth*1.0/4.0), 
				positionY+(int)(squareWidth*1.0/5.0), 
				squareWidth/2, squareWidth/5);
		g.fillRect(positionX+(int)(squareWidth*3.6/12.0), 
				positionY+squareWidth/4, 
				squareWidth/7, squareWidth/2);
		g.fillRect(positionX+(int)(squareWidth*7.0/12.0), 
				positionY+squareWidth/4, 
				squareWidth/7, squareWidth/2);
		g.fillRect(positionX+(int)(squareWidth*1.0/4.0), 
				positionY+(int)(squareWidth*3.0/5.0), 
				squareWidth/2, squareWidth/5);
		
	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("merhaba");
			if((y==0 && x< 7)||(x==0 && y<7))
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
		System.out.println("sieeee");
			if((y==0 && x< 7)||(x==0 && y<7))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		

	

}
