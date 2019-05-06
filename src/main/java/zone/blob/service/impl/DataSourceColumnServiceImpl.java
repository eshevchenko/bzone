package zone.blob.service.impl;

import zone.blob.service.DataSourceColumnService;
import zone.blob.domain.DataSourceColumn;
import zone.blob.repository.DataSourceColumnRepository;
import zone.blob.service.dto.DataSourceColumnDTO;
import zone.blob.service.mapper.DataSourceColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DataSourceColumn}.
 */
@Service
@Transactional
public class DataSourceColumnServiceImpl implements DataSourceColumnService {

    private final Logger log = LoggerFactory.getLogger(DataSourceColumnServiceImpl.class);

    private final DataSourceColumnRepository dataSourceColumnRepository;

    private final DataSourceColumnMapper dataSourceColumnMapper;

    public DataSourceColumnServiceImpl(DataSourceColumnRepository dataSourceColumnRepository, DataSourceColumnMapper dataSourceColumnMapper) {
        this.dataSourceColumnRepository = dataSourceColumnRepository;
        this.dataSourceColumnMapper = dataSourceColumnMapper;
    }

    /**
     * Save a dataSourceColumn.
     *
     * @param dataSourceColumnDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataSourceColumnDTO save(DataSourceColumnDTO dataSourceColumnDTO) {
        log.debug("Request to save DataSourceColumn : {}", dataSourceColumnDTO);
        DataSourceColumn dataSourceColumn = dataSourceColumnMapper.toEntity(dataSourceColumnDTO);
        dataSourceColumn = dataSourceColumnRepository.save(dataSourceColumn);
        return dataSourceColumnMapper.toDto(dataSourceColumn);
    }

    /**
     * Get all the dataSourceColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DataSourceColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataSourceColumns");
        return dataSourceColumnRepository.findAll(pageable)
            .map(dataSourceColumnMapper::toDto);
    }


    /**
     * Get one dataSourceColumn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataSourceColumnDTO> findOne(Long id) {
        log.debug("Request to get DataSourceColumn : {}", id);
        return dataSourceColumnRepository.findById(id)
            .map(dataSourceColumnMapper::toDto);
    }

    /**
     * Delete the dataSourceColumn by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataSourceColumn : {}", id);
        dataSourceColumnRepository.deleteById(id);
    }
}
