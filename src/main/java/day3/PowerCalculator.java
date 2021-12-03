package day3;

import lombok.Getter;
import util.IterativeSolver;

import java.util.List;

@Getter
public class PowerCalculator implements IterativeSolver {
    private long gammaRate;
    private long epsilonRate;
    private long oxygenGenerator;
    private long cO2Scrubber;
    private String totalBinaryString = "";


    @Override
    public void iterate(String currentBit) {
        final int zeroOccurrence = currentBit.replaceAll("1", "").length();
        final int oneOccurrence = currentBit.length() - zeroOccurrence;
        if (zeroOccurrence > oneOccurrence) {
            totalBinaryString += "0";
        } else if (zeroOccurrence < oneOccurrence) {
            totalBinaryString += "1";
        }
    }

    public void convertPower(final List<String> bits) {
        gammaRate = Integer.parseInt(totalBinaryString, 2);
        //todo: find the best fitting bit => oxygen

        epsilonRate = Integer.parseInt(invertBinary(totalBinaryString), 2);
        //todo: find the best fitting bit => CO2

    }

    private String invertBinary(final String binaryString) {
        return binaryString.replaceAll("0", "x").replaceAll("1", "0").replaceAll("x", "1");
    }

    public long getPower() {
        return gammaRate * epsilonRate;
    }

    public long getLifeSupportRating() {
        return oxygenGenerator * cO2Scrubber;
    }
}
