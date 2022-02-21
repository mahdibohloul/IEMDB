package infrastructure.time.services;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
