
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelRoot extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelRoot(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		this.panelInfoStation = new JPanelInfoStation(afficheurServiceMOO);

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		panelInfo.update();
		panelInfoStation.update();
		jPanelInfoSupp.update();
		panelData.update();
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		panelInfoStation.updateMeteoServiceOptions(meteoServiceOptions);
		}

	public boolean isPanelRoot(JPanelRoot jpr)
		{
		return this.afficheurServiceMOO.getTitre().equals(jpr.afficheurServiceMOO.getTitre());
		}

	public boolean isConnected()
		{
		try
			{
			return this.afficheurServiceMOO.getMeteoServiceRemote().isConnect();
			}
		catch (RemoteException e)
			{
			removeComponent();
			System.err.println("N'est pas connecté");
			}
		return false;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getTitleRoot()
		{
		return afficheurServiceMOO.getTitre();
		}

	public AfficheurServiceMOO getAfficheurServiceMOO()
		{
		return this.afficheurServiceMOO;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		try
			{
			String path = "http://i-cms.linternaute.com/image_cms/original/1312345-50-magnifiques-couchers-de-soleil.jpg";
			System.out.println("Get Image from " + path);
			URL url = new URL(path);
			BufferedImage image = ImageIO.read(url);
			System.out.println("Load image into frame...");
			JLabel label = new JLabel(new ImageIcon(image));

			//this.setLocation(200, 200);
			this.setVisible(true);
			}
		catch (Exception exp)
			{
			exp.printStackTrace();
			}
		this.panelControl = new JPanelControl(afficheurServiceMOO);
		this.panelInfo = new JPanelInfo(afficheurServiceMOO);
		this.panelData = new JPanelData(afficheurServiceMOO);
		this.jPanelInfoSupp = new JPanelInfoSupp(afficheurServiceMOO);

		Box boxV = Box.createVerticalBox();
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(panelControl);
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(panelInfoStation);
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(jPanelInfoSupp);
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(panelInfo);
		boxV.add(Box.createVerticalStrut(15));

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(boxV);
		boxH.add(Box.createHorizontalStrut(15));

		setLayout(new BorderLayout());
		add(boxH, BorderLayout.CENTER);
		//	setLayout(new BorderLayout());

		//		add(panelInfo, BorderLayout.CENTER);
		//		add(jPanelInfoSupp, BorderLayout.NORTH);
		//		add(panelControl, BorderLayout.WEST);
		//		add(panelInfo, BorderLayout.SOUTH);

		}

	private void apparence()
		{
		// rien
		//setBackground(Color.ORANGE);
		}

	private void control()
		{
		// rien
		}

	private void removeComponent()
		{
		panelControl = null;
		panelData = null;
		panelInfo = null;
		panelInfoStation = null;
		jPanelInfoSupp = null;
		afficheurServiceMOO = null;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private AfficheurServiceMOO afficheurServiceMOO;
	private JPanelControl panelControl;
	private JPanelData panelData;
	private JPanelInfo panelInfo;
	private JPanelInfoStation panelInfoStation;
	private JPanelInfoSupp jPanelInfoSupp;
	}
