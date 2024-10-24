package cleverton.heusner.service.idformatter;

import org.springframework.stereotype.Service;

@Service
public class IdFormatterServiceImpl implements IdFormatterService {

    @Override
    public long formatId(final String id) {
        return Long.parseLong(id.strip());
    }
}
