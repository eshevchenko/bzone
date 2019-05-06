package zone.blob.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import zone.blob.domain.enumeration.ColumnDataType;

/**
 * A DataCardColumn.
 */
@Entity
@Table(name = "data_card_column")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataCardColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private ColumnDataType dataType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dataCardColumns")
    private DataCard dataCard;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dataCardColumns")
    private DataSourceColumn dataSourceColumn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public DataCardColumn dataType(ColumnDataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    public DataCard getDataCard() {
        return dataCard;
    }

    public DataCardColumn dataCard(DataCard dataCard) {
        this.dataCard = dataCard;
        return this;
    }

    public void setDataCard(DataCard dataCard) {
        this.dataCard = dataCard;
    }

    public DataSourceColumn getDataSourceColumn() {
        return dataSourceColumn;
    }

    public DataCardColumn dataSourceColumn(DataSourceColumn dataSourceColumn) {
        this.dataSourceColumn = dataSourceColumn;
        return this;
    }

    public void setDataSourceColumn(DataSourceColumn dataSourceColumn) {
        this.dataSourceColumn = dataSourceColumn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCardColumn)) {
            return false;
        }
        return id != null && id.equals(((DataCardColumn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataCardColumn{" +
            "id=" + getId() +
            ", dataType='" + getDataType() + "'" +
            "}";
    }
}
