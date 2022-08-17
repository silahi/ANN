package ai.alhous;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class Addition {
    int sample_size = 10000;
    int min_range = 0;
    int max_range = 1;
    int batch_size = 200;
    int n_epocs = 100;

    int n_in = 2;
    int n_out = 1;
    int n_hidden = 10;

    Random random = new Random(123456);

    public Addition() {

        DenseLayer hidden_layer = new DenseLayer.Builder()
                .nIn(n_in).nOut(n_hidden)
                .activation(Activation.SIGMOID)
                .build();

        OutputLayer output_layer = new OutputLayer.Builder(LossFunction.MSE)
                .nIn(n_hidden).nOut(n_out).activation(Activation.IDENTITY)
                .build();

        MultiLayerNetwork network = new MultiLayerNetwork(
                new NeuralNetConfiguration.Builder()
                        .updater(new Adam())
                        .list()
                        .layer(0, hidden_layer)
                        .layer(1, output_layer)
                        .build());
        network.init();
        network.setListeners(new ScoreIterationListener(1));

        var x1 = generate_dataset(sample_size, 1);
        var x2 = generate_dataset(sample_size, 1);
        var x = Nd4j.hstack(x1, x2);
        var y = x1.add(x2);
        var train_set = get_dataset_iterator(x, y, batch_size);

        for (int i = 0; i < n_epocs; i++) {
            train_set.reset();
            network.fit(train_set);
        }

        var t1 = generate_dataset(50, 1);
        var t2 = generate_dataset(50, 1);
        var exp = t1.add(t2);
        var t = Nd4j.hstack(t1, t2);
        var test_set = get_dataset_iterator(t, exp, 50);

        var eval = network.evaluateRegression(test_set);
        System.out.println(eval.stats());

        var pred = network.output(t, false);

        var af = new AdditionFrame(Arrays.asList(exp, pred), Arrays.asList("Expected", "Predicted"));
        af.setVisible(true); 
        new UserInput(network).setVisible(true);
       

    }

    INDArray generate_dataset(int sample_size, int n_features) {
        var rng = Nd4j.random;
        INDArray x = rng.uniform(min_range, max_range, DataType.DOUBLE, sample_size, n_features);
        return x;
    }

    DataSetIterator get_dataset_iterator(INDArray x, INDArray y, int batch_size) {
        DataSet dataSet = new DataSet(x, y);
        List<DataSet> list_dataset = dataSet.asList();
        Collections.shuffle(list_dataset, random);
        return new ListDataSetIterator<>(list_dataset, batch_size);
    }

    public static void main(String[] args) {
        new Addition();
    }
}
