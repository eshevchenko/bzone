package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.DataCardColumnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataCardColumn} and its DTO {@link DataCardColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataCardMapper.class, DataSourceColumnMapper.class})
public interface DataCardColumnMapper extends EntityMapper<DataCardColumnDTO, DataCardColumn> {

    @Mapping(source = "dataCard.id", target = "dataCardId")
    @Mapping(source = "dataSourceColumn.id", target = "dataSourceColumnId")
    DataCardColumnDTO toDto(DataCardColumn dataCardColumn);

    @Mapping(source = "dataCardId", target = "dataCard")
    @Mapping(source = "dataSourceColumnId", target = "dataSourceColumn")
    DataCardColumn toEntity(DataCardColumnDTO dataCardColumnDTO);

    default DataCardColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataCardColumn dataCardColumn = new DataCardColumn();
        dataCardColumn.setId(id);
        return dataCardColumn;
    }
}
