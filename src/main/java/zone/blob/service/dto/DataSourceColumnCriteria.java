package zone.blob.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import zone.blob.domain.enumeration.ColumnDataType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link zone.blob.domain.DataSourceColumn} entity. This class is used
 * in {@link zone.blob.web.rest.DataSourceColumnResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data-source-columns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataSourceColumnCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ColumnDataType
     */
    public static class ColumnDataTypeFilter extends Filter<ColumnDataType> {

        public ColumnDataTypeFilter() {
        }

        public ColumnDataTypeFilter(ColumnDataTypeFilter filter) {
            super(filter);
        }

        @Override
        public ColumnDataTypeFilter copy() {
            return new ColumnDataTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ColumnDataTypeFilter dataType;

    private LongFilter dataSourceId;

    public DataSourceColumnCriteria(){
    }

    public DataSourceColumnCriteria(DataSourceColumnCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.dataType = other.dataType == null ? null : other.dataType.copy();
        this.dataSourceId = other.dataSourceId == null ? null : other.dataSourceId.copy();
    }

    @Override
    public DataSourceColumnCriteria copy() {
        return new DataSourceColumnCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ColumnDataTypeFilter getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataTypeFilter dataType) {
        this.dataType = dataType;
    }

    public LongFilter getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(LongFilter dataSourceId) {
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
        final DataSourceColumnCriteria that = (DataSourceColumnCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(dataType, that.dataType) &&
            Objects.equals(dataSourceId, that.dataSourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        dataType,
        dataSourceId
        );
    }

    @Override
    public String toString() {
        return "DataSourceColumnCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (dataType != null ? "dataType=" + dataType + ", " : "") +
                (dataSourceId != null ? "dataSourceId=" + dataSourceId + ", " : "") +
            "}";
    }

}
