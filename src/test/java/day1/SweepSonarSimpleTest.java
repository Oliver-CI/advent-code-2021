package day1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SweepSonarSimpleTest {

    @Test
    public void mapDecrease(){
        // Arrange
        final SweepSonarSimple sweepSonarSimple = new SweepSonarSimple();
        // Act
        sweepSonarSimple.iterate("1");
        sweepSonarSimple.iterate("0");
        // Assert
        assertThat(sweepSonarSimple.getIncreaseTotal()).isEqualTo(0);
        assertThat(sweepSonarSimple.getDecreaseTotal()).isEqualTo(1);
    }

    @Test
    public void mapIncrease(){
        // Arrange
        final SweepSonarSimple sweepSonarSimple = new SweepSonarSimple();
        // Act
        sweepSonarSimple.iterate("0");
        sweepSonarSimple.iterate("1");
        // Assert
        assertThat(sweepSonarSimple.getIncreaseTotal()).isEqualTo(1);
        assertThat(sweepSonarSimple.getDecreaseTotal()).isEqualTo(0);
    }

    @Test
    public void setUpWorks(){
        // Arrange
        final SweepSonarSimple sweepSonarSimple = new SweepSonarSimple();
        // Act
        sweepSonarSimple.iterate("0");
        // Assert
        assertThat(sweepSonarSimple.getIncreaseTotal()).isEqualTo(0);
        assertThat(sweepSonarSimple.getDecreaseTotal()).isEqualTo(0);
    }

}
