package zone.blob.service.impl;

import zone.blob.service.DataCardService;
import zone.blob.domain.DataCard;
import zone.blob.repository.DataCardRepository;
import zone.blob.service.dto.DataCardDTO;
import zone.blob.service.mapper.DataCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DataCard}.
 */
@Service
@Transactional
public class DataCardServiceImpl implements DataCardService {

    private final Logger log = LoggerFactory.getLogger(DataCardServiceImpl.class);

    private final DataCardRepository dataCardRepository;

    private final DataCardMapper dataCardMapper;

    public DataCardServiceImpl(DataCardRepository dataCardRepository, DataCardMapper dataCardMapper) {
        this.dataCardRepository = dataCardRepository;
        this.dataCardMapper = dataCardMapper;
    }

    /**
     * Save a dataCard.
     *
     * @param dataCardDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataCardDTO save(DataCardDTO dataCardDTO) {
        log.debug("Request to save DataCard : {}", dataCardDTO);
        DataCard dataCard = dataCardMapper.toEntity(dataCardDTO);
        dataCard = dataCardRepository.save(dataCard);
        return dataCardMapper.toDto(dataCard);
    }

    /**
     * Get all the dataCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DataCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataCards");
        return dataCardRepository.findAll(pageable)
            .map(dataCardMapper::toDto);
    }


    /**
     * Get one dataCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataCardDTO> findOne(Long id) {
        log.debug("Request to get DataCard : {}", id);
        return dataCardRepository.findById(id)
            .map(dataCardMapper::toDto);
    }

    /**
     * Delete the dataCard by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataCard : {}", id);
        dataCardRepository.deleteById(id);
    }
}
