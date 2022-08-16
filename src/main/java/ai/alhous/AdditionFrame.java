package ai.alhous;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.linalg.api.ndarray.INDArray; 
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.List;

public class AdditionFrame extends JFrame {
   public AdditionFrame(List<INDArray> y, List<String> title) {  
      setSize(1000, 700);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);

      JFreeChart chart = createChart(xy_dataset(y, title));

      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      chartPanel.setBackground(Color.white);
      add(chartPanel);
   }

   XYDataset xy_dataset(List<INDArray> ndseries, List<String> titles) {
      var dataset = new XYSeriesCollection();
      for (int j = 0; j < ndseries.size(); j++) {
         var series = new XYSeries(titles.get(j));
         for (int i = 0; i < ndseries.get(j).rows(); i++) {
            series.add(i, ndseries.get(j).getDouble(i, 0));
         }
         dataset.addSeries(series);
      }    
      return dataset;

   }

   private JFreeChart createChart(XYDataset dataset) {

      JFreeChart chart = ChartFactory.createXYLineChart(
            "Test Set",
            "Index",
            "Somme",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);

      XYPlot plot = chart.getXYPlot();

      var renderer = new XYLineAndShapeRenderer();
      renderer.setSeriesPaint(0, Color.BLUE);
      renderer.setSeriesPaint(0, Color.GREEN);
      renderer.setSeriesStroke(0, new BasicStroke(1.0f));

      plot.setRenderer(renderer);
      plot.setBackgroundPaint(Color.white);

      plot.setRangeGridlinesVisible(true);
      plot.setRangeGridlinePaint(Color.BLACK);

      plot.setDomainGridlinesVisible(true);
      plot.setDomainGridlinePaint(Color.BLACK);

      chart.getLegend().setFrame(BlockBorder.NONE);

      chart.setTitle(new TextTitle("Prediction Vs Expected",
            new Font("Serif", java.awt.Font.BOLD, 18)));

      return chart;
   }
}
