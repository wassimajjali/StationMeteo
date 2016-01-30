
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.ConnectException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class PCLocal implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCLocal(MeteoService_I meteoService, MeteoServiceOptions meteoServiceOptions, String portCom, AffichageOptions affichageOptions, RmiURL rmiURLafficheurManager)
		{
		this.meteoService = meteoService;
		this.meteoServiceOptions = meteoServiceOptions;
		this.portCom = portCom;
		this.affichageOptions = affichageOptions;
		this.rmiURLafficheurManager = rmiURLafficheurManager;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run()
		{
		try
			{
			server(); // avant
			}
		catch (Exception e)
			{
			System.err.println("[PCLocal :  run : server : failed");
			}

		try
			{
			client(); // aprüs
			}
		catch (RemoteException e)
			{
			System.err.println("[PCLocal :  run : client : failed");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * Print data on the PCLocal
	 * Launch refresh thread
	 * @param afficheurServiceServer
	 * @param afficheurServiceLocal
	 * @param meteoService
	 */
	private void use(final AfficheurServiceWrapper_I afficheurServiceServer, final AfficheurService_I afficheurServiceLocal, final MeteoService_I meteoService)
		{
		meteoService.addMeteoListener(new MeteoListener_I()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					try
						{
						afficheurServiceLocal.printTemperature(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					try
						{
						afficheurServiceLocal.printPression(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					try
						{
						afficheurServiceLocal.printAltitude(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}
			});

		//Refresh slider data
		Thread threadPoolingOptions = new Thread(new Runnable()
			{

				@Override
				public void run()
					{

					while(true)
						{
						try
							{
							meteoServiceOptions = meteoService.getMeteoServiceOptions();
							System.out.println("Meteo : " + meteoServiceOptions);
							afficheurServiceLocal.updateMeteoServiceOptions(meteoServiceOptions);
							afficheurServiceServer.updateMeteoServiceOptions(meteoServiceOptions);
							}
						catch (RemoteException e)
							{
							System.err.println("Connexion impossible");
							}

						attendre(1000); //disons
						}
					}
			});

		threadPoolingOptions.start();
		}

	/*
	 *	Function sleep
	 */
	private void attendre(int time)
		{
		try
			{
			Thread.sleep(time);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Print temperature, altitude and pression on the PCCentral
	 * @param afficheurService	: PCCentral's remote control
	 * @throws MeteoServiceException
	 * @throws RemoteException
	 * @throws ConnectException
	 */
	private void work(final AfficheurServiceWrapper_I afficheurService) throws MeteoServiceException, RemoteException, ConnectException
		{
		meteoService.addMeteoListener(new MeteoListener_I()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					try
						{
						afficheurService.printTemperature(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					try
						{
						afficheurService.printPression(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					try
						{
						afficheurService.printAltitude(event);
						}
					catch (Exception e)
						{
						System.err.println("Connexion impossible");
						}
					}
			});
		}

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, RemoteException, ConnectException
		{
		//Create meteo service
		//		meteoService = (new MeteoServiceSimulatorFactory()).create(portCom);

		//Connection and start of the meteo service
		meteoService.connect();
		meteoService.start(meteoServiceOptions);

		meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
		String id = IdTools.createID(meteoService.getClass().getName());
		RmiTools.shareObject(meteoServiceWrapper, new RmiURL(id));

		afficheurServiceLocal = (new AfficheurSimulateurFactory()).createOnLocalPC(affichageOptions, meteoServiceWrapper);

		//Connection to the PC Central
		RemoteAfficheurCreator_I remoteAfficheurCreator = (RemoteAfficheurCreator_I)RmiTools.connectionRemoteObjectBloquant(RemoteAfficheurCreator.RMI_AFFICHEUR_CREATOR);

		//Create Panel into PC Central
		rmiURLafficheurManager = remoteAfficheurCreator.createRemoteAfficheurService(affichageOptions, new RmiURL(id));
		afficheurServiceServer = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);

		use(afficheurServiceServer, afficheurServiceLocal, meteoService);
		}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException
		{
		try
			{
			work(afficheurServiceServer);
			}
		catch (Exception e)
			{
			System.err.println("Connexion à l'afficheur impossible");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;

	// Tools
	private AfficheurService_I afficheurServiceLocal;
	private MeteoService_I meteoService;
	private MeteoServiceWrapper meteoServiceWrapper;

	private AfficheurServiceWrapper_I afficheurServiceServer;
	}
