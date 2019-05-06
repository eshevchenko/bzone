package zone.blob.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link zone.blob.domain.Flow} entity.
 */
public class FlowDTO implements Serializable {

    private Long id;

    @NotNull
    private String config;

    @NotNull
    private String context;

    @NotNull
    private String activeStep;


    private Long dataCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(String activeStep) {
        this.activeStep = activeStep;
    }

    public Long getDataCardId() {
        return dataCardId;
    }

    public void setDataCardId(Long dataCardId) {
        this.dataCardId = dataCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FlowDTO flowDTO = (FlowDTO) o;
        if (flowDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), flowDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FlowDTO{" +
            "id=" + getId() +
            ", config='" + getConfig() + "'" +
            ", context='" + getContext() + "'" +
            ", activeStep='" + getActiveStep() + "'" +
            ", dataCard=" + getDataCardId() +
            "}";
    }
}
