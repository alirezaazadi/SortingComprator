import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.security.SecureRandom;

/*
Draw chart, and test sorting algorithms
 */


public class buildChart extends ApplicationFrame {
    private int range = 0;
    private int inputCount = 0;
    private int step = 0;
    private int[] mergeInputs;
    private int[] insertionInputs;
    SecureRandom securerandom;


    buildChart(int range, int inputCount, int step) {
        super("Sorting Comparison");
        this.inputCount = inputCount;
        this.range = range;
        this.step = step;
        securerandom = new SecureRandom();
    }

    public void startTesting() {
        creatMainPanel();
        this.pack();
        this.setVisible(true);
    }

    private void creatMainPanel() {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Sorting Comparison",
                "Input Count",
                "Time (sec)",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
                
        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xylineChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);

    }

    private XYDataset createDataset() {

        final XYSeries merge = new XYSeries("Merge Sort");
        final XYSeries insertion = new XYSeries("Insertion Sort");

        //creat two objects ( as lines for merge and insertion sort )

        /*
        Creat two thread (beside main thread), one of them for merge sort, the other one
        for insertion sort.
        because of these multi threads, we can see live result in output chart
         */

        try {
            new Thread(() -> {
                fillDataSets(merge, 1);
            }).start();
            new Thread(() -> {
                fillDataSets(insertion, 2);
            }).start();
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
        ;

        //collect all result and add them to chart panel

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(insertion);
        dataset.addSeries(merge);
        return dataset;


    }

    private void fillDataSets(XYSeries sortingAlgorithm, int algorithmType) throws ArrayIndexOutOfBoundsException {
        /*
        fill two array ( base on sorting type ) by random numbers
        and give call a sorting algorithm from SortingAlgorithm class
        and add the result ( input count as i and sorting duration ) to passed object
         */

        for (int i = 1; i <= inputCount; ){
            if (algorithmType == 1)
                creatRandomInputsMerge(i);
            else
                creatRandomInputsInsertion(i);

            sortingAlgorithm.add(i, getLongTime(algorithmType));

            if (step == 0) i += Math.pow(10, String.format("%d", i).length() - 1);
            // to get our result in less time, we change the loop step ( greater number == greater step )
            else
                i += step;
            //use, pre-defined step, which we got it from the user
        }
    }

    private void creatRandomInputsInsertion(int inputCount) throws ArrayIndexOutOfBoundsException {
        //creat array with random numbers in special range and in special count
        insertionInputs = new int[inputCount];
        insertionInputs = securerandom.ints(inputCount, 0, range).toArray();
    }

    private void creatRandomInputsMerge(int inputCount) throws ArrayIndexOutOfBoundsException {
        mergeInputs = new int[inputCount];
        mergeInputs = securerandom.ints(inputCount, 0, range).toArray();
    }

    private long getLongTime(int sortingType) {
        /*
        call a sorting algorithm based on the the type, and return
        sorting time .
        change the result time from nano second to second :)
         */
        long start = System.nanoTime();
        switch (sortingType) {
            case 1:
                SortingAlgorithms.mergeSort(mergeInputs, 0, mergeInputs.length - 1);
                break;
            case 2:
                SortingAlgorithms.insertionSort(insertionInputs);
                break;
            default:
                break;
        }
        return (System.nanoTime() - start) / 1000000000;
    }
}
