package fit.biktjv.customersclient;

public record RcptDTO(Long id, String dsc, Long custId) {
    public RcptDTO(String dsc, Long custId) {
        this(null, dsc, custId);
    }
}
