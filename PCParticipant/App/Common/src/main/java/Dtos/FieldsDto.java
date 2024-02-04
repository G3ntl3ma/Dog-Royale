package Dtos;

public class FieldsDto {
    private final int count;
    private final int[] positions;

    public FieldsDto(int count, int[] positions) {
        this.count = count;
        this.positions = positions;
    }

    public int getCount() {
        return count;
    }

    public int[] getPositions() {
        return positions;
    }
}
