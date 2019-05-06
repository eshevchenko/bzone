package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link zone.blob.domain.DataSourceFile} entity.
 */
public class DataSourceFileDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String path;

    @NotNull
    private Integer size;


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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

        DataSourceFileDTO dataSourceFileDTO = (DataSourceFileDTO) o;
        if (dataSourceFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataSourceFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataSourceFileDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", size=" + getSize() +
            ", dataSource=" + getDataSourceId() +
            "}";
    }
}
