package fit.biktjv.customersclient;

public record CustDTO(Long id, String name) {
    public CustDTO(String name) {
        this(null, name);
    }
}
