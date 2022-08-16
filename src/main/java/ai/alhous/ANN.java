package ai.alhous;

import java.io.File;
import java.io.IOException;

import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class ANN {

        public static void main(String[] args) throws IOException, InterruptedException {

                CSVRecordReader rr = new CSVRecordReader(1, ',');
                rr.initialize(new FileSplit(new File("C:/Users/Ali/Documents/dl4j/pronto/data/train.csv")));
                DataSetIterator train = new RecordReaderDataSetIterator(rr, 10000, 1, 1);

                CSVRecordReader rr2 = new CSVRecordReader(1, ',');
                rr2.initialize(new FileSplit(new File("C:/Users/Ali/Documents/dl4j/pronto/data/test.csv")));
                DataSetIterator test = new RecordReaderDataSetIterator(rr2, 500, 1, 1);

                DenseLayer hiddenLayer = new DenseLayer.Builder()
                                .name("Hidden Layer")
                                .nIn(1).nOut(3).activation(Activation.IDENTITY)
                                .build();

                OutputLayer outputLayer = new OutputLayer.Builder(LossFunction.MSE)
                                .name("Output Layer")
                                .nIn(3).nOut(1).activation(Activation.IDENTITY)
                                .build();

                MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                                .seed(10000)
                                .activation(Activation.IDENTITY)
                                .updater(new Adam(1e-3)).list()
                                .layer(0, hiddenLayer)
                                .layer(1, outputLayer)
                                .build();
                MultiLayerNetwork network = new MultiLayerNetwork(conf);
                network.init();

                network.setListeners(new ScoreIterationListener(1));

                for (int i = 0; i < 100; i++) {
                        network.fit(train);
                } 
                
                System.out.println("Score F1 : " + network.f1Score(test.next()));

        }
}
