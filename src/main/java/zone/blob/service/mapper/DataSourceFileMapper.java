package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.DataSourceFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataSourceFile} and its DTO {@link DataSourceFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataSourceMapper.class})
public interface DataSourceFileMapper extends EntityMapper<DataSourceFileDTO, DataSourceFile> {

    @Mapping(source = "dataSource.id", target = "dataSourceId")
    DataSourceFileDTO toDto(DataSourceFile dataSourceFile);

    @Mapping(source = "dataSourceId", target = "dataSource")
    DataSourceFile toEntity(DataSourceFileDTO dataSourceFileDTO);

    default DataSourceFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataSourceFile dataSourceFile = new DataSourceFile();
        dataSourceFile.setId(id);
        return dataSourceFile;
    }
}
