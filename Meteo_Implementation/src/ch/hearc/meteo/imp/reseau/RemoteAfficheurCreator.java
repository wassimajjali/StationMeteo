
package ch.hearc.meteo.imp.reseau;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

/**
 * <pre>
 * One instance only (Singleton) on PC-Central
 * RMI-Shared
 * </pre>
 */
public class RemoteAfficheurCreator implements RemoteAfficheurCreator_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator() throws RemoteException
		{
		try
			{
			server();
			}
		catch (UnknownHostException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Remote use
	 */
	@Override
	public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL) throws RemoteException
		{
		MeteoServiceWrapper_I meteoServiceRemote = null;
			// client
			{
			meteoServiceRemote = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL);
			}

			// server
			{
			AfficheurService_I afficheurService = createAfficheurService(affichageOptions, meteoServiceRemote);
			// TODO share afficheurService

			AfficheurServiceWrapper afficheurServiceRemote = new AfficheurServiceWrapper(afficheurService);

			RmiURL afficheurServiceRmiURL = rmiUrl();

			RmiTools.shareObject(afficheurServiceRemote, afficheurServiceRmiURL);

			return afficheurServiceRmiURL; // Retourner le RMI-ID pour une
											// connection distante sur le
											// serveur d'affichage
			}
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance() throws RemoteException
		{
		if (INSTANCE == null)
			{
			INSTANCE = new RemoteAfficheurCreator();
			}

		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		AfficheurSimulateurFactory afficheurSimulateurFactory = new AfficheurSimulateurFactory();
		return afficheurSimulateurFactory.createOnCentralPC(affichageOptions, meteoServiceRemote);
		}

	private void server() throws RemoteException, UnknownHostException
		{
		RmiTools.shareObject(this, RMI_AFFICHEUR_CREATOR);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
		{
		String id = IdTools.createID(PREFIXE);
		RmiURL rmiURL = new RmiURL(id);
		return rmiURL;
		}

	private static RmiURL rmiUrlCentral()
		{
		try
			{
			String ipName = System.getProperty("ip", "127.0.0.1");
			InetAddress ip = InetAddress.getByName(ipName);
			RmiURL rmiURL = new RmiURL(PREFIXE, ip);
			return rmiURL;
			}
		catch (UnknownHostException e)
			{
			e.printStackTrace();
			}
		return null;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools
	private static RemoteAfficheurCreator_I INSTANCE = null;

	// Tools final
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	public static final int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static final String RMI_ID = PREFIXE;
	public static final RmiURL RMI_AFFICHEUR_CREATOR = rmiUrlCentral();
	}
