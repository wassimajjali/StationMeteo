
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

// TODO student
//  Implémenter cette classe
//  Updater l'implémentation de MeteoServiceFactory.create()

/**
 * <pre>
 * Aucune connaissance des autres aspects du projet ici
 *
 * Ouvrer les flux vers le port com
 * ecouter les trames arrivantes (pas boucle, mais listener)
 * decoder trame
 * avertir meteoServiceCallback
 *
 *</pre>
 */
public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		this.portName = portName;
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/**
	 * <pre>
	 * Problem :
	 * 		MeteoService est un MeteoServiceCallback_I
	 * 		ComConnexions_I utilise MeteoServiceCallback_I
	 * 		MeteoService utilise ComConnexions_I
	 *
	 * On est dans la situation
	 * 		A(B)
	 * 		B(A)
	 *
	 * Solution
	 * 		 B
	 * 		 A(B)
	 *  	 B.setA(A)
	 *
	 *  Autrement dit:
	 *
	 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
	 *      MeteoService_I meteoService=new MeteoService(comConnexion);
	 *      comConnexion.setMeteoServiceCallback(meteoService);
	 *
	 *      Ce travail doit se faire dans la factory
	 *
	 *  Warning : call next
	 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
	 *
	 *  </pre>
	 */

	public ComConnexion(String portName, ComOption comOption)
		{
		this(null, portName, comOption);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void start() throws Exception
		{
		serialPort.notifyOnDataAvailable(true);

		serialPort.addEventListener(new SerialPortEventListener()
			{

				@Override
				public void serialEvent(SerialPortEvent e)
					{
					switch(e.getEventType())
						{
						case SerialPortEvent.DATA_AVAILABLE:
							// Récupération des données
							traiterDonnees();
							break;
						}
					}
			});
		}

	@Override
	public void stop() throws Exception
		{
		serialPort.removeEventListener();
		}

	@Override
	public void connect() throws Exception
		{
		// Obtenir un port
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(this.portName);

		// Ouvrir un port
		serialPort = (SerialPort)portId.open(this.getClass().getName(), TIME_ATTENTE_MS);

		// Paramètrage du port
		serialPort.setSerialPortParams(comOption.getSpeed(), comOption.getDataBit(), comOption.getStopBit(), comOption.getParity());
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

		// Ouverture des flux
		outputStream = serialPort.getOutputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		}

	@Override
	public void disconnect() throws Exception
		{
		// Fermeture du port
		serialPort.close();

		// Fermeture des flux
		outputStream.close();
		bufferedReader.close();
		}

	@Override
	public void askAltitudeAsync() throws Exception
		{
		byte[] tabByte = TrameEncoder.coder(TRAME_QUESTION_ALTITUDE);
		outputStream.write(tabByte);
		}

	@Override
	public void askPressionAsync() throws Exception
		{
		byte[] tabByte = TrameEncoder.coder(TRAME_QUESTION_PRESSION);
		outputStream.write(tabByte);
		}

	@Override
	public void askTemperatureAsync() throws Exception
		{
		byte[] tabByte = TrameEncoder.coder(TRAME_QUESTION_TEMPERATURE);
		outputStream.write(tabByte);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public String getNamePort()
		{
		return portName;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMeteoServiceCallback(MeteoService_I meteoService)
		{
		setMeteoServiceCallback((MeteoServiceCallback_I)meteoService);
		}

	/**
	 * For post building
	 */
	@Override
	public void setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
		{
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void traiterDonnees()
		{

		try
			{
			String line = bufferedReader.readLine();

			float valeur = TrameDecoder.valeur(line);

			switch(TrameDecoder.dataType(line))
				{
				case ALTITUDE:
					{
					meteoServiceCallback.altitudePerformed(valeur);
					break;
					}

				case PRESSION:
					{
					meteoServiceCallback.pressionPerformed(valeur);
					break;
					}

				case TEMPERATURE:
					{
					meteoServiceCallback.temperaturePerformed(valeur);
					break;
					}
				default:
					{
					System.err.println("[ComConnexion](traiterDonnes) no datatype");
					}

				}
			}
		catch (Exception e)
			{
			System.err.println("[ComConnexion](traiterDonnes) error callback actionPerformed");
			}

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback;

	// Tools
	private SerialPort serialPort;
	private BufferedWriter writer;
	private OutputStream outputStream;
	private BufferedReader bufferedReader;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int TIME_ATTENTE_MS = 10000;
	private static final String TRAME_QUESTION_PRESSION = "010000";
	private static final String TRAME_QUESTION_TEMPERATURE = "010100";
	private static final String TRAME_QUESTION_ALTITUDE = "010200";
	}
