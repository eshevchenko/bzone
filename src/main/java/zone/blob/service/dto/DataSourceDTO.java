package zone.blob.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link zone.blob.domain.DataSource} entity.
 */
public class DataSourceDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataSourceDTO dataSourceDTO = (DataSourceDTO) o;
        if (dataSourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataSourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataSourceDTO{" +
            "id=" + getId() +
            "}";
    }
}
