public class DataPoint {
    public int[] features;
    int index;
    int expected;
    public DataPoint(String[] stringFeatures, int index,int expected) {
        this.index = index;
        this.expected = expected;
        this.features = new int[stringFeatures.length-1];
        for(int i = 1 ; i < stringFeatures.length ; i++){
            features[i-1] = Integer.parseInt(stringFeatures[i]);
        }
    }


}
