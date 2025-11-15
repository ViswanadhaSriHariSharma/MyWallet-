package Banking.MyApplication.service;

import Banking.MyApplication.model.SummaryDTO;

public interface SummaryService {
    SummaryDTO getSummaryByUser(Long userId);

}
