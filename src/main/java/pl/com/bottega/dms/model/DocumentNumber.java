package pl.com.bottega.dms.model;

public class DocumentNumber {

    private String number;

    public DocumentNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentNumber that = (DocumentNumber) o;

        return number.equals(that.number);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
