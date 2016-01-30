
package ch.hearc.meteo.imp.afficheur.real.vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.JPanelControl;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.JPanelData;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;



public class JPanelRoot extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelRoot(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.panelControl = new JPanelControl(afficheurServiceMOO);
		this.panelData = new JPanelData(afficheurServiceMOO);

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		panelData.update();
		}


	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		//panelSlider.updateMeteoServiceOptions( meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		Box boxV = Box.createVerticalBox();
		//boxV.add(Box.createVerticalStrut(15));
		boxV.add(panelData);
		//boxV.add(Box.createVerticalStrut(15));
		boxV.add(panelControl);
		boxV.add(Box.createVerticalStrut(15));

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(boxV);
		boxH.add(Box.createHorizontalStrut(15));


		setLayout(new BorderLayout());
		add(boxH, BorderLayout.CENTER);
		}

	private void apparence()
		{
		// rien
		setBackground(Color.ORANGE);
		}

	private void control()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelControl panelControl;
	private JPanelData panelData;
	}

