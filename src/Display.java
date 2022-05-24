import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.sound.midi.Patch;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Display implements ActionListener {

	private JFrame fr;
	
	private static JLayeredPane layerPanel;
	//private static ScrollPane scrollPanel;

	static final int H = 180;
	static final int W = 180;
	public Cabin panelCabin[] = new Cabin[2];
	
	private static Control control[] = new Control[2];
	private static FloorCall tableCall[] = new FloorCall[4];

	private JButton button;
	private TableInCabin panel, panel1;
	private JButton bt[]=new JButton[10];
	public Display() {

		fr = new JFrame();
		fr.setTitle("Simulate Elevator");
		
		fr.setVisible(true);
		

		//scrollPanel = new ScrollPane();
		

		layerPanel = new JLayeredPane();

		//scrollPanel.setPreferredSize(new Dimension(3000, 750));
		layerPanel.setPreferredSize(new Dimension(1000, (H) * 4 + 100));
		layerPanel.setRequestFocusEnabled(true);
		

		//scrollPanel.add(layerPanel);
		layerPanel.setOpaque(true);

		fr.add(layerPanel);
		fr.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		fr.setResizable(false);
		fr.setBounds(0, 0, 3000, 750);
		//layerPanel.validate();

		// bang dieu khien
		panel = new TableInCabin(panelCabin[0]);
		panel1 = new TableInCabin(panelCabin[1]);
		// panel.setBackground(Color.red);
		panel.setBounds(600, 0, 200, 500);
		panel.setOpaque(true);
		panel1.setBounds(900, 0, 200, 500);
		panel1.setOpaque(true);
		layerPanel.add(panel);
		// layerPanel.add(panel1);

		// thang may
		panelCabin[0] = new Cabin();
		panelCabin[1] = new Cabin();

		panelCabin[0].setBounds(000,  + H * 3, W, H);
		panelCabin[0].setPreferredSize(new Dimension(W, H));
		panelCabin[0].setOpaque(true);
		panelCabin[0].setFloor(1);

		panelCabin[1].setBounds(300,  + H * 3, W, H);
		panelCabin[1].setPreferredSize(new Dimension(W, H));
		panelCabin[1].setOpaque(true);
		panelCabin[1].setFloor(1);

		layerPanel.add(panelCabin[0]);
		layerPanel.add(panelCabin[1]);

		panelCabin[0].setBackground(Color.blue);
		panelCabin[1].setBackground(Color.blue);

		// phim goi thang may
		for (int i = 0; i < 4; i++) {
			if (i != 0 && i != 3) {

				tableCall[i] = new FloorCall(i + 1);
				tableCall[i].setBackground(Color.gray);
				tableCall[i].setPreferredSize(new Dimension(40, 80));
				tableCall[i].setBounds(220, 120 + (3 - i) * H, 40, 80);
				tableCall[i].setFloor(i + 1);
				tableCall[i].setEnabled(false);
				layerPanel.add(tableCall[i]);
			} else if (i == 3) {
				tableCall[i] = new FloorCall(4);
				tableCall[i].setBackground(Color.GRAY);
				tableCall[i].setPreferredSize(new Dimension(40, 40));
				tableCall[i].setBounds(220, 120 + (3 - i) * H, 40, 40);
				tableCall[i].setFloor(4);// dang dang do
				layerPanel.add(tableCall[i]);
			} else {
				tableCall[i] = new FloorCall(1);
				tableCall[i].setFloor(1);
				tableCall[i].setBackground(Color.GRAY);
				tableCall[i].setPreferredSize(new Dimension(40, 40));
				tableCall[i].setBounds(220, 120 + (3 - i) * H, 40, 40);
				layerPanel.add(tableCall[i]);
			}
		}

		// bang mau len xuong
		for (int i = 0; i < 4; i++) {
			panelCabin[0].getTableInFloor(i).setBackground(Color.BLUE);
			panelCabin[1].getTableInFloor(i).setBackground(Color.BLUE);

			panelCabin[0].getTableInFloor(i).setBounds(190, 20 + i * H, 40, 80);
			layerPanel.add(panelCabin[0].getTableInFloor(i));
			panelCabin[1].getTableInFloor(i).setBounds(250, 20 + i * H, 40, 80);
			layerPanel.add(panelCabin[1].getTableInFloor(i));
			bt[i]=new JButton();
		}

		// khung ben ngoai
		panelCabin[0].setDoor2(layerPanel);
		panelCabin[1].setDoor2(layerPanel);

		// Hien thi so tang
		for (int i = 0; i < 4; i++) {
			JLabel lafloor = new JLabel();
			lafloor.setOpaque(false);
			if (i != 0) {
				lafloor.setText("<html> <Font Size=5>" + (4 - i) + "</html>");
				lafloor.setBounds(230, 80 + i * H, 50, 50);
				layerPanel.add(lafloor);
			}
			if (i == 0) {
				lafloor.setText("<html> <Font Size=5>" + (4 - i) + "</html>");
				lafloor.setBounds(220, 70 + i * H, 70, 50);
				layerPanel.add(lafloor);
			}
		}
	}

	public static Control getControl(int a) {
		return control[a];
	}

	public static FloorCall getTableCall(int i) {
		return tableCall[i];
	}

	//
	public void startSystem() {
		control[0] = new Control(panelCabin[0]);
		control[1] = new Control(panelCabin[1]);

		control[0].start();
		control[0].getThread();
		control[1].start();
		control[1].getThread();

	}

	// anh len xuong tung tang
	public static void setStatusFoorCall(int a, int b) {
		if (a == 1) {
			tableCall[0].button[1].setIcon(new ImageIcon("anhnen/len1.png"));
			FloorCall.checkButtonPressed[1] = 0;
		} else {
			if (b > 0) {
				if (a != 4)
					tableCall[a - 1].button[1].setIcon(new ImageIcon("anhnen/len1.png"));
				FloorCall.checkButtonPressed[a] = 0;

			} else {
				if (a == 4) {
					tableCall[3].button[1].setIcon(new ImageIcon("anhnen/xuong1.png"));
					FloorCall.checkButtonPressed[4] = 0;
				} else if (a != 1) {
					tableCall[a - 1].button[2].setIcon(new ImageIcon("anhnen/xuong1.png"));
					FloorCall.checkButtonPressed[4 + a] = 0;
				} else {
					tableCall[a - 1].button[1].setIcon(new ImageIcon("anhnen/len1.png"));
					FloorCall.checkButtonPressed[a] = 0;
				}
			}
		}
	}

	/*
	 * khoi tao menu intTableInCabin();
	 * 
	 * //bat su kien showTableInCabin.addActionListener(new ActionListener() {
	 * public void actionPerformed(ActionEvent e){ showTableInCabin(); } });
	 */

	/*
	 * public void setMenu(JFrame fr){//khoi tao menubar va cac thanh phan Menu
	 * menuBar=new MenuBar(); menuControl=new Menu("Control"); showTableInCabin=new
	 * MenuItem("Show Table Control In Cabin");
	 * 
	 * menuBar.add(menuControl);
	 * 
	 * menuControl.add(showTableInCabin);
	 * 
	 * fr.setMenuBar(menuBar); }
	 * 
	 * //menu public void intTableInCabin(){ GridBagConstraints bg=new
	 * GridBagConstraints(); frCabin=new JDialog();
	 * frCabin.setCursor(java.awt.Cursor.getPredefinedCursor(12));
	 * frCabin.setResizable(false); frCabin.setTitle("Table Call Floor In Cabin");
	 * frCabin.setLayout(new GridBagLayout()); bg.gridx=0; bg.gridy=0;
	 * frCabin.add(panelCabin[0].getTableInCabin(),bg); bg.gridx=1; bg.gridy=0;
	 * frCabin.add(panelCabin[1].getTableInCabin(),bg); frCabin.pack(); } public
	 * void showTableInCabin(){ frCabin.setVisible(true);
	 * frCabin.setAlwaysOnTop(true); } public void closeTableInCabin(){
	 * frCabin.dispose(); }
	 */
	/*
	 * //them cua ao(cua tren tang) for(int i=0;i<10;i++){ JLabel vidoor=new
	 * JLabel(new ImageIcon("visuadoor.png"));
	 * vidoor.setBounds(300,HEIGHTFLOOR*(9-i)+100,90,260); layerPanel.add(vidoor,new
	 * Integer(4)); JLabel vidoor1=new JLabel(new ImageIcon("visuadoor.png"));
	 * vidoor1.setBounds(600,HEIGHTFLOOR*(9-i)+100,90,260);
	 * layerPanel.add(vidoor1,new Integer(4)); } countComponent++; //them panel an
	 * cua
	 * 
	 * hideDoor[0]=new JPanel(); hideDoor[2]=new JPanel(); hideDoor[1]=new JPanel();
	 * hideDoor[0].setBounds(0,0,300,HEIGHTFLOOR*10+100);
	 * hideDoor[2].setBounds(780,0,300,HEIGHTFLOOR*10+100);
	 * hideDoor[1].setBounds(480,0,120,HEIGHTFLOOR*10+100); for(int i=0;i<3;i++){
	 * hideDoor[i].setBackground(colorBackground[1]); layerPanel.add(hideDoor[i],new
	 * Integer(countComponent)); } countComponent++;
	 */
	// nut bam len xuong

	// panel = new JPanel();
	// panel.setBounds(200,200,150,140);
	// panel.setPreferredSize(new Dimension(W,10*HEIGHTFLOOR));
	// panel.setOpaque(false);
	// fr.add(panel);
	// them vien gach vao cac tang

	// them anh vao tuong

	/*
	 * buongThang[0]=new JPanel(); buongThang[0].setBounds(300,50,W,HEIGHTFLOOR*10);
	 * buongThang[0].setPreferredSize(new Dimension(W,10*HEIGHTFLOOR));
	 * buongThang[0].setOpaque(false); buongThang[0].add(new JLabel(new
	 * ImageIcon("anhnen/xuong1.png"))); layerPanel.add(buongThang[0],new
	 * Integer(countComponent));
	 * 
	 * buongThang[1]=new JPanel(); buongThang[1].setPreferredSize(new
	 * Dimension(W,10*HEIGHTFLOOR));
	 * buongThang[1].setBounds(600,50,W,HEIGHTFLOOR*10); buongThang[1].add(new
	 * JLabel(new ImageIcon("anhnen/len1.png"))); layerPanel.add(buongThang[1],new
	 * Integer(countComponent)); countComponent++;
	 */

	// add layer vao scroll vao frame

	// kiem tra xem co khoa tang b trong cabin khong
	/*
	 * public boolean confirmInputfloor(int a,int b){
	 * if(control[0].getCabin().getDirection().getStatefloorClockInCabin(b)==false&&
	 * control[1].getCabin().getDirection().getStatefloorClockInCabin(b)==false)
	 * return true; else return false; }
	 */

	public void actionPerformed(ActionEvent e) {

	}

}