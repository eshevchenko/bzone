package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.DataSourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataSource} and its DTO {@link DataSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataSourceMapper extends EntityMapper<DataSourceDTO, DataSource> {



    default DataSource fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataSource dataSource = new DataSource();
        dataSource.setId(id);
        return dataSource;
    }
}
