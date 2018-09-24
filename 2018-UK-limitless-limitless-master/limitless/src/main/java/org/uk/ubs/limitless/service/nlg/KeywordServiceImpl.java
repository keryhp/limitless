package org.uk.ubs.limitless.service.nlg;

import org.uk.ubs.limitless.api.KeywordService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeywordServiceImpl implements KeywordService {
    @Override
    public List<String> buildKeywords() throws IOException {
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\keywords.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> keywords = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            keywords.add(st);
        }
        return keywords;
    }
}
