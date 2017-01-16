package system;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.PlotOrientation;

@SuppressWarnings("serial")
public class BarChart extends JFrame {
        public BarChart() {
                final CategoryDataset dataset = createDataset();
                final JFreeChart chart = createChart(dataset);
             // add the chart to a panel...
        		final ChartPanel chartPanel = new ChartPanel(chart);
        		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        		setContentPane(chartPanel);
        		pack();
        		setVisible(true);
        		setLocationRelativeTo(null);
        		setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private CategoryDataset createDataset() {
                DefaultCategoryDataset result = new DefaultCategoryDataset();

                result.addValue(5, "Beer", "Rustur 1");
                result.addValue(10, "Cider", "Rustur 1");
                result.addValue(15, "Soda", "Rustur 1");
                result.addValue(20, "Cocoa", "Rustur 1");
                result.addValue(25, "Other", "Rustur 1");

                result.addValue(15, "Beer", "Rustur 2");
                result.addValue(20, "Cider", "Rustur 2");
                result.addValue(25, "Soda", "Rustur 2");
                result.addValue(30, "Cocoa", "Rustur 2");
                result.addValue(35, "Other", "Rustur 2");

                result.addValue(25, "Beer", "Rustur 3");
                result.addValue(30, "Cider", "Rustur 3");
                result.addValue(35, "Soda", "Rustur 3");
                result.addValue(40, "Cocoa", "Rustur 3");
                result.addValue(45, "Other", "Rustur 3");
                return result;
        }

        private JFreeChart createChart(final CategoryDataset dataset) {
                final JFreeChart chart = ChartFactory.createStackedBarChart(
                                "Rustur VS. Rustur", "", "Amount", dataset,
                                PlotOrientation.VERTICAL, true, true, false);
                return chart;
        }

        public static void main(final String[] args) {
            new BarChart();    
        }
}