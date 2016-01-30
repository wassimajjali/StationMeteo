
package ch.hearc.meteo.imp.use.local;

import java.util.List;

import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.imp.com.real.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class UseSimple
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
		catch (MeteoServiceException e)
			{
			e.printStackTrace();
			}
		}

	public static void main() throws MeteoServiceException
		{
		MeteoPortDetectionServiceFactory meteoPortDetectionServiceFactory = new MeteoPortDetectionServiceFactory();
		List<String> listPorts = meteoPortDetectionServiceFactory.create().findListPortMeteo();

		System.out.println(listPorts);
		MeteoFactory meteoFactory = new MeteoFactory(listPorts.get(0), new ComOption());

		use(meteoFactory.getMeteoService());

		//		MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create("COM1");
		//		use(meteoService);

		}

	public static void use(MeteoService_I meteoService) throws MeteoServiceException
		{
		meteoService.connect();

		meteoService.addMeteoListener(new MeteoListener_I()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					System.out.println(event);
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					System.out.println(event);
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					System.out.println(event);
					}
			});

		scenario(meteoService); // exemple!
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Exemple pour mettre à l'épreuve la logique de fonctionnement
	 * Checker dans la console que la logique verbeuse afficher est correcte
	 * </pre>
	 */
	private static void scenario(MeteoService_I meteoService) throws MeteoServiceException
		{
		MeteoServiceOptions meteoServiceOptions1 = new MeteoServiceOptions(800, 1000, 1200);
		MeteoServiceOptions meteoServiceOptions2 = new MeteoServiceOptions(100, 100, 100);

		for(int i = 1; i <= 2; i++)
			{
			meteoService.start(meteoServiceOptions1);
			meteoService.start(meteoServiceOptions2);
			sleep(3000);
			meteoService.stop();
			sleep(3000);
			}

		meteoService.disconnect();
		sleep(100);
		meteoService.start(meteoServiceOptions2);
		}

	private static void sleep(long delayMS)
		{
		System.out.println("sleep main: " + delayMS);
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	}
