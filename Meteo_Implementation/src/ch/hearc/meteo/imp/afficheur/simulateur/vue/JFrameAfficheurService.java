
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private JFrameAfficheurService(boolean isCentralPC)
		{
		this.isCentralPC = isCentralPC;
		this.listJPanelRoot = new ArrayList<JPanelRoot>();

		try
			{
			for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels())
				{
				if ("Nimbus".equals(info.getName()))
					{
					UIManager.setLookAndFeel(info.getClassName());
					break;
					}
				}
			}
		catch (Exception e)
			{
			}

		Thread threadVerifyMeteo = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					while(true)
						{
						verifyMeteoStationConnected();
						sleep(TIME_DELAY);
						}
					}
			});

		threadVerifyMeteo.start();

		geometry();
		control();
		apparence();

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		for(JPanelRoot jPanelRoot:listJPanelRoot)
			{
			jPanelRoot.update();
			}
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		for(JPanelRoot jPanelRoot:listJPanelRoot)
			{
			if (jPanelRoot.isPanelRoot((JPanelRoot)jTabbedPane.getSelectedComponent()))
				{
				jPanelRoot.updateMeteoServiceOptions(meteoServiceOptions);
				}
			}

		}

	public synchronized void addMeteoPanel(JPanelRoot jPanelRoot)
		{
		jTabbedPane.add(jPanelRoot.getTitleRoot(), jPanelRoot);
		listJPanelRoot.add(jPanelRoot);
		}

	public static JFrameAfficheurService getInstance(boolean isCentralPC)
		{
		if (INSTANCE == null)
			{
			INSTANCE = new JFrameAfficheurService(isCentralPC);
			}
		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		this.jTabbedPane = new JTabbedPane();
		add(jTabbedPane);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void apparence()
		{
		setTitle("Station météo" + ((isCentralPC) ? " - Central" : ""));
		setSize(1200, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		}

	private void sleep(int time_delay)
		{
		try
			{
			Thread.sleep(time_delay);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	private void verifyMeteoStationConnected()
		{
		Iterator<JPanelRoot> iterator = listJPanelRoot.iterator();

		while(iterator.hasNext())
			{
			JPanelRoot jPanelRoot = iterator.next();
			if (!jPanelRoot.isConnected())
				{
				jTabbedPane.remove(jPanelRoot);
				iterator.remove();
				}
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private boolean isCentralPC;

	// Tools
	private List<JPanelRoot> listJPanelRoot;
	private JTabbedPane jTabbedPane;

	private final int TIME_DELAY = 1000;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static JFrameAfficheurService INSTANCE;

	public static final int POOLING_DELAY = 1000;
	public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	public static final Color FOREGROUND_COLOR = new Color(241, 196, 15);
	public static final Color PLOT_BACKGROUND_COLOR = new Color(75, 75, 75);
	}
