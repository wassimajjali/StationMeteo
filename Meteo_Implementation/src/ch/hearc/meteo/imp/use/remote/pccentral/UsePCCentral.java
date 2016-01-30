
package ch.hearc.meteo.imp.use.remote.pccentral;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;

public class UsePCCentral
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		String titre = "Serveur";
		AffichageOptions affichageOptions = new AffichageOptions(-1, titre);

		new PCCentral(affichageOptions).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
