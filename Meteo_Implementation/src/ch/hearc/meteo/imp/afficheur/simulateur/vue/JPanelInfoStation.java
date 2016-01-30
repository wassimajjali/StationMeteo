
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

//import ch.hearc.meteo.imp.afficheur.real.vue2.MagasinImages;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelInfoStation extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInfoStation(AfficheurServiceMOO afficheurServiceMOO2)
		{
		inctemp = 0;
		incpression = 0;
		incalt = 0;
		this.afficheurServiceMOO = afficheurServiceMOO2;

		geometry();
		control();
		apparence();

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{

		for(MeteoEvent t:afficheurServiceMOO.getListTemperature())
			{
			datasetTemperature.addValue(t.getValue(), "temperature", new Integer(inctemp));

			inctemp++;
			}

		for(MeteoEvent p:afficheurServiceMOO.getListPression())
			{
			datasetPression.addValue(p.getValue(), "pression", new Integer(incpression));
			incpression++;
			}
		//datasetAltitude.clear();
		for(MeteoEvent a:afficheurServiceMOO.getListAltitude())
			{
			datasetAltitude.addValue(a.getValue(), "altitude", new Integer(incalt));
			incalt++;
			}

		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jPanelSlider.updateMeteoServiceOptions(meteoServiceOptions);
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

	private void geometry()
		{

		initChart();
		jPanelSlider = new JPanelSlider(afficheurServiceMOO);

		GridLayout grid = new GridLayout(0, 3, 50, 0);
		//		Box boxV = Box.createVerticalBox();
		//		boxV.add(Box.createVerticalStrut(15));
		//		boxV.add(cPanelPression);
		//		boxV.add(cPanelAltitude);

		//	add(cPanelTemperature);
		add(cPanelPression);
		add(cPanelAltitude);
		add(jPanelSlider);
		this.setLayout(grid);

		}

	private void control()
		{
		// TODO Auto-generated method stub

		}

	private void apparence()
		{

		}

	private void initChart()
		{
		datasetTemperature = new DefaultCategoryDataset();
		final JFreeChart barChartTemperature = ChartFactory.createLineChart("Temperature", "temps", "degre", datasetTemperature, PlotOrientation.VERTICAL, false, true, false);
		barChartTemperature.setBorderVisible(false);
		CategoryPlot plot = (CategoryPlot)barChartTemperature.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setRange(1, 10);
		rangeAxis.setUpperMargin(0.1);
		rangeAxis.setLowerMargin(-0.5);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		URL url = null;
		try
			{
			url = new URL("http://medias.rsr.ch/rsrsavoirs/science/2011/science-terre-20110517142903-7169-gallery.jpg?rand=913006883");
			}
		catch (MalformedURLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		BufferedImage image;
		try
			{
			image = ImageIO.read(url);
			plot.setBackgroundImage(image);
			}
		catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

		cPanelTemperature = new ChartPanel(barChartTemperature);

		datasetPression = new DefaultCategoryDataset();
		JFreeChart barChartPression = ChartFactory.createLineChart("Pression", "temps", "pression", datasetPression, PlotOrientation.VERTICAL, false, true, false);
		barChartPression.setBorderVisible(false);

		CategoryPlot plot4 = (CategoryPlot)barChartPression.getPlot();
		plot4.setBackgroundPaint(Color.lightGray);
		plot4.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		NumberAxis rangeAxis4 = (NumberAxis)plot.getRangeAxis();
		rangeAxis4.setUpperMargin(0.1);
		rangeAxis4.setLowerMargin(-0.5);
		rangeAxis4.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		URL url3 = null;
		try
			{
			url3 = new URL("http://capucine112.free.fr/eezibleed/pression2.jpg");
			}
		catch (MalformedURLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		BufferedImage image4;
		try
			{
			image4 = ImageIO.read(url3);
			plot4.setBackgroundImage(image4);
			}
		catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

		cPanelPression = new ChartPanel(barChartPression);

		datasetAltitude = new DefaultCategoryDataset();
		JFreeChart barChartAltitude = ChartFactory.createLineChart("Altitude", "temps", "altitude", datasetAltitude, PlotOrientation.VERTICAL, false, true, false);
		barChartAltitude.setBorderVisible(true);

		CategoryPlot plot3 = (CategoryPlot)barChartAltitude.getPlot();
		plot3.setBackgroundPaint(Color.lightGray);
		plot3.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		NumberAxis rangeAxis3 = (NumberAxis)plot.getRangeAxis();
		rangeAxis3.setUpperMargin(0.1);
		rangeAxis3.setLowerMargin(-0.5);
		rangeAxis3.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		URL url2 = null;
		try
			{
			url2 = new URL("https://www.google.ch/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRw&url=http%3A%2F%2Fwww.brainstorm-vzw.be%2Fhigh-altitude-balloon%2F&ei=jyhwVYSxJuHRywO1p4HQDg&bvm=bv.94911696,d.bGQ&psig=AFQjCNEanIbMOpf5EXtSLLBmteg7uZ_Ipw&ust=1433500175980506");
			}
		catch (MalformedURLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		BufferedImage image3;
		try
			{
			image3 = ImageIO.read(url2);
			plot3.setBackgroundImage(image3);
			}
		catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

		cPanelAltitude = new ChartPanel(barChartAltitude);

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private int inctemp;
	private int incalt;
	private int incpression;
	private AfficheurServiceMOO afficheurServiceMOO;
	private DefaultCategoryDataset datasetTemperature = new DefaultCategoryDataset();
	private DefaultCategoryDataset datasetPression = new DefaultCategoryDataset();
	private DefaultCategoryDataset datasetAltitude = new DefaultCategoryDataset();
	private ChartPanel cPanelTemperature;
	private ChartPanel cPanelAltitude;
	private ChartPanel cPanelPression;
	private ChartPanel cPanelTempAffichage;
	private JPanelSlider jPanelSlider;

	}
