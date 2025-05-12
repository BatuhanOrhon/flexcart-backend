package com.app.flexcart.flexcart.backend.model.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.flexcart.flexcart.backend.model.entity.CampaignEntity;

public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    Optional<CampaignEntity> findByName(String name);

    Optional<CampaignEntity> findByCampaignId(Long campaign_id);

    List<CampaignEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDateTime startAndEnd1,
            LocalDateTime startAndEnd2);

    /**
     * Kullanıcı kodu rahatça tek bir argümanla çağırsın diye default wrapper.
     */
    default List<CampaignEntity> findActiveCampaigns(LocalDateTime date) {
        return findByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date);
    }

}
