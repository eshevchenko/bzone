package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import zone.blob.domain.enumeration.DataCardType;
import zone.blob.domain.enumeration.DataCardStatus;

/**
 * A DTO for the {@link zone.blob.domain.DataCard} entity.
 */
public class DataCardDTO implements Serializable {

    private Long id;

    @NotNull
    private DataCardType type;

    @NotNull
    private DataCardStatus status;


    private Long dataSourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataCardType getType() {
        return type;
    }

    public void setType(DataCardType type) {
        this.type = type;
    }

    public DataCardStatus getStatus() {
        return status;
    }

    public void setStatus(DataCardStatus status) {
        this.status = status;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataCardDTO dataCardDTO = (DataCardDTO) o;
        if (dataCardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataCardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataCardDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataSource=" + getDataSourceId() +
            "}";
    }
}
