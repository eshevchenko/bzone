package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link zone.blob.domain.ColumnPreferences} entity.
 */
public class ColumnPreferencesDTO implements Serializable {

    private Long id;

    @NotNull
    private String key;

    @NotNull
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColumnPreferencesDTO columnPreferencesDTO = (ColumnPreferencesDTO) o;
        if (columnPreferencesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), columnPreferencesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ColumnPreferencesDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
