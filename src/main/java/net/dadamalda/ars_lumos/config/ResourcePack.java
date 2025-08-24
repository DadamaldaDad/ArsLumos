package net.dadamalda.ars_lumos.config;

public enum ResourcePack {
    DETECT(""),
    NONE("default"),
    ARS_LOAFERS("loafers");

    private final String variantId;

    ResourcePack(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantId() {
        return this.variantId;
    }
}
