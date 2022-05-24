import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FloorCall extends JPanel {

	JButton button[] = new JButton[3];
	JLabel show = new JLabel();
	ImageIcon upImage = new ImageIcon("anhnen/len.png");
	ImageIcon up1Image = new ImageIcon("anhnen/len1.png");
	ImageIcon down1Image = new ImageIcon("anhnen/xuong1.png");
	ImageIcon downImage = new ImageIcon("anhnen/xuong.png");
	private int floorShow = 1;
	public static Vector<Point> arraytemporary = new Vector<Point>();
	static int checkButtonPressed[] = new int[20];

	public FloorCall(int floor) {

		if (floor == 1) {// thiet lap bang goi tang 1
			setFloor(1);
			this.setLayout(new GridLayout(1, 1));
			for (int i = 0; i < 2; i++) {
				if (i != 0) {
					button[i] = new JButton();
					this.add(button[i]);
				}
			}
			button[1].setIcon(up1Image);
			//button[2].setIcon(new ImageIcon("anhnen/khoa.png"));
			button[1].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (checkButtonPressed[1] == 0) {
						checkButtonPressed[1] = 1;
						int choose = Choosecabin(Display.getControl(0).getCabin(), Display.getControl(1).getCabin(),
								getFloor(), 1);
						if (choose != 0) {
							button[1].setIcon(upImage);
							if (choose == 1) {
								Display.getControl(0).getCabin().getArrayConsist().addElement(new Point(getFloor(), 1));
							} else if (choose == 2) {
								Display.getControl(1).getCabin().getArrayConsist().addElement(new Point(getFloor(), 1));
							} else if (choose == -1)
								arraytemporary.addElement(new Point(getFloor(), 1));
						}
					}
				}
			});
		} else {
			if (floor == 4) {// thiet lap bang goi tang 10
				setFloor(4);
				this.setLayout(new GridLayout(1, 1));

				for (int i = 0; i < 2; i++) {
					if (i != 0) {
						button[i] = new JButton();
						this.add(button[i]);
					}
				}
				button[1].setIcon(down1Image);
				button[1].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (checkButtonPressed[4] == 0) {
							checkButtonPressed[4] = 1;
							int choose = Choosecabin(Display.getControl(0).getCabin(), Display.getControl(1).getCabin(),
									getFloor(), -1);
							if (choose != 0) {
								button[1].setIcon(downImage);
								if (choose == 1)
									Display.getControl(0).getCabin().getArrayConsist()
											.addElement(new Point(getFloor(), -1));
								else if (choose == 2)
									Display.getControl(1).getCabin().getArrayConsist()
											.addElement(new Point(getFloor(), -1));
								else if (choose == -1)
									arraytemporary.addElement(new Point(getFloor(), -1));
							}
						}
					}
				});
			} else {
				if (floor == 0) {// 1-3
					this.setLayout(new GridLayout(2, 1));
					button[1] = new JButton();
					button[2] = new JButton();
					this.add(button[1]);
					this.add(button[2]);
				} else {// thiet lap cac tang tu 2 den 9
					this.setLayout(new GridLayout(2, 1));
					// TODO Auto-generated constructor stub

					setFloor(floor);
					for (int i = 0; i < 3; i++) {
						button[i] = new JButton();
					}
					button[1].setIcon(up1Image);
					button[2].setIcon(down1Image);

					this.add(button[1]);
					this.add(button[2]);
					button[1].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							// TODO Auto-generated method stub
							if (checkButtonPressed[getFloor()] == 0) {
								checkButtonPressed[getFloor()] = 1;

								int choose = Choosecabin(Display.getControl(0).getCabin(),
										Display.getControl(1).getCabin(), getFloor(), 1);
								if (choose != 0) {
									button[1].setIcon(upImage);
									if (choose == 1) {
										Display.getControl(0).getCabin().getArrayConsist()
												.addElement(new Point(getFloor(), 1));
									} else if (choose == 2) {
										Display.getControl(1).getCabin().getArrayConsist()
												.addElement(new Point(getFloor(), 1));
									} else if (choose == -1)
										arraytemporary.addElement(new Point(getFloor(), 1));

								}
							}
						}
					});
					button[2].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if (checkButtonPressed[4 + getFloor()] == 0) {
								checkButtonPressed[4 + getFloor()] = 1;

								int choose = Choosecabin(Display.getControl(0).getCabin(),
										Display.getControl(1).getCabin(), getFloor(), -1);
								if (choose != 0) {
									button[2].setIcon(downImage);
									if (choose == 1) {
										Display.getControl(0).getCabin().getArrayConsist()
												.addElement(new Point(getFloor(), -1));
									} else if (choose == 2) {
										Display.getControl(1).getCabin().getArrayConsist()
												.addElement(new Point(getFloor(), -1));
									} else if (choose == -1)
										arraytemporary.addElement(new Point(getFloor(), -1));
								}
							}
						}
					});

				}
			}
		}
	}

	public void setFloor(int k) {
		this.floorShow = k;
	}

	public int getFloor() {
		return this.floorShow;
	}

	public static int Choosecabin(Cabin cabin1, Cabin cabin2, int floorcall, int statuscall) {

		int a = cabin1.distance(cabin1.getStatus(), cabin1.getFloor(), floorcall, statuscall);
		int b = cabin2.distance(cabin2.getStatus(), cabin2.getFloor(), floorcall, statuscall);

// 1 cabin 1 chay
		if (a == 0 && cabin1.getIsMoving() == false)
			return 1;
		if (b == 0 && cabin2.getIsMoving() == false)
			return 2;
		if (a < 50 && b < 50)
			return -1;
		else if (a < 50)
			return 2;
		else if (b < 50)
			return 1;
		else if (a < b)
			return 1;
		else
			return 2;

	}

	public static void updateFloorCall() {
		Vector<Point> tempt = new Vector<Point>();
		int size = arraytemporary.size();
		for (int i = 0; i < size; i++) {

			if (/* Display.getControl(0).getCabin() */Choosecabin(Display.getControl(0).getCabin(),
					Display.getControl(1).getCabin(), arraytemporary.elementAt(i).x,
					arraytemporary.elementAt(i).y) == 1) {
				Display.getControl(0).getCabin().getArrayConsist()
						.addElement(new Point(arraytemporary.elementAt(i).x, arraytemporary.elementAt(i).y));
				// arraytemporary.removeElementAt(i);
			} else {
				if (/* Display.getControl(1).getCabin()== */Choosecabin(Display.getControl(0).getCabin(),
						Display.getControl(1).getCabin(), arraytemporary.elementAt(i).x,
						arraytemporary.elementAt(i).y) == 2) {
					Display.getControl(1).getCabin().getArrayConsist()
							.addElement(new Point(arraytemporary.elementAt(i).x, arraytemporary.elementAt(i).y));
					// arraytemporary.removeElementAt(i);
				} else
					tempt.addElement(arraytemporary.elementAt(i));
			}
		}
		arraytemporary = tempt;
	}

}
