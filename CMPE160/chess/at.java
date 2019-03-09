package chess;
import java.awt.Color;
import java.awt.Graphics;

public class at extends Piece{
	
	public at(boolean isBlack)
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
		g.fillOval(positionX+(int)(squareWidth*5.07/12.0), 
				(int)(positionY+squareWidth/7.5), 
				(int)(squareWidth/6), (int)(squareWidth/6));
		g.fillOval(positionX+(int)(squareWidth*2.9/8.0), 
				positionY+squareWidth/4, 
				(int)(squareWidth/3.5), (int)(squareWidth/3.5));
		g.fillRect(positionX+(int)(squareWidth*4.3/10.0), 
				positionY+squareWidth/2, 
				squareWidth/8, (int) (squareWidth/3.5));
		g.fillRect(positionX+(int)(squareWidth*1.0/4.0), 
				positionY+(int)(squareWidth*3.0/5.0), 
				squareWidth/2, squareWidth/5);
		
	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("moveable");
			if(isBlack)
			
		{
			if((x==-1 && y==2 )||(x==-1 && y==-2 )||(x==1 && y==2 )||(x==1 && y==-2 )||
					(x==-2 && y==1 )||(x==-2 && y==-1 )&&(x==2 && y==1 )||(x==2 && y==-1))
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
			if((x==-1 && y==2 )||(x==-1 && y==-2 )||(x==1 && y==2 )||(x==1 && y==-2 )||
					(x==-2 && y==1 )||(x==-2 && y==-1 )&&(x==2 && y==1 )||(x==2 && y==-1))
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
			if((x==-1 && y==2 )||(x==-1 && y==-2 )||(x==1 && y==2 )||(x==1 && y==-2 )||
					(x==-2 && y==1 )||(x==-2 && y==-1 )&&(x==2 && y==1 )||(x==2 && y==-1))
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
			if((x==-1 && y==2 )||(x==-1 && y==-2 )||(x==1 && y==2 )||(x==1 && y==-2 )||
					(x==-2 && y==1 )||(x==-2 && y==-1 )&&(x==2 && y==1 )||(x==2 && y==-1))
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
