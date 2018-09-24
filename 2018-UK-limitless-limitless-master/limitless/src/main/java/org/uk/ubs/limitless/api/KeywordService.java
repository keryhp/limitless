package org.uk.ubs.limitless.api;

import java.io.IOException;
import java.util.List;

public interface KeywordService {
    List<String> buildKeywords() throws IOException;
}
