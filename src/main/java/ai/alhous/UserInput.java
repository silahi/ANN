package ai.alhous;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.awt.*;

public class UserInput extends JFrame {
    JTextField tx1 = new JTextField(15);
    JTextField tx2 = new JTextField(15);

    public UserInput(MultiLayerNetwork model) {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        var p1 = new JPanel();
        p1.add(new JLabel("X1"));
        p1.add(tx1);

        var p2 = new JPanel();
        p2.add(new JLabel("X2"));
        p2.add(tx2);

        var pred_btn = new JButton("Predict Sum");
        var p6 = new JPanel();
        p6.add(pred_btn);

        JLabel true_sum = new JLabel("0");
        JLabel predicted_sum = new JLabel("0");

        var p3 = new JPanel();
        p3.add(new JLabel("Expected Value :"));
        p3.add(true_sum);

        var p4 = new JPanel();
        p4.add(new JLabel("Predicted Value : "));
        p4.add(predicted_sum);

        GridLayout v_layout = new GridLayout(5, 1);
        var root_panel = new JPanel();
        root_panel.setLayout(v_layout);

        root_panel.add(p1);
        root_panel.add(p2);
        root_panel.add(p6);
        root_panel.add(p3);
        root_panel.add(p4);

        add(root_panel);

        pred_btn.addActionListener(e -> {
            double x1 = Double.parseDouble(tx1.getText());
            double x2 = Double.parseDouble(tx1.getText());
            true_sum.setText((x1 + x2) + "");
            true_sum.repaint();

            INDArray in = Nd4j.create(new double[][] { { x1, x2 } });
            double res = model.output(in).getDouble(0, 0);
            predicted_sum.setText(res + "");
            predicted_sum.repaint();
        });

    }
}
