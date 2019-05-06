package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import zone.blob.domain.enumeration.ColumnDataType;

/**
 * A DTO for the {@link zone.blob.domain.DataSourceColumn} entity.
 */
public class DataSourceColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ColumnDataType dataType;


    private Long dataSourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
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

        DataSourceColumnDTO dataSourceColumnDTO = (DataSourceColumnDTO) o;
        if (dataSourceColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataSourceColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataSourceColumnDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", dataSource=" + getDataSourceId() +
            "}";
    }
}
