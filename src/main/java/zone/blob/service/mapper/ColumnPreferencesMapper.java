package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.ColumnPreferencesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ColumnPreferences} and its DTO {@link ColumnPreferencesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ColumnPreferencesMapper extends EntityMapper<ColumnPreferencesDTO, ColumnPreferences> {



    default ColumnPreferences fromId(Long id) {
        if (id == null) {
            return null;
        }
        ColumnPreferences columnPreferences = new ColumnPreferences();
        columnPreferences.setId(id);
        return columnPreferences;
    }
}
