
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TableInCabin extends JPanel implements ActionListener {

	private JButton button[] = new JButton[8];

	private JButton floor1 = new JButton();
	private JButton floor2 = new JButton();
	private JButton state = new JButton();
	private ImageIcon Image1[] = new ImageIcon[9];
	
	private Cabin ca;
	public int i;
	int checkButtonPressed[] = new int[11];
	Vector<Point> containReInCabin = new Vector<Point>();

	public TableInCabin(Cabin cabin) {
		ca = cabin;
		
		Image1[0] = new ImageIcon("imageButtonIncabin/1_1.png");
		Image1[1] = new ImageIcon("imageButtonIncabin/2_1.png");
		Image1[2] = new ImageIcon("imageButtonIncabin/3_1.png");
		Image1[3] = new ImageIcon("imageButtonIncabin/4_1.png");
		Image1[4] = new ImageIcon("imageButtonIncabin/1.png");
		Image1[5] = new ImageIcon("imageButtonIncabin/2.png");
		Image1[6] = new ImageIcon("imageButtonIncabin/3.png");
		Image1[7] = new ImageIcon("imageButtonIncabin/4.png");
		
		for (int i = 0; i < 8; i++) {

			button[i] = new JButton();
			if (i < 4) {
				// String s = "imageButtonIncabin/" + (i + 1) + "_1.png";
				button[i] = new JButton("<html> <Font Size=7>" + (i + 1) + "</html>");
				button[i].setBackground(Color.gray);
			} else {
				// String s = "imageButtonIncabin/" + (i + 1 - 4) + "_1.png";
				button[i] = new JButton("<html> <Font Size=7>" + (i - 3) + "</html>");
				button[i].setBackground(Color.gray);
			}
			button[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int x, i;
					if ((false == false)) {
						for (i = 0; i < 8; i++) {

							if (e.getSource() == button[i] && i < 5) {
								//button[i].setIcon(Image1[i+4]);
								if (checkButtonPressed[i + 1] == 0) {
									checkButtonPressed[i + 1] = 1;
									//button[i].setIcon(Image1[i]);

								}
								int choose = Chocecabin(Display.getControl(0).getCabin(), i + 1, 1);

								if (choose != 0) {
									
									if (choose == 1) {
										Display.getControl(0).getCabin().getArrayConsist()
												.addElement(new Point(i + 1, 1));
										setFloor1(i + 1);
										
									} // else if (choose == -1)
										// containReInCabin.addElement(new Point(i+1, 1));

								}

							}

							// bat dau cai moi
							if (e.getSource() == button[i] && i >= 5) {
								// if (checkButtonPressed[i + 1 - 4] == 0) {
								// checkButtonPressed[i + 1 - 4] = 1;
								// button[i].setIcon(new ImageIcon("imageButtonIncabin/"+(i+1-4)+".png"));

								// }
								int choose = Chocecabin(Display.getControl(1).getCabin(), i + 1 - 4, 1);

								if (choose == 1) {
									Display.getControl(1).getCabin().getArrayConsist()
											.addElement(new Point(i + 1 - 4, 1));
									setFloor2(i + 1 - 4);
								} // else if (choose == -1)
									// containReInCabin.addElement(new Point(i+1-4, 1));

							}
						}

					}
				}
			});
		}
		this.setLayout(null);
		for (int i = 3; i > -1; i--) {

			button[i].setBounds(20, 100 * (4 - i), 50, 50);
			this.add(button[i]);
		}
		for (int i = 7; i >= 4; i--) {

			button[i].setBounds(80, 100 * (8 - i), 50, 50);
			this.add(button[i]);
		}

		floor1.setBounds(20, 15, 50, 50);
		floor2.setBounds(100, 15, 50, 50);
		floor1.setBackground(Color.blue);
		floor2.setBackground(Color.blue);
		floor1.setOpaque(true);
		floor2.setOpaque(true);

		this.add(floor1);
		this.add(floor2);

		this.setBackground(Color.white);
		// this.setBorder(BorderFactory.createTitledBorder("Cabin"));
		this.setPreferredSize(new Dimension(150, 540));

	}

	public void setState(ImageIcon image) {
		state.setIcon(image);
	}

	public void setFloor1(int x) {

		floor1.setText("<html><Size=5>" + x + "</html>");
	}

	public void setFloor2(int x) {

		floor2.setText("<html><Size=5>" + x + "</html>");
	}

	public static int Chocecabin(Cabin cabin1, int floorcall, int statuscall) {

		int a = cabin1.distance(cabin1.getStatus(), cabin1.getFloor(), floorcall, statuscall);

// 1 cabin 1 chay

		if (a <= 50)
			return -1;
		else
			return 1;

	}

	/*
	 * public void setColorButton(int a){
	 * 
	 * button[a-1].setIcon(new ImageIcon("imageButtonIncabin/"+(a)+"_1.png"));
	 * checkButtonPressed[a]=0; }
	 * 
	 * 
	 * 
	 * if(e.getSource()==quickClose){ if(this.ca.getIsClosing()) return;
	 * if(this.ca.getDirection().getStopState()==false){
	 * 
	 * if(ca.getIsUpdating()==false){ if(ca.getIsMoving()==false){ ca.setInterrup();
	 * 
	 * while(ca.getOpenAndCloseDoor().thread.isAlive()==true){ try {
	 * Thread.sleep(50); } catch (InterruptedException ex) {
	 * Logger.getLogger(TableInCabin.class.getName()).log(Level.SEVERE, null, ex); }
	 * 
	 * } ca.getOpenAndCloseDoor().setType(0); ca.getOpenAndCloseDoor().start(); } }
	 * } }else if(e.getSource()==quickOpen){ if(this.ca.getIsOpening()) return;
	 * if(this.ca.getDirection().getStopState()==false){
	 * 
	 * if(ca.getIsMoving()==false){ if(ca.getIsUpdating()==false){
	 * 
	 * if(2>=1){ ca.setInterrup();
	 * 
	 * while(ca.getOpenAndCloseDoor().thread.isAlive()==true){ try {
	 * Thread.sleep(50); } catch (InterruptedException ex) {
	 * Logger.getLogger(TableInCabin.class.getName()).log(Level.SEVERE, null, ex); }
	 * } ca.getOpenAndCloseDoor().setType(1); ca.getOpenAndCloseDoor().start(); } }
	 * } } }
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * quickClose.setIcon(new ImageIcon("imageButtonIncabin/close.jpg"));
	 * quickOpen.setIcon(new ImageIcon("imageButtonIncabin/open.jpg"));
	 * quickOpen.setBounds(10,425,60,60); quickClose.setBounds(80,425,60,60);
	 * quickClose.addActionListener(this); quickOpen.addActionListener(this);
	 * 
	 * this.add(quickClose); this.add(quickOpen);
	 */

}
