package day3;

import lombok.Getter;
import util.IterativeSolver;

import java.util.List;
import java.util.stream.Collectors;

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
        oxygenGenerator = Integer.parseInt(findBestMatch(bits, true), 2);


        epsilonRate = Integer.parseInt(invertBinary(totalBinaryString), 2);
        cO2Scrubber = Integer.parseInt(findBestMatch(bits, false), 2);
    }

    private String invertBinary(final String binaryString) {
        return binaryString.replaceAll("0", "x").replaceAll("1", "0").replaceAll("x", "1");
    }

    private String findBestMatch(final List<String> binaryStrings, boolean mostOccurrences) {
        var matches = binaryStrings;
        StringBuilder resultBinary = new StringBuilder();
        for (int i = 0; 1 < matches.size(); i++) {
            int startIndex = i;
            int endIndex = i + 1;
            String occurence = matches.stream().map(match -> match.substring(startIndex, endIndex)).collect(Collectors.joining());
            final int zeroOccurrence = occurence.replaceAll("1", "").length();
            final int oneOccurrence = occurence.length() - zeroOccurrence;
            if (mostOccurrences) {
                if (zeroOccurrence <= oneOccurrence) {
                    resultBinary.append("1");
                } else resultBinary.append("0");
            } else {
                if (zeroOccurrence <= oneOccurrence) {
                    resultBinary.append("0");
                } else resultBinary.append("1");
            }

            matches = matches.stream().filter(m -> m.startsWith(resultBinary.toString())).toList();
        }
        return matches.get(0);
    }

    public long getPower() {
        return gammaRate * epsilonRate;
    }

    public long getLifeSupportRating() {
        return oxygenGenerator * cO2Scrubber;
    }
}
