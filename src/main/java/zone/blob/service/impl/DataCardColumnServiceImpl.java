package zone.blob.service.impl;

import zone.blob.service.DataCardColumnService;
import zone.blob.domain.DataCardColumn;
import zone.blob.repository.DataCardColumnRepository;
import zone.blob.service.dto.DataCardColumnDTO;
import zone.blob.service.mapper.DataCardColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DataCardColumn}.
 */
@Service
@Transactional
public class DataCardColumnServiceImpl implements DataCardColumnService {

    private final Logger log = LoggerFactory.getLogger(DataCardColumnServiceImpl.class);

    private final DataCardColumnRepository dataCardColumnRepository;

    private final DataCardColumnMapper dataCardColumnMapper;

    public DataCardColumnServiceImpl(DataCardColumnRepository dataCardColumnRepository, DataCardColumnMapper dataCardColumnMapper) {
        this.dataCardColumnRepository = dataCardColumnRepository;
        this.dataCardColumnMapper = dataCardColumnMapper;
    }

    /**
     * Save a dataCardColumn.
     *
     * @param dataCardColumnDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataCardColumnDTO save(DataCardColumnDTO dataCardColumnDTO) {
        log.debug("Request to save DataCardColumn : {}", dataCardColumnDTO);
        DataCardColumn dataCardColumn = dataCardColumnMapper.toEntity(dataCardColumnDTO);
        dataCardColumn = dataCardColumnRepository.save(dataCardColumn);
        return dataCardColumnMapper.toDto(dataCardColumn);
    }

    /**
     * Get all the dataCardColumns.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataCardColumnDTO> findAll() {
        log.debug("Request to get all DataCardColumns");
        return dataCardColumnRepository.findAll().stream()
            .map(dataCardColumnMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dataCardColumn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataCardColumnDTO> findOne(Long id) {
        log.debug("Request to get DataCardColumn : {}", id);
        return dataCardColumnRepository.findById(id)
            .map(dataCardColumnMapper::toDto);
    }

    /**
     * Delete the dataCardColumn by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataCardColumn : {}", id);
        dataCardColumnRepository.deleteById(id);
    }
}
