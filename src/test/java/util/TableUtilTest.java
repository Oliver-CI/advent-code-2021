package util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TableUtilTest {

    @Test
    void doubleValuesInColumn() {
        // Arrange
        int[][] array = new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}};
        // Act
        final List<List<Integer>> integers = TableUtil.columnOperation(array, (x) -> x * 2);
        // Assert
        assertThat(integers).isNotEmpty()
                .hasSize(3);
        assertThat(integers.get(0))
                .containsExactly(2, 8);
        assertThat(integers.get(1))
                .containsExactly(4, 10);
        assertThat(integers.get(2))
                .containsExactly(6, 12);
    }

    @Test
    void checkEvenNumber() {
        // Arrange
        int[][] array = new int[][]{new int[]{1}, new int[]{2}, new int[]{3}, new int[]{4}};
        // Act
        final List<List<Boolean>> bools = TableUtil.columnOperation(array, (x) -> (x % 2 == 0));
        // Assert
        assertThat(bools).isNotEmpty()
                .hasSize(1);
        assertThat(bools.get(0)).containsExactly(false, true, false, true);

    }
}
