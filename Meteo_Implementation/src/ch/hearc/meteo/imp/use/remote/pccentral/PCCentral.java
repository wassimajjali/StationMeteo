
package ch.hearc.meteo.imp.use.remote.pccentral;

import java.net.ConnectException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;

public class PCCentral implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral(AffichageOptions affichageOptions)
		{
		this.affichageOptions = affichageOptions;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run()
		{
		try
			{
			server();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			System.err.println("Remote afficheur creator refused");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() throws RemoteException, ConnectException
		{
		this.remoteAfficheurCreator = (RemoteAfficheurCreator)RemoteAfficheurCreator.getInstance();
		this.afficheurServiceCentral = (new AfficheurSimulateurFactory()).createOnCentralPC(affichageOptions, null);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private AffichageOptions affichageOptions;

	//Tools
	private RemoteAfficheurCreator remoteAfficheurCreator;

	private AfficheurService_I afficheurServiceCentral;
	private String ip;
	}
