package queryclusterer.file;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import queryclusterer.algorithm.Category;

public class XYSeriesDraw extends JFrame {    
	
	double[] results;
	String query;
	
	public XYSeriesDraw(double[] results, String query) {        
		super("XY Line Chart Example with JFreechart");  
		this.results = results;
		this.query = query;
		
		JPanel chartPanel = createChartPanel();        
		add(chartPanel, BorderLayout.CENTER);    
		
		
		
		setSize(640, 480);        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
		setLocationRelativeTo(null);   
		
	}    
	
	private JPanel createChartPanel() {  
		// creates a line chart object      
		// returns the chart panel   
		
		String chartTitle = "Distribution:"+query;
		String xAxisLabel = "Category";
		String yAxisLabel = "Relative Measure";
		
		XYDataset dataset = createDataset();
		
		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		
		return new ChartPanel(chart);
	}  
	
	private XYDataset createDataset() {  
			// creates an XY dataset...     
			// returns the dataset   
		double[] yData = results;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("Distribution");
		System.out.println(results);
			for(int i = 0 ; i < results.length ; i++){
				
				series.add(i, yData[i]);
			}
		
		
		dataset.addSeries(series);
		
		return dataset;
	}   
	

	
	
}