package zone.blob.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import zone.blob.domain.enumeration.DataCardType;

import zone.blob.domain.enumeration.DataCardStatus;

/**
 * A DataCard.
 */
@Entity
@Table(name = "data_card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private DataCardType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DataCardStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dataCards")
    private DataSource dataSource;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataCardType getType() {
        return type;
    }

    public DataCard type(DataCardType type) {
        this.type = type;
        return this;
    }

    public void setType(DataCardType type) {
        this.type = type;
    }

    public DataCardStatus getStatus() {
        return status;
    }

    public DataCard status(DataCardStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DataCardStatus status) {
        this.status = status;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public DataCard dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCard)) {
            return false;
        }
        return id != null && id.equals(((DataCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataCard{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
