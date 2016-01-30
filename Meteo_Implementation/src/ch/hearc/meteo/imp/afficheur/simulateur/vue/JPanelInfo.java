
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
//import ch.hearc.meteo.imp.com.real.MeteoServiceFactory;
//import ch.hearc.meteo.imp.use.PortManager;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelInfo extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInfo(AfficheurServiceMOO afficheurServiceMOO2)
		{


		this.afficheurServiceMOO = afficheurServiceMOO2;
		statPression = new Stat();
		statAltitude = new Stat();
		statTemperature = new Stat();

		geometry();
		control();
		apparence();
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		listMeteoEventPression = afficheurServiceMOO.getListPression();
		listMeteoEventAltitude = afficheurServiceMOO.getListAltitude();
		listMeteoEventTemperature = afficheurServiceMOO.getListTemperature();
		statPression = afficheurServiceMOO.getStatPression();
		statAltitude = afficheurServiceMOO.getStatAltitude();
		statTemperature = afficheurServiceMOO.getStatTemperature();
		textFieldPression.setText(Float.toString(statPression.getLast()));
		textFieldAltitude.setText(Float.toString(statAltitude.getLast()));
		textFieldTemperature.setText(Float.toString(statTemperature.getLast()));


		panelInfoStation.update();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{

		panelInfoStation = new JPanelInfoStation(afficheurServiceMOO);
		textFieldTemperature = new JTextField();
		final float temperature;
		//MeteoService_I service = new MeteoServiceFactory().create(PortManager.getPort());

		GridLayout grid = new GridLayout(0, 6, 50, 200);
		labelTemperature = new JLabel("Temperature :");
		labelTemperature.setForeground(Color.red);
		labelTemperature.setFont(new Font("Serif", Font.BOLD, 19));


		//checkBoxTemperature = new JCheckBox("Temp�rature", true);

		textFieldTemperature.setEditable(false);
		labelPression = new JLabel("Pression :");
		labelPression.setForeground(Color.red);
		labelPression.setFont(new Font("Serif", Font.BOLD, 19));


		
		//checkBoxPression = new JCheckBox("Pression", true);
		textFieldPression = new JTextField("Pression");
		textFieldPression.setEditable(false);

		//checkBoxAltitude = new JCheckBox("Altitude", true);
		
		labelAltitude = new JLabel("Altitude :");
		labelAltitude.setForeground(Color.red);
		labelAltitude.setFont(new Font("Serif", Font.BOLD, 19));


		textFieldAltitude = new JTextField("Altitude");
		textFieldAltitude.setEditable(false);
		this.setLayout(grid);
		this.add(labelTemperature);
		this.add(textFieldTemperature);
		this.add(labelPression);
		this.add(textFieldPression);
		this.add(labelAltitude);
		this.add(textFieldAltitude);
		setBorder(BorderFactory.createTitledBorder("Informations of the station"));

		}

	private void control()
		{

		}

	private void apparence()
		{
		// nothing

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurServiceMOO afficheurServiceMOO;
	private List<MeteoEvent> listMeteoEventPression;
	private List<MeteoEvent> listMeteoEventTemperature;
	private List<MeteoEvent> listMeteoEventAltitude;
	private JPanelInfoStation panelInfoStation;
	private Stat statPression;
	private Stat statTemperature;
	private Stat statAltitude;
	//Tools
	private JLabel labelTemperature;
	private JLabel labelAltitude;
	private JLabel labelPression;
	private JTextField textFieldTemperature;
	private JTextField textFieldAltitude;
	private JTextField textFieldPression;

	private int choix; // 0 = temperature, 1 = Pression, 2 = altitude

	}
