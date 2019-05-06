package zone.blob.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Flow.
 */
@Entity
@Table(name = "flow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "config", nullable = false)
    private String config;

    @NotNull
    @Column(name = "context", nullable = false)
    private String context;

    @NotNull
    @Column(name = "active_step", nullable = false)
    private String activeStep;

    @ManyToOne
    @JsonIgnoreProperties("flows")
    private DataCard dataCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public Flow config(String config) {
        this.config = config;
        return this;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getContext() {
        return context;
    }

    public Flow context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getActiveStep() {
        return activeStep;
    }

    public Flow activeStep(String activeStep) {
        this.activeStep = activeStep;
        return this;
    }

    public void setActiveStep(String activeStep) {
        this.activeStep = activeStep;
    }

    public DataCard getDataCard() {
        return dataCard;
    }

    public Flow dataCard(DataCard dataCard) {
        this.dataCard = dataCard;
        return this;
    }

    public void setDataCard(DataCard dataCard) {
        this.dataCard = dataCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flow)) {
            return false;
        }
        return id != null && id.equals(((Flow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Flow{" +
            "id=" + getId() +
            ", config='" + getConfig() + "'" +
            ", context='" + getContext() + "'" +
            ", activeStep='" + getActiveStep() + "'" +
            "}";
    }
}
