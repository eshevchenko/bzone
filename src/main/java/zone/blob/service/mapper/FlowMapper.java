package zone.blob.service.mapper;

import zone.blob.domain.*;
import zone.blob.service.dto.FlowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Flow} and its DTO {@link FlowDTO}.
 */
@Mapper(componentModel = "spring", uses = {DataCardMapper.class})
public interface FlowMapper extends EntityMapper<FlowDTO, Flow> {

    @Mapping(source = "dataCard.id", target = "dataCardId")
    FlowDTO toDto(Flow flow);

    @Mapping(source = "dataCardId", target = "dataCard")
    Flow toEntity(FlowDTO flowDTO);

    default Flow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Flow flow = new Flow();
        flow.setId(id);
        return flow;
    }
}
