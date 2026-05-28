package rncp.backend.dto;

public class VideoUploadResponse {

    private String url;
    private String secureUrl;
    private String publicId;

    public VideoUploadResponse(String url, String secureUrl, String publicId) {
        this.url = url;
        this.secureUrl = secureUrl;
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
