
package ch.hearc.meteo.imp.com.real;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.meteo.imp.com.real.com.ComPortDetection;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionService implements MeteoPortDetectionService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoPortDetectionService()
		{
		this.comPortDetection = new ComPortDetection();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Retourne la liste de tous les ports s�ries
	 */
	@Override
	public List<String> findListPortSerie()
		{
		return comPortDetection.findListPortSerie();
		}

	/**
	 * Implementation conseil:
	 * 		(C1) Catcher l'ouverture du port qui peut echouer si le port est deja ouvert, et renvoyer false (le port est deja utilis�)
	 * 		(C2) Envoyer une trame, attendre une r�ponse en tenant compte d'un timeout
	 *
	 * Output:
	 * 		Return true si station meteo est connect� au portName
	 * 		Return false si
	 * 				(1) pas sation meteo connect� au PortName
	 * 				(2) StationMeteo connecte au PortName mais deja en utilisation (impossible d'ouvrir le port alors)
	 */
	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS)
		{
		return comPortDetection.isStationMeteoAvailable(portName, timeoutMS);
		}

	/**
	 * Contraintes :
	 * 		(C1) Doit refermer les ports!
	 * 		(C2) Doit �tre safe (dans le sens ou un port com peut contenir un hardware sensible qui ne doit imp�rativement pas �tre d�ranger, ie aucune tentative d'ouverture de port autoris�e)
	 *
	 * Implementation conseil:
	 * 		(I1) Utiliser la m�thode  isStationMeteoAvailable(String portName)
	 * 		(I2) Pour satisfaire la contrainte C2
	 * 				Step1 : Utiliser findPortSerie (ci-dessus)																						---> listPortCom
	 * 				Step2 :	Soustraction de listPortExcluded � listPortCom	(via removeAll)															---> listPortCom (updater)
	 * 				Step3 : Instancien listPortComMeteoAvailable																					---> listPortComMeteoAvailable	(vide)
	 * 				Step4 :	Parcourir listPortCom et utiliser isStationMeteoAvailable (ci-dessous) pour peupler listPortComMeteoAvailable			---> listPortComMeteoAvailable
	 *
	 *  Output:
	 *  	Return la liste des ports surlesquels sont branch�s une station m�t�o (non encore utilis�e) , except listPortExcluded
	 */
	@Override
	public List<String> findListPortMeteo(List<String> listPortExcluded)
		{
		List<String> portsComm = findListPortSerie(); // Step 1

		portsComm.removeAll(listPortExcluded); // Step 2

		List<String> listPortComMeteoAvailable = new ArrayList<String>(); // Step 3

		// Step 4
		for(String portName:portsComm)
			{
			if (isStationMeteoAvailable(portName, TIMEOUT_MS))
				{
				listPortComMeteoAvailable.add(portName);
				}
			}

		return listPortComMeteoAvailable; // Output
		}

	/**
	 * Implementation conseil:
	 * 		(C1) Utiliser la m�thode
	 * 						findListPortMeteo(List<String> listPortExcluded)
	 * 			 avec une listPortExcluded qui existe (Instancier) mais de taille 0
	 */
	@Override
	public List<String> findListPortMeteo()
		{
		List<String> listPortExcluded = new ArrayList<String>(0);
		return findListPortMeteo(listPortExcluded);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ComPortDetection comPortDetection;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private final static int TIMEOUT_MS = 1000;

	}
