package chess;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class ChessFrame extends JFrame implements MouseListener {
	public static final int SQUARE_WIDTH = 45;
	public static final int BOARD_MARGIN = 50;
	int selectedSquareX = -1;
	int selectedSquareY = -1;
	Piece pieces[][] = new Piece[8][8];
	int counter = 0;

	public ChessFrame() {

		initializeChessBoard();
		setTitle("Chess Game");
		// let the screen size fit the board size
		setSize(SQUARE_WIDTH * 8 + BOARD_MARGIN * 2, SQUARE_WIDTH * 8 + BOARD_MARGIN * 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);

	}

	public void initializeChessBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == 1) {
					pieces[i][j] = new Pawn(true);
				} else if (j == 6) {
					pieces[i][j] = new Pawn(false);
				} else {
					pieces[i][j] = null;
				}
			}
		}
		pieces[0][0] = new castle(true);
		pieces[7][0] = new castle(true);
		pieces[0][7] = new castle(false);
		pieces[7][7] = new castle(false);
		pieces[2][0] = new fil(true);
		pieces[5][0] = new fil(true);
		pieces[5][7] = new fil(false);
		pieces[2][7] = new fil(false);
		pieces[1][0] = new at(true);
		pieces[6][0] = new at(true);
		pieces[6][7] = new at(false);
		pieces[1][7] = new at(false);
		pieces[3][0] = new sah(true);
		pieces[3][7] = new sah(false);
		pieces[4][0] = new vezir(true);
		pieces[4][7] = new vezir(false);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		// print the board 's lines to show squares
		for (int i = 0; i <= 8; i++) {
			g.drawLine(BOARD_MARGIN, BOARD_MARGIN + (i) * SQUARE_WIDTH, BOARD_MARGIN + 8 * SQUARE_WIDTH,
					BOARD_MARGIN + (i) * SQUARE_WIDTH);
			g.drawLine(BOARD_MARGIN + (i) * SQUARE_WIDTH, BOARD_MARGIN, BOARD_MARGIN + (i) * SQUARE_WIDTH,
					BOARD_MARGIN + 8 * SQUARE_WIDTH);
		}
		// print the pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] != null) {
					pieces[i][j].drawYourself(g, i * SQUARE_WIDTH + BOARD_MARGIN, j * SQUARE_WIDTH + BOARD_MARGIN,
							SQUARE_WIDTH);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Clicked");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Pressed");
		// calculate which square is selected
		selectedSquareX = (e.getX() - BOARD_MARGIN) / SQUARE_WIDTH;
		selectedSquareY = (e.getY() - BOARD_MARGIN) / SQUARE_WIDTH;
		System.out.println(selectedSquareX + "," + selectedSquareY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Released");
		// calculate which square is targeted

		int targetSquareX = (e.getX() - BOARD_MARGIN) / SQUARE_WIDTH;
		int targetSquareY = (e.getY() - BOARD_MARGIN) / SQUARE_WIDTH;
		System.out.println(targetSquareX + "," + targetSquareY + "\n");

		// if these are inside the board
		if (selectedSquareX >= 0 && selectedSquareY >= 0 && selectedSquareX < 8 && selectedSquareY < 8
				&& targetSquareX >= 0 && targetSquareY >= 0 && targetSquareX < 8 && targetSquareY < 8) {
			System.out.println("inside");
			if (pieces[selectedSquareX][selectedSquareY] != null && user(targetSquareX, targetSquareY) 
					&& pieces[selectedSquareX][selectedSquareY] instanceof at){
				System.out.println("selected");
				int diffX = targetSquareX - selectedSquareX;
				int diffY = targetSquareY - selectedSquareY;
				if (pieces[targetSquareX][targetSquareY] != null ) {
					System.out.println("a target");
					if ((pieces[selectedSquareX][selectedSquareY].canCapture(diffX, diffY))
							&& (pieces[selectedSquareX][selectedSquareY].isBlack != pieces[targetSquareX][targetSquareY].isBlack)
							)
							{
						counter++;
						System.out.println("can capture");
						pieces[targetSquareX][targetSquareY] = pieces[selectedSquareX][selectedSquareY];
						pieces[selectedSquareX][selectedSquareY] = null;
					}
				} else {
					System.out.println("no target");
					if (pieces[selectedSquareX][selectedSquareY].canMove(diffX, diffY) ) {
						counter++;
						System.out.println("can move");
						pieces[targetSquareX][targetSquareY] = pieces[selectedSquareX][selectedSquareY];
						pieces[selectedSquareX][selectedSquareY] = null;
					}
				}
			}else if(pieces[selectedSquareX][selectedSquareY] != null && user(targetSquareX, targetSquareY)){
			int diffX = targetSquareX - selectedSquareX;
			int diffY = targetSquareY - selectedSquareY;
			if (pieces[targetSquareX][targetSquareY] != null ) {
				System.out.println("a target");
				if ((pieces[selectedSquareX][selectedSquareY].canCapture(diffX, diffY))
						&& (pieces[selectedSquareX][selectedSquareY].isBlack != pieces[targetSquareX][targetSquareY].isBlack)
						&& (jumpCheck(targetSquareX,targetSquareY)))
						{
					counter++;
					System.out.println("can capture");
					pieces[targetSquareX][targetSquareY] = pieces[selectedSquareX][selectedSquareY];
					pieces[selectedSquareX][selectedSquareY] = null;
				}
			} else {
				System.out.println("no target");
				if (pieces[selectedSquareX][selectedSquareY].canMove(diffX, diffY) && jumpCheck(targetSquareX,targetSquareY)) {
					counter++;
					System.out.println("can move");
					pieces[targetSquareX][targetSquareY] = pieces[selectedSquareX][selectedSquareY];
					pieces[selectedSquareX][selectedSquareY] = null;
				}
			}
		}
	}
			

		repaint();
	}

	public boolean user(int targetSquareX, int targetSquareY) {
		return (counter % 2 == 0 && pieces[selectedSquareX][selectedSquareY].isBlack == false)
				|| (counter % 2 == 1 && pieces[selectedSquareX][selectedSquareY].isBlack == true);
	}

		public boolean jumpCheck(int targetSquareX, int targetSquareY) {

		if (pieces[selectedSquareX][selectedSquareY] instanceof Pawn) {
			System.out.println("piyon seçildi :"+selectedSquareX+","+selectedSquareY + " hedef :" + targetSquareX + "," +targetSquareY);
			return straightCheck(targetSquareX, targetSquareY);
		}  if (pieces[selectedSquareX][selectedSquareY] instanceof vezir) {
			return straightCheck(targetSquareX, targetSquareY) && crossCheck(targetSquareX, targetSquareY);
		} else if (pieces[selectedSquareX][selectedSquareY] instanceof fil) {
			return crossCheck(targetSquareX, targetSquareY);
		} else if(pieces[selectedSquareX][selectedSquareY] instanceof castle){
			return straightCheck(targetSquareX, targetSquareY);
		}if (pieces[selectedSquareX][selectedSquareY] instanceof sah) {
			return straightCheck(targetSquareX, targetSquareY) && crossCheck(targetSquareX, targetSquareY);
		}
		else
			return false;
	}

	public boolean straightCheck(int targetSquareX, int targetSquareY) {
		boolean result = true;
		if (selectedSquareY == targetSquareY) {
			if(selectedSquareX > targetSquareX) { //sola gidis
				for (int i = Math.min(selectedSquareX, targetSquareX); i < Math.max(selectedSquareX, targetSquareX); i++) {
					if (pieces[i][targetSquareY] != null) {
						result = false;
					}
				}
			}else { //saga gidis
				for (int i = Math.max(selectedSquareX, targetSquareX); i > Math.min(selectedSquareX, targetSquareX); i--) {
					if (pieces[i][targetSquareY] != null) {
						result = false;
					}
				}
			}
			
		} else if (selectedSquareX == targetSquareX ) {
			if(selectedSquareY > targetSquareY) { // beyazlar icin kontrol 
				for (int j = Math.min(selectedSquareY, targetSquareY); j < Math.max(selectedSquareY, targetSquareY); j++) {
					//eskiden 2 renk tas icin de bu for dongusu vardi ancak siyahlar ustte oldugu icin Math.min(selectedSquareY, targetSquareY)-> seçilen taþý veriyordu 
					//ve pieces[selectedSquareX][j] nesnesi dolu geliyordu o yüzden siyahlarin hareketi engelleniyordu.
					if (pieces[selectedSquareX][j] != null) {
						result = false;
					}	
				}
			}else { //siyahlar icin kontrol 
				for (int j = Math.max(selectedSquareY, targetSquareY); j > Math.min(selectedSquareY, targetSquareY); j--) {
					if (pieces[selectedSquareX][j] != null) {
						result = false;
					}	
				}
			}
			
		}
		return result;

	}

	public boolean crossCheck(int targetSquareX, int targetSquareY) {
		boolean result = true;
		if(selectedSquareX > targetSquareX) { //sola dogru hareket orn: 3,5 -> 1,3 veya 3,5->1,7
			if(selectedSquareY > targetSquareY) { // sola yukarý hareket orn: 3,5 -> 1,3 arada sadece 2,4 var o kontrol edilecek
				int count = selectedSquareX - targetSquareX -1; //count=1
				for(int i = 1; i<=count; i++){
					if(pieces[selectedSquareX-i][selectedSquareY-i] != null){ //selectedSquareX-count=2 selectedSquareY-count=4
						result = false;
					}
				}
			}else { //sola asagi hareket orn: 3,5 -> 1,7
				int count = selectedSquareX - targetSquareX -1; //count=1
				for(int i=1; i<=count; i++){
					if(pieces[selectedSquareX-i][selectedSquareY+i] != null){ //selectedSquareX-count=2 selectedSquareY-count=6
						result = false;
					}
				}
			}
		}else{ //saga dogru hareket
			if(selectedSquareY > targetSquareY) { //saga yukarý hareket orn: 3,5->6,2 arada olanlar: (4,4), (5,3)
				int count = targetSquareX - selectedSquareX -1; //count= 2
				for(int i=1; i<=count; i++){
					if(pieces[selectedSquareX+i][selectedSquareY-i] != null){
						result = false;
					}
				}
			}else{ //saga asagi hareket orn:1,3 -> 5,7 arada olanlar : (2,4) (3,5) (4,6) 
				int count = targetSquareX - selectedSquareX -1; //count= 2
				for(int i=1; i<=count; i++){
					if(pieces[selectedSquareX+i][selectedSquareY+i] != null){
						result = false;
					}
				}
			}
		}
		
		return result;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Entered");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Exited");

	}
}
