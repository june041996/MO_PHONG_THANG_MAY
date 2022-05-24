
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Cabin extends JLayeredPane {

	private JLabel labelCabin;

	private int floor = 0;
	private int status = 0;

	private FloorCall tableInFloor[] = new FloorCall[4];// bang hien thi vi tri cabin va chieu di chuyen
	private TableInCabin tabelInCabin = new TableInCabin(this);// bang trong cabin
	private ImageIcon imageState[] = new ImageIcon[3];
	private Vector<Point> arrayConsist = new Vector<Point>();
	private int speed = 13;

	private boolean interrup = false;

	private boolean isMoving = false;

	private boolean positionAvalbel[] = new boolean[12];
	private int nextFloor = 0;
	private int nextState = 0;

	private boolean signalStop = false;
	private boolean isUpdating = false;
	private boolean updateAgain = false;
	private JLabel leftDoor2[] = new JLabel[4];
	private JLabel rightDoor2[] = new JLabel[4];
	private int weigh = 0;
	private boolean isClosing = false, isOpening = false;

	public Cabin() {

		/*
		 * anh nen cabin labelCabin=new JLabel(imageCabin);
		 * labelCabin.setBounds(0,0,180,260); this.add(labelCabin,new Integer(0));
		 */

		// vi tri 2 cua
		/*
		 * rightDoor=new JLabel(imageDoorRight); leftDoor=new JLabel(imageDoorLeft);
		 * leftDoor.setBounds(0,0,90,Display.H); rightDoor.setBounds(90,0,90,Display.H);
		 * this.add(leftDoor,new Integer(15)); this.add(rightDoor,new Integer(15));
		 */
		tabelInCabin.setFloor1(1);
		tabelInCabin.setState(imageState[0]);
		this.setFloor(1);

		for (int i = 0; i < 4; i++) {
			tableInFloor[i] = new FloorCall(0);
			tableInFloor[i].button[2].setText("<html> <Font Size=4>" + this.getFloor() + "</html>");

		}
		imageState[0] = new ImageIcon("standing.png");
		imageState[1] = new ImageIcon("anhnen/up.gif");
		imageState[2] = new ImageIcon("anhnen/down.gif");
		this.setPreferredSize(new Dimension(Display.W, Display.H));
		// openAndCloseDoor=new OpenAndCloseDoor(this);

		// thiet lap ca vi tri khong the cho nguoi dung

	}

	// nen thang may 100: giam hang
	public void setDoor2(JLayeredPane layer) {

		for (int i = 0; i < 2; i++) {
			//leftDoor2[i] = new JLabel(new ImageIcon("picture/doorleft1.png"));
			//rightDoor2[i] = new JLabel(new ImageIcon("picture/doorright1.png"));
			JLabel lab1 = new JLabel();
			JLabel lab2 = new JLabel();

			lab2.setOpaque(true);
			lab2.setBackground(Color.gray);
			lab1.setOpaque(true);
			lab1.setBackground(Color.gray);

			lab1.setBounds(0	,  Display.W*i*2 , Display.W, Display.H);
			
			lab2.setBounds(300 	,  Display.W*i*2 , Display.W	, Display.H);

			// leftDoor2[i].setBounds(this.getLocation().x
			// ,100+Display.HEIGHTFLOOR*(3-i),90,Display.H);
			// rightDoor2[i].setBounds(this.getLocation().x+90
			// ,100+Display.HEIGHTFLOOR*(3-i),90,Display.H);

			// layer.add(leftDoor2[i],new Integer(5));
			// layer.add(rightDoor2[i],new Integer(5));
			layer.add(lab1);
			layer.add(lab2);
		}
	}

	public void setFloor(int x) {
		this.floor = x;
	}

	public int getFloor() {
		return this.floor;
	}

	public Vector<Point> getArrayConsist() {
		return this.arrayConsist;
	}

	public void setArrayConsist(Vector<Point> vt) {
		this.arrayConsist = vt;
	}

	public FloorCall getTableInFloor(int i) {
		return this.tableInFloor[i];
	}

	public TableInCabin getTableInCabin() {
		return this.tabelInCabin;
	}

	/*
	 * public OpenAndCloseDoor getOpenAndCloseDoor(){ return this.openAndCloseDoor;
	 * }
	 */
	public void setIsMoving(boolean b) {
		this.isMoving = b;
	}

	public boolean getIsMoving() {
		return this.isMoving;
	}

	public void setNextFloor(int a) {
		this.nextFloor = a;
	}

	public int getNextFloor() {
		return this.nextFloor;
	}

	public void setNextState(int a) {
		this.nextState = a;
	}

	public int getNextState() {
		return this.nextState;
	}

	// phuong thuc di chuyen cabin
	public void move(int first, int last, int actualDistance) {
		this.isMoving = true;
		int distance = last - first;
		int lenght = arrayConsist.size();
		int lenght1 = this.tabelInCabin.containReInCabin.size();
		int interupt = 0;
		int distanBefor = actualDistance;
		int tesk123 = this.nextFloor;
		int teskstate = this.nextState;
		Point p1 = new Point(this.nextFloor, this.nextState);
		if (distance > 0) {// di xuong
			this.setStatus(-1);
			this.setTabelInFloor_State(-1);
			int d = this.getLocation().x;
			int k = this.getLocation().y;
			int i = 0;
			for (i = 0; i <= distance; i++) {
				this.setLocation(d, k + i);
				if (distance - i < 50)
					try {
						Thread.sleep(60 - (distance - i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				else
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				this.setFloor((4 - (int) (0.9 + (float) (this.getLocation().y ) / Display.H)));
				if (this.signalStop == true) {

				} else {
					this.setStatus(-1);
					int sta = this.getStatus();
					int floornow = this.getFloor();
					int change;
					int dis0 = -1;
					int l = arrayConsist.size();
					// chon lai tang de den trong qua trinh di chuyen
					if (l > lenght) {
						int dsf = actualDistance - i;
						for (change = lenght; change < l; change++) {
							dis0 = distance(sta, floornow, arrayConsist.elementAt(change).x,
									arrayConsist.elementAt(change).y);
							if (dis0 >= 50 && dis0 < dsf) {
								actualDistance = dis0;
								this.nextFloor = arrayConsist.elementAt(change).x;
								this.nextState = arrayConsist.elementAt(change).y;
								dsf = dis0;
							}
						}
						actualDistance = dsf;
					}
					if (this.nextFloor == tesk123) {
						int l1 = this.tabelInCabin.containReInCabin.size();
						if (l1 > lenght1) {
							int df = actualDistance - i;
							for (change = lenght1; change < l1; change++) {
								dis0 = distance(this.getStatus(), this.getFloor(),
										tabelInCabin.containReInCabin.elementAt(change).x,
										tabelInCabin.containReInCabin.elementAt(change).y);
								if (dis0 >= 50 && dis0 < df) {
									this.nextFloor = tabelInCabin.containReInCabin.elementAt(change).x;
									this.nextState = tabelInCabin.containReInCabin.elementAt(change).y;
									df = dis0;
								}
							}
							actualDistance = df;
						}
						lenght1 = l1;
						if (tesk123 != this.nextFloor) {
							interupt = 1;
						}
						if (interupt == 1) {
							if (distance - i > 50) {
								move(this.getLocation().y,  Display.H * (4 - this.nextFloor), actualDistance);
								break;
							} else {
								this.nextFloor = tesk123;
								this.nextState = teskstate;
							}
						}
					} else {
						int l1 = this.tabelInCabin.containReInCabin.size();
						int sfs = this.nextFloor;
						if (l1 > lenght1) {
							int df = actualDistance;
							for (change = lenght1; change < l1; change++) {
								dis0 = distance(this.getStatus(), this.getFloor(),
										tabelInCabin.containReInCabin.elementAt(change).x,
										tabelInCabin.containReInCabin.elementAt(change).y);
								if (dis0 >= 50 && dis0 < df) {
									this.nextFloor = tabelInCabin.containReInCabin.elementAt(change).x;
									this.nextState = tabelInCabin.containReInCabin.elementAt(change).y;
									df = dis0;
								}
							}
							actualDistance = df;
						}
						lenght1 = l1;
						if (sfs == this.nextFloor) {
							interupt = 1;
						}
						if (interupt == 1) {
							move(this.getLocation().y,  Display.H * (4 - this.nextFloor), actualDistance);
							break;
						}
					}
				}
			}
			// cap nhat lai trang thai
			if (this.tabelInCabin.containReInCabin.size() != 0)
				this.setStatus(-1);
			else {
				for (int j = 0; j < this.arrayConsist.size(); j++) {
					if (this.arrayConsist.elementAt(j).y == -1)
						this.setStatus(-1);
					break;
				}
			}

		} else {// di len
			this.setStatus(1);
			if (distance != 0)
				this.setTabelInFloor_State(1);
			int d = this.getLocation().x;
			int k = this.getLocation().y;
			int i = 0;
			distance = -distance;
			for (i = 0; i <= distance; i++) {
				this.setLocation(d, k - i);
				if (distance - i < 50)
					try {
						Thread.sleep(60 - (distance - i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				this.setFloor(4 - (this.getLocation().y ) / Display.H);
				if (this.signalStop == true) {

				} else {
					int sta = this.getStatus();
					int floornow = this.getFloor();
					int change;
					int dis0 = -1;
					int l = arrayConsist.size();
					// chon lai tang de den trong qua trinh di chuyen
					if (l > lenght) {
						for (change = lenght; change < l; change++) {
							dis0 = distance(sta, floornow, arrayConsist.elementAt(change).x,
									arrayConsist.elementAt(change).y);
							if (dis0 >= 50 && dis0 < actualDistance - i) {
								actualDistance = dis0;
								this.nextFloor = arrayConsist.elementAt(change).x;
								this.nextState = arrayConsist.elementAt(change).y;
							}
						}
					}
					int l1 = this.tabelInCabin.containReInCabin.size();
					if (l1 > lenght1) {
						int df = actualDistance - i;
						for (change = lenght1; change < l1; change++) {
							dis0 = distance(this.getStatus(), this.getFloor(),
									tabelInCabin.containReInCabin.elementAt(change).x,
									tabelInCabin.containReInCabin.elementAt(change).y);
							if (dis0 >= 50 && dis0 < df) {
								this.nextFloor = tabelInCabin.containReInCabin.elementAt(change).x;
								this.nextState = tabelInCabin.containReInCabin.elementAt(change).y;
								df = dis0;
							}
						}

						actualDistance = df;
					}
					lenght1 = l1;
					if (tesk123 != this.nextFloor) {
						interupt = 1;
					}

					if (distanBefor != actualDistance)
						interupt = 1;
					if (interupt == 1) {

						if (distance - i > 50) {
							move(this.getLocation().y,  + Display.H * (4 - this.nextFloor), actualDistance);
							break;
						} else {
							this.nextFloor = tesk123;
							this.nextState = teskstate;
						}
					}

				}
			}
			// cap nhat lai trang thai
			if (this.tabelInCabin.containReInCabin.size() != 0)
				this.setStatus(1);
			for (int j = 0; j < this.arrayConsist.size(); j++) {
				if (this.arrayConsist.elementAt(j).y == 1)
					this.setStatus(1);
				break;
			}
		}
		this.isMoving = false;
	}

	public void setStatus(int i) {
		this.status = i;
	}

	public int getStatus() {
		return this.status;
	}

	// set mui ten
	public void setTabelInFloor_State(int a) {
		if (a == 0) {
			for (int i = 0; i < 4; i++)
				tableInFloor[i].button[1].setIcon(imageState[0]);
			tabelInCabin.setState(imageState[0]);

		} else if (a == 1) {
			for (int i = 0; i < 4; i++)
				tableInFloor[i].button[1].setIcon(imageState[1]);
			tabelInCabin.setState(imageState[1]);

		} else {
			for (int i = 0; i < 4; i++)
				tableInFloor[i].button[1].setIcon(imageState[2]);
			tabelInCabin.setState(imageState[2]);

		}
	}

	// tinh khoang cach giua cabin va tang goi state huong
	public int distance(int state, int floor, int floorCall, int stateCall) {
		if (state == 1) {// di len
			if (stateCall == -1) {// goi xuong
				if (floor >= floorCall)
					return -1;
				else
					return (+ Display.H * (4 - floor) + Display.H * (4 - floorCall));
			} else {// goi len
				if (floorCall < floor)
					return -1;
				else
					return (Display.H * (floorCall - floor));
			}
		} else if (state == -1) {// di xuong
			if (stateCall == 1) {// goi len

				if (floorCall >= floor)
					return -1;
				else {
					return (Display.H * (floor - 1) + Display.H * (floorCall - 1));
				}

			} else {// goi xuong
				if (floorCall > floor)
					return -1;
				else
					return Display.H * (floor - floorCall);
			}
		} else {// state==0

			if (stateCall == 1) {// goi di len
				if (floorCall >= floor)
					return Display.H * (floorCall - floor);
				else
					return (Display.H) * (floor - 1) + Display.H * (floorCall - 1);

			} else {// goi di xuong
				if (floorCall <= floor)
					return Display.H * (floor - floorCall);
				else
					return Display.H * (4 - floor) + Display.H * (4 - floorCall);

			}
		}

	}

	public void setSpeed(int s) {
		this.speed = s;
	}

	/*
	 * public boolean isActive(){ return direction.confirmActive(); }
	 */

	public void setInterrup() {
		interrup = true;
	}

	public int getSpeed() {
		return this.speed;
	}

	// phuong thuc cap nhan nguoi
	public void updatePerson(boolean emergencyTreatment) {
		// tat den bao tren tang
		for (int i = 0; i < this.arrayConsist.size(); i++) {
			if (this.arrayConsist.elementAt(i).x == this.getFloor() && this.arrayConsist.elementAt(i).y == nextState) {
				this.arrayConsist.removeElementAt(i);
			}
			;

		}
		if (this.getFloor() == 1)
			this.nextState = 1;
		if (this.getFloor() == 4)
			this.nextState = -1;
		// if(this.direction.getStopState()==false)
		Display.setStatusFoorCall(this.getFloor()/* this.nextFloor */, this.nextState);

		// dau tien goi ham mo cua;
		// this.openAndCloseDoor.setType(1);
		// this.openAndCloseDoor.start();
		// cho cho mo cua xong moi bat dau cap nhat
		/*
		 * while(this.openAndCloseDoor.thread.isAlive()==true||this.interrup==true){ try
		 * { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();
		 * } }
		 */

		// thiet lap bien updating = true
		/*
		 * this.setIsUpdating(true);
		 * 
		 * 
		 * 
		 * this.setUpdateAgain(false);
		 */
	}

	// vong for duyet khi khong co dung khan cap
	/*
	 * public void setIsUpdating(boolean b) { this.isUpdating=b; } public boolean
	 * getIsUpdating(){ return this.isUpdating; } public void setUpdateAgain(boolean
	 * b){ this.updateAgain=b; } public boolean getUpdateAgain(){ return
	 * this.updateAgain;
	 * 
	 * }
	 */
	// lop dong mo cua cabin
	/*
	 * class OpenAndCloseDoor implements Runnable{ private Cabin cabin; Thread
	 * thread=null; int type;//xac dinh dong hay mo cua 1 la mo ,so khac la dong
	 * public OpenAndCloseDoor(Cabin ca){ cabin=ca; } public void start(){
	 * thread=new Thread(this); thread.start(); } public void stop(){ thread.stop();
	 * } public void setType(int a){ this.type=a; } public void run(){ if(type==1){
	 * openCabin(); stop(); }else{ closeCabin(); stop(); } } }
	 */
	// phuong thuc dong cua cabin
	public void closeCabin() {
		/*
		 * this.isOpenned=false; this.isClosed=false; int
		 * move1=leftDoor.getLocation().x; if(interrup) interrup=false; int i=0;
		 * 
		 * this.isClosing=true; for( i=move1;i<=0;i++){ try { Thread.sleep(50); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 * Setlocaldoor(i,leftDoor.getLocation().y+5); if(interrup) break;
		 * if(this.getUpdateAgain()==true){ break; }
		 * 
		 * } this.isClosing=false; if(i==1) this.isClosed=true;
		 */
	}

	// phuong thuc mo cua cabin
	public void openCabin() {
		/*
		 * this.isOpenned=false; this.isClosed=false; int
		 * move1=leftDoor.getLocation().x; if(interrup) interrup=false; int i=0;
		 * this.isOpening=true; for( i=move1;i>-80;i--){ try { Thread.sleep(50); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 * Setlocaldoor(i,leftDoor.getLocation().y+5); if(interrup) break; }
		 * this.isOpening=false; if(i==-80) this.isOpenned=true;
		 */
	}

	// phuong thuc lap vi tri cua cho cabin
	public void Setlocaldoor(int x, int y) {
		/*
		 * int oldLeft=leftDoor.getLocation().x; int oldRight=rightDoor.getLocation().x;
		 * int a=this.getLocation().x; int b=this.getLocation().y; {
		 * leftDoor2[this.getFloor()-1].setLocation(a+x,b+y-5);
		 * rightDoor2[this.getFloor()-1].setLocation(oldLeft-x+oldRight+a,b+y-5);
		 * leftDoor.setBounds(x,y-5,90,260);
		 * rightDoor.setBounds(oldLeft-x+oldRight,y-5,90,260); }
		 */
	}
}
