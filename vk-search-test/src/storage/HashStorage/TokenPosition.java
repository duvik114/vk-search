package storage.HashStorage;

public class TokenPosition implements Comparable<TokenPosition> {
    private int variantNumber;
    private int positionInVariant;

    public TokenPosition(int variantNumber, int positionInVariant) {
        this.variantNumber = variantNumber;
        this.positionInVariant = positionInVariant;
    }

    @Override
    public int compareTo(TokenPosition tokenPosition) {
        return this.positionInVariant - tokenPosition.positionInVariant;
    }

    public int getVariantNumber() {
        return variantNumber;
    }

    public void setVariantNumber(int variantNumber) {
        this.variantNumber = variantNumber;
    }

    public int getPositionInVariant() {
        return positionInVariant;
    }

    public void setPositionInVariant(int positionInVariant) {
        this.positionInVariant = positionInVariant;
    }
}