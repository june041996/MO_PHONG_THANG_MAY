import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import sun.audio.AudioStream;

public class Control implements Runnable {
	private Thread thread = null;
	private Cabin cabin;

	public Control(Cabin ca) {
		// TODO Auto-generated constructor stub
		cabin = ca;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		cabinAcction();
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		thread = null;
	}

	public Thread getThread() {
		return this.thread;
	}

	public void setCabin(Cabin ca) {
		this.cabin = ca;
	}

	public Cabin getCabin() {
		return this.cabin;
	}

	private void cabinAcction() {
		while (true) {
			FloorCall.updateFloorCall();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (cabin.getArrayConsist().size() != 0 || cabin.getTableInCabin().containReInCabin.size() != 0) {
				/*
				 * int run=0; for( run=0;run<cabin.getArrayConsist().size();run++){
				 * if(cabin.getStatus()==cabin.getArrayConsist().elementAt(run).y){ break; } }
				 * if((run==cabin.getArrayConsist().size()&&run!=0)||(run==0&&cabin.
				 * getArrayConsist().size()==0)){
				 * for(run=0;run<cabin.getTableInCabin().containReInCabin.size();run++){
				 * if(cabin.getStatus()==cabin.getTableInCabin().containReInCabin.elementAt(run)
				 * .y) break; }
				 * if(run==cabin.getTableInCabin().containReInCabin.size()&&run!=0||run==0&&
				 * cabin.getTableInCabin().containReInCabin.size()==0){ cabin.setStatus(0); } }
				 * if(cabin.getFloor()==1) cabin.setNextState(1); if(cabin.getFloor()==4)
				 * cabin.setNextFloor(-1); cabin.setIsMoving(true); int i=0; int
				 * k=cabin.getArrayConsist().size(); int st=cabin.getStatus(); int
				 * t=cabin.getFloor(); int temp=0; Vector<Point> vt=new Vector<Point>(); boolean
				 * ch=true; int save=0,dis=0; for(i=0;i<k;i++){ Point point=new Point();
				 * point=cabin.getArrayConsist().elementAt(i); int
				 * h=cabin.distance(st,t,point.x,point.y); if(h>=0){ if(ch){ save=temp; dis=h;
				 * ch=false; } if(h<dis){ save=temp; dis=h; } vt.addElement(point); temp++;
				 * }else FloorCall.arraytemporary.add(cabin.getArrayConsist().elementAt(i));
				 * 
				 * } cabin.setArrayConsist(vt); int mis=save; if(mis!=0){ Point ptemp=new
				 * Point(); Point ptemp1=new Point();
				 * ptemp=cabin.getArrayConsist().elementAt(mis);
				 * ptemp1=cabin.getArrayConsist().elementAt(0);
				 * cabin.getArrayConsist().setElementAt(ptemp, 0);
				 * cabin.getArrayConsist().setElementAt(ptemp1, mis);
				 * 
				 * }
				 */
				int d = 10000;
				if (cabin.getArrayConsist().size() > 0) {// ton tai it nhat 1 tu co the chon duoc trong mang chua goi tu
															// tang
					Point p = cabin.getArrayConsist().elementAt(0);
					cabin.setNextFloor(p.x);
					cabin.setNextState(p.y);
					d = cabin.distance(cabin.getStatus(), cabin.getFloor(), p.x, p.y);
					int size = cabin.getTableInCabin().containReInCabin.size();
					for (int j = 0; j < size; j++) {
						Point point = cabin.getTableInCabin().containReInCabin.elementAt(j);
						int di = cabin.distance(cabin.getStatus(), cabin.getFloor(), point.x, point.y);
						if (di >= 0 && di < d) {
							cabin.setNextFloor(point.x);
							cabin.setNextState(point.y);
							d = di;
						}
					}
				} else {
					int b = 0, a = 0;
					int size = cabin.getTableInCabin().containReInCabin.size();
					for (int j = 0; j < size; j++) {
						Point point = cabin.getTableInCabin().containReInCabin.elementAt(j);
						int di = cabin.distance(cabin.getStatus(), cabin.getFloor(), point.x, point.y);
						if (di >= 0 && di < d) {
							a = point.x;
							b = point.y;
							d = di;
						}
					}
					cabin.setNextFloor(a);
					cabin.setNextState(b);
				}

				if (d < 10000) {
					cabin.move(cabin.getLocation().y, + Display.H * (5 - cabin.getNextFloor()) - Display.H, cabin
							.distance(cabin.getStatus(), cabin.getFloor(), cabin.getNextFloor(), cabin.getNextState()));
					// thiet lap lai mau cho cac tang
					cabin.setTabelInFloor_State(0);

					cabin.setIsMoving(false);
					{
						// if(cabin.getDirection().getStopState()==false)
						cabin.updatePerson(false);
					}
					/*
					 * if(false==false){ }else{ //xu ly hien tuong stop o day try {
					 * Thread.sleep(500); } catch (InterruptedException ex) {
					 * Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex); }
					 * 
					 * 
					 * try { Thread.sleep(500); } catch (InterruptedException ex) {
					 * Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex); }
					 * //dua cac tang trong mang cua no cho mang chua tam thoi for(int
					 * j=0;j<cabin.getArrayConsist().size();j++){
					 * FloorCall.arraytemporary.addElement(cabin.getArrayConsist().elementAt(j)); }
					 * cabin.getArrayConsist().removeAllElements();
					 * cabin.getTableInCabin().containReInCabin.removeAllElements();
					 * //cabin.getOpenAndCloseDoor().setType(0);
					 * //cabin.getOpenAndCloseDoor().start(); int kf=0;
					 * while(cabin.getDirection().getStopState()==true){ try { Thread.sleep(500); }
					 * catch (InterruptedException ex) {
					 * Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex); }
					 * if(Display.getControl(0).cabin.getDirection().getStopState()==true&&Display.
					 * getControl(1).cabin.getDirection().getStopState()==true){ k=1; for(int
					 * ki=1;ki<=10;ki++) { if(ki==1) Display.setStatusFoorCall(ki,1); else
					 * if(ki==10) Display.setStatusFoorCall(ki,-1); else {
					 * Display.setStatusFoorCall(ki,-1); Display.setStatusFoorCall(ki,1); } }
					 * Display.getControl(0).cabin.getArrayConsist().removeAllElements();
					 * Display.getControl(1).cabin.getArrayConsist().removeAllElements();
					 * FloorCall.arraytemporary.removeAllElements(); }
					 * 
					 * }
					 * 
					 * }
					 */
				} else {
					cabin.setStatus(0);
				}
			} else {// cap nhat lai trang thai khi khong het 1 loai trang thai nao do

				cabin.setStatus(0);
				FloorCall.updateFloorCall();
				/*
				 * if(cabin.getIsOpened()==true){ try { Thread.sleep(3000); } catch
				 * (InterruptedException ex) {
				 * Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex); }
				 * //cabin.getOpenAndCloseDoor().setType(0);
				 * //cabin.getOpenAndCloseDoor().start(); }
				 */
			}
		}
	}
}
