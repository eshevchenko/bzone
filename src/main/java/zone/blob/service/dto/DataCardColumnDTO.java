package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import zone.blob.domain.enumeration.ColumnDataType;

/**
 * A DTO for the {@link zone.blob.domain.DataCardColumn} entity.
 */
public class DataCardColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private ColumnDataType dataType;


    private Long dataCardId;

    private Long dataSourceColumnId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    public Long getDataCardId() {
        return dataCardId;
    }

    public void setDataCardId(Long dataCardId) {
        this.dataCardId = dataCardId;
    }

    public Long getDataSourceColumnId() {
        return dataSourceColumnId;
    }

    public void setDataSourceColumnId(Long dataSourceColumnId) {
        this.dataSourceColumnId = dataSourceColumnId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataCardColumnDTO dataCardColumnDTO = (DataCardColumnDTO) o;
        if (dataCardColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataCardColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataCardColumnDTO{" +
            "id=" + getId() +
            ", dataType='" + getDataType() + "'" +
            ", dataCard=" + getDataCardId() +
            ", dataSourceColumn=" + getDataSourceColumnId() +
            "}";
    }
}
