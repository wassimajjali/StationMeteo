
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelInfoSupp extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInfoSupp(AfficheurServiceMOO afficheurServiceMOO2)
		{
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
			dataset.setValue(t.getValue());
			}
		for(MeteoEvent p:afficheurServiceMOO.getListPression())
			{
			dataset1.setValue(p.getValue());
			}
		for(MeteoEvent a:afficheurServiceMOO.getListAltitude())
			{
			dataset2.setValue(a.getValue());
			}
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
	private void apparence()
		{
		// TODO Auto-generated method stub

		}

	private void control()
		{
		// TODO Auto-generated method stub

		}

	private void geometry()
		{
		//jPanelSlider=new JPanelSlider(afficheurServiceMOO);
		//GridLayout grid = new GridLayout(0, 2, 500, 100);
		GridLayout grid = new GridLayout(0, 3, 50, 0);
//		Box boxV = Box.createVerticalBox();
//		boxV.add(Box.createVerticalStrut(15));
		

		final ThermometerPlot plot3 = new ThermometerPlot(dataset);
        final JFreeChart chart = new JFreeChart("Temperature",  // chart title
                                          JFreeChart.DEFAULT_TITLE_FONT,
                                          plot3,                 // plot
                                          false);               // include legend



        plot3.setThermometerStroke(new BasicStroke(2.0f));
        plot3.setThermometerPaint(Color.lightGray);
        cPanelTemp = new ChartPanel(chart);

        add(cPanelTemp);
        setLayout(grid);
        //cPanelTempAffichage = new ChartPanel(chart);
        dataset1 = new DefaultValueDataset();
        dataset2 = new DefaultValueDataset();
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, dataset1);
        dialplot.setDataset(1, dataset2);
        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);
        dialplot.setDialFrame(standarddialframe);
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        DialTextAnnotation dialtextannotation = new DialTextAnnotation("Pression / Altitude");
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);
        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.darkGray);
        dialvalueindicator.setRadius(0.59999999999999998D);
        dialvalueindicator.setAngle(-103D);
        dialplot.addLayer(dialvalueindicator);
        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.red);
        dialvalueindicator1.setRadius(0.59999999999999998D);
        dialvalueindicator1.setAngle(-77D);
        dialplot.addLayer(dialvalueindicator1);
        StandardDialScale standarddialscale = new StandardDialScale(-40D, 2000D, -120D, -300D, 200D, 5);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        dialplot.addScale(0, standarddialscale);
        StandardDialScale standarddialscale1 = new StandardDialScale(0.0D, 2000D, -120D, -300D, 200D, 5);
        standarddialscale1.setTickRadius(0.5D);
        standarddialscale1.setTickLabelOffset(0.14999999999999999D);
        standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
        standarddialscale1.setMajorTickPaint(Color.red);
        standarddialscale1.setMinorTickPaint(Color.red);
        dialplot.addScale(1, standarddialscale1);
        dialplot.mapDatasetToScale(1, 1);
        StandardDialRange standarddialrange = new StandardDialRange(90D, 100D, Color.blue);
        standarddialrange.setScaleIndex(1);
        standarddialrange.setInnerRadius(0.58999999999999997D);
        standarddialrange.setOuterRadius(0.58999999999999997D);
        dialplot.addLayer(standarddialrange);
        org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
        pin.setRadius(0.55000000000000004D);
        dialplot.addPointer(pin);
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        dialplot.addPointer(pointer);
        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);
        JFreeChart jfreechart = new JFreeChart(dialplot);
        jfreechart.setTitle("Pression & Altitude");
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        //chartpanel.setPreferredSize(new Dimension(400, 400));
        add(chartpanel);
        //add(jPanelSlider);
    
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurServiceMOO afficheurServiceMOO;
	final private DefaultValueDataset dataset = new DefaultValueDataset();
	private ChartPanel cPanelTemp;
	private DefaultValueDataset dataset1;
    private DefaultValueDataset dataset2;
//    private JPanelSlider jPanelSlider;

	}
