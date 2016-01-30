
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.vue.JFrameConfiguration;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try
			{
			main();
			}
		catch (Exception e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}

	public static void main() throws RemoteException, MeteoServiceException
		{
		new JFrameConfiguration();
		}
	}
