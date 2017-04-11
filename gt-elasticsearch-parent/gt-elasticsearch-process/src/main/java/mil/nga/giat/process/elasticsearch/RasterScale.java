package mil.nga.giat.process.elasticsearch;

import java.util.List;

public class RasterScale {

    private boolean isMinMaxInitialized = false;
    private boolean scaleSet = false;
    private float dataMin;
    private float dataMax;
    private float scaleMin;
    private float scaleMax;
    
    public RasterScale(List<Float> range) {
        if (null != range && range.size() == 1) {
            scaleSet = true;
            scaleMin = 0;
            scaleMax = range.get(0);
        } else if (null != range && range.size() == 2) {
            scaleSet = true;
            scaleMax = range.get(0);
            scaleMin = range.get(1);
            if (scaleMin > scaleMax) {
                scaleMax = range.get(1);
                scaleMin = range.get(0);
            }
        }
        
        if (scaleSet && scaleMax == scaleMin) {
            throw new IllegalArgumentException();
        }
    }
    
    public float scaleValue(float value) {
        float scaledValue = value;
        if (scaleSet) {
            return ((scaleMax - scaleMin) * (value - dataMin) / (dataMax - dataMin)) + scaleMin;
        }
        return scaledValue;
    }
    
    public void prepareScale(float value) {
        if (!scaleSet) return; 

        if (isMinMaxInitialized) {
            if (value < dataMin) {
                dataMin = value;
            }
            if (value > dataMax) {
                dataMax = value;
            }
        } else {
            dataMin = value;
            dataMax = value;
            isMinMaxInitialized = true;
        }
    }
    
    public boolean isScaleSet() {
        return scaleSet;
    }

    public float getDataMin() {
        return dataMin;
    }

    public float getDataMax() {
        return dataMax;
    }

    public float getScaleMin() {
        return scaleMin;
    }

    public float getScaleMax() {
        return scaleMax;
    }
}