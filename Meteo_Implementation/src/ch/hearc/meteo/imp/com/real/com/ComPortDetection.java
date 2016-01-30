
package ch.hearc.meteo.imp.com.real.com;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPortIdentifier;

import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class ComPortDetection
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComPortDetection()
		{
		this.isAvailable = false;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Retourne la liste de tous les ports séries
	 */
	public List<String> findListPortSerie()
		{
		ArrayList<String> portsComm = new ArrayList<String>();

		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portIdentifier;

		while(portEnum.hasMoreElements())
			{
			portIdentifier = portEnum.nextElement();

			if (getPortTypeName(portIdentifier.getPortType()).equals("Serial"))
				{
				portsComm.add(portIdentifier.getName());
				}
			}

		return portsComm;
		}

	/**
	 * Implementation conseil:
	 * 		(C1) Catcher l'ouverture du port qui peut echouer si le port est deja ouvert, et renvoyer false (le port est deja utilisé)
	 * 		(C2) Envoyer une trame, attendre une réponse en tenant compte d'un timeout
	 *
	 * Output:
	 * 		Return true si station meteo est connecté au portName
	 * 		Return false si
	 * 				(1) pas sation meteo connecté au PortName
	 * 				(2) StationMeteo connecte au PortName mais deja en utilisation (impossible d'ouvrir le port alors)
	 */
	public boolean isStationMeteoAvailable(String portName, long timeoutMS)
		{

		MeteoFactory meteoFactory = new MeteoFactory(portName, new ComOption());

		MeteoService_I meteoService = meteoFactory.getMeteoService();

		try
			{
			meteoService.connect();
			}
		catch (MeteoServiceException e)
			{
			System.err.println("[FreePorts](tryToConnect) : " + portName + " error : " + e);
			return false;
			}

		meteoService.addMeteoListener(new MeteoListener_I()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					isAvailable = true;
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					isAvailable = true;
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					isAvailable = true;
					}
			});

		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);

		sleep(timeoutMS);

		try
			{
			//meteoService.stop();
			meteoService.disconnect();
			}
		catch (MeteoServiceException e)
			{
			System.err.println("[FreePorts](tryToDisconnect) : " + portName + " error : " + e);
			}

		return isAvailable;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static String getPortTypeName(int portType)
		{
		switch(portType)
			{
			case CommPortIdentifier.PORT_I2C:
				{
				return "I2C";
				}
			case CommPortIdentifier.PORT_PARALLEL:
				{
				return "Parallel";
				}
			case CommPortIdentifier.PORT_RAW:
				{
				return "Raw";
				}
			case CommPortIdentifier.PORT_RS485:
				{
				return "RS485";
				}
			case CommPortIdentifier.PORT_SERIAL:
				{
				return "Serial";
				}
			default:
				{
				return "Unknown type";
				}
			}
		}

	private static void sleep(long delayMS)
		{
		System.out.println("sleep ComPortDetection: " + delayMS);
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	// Tools
	private boolean isAvailable;
	}
