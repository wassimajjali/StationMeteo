
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelSlider extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSlider(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		apparence();
		control();
		Thread thread = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					while(true)
						{
						try
							{
							Thread.sleep(JFrameAfficheurService.POOLING_DELAY);
							//updateMeteoServiceOptions(meteoServiceOptions);
							}
						catch (Exception e)
							{
							e.printStackTrace();
							}
						}
					}
			});

		thread.start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
			{
			jSliderTemperatureDt.setValue((int)meteoServiceOptions.getTemperatureDT());
			jSliderAltitudeDt.setValue((int)meteoServiceOptions.getAltitudeDT());
			jSliderPressureDt.setValue((int)meteoServiceOptions.getPressionDT());
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());

		try
			{
			jSliderTemperatureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getMeteoServiceOptions().getTemperatureDT());
			}
		catch (RemoteException e)
			{
			jSliderTemperatureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}
		try
			{
			jSliderAltitudeDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getMeteoServiceOptions().getAltitudeDT());
			}
		catch (RemoteException e)
			{
			jSliderAltitudeDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}
		try
			{

			jSliderPressureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getMeteoServiceOptions().getPressionDT());
			}
		catch (RemoteException e)
			{
			jSliderPressureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}

		jSliderTemperatureDt.setMajorTickSpacing(JSLIDER_STEP);
		jSliderAltitudeDt.setMajorTickSpacing(JSLIDER_STEP);
		jSliderPressureDt.setMajorTickSpacing(JSLIDER_STEP);

		jSliderTemperatureDt.setPaintTicks(true);
		jSliderAltitudeDt.setPaintTicks(true);
		jSliderPressureDt.setPaintTicks(true);

		jSliderTemperatureDt.setPaintLabels(true);
		jSliderAltitudeDt.setPaintLabels(true);
		jSliderPressureDt.setPaintLabels(true);

		jSliderTemperatureDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		jSliderAltitudeDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		jSliderPressureDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);

		jSliderTemperatureDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jSliderAltitudeDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jSliderPressureDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);

		Box boxH = Box.createHorizontalBox();

		Box boxTemperature = Box.createVerticalBox();
		JLabel jLabelTemperature = new JLabel("Température Dt");
		boxTemperature.add(jLabelTemperature);
		boxTemperature.add(jSliderTemperatureDt);
		boxH.add(boxTemperature);
		boxH.add(Box.createHorizontalGlue());

		Box boxAltitude = Box.createVerticalBox();
		JLabel jLabelAltitude = new JLabel("Altitude Dt");
		boxAltitude.add(jLabelAltitude);
		boxAltitude.add(jSliderAltitudeDt);
		boxH.add(boxAltitude);
		boxH.add(Box.createHorizontalGlue());

		Box boxPressure = Box.createVerticalBox();
		JLabel jLabelPressure = new JLabel("Pression Dt");
		boxPressure.add(jLabelPressure);
		boxPressure.add(jSliderPressureDt);
		boxH.add(boxPressure);
		boxH.add(Box.createHorizontalGlue());

		add(boxH, BorderLayout.CENTER);

		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);

		}

	private void control()
		{
		//temperature control
		jSliderTemperatureDt.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent e)
					{
					int value = jSliderTemperatureDt.getValue();

					try
						{
						MeteoServiceOptions meteoServiceOption = new MeteoServiceOptions(afficheurServiceMOO.getMeteoServiceOptions());
						meteoServiceOption.setTemperatureDT(value);
						afficheurServiceMOO.setMeteoServiceOptions(meteoServiceOption);
						}
					catch (RemoteException e1)
						{
						System.err.println("[JPanelSlider] remote update failed");
						e1.printStackTrace();
						}

					}
			});

		//pression control
		jSliderPressureDt.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent e)
					{
					int value = jSliderPressureDt.getValue();

					try
						{
						MeteoServiceOptions meteoServiceOption = new MeteoServiceOptions(afficheurServiceMOO.getMeteoServiceOptions());
						meteoServiceOption.setPressionDT(value);
						afficheurServiceMOO.setMeteoServiceOptions(meteoServiceOption);
						}
					catch (RemoteException e1)
						{
						System.err.println("[JPanelSlider] remote update failed");
						e1.printStackTrace();
						}

					}
			});
		//altitude
		jSliderAltitudeDt.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent e)
					{
					int value = jSliderAltitudeDt.getValue();

					try
						{
						MeteoServiceOptions meteoServiceOption = new MeteoServiceOptions(afficheurServiceMOO.getMeteoServiceOptions());
						meteoServiceOption.setAltitudeDT(value);
						afficheurServiceMOO.setMeteoServiceOptions(meteoServiceOption);
						}
					catch (RemoteException e1)
						{
						System.err.println("[JPanelSlider] remote update failed");
						e1.printStackTrace();
						}

					}
			});

		}

	//	private void setTitleBorder(int value)
	//		{
	//		border.setTitle("Delta Time Temperature =" + value + " (ms)");
	//		//border2.setTitle("Delta Time Altitude =" + value + " (ms)");
	//		//border3.setTitle("Delta Time Pression =" + value + " (ms)");
	//
	//		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	// Tools
	private JSlider jSliderTemperatureDt;
	private JSlider jSliderAltitudeDt;
	private JSlider jSliderPressureDt;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int JSLIDER_MIN = 500;
	private static final int JSLIDER_MAX = 3000;
	private static final int JSLIDER_STEP = 500;

	}
