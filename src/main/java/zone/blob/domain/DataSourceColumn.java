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
 * A DataSourceColumn.
 */
@Entity
@Table(name = "data_source_column")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataSourceColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private ColumnDataType dataType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dataSourceColumns")
    private DataSource dataSource;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataSourceColumn name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public DataSourceColumn dataType(ColumnDataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public DataSourceColumn dataSource(DataSource dataSource) {
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
        if (!(o instanceof DataSourceColumn)) {
            return false;
        }
        return id != null && id.equals(((DataSourceColumn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataSourceColumn{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dataType='" + getDataType() + "'" +
            "}";
    }
}
