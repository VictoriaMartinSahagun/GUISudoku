package Gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.*;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.JComboBox;
public class GUI extends JFrame {
	private Juego juego;
	private JPanel contentPane;
	private int seleccion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		juego = new Juego();
		
		setTitle("SUDOKU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 453);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//TIMER
		JPanel PanelTimer = new JPanel();
		contentPane.add(PanelTimer, BorderLayout.NORTH);
		PanelTimer.setLayout(new GridLayout(0, 5, 0, 0));
		JLabel m1 = new JLabel("0");
		PanelTimer.add(m1);
		JLabel m2 = new JLabel("0");
		PanelTimer.add(m2);
		JLabel med = new JLabel(":");
		PanelTimer.add(med);
		JLabel s1 = new JLabel("0");
		PanelTimer.add(s1);
		JLabel s2 = new JLabel("0");
		PanelTimer.add(s2);
		
		Timer timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	Grafica imagen= new Grafica();
                long tiempo = juego.getTiempoTranscurrido();
                int totalS = (int) (tiempo/1000)%60;
                int si1 = totalS/10;
                int si2 = totalS%10;
                int totalM = (int) (tiempo/1000)/100;
                int mi1 = totalM/10;
                int mi2 = totalM%10;
                
                imagen.actualizar(si1);
                s1.setIcon(imagen.getGrafico());
                reDimensionar(s1,imagen.getGrafico());
                
                imagen.actualizar(si2);
                s2.setIcon(imagen.getGrafico());
                reDimensionar(s2,imagen.getGrafico());
                
                imagen.actualizar(mi1);
                m1.setIcon(imagen.getGrafico());
                reDimensionar(m1,imagen.getGrafico());
                
                imagen.actualizar(mi2);
                m2.setIcon(imagen.getGrafico());
                reDimensionar(m2,imagen.getGrafico());
                
            }
        });
		
		//TABLERO DE JUEGO
		JPanel PanelTablero = new JPanel();
		PanelTablero.setBorder(new LineBorder(SystemColor.activeCaption, 5));
		contentPane.add(PanelTablero, BorderLayout.CENTER);
		PanelTablero.setLayout(new GridLayout(juego.getCantFilas(), juego.getCantFilas(), 0, 0));
		
		//PANEL INICIO Y VARIABLES
		JPanel PanelInicioExtras = new JPanel();
		contentPane.add(PanelInicioExtras, BorderLayout.SOUTH);
		PanelInicioExtras.setLayout(new BorderLayout(0, 0));
		
		//VARIABLES
		JPanel PanelVariables = new JPanel();
		PanelInicioExtras.add(PanelVariables,BorderLayout.NORTH);
		PanelVariables.setLayout(new GridLayout(1,0,0,0));
		for(int i=1;i<juego.getCantFilas()+1;i++) {
			JButton boton = new JButton(""+i);
			boton.setName("elemento "+i);
			PanelVariables.add(boton);
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					seleccion=Integer.parseInt(boton.getText());
				}
			});
			
		}
		
		//INICIO
		JButton Iniciar = new JButton("Iniciar");
		Iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iniciar.setText("Iniciado");
				iniciarJuego(PanelTablero);
				timer.start();
				Iniciar.setEnabled(false);
			}
		});
		PanelInicioExtras.add(Iniciar, BorderLayout.SOUTH);
		
		
	}
	
	private void iniciarJuego(JPanel PanelTablero) {
		int filas=juego.getCantFilas();
		for (int i = 0; i <filas; i++) {
			for(int j =0; j<filas; j++) {
				Celda c = juego.getCelda(i,j);
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				JLabel label = new JLabel();
				label.setName(i+""+j);
				label.setBorder(new LineBorder(SystemColor.activeCaption, 5));
				PanelTablero.add(label);
				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});
				if(c.getValue()!=null) {
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int nombre= Integer.parseInt(label.getName());
							int columna =(nombre%10);
							int fila = nombre/10;
							juego.setCelda(fila,columna,seleccion);
							ImageIcon grafico = juego.getCelda(fila,columna).getEntidadGrafica().getGrafico();
							label.setIcon(grafico);
							reDimensionar(label, grafico);
							juego.validarRespuesta(fila,columna);	//le indico al juego que valide la celda agregada.
							//recorro la matriz y si existen celdas mal ubicadas las señalo.
							for(int i=0;i<juego.getCantFilas();i++) {
								for(int j=0;j<juego.getCantFilas();j++) {
									if (juego.getCelda(i, j).getEstado()) {
										label.setBorder(new LineBorder(SystemColor.activeCaption, 5));
									}else {
										label.setBorder(new LineBorder(Color.MAGENTA, 5));
									}
								}
							}
						}
					});
				}
			}
		}	
	}
	private void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
}
