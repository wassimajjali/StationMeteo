
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.spec.com.port.MeteoPortDetectionServiceFactory_I;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionServiceFactory implements MeteoPortDetectionServiceFactory_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoPortDetectionServiceFactory()
		{
		this.create();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public MeteoPortDetectionService_I create()
		{
		return new MeteoPortDetectionService();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
