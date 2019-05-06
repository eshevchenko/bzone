package zone.blob.service.impl;

import zone.blob.service.FlowService;
import zone.blob.domain.Flow;
import zone.blob.repository.FlowRepository;
import zone.blob.service.dto.FlowDTO;
import zone.blob.service.mapper.FlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Flow}.
 */
@Service
@Transactional
public class FlowServiceImpl implements FlowService {

    private final Logger log = LoggerFactory.getLogger(FlowServiceImpl.class);

    private final FlowRepository flowRepository;

    private final FlowMapper flowMapper;

    public FlowServiceImpl(FlowRepository flowRepository, FlowMapper flowMapper) {
        this.flowRepository = flowRepository;
        this.flowMapper = flowMapper;
    }

    /**
     * Save a flow.
     *
     * @param flowDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FlowDTO save(FlowDTO flowDTO) {
        log.debug("Request to save Flow : {}", flowDTO);
        Flow flow = flowMapper.toEntity(flowDTO);
        flow = flowRepository.save(flow);
        return flowMapper.toDto(flow);
    }

    /**
     * Get all the flows.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlowDTO> findAll() {
        log.debug("Request to get all Flows");
        return flowRepository.findAll().stream()
            .map(flowMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one flow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FlowDTO> findOne(Long id) {
        log.debug("Request to get Flow : {}", id);
        return flowRepository.findById(id)
            .map(flowMapper::toDto);
    }

    /**
     * Delete the flow by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Flow : {}", id);
        flowRepository.deleteById(id);
    }
}
