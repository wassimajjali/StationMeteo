
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.imp.com.real.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.use.remote.pclocal.PCLocal;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

import com.bilat.tools.reseau.rmi.RmiTools;

public class JPanelConfiguration extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelConfiguration()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

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
		// JComponent : Instanciation
		jComboBoxMeteo = new JComboBox<String>();
		refresh = new JButton("Rafraîchissement");
		connect = new JButton("Connexion");

		refreshList();

			// Layout : Specification
			{
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowLayout);
			}

		// JComponent : add
		Box boxV = Box.createVerticalBox();
		Box boxH = Box.createHorizontalBox();

		boxH.add(connect);
		boxH.add(refresh);
		boxV.add(jComboBoxMeteo);
		boxV.add(boxH);

		this.add(boxV);
		}

	private void control()
		{
		connect.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					MeteoFactory meteoFactory = new MeteoFactory(jComboBoxMeteo.getSelectedItem().toString(), new ComOption());
					use(meteoFactory.getMeteoService());
					}
			});

		refresh.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					jComboBoxMeteo.removeAll();
					jComboBoxMeteo.removeAllItems();
					refreshList();
					}
			});
		}

	private void appearance()
		{
		//rien
		}

	private void refreshList()
		{
		MeteoPortDetectionServiceFactory meteoPortDetectionServiceFactory = new MeteoPortDetectionServiceFactory();
		MeteoPortDetectionService_I meteoPortDetectionService = meteoPortDetectionServiceFactory.create();
		listPort = meteoPortDetectionService.findListPortMeteo();

		for(String port:listPort)
			{
			jComboBoxMeteo.addItem(port);
			}
		}

	private void use(MeteoService_I meteoService)
		{
		String portName = jComboBoxMeteo.getSelectedItem().toString();
		// Service Meteo
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200); //essentiel

		// Service Affichage

		String titre = RmiTools.getLocalHost() + " " + portName;
		AffichageOptions affichageOption = new AffichageOptions(3, titre);

		new PCLocal(meteoService, meteoServiceOptions, portName, affichageOption, null).run();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JComboBox<String> jComboBoxMeteo;
	private JButton refresh;
	private JButton connect;

	private List<String> listPort;
	}
