package zone.blob.service.impl;

import zone.blob.service.DataSourceFileService;
import zone.blob.domain.DataSourceFile;
import zone.blob.repository.DataSourceFileRepository;
import zone.blob.service.dto.DataSourceFileDTO;
import zone.blob.service.mapper.DataSourceFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DataSourceFile}.
 */
@Service
@Transactional
public class DataSourceFileServiceImpl implements DataSourceFileService {

    private final Logger log = LoggerFactory.getLogger(DataSourceFileServiceImpl.class);

    private final DataSourceFileRepository dataSourceFileRepository;

    private final DataSourceFileMapper dataSourceFileMapper;

    public DataSourceFileServiceImpl(DataSourceFileRepository dataSourceFileRepository, DataSourceFileMapper dataSourceFileMapper) {
        this.dataSourceFileRepository = dataSourceFileRepository;
        this.dataSourceFileMapper = dataSourceFileMapper;
    }

    /**
     * Save a dataSourceFile.
     *
     * @param dataSourceFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataSourceFileDTO save(DataSourceFileDTO dataSourceFileDTO) {
        log.debug("Request to save DataSourceFile : {}", dataSourceFileDTO);
        DataSourceFile dataSourceFile = dataSourceFileMapper.toEntity(dataSourceFileDTO);
        dataSourceFile = dataSourceFileRepository.save(dataSourceFile);
        return dataSourceFileMapper.toDto(dataSourceFile);
    }

    /**
     * Get all the dataSourceFiles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataSourceFileDTO> findAll() {
        log.debug("Request to get all DataSourceFiles");
        return dataSourceFileRepository.findAll().stream()
            .map(dataSourceFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dataSourceFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataSourceFileDTO> findOne(Long id) {
        log.debug("Request to get DataSourceFile : {}", id);
        return dataSourceFileRepository.findById(id)
            .map(dataSourceFileMapper::toDto);
    }

    /**
     * Delete the dataSourceFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataSourceFile : {}", id);
        dataSourceFileRepository.deleteById(id);
    }
}
