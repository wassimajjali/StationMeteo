
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.BoxSerieTemporelle;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.JPanelStat;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelEvent extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelEvent(Stat stat, List<MeteoEvent> listMeteoEvent, String titre)
		{
		this.stat = stat;
		this.listMeteoEvent = listMeteoEvent;
		this.titre = titre;
		this.inctemp = 0;

		geometry();
		control();
		apparence();

		this.dataSeries = new XYSeries("Sous-titre du graphe");
		seriesCollection = new XYSeriesCollection();

		XYDataset xyDataset = addDataset(0.0, 0.0);
		JFreeChart jfreechart = createChart(xyDataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);

		BoxLayout Box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(Box);
		add(chartpanel);

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		//boxSerieTemnporelle.update();
		//panelStat.update();

		System.out.println("Valeur stat:" + stat.getLast());
		inctemp++;
		dataSeries.add(inctemp, stat.getLast());

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		panelStat = new JPanelStat(stat);
		boxSerieTemnporelle = new BoxSerieTemporelle(listMeteoEvent);

		panelStat.setMaximumSize(new Dimension(180, 100));
		boxSerieTemnporelle.setMaximumSize(new Dimension(250, 100));

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(panelStat);
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(boxSerieTemnporelle);
		boxH.add(Box.createHorizontalStrut(15));

		Box boxV = Box.createVerticalBox();
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(boxH);
		boxV.add(Box.createVerticalStrut(15));

		setLayout(new BorderLayout());
		add(boxV, BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder(titre));
		}

	private void apparence()
		{
		// rien
		}

	private void control()
		{
		// rien
		}

	private XYDataset addDataset(double x, double y)
		{

		dataSeries.add(x, y);
		seriesCollection.addSeries(dataSeries);

		return seriesCollection;
		}

	private JFreeChart createChart(XYDataset xydataset)
		{
		JFreeChart jfreechart = ChartFactory.createXYLineChart(titre, "Angle (Deg)", "Y", xydataset, PlotOrientation.VERTICAL, true, true, false);

		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		return jfreechart;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Stat stat;

	private List<MeteoEvent> listMeteoEvent;
	private String titre;
	private XYSeries dataSeries;
	private int inctemp;
	private XYSeriesCollection seriesCollection;

	// Tools
	private JPanelStat panelStat;
	private BoxSerieTemporelle boxSerieTemnporelle;
	}
