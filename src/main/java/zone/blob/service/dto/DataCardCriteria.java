package zone.blob.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import zone.blob.domain.enumeration.DataCardType;
import zone.blob.domain.enumeration.DataCardStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link zone.blob.domain.DataCard} entity. This class is used
 * in {@link zone.blob.web.rest.DataCardResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data-cards?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataCardCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DataCardType
     */
    public static class DataCardTypeFilter extends Filter<DataCardType> {

        public DataCardTypeFilter() {
        }

        public DataCardTypeFilter(DataCardTypeFilter filter) {
            super(filter);
        }

        @Override
        public DataCardTypeFilter copy() {
            return new DataCardTypeFilter(this);
        }

    }
    /**
     * Class for filtering DataCardStatus
     */
    public static class DataCardStatusFilter extends Filter<DataCardStatus> {

        public DataCardStatusFilter() {
        }

        public DataCardStatusFilter(DataCardStatusFilter filter) {
            super(filter);
        }

        @Override
        public DataCardStatusFilter copy() {
            return new DataCardStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DataCardTypeFilter type;

    private DataCardStatusFilter status;

    private LongFilter dataSourceId;

    public DataCardCriteria(){
    }

    public DataCardCriteria(DataCardCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.dataSourceId = other.dataSourceId == null ? null : other.dataSourceId.copy();
    }

    @Override
    public DataCardCriteria copy() {
        return new DataCardCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DataCardTypeFilter getType() {
        return type;
    }

    public void setType(DataCardTypeFilter type) {
        this.type = type;
    }

    public DataCardStatusFilter getStatus() {
        return status;
    }

    public void setStatus(DataCardStatusFilter status) {
        this.status = status;
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
        final DataCardCriteria that = (DataCardCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dataSourceId, that.dataSourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        status,
        dataSourceId
        );
    }

    @Override
    public String toString() {
        return "DataCardCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (dataSourceId != null ? "dataSourceId=" + dataSourceId + ", " : "") +
            "}";
    }

}
