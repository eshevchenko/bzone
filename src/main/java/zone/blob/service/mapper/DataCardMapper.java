package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.DataCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataCard} and its DTO {@link DataCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataSourceMapper.class})
public interface DataCardMapper extends EntityMapper<DataCardDTO, DataCard> {

    @Mapping(source = "dataSource.id", target = "dataSourceId")
    DataCardDTO toDto(DataCard dataCard);

    @Mapping(source = "dataSourceId", target = "dataSource")
    DataCard toEntity(DataCardDTO dataCardDTO);

    default DataCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataCard dataCard = new DataCard();
        dataCard.setId(id);
        return dataCard;
    }
}
