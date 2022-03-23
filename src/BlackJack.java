
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;



public class BlackJack extends JFrame implements Runnable{

	public static void main(String[] args) {
		EventQueue.invokeLater(new BlackJack("BlackJack"));	
	}
	
	private int wynik=0,przejscie;
	private JMenuBar menu;
	private JMenu menu_option;
	private JMenuItem zamknij_okno;
	private CloseAction ca=new CloseAction();
	private JMenuItem pomoc;
	private JButton submit;

	private JPanel panel,batoniki,rezultat;
	private JButton dobierz, pasuj, Bpomoc;
	
	private JTextArea textArea;
	
	private int fontsize=52, fontsize_imie=30;
	private Font font,font_imie;
	private String[] options;
	private String name;
	
	private JLabel wyniczek;
	private JLabel imie_gracza;
	
	public BlackJack(String title) {
		super(title);
		
		//ustawianie okienka
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension dim=tk.getScreenSize();
		setSize(new Dimension(dim.width/2, dim.height/2));
		setLocationByPlatform(true);
		
		
		//pasek menu
		
		menu = new JMenuBar();
		setJMenuBar(menu);
		menu_option = new JMenu("Plik");
		menu.add(menu_option);
		
		//Dodanie do menu przycisku "help" oraz "zamknij" i obsluga zdarzen
		
		pomoc= new JMenuItem("Pomoc");
		menu_option.add(pomoc);
		pomoc.setActionCommand("helpik");
		pomoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		pomoc.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String command = e.getActionCommand();
					if(command.equals("helpik")) {
						
						JOptionPane.showMessageDialog(null, "Wartosc liczbowa kart: \n"+
						"Punkty sa rowne wartosci liczbowej karty poza Waletem, Dama i Krolem ktore sa warte 10 punktow oraz Asem ktory jest warty 1 lub 11 punktow.","Punktacja",
						JOptionPane.QUESTION_MESSAGE);
				}
			}
		});
		zamknij_okno = new JMenuItem(ca);
		menu_option.add(zamknij_okno);

		addWindowListener(new WindowClosingAdapter());
	
		panel = new JPanel();


		textArea= new JTextArea("Imie",1,15);
	

		submit = new JButton("Potwiedz");
		submit.setActionCommand("submit");
		font_imie=new Font("Helvetica",Font.BOLD,fontsize_imie);
		
		
		//ustawienie imienia gracza
		
		submit.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if(command.equals("submit")) {
					name = textArea.getText();
				
					if(name.length()<3) {

						JOptionPane.showMessageDialog(null, "Nazwa musi miec co najmniej 3 znaki","Blad",JOptionPane.ERROR_MESSAGE);
					}
					else {
						
						imie_gracza.setText(name);
						imie_gracza.setFont(font_imie);

					} 
				}
			}
		});
		
		
		
		
		imie_gracza = new JLabel("    ");
	
		panel.add(textArea);
		panel.add(submit);
		
		panel.add(imie_gracza);

					
		batoniki = new JPanel();
		
		rezultat = new JPanel();
		
		wyniczek = new JLabel("     ");
		wyniczek.setPreferredSize(new Dimension(250,200));
		font=new Font("Helvetica",Font.BOLD,fontsize);
		wyniczek.setFont(font);
		rezultat.add(wyniczek);
		
		//dodanie przycisku "dobierz", losowanie wartosci karty i dodanie jej do ogolnego wyniku
		
		dobierz = new JButton("Dobierz karte");
		batoniki.add(dobierz);
		dobierz.setActionCommand("dobrane");
		options = new String[] {"1", "11"};
		dobierz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				Random generator = new Random();
				if(command.equals("dobrane")) {
					przejscie = generator.nextInt(13)+2;
					if(przejscie == 11 || przejscie == 12 || przejscie == 13)
						przejscie = 10;
					if(przejscie == 14) {
						int x = JOptionPane.showOptionDialog(null,"Wylosowales asa! Wybierz czy ma miec wartosc 1 czy 11:","As",JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
						if(x==0)
							przejscie =1;
						else if(x==1)
							przejscie =11;
					}
					wynik = wynik + przejscie;
					if(wynik>21) {
						JOptionPane.showMessageDialog(null, "Przegrales i zakonczyles gre z wynikiem: "+ wynik,"Wynik",JOptionPane.QUESTION_MESSAGE);
						wynik = 0;  // ??
					}
					else if(wynik==21){
						JOptionPane.showMessageDialog(null, "OCZKO!!!: "+ wynik,"Wynik",JOptionPane.QUESTION_MESSAGE);
						wynik = 0;
					}		
					}
				wyniczek.setText("Wynik: "+wynik);
				
			}
		});
		
		//Dodanie do menu przycisku "pasuj" i obsluga tego zdarzenia
			
		pasuj = new JButton("Pasuj");
		batoniki.add(pasuj);
		pasuj.setActionCommand("spasowane");
		pasuj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if(command.equals("spasowane")) {
					JOptionPane.showMessageDialog(null, "Konczysz gre z wynikiem: " + wynik,"Wynik",JOptionPane.QUESTION_MESSAGE);
					wynik = 0;
				}
			}
		});
		
		//Dodanie do panelu aplikacji przycisku "help" i obsluga tego zdarzenia
		
		Bpomoc = new JButton("Pomoc");
		batoniki.add(Bpomoc);
		Bpomoc.setActionCommand("helpik");
		Bpomoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if(command.equals("helpik")) {
					
					JOptionPane.showMessageDialog(null, "Wartosc liczbowa kart: \n"+
					"Punkty sa rowne wartosci liczbowej karty poza Waletem, Dama i Krolem ktore sa warte 10 punktow oraz Asem ktory jest warty 1 lub 11 punktow.","Punktacja",
					JOptionPane.QUESTION_MESSAGE);
				}
			}
		});
		
		add(panel,BorderLayout.NORTH);
		add(batoniki,BorderLayout.SOUTH);	
		add(rezultat,BorderLayout.CENTER);
	
	}
	
	
	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private BlackJack getFrame() {
		return this;
	}
	
	class WindowClosingAdapter extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showOptionDialog(e.getWindow(),"Czy chcesz zamknac aplikacje ?","Potwierdzenie",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Tak","Nie"},"Nie")==0)
				System.exit(0);
		}
	}
	
	class CloseAction extends AbstractAction{
		public CloseAction() {
			putValue(Action.NAME, "Zamknij");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			BlackJack bj=getFrame();
			bj.dispatchEvent(new WindowEvent(bj, WindowEvent.WINDOW_CLOSING));
		}
	}
}



