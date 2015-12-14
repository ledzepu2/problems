package statistics.utils;

import java.util.Arrays;

public class StatsUtil {


    public static float doVarianceImperatively(int[] sample) {
        float average = 0;
        for (float p : sample) {
            average += p;
        }
        average /= sample.length;

        float variance = 0;
        for (float p : sample) {
            variance += (p - average) * (p - average);
        }
        return variance / sample.length;
    }

    public static float doVarianceViaStream(int[] sample) {
        Double average = Arrays.stream(sample).parallel().average().getAsDouble();
        Double variance = Arrays.stream(sample).parallel()
                .mapToDouble(p -> ((new Double(p) - average) * (new Double(p) - average))).
                        sum() / sample.length;

        return variance.floatValue();
    }

    public static int calculateMin(int[] sample) {
        return Arrays.stream(sample).min().getAsInt();

    }

    public static int calculatMaximum(int[] sample) {
        return Arrays.stream(sample).max().getAsInt();
    }

    public static float calculatMean(int[] sample) {

        return Arrays.stream(sample).reduce(0,Integer::sum)/sample.length;
    }
}