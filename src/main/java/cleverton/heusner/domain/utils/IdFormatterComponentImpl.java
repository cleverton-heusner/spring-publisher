package cleverton.heusner.domain.utils;

import cleverton.heusner.port.input.utils.IdFormatterComponent;

public class IdFormatterComponentImpl implements IdFormatterComponent {

    @Override
    public long formatId(final String id) {
        return Long.parseLong(id.strip());
    }
}