package com.practice.onlinedonation.scheduler.databaseScheduler;

import com.practice.onlinedonation.Service.DonationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseScheduler {

    @Autowired
    private DonationService donationService;

    //This method cleans up the database entries that were used while testing
    @Scheduled(cron = "0 */5 * * * ?")
    public void cleanTestingDatabase(){
        log.info("Running Schedule task: ");
        donationService.cleanTestingDatabase();
    }
}
