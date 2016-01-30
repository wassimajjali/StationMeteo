
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.real.com.ComConnexion;
import ch.hearc.meteo.imp.com.real.com.ComConnexions_I;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class MeteoFactory
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public MeteoFactory(String port, ComOption comOption)
		{
		this.port = port;
		this.comOption = comOption;

		ComConnexions_I comConnexion = new ComConnexion(this.port, this.comOption);
		meteoService = new MeteoService(comConnexion);
		comConnexion.setMeteoServiceCallback(meteoService);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setPort(String port)
		{
		this.port = port;
		}

	public void setComOption(ComOption comOption)
		{
		this.comOption = comOption;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public MeteoService_I getMeteoService()
		{
		return this.meteoService;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private String port;
	private ComOption comOption;

	// Output
	private MeteoService_I meteoService;
	}
