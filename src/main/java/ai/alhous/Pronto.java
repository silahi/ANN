package ai.alhous;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Pronto {
    public INDArray relu(INDArray x) {
        return (x.getDouble(0, 0) > 0.0) ? x : Nd4j.zeros(1, 1);
    }
    
    


}
