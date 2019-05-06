package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.DataSourceColumnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataSourceColumn} and its DTO {@link DataSourceColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataSourceMapper.class})
public interface DataSourceColumnMapper extends EntityMapper<DataSourceColumnDTO, DataSourceColumn> {

    @Mapping(source = "dataSource.id", target = "dataSourceId")
    DataSourceColumnDTO toDto(DataSourceColumn dataSourceColumn);

    @Mapping(source = "dataSourceId", target = "dataSource")
    DataSourceColumn toEntity(DataSourceColumnDTO dataSourceColumnDTO);

    default DataSourceColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataSourceColumn dataSourceColumn = new DataSourceColumn();
        dataSourceColumn.setId(id);
        return dataSourceColumn;
    }
}
