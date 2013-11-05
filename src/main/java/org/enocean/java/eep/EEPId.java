package org.enocean.java.eep;

public class EEPId {

    private String id;

    /**
     * Some devices do not behave exactly as specified in the EEP. They get a
     * variant (mostly manufacturer name) to separate them from the real EEP
     * implementations.
     */
    private String variant;

    public EEPId(String id) {
        this.id = id;
    }

    public EEPId(String id, String variant) {
        this.id = id;
        this.variant = variant;
    }

    public String getId() {
        if (variant != null) {
            return id + "-" + variant;
        }
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((variant == null) ? 0 : variant.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EEPId other = (EEPId) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (variant == null) {
            if (other.variant != null) {
                return false;
            }
        } else if (!variant.equals(other.variant)) {
            return false;
        }
        return true;
    }

}
