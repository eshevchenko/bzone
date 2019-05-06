package zone.blob.service.impl;

import zone.blob.service.DataSourceService;
import zone.blob.domain.DataSource;
import zone.blob.repository.DataSourceRepository;
import zone.blob.service.dto.DataSourceDTO;
import zone.blob.service.mapper.DataSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DataSource}.
 */
@Service
@Transactional
public class DataSourceServiceImpl implements DataSourceService {

    private final Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    private final DataSourceRepository dataSourceRepository;

    private final DataSourceMapper dataSourceMapper;

    public DataSourceServiceImpl(DataSourceRepository dataSourceRepository, DataSourceMapper dataSourceMapper) {
        this.dataSourceRepository = dataSourceRepository;
        this.dataSourceMapper = dataSourceMapper;
    }

    /**
     * Save a dataSource.
     *
     * @param dataSourceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataSourceDTO save(DataSourceDTO dataSourceDTO) {
        log.debug("Request to save DataSource : {}", dataSourceDTO);
        DataSource dataSource = dataSourceMapper.toEntity(dataSourceDTO);
        dataSource = dataSourceRepository.save(dataSource);
        return dataSourceMapper.toDto(dataSource);
    }

    /**
     * Get all the dataSources.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataSourceDTO> findAll() {
        log.debug("Request to get all DataSources");
        return dataSourceRepository.findAll().stream()
            .map(dataSourceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dataSource by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataSourceDTO> findOne(Long id) {
        log.debug("Request to get DataSource : {}", id);
        return dataSourceRepository.findById(id)
            .map(dataSourceMapper::toDto);
    }

    /**
     * Delete the dataSource by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataSource : {}", id);
        dataSourceRepository.deleteById(id);
    }
}
